package worldwonders.storage.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import akka.NotUsed;

public interface StorageService extends Service {
    ServiceCall<String, NotUsed, byte[]> downloadImage();

    @Override
    default Descriptor descriptor() {
        return named("storage-service").with(
                restCall(Method.GET, "/image/:url", downloadImage())
                    .withResponseSerializer(new ByteArrayResponseSerializer())
        ).withAutoAcl(true);
    }
}
