package com.revolut.money.transfer.resource;

import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.service.AccountService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    private AccountService accountService;

    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Path("/{accountId}")
    public Account getUser(@PathParam("accountId") Long accountId) {
        return accountService.findAccount(accountId);
    }


}
