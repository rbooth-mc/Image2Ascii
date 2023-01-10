package org.bhp.image2ascii;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageResizer {

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Please provide the path to an image file, desired width, desired height, and output file path as command-line arguments.");
        } else {
            String inputFilePath = args[0];
            int width = Integer.parseInt(args[1]);
            int height = Integer.parseInt(args[2]);
            String outputFilePath = args[3];
            BufferedImage resizedImage = resize(inputFilePath, width, height);
            save(resizedImage, "jpg", outputFilePath);
        }
    }

    public static BufferedImage resize(String inputFilePath, int width, int height) {
        BufferedImage reducedImage = null;
        try {
            BufferedImage originalImage = ImageIO.read(new File(inputFilePath));

            // Convert the image to grayscale
            BufferedImage grayImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            Graphics grayImageGraphics = grayImage.getGraphics();
            grayImageGraphics.drawImage(originalImage, 0, 0, null);
            grayImageGraphics.dispose();

            // Resize the image
            BufferedImage resizedImage = new BufferedImage(width, height, grayImage.getType());
            Graphics2D g = resizedImage.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(grayImage, 0, 0, width, height, 0, 0, grayImage.getWidth(), grayImage.getHeight(), null);
            g.dispose();

            // Reduce the resolution of the image
            reducedImage = new BufferedImage(width / 2, height / 2, grayImage.getType());
            g = reducedImage.createGraphics();
            g.drawImage(resizedImage, 0, 0, width / 2, height / 2, 0, 0, width, height, null);
            g.dispose();
        } catch (IOException e) {
            System.out.println("An error occurred while trying to read or write the image file: " + e.getMessage());
        }

        return reducedImage;
    }

    public static void save(BufferedImage bufferedImage, String formatName, String outputFilePath) {
        // Save the reduced image
        try {
            ImageIO.write(bufferedImage, formatName, new File(outputFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
