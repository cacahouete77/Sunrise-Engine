package sunrise.environment.graphics;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class GraphicsManager {
    private HashMap<String, BufferedImage> images = new HashMap<>();

    public GraphicsManager(String graphicsPath) {

    }

    public BufferedImage getTexture(String texture) {
        return images.get(texture);
    }
}
