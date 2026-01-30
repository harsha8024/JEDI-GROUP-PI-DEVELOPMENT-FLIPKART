package com.flipfit;

import com.flipfit.rest.*;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;
import java.util.EnumSet;

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

        // Enable CORS
        configureCors(environment);

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
        System.out.println("✓ CORS enabled - API accessible from browser");
        System.out.println("========================================");
    }

    private void configureCors(Environment environment) {
        FilterRegistration.Dynamic filter = environment.servlets()
            .addFilter("CORS", CrossOriginFilter.class);
        
        // Configure CORS parameters
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        filter.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");
        
        System.out.println("CORS filter configured successfully");
    }
}

