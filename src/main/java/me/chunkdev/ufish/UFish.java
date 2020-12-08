package me.chunkdev.ufish;

import org.bukkit.plugin.java.JavaPlugin;

public class UFish extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new FishingListener(), this);
        getServer().getPluginManager().registerEvents(new FishingRodListener(), this);
    }
}
