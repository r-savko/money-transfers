package com.revolut.money.transfer.resource;

import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.service.AccountService;
import io.swagger.annotations.Api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/v1/account")
@Api("/v1/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    private AccountService accountService;

    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Path("/{accountId}")
    public Account findAccount(@PathParam("accountId") Long accountId) {
        return accountService.findAccount(accountId);
    }

    @DELETE
    @Path("/{accountId}")
    public void deleteAccount(@PathParam("accountId") Long accountId) {
        accountService.deleteAccount(accountId);
    }


}
