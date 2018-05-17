package com.jacob.jmail;


import org.jdbi.v3.core.Jdbi;
import com.jacob.jmail.db.MessageDAO;
import com.jacob.jmail.db.UserDAO;
import com.jacob.jmail.resources.LogInResource;
import com.jacob.jmail.resources.MessageResource;
import com.jacob.jmail.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;


public class JMailApplication extends Application<JMailConfiguration> {

    public static void main(final String[] args) throws Exception {
        new JMailApplication().run(new String[] {"server", "config.yml"});
    }

    @Override
    public String getName() {
        return "JMail";
    }

    @Override
    public void initialize(final Bootstrap<JMailConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final JMailConfiguration configuration,
                    final Environment environment) {
    		final JdbiFactory factory = new JdbiFactory();
    		final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");
    		final UserDAO userDAO = jdbi.onDemand(UserDAO.class);
    		final MessageDAO messageDAO = jdbi.onDemand(MessageDAO.class);
        environment.jersey().register(new UserResource(userDAO));
        environment.jersey().register(new MessageResource(messageDAO, userDAO));
        environment.jersey().register(new LogInResource(userDAO));
    }

}
