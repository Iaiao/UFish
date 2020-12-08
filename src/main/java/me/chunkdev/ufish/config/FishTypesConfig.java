package me.chunkdev.ufish.config;

import me.chunkdev.ufish.FishType;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FishTypesConfig {
    public static Map<Biome, List<FishType>> types = new HashMap<Biome, List<FishType>>() {{
        put(Biome.OCEAN, Arrays.asList(new FishType("Custom Fish", 10)));
    }};
    public static boolean enabled = true;

    static void load(ConfigurationSection config) {
        // TODO
    }
}
