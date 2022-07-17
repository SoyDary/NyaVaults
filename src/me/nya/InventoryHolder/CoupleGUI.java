package me.nya.InventoryHolder;


import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.nya.NyaVaults;
import me.nya.Utils.Utils;

public class CoupleGUI implements InventoryHolder {	
	NyaVaults plugin = NyaVaults.getPlugin(NyaVaults.class);
	Inventory inv;
	String target;
	String id;
	
	public CoupleGUI(String target, String id) throws IOException {	
		this.inv = Bukkit.createInventory(this, 54, plugin.getUtils().color("     &#ff3333ღ &#ff99ff&lCofre matrimonial &#ff3333ღ"));
		this.target	= target;
		this.id = id;
		loadItems(target);	
		}
	public void loadItems(String t) throws IOException {
		File pvf = new File(plugin.getDataFolder(), "PlayerVaults/couple_"+id+".yml");
		FileConfiguration pv = (FileConfiguration)YamlConfiguration.loadConfiguration(pvf);
		String invb64 = pv.getString("Items.1");
		if(invb64 != null) {
			plugin.getUtils();
			Inventory inv = Utils.fromBase64(invb64);
			ItemStack[] items = inv.getContents();
			this.inv.setContents(items);
		}
	}

	@Override
	public Inventory getInventory() {		
    	ItemStack glass = new ItemStack(Material.PINK_STAINED_GLASS_PANE);
    	ItemMeta glassMeta = glass.getItemMeta();
	    glassMeta.setDisplayName("§e");
	    glass.setItemMeta(glassMeta);
		for (int i1 = 45; i1 < inv.getSize()-3; i1++) { inv.setItem(i1, glass); }
		inv.setItem(51, plugin.getUtils().vaultItem_main(false));
		inv.setItem(52, plugin.getUtils().vaultItem_cookies(false));
		inv.setItem(53, plugin.getUtils().vaultItem_maps(false));
		return inv;
	}

}