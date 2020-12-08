package me.chunkdev.ufish.config;

import me.chunkdev.ufish.FishType;
import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FishTypesConfig {
    public static Map<Biome, List<FishType>> types = new HashMap<>();
    public static boolean enabled = true;

    public static void load(ConfigurationSection config) {
        Map<String, FishType> types = new HashMap<>();
        for (String key : config.getConfigurationSection("fishes").getKeys(false)) {
            int hunger = config.getInt("fishes." + key);
            types.put(key, new FishType(ChatColor.translateAlternateColorCodes('&', key), hunger));
        }
        for (String key : config.getConfigurationSection("biomes").getKeys(false)) {
            Biome biome = Biome.valueOf(key.toUpperCase());
            FishTypesConfig.types.put(
                    biome,
                    config.getStringList("biomes." + key)
                            .stream()
                            .map(types::get)
                            .collect(Collectors.toList())
            );
        }
    }
}
