package agh.edu.pl.healthmonitoringsystemapplication.domain.components.image;

import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.InvalidImageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ImageDecoderTest {

    private ImageDecoder imageDecoder;

    @BeforeEach
    void setUp() {
        imageDecoder = new ImageDecoder();
    }

    @Test
    void testShouldReturnBufferedImageWhenGivenValidBase64String() throws IOException {
        // Given
        BufferedImage originalImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "png", baos);
        String base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(baos.toByteArray());

        // When
        BufferedImage decodedImage = imageDecoder.decodeBase64Image(base64Image);

        // Then
        assertNotNull(decodedImage);
        assertEquals(originalImage.getWidth(), decodedImage.getWidth());
        assertEquals(originalImage.getHeight(), decodedImage.getHeight());
    }

    @Test
    void testShouldThrowInvalidImageExceptionWhenGivenInvalidBase64String() {
        // Given
        String invalidBase64Image = "data:image/png;base64,invalid_base64";

        // When & Then
        InvalidImageException exception = assertThrows(InvalidImageException.class, () -> {
            imageDecoder.decodeBase64Image(invalidBase64Image);
        });
        assertEquals("Failed to decode base64 image: Illegal base64 character 5f", exception.getMessage());
    }

    @Test
    void testShouldThrowInvalidImageExceptionWhenBase64StringHasNoComma() {
        // Given
        String base64WithoutComma = "data:image/png;base64_invalid_base64";

        // When & Then
        InvalidImageException exception = assertThrows(InvalidImageException.class, () -> {
            imageDecoder.decodeBase64Image(base64WithoutComma);
        });
        assertEquals("Failed to decode base64 image: Index 1 out of bounds for length 1", exception.getMessage());
    }
}
