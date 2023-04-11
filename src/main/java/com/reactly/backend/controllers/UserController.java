package com.reactly.backend.controllers;

import com.reactly.backend.dtos.UserDto;
import com.reactly.backend.entities.User;
import com.reactly.backend.services.UserService;
import com.reactly.backend.services.WebsiteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/users")

public class UserController {

    private final UserService userService;
    private final WebsiteService websiteService;

    public UserController(UserService userService, WebsiteService websiteService) {
        this.userService = userService;
        this.websiteService = websiteService;
    }


    //get all users
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getAllUsers() {

        return ResponseEntity.ok(userService.mapToUserDtos(userService.getAllUsers()));
    }
}
