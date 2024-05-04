import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdatedPixelTest {

    public static void main(String[] args) throws IOException {
        // First, I want to set up a new data structure
        HashMap<String, BlockData> data = new HashMap<>();
        // Instead of a BufferedReader, how can we read a JSON better?
        JsonReader reader = Json.createReader(new FileReader("blocks.json"));
        // Parse our JSON file into a JsonArray
        JsonArray jsonArray = reader.readArray();

        // Iterate through the JSON file and append each individual object
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject obj = jsonArray.getJsonObject(i);

            // Extract data from JSON object
            String textureName = obj.getString("name");
            JsonArray array = obj.getJsonArray("averageRGBA");
            RGBA rgba = new RGBA((short) array.getInt(0), (short) array.getInt(1),
                    (short) array.getInt(2), (short) array.getInt(3));
            String materialName = obj.getString("MaterialName");
            String blockData = obj.getString("blockData");
            BlockData block;
            if (blockData.isEmpty()) {
                block = new BlockData(rgba, materialName, -1);
            } else {
                block = new BlockData(rgba, materialName, Integer.parseInt(blockData));
            }
            // Append data to the HashSet
            data.put(textureName, block);
        }

        // Now we can get a list of all the pixel values for an image and save them to a 2D array
        BufferedImage image = ImageIO.read(new File("JonesPhillip.jpg"));
        int width = image.getWidth();
        int height = image.getHeight();
        BlockData[][] blocks = new BlockData[width][height];

        // Now we want to iterate through the imported photo and map all the pixels to a supported block in game
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                BlockData closestBlock = findClosestMatch(data, image.getRGB(x, y));
                blocks[x][y] = closestBlock;
            }
        }
        System.out.println(blocks[110][257].toString());
    }

    public static BlockData findClosestMatch(HashMap<String, BlockData> data, int rgba) {
        if (((rgba >> 24) & 0xFF) == 0) return data.get("air");
        double shortestDist = Double.MAX_VALUE;
        BlockData block = data.get("air");

        for (Map.Entry<String, BlockData> entry : data.entrySet()) {
            double dist = getDist(rgba, entry);
            if (dist < shortestDist) {
                shortestDist = dist;
                block = entry.getValue();
            }
        }
        return block;
    }

    private static double getDist(int rgba, Map.Entry<String, BlockData> entry) {
        RGBA blockColor = entry.getValue().getRgba();

        // Dist equation equivalent: (rgba.getX - blockColor.getX)^2
        double alphaDist = Math.pow(((rgba >> 24) & 0xFF) - blockColor.getAlpha(), 2);
        double redDist = Math.pow(((rgba >> 16) & 0xFF) - blockColor.getRed(), 2);
        double greenDist = Math.pow(((rgba >> 8) & 0xFF) - blockColor.getGreen(), 2);
        double blueDist = Math.pow((rgba & 0xFF) - blockColor.getBlue(), 2);

        return Math.sqrt(alphaDist + redDist + greenDist + blueDist);
    }


}
