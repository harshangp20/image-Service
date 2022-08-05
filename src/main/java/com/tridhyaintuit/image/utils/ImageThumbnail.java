package com.tridhyaintuit.image.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageThumbnail {

    public static BufferedImage resizeImage( BufferedImage originalImage, int width, int height ) throws IOException {

        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage,0 , 0, width, height, null);
        graphics2D.dispose();
        return resizedImage;

    }

}
