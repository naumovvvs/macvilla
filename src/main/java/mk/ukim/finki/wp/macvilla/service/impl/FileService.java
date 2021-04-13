package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.exceptions.FileStorageException;
import mk.ukim.finki.wp.macvilla.web.controller.FilepathConstants;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    private final String uploadDir = System.getProperty("user.home") + FilepathConstants.FILE_DESTINATION_SUFFIX;

    public void uploadFile(MultipartFile file) {

        try {
            Path copyLocation = Paths
                    .get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileStorageException("Could not store file " + file.getOriginalFilename()
                    + ".Please try again!");
        }
    }
}
