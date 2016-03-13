package worldwonders.guest.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.revenj.patterns.ServiceLocator;
import worldwonders.Boot;
import worldwonders.guest.api.GuestService;

import java.io.IOException;
import java.util.Properties;

public class GuestServiceModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        final ServiceLocator locator;
        try {
            final Config config = ConfigFactory.load();
            final String jdbcUrl = config.getString("revenj.jdbcUrl");
            final Properties properties = new Properties();
            properties.put("revenj.resolveUnknown", "true");
            locator = Boot.configure(jdbcUrl, properties);
        } catch (final IOException e){
            throw new RuntimeException("Could not initialize ServiceLocator", e);
        }

        bindServices(serviceBinding(GuestService.class, locator.resolve(GuestServiceImpl.class)));
    }
}
