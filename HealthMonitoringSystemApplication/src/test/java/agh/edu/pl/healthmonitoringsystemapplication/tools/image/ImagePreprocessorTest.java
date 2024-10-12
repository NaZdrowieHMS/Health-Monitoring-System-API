package agh.edu.pl.healthmonitoringsystemapplication.tools.image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tensorflow.Tensor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ImagePreprocessorTest {

    private ImagePreprocessor imagePreprocessor;

    @BeforeEach
    void setUp() {
        imagePreprocessor = new ImagePreprocessor();
    }

    @Test
    void testShouldReturnValidTensorWhenGivenBufferedImage() {
        // Given
        BufferedImage inputImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = inputImage.createGraphics();
        g.setColor(Color.RED);
        g.fillRect(0, 0, 300, 300); // Fill the image with red color
        g.dispose();

        // When
        Tensor resultTensor = imagePreprocessor.preprocessImage(inputImage);

        // Then
        assertNotNull(resultTensor);
        assertEquals(4, resultTensor.shape().numDimensions()); // Shape should have 4 dimensions
        List<Long> resultTensorShape = resultTensor.shape().toListOrNull();
        assertEquals(1, resultTensorShape.getFirst()); // Batch size should be 1
        assertEquals(224, resultTensorShape.get(1)); // Height should be 224
        assertEquals(224, resultTensorShape.get(2)); // Width should be 224
        assertEquals(3, resultTensorShape.getLast()); // Channels should be 3
    }
}
