package me.chunkdev.ufish.config;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;

public class FishingRodConfig {
    public static boolean breakingEnabled = true;
    public static double breakingChance = 0.05;
    public static boolean repairingEnabled = true;
    public static Material repairingItem = Material.STRING;
    public static HashMap<Material, Integer> baits = new HashMap<>() ;

    public static void load(ConfigurationSection config) {
        breakingEnabled = config.getBoolean("breaking.enabled");
        breakingChance = config.getDouble("breaking.chance") / 100.0;
        repairingEnabled = config.getBoolean("repairing.enabled");
        repairingItem = Material.matchMaterial(config.getString("repairing.item"));
        for(String key : config.getConfigurationSection("baits").getKeys(false)) {
            baits.put(Material.matchMaterial(key), config.getInt("baits." + key));
        }
    }
}
