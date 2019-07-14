package com.revolut.money.transfer.resource;

import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.model.Transaction;
import com.revolut.money.transfer.model.User;
import com.revolut.money.transfer.resource.model.CreateAccountRequest;
import com.revolut.money.transfer.service.AccountService;
import com.revolut.money.transfer.service.TransferService;
import com.revolut.money.transfer.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "User API", produces = MediaType.APPLICATION_JSON)
public class UserResource {

    private UserService userService;
    private AccountService accountService;

    public UserResource(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @GET
    @Path("/{userId}")
    @ApiOperation(value = "Get user info")
    public User findUser(@PathParam("userId") Long userId) {
        return userService.findUser(userId);
    }

    @GET
    @Path("/{userId}/accounts")
    @ApiOperation(value = "Get accounts for user")
    public List<Account> findUserAccounts(@PathParam("userId") Long userId) {
        return userService.findUserAccounts(userId);
    }

    @POST
    @Path("/{userId}/account")
    @ApiOperation(value = "Create new account for user")
    public Account createAccount(@PathParam("userId") Long userId, CreateAccountRequest createAccountRequest) {
        return accountService.createAccount(
                userId, createAccountRequest.getCurrencyCode(), createAccountRequest.getAccountNumber()
        );
    }
}
