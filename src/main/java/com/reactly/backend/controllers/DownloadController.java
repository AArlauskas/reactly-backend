package com.reactly.backend.controllers;

import com.reactly.backend.dtos.DownloadRequestDto;
import com.reactly.backend.services.DownloadService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public ResponseEntity<String> healthcheck()
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
        catch (Exception e) {
            String exceptionMessage = e.getMessage();
            String stackTrace =e
                    + Arrays.asList(e.getStackTrace())
                    .stream()
                    .map(Objects::toString)
                    .collect(Collectors.joining("\n"));
            System.out.println("Exception " + e);
            System.out.println("Exception message: " + exceptionMessage);
            System.out.println("Stack trace: " + stackTrace);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
