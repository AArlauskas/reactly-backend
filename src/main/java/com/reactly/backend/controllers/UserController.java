package com.reactly.backend.controllers;

import com.reactly.backend.dtos.UserDto;
import com.reactly.backend.entities.User;
import com.reactly.backend.errors.BaseException;
import com.reactly.backend.errors.ErrorCode;
import com.reactly.backend.services.UserService;
import com.reactly.backend.services.WebsiteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //register
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseEntity<UserDto> register(@RequestBody UserDto dto) throws BaseException {

        //validate
        if(dto.email == null || dto.email.isEmpty())
        {
            throw new BaseException(ErrorCode.BAD_PARAMETERS, "Email is required");
        }

        if(dto.password == null || dto.password.length() < 3)
        {
            throw new BaseException(ErrorCode.BAD_PARAMETERS, "Password is required");
        }

        if(userService.getUserByEmail(dto.email) == null)
        {
            throw new BaseException(ErrorCode.BAD_PARAMETERS, "Email already exists");
        }

        User user = userService.register(dto);

        return ResponseEntity.ok().body(userService.mapToUserDto(user));
    }

}
