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
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

public class EffMakeImage extends AsyncEffect {

    private Expression<String> name;
    private Expression<String> image;
    private boolean file;

    static {
        Skript.registerEffect(EffMakeImage.class, "(make|create) image %string% from (1¦file|2¦b[ase]64) %string%");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parse) {
        name = (Expression<String>) exprs[0];
        image = (Expression<String>) exprs[1];
        file = parse.mark == 1;
        return true;
    }


    @Override
    protected void execute(Event e) {

        String image = this.image.getSingle(e);
        String name = this.name.getSingle(e);
        BufferedImage bufferedImage = null;

        if (image == null || name == null) {
            return;
        }

        if (file) {
            image = Util.relativePath(image);
            try {
                bufferedImage = ImageIO.read(new File(image));
            } catch(Exception e1) {}
        } else {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                byte[] bytes = decoder.decodeBuffer(image);
                ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
                bufferedImage = ImageIO.read(stream);
                stream.close();
            } catch(Exception e1) {
                Util.error(e1);
            }
        }

        if (bufferedImage != null) {
            ImageStore.put(name, bufferedImage);
        }

    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "make image" + name + "from" + (file ? "file" : "base64") + image;
    }
}