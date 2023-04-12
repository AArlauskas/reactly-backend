package com.reactly.backend.controllers;

import com.reactly.backend.auth.TokenProvider;
import com.reactly.backend.dtos.AuthRequest;
import com.reactly.backend.dtos.AuthToken;
import com.reactly.backend.dtos.UserDto;
import com.reactly.backend.entities.User;
import com.reactly.backend.errors.BaseException;
import com.reactly.backend.errors.ErrorCode;
import com.reactly.backend.services.UserService;
import com.reactly.backend.services.WebsiteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/users")

public class UserController {

    private final UserService userService;
    private final WebsiteService websiteService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    public UserController(UserService userService, WebsiteService websiteService, AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        this.userService = userService;
        this.websiteService = websiteService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }


    //get all users
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getAllUsers() {

        return ResponseEntity.ok(userService.mapToUserDtos(userService.getAllUsers()));
    }

    //personal
    @RequestMapping(value = "personal", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ADMIN','BASIC')")
    public ResponseEntity<UserDto> getPersonalInfo(Authentication authentication) {
        User user = userService.getAuthUser((UserDetails)authentication.getPrincipal());
        if(user == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new UserDto(user));
    }

    //authenticate
    @RequestMapping(value = "authenticate", method = RequestMethod.POST)
    public ResponseEntity<AuthToken> authenticate(@RequestBody AuthRequest authRequest) throws BaseException {
        if(authRequest.email == null || authRequest.email.isEmpty())
        {
            throw new BaseException(ErrorCode.BAD_PARAMETERS, "Email is required");
        }

        if(authRequest.password == null || authRequest.password.length() < 3)
        {
            throw new BaseException(ErrorCode.BAD_PARAMETERS, "Password is required");
        }

        try
        {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.email,
                            authRequest.password
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = tokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new AuthToken(token));
        }
        catch (AuthenticationException e)
        {
            return ResponseEntity.notFound().build();
        }

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

        if(userService.getUserByUsernameAuth(dto.email) != null)
        {
            throw new BaseException(ErrorCode.BAD_PARAMETERS, "Email already exists");
        }

        User user = userService.register(dto);

        return ResponseEntity.ok().body(userService.mapToUserDto(user));
    }

}
