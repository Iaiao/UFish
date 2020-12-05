package me.chunkdev.ufish.config;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class FishingRodConfig {
    public static boolean breakingEnabled = true;
    public static double breakingChance = 0.2;
    public static boolean repairingEnabled = true;
    public static Material repairingItem = Material.STRING;

    static void load(ConfigurationSection config) {
        // TODO
    }
}
