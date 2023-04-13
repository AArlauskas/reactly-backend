package com.reactly.backend.dtos;

import com.reactly.backend.entities.Website;

public class WebsiteDto {
    public String id;
    public String name;
    public String content;
    public UserDto user;

    public WebsiteDto() {
    }

    public WebsiteDto(String id, String name, String content, UserDto user) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.user = user;
    }

    public WebsiteDto(Website website)
    {
        id = website.getId();
        name = website.getName();
    }
}
