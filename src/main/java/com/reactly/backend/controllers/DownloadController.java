package com.reactly.backend.controllers;

import com.reactly.backend.dtos.DownloadRequestDto;
import com.reactly.backend.services.DownloadService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("api/downloads")
public class DownloadController {
    private DownloadService downloadService;

    public DownloadController(DownloadService downloadService)
    {
        this.downloadService = downloadService;
    }

    @RequestMapping(value = "healthcheck", method = RequestMethod.GET)
    public ResponseEntity<String> healtcheck()
    {
        return ResponseEntity.ok("Service is UP");
    }

    @RequestMapping(value = "zip", method = RequestMethod.POST)
    public ResponseEntity<?> zip(@RequestBody DownloadRequestDto dto) {
        try
        {
            Resource resource = downloadService.getZippedProject(dto);
            String contentType = "application/octet-stream";
            String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                    .body(resource);

        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
