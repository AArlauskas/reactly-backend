package com.reactly.backend.services;

import com.reactly.backend.entities.Website;
import com.reactly.backend.repositories.WebsiteRepository;
import org.springframework.stereotype.Service;

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
}
