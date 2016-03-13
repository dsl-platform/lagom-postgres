package worldwonders.admin.api;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

public interface AdminService extends Service {
    ServiceCall<NotUsed, NotUsed, NotUsed> reset();

    @Override
    default Descriptor descriptor() {
        return named("adminservice").with(
                restCall(Method.GET, "/admin/reset", reset())
        ).withAutoAcl(true);
    }
}
