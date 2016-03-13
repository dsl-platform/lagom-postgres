package worldwonders.guest.api;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;
import worldwonders.model.Wonder;

import java.util.List;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

public interface GuestService extends Service {
    ServiceCall<NotUsed, NotUsed, List<Wonder>> getAll();

    @Override
    default Descriptor descriptor() {
        return named("guestservice").with(
                restCall(Method.GET, "/guest/getAll", getAll())
        ).withAutoAcl(true);
    }
}
