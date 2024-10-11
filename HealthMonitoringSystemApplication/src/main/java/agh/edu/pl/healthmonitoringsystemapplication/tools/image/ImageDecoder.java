package agh.edu.pl.healthmonitoringsystemapplication.tools.image;

import agh.edu.pl.healthmonitoringsystemapplication.exceptions.InvalidImageException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageDecoder {

    public BufferedImage decodeBase64Image(String base64Str) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64Str.split(",")[1]); // Keep part after comma
            return ImageIO.read(new ByteArrayInputStream(imageBytes));
        } catch (IOException e) {
            throw new InvalidImageException("Failed to decode base64 image: " + e.getMessage());
        }
    }
}
