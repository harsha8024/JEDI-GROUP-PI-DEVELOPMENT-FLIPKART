package com.flipfit.rest;

import com.flipfit.bean.Notification;
import com.flipfit.business.NotificationInterface;
import com.flipfit.business.NotificationServiceImpl;
import com.flipfit.exception.InvalidInputException;
import com.flipfit.exception.NotificationFailedException;
import com.flipfit.exception.NotificationNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/notification")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationController {

    private final NotificationInterface notificationService;

    public NotificationController() {
        this.notificationService = new NotificationServiceImpl();
    }

    @POST
    @Path("/send")
    public Response sendNotification(@QueryParam("userId") String userId,
                                    @QueryParam("message") String message,
                                    @QueryParam("title") String title) {
        try {
            notificationService.sendNotification(userId, message, title);
            return Response.ok("Notification Sent Successfully").build();
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (NotificationFailedException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/unread/{userId}")
    public Response getUnreadNotifications(@PathParam("userId") String userId) {
        try {
            List<Notification> notifications = notificationService.getUnreadNotifications(userId);
            return Response.ok(notifications).build();
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/mark-read/{notificationId}")
    public Response markAsRead(@PathParam("notificationId") String notificationId) {
        try {
            notificationService.markAsRead(notificationId);
            return Response.ok("Notification Marked as Read").build();
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (NotificationNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
