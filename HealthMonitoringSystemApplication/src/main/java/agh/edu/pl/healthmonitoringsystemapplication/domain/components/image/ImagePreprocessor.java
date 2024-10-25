package agh.edu.pl.healthmonitoringsystemapplication.domain.components.image;

import org.springframework.stereotype.Component;
import org.tensorflow.Tensor;
import org.tensorflow.ndarray.Shape;
import org.tensorflow.ndarray.buffer.DataBuffers;
import org.tensorflow.types.TFloat32;

import java.awt.*;
import java.awt.image.BufferedImage;

@Component
public class ImagePreprocessor {

    public Tensor preprocessImage(BufferedImage image) {
        // Resize and convert to RGB
        BufferedImage resizedImage = new BufferedImage(224, 224, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image.getScaledInstance(224, 224, Image.SCALE_SMOOTH), 0, 0, null);
        g.dispose();

        // Normalization and create the float array
        float[] imageArray = new float[224 * 224 * 3]; // 224x224 pixels, 3 channels (RGB)

        int index = 0;
        for (int y = 0; y < 224; y++) {
            for (int x = 0; x < 224; x++) {
                Color pixelColor = new Color(resizedImage.getRGB(x, y));
                imageArray[index++] = pixelColor.getRed() / 255.0f;   // Red channel
                imageArray[index++] = pixelColor.getGreen() / 255.0f; // Green channel
                imageArray[index++] = pixelColor.getBlue() / 255.0f;  // Blue channel
            }
        }
        return TFloat32.tensorOf(Shape.of(1, 224, 224, 3), DataBuffers.of(imageArray)); // Shape [1, 224, 224, 3]
    }
}

