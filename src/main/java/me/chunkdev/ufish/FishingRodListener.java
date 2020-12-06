package me.chunkdev.ufish;

import me.chunkdev.ufish.config.FishingRodConfig;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Random;

public class FishingRodListener implements Listener {
    @EventHandler
    public void onFishing(PlayerFishEvent event) {
        EquipmentSlot hand = event.getPlayer().getInventory().getItemInMainHand().getType() == Material.FISHING_ROD ? EquipmentSlot.HAND : EquipmentSlot.OFF_HAND;
        EquipmentSlot otherHand = hand == EquipmentSlot.HAND ? EquipmentSlot.OFF_HAND : EquipmentSlot.HAND;
        FishingRod fishingRod = FishingRod.fromItemStack(event.getPlayer().getEquipment().getItem(hand)).orElse(null);
        if (event.getState() == PlayerFishEvent.State.FISHING) {
            if (fishingRod != null) {
                if (fishingRod.isBroken()) {
                    if (event.getPlayer().getEquipment().getItem(otherHand).getType() == FishingRodConfig.repairingItem) {
                        if (hand == EquipmentSlot.HAND) {
                            event.getPlayer().swingMainHand();
                        } else {
                            event.getPlayer().swingOffHand();
                        }
                        event.getPlayer().getEquipment().getItem(otherHand).setAmount(event.getPlayer().getEquipment().getItem(otherHand).getAmount() - 1);
                        if (hand == EquipmentSlot.HAND) {
                            event.getPlayer().swingOffHand();
                        } else {
                            event.getPlayer().swingMainHand();
                        }
                        fishingRod.repairFishingRod();
                        event.setCancelled(true);
                    }
                } else if (FishingRodConfig.breakingEnabled) {
                    if (new Random().nextDouble() < FishingRodConfig.breakingChance) {
                        if (FishingRodConfig.repairingEnabled) {
                            fishingRod.breakFishingRod();
                        } else {
                            fishingRod.getItem().setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }
}
