package me.chunkdev.ufish;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

public class FishingRod {
    private final ItemStack item;
    private static NamespacedKey broken = new NamespacedKey(UFish.getPlugin(UFish.class), "broken");

    private FishingRod(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    public void breakFishingRod() {
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.getPersistentDataContainer().set(broken, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
    }

    public void repairFishingRod() {
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.getPersistentDataContainer().set(broken, PersistentDataType.BYTE, (byte) 0);
        item.setItemMeta(meta);
    }

    public boolean isBroken() {
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        return meta.getPersistentDataContainer().has(broken, PersistentDataType.BYTE) && meta.getPersistentDataContainer().get(broken, PersistentDataType.BYTE) == 1;
    }

    public static Optional<FishingRod> fromItemStack(ItemStack item) {
        if (item == null || item.getType() == Material.FISHING_ROD) {
            return Optional.of(new FishingRod(item));
        } else {
            return Optional.empty();
        }
    }
}
