import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ImageRGBAnalyzer {

    public static void main(String[] args) {
        // Specify the directory containing PNG files
        File directory = new File("blocks/");

        // Create StringBuilder to store JSON string
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[\n");

        // Iterate through each file in the directory
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".png")) {
                    try {
                        // Read the image file
                        BufferedImage image = ImageIO.read(file);
                        // Calculate average RGBA values
                        int[] averageRGBA = calculateAverageRGBA(image);
                        // Append JSON entry for current file
                        jsonBuilder.append("    {\n");
                        jsonBuilder.append("        \"name\": \"" + file.getName() + "\",\n");
                        jsonBuilder.append("        \"averageRGBA\": [" + averageRGBA[0] + ", " + averageRGBA[1] +
                                ", " + averageRGBA[2] + ", " + averageRGBA[3] + "]\n");
                        jsonBuilder.append("    },\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            // Remove trailing comma and newline if there are files
            if (jsonBuilder.length() > 2) {
                jsonBuilder.setLength(jsonBuilder.length() - 2);
            }
            jsonBuilder.append("\n]");
        }

        // Write JSON string to file
        try (FileWriter fileWriter = new FileWriter("output.json")) {
            fileWriter.write(jsonBuilder.toString());
            System.out.println("JSON file created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to calculate average RGBA values of an image
    private static int[] calculateAverageRGBA(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int totalRed = 0;
        int totalGreen = 0;
        int totalBlue = 0;
        int totalAlpha = 0;
        int totalPixels = width * height;

        // Loop through each pixel in the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                // Extract RGBA values
                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                // Add RGBA values to totals
                totalAlpha += alpha;
                totalRed += red;
                totalGreen += green;
                totalBlue += blue;
            }
        }

        // Calculate average RGBA values
        int averageAlpha = totalAlpha / totalPixels;
        int averageRed = totalRed / totalPixels;
        int averageGreen = totalGreen / totalPixels;
        int averageBlue = totalBlue / totalPixels;

        return new int[]{averageRed, averageGreen, averageBlue, averageAlpha};
    }
}
