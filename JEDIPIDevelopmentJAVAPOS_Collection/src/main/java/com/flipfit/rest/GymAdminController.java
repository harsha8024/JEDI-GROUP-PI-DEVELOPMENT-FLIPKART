package com.flipfit.rest;

import com.flipfit.bean.Gym;
import com.flipfit.bean.Slot;
import com.flipfit.bean.User;
import com.flipfit.bean.Booking;
import com.flipfit.business.GymAdminInterface;
import com.flipfit.business.GymAdminServiceImpl;
import com.flipfit.business.GymUserInterface;
import com.flipfit.business.GymUserServiceImpl;
import com.flipfit.exception.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GymAdminController {

    private final GymAdminInterface adminService;
    private final GymUserInterface userService;

    public GymAdminController() {
        this.adminService = new GymAdminServiceImpl();
        this.userService = new GymUserServiceImpl();
    }

    @POST
    @Path("/login")
    public Response loginAdmin(@QueryParam("email") String email, 
                               @QueryParam("password") String password) {
        try {
            boolean success = userService.login(email, password);
            if (success) {
                com.flipfit.bean.GymAdmin admin = (com.flipfit.bean.GymAdmin) GymUserServiceImpl.getUserMap().get(email);
                if (admin != null) admin.setPassword(null);
                return Response.ok(admin).build();
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
    @Path("/gyms/pending")
    public Response viewPendingApprovals() {
        List<Gym> pendingGyms = adminService.viewPendingApprovals();
        return Response.ok(pendingGyms).build();
    }

    @PUT
    @Path("/gyms/approve/{gymId}")
    public Response approveGym(@PathParam("gymId") String gymId) {
        try {
            adminService.approveGym(gymId);
            return Response.ok(java.util.Collections.singletonMap("message", "Gym Approved Successfully")).build();
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
            return Response.ok(java.util.Collections.singletonMap("message", "Gym Rejected Successfully")).build();
         } catch (ApprovalFailedException e) {
             return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/users")
    public Response viewAllUsers() {
        List<User> users = adminService.viewAllUsers();
        return Response.ok(users).build();
    }

    @GET
    @Path("/gyms")
    public Response viewAllGyms() {
        List<Gym> gyms = adminService.viewAllGyms();
        return Response.ok(gyms).build();
    }

    @GET
    @Path("/bookings")
    public Response viewAllBookings() {
        java.util.List<Booking> bookings = adminService.viewAllBookings();
        return Response.ok(bookings).build();
    }

    @GET
    @Path("/gyms/approved")
    public Response viewApprovedGyms() {
        List<Gym> approvedGyms = adminService.viewAllGyms().stream()
            .filter(Gym::isApproved)
            .collect(java.util.stream.Collectors.toList());
        return Response.ok(approvedGyms).build();
    }

    @GET
    @Path("/gyms/location/{location}")
    public Response viewGymsByLocation(@PathParam("location") String location) {
        List<Gym> gyms = adminService.viewAllGyms().stream()
            .filter(gym -> gym.getLocation() != null && gym.getLocation().equalsIgnoreCase(location))
            .collect(java.util.stream.Collectors.toList());
        return Response.ok(gyms).build();
    }

    @GET
    @Path("/owners/approved")
    public Response viewApprovedGymOwners() {
        List<User> allUsers = adminService.viewAllUsers();
        List<com.flipfit.bean.GymOwner> approvedOwners = allUsers.stream()
            .filter(user -> user instanceof com.flipfit.bean.GymOwner && user.isActive())
            .map(user -> (com.flipfit.bean.GymOwner) user)
            .collect(java.util.stream.Collectors.toList());
        return Response.ok(approvedOwners).build();
    }

    @GET
    @Path("/owners/pending")
    public Response viewPendingGymOwners() {
        List<User> allUsers = adminService.viewAllUsers();
        List<com.flipfit.bean.GymOwner> pendingOwners = allUsers.stream()
            .filter(user -> user instanceof com.flipfit.bean.GymOwner && !user.isActive())
            .map(user -> (com.flipfit.bean.GymOwner) user)
            .collect(java.util.stream.Collectors.toList());
        return Response.ok(pendingOwners).build();
    }

    @PUT
    @Path("/owners/approve/{ownerId}")
    public Response approveGymOwner(@PathParam("ownerId") String ownerId) {
        try {
            adminService.approveGymOwner(ownerId);
            return Response.ok(java.util.Collections.singletonMap("message", "Gym Owner Approved Successfully")).build();
        } catch (ApprovalFailedException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/owners/reject/{ownerId}")
    public Response rejectGymOwner(@PathParam("ownerId") String ownerId) {
        try {
            adminService.rejectGymOwner(ownerId);
            return Response.ok(java.util.Collections.singletonMap("message", "Gym Owner Rejected Successfully")).build();
        } catch (ApprovalFailedException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/slots/pending")
    public Response viewPendingSlots() {
        List<Slot> pendingSlots = adminService.viewPendingSlots();
        return Response.ok(pendingSlots).build();
    }

    @PUT
    @Path("/slots/approve/{slotId}")
    public Response approveSlot(@PathParam("slotId") String slotId) {
        try {
            adminService.approveSlot(slotId);
            return Response.ok(java.util.Collections.singletonMap("message", "Slot Approved Successfully")).build();
        } catch (ApprovalFailedException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/slots/reject/{slotId}")
    public Response rejectSlot(@PathParam("slotId") String slotId) {
        try {
            adminService.rejectSlot(slotId);
            return Response.ok(java.util.Collections.singletonMap("message", "Slot Rejected Successfully")).build();
        } catch (ApprovalFailedException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/reports")
    public Response generateReports(@QueryParam("reportType") int reportType) {
        try {
            adminService.generateReports(reportType);
            return Response.ok(java.util.Collections.singletonMap("message", "Report generated successfully")).build();
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/reports/payment")
    public Response viewPaymentReports() {
        adminService.viewPaymentReports();
        return Response.ok(java.util.Collections.singletonMap("message", "Payment reports retrieved successfully")).build();
    }

    @GET
    @Path("/reports/revenue")
    public Response viewRevenueByDateRange(@QueryParam("startDate") String startDate,
                                          @QueryParam("endDate") String endDate) {
        try {
            adminService.viewRevenueByDateRange(startDate, endDate);
            return Response.ok(java.util.Collections.singletonMap("message", "Revenue report generated successfully")).build();
        } catch (InvalidDateRangeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}

