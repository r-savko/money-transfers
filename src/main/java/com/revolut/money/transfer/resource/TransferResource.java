package com.revolut.money.transfer.resource;

import com.revolut.money.transfer.model.Transaction;
import com.revolut.money.transfer.resource.model.TransferRequest;
import com.revolut.money.transfer.service.TransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/v1/transfer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Fund transfer API", produces = MediaType.APPLICATION_JSON)
public class TransferResource {

    private static final Logger logger = LoggerFactory.getLogger(TransferResource.class);

    private TransferService transferService;

    public TransferResource(TransferService transferService) {
        this.transferService = transferService;
    }

    @POST
    @ApiOperation(value = "Fund transfer between two accounts")
    public Transaction transfer(@Valid TransferRequest transferRequest) {

        Long from = transferRequest.getFromAccount();
        Long to = transferRequest.getToAccount();

        logger.info("Transferring funds from the account " + from + " to the account " + to);

        return transferService.transfer(from, to, transferRequest.getAmount());
    }

}
