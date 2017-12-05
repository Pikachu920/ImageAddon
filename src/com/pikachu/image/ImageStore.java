package com.pikachu.image;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Set;

public class ImageStore {

    private static HashMap<String, BufferedImage> images = new HashMap<>();

    public static void put(String name, BufferedImage image) {
        images.put(name, image);
    }

    public static BufferedImage get(String name) {
        return images.get(name);
    }

    public static void remove(String name) {
        images.remove(name);
    }

    public static String[] allNames() {
        Set<String> set = images.keySet();
        return set.toArray(new String[set.size()]);
    }

}
