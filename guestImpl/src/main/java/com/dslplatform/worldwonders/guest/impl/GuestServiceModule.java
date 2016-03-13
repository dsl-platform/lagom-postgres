package com.dslplatform.worldwonders.guest.impl;

import com.dslplatform.worldwonders.guest.api.GuestService;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public class GuestServiceModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindServices(serviceBinding(GuestService.class, GuestServiceImpl.class));
    }
}
