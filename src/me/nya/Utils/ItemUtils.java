package me.nya.Utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import me.nya.NyaVaults;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;


public class ItemUtils {
	
	private static NyaVaults plugin = NyaVaults.getPlugin(NyaVaults.class);

	public static int getMapID(ItemStack map) {
	    net.minecraft.world.item.ItemStack nmsitem = getNMSItem(map);
	    if(nmsitem == null) return -1;
	    NBTTagCompound tag = nmsitem.u();
	    if (tag == null) tag = new NBTTagCompound(); 
	    for (String k : tag.d()) {
	        NBTBase nbtbase = tag.c(k);
	        if (k.equalsIgnoreCase("map")) {
	        	return Integer.valueOf(nbtbase.toString());
	        } 
	    }
		return -1;
	}
	public static void setBukkitData(ItemStack item, String data, Object value) {
		ItemMeta im = item.getItemMeta();
		NamespacedKey spaceKey = new NamespacedKey(plugin, data);
		im.getPersistentDataContainer().set(spaceKey, PersistentDataType.STRING, value+"");
		item.setItemMeta(im);
		
	}

	public static ItemStack getItemfromNMS(net.minecraft.world.item.ItemStack item) {
		try {
			Class<?> CraftItemStack = Class.forName("org.bukkit.craftbukkit."+plugin.server_version+".inventory.CraftItemStack");
			Method method = CraftItemStack.getMethod("asBukkitCopy", net.minecraft.world.item.ItemStack.class); 
			Object object = method.invoke(null, item);
			return (ItemStack) object;
		} catch (Exception e) {
			return null;
		}

	}
	public static net.minecraft.world.item.ItemStack getNMSItem(ItemStack item) {
		try {
			Class<?> CraftItemStack = Class.forName("org.bukkit.craftbukkit."+plugin.server_version+".inventory.CraftItemStack");
			Method method = CraftItemStack.getMethod("asNMSCopy", ItemStack.class); 
			Object object = method.invoke(null, item);
			return (net.minecraft.world.item.ItemStack) object;
		} catch (Exception e) {
			return null;
		}
	}

	
	public static String getBukkitData(ItemStack item, String data) {
		NamespacedKey key = new NamespacedKey(plugin, data);
		ItemMeta itemMeta = item.getItemMeta();
		PersistentDataContainer tagContainer = itemMeta.getPersistentDataContainer();
		if(tagContainer.has(key, PersistentDataType.STRING)) {
			return tagContainer.get(key, PersistentDataType.STRING);
		} else {
			return null;
		}	
	}

	public static void setName(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(plugin.getUtils().color(name));
		item.setItemMeta(meta);
	}

	public static void addLoreLine(ItemStack item, String str) {
		ItemMeta im = item.getItemMeta();
		List<String> lines = new ArrayList<String>();
		if(im.hasLore()) lines = im.getLore();
		lines.add(plugin.getUtils().color(str));
		im.setLore(lines);
		item.setItemMeta(im);	
		
	}
	public static void addGlow(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(Enchantment.LURE, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
	}
	public static ItemStack namedItem(Material mat, String name) {
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(plugin.getUtils().color(name));
		item.setItemMeta(meta);
		return item;
		
	}
	public static ItemStack getSkullfromTexutre(String texture, UUID uuid) {
		if(texture == null || texture.equals("") || texture.length() < 60) return getHeadFromOF(uuid);			
		String originalInput = "{textures:{SKIN:{url:\"http://textures.minecraft.net/texture/"+texture+"\"}}}";
		String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);  
		SkullMeta skullMeta = (SkullMeta)item.getItemMeta();
		GameProfile p2 = new GameProfile(uuid, null);
	    p2.getProperties().put("textures", new Property("textures", encodedString));
		try {
		  Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", new Class[] { GameProfile.class });
		  mtd.setAccessible(true);
		  mtd.invoke(skullMeta, new Object[] { p2 });
		} catch (IllegalAccessException|java.lang.reflect.InvocationTargetException|NoSuchMethodException ex) {
		  ex.printStackTrace();
		} 	  
		item.setItemMeta((ItemMeta)skullMeta);
		return item;	
	}
    public static ItemStack getHeadFromOF(UUID uuid) {
    	ItemStack item = new ItemStack(Material.PLAYER_HEAD);
    	SkullMeta meta = (SkullMeta) item.getItemMeta();
    	meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
    	item.setItemMeta(meta);
    	return item;     	
    }

}
