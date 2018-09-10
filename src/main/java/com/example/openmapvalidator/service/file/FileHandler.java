package com.example.openmapvalidator.service.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileHandler {
    String saveFile(MultipartFile file);
    File createTmpFileAndPutContent(String content) throws IOException;
}
