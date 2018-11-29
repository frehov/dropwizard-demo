package com.crappyengineering.dropwizard.demo;

import javax.persistence.EntityManager;

import com.crappyengineering.dropwizard.demo.model.Task;
import com.crappyengineering.dropwizard.demo.model.Tasklist;
import com.crappyengineering.dropwizard.demo.repository.TaskRepository;
import com.crappyengineering.dropwizard.demo.repository.TasklistRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DemoApplication extends Application<DemoConfiguration> {

    public static void main(final String[] args) throws Exception {
        new DemoApplication().run(args);
    }

    @Override
    public String getName() {
        return "dropwizard-demo";
    }

    private final HibernateBundle<DemoConfiguration> hibernate = new HibernateBundle<DemoConfiguration>(Tasklist.class, Task.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(DemoConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(final Bootstrap<DemoConfiguration> bootstrap) {
        // TODO: application initialization
        configureJacksonObjectMapper(bootstrap.getObjectMapper());

        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor()
                )
        );
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(final DemoConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application

        final EntityManager entityManager = hibernate.getSessionFactory().createEntityManager();
        final TasklistRepository tasklistRepository = new TasklistRepository(entityManager);
        final TaskRepository taskRepository= new TaskRepository(entityManager);

    }

    private void configureJacksonObjectMapper(ObjectMapper mapper){
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true);
        mapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true);
    }

}
