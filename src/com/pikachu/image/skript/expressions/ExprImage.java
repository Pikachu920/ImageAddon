package com.pikachu.image.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.pikachu.image.ImageStore;
import org.bukkit.event.Event;

import java.awt.image.BufferedImage;

public class ExprImage extends SimpleExpression<BufferedImage> {

    private Expression<String> name;

    static {
        Skript.registerExpression(ExprImage.class, BufferedImage.class, ExpressionType.SIMPLE,"image[s] %strings%");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        name = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    protected BufferedImage[] get(final Event e) {
        return new BufferedImage[] { ImageStore.get(name.getSingle(e)) };
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "image " + name;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.RESET) {
            return CollectionUtils.array(String[].class);
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        String[] names = name.getAll(e);
        if (names == null) return;
        switch (mode) {
            case RESET:
            case DELETE:
                for (String name : names) {
                    ImageStore.remove(name);
                }
                break;
        }
    }

    @Override
    public Class<? extends BufferedImage> getReturnType() {
        return BufferedImage.class;
    }

}
