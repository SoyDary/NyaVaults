package me.dary;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;

import me.dary.Commands.Comandos;
import me.dary.Commands.MapColor;
import me.dary.InventoryHolder.GUIManager;
import me.dary.Utils.Utils;

public class NyaVaults extends JavaPlugin {
	private Utils utils;
	private DataManager datamanager;
	Comandos comandos;
	GUIManager guimanager;
	public Essentials essentials;
	public String server_version;

	public void onEnable() {
		this.utils = new Utils(this);
		this.datamanager = new DataManager(this);
		this.comandos = new Comandos();
		this.guimanager = new GUIManager();
		Bukkit.getConsoleSender().sendMessage("[NyaVaults] Activado uwu");
		Bukkit.getPluginManager().registerEvents(new Eventos(), this);
		Bukkit.getPluginManager().registerEvents(new MigraciónListener(), this);
		if (Bukkit.getPluginManager().isPluginEnabled("Essentials"))
			this.essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
		getCommand("nyavaults").setExecutor(new Comandos());
		getCommand("markmap").setExecutor(new Comandos());
		getCommand("myvault").setExecutor(new Comandos());
		getCommand("mycookies").setExecutor(new Comandos());
		getCommand("mapcolor").setExecutor(new MapColor());
		getCommand("mymaps").setExecutor(new Comandos());
		getCommand("nyavaults").setTabCompleter(new TabComplete());
		loadConfig();
		getDataManager().onStart();
		this.server_version = getServerVersion();

	}

	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("[NyaVaults] Desactivado unu");

	}

	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public Utils getUtils() {
		return this.utils;

	}

	public DataManager getDataManager() {
		return this.datamanager;
	}

	public static NyaVaults getInstance() {
		return (NyaVaults) JavaPlugin.getPlugin(NyaVaults.class);
	}

	public Comandos getCommands() {
		return this.comandos;
	}

	public GUIManager getGUI() {
		return this.guimanager;
	}

	private String getServerVersion() {
		String version;
		try {
			version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		} catch (Exception e) {
			return "unknown";
		}
		return version;
	}

}
