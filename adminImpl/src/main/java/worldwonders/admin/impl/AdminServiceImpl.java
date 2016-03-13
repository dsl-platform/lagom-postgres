package worldwonders.admin.impl;

import com.dslplatform.json.DslJson;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.revenj.json.DslJsonSerialization;
import org.revenj.patterns.PersistableRepository;
import org.revenj.patterns.ServiceLocator;
import org.revenj.serialization.Serialization;
import worldwonders.Boot;
import worldwonders.admin.api.AdminService;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import worldwonders.model.Wonder;
import worldwonders.model.repositories.WonderRepository;

import java.io.IOException;
import java.util.List;

import static java.util.concurrent.CompletableFuture.completedFuture;

public class AdminServiceImpl implements AdminService {
    private final PersistableRepository<Wonder> wonderRepostiory;
    private final DslJsonSerialization jsonSerialization;

    public AdminServiceImpl(
            final PersistableRepository<Wonder> wonderRepository,
            final DslJsonSerialization jsonSerialization) {
        this.wonderRepostiory = wonderRepository;
        this.jsonSerialization = jsonSerialization;
    }

    @Override
    public ServiceCall<NotUsed, NotUsed, NotUsed> reset() {
        return (id, request) -> {
            try {
                final List<Wonder> currentWonders = wonderRepostiory.search();
                wonderRepostiory.delete(currentWonders);

                final byte[] wondersJson = Resources.toByteArray(
                        AdminServiceImpl.class.getResource("/wonders.json"));

                final List<Wonder> defaultWonders =
                    jsonSerialization.deserializeList(
                            Wonder.class,
                            wondersJson,
                            wondersJson.length);

                wonderRepostiory.insert(defaultWonders);
                return completedFuture(NotUsed.getInstance());
            } catch (final IOException e) {
                e.printStackTrace();
                return completedFuture(NotUsed.getInstance());
            }
        };
    }
}
