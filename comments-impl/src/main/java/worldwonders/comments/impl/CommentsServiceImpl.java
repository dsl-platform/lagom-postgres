package worldwonders.comments.impl;

import static java.util.concurrent.CompletableFuture.completedFuture;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import org.revenj.patterns.DomainEventStore;
import org.revenj.patterns.ServiceLocator;

import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.NotUsed;
import worldwonders.Boot;
import worldwonders.comments.Comment;
import worldwonders.comments.api.CommentsService;
import worldwonders.comments.repositories.CommentRepository;
import worldwonders.wonders.NewComment;
import worldwonders.wonders.api.WondersService;

public class CommentsServiceImpl implements CommentsService {
    private final WondersService wondersService;
    private final DomainEventStore<Comment> commentStore;

    @Inject
    public CommentsServiceImpl(final WondersService wondersService) throws IOException {
        this.wondersService = wondersService;

        final Config config = ConfigFactory.load();
        final String jdbcUrl = config.getString("revenj.jdbcUrl");
        final ServiceLocator locator = Boot.configure(jdbcUrl);

        commentStore = locator.resolve(CommentRepository.class);
    }

    @Override
    public ServiceCall<NotUsed, NotUsed, List<Comment>> findAll() {
        return (id, request) -> completedFuture(commentStore.search());
    }

    public static final int MIN_RATING = 1;
    public static final int MAX_RATING = 5;

    @Override
    public ServiceCall<NotUsed, Comment, NotUsed> postComment() {
        return (id, request) -> {
            final int rating = request.getRating();
            if (rating < MIN_RATING || rating > MAX_RATING)
                throw new IllegalArgumentException("Invalid rating: " + rating);

            // persist Comment event to the database
            commentStore.submit(request);

            final List<Comment> comments = commentStore
                    .search(new Comment.findByTopic(request.getTopic()));

            // calculate latest topic count & average rating
            final int topicCount = comments.size();
            final double averageRating = comments
                    .stream()
                    .mapToInt(Comment::getRating)
                    .average()
                    .getAsDouble();

            final NewComment newComment = new NewComment()
                .setAverageRating(averageRating)
                .setTotalRatings(topicCount)
                .setWonderName(request.getTopic())
                .setComment(
                        new worldwonders.wonders.Comment()
                        .setUser(request.getUser())
                        .setBody(request.getBody())
                        .setRating(rating)
                        .setCreatedAt(request.getQueuedAt()));

            CompletionStage<NotUsed> response = wondersService.newComment().invoke(newComment);
            return response.thenApply(notUsed -> {
                System.out.println(notUsed);
                return NotUsed.getInstance();
            });
        };
    }

    @Override
    public ServiceCall<String, NotUsed, List<Comment>> findForTopic() {
        return (id, request) -> completedFuture(commentStore.search(new Comment.findByTopic(id)));
    }
}
