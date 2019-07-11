package com.revolut.money.transfer.resource;

import com.revolut.money.transfer.db.entity.User;
import com.revolut.money.transfer.db.repository.UserRepository;
import com.revolut.money.transfer.model.UserResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private UserRepository userRepository;

    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GET
    public UserResponse getUser(@QueryParam("id") Long id) {
        User user =  userRepository.find(id);
        return new UserResponse().setName(user.getName()).setSurname(user.getSurname());
    }

}
