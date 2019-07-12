package com.revolut.money.transfer.resource;

import com.revolut.money.transfer.model.Transaction;
import com.revolut.money.transfer.resource.model.TransferRequest;
import com.revolut.money.transfer.service.TransferService;
import io.swagger.annotations.Api;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

@Path("/transfer")
@Api("/transfer")
public class TransferResource {

    private TransferService transferService;

    public TransferResource(TransferService transferService) {
        this.transferService = transferService;
    }

    @POST
    public Transaction transfer(TransferRequest transferRequest) {
        return transferService.transfer(transferRequest.getFromAccountId(), transferRequest.getToAccountId(), transferRequest.getAmount());
    }

    @GET
    public List<Transaction> transactions(){
        return transferService.getAllTransactions();
    }

}
