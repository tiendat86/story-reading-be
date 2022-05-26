package com.storyreadingbe.controller;

import com.storyreadingbe.common.URLConst;
import com.storyreadingbe.dto.request.UserDetailDTO;
import com.storyreadingbe.dto.request.UserLoginDTO;
import com.storyreadingbe.dto.response.UserResponseDTO;
import com.storyreadingbe.entity.User;
import com.storyreadingbe.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @PostMapping(value = URLConst.REGISTER)
    public void register(@ModelAttribute User user) {
        userService.register(user);
    }

    @PostMapping(value = URLConst.LOGIN)
    public UserResponseDTO login(@ModelAttribute UserLoginDTO dto) {
        return userService.login(dto.getUsername(), dto.getPassword());
    }

    @PostMapping(value = URLConst.CREATE_USER_DETAIL)
    public void createUserDetail(@ModelAttribute UserDetailDTO dto) {
        userService.createUser(dto);
    }
    
    @PostMapping(value = URLConst.Author.CHANGE_AVATAR)
    public UserResponseDTO changeAvatar(@RequestParam("username") String username,
                                     @RequestPart("image") MultipartFile image) {
        return userService.changeAvatar(username, image);
    }
}
