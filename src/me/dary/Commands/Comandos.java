package me.dary.Commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Chest.Type;
import org.bukkit.block.sign.Side;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.google.common.io.Files;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import me.clip.placeholderapi.PlaceholderAPI;
import me.dary.NyaVaults;
import me.dary.Utils.Utils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Comandos implements CommandExecutor {

	private NyaVaults plugin = NyaVaults.getPlugin(NyaVaults.class);

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender s, Command c, String label, String[] a) {
		String l = c.getLabel();
		Player p = s instanceof Player ? (Player) s : null;
		if (s instanceof Player) {
			Player player = (Player) s;
			if (l.equalsIgnoreCase("nyavaults") || l.equalsIgnoreCase("nv")) {
				if (player.hasPermission("nv.admin")) {
					if (a.length != 0) {
						if (a[0].equalsIgnoreCase("admin")) {
							plugin.getGUI().admin(player, 0);

						}
						if (a[0].equalsIgnoreCase("test")) {
							p.sendMessage("" + p.getName());

						}
						if (a[0].equalsIgnoreCase("export")) {
							//export(player);
						}
						if (a[0].equalsIgnoreCase("seassonItem")) {
							seassonItem(player);
						}
						if (a[0].equalsIgnoreCase("check")) {

						}

					} else {
						player.sendMessage("§7Faltan argumentos gatunos");
					}
				}
			}
			if (l.equalsIgnoreCase("myvault") || l.equalsIgnoreCase("caja") || l.equalsIgnoreCase("backpack")) {

				if (a.length == 0) {
					try {
						plugin.getGUI().vault(player, player.getUniqueId().toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					if (player.hasPermission("nv.admin")) {
						OfflinePlayer op = plugin.getUtils().getUser(a[0]);
						if(op != null) {
							try {
								plugin.getGUI().vault(player, op.getUniqueId().toString());
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
							p.sendMessage("§cEste no es un jugador registrado.");
						}
						return true;

					} else {
						player.sendMessage("§e§oPermisos insuficientes!");
					}
				}

			}
			if (l.equalsIgnoreCase("mycookies") || l.equalsIgnoreCase("galletas")) {

				if (a.length == 0) {
					try {
						plugin.getGUI().cookies(player, player.getUniqueId().toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					if (player.hasPermission("nv.admin")) {
						if (a[0].contains("-")) {
							if (plugin.getDataManager().Players.getString("Players." + a[0]) != null) {
								try {
									plugin.getGUI().cookies(player, a[0]);
								} catch (IOException e) {
									e.printStackTrace();
								}
							} else {
								player.sendMessage("§cEste no es un jugador registrado");
							}
						} else {
							Player op = Bukkit.getPlayer(a[0]);
							if (op != null) {
								try {
									plugin.getGUI().cookies(player, op.getUniqueId().toString());
								} catch (IOException e) {
									e.printStackTrace();
								}
							} else {
								String uuid = null;
								String uuid2 = null;
								for (String key : plugin.getDataManager().Players.getConfigurationSection("Players")
										.getKeys(false)) {
									if (plugin.getDataManager().Players.getString("Players." + key + ".name")
											.equalsIgnoreCase(a[0])) {
										if (uuid == null)
											uuid = key;
										else
											uuid2 = key;
									}
								}
								if (uuid2 == null) {
									if (uuid != null) {
										try {
											plugin.getGUI().cookies(player, uuid);
										} catch (IOException e) {
											e.printStackTrace();
										}

									} else {
										player.sendMessage("§cEste no es un jugador registrado");
									}
								} else {
									if (plugin.getUtils().isPremium(uuid))
										uuid = uuid + " §e(Premium)";
									if (plugin.getUtils().isPremium(uuid2))
										uuid2 = uuid2 + " §e(Premium)";
									String realName = plugin.getDataManager().Players
											.getString("Players." + uuid + ".name");
									player.sendMessage(plugin.getUtils()
											.color(" &#33ccff&l&oHay 2 cuentas registradas con este nombre:"));
									player.sendMessage("");
									TextComponent end = new TextComponent();
									TextComponent t1 = new TextComponent("§9↳ §b" + realName + "§8: §7" + uuid);
									ComponentBuilder cba1 = new ComponentBuilder();
									cba1.append("§7§o/galletas " + uuid.split(" ")[0]);
									HoverEvent ev1 = new HoverEvent(HoverEvent.Action.SHOW_TEXT, cba1.create());
									t1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
											"/galletas " + uuid.split(" ")[0]));
									t1.setHoverEvent(ev1);
									TextComponent t2 = new TextComponent("§9↳ §b" + realName + "§8: §7" + uuid2);
									ComponentBuilder cba2 = new ComponentBuilder();
									cba2.append("§7§o/galletas " + uuid2.split(" ")[0]);
									HoverEvent ev2 = new HoverEvent(HoverEvent.Action.SHOW_TEXT, cba2.create());
									t2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
											"/galletas " + uuid2.split(" ")[0]));
									t2.setHoverEvent(ev2);
									end.addExtra((BaseComponent) t1);
									end.addExtra("\n");
									end.addExtra((BaseComponent) t2);
									player.spigot().sendMessage(end);
								}

							}
						}
					} else {
						player.sendMessage("§e§oPermisos insuficientes!");
					}
				}

			}
			if (l.equalsIgnoreCase("marrychest") || l.equalsIgnoreCase("mc")) {

				if (a.length == 0) {
					if (hasCouple(player.getUniqueId().toString())) {
						try {
							plugin.getGUI().couple(player, player.getUniqueId().toString());
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						player.sendMessage("§cNo tienes un cofre matrimonial registrado");
					}
				} else {
					if (player.hasPermission("nv.admin")) {
						if (a[0].contains("-")) {
							if (plugin.getDataManager().Players.getString("Players." + a[0]) != null) {
								if (hasCouple(a[0])) {
									try {
										plugin.getGUI().couple(player, a[0]);
									} catch (IOException e) {
										e.printStackTrace();
									}
								} else {
									player.sendMessage("§cEste jugador notiene un cofre matrimonial registrado!");

								}
							} else {
								player.sendMessage("§cEste no es un jugador registrado");
							}
						} else {
							Player op = Bukkit.getPlayer(a[0]);
							if (op != null) {
								if (hasCouple(op.getUniqueId().toString())) {
									try {
										plugin.getGUI().couple(player, op.getUniqueId().toString());
									} catch (IOException e) {
										e.printStackTrace();
									}
								} else {
									player.sendMessage("§cEste jugador no tiene un cofre matrimonial registrado!");

								}
							} else {
								String uuid = null;
								String uuid2 = null;
								for (String key : plugin.getDataManager().Players.getConfigurationSection("Players")
										.getKeys(false)) {
									if (plugin.getDataManager().Players.getString("Players." + key + ".name")
											.equalsIgnoreCase(a[0])) {
										if (uuid == null)
											uuid = key;
										else
											uuid2 = key;
									}
								}
								if (uuid2 == null) {
									if (uuid != null) {
										if (hasCouple(uuid)) {
											try {
												plugin.getGUI().couple(player, uuid);
											} catch (IOException e) {
												e.printStackTrace();
											}
										} else {
											player.sendMessage("§cEste no es un jugador registrado");
										}

									} else {
										player.sendMessage("§cEste no es un jugador registrado");
									}
								} else {
									if (plugin.getUtils().isPremium(uuid))
										uuid = uuid + " §e(Premium)";
									if (plugin.getUtils().isPremium(uuid2))
										uuid2 = uuid2 + " §e(Premium)";
									String realName = plugin.getDataManager().Players
											.getString("Players." + uuid + ".name");
									player.sendMessage(plugin.getUtils()
											.color(" &#33ccff&l&oHay 2 cuentas registradas con este nombre:"));
									player.sendMessage("");
									TextComponent end = new TextComponent();
									TextComponent t1 = new TextComponent("§9↳ §b" + realName + "§8: §7" + uuid);
									ComponentBuilder cba1 = new ComponentBuilder();
									cba1.append("§7§o/marrychest " + uuid.split(" ")[0]);
									HoverEvent ev1 = new HoverEvent(HoverEvent.Action.SHOW_TEXT, cba1.create());
									t1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
											"/marrychest " + uuid.split(" ")[0]));
									t1.setHoverEvent(ev1);
									TextComponent t2 = new TextComponent("§9↳ §b" + realName + "§8: §7" + uuid2);
									ComponentBuilder cba2 = new ComponentBuilder();
									cba2.append("§7§o/marrychest " + uuid2.split(" ")[0]);
									HoverEvent ev2 = new HoverEvent(HoverEvent.Action.SHOW_TEXT, cba2.create());
									t2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
											"/marrychest " + uuid2.split(" ")[0]));
									t2.setHoverEvent(ev2);
									end.addExtra((BaseComponent) t1);
									end.addExtra("\n");
									end.addExtra((BaseComponent) t2);
									player.spigot().sendMessage(end);
								}

							}
						}
					} else {
						player.sendMessage("§e§oPermisos insuficientes!");
					}
				}

			}
			if (l.equalsIgnoreCase("mymaps") || l.equalsIgnoreCase("mapas")) {
				if (a.length == 0) {
					try {
						plugin.getGUI().maps(player, player.getUniqueId().toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					if (player.hasPermission("nv.admin")) {
						if (a[0].contains("-")) {
							if (plugin.getDataManager().Players.getString("Players." + a[0]) != null) {
								try {
									plugin.getGUI().maps(player, a[0]);
								} catch (IOException e) {
									e.printStackTrace();
								}
							} else {
								player.sendMessage("§cEste no es un jugador registrado");
							}
						} else {
							Player op = Bukkit.getPlayer(a[0]);
							if (op != null) {
								try {
									plugin.getGUI().maps(player, op.getUniqueId().toString());
								} catch (IOException e) {
									e.printStackTrace();
								}
							} else {
								String uuid = null;
								String uuid2 = null;
								for (String key : plugin.getDataManager().Players.getConfigurationSection("Players")
										.getKeys(false)) {
									if (plugin.getDataManager().Players.getString("Players." + key + ".name")
											.equalsIgnoreCase(a[0])) {
										if (uuid == null)
											uuid = key;
										else
											uuid2 = key;
									}
								}
								if (uuid2 == null) {
									if (uuid != null) {
										try {
											plugin.getGUI().maps(player, uuid);
										} catch (IOException e) {
											e.printStackTrace();
										}

									} else {
										player.sendMessage("§cEste no es un jugador registrado");
									}
								} else {
									if (plugin.getUtils().isPremium(uuid))
										uuid = uuid + " §e(Premium)";
									if (plugin.getUtils().isPremium(uuid2))
										uuid2 = uuid2 + " §e(Premium)";
									String realName = plugin.getDataManager().Players
											.getString("Players." + uuid + ".name");
									player.sendMessage(plugin.getUtils()
											.color(" &#33ccff&l&oHay 2 cuentas registradas con este nombre:"));
									player.sendMessage("");
									TextComponent end = new TextComponent();
									TextComponent t1 = new TextComponent("§9↳ §b" + realName + "§8: §7" + uuid);
									ComponentBuilder cba1 = new ComponentBuilder();
									cba1.append("§7§o/mapas " + uuid.split(" ")[0]);
									HoverEvent ev1 = new HoverEvent(HoverEvent.Action.SHOW_TEXT, cba1.create());
									t1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
											"/mapas " + uuid.split(" ")[0]));
									t1.setHoverEvent(ev1);
									TextComponent t2 = new TextComponent("§9↳ §b" + realName + "§8: §7" + uuid2);
									ComponentBuilder cba2 = new ComponentBuilder();
									cba2.append("§7§o/mapas " + uuid2.split(" ")[0]);
									HoverEvent ev2 = new HoverEvent(HoverEvent.Action.SHOW_TEXT, cba2.create());
									t2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
											"/mapas " + uuid2.split(" ")[0]));
									t2.setHoverEvent(ev2);
									end.addExtra((BaseComponent) t1);
									end.addExtra("\n");
									end.addExtra((BaseComponent) t2);
									player.spigot().sendMessage(end);
								}

							}
						}
					} else {
						player.sendMessage("§e§oPermisos insuficientes!");
					}
				}

			}

			if (l.equalsIgnoreCase("markmap") || l.equalsIgnoreCase("mm")) {
				if (player.hasPermission("nv.markmap")) {
					MarkMap(player);
				} else {
					player.sendMessage("§cNo tienes permisos");
				}

			}

		} else {
			s.sendMessage("No eres un jugador");
		}

		return true;
	}

	public String nf(String txt) {
		int x = Integer.valueOf(txt);
		return NumberFormat.getIntegerInstance().format(x);
	}

	/*
	public void export(Player p) {
		List<String> players = plugin.getDataManager().registedPlayers();
	    generarCoordenadas();
	}
	
    public void generarCoordenadas() {
    	int filasZ = 20;
        int espacioX = 2;
        int espacioEntreCeldas = 1;

        List<String> players = plugin.getDataManager().registedPlayers();
        for (int i = 0; i < players.size(); i++) {
        	String uuid = players.get(i);
        	String name = plugin.getDataManager().getNameFromUUID(uuid);
            int columnaX = i / filasZ;
            int filaZ = i % filasZ;

            int baseX = (espacioX + espacioEntreCeldas) * columnaX;

            int x1 = baseX;
            int x2 = baseX + 1;
            int z = filaZ;
            
            System.out.println("Celda " +name+" "+ (i + 1) + ": (" + x1 + ", " + z + ") - (" + x2 + ", " + z + ")");
            signID(x1, 125, z, name, ""+uuid);
            
            chest(x1, 123, z, Type.LEFT,"vault");
            chest(x2, 123, z, Type.RIGHT,"vault");
            try {
            	setContent(x1, 123, z, plugin.getGUI().genericVault(null, uuid).getContents());
			} catch (IOException e) {
				e.printStackTrace();
			}
            
            chest(x1, 122, z, Type.LEFT,"cookies");
            chest(x2, 122, z, Type.RIGHT,"cookies");
            try {
            	setContent(x1, 122, z, plugin.getGUI().genericCookies(null, uuid).getContents());
			} catch (IOException e) {
				e.printStackTrace();
			}
            
            chest(x1, 121, z, Type.LEFT,"maps");
            chest(x2, 121, z, Type.RIGHT,"maps");
            try {
            	setContent(x1, 121, z, plugin.getGUI().genericMaps(null, uuid).getContents());
			} catch (IOException e) {
				e.printStackTrace();
			}

            
        }
    }
    
    public void setContent(int x, int y, int  z, ItemStack[] contents) {
    	Block block = Bukkit.getWorld("Nya").getBlockAt(x, y, z);
    	Chest chest = (Chest) block.getState();
    	chest.getInventory().setContents(contents);
    	
    }
    public void chest(int x, int y, int  z, Type type, String ID) {
    	Block block = Bukkit.getWorld("Nya").getBlockAt(x, y, z);
    
    	block.setType(Material.CHEST);
    	Chest chest = (Chest) block.getState();
    	chest.setCustomName("nvchest-"+ID);
        BlockData blockData = chest.getBlockData();
        org.bukkit.block.data.type.Chest chestData = (org.bukkit.block.data.type.Chest) blockData;   	
        chestData.setType(type);
        chest.setBlockData(chestData);
        chest.update();
    }
    
    public void signID(int x, int y, int  z, String name, String uuid) {
    	Block block = Bukkit.getWorld("Nya").getBlockAt(x, y, z);
    
    	block.setType(Material.BIRCH_SIGN);
    	Sign sign = (Sign) block.getState();
    	
    	sign.getSide(Side.FRONT).setLine(0, ""+x+" "+z);
    	sign.getSide(Side.FRONT).setLine(1, ""+name);
    	sign.getSide(Side.FRONT).setLine(3, ""+uuid);
        BlockData blockData = sign.getBlockData();
        org.bukkit.block.data.type.Sign signData = (org.bukkit.block.data.type.Sign) blockData;   	
        signData.setRotation(BlockFace.NORTH);
        sign.setBlockData(signData);
        sign.update();
    }
    */

	
	public void seassonItem(Player p) {
		ItemStack item = new ItemStack(Material.GOLDEN_AXE);
		item = Utils.setTags(item, p);
		ItemMeta im = item.getItemMeta();
		im.addEnchant(Enchantment.SILK_TOUCH, 3, false);
		//im.addEnchant(Enchantment.EFFICIENCY, 1, false);
		im.setUnbreakable(true);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		String mine_block = PlaceholderAPI.setPlaceholders(p, "%statistic_mine_block%");
		String mine_block_diamond = PlaceholderAPI.setPlaceholders(p, "%statistic_mine_block:diamond_ore%");
		String mine_block_netherite = PlaceholderAPI.setPlaceholders(p, "%statistic_mine_block:ancient_debris%");
		String fish_caught = PlaceholderAPI.setPlaceholders(p, "%statistic_fish_caught%");
		String craft_item = PlaceholderAPI.setPlaceholders(p, "%statistic_craft_item%");
		String mob_kills = PlaceholderAPI.setPlaceholders(p, "%statistic_mob_kills%");
		String play_time = PlaceholderAPI.setPlaceholders(p, "%javascript_playtime%");
		String animal_bred = PlaceholderAPI.setPlaceholders(p, "%statistic_animals_bred%");
		String deaths = PlaceholderAPI.setPlaceholders(p, "%statistic_deaths%");
		String trade_villager = PlaceholderAPI.setPlaceholders(p, "%statistic_traded_with_villager%");
		String logros = PlaceholderAPI.setPlaceholders(p, "%equipo_logro_done%");
		String team_name = PlaceholderAPI.setPlaceholders(p, "%equipo_name%");
		String team_line = PlaceholderAPI.setPlaceholders(p,
				"%equipo_signcolor%» %equipo_color%Equipo: %equipo_prefix%%equipo_displayname%&r %equipo_signcolor%«");
		Date date = new Date(p.getFirstPlayed());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		String f1 = formatter.format(date).split(" ")[0];
		String f2 = formatter.format(date).split(" ")[1];
		String[] fecha = f1.split("/");
		String[] hora = f2.split(":");
		String nyafecha = "&#ffff99" + fecha[0] + "&#ffff1a/&#ffff99" + fecha[1] + "&#ffff1a/&#ffff99" + fecha[2]
				+ " &#b3ffec| &#ffd966" + hora[0] + "&6:&#ffd966" + hora[1] + "&6:&#ffd966" + hora[2];
		ArrayList<String> lore = new ArrayList<String>();
		im.setDisplayName(plugin.getUtils().clr("#FF99FF» &e" + p.getName() + " &#FFFF99&lｓ&#FFFF33⁴ #FF99FF«"));
		lore.add(plugin.getUtils().clr("&#FFBF80~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"));
		lore.add(plugin.getUtils().clr("&#FFFF80&lEstadisticas de la temporada 4:"));
		lore.add(plugin.getUtils().clr(""));
		lore.add(plugin.getUtils().clr("&a⛏ &7Bloques minados: &#99FF99" + nf(mine_block)));
		lore.add(plugin.getUtils().clr("   &8↳ &#ccccccDiamante: &#e6ff99" + nf(mine_block_diamond)));
		lore.add(plugin.getUtils().clr("   &8↳ &#ccccccNetherita: &#e6ff99" + nf(mine_block_netherite)));
		lore.add(plugin.getUtils().clr("&#1AFFFF🎣 &7Peces pescados: &#B3FFEC" + nf(fish_caught)));
		lore.add(plugin.getUtils().clr("&#bf8040⌂ &7Items crafteados: &#d9b38c" + nf(craft_item)));
		lore.add(plugin.getUtils().clr("&#FF3333🗡 &7Mobs asesinados: &#FF9999" + nf(mob_kills)));
		lore.add(plugin.getUtils().clr("&e⏳ &7Tiempo jugado: " + play_time));
		lore.add(plugin.getUtils().clr("&#FF80FF❤ &7Animales criados: &#FFB3FF" + nf(animal_bred)));
		lore.add(plugin.getUtils().clr("&#4D4D4D☠ &7Muertes totales: &#CCCCCC" + deaths));
		lore.add(plugin.getUtils().clr("&#009900⬙ &7Tradeos con aldeanos: &#4DFF4D" + nf(trade_villager)));
		lore.add(plugin.getUtils().clr("&#ffff00✭ &7Logros conseguidos: &#99ffff" + logros + "&#009999/&#99ffff91"));
		lore.add(plugin.getUtils().clr("&e"));
		if (team_name != "")
			lore.add(plugin.getUtils().clr(team_line));
		lore.add(plugin.getUtils().clr("&#FFBF80~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"));
		lore.add(plugin.getUtils().clr("&#8cff1a☘ &ePrimer registro: " + nyafecha + " &#8cff1a☘"));
		lore.add(plugin.getUtils().clr(""));
		lore.add(plugin.getUtils().clr("&#ff99ff&oGracias por acompañarnos &#ff0000❣"));
		im.setLore(lore);
		item.setItemMeta(im);
		sendItem(p, item);
		p.sendMessage(plugin.getUtils().clr(
				"&#ffff00&lK&#ffff33&lo&#ffff4d&lk&#ffff66&lo&#ffff80&lr&#ffff99&li&#ff8000&lC&#ff9933&lr&#ffb366&la&#ffbf80&lf&#ffcc99&l&lt &b» &#ffff99Recibiste un item especial por el final de temporada!"));
		p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 10);

	}

	public void sendItem(final Player p, final ItemStack item) {
		Boolean isFull = Boolean
				.valueOf(!Arrays.<ItemStack>asList(p.getInventory().getStorageContents()).contains(null));
		if (checkspace(p, item, item.getAmount())) {
			p.getInventory().addItem(new ItemStack[] { item.clone() });
		} else if (isFull.booleanValue()) {
			Bukkit.getScheduler().runTaskLater((Plugin) this.plugin, new Runnable() {
				public void run() {
					p.getWorld().dropItem(p.getLocation(), item);
				}
			}, 1L);
		} else {
			p.getInventory().addItem(new ItemStack[] { item });
		}
	}

	public boolean checkspace(Player p, ItemStack item, int amount) {
		int emount = amount;
		for (ItemStack i : p.getInventory()) {
			if (i != null && !i.getType().equals(Material.AIR)) {
				ItemStack ii = i.clone();
				ItemStack ii2 = item.clone();
				ii.setAmount(1);
				ii2.setAmount(1);
				if ((ii.equals(ii2) || ii == ii2) && i.getAmount() < i.getMaxStackSize()) {
					int a = i.getMaxStackSize() - i.getAmount();
					if (emount - a <= 0)
						return true;
					emount -= a;
				}
			}
		}
		if (emount <= 0)
			return true;
		Boolean isFull = Boolean
				.valueOf(!Arrays.<ItemStack>asList(p.getInventory().getStorageContents()).contains(null));
		if (!isFull.booleanValue())
			return true;
		return false;
	}

	public boolean hasCouple(String uuid) {
		boolean bl = false;
		if (plugin.getDataManager().Players.getString("Players." + uuid + ".couple_vaultID") != null) {
			bl = true;

		}
		return bl;
	}

	public void setMapColor(Player p, String arg) {
		ItemStack hand = p.getInventory().getItemInMainHand();
		if (hand.getType().toString().equals("FILLED_MAP")) {

		}
	}

	public void MarkMap(Player p) {
		ItemStack item = getSelectedItem(p);
		Integer id = -1;
		String saved = "";

		if (item != null && item.getType().toString().equals("FILLED_MAP")) {
			NBTItem nbt = new NBTItem(item);
			saved = nbt.getString("SavedMap");
			id = nbt.getInteger("map");
			File miau = new File("KokoriCraft/data/map_" + id + ".dat");
			if (!saved.contains("true")) {
				if (miau.exists()) {
					if (plugin.getDataManager().Maps.get("SavedMaps.map_" + id) == null) {
						int new_map_count = plugin.getDataManager().Maps.getInt("map_count") + 1;
						plugin.getDataManager().Maps.set("map_count", new_map_count);
						p.sendMessage("§bMapa guardado!");
						plugin.getDataManager().Maps.set("SavedMaps.map_" + id + ".newID", new_map_count);
						plugin.getDataManager().saveMaps();
						try {
							copyMap("" + id, String.valueOf(new_map_count));
						} catch (IOException e) {
							p.sendMessage(plugin.getUtils()
									.color("&b▆ &#ff9933¡Hubo un problema intentando marcar este mapa!"));
							e.printStackTrace();
						}
						setTag(new_map_count, p);

					} else {
						setTag(plugin.getDataManager().Maps.getInt("SavedMaps.map_" + id + ".newID"), p);

					}

				} else {
					p.sendMessage(plugin.getUtils().color("&b▆ &#a64dff¡Es muy pronto para guardar este mapa!"));
					p.sendMessage(plugin.getUtils().color("   &#e699ffIntentalo en unos minutos..."));
				}
			} else {
				p.sendMessage(plugin.getUtils().color("&b▆ &#a64dffEste mapa ya fue guardado."));
			}

		} else {
			p.sendMessage(plugin.getUtils().color("&b▆ &#a64dff¡Debes tener un mapa en la mano!"));

		}

	}

	public ItemStack getSelectedItem(Player p) {
		ItemStack mainHand = p.getInventory().getItemInMainHand();
		ItemStack offHand = p.getInventory().getItemInOffHand();
		ItemStack SelectedItem = new ItemStack(Material.AIR);
		if (!mainHand.getType().toString().equalsIgnoreCase("AIR")) {
			SelectedItem = mainHand;
		} else {
			if (!offHand.getType().toString().equalsIgnoreCase("AIR")) {
				SelectedItem = offHand;
			} else {
			}
		}
		return SelectedItem;

	}

	public void copyMap(String oldID, String newID) throws IOException {

		File origen;
		origen = new File("KokoriCraft/data/map_" + oldID + ".dat");
		Path destino1 = Paths.get("KokoriCraft/data/map_" + newID + ".dat");
		Path destino2 = Paths.get("plugins/NyaVaults/SavedMaps/map_" + newID + ".dat");
		Files.copy(origen, destino1.toFile());
		Files.copy(origen, destino2.toFile());
	}

	public void setTag(int newID, Player p) {
		ItemStack SelectedItem = getSelectedItem(p);
		NBTItem nbt = new NBTItem(SelectedItem);
		NBTCompound comp = nbt.getOrCreateCompound("display");
		if (!comp.hasTag("MapColor"))
			comp.setInteger("MapColor", 16777215);
		nbt.setString("SavedMap", "true");
		nbt.mergeCompound(comp);
		ItemStack item2 = nbt.getItem();

		/*
		 * net.minecraft.world.item.ItemStack nmsitem =
		 * ItemUtils.getNMSItem(SelectedItem); NBTTagCompound tag = nmsitem.u(); if (tag
		 * == null) tag = new NBTTagCompound(); NBTBase nbtbase = tag.c("display");
		 * NBTTagCompound tag2 = (NBTTagCompound) nbtbase; if (tag2 == null) tag2 = new
		 * NBTTagCompound(); NBTBase nb2 = tag2.c("MapColor"); if(nb2 == null)
		 * tag2.a("MapColor", 16747546); tag.a("display", tag2); tag.a("map", newID);
		 * tag.a("SavedMap", "true"); nmsitem.c(tag); ItemStack item2 =
		 * ItemUtils.getItemfromNMS(nmsitem);
		 */
		ItemMeta im = item2.getItemMeta();

		if (im.hasLore()) {
			List<String> lore = item2.getItemMeta().getLore();
			lore.add(plugin.getUtils().color(
					"&#ffff00&lK&#ffff33&lo&#ffff4d&lk&#ffff66&lo&#ffff80&lr&#ffff99&li&#ff8000&lC&#ff9933&lr&#ffb366&la&#ffbf80&lf&#ffcc99&l&lt &#ffffff&lｓ&#ffffb3⁷"));
			im.setLore(lore);
			item2.setItemMeta(im);
		} else {

			ArrayList<String> lore = new ArrayList<String>();

			lore.add(plugin.getUtils().color(
					"&#ffff00&lK&#ffff33&lo&#ffff4d&lk&#ffff66&lo&#ffff80&lr&#ffff99&li&#ff8000&lC&#ff9933&lr&#ffb366&la&#ffbf80&lf&#ffcc99&l&lt &#ffffff&lｓ&#ffffb3⁷"));
			im.setLore(lore);
			item2.setItemMeta(im);
		}
		ReplaceSelectedItem(p, item2);
		p.sendMessage(plugin.getUtils().color("&b▆ &#a6ff4d¡Mapa marcado correctamente!"));

	}

	public void ReplaceSelectedItem(Player p, ItemStack item) {
		if (!p.getInventory().getItemInMainHand().getType().toString().equalsIgnoreCase("AIR")) {
			p.getInventory().setItemInMainHand(item);

		} else {
			p.getInventory().setItemInOffHand(item);

		}

	}

}
