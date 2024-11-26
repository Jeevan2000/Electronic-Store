package com.example.electronic.store.services.impl;

import com.example.electronic.store.exceptions.BadApiRequest;
import com.example.electronic.store.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    Logger logger = LoggerFactory.getLogger(FileService.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        //abc.png
        String originalFileName =file.getOriginalFilename();
        logger.info("File Name : {} ", originalFileName);
        String filename = UUID.randomUUID().toString();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String filenamewithext = filename + extension;
        String fullPath = path+ File.separator +filenamewithext;

        if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg"))
        {
            // file save

            File folder = new File(path);
            if(!folder.exists())
            {
                // create the folder
                folder.mkdirs();
            }

            Files.copy(file.getInputStream(), Paths.get(fullPath));
            return filenamewithext;
        }
        else {
            throw new BadApiRequest("File with these "+ extension+" not allowed");
        }
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullpath = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullpath);
        return inputStream;
    }
}
