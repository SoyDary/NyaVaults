package me.nya.InventoryHolder;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;

import me.nya.NyaVaults;

public class AdminGUI implements InventoryHolder {	
	  public ArrayList<Inventory> pages = new ArrayList<>();
	  NyaVaults plugin = NyaVaults.getPlugin(NyaVaults.class);
	  int page_num;
	  Player p;
	  
	public AdminGUI(Player p, int page_num) throws IOException {	
		this.p = p;
		this.page_num = page_num;
		List<String> players = plugin.getDataManager().registedPlayers();
		List<List<String>> sections = Lists.partition(players, 45);
		for(List<String> sec: sections) {
			Inventory page = blankPage(page_num, sections.size());
			for(int i = 0; i < sec.size(); i++) {
				page.addItem(plugin.getUtils().vaults_headbutton(sec.get(i)));
			
			}
			pages.add(page);
			
		}
	}

	@Override
	public Inventory getInventory() {
		Inventory inv = pages.get(this.page_num);
		this.p.openInventory(inv);
		
		return null;
	}

	private Inventory blankPage(int current, int total) {
	    Inventory page = Bukkit.createInventory(this, 54, "§4Jugadores registrados §8(§7"+(current+1)+"§8/§7"+(total)+"§8)");
	    ItemStack nextpage = new ItemStack(Material.ARROW);
	    ItemMeta meta = nextpage.getItemMeta();
	    meta.setDisplayName(this.plugin.getUtils().color("&cSiguiente pagina"));
	    nextpage.setItemMeta(meta);
	    ItemStack prevpage = new ItemStack(Material.ARROW);
	    meta = prevpage.getItemMeta();
	    meta.setDisplayName(this.plugin.getUtils().color("&cPagina anterior"));
	    prevpage.setItemMeta(meta);
	    ItemStack compass = new ItemStack(Material.COMPASS);
	    compass.setAmount(current+1);
	    ItemMeta cmeta = compass.getItemMeta();
	    cmeta.setDisplayName("§4");
	    compass.setItemMeta(cmeta);
	    for (int i1 = 45; i1 < page.getSize(); i1++) { page.setItem(i1, emptyItem(Material.RED_STAINED_GLASS_PANE)); }
	    if(total > current+1) page.setItem(50, nextpage);
	    page.setItem(49, compass);
	    if(current >= 1) page.setItem(48, prevpage);    
	    return page;
	}
	public ItemStack emptyItem(Material mat) {
		ItemStack item = new ItemStack(mat);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName("§4");
		item.setItemMeta(im);
		return item;
		
		
	}

}