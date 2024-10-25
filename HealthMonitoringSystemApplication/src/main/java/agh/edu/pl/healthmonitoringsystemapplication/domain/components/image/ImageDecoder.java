package agh.edu.pl.healthmonitoringsystemapplication.domain.components.image;

import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.InvalidImageException;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Base64;

@Component
public class ImageDecoder {

    public BufferedImage decodeBase64Image(String base64Str) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64Str.split(",")[1]); // Keep part after comma
            return ImageIO.read(new ByteArrayInputStream(imageBytes));
        } catch (Exception ex) {
            throw new InvalidImageException("Failed to decode base64 image: " + ex.getMessage());
        }
    }
}
