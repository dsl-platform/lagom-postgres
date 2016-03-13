package worldwonders.admin.impl;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.revenj.patterns.ServiceLocator;
import java.util.Properties;
import worldwonders.Boot;
import worldwonders.admin.api.AdminService;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

import java.io.IOException;

public class AdminServiceModule extends AbstractModule implements ServiceGuiceSupport {
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

        bindServices(serviceBinding(AdminService.class, locator.resolve(AdminServiceImpl.class)));
    }
}
