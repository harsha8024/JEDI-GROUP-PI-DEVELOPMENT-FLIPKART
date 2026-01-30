package com.flipfit.rest;

import com.flipfit.bean.Payment;
import com.flipfit.business.PaymentInterface;
import com.flipfit.business.PaymentServiceImpl;
import com.flipfit.exception.InvalidInputException;
import com.flipfit.exception.PaymentFailedException;
import com.flipfit.exception.PaymentNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/payment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentController {

    private final PaymentInterface paymentService;

    public PaymentController() {
        this.paymentService = new PaymentServiceImpl();
    }

    @POST
    @Path("/process")
    public Response processPayment(@QueryParam("bookingId") String bookingId,
                                   @QueryParam("amount") double amount,
                                   @QueryParam("paymentMethod") String paymentMethod) {
        try {
            boolean success = paymentService.processPayment(bookingId, amount, paymentMethod);
            if (success) {
                return Response.ok(java.util.Collections.singletonMap("message", "Payment Processed Successfully")).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Payment Processing Failed").build();
            }
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (PaymentFailedException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{paymentId}")
    public Response getPaymentDetails(@PathParam("paymentId") String paymentId) {
        try {
            Payment payment = paymentService.getPaymentDetails(paymentId);
            return Response.ok(payment).build();
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (PaymentNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/refund/{paymentId}")
    public Response refundPayment(@PathParam("paymentId") String paymentId) {
        try {
            boolean success = paymentService.refundPayment(paymentId);
            if (success) {
                return Response.ok(java.util.Collections.singletonMap("message", "Refund Processed Successfully")).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Refund Processing Failed").build();
            }
        } catch (InvalidInputException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (PaymentNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (PaymentFailedException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
