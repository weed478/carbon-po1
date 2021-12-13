package agh.ics.oop.gui;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class ImageProvider {

    private static final Map<String, Image> cache = new HashMap<>();

    public static Image getForAsset(String assetName) {
        Image imageView = cache.get(assetName);
        if (imageView != null) {
            return imageView;
        }

        Image image;
        try {
            image = new Image(new FileInputStream(assetName));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Invalid asset name");
        }

        cache.put(assetName, image);
        return image;
    }
}
