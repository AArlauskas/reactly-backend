package com.reactly.backend.dtos;

public class RenameWebsiteRequestDto {
    public String websiteId;
    public String newName;

    public RenameWebsiteRequestDto() {
    }

    public RenameWebsiteRequestDto(String websiteId, String newName) {
        this.websiteId = websiteId;
        this.newName = newName;
    }
}
