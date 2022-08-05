package com.tridhyaintuit.image.utils;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@ToString
@Configuration
public class FileConfig {

    @Value( "${com.tridhyaintuit.file.UPLOADED_IMAGES}" )
    private String UPLOADED_IMAGES;

    @Value( "${com.tridhyaintuit.file1.UPLOADED_VIDEOS}" )
    private String UPLOADED_VIDEOS;

    @Value( "${com.tridhyaintuit.file2.UPLOADED_THUMBNAILS}" )
    private String UPLOADED_THUMBNAILS;

}
