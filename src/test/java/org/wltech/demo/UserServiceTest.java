package org.wltech.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.wltech.demo.model.user.UserRequest;
import org.wltech.demo.model.user.UserResponse;
import org.wltech.demo.service.user.UserService;

public class UserServiceTest {
    @Autowired
    private UserService userService;

    interface DataTestCreateUser {
    String username = "wangjia888@gmail.com";
    String password = "p@ssword";
    }   

    @Test
    void testCreateUser() {
    UserRequest userRequest = new UserRequest();
    userRequest.setUsername(DataTestCreateUser.username);
    userRequest.setPassword(DataTestCreateUser.password);
    UserResponse userResponse = userService.createUser(userRequest);

    System.out.println(userResponse.getId());
    System.out.println(userResponse.getUsername());
    System.out.println(userResponse.getExpiredDate());

    Assertions.assertNotNull(userResponse);
    Assertions.assertNotNull(userResponse.getId());
    Assertions.assertEquals(DataTestCreateUser.username, userResponse.getUsername());
    }
}