package me.chunkdev.ufish;

import me.chunkdev.ufish.config.FishTypesConfig;
import me.chunkdev.ufish.config.FishingRodConfig;
import me.chunkdev.ufish.config.MessagesConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class UFish extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FishingRodConfig.load(getConfig().getConfigurationSection("rod"));
        FishTypesConfig.load(getConfig().getConfigurationSection("fish"));
        MessagesConfig.load(getConfig().getConfigurationSection("messages"));
        getServer().getPluginManager().registerEvents(new FishingListener(), this);
        getServer().getPluginManager().registerEvents(new FishingRodListener(), this);
        getServer().getPluginManager().registerEvents(new FishTypeListener(), this);
    }
}
