package com.flipfit.rest;

import com.flipfit.bean.Registration;
import com.flipfit.business.RegistrationInterface;
import com.flipfit.business.RegistrationServiceImpl;
import com.flipfit.exception.InvalidInputException;
import com.flipfit.exception.RegistrationAlreadyExistsException;
import com.flipfit.exception.RegistrationFailedException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/registration")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegistrationController {

    private final RegistrationInterface registrationService;

    public RegistrationController() {
        this.registrationService = new RegistrationServiceImpl();
    }

    @POST
    @Path("/create")
    public Response createRegistration(@QueryParam("userId") String userId,
                                      @QueryParam("role") String role) {
        try {
            Registration registration = registrationService.createRegistration(userId, role);
            return Response.status(Response.Status.CREATED).entity(registration).build();
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (RegistrationAlreadyExistsException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (RegistrationFailedException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/pending")
    public Response getPendingRegistrations() {
        List<Registration> registrations = registrationService.getPendingRegistrations();
        return Response.ok(registrations).build();
    }

    @PUT
    @Path("/approve/{registrationId}")
    public Response approveRegistration(@PathParam("registrationId") String registrationId,
                                       @QueryParam("adminId") String adminId) {
        try {
            boolean success = registrationService.approveRegistration(registrationId, adminId);
            if (success) {
                return Response.ok(java.util.Collections.singletonMap("message", "Registration Approved Successfully")).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Approval Failed").build();
            }
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (RegistrationFailedException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/reject/{registrationId}")
    public Response rejectRegistration(@PathParam("registrationId") String registrationId,
                                      @QueryParam("adminId") String adminId) {
        try {
            boolean success = registrationService.rejectRegistration(registrationId, adminId);
            if (success) {
                return Response.ok(java.util.Collections.singletonMap("message", "Registration Rejected Successfully")).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Rejection Failed").build();
            }
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (RegistrationFailedException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
