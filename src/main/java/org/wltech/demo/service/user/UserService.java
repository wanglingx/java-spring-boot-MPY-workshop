package org.wltech.demo.service.user;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.wltech.demo.constant.StatusCode;
import org.wltech.demo.entity.jpa.User;
import org.wltech.demo.exception.BaseException;
import org.wltech.demo.model.user.UserAuthenRequest;
import org.wltech.demo.model.user.UserAuthenResponse;
import org.wltech.demo.model.user.UserRegisterRequest;
import org.wltech.demo.model.user.UserRegisterResponse;
import org.wltech.demo.model.user.UserRequest;
import org.wltech.demo.model.user.UserResponse;
import org.wltech.demo.repository.jpa.UserRepository;
import org.wltech.demo.security.util.JwtUtil;

@Service
public class UserService {

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setIsEnabled("Y");
        user.setIsLocked("N");
        user.setExpirDate(new Date());
        user.setCreateBy(1);
        user.setCreateAt(new Date());
        user.setDeleteFlag("N");
        userRepository.save(user);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setExpiredDate(user.getExpirDate());

        return userResponse;
    }

    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) {
        User user = new User();
        user.setUsername(userRegisterRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        user.setIsEnabled("Y");
        user.setIsLocked("N");
        user.setExpirDate(new Date());
        user.setCreateBy(1);
        user.setCreateAt(new Date());
        user.setDeleteFlag("N");
        userRepository.save(user);

        UserRegisterResponse userResponse = new UserRegisterResponse();
        userResponse.setUserId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setExpiredDate(user.getExpirDate());

        return userResponse;
    }

    public UserAuthenResponse authenUser(UserAuthenRequest userAuthenRequest) {
        User user = userRepository.findByUsername(userAuthenRequest.getUsername());
        if (user == null) {
            throw new BaseException(HttpStatus.UNAUTHORIZED, StatusCode.ERR_CODE_401, StatusCode.ERR_DESC_401);
        }
        if (!(passwordEncoder.matches(userAuthenRequest.getPassword(), user.getPassword()))) {
            throw new BaseException(HttpStatus.UNAUTHORIZED, StatusCode.ERR_CODE_401, StatusCode.ERR_DESC_401);
        }

        String token = jwtUtil.generateToken(user.getUsername());
        UserAuthenResponse userAuthenResponse = new UserAuthenResponse();
        userAuthenResponse.setUserId(user.getId());
        userAuthenResponse.setUsername(user.getUsername());
        userAuthenResponse.setToken(token);

        return userAuthenResponse;
    }

    public UserAuthenResponse refreshToken() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(username);

        String token = jwtUtil.generateToken(user.getUsername());
        UserAuthenResponse userAuthenResponse = new UserAuthenResponse();
        userAuthenResponse.setUserId(user.getId());
        userAuthenResponse.setUsername(user.getUsername());
        userAuthenResponse.setToken(token);

        return userAuthenResponse;
    }
}


