/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shaneschulte.minecraft.windoomraiding;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.nitnelave.CreeperHeal.CreeperHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 *
 * @author schul
 */
public class BlockListener implements Listener {
    
    private final WindoomRaiding pl;
    
    BlockListener(WindoomRaiding pl) {
        this.pl = pl;
    }
    
    @EventHandler
    public void onArmorStandPlace(EntitySpawnEvent e) {
        if(e.getEntity() instanceof ArmorStand) {
            ArmorStand as = (ArmorStand)e.getEntity();
            as.setGravity(false);
        }
    }
    
    @EventHandler(ignoreCancelled=false, priority=EventPriority.HIGH)
    public void onBlockBreak(BlockPlaceEvent e) {
        Location loc = e.getBlock().getLocation();
        ClaimedResidence res = pl.res.getResidenceManager().getByLoc(loc);
        if(res == null) return;
        Player p = e.getPlayer();
        ResidencePermissions perms = res.getPermissions();
        boolean canBuild = perms.playerHas(p, Flags.build, true);
        boolean canRaid  = perms.has(Flags.tnt, true);
        if(canBuild) return;
        Material m = e.getBlock().getType();
        if(canRaid && m == Material.TNT) {
            e.setCancelled(true);
            loc.getWorld().spawnEntity(loc.add(0.5, 0, 0.5), EntityType.PRIMED_TNT);
            if(p.getInventory().getItemInMainHand().getType() == Material.TNT)
                p.getInventory().removeItem(new ItemStack(Material.TNT, 1));
            else if(p.getInventory().getItemInOffHand().getType() == Material.TNT)
                p.getInventory().getItemInOffHand().setAmount(
                        p.getInventory().getItemInOffHand().getAmount() - 1);
        }
    }
}
