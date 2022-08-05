package com.tridhyaintuit.image.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FileResponse {

    private String fileName;
    private String filePath;
    private List<String> fileDownloadUri;
    private String fileType;
    private long sizeOfFileInMB;
}