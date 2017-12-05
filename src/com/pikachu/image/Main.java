package com.pikachu.image;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.IOException;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    private static Main instance;
    private static SkriptAddon addon;
    private Logger log;
    private FileConfiguration config = getConfig();

    public Main() {
        if (instance == null) {
            instance = this;
            log = this.getLogger();
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void onEnable() {

        config.addDefault("failSilently", true);
        config.options().copyDefaults(true);
        saveConfig();

        Util.colors.put("BLACK", new int[] {0, 0, 0});
        Util.colors.put("DARK_BLUE", new int[] {0, 0, 170});
        Util.colors.put("DARK_GREEN", new int[] {0, 170, 0});
        Util.colors.put("DARK_AQUA", new int[] {0, 170, 170});
        Util.colors.put("DARK_RED", new int[] {170, 0, 0});
        Util.colors.put("DARK_PURPLE", new int[] {170, 0, 170});
        Util.colors.put("GOLD", new int[] {255, 170, 0});
        Util.colors.put("GRAY", new int[] {170, 170, 170});
        Util.colors.put("DARK_GRAY", new int[] {85, 85, 85});
        Util.colors.put("BLUE", new int[] {85, 85, 255});
        Util.colors.put("GREEN", new int[] {85, 255, 85});
        Util.colors.put("AQUA", new int[] {85, 255, 255});
        Util.colors.put("RED", new int[] {255, 85, 85});
        Util.colors.put("LIGHT_PURPLE",  new int[] {255, 85, 255});
        Util.colors.put("YELLOW", new int[] {255, 255, 85});
        Util.colors.put("WHITE", new int[] {255, 255, 255});

        try {
            getAddonInstance().loadClasses("com.pikachu.skriptaddon.skript", "classes", "expressions", "effects");
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public static Main getInstance() {
        if (instance == null) {
            throw new IllegalStateException();
        }
        return instance;
    }

    public static SkriptAddon getAddonInstance() {
        if (addon == null) {
            addon = Skript.registerAddon(getInstance());
        }
        return addon;
    }

}