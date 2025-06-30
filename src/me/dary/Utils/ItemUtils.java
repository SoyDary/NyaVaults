package me.dary.Utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import me.dary.NyaVaults;

public class ItemUtils {

	private static NyaVaults plugin = NyaVaults.getPlugin(NyaVaults.class);

	public static void setBukkitData(ItemStack item, String data, Object value) {
		ItemMeta im = item.getItemMeta();
		NamespacedKey spaceKey = new NamespacedKey(plugin, data);
		im.getPersistentDataContainer().set(spaceKey, PersistentDataType.STRING, value + "");
		item.setItemMeta(im);

	}

	public static String getBukkitData(ItemStack item, String data) {
		NamespacedKey key = new NamespacedKey(plugin, data);
		ItemMeta itemMeta = item.getItemMeta();
		PersistentDataContainer tagContainer = itemMeta.getPersistentDataContainer();
		if (tagContainer.has(key, PersistentDataType.STRING)) {
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
		if (im.hasLore())
			lines = im.getLore();
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

	public static ItemStack getSkullFromTexture(String texture, UUID uuid) {	
		if(texture == null) {
			return getHeadFromOF(uuid);
		}
		if(texture.length() < 65) {
			texture = "http://textures.minecraft.net/texture/"+texture;
		}
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);  
		SkullMeta skullMeta = (SkullMeta)item.getItemMeta();
		PlayerProfile profile = Bukkit.createPlayerProfile(uuid);
		PlayerTextures textures = profile.getTextures();
		try {
			textures.setSkin(new URL("http://textures.minecraft.net/texture/"+texture));
		} catch (MalformedURLException e) {}
		profile.setTextures(textures);
		skullMeta.setOwnerProfile(profile);
		item.setItemMeta(skullMeta);
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
