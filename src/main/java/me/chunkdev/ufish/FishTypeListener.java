package me.chunkdev.ufish;

import me.chunkdev.ufish.config.FishTypesConfig;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class FishTypeListener implements Listener {
    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            Item caught = (Item) event.getCaught();
            assert caught != null;
            switch (caught.getItemStack().getType()) {
                case COD:
                case SALMON:
                    Biome biome = caught.getLocation().getBlock().getBiome();
                    if (FishTypesConfig.types.containsKey(biome)) {
                        HashMap<FishType, Integer> fish = FishTypesConfig.types.get(biome);
                        FishType type = null;
                        int max = fish
                                .values()
                                .stream()
                                .reduce(Integer::sum)
                                .get();
                        int rand = new Random().nextInt(max);
                        int i = 0;
                        List<Map.Entry<FishType, Integer>> types = fish.entrySet().stream().collect(Collectors.toList());
                        while (rand > 0) {
                            i++;
                            rand -= types.get(i).getValue();
                            type = types.get(i).getKey();
                        }
                        caught.setItemStack(type.getItem());
                        if (event.getPlayer().isInsideVehicle() && event.getPlayer().getVehicle().getType() == EntityType.BOAT) {
                            if (Math.random() < FishTypesConfig.doublingChance) {
                                caught.getItemStack().setAmount(2);
                            }
                        }
                    }
            }
        }
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        EquipmentSlot hand = event.getPlayer().getEquipment().getItemInMainHand().equals(event.getItem()) ? EquipmentSlot.HAND : EquipmentSlot.OFF_HAND;
        ItemStack item = event.getPlayer().getEquipment().getItem(hand);
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
