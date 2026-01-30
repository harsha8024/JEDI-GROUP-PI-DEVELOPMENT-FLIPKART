package com.flipfit.rest;

import com.flipfit.bean.Slot;
import com.flipfit.business.SlotServiceInterface;
import com.flipfit.business.SlotServiceImpl;
import com.flipfit.exception.InvalidInputException;
import com.flipfit.exception.RegistrationFailedException;
import com.flipfit.exception.SlotNotFoundException;
import com.flipfit.exception.SlotOperationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalTime;
import java.util.List;

@Path("/slot")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SlotController {

    private final SlotServiceInterface slotService;

    public SlotController() {
        this.slotService = new SlotServiceImpl();
    }

    @POST
    @Path("/create")
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
    @Path("/gym/{gymId}")
    public Response getSlotsForGym(@PathParam("gymId") String gymId) {
        try {
            List<Slot> slots = slotService.getSlotsForGym(gymId);
            return Response.ok(slots).build();
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{slotId}")
    public Response getSlotById(@PathParam("slotId") String slotId) {
        try {
            Slot slot = slotService.getSlotById(slotId);
            return Response.ok(slot).build();
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (SlotNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{slotId}")
    public Response updateSlot(@PathParam("slotId") String slotId,
                              @QueryParam("capacity") int capacity) {
        try {
            slotService.updateSlot(slotId, capacity);
            return Response.ok(java.util.Collections.singletonMap("message", "Slot Updated Successfully")).build();
        } catch (InvalidInputException | SlotOperationException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (SlotNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{slotId}")
    public Response deleteSlot(@PathParam("slotId") String slotId) {
        try {
            slotService.deleteSlot(slotId);
            return Response.ok(java.util.Collections.singletonMap("message", "Slot Deleted Successfully")).build();
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (SlotNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
