package com.pikachu.image.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.pikachu.image.AsyncEffect;
import com.pikachu.image.ImageStore;
import com.pikachu.image.Util;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import javax.xml.soap.Text;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.AttributedString;

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
        Font font = new Font("Arial", Font.PLAIN, 10);
        Font serifFont = new Font("Serif", Font.PLAIN, 10);
        Font sansSerifFont = new Font("Monospaced", Font.PLAIN, 10);
        AttributedString as = new AttributedString(string);
        as.addAttribute(TextAttribute.FONT, serifFont);
        as.addAttribute(TextAttribute.FONT, sansSerifFont, 2, 5);
        as.addAttribute(TextAttribute.FOREGROUND, Color.red, 5, 6);
        as.addAttribute(TextAttribute.FOREGROUND, Color.red, 16, 17);

        System.out.println("im here 3! -> " + as);
        Graphics2D graphics2D = image.createGraphics();

        graphics2D.setFont(font);
        System.out.println("hey 5");
        FontMetrics metrics = graphics2D.getFontMetrics();
        int x = ((image.getWidth() - metrics.stringWidth(string)) / 2); //
        int y = ((image.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

        graphics2D.clearRect(0, 0, image.getWidth(), image.getHeight());
        graphics2D.drawString(as.getIterator(), x, y);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.75f));
        graphics2D.dispose();

    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "draw " + string + " to image " + name;
    }

}
