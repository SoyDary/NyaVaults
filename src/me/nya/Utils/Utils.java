package me.nya.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import me.clip.placeholderapi.PlaceholderAPI;
import me.nya.NyaVaults;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class Utils {
	NyaVaults plugin;
  
  Boolean hasPlaceholderApi = Boolean.valueOf(false);
  
  private final Pattern pattern;
  
  
  public void sendMessage(CommandSender s, String message) {
    s.sendMessage(color(message));
  }
    
  public Utils(NyaVaults plugin) {
    this.pattern = Pattern.compile("(?<!\\\\)(#[a-fA-F0-9]{6})");
    this.plugin = plugin;
    Plugin pha = plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI");
    if (pha != null)
      this.hasPlaceholderApi = Boolean.valueOf(pha.isEnabled()); 
  }
  public String color(String text) {
	    String end = "";
	    if (text == null || text == "")
	      return ""; 
	    String text2 = parseColor(text);
	    String[] words = text2.split(Pattern.quote("&#"));
	    if (words.length != 0) {
	      int count = 0;
	      byte b;
	      int i;
	      String[] arrayOfString;
	      for (i = (arrayOfString = words).length, b = 0; b < i; ) {
	        String t = arrayOfString[b];
	        String more = "";
	        if (count != 0)
	          more = "#"; 
	        String t2 = t;
	        t2 = normalColor(t2);
	        t2 = HexColor(String.valueOf(more) + t2);
	        end = String.valueOf(end) + t2;
	        count++;
	        b++;
	      } 
	      return end;
	    } 
	    return text;
	  }

private String parseColor(String text) {
	    if ((((text.length() == 0) ? 1 : 0) | ((text == null) ? 1 : 0)) != 0 || text.length() < 7)
	      return text; 
	    String tedit = text;
	    String text2 = text;
	    for (int i = text2.length() - 1; i > 0; i--) {
	      String c = (new StringBuilder(String.valueOf(text2.charAt(i)))).toString();
	      if (c.contains("#") && i - 1 < 0 && text2.length() >= 7) {
	        String color = String.valueOf(c) + text2.charAt(i + 1) + text2.charAt(i + 2) + text2.charAt(i + 3) + text2.charAt(i + 4) + text2.charAt(i + 5) + text2.charAt(i + 6);
	        if (isColor(color))
	          tedit = addChar(text2, "&", i); 
	      } else if (c.contains("#") && i - 1 > 0 && i + 6 <= text2.length() - 1) {
	        String color = String.valueOf(c) + text2.charAt(i + 1) + text2.charAt(i + 2) + text2.charAt(i + 3) + text2.charAt(i + 4) + text2.charAt(i + 5) + text2.charAt(i + 6);
	        if (isColor(color) && !(new StringBuilder(String.valueOf(text2.charAt(i - 1)))).toString().contains("&"))
	          tedit = addChar(text2, "&", i); 
	      } 
	    } 
	    return tedit;
	  }
private String addChar(String str, String ch, int position) {
	    return String.valueOf(str.substring(0, position)) + ch + str.substring(position);
	  }
private boolean isColor(String text) {
	    String text2 = text;
	    if (text.startsWith("&"))
	      text2 = text.replaceFirst("&", ""); 
	    try {
	      ChatColor.of(text2);
	      return true;
	    } catch (Exception ex) {
	      return false;
	    }     
	  }

private String HexColor(String message) {
	    Matcher matcher = this.pattern.matcher(message);
	    while (matcher.find()) {
	      String color = message.substring(matcher.start(), matcher.end());
	      Boolean isColor = Boolean.valueOf(false);
	      try {
	        ChatColor.of(color);
	        isColor = Boolean.valueOf(true);
	      } catch (Exception exception) {}
	      if (isColor.booleanValue())
	        message = message.replace(color, ""+ChatColor.of(color)); 
	    } 
	    return message;
	  }
	  
private String normalColor(String message) {
	    message = ChatColor.translateAlternateColorCodes('&', message);
	    return message;
	  }
  
  public String clr(String text) {
	    String end = "";
	    if (text == null || text == "")
	      return ""; 
	    String text2 = parseColor(text);
	    String[] words = text2.split(Pattern.quote("&#"));
	    if (words.length != 0) {
	      int count = 0;
	      byte b;
	      int i;
	      String[] arrayOfString;
	      for (i = (arrayOfString = words).length, b = 0; b < i; ) {
	        String t = arrayOfString[b];
	        String more = "";
	        if (count != 0)
	          more = "#"; 
	        String t2 = t;
	        t2 = normalColor(t2);
	        t2 = HexColor(String.valueOf(more) + t2);
	        t2 = color2(t2);
	        end = String.valueOf(end) + t2;
	        count++;
	        b++;
	      } 
	      return end;
	    } 
	    return text;
	  }
  private String color2(String text) {
	    String end = "";
	    if (text == null || text == "")
	      return ""; 
	    String text2 = parseColor(text);
	    String[] words = text2.split(Pattern.quote("#"));
	    if (words.length != 0) {
	      int count = 0;
	      byte b;
	      int i;
	      String[] arrayOfString;
	      for (i = (arrayOfString = words).length, b = 0; b < i; ) {
	        String t = arrayOfString[b];
	        String more = "";
	        if (count != 0)
	          more = "#"; 
	        String t2 = t;
	        t2 = normalColor(t2);
	        t2 = HexColor(String.valueOf(more) + t2);
	        end = String.valueOf(end) + t2;
	        count++;
	        b++;
	      } 
	      return end;
	    } 
	    return text;
	  }


  
  public List<String> color(List<String> lore) {
    List<String> endlore = new ArrayList<>();
    for (String text : lore)
      endlore.add(color(text)); 
    return endlore;
  }
  

  
  public static String toBase64(Inventory inventory, int size) throws IllegalStateException {
      try {
          ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
          BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
          dataOutput.writeInt(size);
          for (int i = 0; i < size; i++) {
              dataOutput.writeObject(inventory.getItem(i));
          }
          
          dataOutput.close();
          return Base64Coder.encodeLines(outputStream.toByteArray());
      } catch (Exception e) {
          throw new IllegalStateException("Unable to save item stacks.", e);
      }
  }
  

  public static Inventory fromBase64(String data) throws IOException {
      try {
          ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
          BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
          Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());

          for (int i = 0; i < inventory.getSize(); i++) {
              inventory.setItem(i, (ItemStack) dataInput.readObject());
          }
          
          dataInput.close();
          return inventory;
      } catch (ClassNotFoundException e) {
          throw new IOException("Unable to decode class type.", e);
      }
  }
  public boolean isPremium(String uuid){
	  boolean bl = false;
	  try {
		  HttpsURLConnection connection = (HttpsURLConnection) new URL("https://api.mojang.com/user/profiles/"+uuid.replaceAll("-", "")+"/names").openConnection();
		  if(connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
			  bl = true;		  
		  }
	  } catch (MalformedURLException  e) {		 
	  } catch (IOException e1) {	  
	  }
	  return bl;	  
  }
  
  public ItemStack vaultItem_main(boolean enchanted) {
	  ItemStack item = new ItemStack(Material.SPYGLASS);

	  ItemMeta im = item.getItemMeta();
	  if(enchanted) im.addEnchant(Enchantment.LURE, 1, false);
	  im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
  	  ArrayList<String> lore = new ArrayList<String>();
  	  lore.add(color("&8&l&m                                "));
  	  lore.add(color("&e??? &7&oCaja principal para guardar"));
   	  lore.add(color("    &7&ocualquier tipo de cositas.."));
   	  lore.add(color("&8&l&m                                "));
      im.setDisplayName(color("     &#FFFF1A&lCaja fuerte principal"));  
  	  im.setLore(lore);	  
	  item.setItemMeta(im);
	  return item;
			  
  }
  public ItemStack vaultItem_cookies(boolean enchanted) {
	  ItemStack item = new ItemStack(Material.COOKIE);
	  ItemMeta im = item.getItemMeta();
	  if(enchanted) im.addEnchant(Enchantment.LURE, 1, false);
	  im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
  	  ArrayList<String> lore = new ArrayList<String>();
  	  lore.add(color("&8&l&m                                  "));
  	  lore.add(color("&#d9b38c??? &7&oUn lugar seguro para guardar"));
   	  lore.add(color("   &7&otodas tus ricas galletas."));
   	  lore.add(color("&8&l&m                                  "));
      im.setDisplayName(color("        &#bf8040&lB??veda de galletas"));  
  	  im.setLore(lore);	  
	  item.setItemMeta(im);
	  return item;
			  
  }
  public ItemStack vaultItem_maps(boolean enchanted) {
	  ItemStack item = new ItemStack(Material.FILLED_MAP);
	  ItemMeta im = item.getItemMeta();
	  if(enchanted) im.addEnchant(Enchantment.LURE, 1, false);
	  im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
  	  ArrayList<String> lore = new ArrayList<String>();
  	  lore.add(color("&8&l&m                                                "));
  	  lore.add(color("&b??? &7&oLugar especial en el que puedes guardar "));
   	  lore.add(color("   &7&omapas que consideras &#b3ecff&oespeciales &7&oy que"));
   	  lore.add(color("   &7&osean inmunes a el paso del tiempo."));
   	  lore.add(color(""));
   	  lore.add(color("&#0066cc?? &7&oSi quieres marcar un mapa como &#b3ecff&oespecial &7&ousa"));
   	  lore.add(color("   &7&oel comando &#1affff/markmap &7&ocon un mapa en la mano."));
   	  lore.add(color("&8&l&m                                                "));
      im.setDisplayName(color("              &#00bfff&lBibilioteca de mapas"));  
	  im.setLore(lore);	  
	  item.setItemMeta(im);
	  net.minecraft.world.item.ItemStack nmsitem = ItemUtils.getNMSItem(item);
	  NBTTagCompound tag = nmsitem.u();
	  if (tag == null)
	      tag = new NBTTagCompound(); 
	  NBTBase nbtbase = tag.c("display");
	  NBTTagCompound tag2 = (NBTTagCompound) nbtbase;
	  if (tag2 == null) tag2 = new NBTTagCompound();   
	  NBTBase nb2 = tag2.c("MapColor");
	  if(nb2 == null) tag2.a("MapColor", 16747546);
	  tag.a("display", tag2);
	  nmsitem.c(tag);
	  ItemStack item2 = ItemUtils.getItemfromNMS(nmsitem);
	  return item2;
			  
  }
  public ItemStack vaultItem_head(String uuid) {  
	  Player p = Bukkit.getPlayer(UUID.fromString(uuid));
	  ItemStack head = new ItemStack(Material.PLAYER_HEAD);  
	  ItemStack item = setTag(head, uuid);
	  SkullMeta skullMeta = (SkullMeta)item.getItemMeta();
	  if(p != null) { 
		  String originalInput = "{textures:{SKIN:{url:\"http://textures.minecraft.net/texture/"+getSkinID(p)+"\"}}}";
			String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
			  GameProfile profile = new GameProfile(UUID.randomUUID(), null);
			  profile.getProperties().put("textures", new Property("textures", encodedString));
			  try {
			    Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", new Class[] { GameProfile.class });
			    mtd.setAccessible(true);
			    mtd.invoke(skullMeta, new Object[] { profile });
			  } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException|NoSuchMethodException ex) {
			    ex.printStackTrace();
			  } 	  
			  //item.setItemMeta((ItemMeta)skullMeta);
		  
	  } else {
		  OfflinePlayer of = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
		  skullMeta.setOwningPlayer(of);
		  //item.setItemMeta(skullMeta);
		  
	  }

	  skullMeta.setDisplayName(color(playerColor(uuid)+plugin.getDataManager().getNameFromUUID(uuid)));
	  item.setItemMeta(skullMeta);
	  return item;
			  
  }
  public ItemStack vaults_headbutton(String uuid) {  
	  ItemStack head = ItemUtils.getSkullfromTexutre(PlaceholderAPI.setPlaceholders(null, "%profile_{"+uuid+"}_skinid%"), UUID.fromString(uuid));
	  ArrayList<String> lore = new ArrayList<String>();
	  SkullMeta meta = (SkullMeta)head.getItemMeta();
	  meta.setDisplayName(color(playerColor(uuid)+plugin.getDataManager().getNameFromUUID(uuid)));
	  lore.add(color("&7")+uuid);
	  meta.setLore(lore);
      OfflinePlayer of = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
	  meta.setOwningPlayer(of);
	  head.setItemMeta((ItemMeta)meta);
	  head = setTag(head, uuid);
	  return head;
			  
  }
  public static ItemStack setTag(ItemStack item, String id) {
	    net.minecraft.world.item.ItemStack nmsitem = ItemUtils.getNMSItem(item);
	    NBTTagCompound tag = nmsitem.u();
	    if (tag == null)
	        tag = new NBTTagCompound(); 
	    tag.a("VaultUUID", id);
	    nmsitem.c(tag);
	    ItemStack item2 = ItemUtils.getItemfromNMS(nmsitem);
	    return item2;
	  }

	public String playerColor(String uuid) {
		String color = "??7";
		File colorsFile = new File("plugins/NekoNya/Players.yml");
		if(colorsFile.exists()) {
			FileConfiguration config = (FileConfiguration)YamlConfiguration.loadConfiguration(colorsFile);
			if(config.getString("Players."+uuid+".ChatColor") != null) {
				color = config.getString("Players."+uuid+".ChatColor");
			} else {
				if(config.getString("Players."+uuid+".NickColor") != null) {
					color = config.getString("Players."+uuid+".NickColor");
					
				} else {
					if(config.getString("Players."+uuid+".SecondChatColor") != null) {
						color = config.getString("Players."+uuid+".SecondChatColor");
					}
					
				}
			}
			
		}
		return color; 
		
	}
	public ItemStack couple_button(String trigger, boolean enchanted) {
		  ItemStack item = new ItemStack(Material.POPPY);
		  ItemMeta im = item.getItemMeta();
		  if(enchanted) im.addEnchant(Enchantment.LURE, 1, false);
		  im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	  	  ArrayList<String> lore = new ArrayList<String>();
	  	  lore.add(color("&8&l&m                                  "));
	  	  lore.add(color("&#ff3333??? &7&oCofre compartido con ??f")+getCoupleName(trigger));
	   	  lore.add(color("&8&l&m                                  "));
	      im.setDisplayName(color("         &#ff99ff&lCofre matrimonial"));  
	  	  im.setLore(lore);	  
		  item.setItemMeta(im);
		  return item;
				  			
	}
	public String getCoupleName(String trigger) {
		String name = null;
		String id = plugin.getDataManager().Players.getString("Players."+trigger+".couple_vaultID");
		String[] players = plugin.getDataManager().Couples.getString("couple_"+id).split(";"); 
		if (players[0].equals(trigger)) {
			name = plugin.getDataManager().getNameFromUUID(players[1]); 
		} else {
			name = plugin.getDataManager().getNameFromUUID(players[0]); 
		}
		return name;
		
	}
	public ItemStack vault_AdminButton() {	
		String textura = "588149e56cde02eee620982884b893634756be9141f2e2777b51cb0a66c31dce";
		String originalInput = "{textures:{SKIN:{url:\"http://textures.minecraft.net/texture/"+textura+"\"}}}";
		String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);  
		SkullMeta skullMeta = (SkullMeta)item.getItemMeta();
		  GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		  profile.getProperties().put("textures", new Property("textures", encodedString));
		  try {
		    Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", new Class[] { GameProfile.class });
		    mtd.setAccessible(true);
		    mtd.invoke(skullMeta, new Object[] { profile });
		  } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException|NoSuchMethodException ex) {
		    ex.printStackTrace();
		  } 	  
		  item.setItemMeta((ItemMeta)skullMeta);
		  ItemMeta im = item.getItemMeta();
		  im.setDisplayName(color("&#ffff00&lMenu de nyadmin"));
		  ArrayList<String> lore = new ArrayList<String>();
		  lore.add(color(""));
		  lore.add(color("&eVer todas las cajas registradas"));
	      im.setLore(lore);
		  item.setItemMeta(im);
		  return item;
		
	}
	
	  public GameProfile getGameProfile(Player p) {
			try {
				Class<?> strClass = (Class<?>) Class.forName("org.bukkit.craftbukkit."+plugin.server_version+".entity.CraftPlayer");
				GameProfile profile = (GameProfile) strClass.cast(p).getClass().getMethod("getProfile").invoke(strClass.cast(p));
				return profile;			
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
				return null;
			}
	  }
	  public String getSkinID(Player p) {
			GameProfile profile = getGameProfile(p);
			if(profile == null) return null;
			Property property = profile.getProperties().get("textures").iterator().next();
			String b64texture = property.getValue();
			byte[] valueDecoded = Base64.getDecoder().decode(b64texture);				
			String id = new String(valueDecoded).split("url")[1].split("\"")[2].split("texture/")[1];
			return id;	
		}
	  public static ItemStack setTags(ItemStack item, Player p) {
		    net.minecraft.world.item.ItemStack nmsitem = ItemUtils.getNMSItem(item);
		    NBTTagCompound tag = nmsitem.u();
		    if (tag == null)
		        tag = new NBTTagCompound(); 
		    tag.a("Brillo", "yellow");
		    tag.a("HDName", "yes");
		    tag.a("Owner", p.getName());
		 
		    tag.a("RepairCost", 99999);
		    nmsitem.c(tag);
		    ItemStack item2 = ItemUtils.getItemfromNMS(nmsitem);
		    return item2;
		  }
	
}
