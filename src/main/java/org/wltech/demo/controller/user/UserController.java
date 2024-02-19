package org.wltech.demo.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wltech.demo.model.common.SuccessResponse;
import org.wltech.demo.model.user.UserAuthenRequest;
import org.wltech.demo.model.user.UserAuthenResponse;
import org.wltech.demo.model.user.UserRegisterRequest;
import org.wltech.demo.model.user.UserRegisterResponse;
import org.wltech.demo.model.user.UserRequest;
import org.wltech.demo.model.user.UserResponse;
import org.wltech.demo.service.user.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    // http://localhost:8080/api/v1/user/test/10
    @GetMapping("/test/{id}")
    public String test(@PathVariable String id) {
        return id;
    }

    // http://localhost:8080/api/v1/user/create
    @PostMapping("/create")
    public ResponseEntity<SuccessResponse<UserResponse>> create(@RequestBody UserRequest userRequest) {
        SuccessResponse<UserResponse> successResponse = new SuccessResponse<UserResponse>();
        UserResponse userResponse = userService.createUser(userRequest);
        successResponse.setData(userResponse);
        return ResponseEntity.ok(successResponse);
    }

    // http://localhost:8080/api/v1/user/register
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<UserRegisterResponse>> register(
            @RequestBody UserRegisterRequest userRegisterRequest) {
        SuccessResponse<UserRegisterResponse> successResponse = new SuccessResponse<UserRegisterResponse>();
        UserRegisterResponse userResponse = userService.registerUser(userRegisterRequest);
        successResponse.setData(userResponse);
        return ResponseEntity.ok(successResponse);
    }

    // http://localhost:8080/api/v1/user/authen
    @PostMapping("/authen")
    public ResponseEntity<SuccessResponse<UserAuthenResponse>> register(
            @RequestBody UserAuthenRequest userAuthenRequest) {
        return ResponseEntity.ok(new SuccessResponse<UserAuthenResponse>(userService.authenUser(userAuthenRequest)));
    }

    // http://localhost:8080/api/v1/user/refresh-token
    @GetMapping("/refresh-token")
    public ResponseEntity<SuccessResponse<UserAuthenResponse>> refreshToken() {
        return ResponseEntity.ok(new SuccessResponse<UserAuthenResponse>(userService.refreshToken()));
    }

}

