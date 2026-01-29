package com.flipfit.rest;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Gym;
import com.flipfit.business.GymOwnerInterface;
import com.flipfit.business.GymOwnerServiceImpl;
import com.flipfit.exception.InvalidInputException;
import com.flipfit.exception.RegistrationFailedException;
import com.flipfit.exception.UserNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/owner")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GymOwnerController {

    private final GymOwnerInterface ownerService;

    public GymOwnerController() {
        this.ownerService = new GymOwnerServiceImpl();
    }

    @POST
    @Path("/gyms/register")
    public Response registerGym(Gym gym) {
        try {
            ownerService.registerGym(gym);
            return Response.status(Response.Status.CREATED).entity("Gym Registered Successfully").build();
        } catch (RegistrationFailedException | InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/gyms/{ownerId}")
    public Response viewMyGyms(@PathParam("ownerId") String ownerId) {
        try {
            List<Gym> gyms = ownerService.viewMyGyms(ownerId);
            return Response.ok(gyms).build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (InvalidInputException e) {
             return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/bookings/{ownerId}")
    public Response viewBookings(@PathParam("ownerId") String ownerId) {
        try {
             List<Booking> bookings = ownerService.viewBookings(ownerId);
             return Response.ok(bookings).build();
        } catch (UserNotFoundException e) {
             return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (InvalidInputException e) {
              return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}

