package worldwonders.comments.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

import worldwonders.comments.api.CommentsService;
import worldwonders.wonders.api.WondersService;

public class CommentsServiceModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindClient(WondersService.class);
        bindServices(serviceBinding(CommentsService.class, CommentsServiceImpl.class));
    }
}
