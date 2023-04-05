package com.reactly.backend.dtos;

import com.reactly.backend.entitites.PageEntity;

import java.util.List;

public class DownloadRequestDto {
    public List<PageEntity> pages;
    public String root;
    public String theme;
}
