package me.dary;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;
import me.dary.InventoryHolder.AdminGUI;
import me.dary.InventoryHolder.CookiesGUI;
import me.dary.InventoryHolder.CoupleGUI;
import me.dary.InventoryHolder.MapsGUI;
import me.dary.InventoryHolder.VaultGUI;
import me.dary.Utils.Utils;

public class Eventos implements Listener {
	ArrayList<Player> clicked = new ArrayList<Player>();
	public Map<Player, ItemStack> offhandItems = new HashMap<Player, ItemStack>();
    NyaVaults plugin = NyaVaults.getPlugin(NyaVaults.class);

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) throws IOException {
		Player p = (Player) e.getPlayer();
		Inventory topInv = p.getOpenInventory().getTopInventory();
		if(topInv.getHolder() instanceof VaultGUI) {
			String owner = getVaultOwner(topInv.getItem(45));
	    	File pvf = new File(plugin.getDataFolder(), "PlayerVaults/"+owner+".yml");
			FileConfiguration pv = (FileConfiguration)YamlConfiguration.loadConfiguration(pvf);
			String invb64 = ""; if(pv.getString("MainVault.1") != null) invb64 = pv.getString("MainVault.1");
			String sinv = Utils.toBase64(topInv, 45);
			if(invb64.equals(sinv)) {
			} else {
			plugin.getUtils();
			pv.set("MainVault.1", sinv);
			plugin.getDataManager().saveFile(pvf, pv);
			}
		}
	
		if(topInv.getHolder() instanceof CookiesGUI) {
			String owner = getVaultOwner(topInv.getItem(45));
	    	File pvf = new File(plugin.getDataFolder(), "PlayerVaults/"+owner+".yml");
			FileConfiguration pv = (FileConfiguration)YamlConfiguration.loadConfiguration(pvf);
			String invb64 = ""; if(pv.getString("CookiesVault.1") != null) invb64 = pv.getString("CookiesVault.1");
			String sinv = Utils.toBase64(topInv, 45);
			if(invb64.equals(sinv)) {
			} else {
			plugin.getUtils();
			pv.set("CookiesVault.1", sinv);
			plugin.getDataManager().saveFile(pvf, pv);
			}
		}
		if(topInv.getHolder() instanceof MapsGUI) {
			String owner = getVaultOwner(topInv.getItem(45));
	    	File pvf = new File(plugin.getDataFolder(), "PlayerVaults/"+owner+".yml");
			FileConfiguration pv = (FileConfiguration)YamlConfiguration.loadConfiguration(pvf);
			String invb64 = ""; if(pv.getString("MapsVault.1") != null) invb64 = pv.getString("MapsVault.1");
			String sinv = Utils.toBase64(topInv, 45);
			if(invb64.equals(sinv)) {
			} else {
			plugin.getUtils();
			pv.set("MapsVault.1", sinv);
			plugin.getDataManager().saveFile(pvf, pv);
			}
		}
		
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)  {		
		Player p = (Player) e.getWhoClicked();
		Inventory topInv = p.getOpenInventory().getTopInventory();
		if (topInv.getHolder() instanceof VaultGUI) {		
			String owner = getVaultOwner(topInv.getItem(45));
			if ((e.getSlot() >= 45 && e.getSlot() <= 54))  {
				e.setCancelled(true);	
				if(e.getSlot() == 51) {
					p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5, 1);
				}
				if(e.getSlot() == 46) {
					if(p.hasPermission("nv.admin")) {
						if (e.getClick() != ClickType.MIDDLE) return;
						p.closeInventory();
						File pvf = new File(plugin.getDataFolder(), "PlayerVaults/"+owner+".yml");
						pvf.delete();
						p.sendMessage("§4Eliminado el cofre §c"+owner);
						plugin.getGUI().admin(p, 0);
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
		if (topInv.getHolder() instanceof CookiesGUI) {
			String owner = getVaultOwner(topInv.getItem(45));
			ItemStack currentItem = e.getCurrentItem();
			if(currentItem !=null && !validCookie(currentItem)) {
				e.setCancelled(true);
				if ((e.getSlot() >= 45 && e.getSlot() <= 54))  {
					if(e.getSlot() == 47) {
						if(p.hasPermission("nv.admin")) {
							plugin.getGUI().admin(p, 0);
							p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5, 1);
						}

					}

				if(e.getSlot() == 51) {
					try {
						plugin.getGUI().vault(p, owner);
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5, 1);
					} catch (IOException e1) {
						e1.printStackTrace();
					}	
					
				}
				if(e.getSlot() == 52) {
					p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5, 1);	   
				}
			   if(e.getSlot() == 53) {
					try {
						plugin.getGUI().maps(p, owner);
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5, 1);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				   
			   }
			} else {
				p.sendMessage(plugin.getUtils().color("&#bf8040⬤ &#d9b38c¡Solo puedes guardar galletas aquí!"));
				 p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 5, 1);
			}
			}
		}
		if (topInv.getHolder() instanceof MapsGUI) {	
			String owner = getVaultOwner(topInv.getItem(45));
			ItemStack currentItem = e.getCurrentItem();
			if(e.getSlot() == 53) e.setCancelled(true);
			if(currentItem != null) {
				if(!currentItem.getType().toString().equalsIgnoreCase("FILLED_MAP")) {			
				e.setCancelled(true);	
				if ((e.getSlot() >= 45 && e.getSlot() <= 54))  {
					if(e.getSlot() == 47) {
						if(p.hasPermission("nv.admin")) {
							plugin.getGUI().admin(p, 0);
							p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5, 1);
						}

					}
					
					
					if(e.getSlot() == 51) {
						try {
							plugin.getGUI().vault(p, owner);
							p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5, 1);
						} catch (IOException e1) {
							e1.printStackTrace();
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
				} else {
					p.sendMessage(plugin.getUtils().color("&b▆ &#1a8cff¡Solo puedes introducir mapas aquí!"));
				    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 5, 1);
				}
			} else {
				if(!validMap(currentItem) && !p.hasPermission("nya.mapasbypass")) {
					e.setCancelled(true);
					if(!currentItem.equals(plugin.getUtils().vaultItem_maps(true))) {
						 p.sendMessage(plugin.getUtils().color("&b▆ &#ffff66¡Debes de marcar este mapa como &#b3ecffespecial &#ffff66primero!"));
						 p.sendMessage(plugin.getUtils().color("   &#ffff99&oUsa el comando: &#ffffe6&o/markmap"));
						 p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 5, 1);
						
					} else {
						   if(e.getSlot() == 53) {
							   p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5, 1);
				
						   }
						
					}
				
						
					} 
					
				}
			}
			} 
		if (topInv.getHolder() instanceof AdminGUI) {
			e.setCancelled(true);
			if(e.getClickedInventory() != null && e.getClickedInventory().equals(topInv)) {
				if ((e.getSlot() >= 0 && e.getSlot() <= 44))  {				
					ItemStack currentItem = e.getCurrentItem();
					if(currentItem != null) {
					String owner =null;
					p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 5, 1);
					owner = getVaultOwner(currentItem);
						
					try {
						plugin.getGUI().vault(p, owner);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}	
				}
				int currentPage = topInv.getItem(49).getAmount()-1;
				if(e.getSlot() == 48) {
					if(topInv.getItem(48).getType().toString().equals("ARROW")) {
						plugin.getGUI().admin(p, currentPage-1);
					}
				}
				if(e.getSlot() == 50) {
					if(topInv.getItem(50).getType().toString().equals("ARROW")) {
						plugin.getGUI().admin(p, currentPage+1);
						
					}
					
					
				}
			}
		
		}
	}

	@EventHandler
	public void join(PlayerJoinEvent e) {
		Player p = (Player) e.getPlayer();		  
		plugin.getDataManager().savePlayerName(p);
		  
	}
	public boolean validCookie(ItemStack item) {
		boolean bl = false;
		if(item != null && item.getType().toString().equals("COOKIE")) {
			if(!item.equals(plugin.getUtils().vaultItem_cookies(true))) {
				bl = true;
				
			}
		}
		return bl;
		
	}
	public boolean validMap(ItemStack item) {
        NBTItem nbt = new NBTItem(item);
        return nbt.getString("SavedMap").equals("true");
	}
	
	public String getVaultOwner(ItemStack item) {	
		if(item == null) return null;
		NBTItem nbt = new NBTItem(item);
		return nbt.getString("VaultUUID");	
	}
	public boolean hasCouple(String uuid) {
		boolean bl = false;
		if(plugin.getDataManager().Players.getString("Players."+uuid+".couple_vaultID") != null) {
			bl = true;
			
		}
		return bl;
	}
	@EventHandler
	public void swapItem(InventoryClickEvent e) {
		if(!isMenu(e.getInventory().getHolder())) return;
		if(e.getClick() != ClickType.SWAP_OFFHAND) return;		
		e.setCancelled(true);	
	}
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e) {		
		if(!isMenu(e.getInventory().getHolder())) return;
		
		offhandItems.put((Player) e.getPlayer(), e.getPlayer().getInventory().getItem(40));
	}

	@EventHandler
	public void onInventoryCloseE(InventoryCloseEvent e) {	
		if(!isMenu(e.getInventory().getHolder())) return;
		if(!offhandItems.containsKey(e.getPlayer())) return;
		e.getPlayer().getInventory().setItem(40, offhandItems.get(e.getPlayer()));
	}
	
	public boolean isMenu(InventoryHolder holder) {
		if(holder instanceof VaultGUI) return true;
		if(holder instanceof CookiesGUI) return true;
		if(holder instanceof AdminGUI) return true;
		if(holder instanceof CoupleGUI) return true;
		if(holder instanceof MapsGUI) return true;
		return false;
		
		
	}
	
}