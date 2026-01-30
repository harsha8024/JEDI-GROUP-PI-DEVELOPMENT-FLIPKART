package com.flipfit.rest;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Gym;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.Slot;
import com.flipfit.bean.User;
import com.flipfit.bean.Role;
import com.flipfit.business.GymOwnerInterface;
import com.flipfit.business.GymOwnerServiceImpl;
import com.flipfit.business.GymUserInterface;
import com.flipfit.business.GymUserServiceImpl;
import com.flipfit.business.SlotServiceInterface;
import com.flipfit.business.SlotServiceImpl;
import com.flipfit.exception.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalTime;
import java.util.List;

@Path("/owner")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GymOwnerController {

    private final GymOwnerInterface ownerService;
    private final GymUserInterface userService;
    private final SlotServiceInterface slotService;

    public GymOwnerController() {
        this.ownerService = new GymOwnerServiceImpl();
        this.userService = new GymUserServiceImpl();
        this.slotService = new SlotServiceImpl();
    }

    @POST
    @Path("/register")
    public Response registerOwner(GymOwner owner) {
        try {
            // Set role to OWNER
            Role role = new Role();
            role.setRoleName("OWNER");
            owner.setRole(role);
            
            userService.register(owner);
            // Return created owner data (without password)
            GymOwner created = (GymOwner) GymUserServiceImpl.getUserMap().get(owner.getEmail());
            if (created != null) created.setPassword(null);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (RegistrationFailedException | InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Registration failed: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("/login")
    public Response loginOwner(@QueryParam("email") String email, 
                               @QueryParam("password") String password) {
        try {
            boolean success = userService.login(email, password);
            if (success) {
                GymOwner logged = (GymOwner) GymUserServiceImpl.getUserMap().get(email);
                if (logged != null) logged.setPassword(null);
                return Response.ok(logged).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Login failed").build();
            }
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (InvalidCredentialsException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Login failed: " + e.getMessage())
                    .build();
        }
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

    @POST
    @Path("/slots/create")
    public Response createSlot(@QueryParam("gymId") String gymId,
                               @QueryParam("startTime") String startTime,
                               @QueryParam("endTime") String endTime,
                               @QueryParam("capacity") int capacity) {
        try {
            LocalTime start = LocalTime.parse(startTime);
            LocalTime end = LocalTime.parse(endTime);
            slotService.createSlot(gymId, start, end, capacity);
            return Response.status(Response.Status.CREATED).entity("Slot Created Successfully").build();
        } catch (RegistrationFailedException | InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid time format. Use HH:mm").build();
        }
    }

    @GET
    @Path("/slots/{gymId}")
    public Response getSlots(@PathParam("gymId") String gymId) {
        try {
            List<Slot> slots = slotService.getSlotsForGym(gymId);
            return Response.ok(slots).build();
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/slots/{slotId}")
    public Response updateSlot(@PathParam("slotId") String slotId,
                              @QueryParam("capacity") int capacity) {
        try {
            slotService.updateSlot(slotId, capacity);
            return Response.ok(java.util.Collections.singletonMap("message", "Slot Updated Successfully")).build();
        } catch (InvalidInputException | SlotNotFoundException | SlotOperationException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/slots/{slotId}")
    public Response deleteSlot(@PathParam("slotId") String slotId) {
        try {
            slotService.deleteSlot(slotId);
            return Response.ok(java.util.Collections.singletonMap("message", "Slot Deleted Successfully")).build();
        } catch (InvalidInputException | SlotNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/schedule/update")
    public Response updateSchedule(@QueryParam("gymId") String gymId) {
        try {
            ownerService.updateSchedule(gymId);
            return Response.ok(java.util.Collections.singletonMap("message", "Schedule Updated Successfully")).build();
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}

