package worldwonders.comments.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

import java.util.List;

import org.revenj.json.lagom.DslJsonLagomSerialization;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import akka.NotUsed;
import worldwonders.comments.Comment;

public interface CommentsService extends Service {
    ServiceCall<NotUsed, NotUsed, List<Comment>> findAll();

    ServiceCall<String, NotUsed, List<Comment>> findForTopic();

    ServiceCall<NotUsed, Comment, NotUsed> postComment();

    @Override
    default Descriptor descriptor() {
        return named("comments-service").with(
                restCall(Method.GET, "/comments", findAll()),
                restCall(Method.GET, "/comments/:topic", findForTopic()),
                restCall(Method.POST, "/comment", postComment())
        ).withAutoAcl(true)
        .replaceAllMessageSerializers(DslJsonLagomSerialization.optimizeSerializersFor(CommentsService.class));
    }
}
