package com.flipfit;

import com.flipfit.rest.*;
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

        // Register All REST Controllers
        environment.jersey().register(new UserController());
        environment.jersey().register(new GymCustomerController());
        environment.jersey().register(new GymOwnerController());
        environment.jersey().register(new GymAdminController());
        environment.jersey().register(new PaymentController());
        environment.jersey().register(new NotificationController());
        environment.jersey().register(new RegistrationController());
        environment.jersey().register(new SlotController());

        System.out.println("========================================");
        System.out.println("   FlipFit Application Started!");
        System.out.println("========================================");
        System.out.println("REST API is now available at:");
        System.out.println("  - User API: http://localhost:8080/user");
        System.out.println("  - Customer API: http://localhost:8080/customer");
        System.out.println("  - Owner API: http://localhost:8080/owner");
        System.out.println("  - Admin API: http://localhost:8080/admin");
        System.out.println("  - Payment API: http://localhost:8080/payment");
        System.out.println("  - Notification API: http://localhost:8080/notification");
        System.out.println("  - Registration API: http://localhost:8080/registration");
        System.out.println("  - Slot API: http://localhost:8080/slot");
        System.out.println("========================================");
    }
}

