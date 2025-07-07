package me.dary;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;
import me.dary.InventoryHolder.AdminGUI;

public class MigraciónListener implements Listener {
	
    NyaVaults plugin = NyaVaults.getPlugin(NyaVaults.class);

    
	@EventHandler(priority = EventPriority.LOWEST)
	public void signInteract(PlayerInteractEvent e) {
		if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		Player p = e.getPlayer();
		if(!p.getWorld().getName().equals("Nya")) return;
		Block block = e.getClickedBlock();		
		if(block == null || !(block.getState() instanceof Sign sign)) return;
		e.setCancelled(true);
	
		String name = sign.getSide(Side.FRONT).getLines()[1];
		String uuid = sign.getSide(Side.FRONT).getLines()[3];
		
		if(block.getY() == 124) {
			Location loc = findSign(130, name, uuid);
			
			if(loc != null) {
				loc.add(0.5, 0, 0.5);
				p.teleport(loc);
			}	
		}
		
		
		if(block.getY() == 130) {
			Location loc = findSign(124, name, uuid);
			if(loc != null) {
				loc.add(0.5, 0, 0.5);
				p.teleport(loc);
			}		
		}
	}
}