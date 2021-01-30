package me.chunkdev.ufish.config;

import me.chunkdev.ufish.FishType;
import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FishTypesConfig {
    public static Map<Biome, HashMap<FishType, Integer>> types = new HashMap<>();
    public static double doublingChance;

    public static void load(ConfigurationSection config) {
        doublingChance = config.getDouble("doublingchance") / 100.0;
        Map<String, FishType> types = new HashMap<>();
        for (String key : config.getConfigurationSection("fish").getKeys(false)) {
            int hunger = config.getInt("fish." + key);
            types.put(key, new FishType(ChatColor.translateAlternateColorCodes('&', key), hunger));
        }
        for (String key : config.getConfigurationSection("biomes").getKeys(false)) {
            Biome biome = Biome.valueOf(key.toUpperCase());
            System.out.println(config.getConfigurationSection("biomes." + key).getValues(false));
            FishTypesConfig.types.put(
                    biome,
                    (HashMap<FishType, Integer>) config.getConfigurationSection("biomes." + key).getValues(false)
                            .entrySet()
                            .stream()
                            .map(entry -> new AbstractMap.SimpleEntry<>(types.get(entry.getKey()), (Integer) entry.getValue()))
                            .collect(Collectors.toMap(e -> ((Map.Entry<FishType, Integer>)e).getKey(), e -> ((Map.Entry<FishType, Integer>)e).getValue()))
            );
        }
    }
}
