import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PixelToBlockTest {

    public static void main(String[] args) throws IOException {
        //Set up a hashmap to hold all of our key-value pairs.
        HashMap<String, RGBA> map = new HashMap<>();
        //Set up the buffered reader responsible for iterating through the json file
        BufferedReader br = new BufferedReader(new FileReader("blocks.json"));
        //Set up all our variables for the image we are about to import

        //Start the logic to loop over the JSON file to parse all of the blocks and their respective values.
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains("{")) {
                String name = grabName(br.readLine());
                RGBA rgba = grabRGBA(br.readLine());
                map.put(name, rgba);
            }

        }

        //Now, lets get a list of all the pixel values for an image and save all of their rgb values in a 2D array
        BufferedImage image = ImageIO.read(new File("JonesPhillip.jpg"));
        int width = image.getWidth();
        int height = image.getHeight();
        String[][] blocks = new String[width][height];

        // Now we want to iterate through the imported photo and map all of the pixels to a supported block in game.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                String closestBlock = findClosestMatch(map, image.getRGB(x, y));
//                System.out.println("Pixel: (" + x + ", " + y + ")");
//                System.out.println("Closest Block Match: " + closestBlock);
                blocks[x][y] = closestBlock;
            }
        }
        System.out.println(blocks[110][257]);
    }

    /**
     * Parses name of block within the string passed in from JSON FileIO
     * @param name - String containing the name we need to extract
     * @return - Returns shortened string only containing name
     */
    public static String grabName(String name) {
        int startIndex = 17;
        int endIndex = name.indexOf('"', startIndex + 1);
        if (endIndex == -1)
            endIndex = name.indexOf(',', startIndex + 1);
        if (endIndex == -1)
            endIndex = name.length();

        return name.substring(startIndex, endIndex);
    }

    /**
     * This method parses the JSON RGBA object from each entry in the file, passed in as a single line of FileIO
     * @param rgba - String containing the values needing to be parsed
     * @return - New RGBA object containing parsed values from string
     */
    public static RGBA grabRGBA(String rgba) {
        String[] values = rgba.substring(rgba.indexOf('[') + 1, rgba.indexOf(']')).split(",");

        short red = Short.parseShort(values[0].trim());
        short green = Short.parseShort(values[1].trim());
        short blue = Short.parseShort(values[2].trim());
        short alpha = Short.parseShort(values[3].trim());

        return new RGBA(red, green, blue, alpha);
    }

    /**
     *
     * @param map - Hashmap containing block names and their corresponding average rgba values
     * @param rgba - rgba value of specific pixel of an image represented as an integer
     * @return - Returns string block calculated to be the closest fit for image's specific pixel
     */
    public static String findClosestMatch(HashMap<String, RGBA> map, int rgba) {
        if (((rgba >> 24) & 0xFF) == 0) return "air"; // Checks to see if there's 0 opacity, meaning "blank" pixel
        double shortestDist = Double.MAX_VALUE;
        String block = "air";

        for (Map.Entry<String, RGBA> entry : map.entrySet()) {
            String blockName = entry.getKey();
            double dist = getDist(rgba, entry);
            if (dist < shortestDist) {
                shortestDist = dist;
                block = blockName;
            }
        }
        return block;
    }

    private static double getDist(int rgba, Map.Entry<String, RGBA> entry) {
        RGBA blockColor = entry.getValue();

        // Dist equation equivalent: (rgba.getX - blockColor.getX)^2
        double alphaDist = Math.pow(((rgba >> 24) & 0xFF) - blockColor.getAlpha(), 2);
        double redDist = Math.pow(((rgba >> 16) & 0xFF) - blockColor.getRed(), 2);
        double greenDist = Math.pow(((rgba >> 8) & 0xFF) - blockColor.getGreen(), 2);
        double blueDist = Math.pow((rgba & 0xFF) - blockColor.getBlue(), 2);

        double dist = Math.sqrt(alphaDist + redDist + greenDist + blueDist);
        return dist;
    }
}
