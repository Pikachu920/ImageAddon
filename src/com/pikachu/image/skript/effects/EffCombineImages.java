package com.pikachu.image.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.pikachu.image.AsyncEffect;
import com.pikachu.image.ImageStore;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EffCombineImages extends AsyncEffect {


    private Expression<String> names;
    private Expression<String> result;

    static {
        Skript.registerEffect(EffCombineImages.class, "combine [images] %strings% vertically as %string%");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parse) {
        names = (Expression<String>) exprs[0];
        result = (Expression<String>) exprs[1];
        return true;
    }


    @Override
    protected void execute(Event e) {

        String[] names = this.names.getArray(e);
        String result = this.result.getSingle(e);
        ArrayList<BufferedImage> images = new ArrayList<>();
        int width = -1;
        int height = 0;
        for (String name : names) {
            BufferedImage image = ImageStore.get(name);
            if (image != null) {
                if (image.getWidth() > width) {
                    width = image.getWidth();
                }
                height += image.getHeight();
                images.add(image);
            }
        }

        if (images.size() != 0) {
            BufferedImage end = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = end.getGraphics();
            int y = 0;
            for (BufferedImage part : images) {
                graphics.drawImage(part, (width / 2) - (part.getWidth() / 2), y, null);
                y += part.getHeight();
            }
            ImageStore.put(result, end);
        }

    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "draw " + names + " to image " + result;
    }

}
