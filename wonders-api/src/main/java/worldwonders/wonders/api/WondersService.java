package worldwonders.wonders.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

import java.util.List;

import org.revenj.json.lagom.DslJsonLagomSerialization;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import akka.NotUsed;
import worldwonders.wonders.NewComment;
import worldwonders.wonders.Wonder;

public interface WondersService extends Service {
    ServiceCall<NotUsed, NotUsed, List<Wonder>> findAll();

    ServiceCall<NotUsed, NotUsed, List<String>> getWonderTypes();

    ServiceCall<NotUsed, Wonder, NotUsed> makeWonder();

    ServiceCall<NotUsed, NewComment, NotUsed> newComment();

    @Override
    default Descriptor descriptor() {
        return named("wonders-service").with(
                restCall(Method.GET, "/wonders", findAll()),
                restCall(Method.GET, "/wonderTypes", getWonderTypes()),
                restCall(Method.POST, "/wonder", makeWonder()),
                restCall(Method.POST, "/new-comment", newComment())
        ).withAutoAcl(true)
        .replaceAllMessageSerializers(DslJsonLagomSerialization.optimizeSerializersFor(WondersService.class));
    }
}
