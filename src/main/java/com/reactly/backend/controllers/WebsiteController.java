package com.reactly.backend.controllers;

import com.reactly.backend.dtos.RenameWebsiteRequestDto;
import com.reactly.backend.errors.BaseException;
import com.reactly.backend.errors.ErrorCode;
import com.reactly.backend.services.UserService;
import com.reactly.backend.services.WebsiteService;
import org.springframework.http.ResponseEntity;
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
}
