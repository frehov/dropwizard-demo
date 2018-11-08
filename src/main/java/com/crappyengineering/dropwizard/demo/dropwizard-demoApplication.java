package com.crappyengineering.dropwizard.demo;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class dropwizard-demoApplication extends Application<dropwizard-demoConfiguration> {

    public static void main(final String[] args) throws Exception {
        new dropwizard-demoApplication().run(args);
    }

    @Override
    public String getName() {
        return "dropwizard-demo";
    }

    @Override
    public void initialize(final Bootstrap<dropwizard-demoConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final dropwizard-demoConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
