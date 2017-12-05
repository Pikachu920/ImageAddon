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
import java.awt.image.BufferedImage;
import java.io.File;

public class EffSaveImage extends AsyncEffect {


    private Expression<String> name;
    private Expression<String> path;

    static {
        Skript.registerEffect(EffSaveImage.class, "save image %string% (to|as) %string%");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parse) {
        name = (Expression<String>) exprs[0];
        path = (Expression<String>) exprs[1];
        return true;
    }


    @Override
    protected void execute(Event e) {

        String name = this.name.getSingle(e);
        String path = this.path.getSingle(e);

        BufferedImage image = ImageStore.get(name);

        if (name == null || path == null || image == null) {
            return;
        }

        File file =  new File(Util.relativePath(path));
        file.mkdirs();

        try {
            ImageIO.write(ImageStore.get(name), "png", file);
        } catch(Exception e1) {
            Util.error(e1);
        }

    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "save image " + name + " to " + path;
    }

}
