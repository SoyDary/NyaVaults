package me.dary.InventoryHolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.dary.NyaVaults;
import me.dary.Utils.ItemUtils;


public class GUIManager {
    public GUIManager() {
    	this.mainVault = new HashMap<>();
    	this.cookiesVault = new HashMap<>();
    	this.mapsVault = new HashMap<>();
    	this.coupleVault = new HashMap<>();
        this.Players = new HashMap<>();
        this.changePge = new ArrayList<>();
	}
    
	private NyaVaults plugin = NyaVaults.getPlugin(NyaVaults.class);
	public HashMap<String, VaultGUI> mainVault;
	public HashMap<String, CookiesGUI> cookiesVault;
	public HashMap<String, MapsGUI> mapsVault;
	public HashMap<String, CoupleGUI> coupleVault;
    public HashMap<UUID, AdminGUI> Players;
    public ArrayList<UUID> changePge;

	public Inventory genericVault(Player p, String target) throws IOException {
		File pvf = new File(plugin.getDataFolder(), "PlayerVaults/"+target+".yml");
		FileConfiguration pv = (FileConfiguration)YamlConfiguration.loadConfiguration(pvf);
		plugin.getUtils();
		if (!pvf.exists()) {
			try {
				pv.save(pvf);	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		VaultGUI gui = null;
		try {
			gui = new VaultGUI(target);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return gui.getInventory();	
	}
	
	public Inventory genericCookies(Player p, String target) throws IOException {
		File pvf = new File(plugin.getDataFolder(), "PlayerVaults/"+target+".yml");
		FileConfiguration pv = (FileConfiguration)YamlConfiguration.loadConfiguration(pvf);
		plugin.getUtils();
		if (!pvf.exists()) {
			try {
				pv.save(pvf);	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		CookiesGUI gui = null;
		try {
			gui = new CookiesGUI(target);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gui.getInventory();	
	}
	
	public Inventory genericMaps(Player p, String target) throws IOException {
		File pvf = new File(plugin.getDataFolder(), "PlayerVaults/"+target+".yml");
		FileConfiguration pv = (FileConfiguration)YamlConfiguration.loadConfiguration(pvf);
		plugin.getUtils();
		if (!pvf.exists()) {
			try {
				pv.save(pvf);	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		MapsGUI gui = null;
		try {
			gui = new MapsGUI(target);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gui.getInventory();	
	}
	
	public void vault(Player p, String target) throws IOException {
		if(p.hasPermission("nv.caja")) {
		File pvf = new File(plugin.getDataFolder(), "PlayerVaults/"+target+".yml");
		FileConfiguration pv = (FileConfiguration)YamlConfiguration.loadConfiguration(pvf);
		plugin.getUtils();
		if (!pvf.exists()) {
			try {
				pv.save(pvf);	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(mainVault.containsKey(target)) {
			VaultGUI gui = mainVault.get(target);	
			p.openInventory(setExtraItems(gui.getInventory(), p, target, false));
		} else {
			VaultGUI gui = null;
			try {
				gui = new VaultGUI(target);
			} catch (IOException e) {
				e.printStackTrace();
			}
			mainVault.put(target, gui);
			p.openInventory(setExtraItems(gui.getInventory(), p, target, false));				
		}
		} else {
			p.sendMessage("§CNo tienes permiso de usar este comando");
		}
	}
	
	public void cookies(Player p, String target) throws IOException {
		File pvf = new File(plugin.getDataFolder(), "PlayerVaults/"+target+".yml");
		FileConfiguration pv = (FileConfiguration)YamlConfiguration.loadConfiguration(pvf);
		plugin.getUtils();
		if (!pvf.exists()) {
			try {
				pv.save(pvf);	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(cookiesVault.containsKey(target)) {
			CookiesGUI gui = cookiesVault.get(target);				
			p.openInventory(setExtraItems(gui.getInventory(), p, target, false));
		} else {
			CookiesGUI gui = null;
			try {
				gui = new CookiesGUI(target);
			} catch (IOException e) {
				e.printStackTrace();
			}
			cookiesVault.put(target, gui);
			p.openInventory(setExtraItems(gui.getInventory(), p, target, false));	
		}	
	}
	
	public void admin(Player p, int page) {
		try {
			AdminGUI gui = new AdminGUI(p, page);
			gui.getInventory();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void maps(Player p, String target) throws IOException {
		File pvf = new File(plugin.getDataFolder(), "PlayerVaults/"+target+".yml");
		FileConfiguration pv = (FileConfiguration)YamlConfiguration.loadConfiguration(pvf);
		plugin.getUtils();
		if (!pvf.exists()) {
			try {
				pv.save(pvf);	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(mapsVault.containsKey(target)) {
			MapsGUI gui = mapsVault.get(target);
			p.openInventory(setExtraItems(gui.getInventory(), p, target, false));		
		} else {
			MapsGUI gui = null;
			try {
				gui = new MapsGUI(target);
			} catch (IOException e) {
				e.printStackTrace();
			}
			mapsVault.put(target, gui);
			p.openInventory(setExtraItems(gui.getInventory(), p, target, false));			
		}	
	}
	public void couple(Player p, String target) throws IOException {
		String id = plugin.getDataManager().Players.getString("Players."+target+".couple_vaultID");
		File pvf = new File(plugin.getDataFolder(), "PlayerVaults/couple_"+id+".yml");
		FileConfiguration pv = (FileConfiguration)YamlConfiguration.loadConfiguration(pvf);
		plugin.getUtils();
		if (!pvf.exists()) {
			try {
				pv.save(pvf);	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(coupleVault.containsKey(id)) {
			CoupleGUI gui = coupleVault.get(id);
			p.openInventory(setExtraItems(gui.getInventory(), p, target, true));		
		} else {
			CoupleGUI gui = null;
			try {
				gui = new CoupleGUI(target, id);
			} catch (IOException e) {
				e.printStackTrace();
			}
			coupleVault.put(id, gui);
			p.openInventory(setExtraItems(gui.getInventory(), p, target, true));			
		}	
	}
	public Inventory setExtraItems(Inventory inv, Player p, String target, boolean enchanted) {
		if(p.hasPermission("nv.admin")) {
			ItemStack item = ItemUtils.namedItem(Material.TEST_BLOCK, "&#ffff00&lMenú de administrador");
			ItemMeta meta = item.getItemMeta();
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(plugin.getUtils().color(""));
			lore.add(plugin.getUtils().color("&eVer todas las cajas registradas"));
			lore.add("");
			lore.add(plugin.getUtils().color("&#ff0000&l&nPRESIONA TECLA UNKNOWN"));
			lore.add("");
			lore.add("§cPARA ELIMINAR JUGADOR");
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(46, item);
		}

		return inv;
		
		
	}
	

}
