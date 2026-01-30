package com.flipfit.rest;

import com.flipfit.bean.User;
import com.flipfit.business.GymUserServiceImpl;
import com.flipfit.business.GymUserInterface;
import com.flipfit.exception.InvalidCredentialsException;
import com.flipfit.exception.RegistrationFailedException;
import com.flipfit.exception.UserNotFoundException;
import com.flipfit.exception.InvalidInputException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    private final GymUserInterface userService;

    public UserController() {
        this.userService = new GymUserServiceImpl();
    }

    @POST
    @Path("/login")
    public Response login(User userCredentials) {
        try {
            boolean loggedIn = userService.login(userCredentials.getEmail(), userCredentials.getPassword());
            if (loggedIn) {
                // Return the user object (without password) so clients get useful data
                com.flipfit.bean.User loggedUser = GymUserServiceImpl.getUserMap().get(userCredentials.getEmail());
                if (loggedUser != null) loggedUser.setPassword(null);
                return Response.ok(loggedUser).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Login Failed").build();
            }
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (InvalidCredentialsException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        } catch (InvalidInputException e) {
             return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/register")
    public Response register(User user) {
        try {
            userService.register(user);
            // Fetch created user and return it (without password)
            com.flipfit.bean.User created = GymUserServiceImpl.getUserMap().get(user.getEmail());
            if (created != null) created.setPassword(null);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (RegistrationFailedException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (InvalidInputException e) {
             return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/update-password")
    public Response updatePassword(@QueryParam("email") String email, @QueryParam("password") String password) {
        try {
             userService.updatePassword(email, password);
             return Response.ok(java.util.Collections.singletonMap("message", "Password updated successfully")).build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/logout")
    public Response logout() {
        userService.logout();
        return Response.ok(java.util.Collections.singletonMap("message", "Logged out successfully")).build();
    }
}

