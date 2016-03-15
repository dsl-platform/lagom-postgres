package worldwonders.wonders.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

import worldwonders.wonders.api.WondersService;

public class WondersServiceModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindServices(serviceBinding(WondersService.class, WondersServiceImpl.class));
    }
}
