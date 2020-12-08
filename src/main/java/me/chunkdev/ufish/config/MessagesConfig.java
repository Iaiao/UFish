package me.chunkdev.ufish.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public class MessagesConfig {
    public static String broke;
    public static String isBroken;
    public static String repaired;

    public static void load(ConfigurationSection config) {
        broke = ChatColor.translateAlternateColorCodes('&', config.getString("broke"));
        isBroken = ChatColor.translateAlternateColorCodes('&', config.getString("isbroken"));
        repaired = ChatColor.translateAlternateColorCodes('&', config.getString("repaired"));
    }
}
