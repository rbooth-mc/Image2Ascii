package org.bhp.image2ascii;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageToASCII {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Please provide the path to an image file, desired width, desired height.");
            return;
        }
        String inputFilePath = args[0];
        int width = Integer.parseInt(args[1]);
        int height = Integer.parseInt(args[2]);

        BufferedImage image = ImageResizer.resize(inputFilePath, width, height);
        if (image == null) {
            throw new NullPointerException("Resized image is NULL");
        }

        // Convert the image to grayscale
        BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        // Map the pixels of the image to ASCII characters
        StringBuilder asciiArt = new StringBuilder();
        final String ASCII_CHARS = "#@B%8WM#*oahkbdpwmZO0QCJYXzcvnxrjft/\\|()1{}[]-_+~<>i!lI;:\"^`'. ";
        for (int y = 0; y < grayImage.getHeight(); y++) {
            for (int x = 0; x < grayImage.getWidth(); x++) {
                int pixel = grayImage.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;
                int intensity = (red + green + blue) / 3;

                int index = (int)((intensity / 255.0) * (ASCII_CHARS.length() - 1));
                asciiArt.append(ASCII_CHARS.charAt(index));
            }
            asciiArt.append("\n");
        }

        System.out.println(asciiArt);
    }
}
