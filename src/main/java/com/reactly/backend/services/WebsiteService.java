package com.reactly.backend.services;

import com.reactly.backend.dtos.UserDto;
import com.reactly.backend.dtos.WebsiteDto;
import com.reactly.backend.entities.User;
import com.reactly.backend.entities.Website;
import com.reactly.backend.repositories.WebsiteRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebsiteService {

    private final WebsiteRepository websiteRepository;

    public WebsiteService(WebsiteRepository websiteRepository) {
        this.websiteRepository = websiteRepository;
    }

    public boolean isWebsiteExist(String websiteId) {
        return websiteRepository.existsById(websiteId);
    }

    //get all websites
    public Iterable<Website> getAllWebsites() {
        return websiteRepository.findAll();
    }

    //get website by id
    public Website getWebsiteById(String websiteId) {
        return websiteRepository.findById(websiteId).orElse(null);
    }

    //add website
    public Website addWebsite(Website website) {
        return websiteRepository.save(website);
    }

    //create website
    public Website createWebsite(String websiteName, User user) {
        Website website = new Website();
        website.setName(websiteName);
        website.setUser(user);
        return websiteRepository.save(website);
    }

    //update website
    public Website updateWebsite(Website website) {
        return websiteRepository.save(website);
    }

    //delete website
    public void deleteWebsite(String websiteId) {
        websiteRepository.deleteById(websiteId);
    }

    //get all websites by user id
    public Iterable<Website> getAllWebsitesByUserId(String userId) {
        return websiteRepository.findAllByUserId(userId);
    }

    //rename website
    public void renameWebsite(String websiteId, String newName) {
        Website website = getWebsiteById(websiteId);
        website.setName(newName);
        updateWebsite(website);
    }

    //map website to dto
    public WebsiteDto mapToDto(Website website) {
        WebsiteDto websiteDto = new WebsiteDto();
        websiteDto.id = website.getId();
        websiteDto.name = website.getName();
        websiteDto.content = website.getContent();
        websiteDto.user = new UserDto(website.getUser());
        return websiteDto;
    }

    //map websites to dtos
    public Iterable<WebsiteDto> mapToDtos(Iterable<Website> websites) {
        List<WebsiteDto> websiteDtos = new ArrayList<>();
        for (Website website : websites) {
            websiteDtos.add(mapToDto(website));
        }
        return websiteDtos;
    }

    //get user's websites
    @Transactional
    public Iterable<WebsiteDto> getUserWebsites(String userId) {
        return mapToDtos(getAllWebsitesByUserId(userId));
    }

    //update website's content
    public void updateWebsiteContent(String websiteId, String content) {
        Website website = getWebsiteById(websiteId);
        website.setContent(content);
        updateWebsite(website);
    }
}
