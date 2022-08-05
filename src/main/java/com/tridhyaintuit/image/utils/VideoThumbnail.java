package com.tridhyaintuit.image.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class VideoThumbnail {

    protected String thumbnail;

    public VideoThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void getThumbnail(String videoName,
                             String thumbnailName,int width,
                             int height, int hour,
                             int min,float sec) throws IOException, InterruptedException {

        ProcessBuilder processBuilder = new ProcessBuilder(thumbnail, "-y", "-i", videoName, "-vframes", "1",
                "-ss", hour + ":" + min + ":" + sec, "-f", "mjpeg", "-s", width + "*" + height, "-an", thumbnailName);
        Process process = processBuilder.start();
        InputStream stream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line;
        while ((line = reader.readLine()) != null);
            process.waitFor();
    }
}
