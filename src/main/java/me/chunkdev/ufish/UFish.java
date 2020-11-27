package me.chunkdev.ufish;

import org.bukkit.plugin.java.JavaPlugin;

public class UFish extends JavaPlugin {
    private UFishAPI api;

    @Override
    public void onEnable() {
        api = new UFishAPI();
        getServer().getPluginManager().registerEvents(new FishingListener(), this);
    }

    public static UFishAPI getApi() {
        return getPlugin(UFish.class).api;
    }
}
