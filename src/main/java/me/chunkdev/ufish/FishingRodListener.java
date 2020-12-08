package me.chunkdev.ufish;

import me.chunkdev.ufish.config.FishingRodConfig;
import me.chunkdev.ufish.config.MessagesConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Random;

public class FishingRodListener implements Listener {
    private final HashMap<Player, Integer> luckEffect = new HashMap<>();

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
                        event.getPlayer().sendMessage(MessagesConfig.repaired);
                        fishingRod.repairFishingRod();
                    } else {
                        event.getPlayer().sendMessage(MessagesConfig.isBroken);
                    }
                    event.setCancelled(true);
                } else {
                    if (FishingRodConfig.breakingEnabled) {
                        if (new Random().nextDouble() < FishingRodConfig.breakingChance) {
                            if (FishingRodConfig.repairingEnabled) {
                                event.getPlayer().sendMessage(MessagesConfig.broke);
                                fishingRod.breakFishingRod();
                            } else {
                                fishingRod.getItem().setType(Material.AIR);
                            }
                            return;
                        }
                    }
                    if (FishingRodConfig.baits.containsKey(event.getPlayer().getEquipment().getItem(otherHand).getType())) {
                        Integer luck = FishingRodConfig.baits.get(event.getPlayer().getEquipment().getItem(otherHand).getType());
                        event.getPlayer().getEquipment().getItem(otherHand).setAmount(event.getPlayer().getEquipment().getItem(otherHand).getAmount() - 1);
                        if (hand == EquipmentSlot.HAND) {
                            event.getPlayer().swingOffHand();
                        } else {
                            event.getPlayer().swingMainHand();
                        }
                        event.getPlayer().getAttribute(Attribute.GENERIC_LUCK).setBaseValue(event.getPlayer().getAttribute(Attribute.GENERIC_LUCK).getBaseValue() + luck);
                        luckEffect.put(event.getPlayer(), luck);
                    }
                }
            }
        } else {
            if (luckEffect.containsKey(event.getPlayer())) {
                event.getPlayer().getAttribute(Attribute.GENERIC_LUCK).setBaseValue(event.getPlayer().getAttribute(Attribute.GENERIC_LUCK).getBaseValue() - luckEffect.get(event.getPlayer()));
                luckEffect.remove(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (luckEffect.containsKey(event.getPlayer())) {
            event.getPlayer().getAttribute(Attribute.GENERIC_LUCK).setBaseValue(event.getPlayer().getAttribute(Attribute.GENERIC_LUCK).getBaseValue() - luckEffect.get(event.getPlayer()));
            luckEffect.remove(event.getPlayer());
        }
    }
}
