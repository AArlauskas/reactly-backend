package com.reactly.backend.services;

import com.reactly.backend.dtos.DownloadRequestDto;
import com.reactly.backend.entitites.PageEntity;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class DownloadService {

    public final String DIRECTORY_NAME = "reactly-projects";

    public Resource getZippedProject(DownloadRequestDto dto) throws IOException {
        String rootPath = copyTemplateProject();
        createAppFile(rootPath, dto.root);
        createThemeFile(rootPath, dto.theme);
        dto.pages.forEach(page -> {
            try {
                createPageFile(rootPath, page);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        File fileToZip = new File(rootPath);
        FileOutputStream fos = new FileOutputStream(rootPath + "\\reactly-project.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        fos.close();

        Path filePath = Paths.get(rootPath + "\\reactly-project.zip");
        return new UrlResource(filePath.toUri());
    }

    private String copyTemplateProject() throws IOException {
        String source = getClass().getClassLoader().getResource("project-template").getPath();
        String destination = DIRECTORY_NAME;
        File destinationDirectory = new File(destination);
        FileUtils.cleanDirectory(destinationDirectory);
        FileUtils.copyDirectory(new File(source), new File(destination));
        return destinationDirectory.getAbsolutePath();
    }

    private void createAppFile(String path, String AppContent) throws IOException {
        String filePath = path + "\\src\\App.js";
        Path appPath = Path.of(filePath);
        Files.createFile(appPath);
        Files.writeString(appPath, AppContent);
    }

    private void createThemeFile(String path, String themeContent) throws IOException {
        String filePath = path + "\\src\\theme.js";
        Path appPath = Path.of(filePath);
        Files.createFile(appPath);
        Files.writeString(appPath, themeContent);
    }

    private void createPageFile(String path, PageEntity page) throws IOException {
        String filePath = path + "\\src\\pages\\" + page.title + ".jsx";
        Path pagePath = Path.of(filePath);
        Files.createFile(pagePath);
        Files.writeString(pagePath, page.contents);
    }

    private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                if(childFile.getName().equals("reactly-project.zip")) continue;;
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
}
