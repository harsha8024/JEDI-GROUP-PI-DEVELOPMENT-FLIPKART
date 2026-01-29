package com.flipfit.rest;

import com.flipfit.bean.Gym;
import com.flipfit.bean.User;
import com.flipfit.business.GymAdminInterface;
import com.flipfit.business.GymAdminServiceImpl;
import com.flipfit.exception.ApprovalFailedException;
import com.flipfit.exception.InvalidInputException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GymAdminController {

    private final GymAdminInterface adminService;

    public GymAdminController() {
        this.adminService = new GymAdminServiceImpl();
    }

    @GET
    @Path("/gyms/pending")
    public List<Gym> viewPendingApprovals() {
        return adminService.viewPendingApprovals();
    }

    @PUT
    @Path("/gyms/approve/{gymId}")
    public Response approveGym(@PathParam("gymId") String gymId) {
        try {
            adminService.approveGym(gymId);
            return Response.ok("Gym Approved Successfully").build();
        } catch (ApprovalFailedException e) {
             return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/gyms/reject/{gymId}")
    public Response rejectGym(@PathParam("gymId") String gymId) {
        try {
            adminService.rejectGym(gymId);
            return Response.ok("Gym Rejected Successfully").build();
         } catch (ApprovalFailedException e) {
             return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/users")
    public List<User> viewAllUsers() {
        return adminService.viewAllUsers();
    }

    @GET
    @Path("/gyms")
    public List<Gym> viewAllGyms() {
        return adminService.viewAllGyms();
    }
}

