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

public class ExprAllImages extends SimpleExpression<String> {

    private Expression<String> name;

    static {
        Skript.registerExpression(ExprAllImages.class, String.class, ExpressionType.SIMPLE,"all [(the|of the)] images");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    protected String[] get(final Event e) {
        return ImageStore.allNames();
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.RESET) {
            return CollectionUtils.array(String.class);
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        switch (mode) {
            case RESET:
            case DELETE:
                for (String image : ImageStore.allNames()) {
                    ImageStore.remove(image);
                }
                break;
        }
    }
    @Override
    public String toString(Event e, boolean debug) {
        return "all images";
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

}
