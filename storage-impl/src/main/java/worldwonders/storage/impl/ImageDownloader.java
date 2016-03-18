package worldwonders.storage.impl;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.OffsetDateTime;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.revenj.patterns.PersistableRepository;

import com.google.common.cache.CacheLoader;
import com.google.common.io.ByteStreams;

import worldwonders.storage.ImageCache;

class ImageDownloader extends CacheLoader<URL, ImageCache> {
    private final PersistableRepository<ImageCache> imageCacheRepository;

    ImageDownloader(final PersistableRepository<ImageCache> imageCacheRepository) {
        this.imageCacheRepository = imageCacheRepository;
    }

    public ImageCache load(final URL url) throws Exception {
        final Optional<ImageCache> cached = imageCacheRepository.find(url.toString());
        if (cached.isPresent()) {
            return cached.get();
        }

        final URLConnection conn = url.openConnection();
        OffsetDateTime createdAt;
        try {
            createdAt = StorageServiceImpl.fromInternetDate(conn.getHeaderField("Last-Modified"));
        }
        catch (final Exception e) {
            createdAt = OffsetDateTime.now();
        }

        byte[] body = ByteStreams.toByteArray(conn.getInputStream());
        Integer width = null;
        Integer height = null;

        try {
            final BufferedImage original = ImageIO.read(new ByteArrayInputStream(body));
            width = original.getWidth();
            height = original.getHeight();

            final BufferedImage converted = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            final Graphics2D graphics = converted.createGraphics();
            graphics.drawImage(original, 0, 0, width, height, null);
            graphics.dispose();

            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(converted, "jpg", baos);
            body = baos.toByteArray();
        }
        catch (final IOException e) {}

        final ImageCache imageCache = new ImageCache()
            .setUrl(url.toURI())
            .setBody(body)
            .setSize(body.length)
            .setWidth(width)
            .setHeight(height)
            .setCreatedAt(createdAt);

        try {
            imageCacheRepository.insert(imageCache);
        }
        catch (final Exception e) {}

        return imageCache;
    }
}
