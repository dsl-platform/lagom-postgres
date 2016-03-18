package worldwonders.storage.impl;

import static java.util.concurrent.CompletableFuture.completedFuture;

import java.io.IOException;
import java.net.URL;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.revenj.patterns.PersistableRepository;
import org.revenj.patterns.ServiceLocator;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.lightbend.lagom.javadsl.api.transport.ResponseHeader;
import com.lightbend.lagom.javadsl.server.HeaderServiceCall;
import com.lightbend.lagom.javadsl.server.ServerServiceCall;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.NotUsed;
import akka.japi.Pair;
import worldwonders.Boot;
import worldwonders.storage.ImageCache;
import worldwonders.storage.api.StorageService;
import worldwonders.storage.repositories.ImageCacheRepository;

public class StorageServiceImpl implements StorageService {
    private LoadingCache<URL, ImageCache> localImageCache;

    public StorageServiceImpl() throws IOException {
        final Config config = ConfigFactory.load();
        final String jdbcUrl = config.getString("revenj.jdbcUrl");
        final ServiceLocator locator = Boot.configure(jdbcUrl);

        final PersistableRepository<ImageCache> imageCacheRepository =
                locator.resolve(ImageCacheRepository.class);

        localImageCache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(new ImageDownloader(imageCacheRepository));

    }

    static String toInternetDate(final OffsetDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.RFC_1123_DATE_TIME);
    }

    static OffsetDateTime fromInternetDate(final String dateTime) {
        return OffsetDateTime.parse(dateTime, DateTimeFormatter.RFC_1123_DATE_TIME);
    }

    private static final int EXPIRY_SECONDS = 3600 * 24;

    @Override
    public ServerServiceCall<String, NotUsed, byte[]> downloadImage() {
        return HeaderServiceCall.of((requestHeader, id, name) -> {
            try {
                final URL url = new URL(new String(Base64.decodeBase64(id.getBytes("ISO-8859-1")), "UTF-8"));
                final ImageCache image = localImageCache.get(url);
                final OffsetDateTime cacheCreatedAt = image.getCreatedAt().withNano(0);

                final Optional<String> ifModifiedSince = requestHeader.getHeader("If-Modified-Since");
                if (ifModifiedSince.isPresent()) {
                    final OffsetDateTime ifModifiedSinceDate = fromInternetDate(ifModifiedSince.get());
                    if (!ifModifiedSinceDate.isBefore(cacheCreatedAt)) {
                        final ResponseHeader responseHeader = ResponseHeader.NO_CONTENT.withStatus(304);
                        return completedFuture(Pair.create(responseHeader, new byte[0]));
                    }
                }

                final String modifiedDate = toInternetDate(cacheCreatedAt);
                final String expiresDate = toInternetDate(cacheCreatedAt.plusSeconds(EXPIRY_SECONDS));
                final String nowDate = toInternetDate(OffsetDateTime.now());

                final ResponseHeader responseHeader = ResponseHeader.OK
                        .withHeader("Mime-Type", "application/jpeg")
                        .withHeader("Last-Modified", modifiedDate)
                        .withHeader("Expires", expiresDate)
                        .withHeader("Date", nowDate)
                        .withHeader("Pragma", "Public")
                        .withHeader("Cache-Control", "public, max-age=" + EXPIRY_SECONDS + ", must-revalidate");

                final byte[] body = image.getBody();
                return completedFuture(Pair.create(responseHeader, body));
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
