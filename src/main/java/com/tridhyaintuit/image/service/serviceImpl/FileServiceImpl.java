package com.tridhyaintuit.image.service.serviceImpl;

import com.tridhyaintuit.image.exception.MyFileNotFoundException;
import com.tridhyaintuit.image.service.FileService;
import com.tridhyaintuit.image.utils.FileConfig;
import com.tridhyaintuit.image.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileUtils fileUtils;
    List<String> imageExtension = Arrays.asList("image/png", "image/jpeg", "image/gif", "image/jpg");

    List<String> videoExtension = Arrays.asList("video/x-flv",
            "video/3gpp", "video/mp4",
            "video/x-matroska", "video/x-msvideo",
            "video/x-ms-wmv", "video/mpeg");

    @Autowired
    FileConfig config;

    private Path fileStorageLocation;

    @Override
    public List<String> uploadImage(MultipartFile image, String folder) throws MyFileNotFoundException {

        String imageName = image.getOriginalFilename();
        String content = image.getContentType();

        String extension = "";
        int i = Objects.requireNonNull(imageName).lastIndexOf('.');
        if (i > 0) {
            extension = imageName.substring(i + 1);
        }

        if (imageExtension.contains(content)) {
            return fileUtils.storeImage(image, folder);
        } else {
            throw new MyFileNotFoundException("File not found  pls try again" + imageName);
        }

    }

    @Override
    public Resource loadFileImageAsResource(String fileName, String folder) throws MyFileNotFoundException {

        this.fileStorageLocation = Paths.get(config.getUPLOADED_IMAGES())
                .toAbsolutePath().normalize();

        Path videoThumbnailStorageLocation;

        Resource resource;
        Path filePath;

        try {

            if (folder == null) {
                fileStorageLocation = Paths.get(this.config.getUPLOADED_IMAGES()).toAbsolutePath().normalize();
            } else {
                fileStorageLocation = Paths.get(this.config.getUPLOADED_IMAGES() + "/" + folder).toAbsolutePath().normalize();
            }

            filePath = fileStorageLocation.resolve(fileName).normalize();
            resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {

                if (folder == null) {
                    videoThumbnailStorageLocation = Paths.get(this.config.getUPLOADED_THUMBNAILS()).toAbsolutePath().normalize();
                } else {
                    videoThumbnailStorageLocation = Paths.get(this.config.getUPLOADED_THUMBNAILS() + "/" + folder).toAbsolutePath().normalize();
                }

                filePath = videoThumbnailStorageLocation.resolve(fileName).normalize();
                resource = new UrlResource(filePath.toUri());

            }
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException(" File not Found => " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException(" File not Found => " + fileName, ex);
        }

    }

    @Override
    public List<String> uploadVideo(MultipartFile video, String folder) {

        String videoName = video.getOriginalFilename();
        String videoType = video.getContentType();

        String extension = "";
        int i = Objects.requireNonNull(videoName).lastIndexOf('.');
        if (i > 0) {
            extension = videoName.substring(i + 1);
        }

        if (videoExtension.contains(videoType)) {
            return fileUtils.storeVideo(video, folder);
        } else {
            return Collections.singletonList("Error while upload video.....");
        }

    }

    @Override
    public Resource loadVideoAsResource(String videoName, String folder) throws MyFileNotFoundException {

        this.fileStorageLocation = Paths.get(config.getUPLOADED_VIDEOS())
                .toAbsolutePath().normalize();

        Path filePath;
        Resource resource;

        try {

            if (folder == null) {
                fileStorageLocation = Paths.get(this.config.getUPLOADED_VIDEOS()).toAbsolutePath().normalize();
            } else {
                fileStorageLocation = Paths.get(this.config.getUPLOADED_VIDEOS() + "/" + folder).toAbsolutePath().normalize();
            }

            filePath = fileStorageLocation.resolve(videoName).normalize();
            resource = new UrlResource(filePath.toUri());


            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found  pls try again later....." + videoName);
            }

        } catch (MalformedURLException exception) {
            throw new MyFileNotFoundException("File not found pls try again later......." + videoName, exception);
        }
    }
}