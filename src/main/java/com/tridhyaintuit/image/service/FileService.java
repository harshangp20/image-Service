package com.tridhyaintuit.image.service;

import com.tridhyaintuit.image.exception.MyFileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    List<String > uploadImage(MultipartFile image, String folder) throws MyFileNotFoundException;
    Resource loadFileImageAsResource(String imageName,String folder) throws MyFileNotFoundException;
    List<String> uploadVideo(MultipartFile video,String folder);
    Resource loadVideoAsResource(String videoName,String folder) throws MyFileNotFoundException;
}
