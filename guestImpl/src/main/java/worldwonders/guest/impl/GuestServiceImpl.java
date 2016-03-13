package worldwonders.guest.impl;

import org.revenj.patterns.SearchableRepository;
import worldwonders.guest.api.GuestService;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import worldwonders.model.Wonder;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static java.util.concurrent.CompletableFuture.completedFuture;

public class GuestServiceImpl implements GuestService {
    private final SearchableRepository<Wonder> wonderRepostitory;

    public GuestServiceImpl(final SearchableRepository<Wonder> wonderRepository) {
        this.wonderRepostitory = wonderRepository;
    }

    @Override
    public ServiceCall<NotUsed, NotUsed, List<Wonder>> getAll() {
        return (id, request) -> {
            try {
                return completedFuture(
                        wonderRepostitory.query()
                                .sortedBy(it -> it.getEnglishName()).list());
            }
            catch (final IOException e) {
                e.printStackTrace();
                return completedFuture(Collections.EMPTY_LIST);
            }
        };
    }
}
