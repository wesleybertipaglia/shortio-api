package com.wesleybertipaglia.exceptions;

import java.util.Map;

import io.quarkus.security.UnauthorizedException;
import jakarta.validation.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.*;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {
        @Override
        public Response toResponse(Throwable exception) {
                if (exception instanceof ConstraintViolationException es) {
                        var errors = es.getConstraintViolations().stream()
                                        .map(e -> Map.of("field", e.getPropertyPath().toString(), "message",
                                                        e.getMessage()))
                                        .toList();

                        return Response.status(Response.Status.BAD_REQUEST)
                                        .entity(Map.of("errors", errors))
                                        .build();
                }

                if (exception instanceof IllegalArgumentException e) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                        .entity(Map.of("error", e.getMessage()))
                                        .build();
                }

                if (exception instanceof BadRequestException e) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                        .entity(Map.of("error", e.getMessage()))
                                        .build();
                }

                if (exception instanceof UnauthorizedException e) {
                        return Response.status(Response.Status.UNAUTHORIZED)
                                        .entity(Map.of("error", e.getMessage()))
                                        .build();
                }

                if (exception instanceof ForbiddenException e) {
                        return Response.status(Response.Status.FORBIDDEN)
                                        .entity(Map.of("error", e.getMessage()))
                                        .build();
                }

                if (exception instanceof NotFoundException e) {
                        return Response.status(Response.Status.NOT_FOUND)
                                        .entity(Map.of("error", e.getMessage()))
                                        .build();
                }

                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity(Map.of("error", "Unexpected server error"))
                                .build();
        }
}
