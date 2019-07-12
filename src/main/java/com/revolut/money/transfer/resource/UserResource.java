package com.revolut.money.transfer.resource;

import com.revolut.money.transfer.model.User;
import com.revolut.money.transfer.service.UserService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Path("/{userId}")
    public User getUser(@PathParam("userId") Long userId) {
        return userService.findUser(userId);
    }

}
