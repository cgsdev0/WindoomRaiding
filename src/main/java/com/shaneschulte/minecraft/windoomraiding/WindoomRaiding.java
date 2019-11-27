package com.shaneschulte.minecraft.windoomraiding;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author CoolGamrSms
 */
public class WindoomRaiding extends JavaPlugin {
    
    public Residence res;
    
    @Override
    public void onEnable() {
        System.out.println("Hell fuckin yea");
        res = (Residence)getServer().getPluginManager().getPlugin("Residence");
        getServer().getPluginManager().registerEvents(new BlockListener(this), this);
    }
    
    @Override
    public void onDisable() {
        System.out.println("Hell fuckin no");
    }
}
