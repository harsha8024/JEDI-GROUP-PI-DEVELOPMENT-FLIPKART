package com.flipfit.rest;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Gym;
import com.flipfit.bean.Slot;
import com.flipfit.bean.User;
import com.flipfit.bean.Role;
import com.flipfit.business.GymCustomerInterface;
import com.flipfit.business.GymCustomerServiceImpl;
import com.flipfit.business.GymUserInterface;
import com.flipfit.business.GymUserServiceImpl;
import com.flipfit.exception.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.List;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GymCustomerController {

    private final GymCustomerInterface customerService;
    private final GymUserInterface userService;

    public GymCustomerController() {
        this.customerService = new GymCustomerServiceImpl();
        this.userService = new GymUserServiceImpl();
    }

    @POST
    @Path("/register")
    public Response registerCustomer(User user) {
        try {
            // Set role to CUSTOMER
            Role role = new Role();
            role.setRoleName("CUSTOMER");
            user.setRole(role);
            
            userService.register(user);
            // Return created customer (without password)
            com.flipfit.bean.GymCustomer created = (com.flipfit.bean.GymCustomer) GymUserServiceImpl.getUserMap().get(user.getEmail());
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
    public Response loginCustomer(@QueryParam("email") String email, 
                                   @QueryParam("password") String password) {
        try {
            boolean success = userService.login(email, password);
            if (success) {
                com.flipfit.bean.GymCustomer logged = (com.flipfit.bean.GymCustomer) GymUserServiceImpl.getUserMap().get(email);
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

    @GET
    @Path("/gyms")
    public Response viewCenters(@QueryParam("city") String city) {
        try {
            List<Gym> gyms = customerService.viewCenters(city);
            return Response.ok(gyms).build();
        } catch (InvalidInputException e) {
             return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (GymNotFoundException e) {
             return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/gyms/{gymId}/slots")
    public Response viewSlots(@PathParam("gymId") String gymId, @QueryParam("date") String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            List<Slot> slots = customerService.viewSlotsForGym(gymId, localDate);
            return Response.ok(slots).build();
        } catch (InvalidInputException | java.time.format.DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (GymNotFoundException e) {
             return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/book")
    public Response bookSlot(@QueryParam("userId") String userId,
                             @QueryParam("slotId") String slotId,
                             @QueryParam("gymId") String gymId,
                             @QueryParam("date") String date) {
        try {
             LocalDate localDate = LocalDate.parse(date);
             boolean success = customerService.bookSlot(userId, slotId, gymId, localDate);
             if (success) {
                 return Response.ok(java.util.Collections.singletonMap("message", "Booking Successful")).build();
             } else {
                 return Response.status(Response.Status.BAD_REQUEST).entity("Booking Failed").build();
             }
        } catch (BookingFailedException | SlotNotAvailableException | BookingCreationException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (InvalidInputException | java.time.format.DateTimeParseException e) {
             return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/cancel/{bookingId}")
    public Response cancelBooking(@PathParam("bookingId") String bookingId, @QueryParam("userId") String userId) {
        try {
            boolean success = customerService.cancelBooking(bookingId, userId);
            if (success) {
                 return Response.ok(java.util.Collections.singletonMap("message", "Booking Cancelled Successfully")).build();
            } else {
                 return Response.status(Response.Status.BAD_REQUEST).entity("Cancellation Failed").build();
            }
        } catch (BookingFailedException | InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/bookings/{userId}")
    public Response viewMyBookings(@PathParam("userId") String userId) {
        try {
            List<Booking> bookings = customerService.viewMyBookings(userId);
            return Response.ok(bookings).build();
        } catch (InvalidInputException e) {
             return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}

