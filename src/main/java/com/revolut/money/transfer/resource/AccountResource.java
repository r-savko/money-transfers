package com.revolut.money.transfer.resource;

import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.model.Transaction;
import com.revolut.money.transfer.service.AccountService;
import com.revolut.money.transfer.service.TransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/account")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Account API", produces = MediaType.APPLICATION_JSON)
public class AccountResource {

    private AccountService accountService;
    private TransferService transferService;

    public AccountResource(AccountService accountService, TransferService transferService) {
        this.accountService = accountService;
        this.transferService = transferService;
    }

    @GET
    @Path("/{accountId}")
    @ApiOperation(value = "Get account info")
    public Account findAccount(@PathParam("accountId") @NotNull Long accountId) {
        return accountService.findAccount(accountId);
    }

    @DELETE
    @Path("/{accountId}")
    @ApiOperation(value = "Delete account with all transactions")
    public void deleteAccount(@PathParam("accountId") @NotNull Long accountId) {
        accountService.deleteAccount(accountId);
    }

    @GET
    @Path("/{accountId}/transactions")
    @ApiOperation(value = "Get account transactions")
    public List<Transaction> findAccountTransactions(@PathParam("accountId") @NotNull Long accountId) {
        return transferService.findAccountTransactions(accountId);
    }


}
