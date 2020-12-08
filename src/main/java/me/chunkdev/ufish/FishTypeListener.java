package me.chunkdev.ufish;

import me.chunkdev.ufish.config.FishTypesConfig;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

public class FishTypeListener implements Listener {
    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (FishTypesConfig.enabled) {
            if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
                Item caught = (Item) event.getCaught();
                assert caught != null;
                if (caught.getItemStack().getType() == Material.COD) {
                    Biome biome = caught.getLocation().getBlock().getBiome();
                    if (FishTypesConfig.types.containsKey(biome)) {
                        FishType type = FishTypesConfig.types.get(biome).get(new Random().nextInt(FishTypesConfig.types.get(biome).size()));
                        caught.setItemStack(type.getItem());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        EquipmentSlot hand = event.getPlayer().getEquipment().getItemInMainHand().equals(event.getItem()) ? EquipmentSlot.HAND : EquipmentSlot.OFF_HAND;
        ItemStack item = event.getPlayer().getEquipment().getItem(hand);
        if (FishTypesConfig.enabled) {
            if (event.getItem().getType() == Material.COD) {
                if (event.getItem().hasItemMeta()) {
                    ItemMeta meta = event.getItem().getItemMeta();
                    assert meta != null;
                    if (meta.getPersistentDataContainer().has(FishType.HUNGER_KEY, PersistentDataType.INTEGER)) {
                        Integer hunger = meta.getPersistentDataContainer().get(FishType.HUNGER_KEY, PersistentDataType.INTEGER);
                        assert hunger != null;
                        item.setAmount(item.getAmount() - 1);
                        event.setCancelled(true);
                        event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + hunger);
                    }
                }
            }
        }
    }
}
