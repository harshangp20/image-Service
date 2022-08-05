package com.tridhyaintuit.image.controller;

import com.tridhyaintuit.image.model.FileResponse;
import com.tridhyaintuit.image.service.serviceImpl.FileServiceImpl;
import com.tridhyaintuit.image.exception.MyFileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileServiceImpl fileService;

    @PostMapping("/uploadImage")
    public FileResponse uploadImage(@RequestParam("file") MultipartFile file, String folder) throws MyFileNotFoundException {

        long sizeInBytes = file.getSize();
        long sizeInKB = sizeInBytes / 1024;
        long sizeInMB = sizeInKB / 1024 ;

        List<String> images;
        images = fileService.uploadImage(file,folder);

            List<String> downloadUris = new ArrayList<>();

            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/downloadImage/")
                    .path(Objects.requireNonNull(images.get(0)))
                    .toUriString();
            downloadUris.add(fileDownloadUri);
            String fileDownloadUri1 = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/downloadImage/")
                    .path(Objects.requireNonNull(images.get(1)))
                    .toUriString();
            downloadUris.add(fileDownloadUri1);
            String fileDownloadUri2 = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/downloadImage/")
                    .path(Objects.requireNonNull(images.get(2)))
                    .toUriString();
            downloadUris.add(fileDownloadUri2);
            String fileDownloadUri3 = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/downloadImage/")
                    .path(Objects.requireNonNull(images.get(3)))
                    .toUriString();
            downloadUris.add(fileDownloadUri3);
            return new FileResponse( file.getOriginalFilename() ,
                    images.get(4),
                    downloadUris,
                    file.getContentType(),
                    sizeInMB );

    }

    @PostMapping("/uploadVideo")
    public FileResponse uploadVideo(@RequestParam("file") MultipartFile file, String folder) {

        long sizeInBytes = file.getSize();
        long sizeInKB = sizeInBytes / 1024;
        long sizeInMB = sizeInKB / 1024 ;

        List<String> videos;
        videos = fileService.uploadVideo(file, folder);

        List<String> downloadUris = new ArrayList<>();

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/downloadVideo/")
                .path(Objects.requireNonNull(videos.get(0)))
                .toUriString();
        downloadUris.add(fileDownloadUri);

        String fileDownloadUri1 = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/downloadImage/")
                .path(Objects.requireNonNull(videos.get(1)))
                .toUriString();
        downloadUris.add(fileDownloadUri1);

        return new FileResponse( file.getOriginalFilename() ,
                videos.get(2),
                downloadUris,
                file.getContentType(),
                sizeInMB );
    }

    @GetMapping("/downloadImage/{filename}")
    public ResponseEntity<Resource> downloadImageFile(@PathVariable String filename, @RequestParam String folder, HttpServletRequest httpServletRequest) throws MyFileNotFoundException {

        Resource resource = fileService.loadFileImageAsResource(filename,folder);
        String imageType = null;

        try{
            imageType = httpServletRequest.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException exception){
            logger.info(" Could not determine image type. ");
        }

        if (imageType == null){
            imageType = "application/octet-stream ";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(imageType))
                .header(HttpHeaders.CONTENT_DISPOSITION,"Attachment; fileName=\"" + resource.getFilename())
                .body(resource);
    }

    @GetMapping("/downloadVideo/{videoName}")
    public ResponseEntity<Resource> downloadVideo(@PathVariable String videoName, @RequestParam String folder, HttpServletRequest request) throws MyFileNotFoundException {

        Resource resource = fileService.loadVideoAsResource(videoName,folder);

        String videoType = null;

        try{
            videoType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (IOException exception){
            logger.info("Could not determine video Type......");
        }

        if (videoType == null){
            videoType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(videoType))
                .header(HttpHeaders.CONTENT_ENCODING,"base64")
                .header(HttpHeaders.TRANSFER_ENCODING,"base64")
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; videoName=\"" + resource.getFilename() + "\"")
                .body(resource);

    }

    @GetMapping("file/{fileName}")
    public ResponseEntity<Resource> getFiles(@PathVariable String fileName,@RequestParam String folder, HttpServletRequest request) throws MyFileNotFoundException {

        Resource resource = fileService.loadFileImageAsResource(fileName,folder);

        String contentType = null;

        try{
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException exception){
            logger.info("Could not determine file type.");
        }

        assert contentType != null;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_ENCODING,"base64")
                .header(HttpHeaders.TRANSFER_ENCODING,"base64")
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=\"" + "\"")
                .body(resource);
    }

}