package com.pikachu.image;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.File;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Util {

    private static String absolute = new File("").getAbsolutePath();
    private static FileConfiguration config = Main.getInstance().getConfig();
    private static Logger log = Main.getInstance().getLogger();
    public static HashMap<String, int[]> colors = new HashMap<>();
    public static final String colorCode = Pattern.quote("§");

    public static String relativePath(String path) {
        path.replace("/", File.separator);
        path.replace("\\", File.separator);
        String[] split = path.split(Pattern.quote(File.separator));
        StringBuilder builder = new StringBuilder(absolute);
        builder.append(File.separator);
        for (String part : split) {
            if (!"".equals(part)) {
                builder.append(part);
                builder.append(File.separator);
            }
        }
        return builder.deleteCharAt(builder.length() - 1).toString();
    }

    public static Object getAttribute(ChatColor format) {
        switch (format) {
            case BOLD:
                return TextAttribute.WEIGHT_BOLD;
            case ITALIC:
                return TextAttribute.SUPERSCRIPT;
            case UNDERLINE:
                return TextAttribute.UNDERLINE;
            case STRIKETHROUGH:
                return TextAttribute.STRIKETHROUGH;
            case BLACK:
                return new Color(0, 0, 0);
            case DARK_BLUE:
                return new Color(0, 0, 170);
            case DARK_GREEN:
                return new Color(0, 170, 0);
            case DARK_AQUA:
                return new Color(0, 170, 170);
            case DARK_RED:
                return new Color(170, 0, 0);
            case DARK_PURPLE:
                return new Color(170, 0, 170);
            case GOLD:
                return new Color(255, 170, 0);
            case GRAY:
                return new Color(170, 170, 170);
            case DARK_GRAY:
                return new Color(85, 85, 85);
            case BLUE:
                return new Color(85, 85, 255);
            case GREEN:
                return new Color(85, 255, 85);
            case AQUA:
                return new Color(85, 255, 255);
            case RED:
                return new Color(255, 85, 85);
            case LIGHT_PURPLE:
                return new Color(255, 85, 255);
            case YELLOW:
                return new Color(255, 255, 85);
            case WHITE:
                return new Color(255, 255, 255);
        }
        return null;
    }

    public static AttributedString format(String mcFormat) {

        System.out.println("new input string --> " + mcFormat);

        if (mcFormat == null) {
            return null;
        }

        if (!(mcFormat.contains(colorCode))) {
            AttributedString white = new AttributedString(mcFormat);
            white.addAttribute(TextAttribute.FOREGROUND, getAttribute(ChatColor.WHITE));
            return white;
        }

        String[] split = mcFormat.split(colorCode);
        ArrayList<HashMap<String, ChatColor>> toBeFormatted = new ArrayList<>();

        System.out.println("split size --> " + split.length);
        for (String part : split) {
            if (part.isEmpty()) continue;
            System.out.println(part+ " this is the part");
            ChatColor formattingCode = ChatColor.getByChar(part.charAt(0));
            HashMap<String, ChatColor> data = new HashMap<>();
            if (formattingCode != null) {
                part = part.substring(1);
                data.put(part, formattingCode);
                toBeFormatted.add(data);
            } else {
                data.put(part, null);
                toBeFormatted.add(data);
            }
        }

        for (HashMap<String, ChatColor> map : toBeFormatted) {

            for (String s : map.keySet()) {
                System.out.println("String: \"" + s + "\" Color: " + (map.get(s) == null ? "null" : map.get(s).name()));
            }

        }

        return null;
    }

    public static void error(Exception e) {
        if (!config.getBoolean("failSilently", true)) {
            e.printStackTrace();
        }
    }
}

