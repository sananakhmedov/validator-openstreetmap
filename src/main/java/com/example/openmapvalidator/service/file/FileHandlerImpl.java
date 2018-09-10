package com.example.openmapvalidator.service.file;

import com.example.openmapvalidator.helper.Const;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * this class helps to save file into project classpath
 * @author Sanan.Ahmadzada
 */
@Service
public class FileHandlerImpl implements FileHandler {

    public String saveFile(MultipartFile file) {
        String fileName = file.getOriginalFilename().trim().replaceAll("\\s","");
        try {
            File localFile = new File(new ClassPathResource(Const.MAP_FOLDER_ROOT).getFile(), fileName);
            FileUtils.writeByteArrayToFile(localFile, file.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public File createTmpFileAndPutContent(String content) throws IOException {
        File tmpFile = File.createTempFile("test", ".xml");
        FileWriter writer = new FileWriter(tmpFile);
        writer.write(content);
        writer.close();

        return tmpFile;
    }

}
