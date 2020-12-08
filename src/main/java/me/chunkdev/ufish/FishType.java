package me.chunkdev.ufish;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class FishType {
    public String name;
    public int hunger;
    public static NamespacedKey HUNGER_KEY = new NamespacedKey(UFish.getPlugin(UFish.class), "hunger");

    public FishType(String name, int hunger) {
        this.name = name;
        this.hunger = hunger;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.COD);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(HUNGER_KEY, PersistentDataType.INTEGER, hunger);
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
}
