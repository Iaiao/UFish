package me.chunkdev.ufish;

import me.chunkdev.ufish.config.FishingRodConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.Optional;
import java.util.Random;

public class FishingRodListener implements Listener {
    @EventHandler
    public void onFishing(PlayerFishEvent event) {
        FishingRod fishingRod = FishingRod.fromItemStack(event.getPlayer().getInventory().getItemInMainHand()).orElse(FishingRod.fromItemStack(event.getPlayer().getInventory().getItemInOffHand()).orElse(null));
        if (fishingRod != null) {
            if(fishingRod.isBroken()) {
                event.setCancelled(true);
                if(event.getPlayer().getInventory().getItemInMainHand().getType() == FishingRodConfig.repairingItem) {
                    event.getPlayer().swingMainHand();
                    event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                    event.getPlayer().swingOffHand();
                    fishingRod.repairFishingRod();
                } else if(event.getPlayer().getInventory().getItemInOffHand().getType() == FishingRodConfig.repairingItem) {
                    event.getPlayer().swingOffHand();
                    event.getPlayer().getInventory().getItemInOffHand().setAmount(event.getPlayer().getInventory().getItemInOffHand().getAmount() - 1);
                    event.getPlayer().swingMainHand();
                    fishingRod.repairFishingRod();
                } else {
                    event.getHook().remove();
                }
                return;
            }
            if (FishingRodConfig.breakingEnabled) {
                if (new Random().nextDouble() < FishingRodConfig.breakingChance) {
                    fishingRod.breakFishingRod();
                }
            }
        }
    }
}
