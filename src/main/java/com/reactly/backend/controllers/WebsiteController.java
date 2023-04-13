package com.reactly.backend.controllers;

import com.reactly.backend.dtos.RenameWebsiteRequestDto;
import com.reactly.backend.dtos.WebsiteDto;
import com.reactly.backend.entities.User;
import com.reactly.backend.entities.Website;
import com.reactly.backend.errors.BaseException;
import com.reactly.backend.errors.ErrorCode;
import com.reactly.backend.services.UserService;
import com.reactly.backend.services.WebsiteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/websites")
public class WebsiteController {

    private final WebsiteService websiteService;
    private final UserService userService;

    public WebsiteController(WebsiteService websiteService, UserService userService) {
        this.websiteService = websiteService;
        this.userService = userService;
    }

    //create website
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<WebsiteDto> createWebsite(@RequestBody String websiteName, Authentication authentication) throws BaseException {

        //validate
        if(websiteName == null || websiteName.length() < 3)
        {
            throw new BaseException(ErrorCode.BAD_PARAMETERS, "Website name is required");
        }

        User user = userService.getAuthUser((UserDetails)authentication.getPrincipal());
        if(user == null) return ResponseEntity.notFound().build();

        Website website = websiteService.createWebsite(websiteName, user);

        return ResponseEntity.ok().body(new WebsiteDto(website));
    }

    //rename website
    @RequestMapping(value = "rename", method = RequestMethod.PUT)
    public ResponseEntity<Void> renameWebsite(@RequestBody RenameWebsiteRequestDto dto) throws BaseException {

        //validate
        if(dto.websiteId == null || dto.websiteId.isEmpty())
        {
            throw new BaseException(ErrorCode.BAD_PARAMETERS, "Website id is required");
        }

        if(dto.newName == null || dto.newName.length() < 3)
        {
            throw new BaseException(ErrorCode.BAD_PARAMETERS, "New name is required");
        }

        if(websiteService.isWebsiteExist(dto.websiteId))
        {
            websiteService.renameWebsite(dto.websiteId, dto.newName);
        }

        return ResponseEntity.ok().build();
    }

    //delete website
    @RequestMapping(value = "{websiteId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteWebsite(@PathVariable String websiteId) throws BaseException {

        //validate
        if(websiteId == null || websiteId.isEmpty())
        {
            throw new BaseException(ErrorCode.BAD_PARAMETERS, "Website id is required");
        }

        if(websiteService.isWebsiteExist(websiteId))
        {
            websiteService.deleteWebsite(websiteId);
        }

        return ResponseEntity.ok().build();
    }
}
