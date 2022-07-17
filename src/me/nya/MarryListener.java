package me.nya;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.hotmail.faviorivarola.ActionsChat.events.AcceptMarryEvent;
import com.hotmail.faviorivarola.ActionsChat.events.DivorceMarryEvent;
import com.hotmail.faviorivarola.ActionsChat.events.ProposeMarryEvent;

import me.nya.InventoryHolder.CoupleGUI;
import me.nya.Utils.ItemUtils;
import me.nya.Utils.Utils;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;


public class MarryListener implements Listener{
	private NyaVaults plugin = NyaVaults.getPlugin(NyaVaults.class);
	@EventHandler
	public void porpose(ProposeMarryEvent e) {
		Player p1 = e.getProposer();
		Player p2 = e.getReceive();
		if(p1.equals(p2)) {
			e.setCancelled(true);
			p1.sendMessage("§7No puedes casarte conigo mism@");
		}
	}
	@EventHandler
	public void marryAccept(AcceptMarryEvent e) {
		Player p1 = e.getProposer();
		Player p2= e.getAcceptor();
		plugin.getDataManager().createCoupleChest(p1, p2);
		p1.sendMessage(plugin.getUtils().color("&#ff1a1aღ &#ff99ff¡Un cofre compartido con &f"+p2.getName()+" &#ff99fffue creado! &#ff1a1aღ &7(/mc)"));
		p2.sendMessage(plugin.getUtils().color("&#ff1a1aღ &#ff99ff¡Un cofre compartido con &f"+p1.getName()+" &#ff99fffue creado! &#ff1a1aღ &7(/mc)"));	
	}
	@EventHandler
	public void marryDivorce(DivorceMarryEvent e) {
		Player p1 = Bukkit.getPlayer(e.getPlayer());
		Player p2 = Bukkit.getPlayer(e.getTarget());
		plugin.getDataManager().deleteCoupleChest(p1);
		p1.sendMessage(plugin.getUtils().color("&#595959&mღ&r &7¡Tu cofre compartido con &f"+e.getTarget()+" &7fue eliminado! &#595959&mღ"));		
		if(p2 != null) p2.sendMessage(plugin.getUtils().color("&#595959&mღ&r &7¡Tu cofre compartido con &f"+e.getPlayer()+" &7fue eliminado! &#595959&mღ"));
		
	}
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)  {		
		Player p = (Player) e.getWhoClicked();
		Inventory topInv = p.getOpenInventory().getTopInventory();
		if (topInv.getHolder() instanceof CoupleGUI) {		
			String owner = getVaultOwner(topInv.getItem(45));
			if ((e.getSlot() >= 45 && e.getSlot() <= 54))  {
				e.setCancelled(true);	
				if(e.getSlot() == 50) {				
					p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5, 1);
				}
				if(e.getSlot() == 51) {
					try {
						plugin.getGUI().vault(p, owner);
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5, 1);
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
				if(e.getSlot() == 47) {
					if(p.hasPermission("nv.admin")) {
						plugin.getGUI().admin(p, 0);
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5, 1);
					}

				}
				if(e.getSlot() == 52) {
					try {
						plugin.getGUI().cookies(p, owner);
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5, 1);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
									   
				}
			   if(e.getSlot() == 53) {
					try {
						plugin.getGUI().maps(p, owner);
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5, 1);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				   
			   }
			}
		}

	}
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) throws IOException {
		Player p = (Player) e.getPlayer();
		Inventory topInv = p.getOpenInventory().getTopInventory();
		if(topInv.getHolder() instanceof CoupleGUI) {
			String id = getCoupleID(topInv.getItem(45));
	    	File pvf = new File(plugin.getDataFolder(), "PlayerVaults/couple_"+id+".yml");
			FileConfiguration pv = (FileConfiguration)YamlConfiguration.loadConfiguration(pvf);
			String invb64 = ""; if(pv.getString("Items.1") != null) invb64 = pv.getString("Items.1");
			String sinv = Utils.toBase64(topInv, 45);
			if(invb64.equals(sinv)) {
			} else {
			plugin.getUtils();
			pv.set("Items.1", sinv);
			plugin.getDataManager().saveFile(pvf, pv);
			}
		}
	
	}
	public String getCoupleID(ItemStack item) {	
		String id = null;
		if(item != null) {
		net.minecraft.world.item.ItemStack nmsitem = ItemUtils.getNMSItem(item);
		NBTTagCompound tag = nmsitem.u(); if (tag == null) tag = new NBTTagCompound(); 
		NBTBase nbtbase = tag.c("VaultUUID"); String owner = nbtbase.toString().replaceAll("\"", "");
		id = plugin.getDataManager().Players.getString("Players."+owner+".couple_vaultID");
		}		
		return id;
		
	}
	public String getVaultOwner(ItemStack item) {	
		String owner = null;
		if(item != null) {
	    net.minecraft.world.item.ItemStack nmsitem = ItemUtils.getNMSItem(item);
		NBTTagCompound tag = nmsitem.u(); if (tag == null) tag = new NBTTagCompound(); 
		NBTBase nbtbase = tag.c("VaultUUID"); owner = nbtbase.toString().replaceAll("\"", "");
		}
		return owner;
		
	}

}
