package me.chunkdev.ufish.config;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;

public class FishingRodConfig {
    public static boolean breakingEnabled = true;
    public static double breakingChance = 0.05;
    public static boolean repairingEnabled = true;
    public static Material repairingItem = Material.STRING;
    public static HashMap<Material, Integer> baits = new HashMap<Material, Integer>() {{
        put(Material.SPIDER_EYE, 2);
        put(Material.SUGAR, 5);
    }};

    static void load(ConfigurationSection config) {
        // TODO
    }
}
