package com.flipfit;

import com.flipfit.rest.GymAdminController;
import com.flipfit.rest.GymCustomerController;
import com.flipfit.rest.GymOwnerController;
import com.flipfit.rest.UserController;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class FlipFitApplication extends Application<FlipFitConfiguration> {

    public static void main(final String[] args) throws Exception {
        new FlipFitApplication().run(args);
    }

    @Override
    public String getName() {
        return "FlipFit";
    }

    @Override
    public void initialize(final Bootstrap<FlipFitConfiguration> bootstrap) {
        // Initialize assets, bundles, etc.
    }

    @Override
    public void run(final FlipFitConfiguration configuration,
                    final Environment environment) {

        // Register Resources
        environment.jersey().register(new GymAdminController());
        environment.jersey().register(new GymCustomerController());
        environment.jersey().register(new GymOwnerController());
        environment.jersey().register(new UserController());

        System.out.println("FlipFit Application Started successfully!");
    }
}

