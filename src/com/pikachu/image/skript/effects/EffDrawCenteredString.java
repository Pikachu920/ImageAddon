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

public class EffDrawCenteredString extends AsyncEffect {


    private Expression<String> name;
    private Expression<String> string;

    static {
        Skript.registerEffect(EffDrawCenteredString.class, "draw centered %string% (to|on) image %string%");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parse) {
        string = (Expression<String>) exprs[0];
        name = (Expression<String>) exprs[1];
        return true;
    }


    @Override
    protected void execute(Event e) {

        String string = this.string.getSingle(e);
        String name = this.name.getSingle(e);

        BufferedImage image = ImageStore.get(name);

        if (string == null || name == null || image == null) {
            return;
        }

        //AttributedString formatted = Util.format(string);
        //if (formatted == null) return;

        //System.out.println("im here! -> " + formatted);
        Graphics graphics = image.getGraphics();
        Graphics2D graphics2D = (Graphics2D) graphics.create();

        Font font = new Font("Minecraftia Regular", Font.PLAIN, 31);
        graphics2D.setFont(font);
        System.out.println("hey2");
        FontMetrics metrics = graphics2D.getFontMetrics();
        int x = ((image.getWidth() - metrics.stringWidth(string)) / 2);
        int y = ((image.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(string, x, y);

        graphics2D.dispose();

    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "draw " + string + " to image " + name;
    }

}
