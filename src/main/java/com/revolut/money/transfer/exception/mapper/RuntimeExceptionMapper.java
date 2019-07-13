package com.revolut.money.transfer.exception.mapper;

import com.revolut.money.transfer.exception.model.ErrorResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    private static final Logger logger = LoggerFactory.getLogger(RuntimeExceptionMapper.class);

    @Override
    public Response toResponse(RuntimeException exception) {

        logger.error("Internal server error", exception);

        return Response
                .serverError()
                .status(HttpStatus.INTERNAL_SERVER_ERROR_500)
                .entity(new ErrorResponse(exception.getMessage()))
                .build();
    }
}
