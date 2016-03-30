package worldwonders.wonders.impl;

import static java.util.concurrent.CompletableFuture.completedFuture;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.revenj.patterns.DataContext;
import org.revenj.patterns.PersistableRepository;
import org.revenj.patterns.ServiceLocator;

import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.server.ServerServiceCall;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.NotUsed;
import worldwonders.Boot;
import worldwonders.wonders.Comment;
import worldwonders.wonders.NewComment;
import worldwonders.wonders.Wonder;
import worldwonders.wonders.WonderType;
import worldwonders.wonders.api.WondersService;

public class WondersServiceImpl implements WondersService {
    private final DataContext dataContext;

    public WondersServiceImpl() throws IOException {
        final Config config = ConfigFactory.load();
        final String jdbcUrl = config.getString("revenj.jdbcUrl");
        final ServiceLocator locator = Boot.configure(jdbcUrl);
        this.dataContext = locator.resolve(DataContext.class);

    }

    @Override
    public ServerServiceCall<NotUsed, NotUsed, List<Wonder>> findAll() {
        return (id, request) -> {
            return completedFuture(dataContext.search(Wonder.class));
        };
      }

    @Override
    public ServiceCall<NotUsed, NotUsed, List<String>> getWonderTypes() {
        return (id, request) -> completedFuture(Arrays.asList(WonderType.values()).stream()
            .map(wt -> wt.toString()).collect(Collectors.toList()));
    }

    @Override
    public ServiceCall<NotUsed, Wonder, NotUsed> makeWonder() {
        return (id, request) -> {
            try {
                dataContext.create(request);
            }
            catch (final IOException e) {
                throw new RuntimeException(e);
            }

            return completedFuture(NotUsed.getInstance());
        };
    }

    public static final int MAX_CHOSEN_COMMENTS = 5;

    @Override
    public ServiceCall<NotUsed, NewComment, NotUsed> newComment() {
        return (id, request) -> {
            final String wonderName = request.getWonderName();
            final Wonder wonder = dataContext.find(Wonder.class, wonderName).get();

            wonder
                .setAverageRating(request.getAverageRating())
                .setTotalRatings(request.getTotalRatings());

            dataContext.submit(request);

            final Comment newComment = request.getComment();

            final List<Comment> chosenComments = wonder.getChosenComments();
            if (chosenComments.size() < MAX_CHOSEN_COMMENTS) {
                chosenComments.add(newComment);
            } else {
                for (int i = 0; i < chosenComments.size(); i ++) {
                    final Comment oldComment = chosenComments.get(i);
                    if (oldComment.getRating() <= newComment.getRating() &&
                            oldComment.getCreatedAt().isBefore(newComment.getCreatedAt())) {
                        chosenComments.set(i, newComment);
                    }
                }
            }

            try {
                dataContext.update(wonder);
            }
            catch (final IOException e) {
                throw new RuntimeException(e);
            }

            return completedFuture(NotUsed.getInstance());
        };
    }


}
