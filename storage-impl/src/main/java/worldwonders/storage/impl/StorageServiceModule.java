package worldwonders.storage.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

import worldwonders.storage.api.StorageService;

public class StorageServiceModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindServices(serviceBinding(StorageService.class, StorageServiceImpl.class));
    }
}
