package com.reactly.backend.controllers;

import com.reactly.backend.dtos.RenameWebsiteRequestDto;
import com.reactly.backend.dtos.UpdateWebsiteContentRequest;
import com.reactly.backend.dtos.WebsiteDto;
import com.reactly.backend.entities.User;
import com.reactly.backend.entities.Website;
import com.reactly.backend.errors.BaseException;
import com.reactly.backend.errors.ErrorCode;
import com.reactly.backend.services.UserService;
import com.reactly.backend.services.WebsiteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    //get user's websites
    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ADMIN','BASIC')")
    public ResponseEntity<Iterable<WebsiteDto>> getUserWebsites(Authentication authentication) {

        User user = userService.getAuthUser((UserDetails)authentication.getPrincipal());
        if(user == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(websiteService.getUserWebsites(user.getId()));
    }

    //get website's content
    @RequestMapping(value = "{websiteId}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ADMIN','BASIC')")
    public ResponseEntity<String> getWebsiteContent(@PathVariable String websiteId) {

            if(websiteService.isWebsiteExist(websiteId))
            {
                String content = websiteService.getWebsiteById(websiteId).getContent();
                return ResponseEntity.ok(content);
            }

            return ResponseEntity.notFound().build();
    }

    //update website's content
    @RequestMapping(value = "{websiteId}", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ADMIN','BASIC')")
    public ResponseEntity<Void> updateWebsiteContent(@PathVariable String websiteId, @RequestBody UpdateWebsiteContentRequest content) {

            if(websiteService.isWebsiteExist(websiteId))
            {
                websiteService.updateWebsiteContent(websiteId, content.content);
                return ResponseEntity.ok().build();
            }

            return ResponseEntity.notFound().build();
    }

    //create website
    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ADMIN','BASIC')")
    public ResponseEntity<WebsiteDto> createWebsite(@RequestBody WebsiteDto websiteDto, Authentication authentication) throws BaseException {

        //validate
        if(websiteDto.name == null || websiteDto.name.length() < 3)
        {
            throw new BaseException(ErrorCode.BAD_PARAMETERS, "Website name is required");
        }

        User user = userService.getAuthUser((UserDetails)authentication.getPrincipal());
        if(user == null) return ResponseEntity.notFound().build();

        Website website = websiteService.createWebsite(websiteDto.name, user);

        return ResponseEntity.ok().body(new WebsiteDto(website));
    }

    //rename website
    @RequestMapping(value = "rename", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ADMIN','BASIC')")
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
    @PreAuthorize("hasAnyRole('ADMIN','BASIC')")
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
