package com.revolut.money.transfer.resource;

import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.model.Transaction;
import com.revolut.money.transfer.model.User;
import com.revolut.money.transfer.resource.model.CreateAccountRequest;
import com.revolut.money.transfer.service.AccountService;
import com.revolut.money.transfer.service.TransferService;
import com.revolut.money.transfer.service.UserService;
import io.swagger.annotations.Api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/user")
@Api("/v1/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private UserService userService;
    private AccountService accountService;
    private TransferService transferService;

    public UserResource(UserService userService, AccountService accountService, TransferService transferService) {
        this.userService = userService;
        this.accountService = accountService;
        this.transferService = transferService;
    }

    @GET
    @Path("/{userId}")
    public User findUser(@PathParam("userId") Long userId) {
        return userService.findUser(userId);
    }

    @GET
    @Path("/{userId}/accounts")
    public List<Account> findUserAccounts(@PathParam("userId") Long userId) {
        return userService.findUserAccounts(userId);
    }

    @POST
    @Path("/{userId}/account")
    public Account createAccount(@PathParam("userId") Long userId, CreateAccountRequest createAccountRequest) {
        return accountService.createAccount(
                userId, createAccountRequest.getCurrencyCode(), createAccountRequest.getAccountNumber()
        );
    }

    @GET
    @Path("/{userId}/transactions/incoming")
    public List<Transaction> findUserIncomingTransactions(@PathParam("userId") Long userId) {
        return transferService.findUserIncomingTransactions(userId);
    }

    @GET
    @Path("/{userId}/transactions/outgoing")
    public List<Transaction> findUserOutgoingTransactions(@PathParam("userId") Long userId) {
        return transferService.findUserOutgoingTransactions(userId);
    }
}
