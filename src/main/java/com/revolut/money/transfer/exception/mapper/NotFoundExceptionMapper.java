package com.revolut.money.transfer.exception.mapper;


import com.revolut.money.transfer.exception.NotFoundException;
import com.revolut.money.transfer.exception.model.ErrorResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    private static final Logger logger = LoggerFactory.getLogger(NotFoundExceptionMapper.class);

    @Override
    public Response toResponse(NotFoundException exception) {

        logger.error("Resource was not found", exception);

        return Response
                .serverError()
                .status(HttpStatus.NOT_FOUND_404)
                .entity(new ErrorResponse(exception.getMessage()))
                .build();
    }
}
