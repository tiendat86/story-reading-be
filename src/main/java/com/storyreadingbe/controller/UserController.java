package com.storyreadingbe.controller;

import com.storyreadingbe.common.URLConst;
import com.storyreadingbe.dto.request.UserDetailDTO;
import com.storyreadingbe.dto.request.UserLoginDTO;
import com.storyreadingbe.dto.response.BookResponseDTO;
import com.storyreadingbe.entity.User;
import com.storyreadingbe.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @PostMapping(value = URLConst.REGISTER,
            produces = { "application/json" },
            consumes = { "multipart/form-data" })
    public void register(@ModelAttribute User user) {
        userService.register(user);
    }

    @PostMapping(value = URLConst.LOGIN,
            produces = { "application/json" },
            consumes = { "multipart/form-data" })
    public User login(@ModelAttribute UserLoginDTO dto) {
        return userService.login(dto.getUsername(), dto.getPassword());
    }

    @PostMapping(value = URLConst.CREATE_USER_DETAIL)
    public void createUserDetail(@ModelAttribute UserDetailDTO dto) {
        userService.createUser(dto);
    }
}
