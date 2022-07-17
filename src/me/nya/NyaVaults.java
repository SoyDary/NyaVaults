package me.nya;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.nya.InventoryHolder.GUIManager;
import me.nya.Utils.Utils;

public class NyaVaults extends JavaPlugin{
	private Utils utils;
	private DataManager datamanager;
	Comandos comandos;
	GUIManager guimanager;
	public String server_version;
	
	


	public void onEnable() {
		this.utils = new Utils(this);
		this.datamanager = new DataManager(this);
		this.comandos = new Comandos();
		this.guimanager = new GUIManager();
		Bukkit.getConsoleSender().sendMessage("[NyaVaults] Activado uwu");
		Bukkit.getPluginManager().registerEvents(new Eventos(), (Plugin)this);
		if(Bukkit.getPluginManager().getPlugin("ActionsChat") != null) {
			getCommand("marrychest").setExecutor(new Comandos());
			Bukkit.getPluginManager().registerEvents(new MarryListener(), (Plugin)this);
		}	
		getCommand("nyavaults").setExecutor(new Comandos());
		getCommand("markmap").setExecutor(new Comandos());
		getCommand("myvault").setExecutor(new Comandos());
		getCommand("mycookies").setExecutor(new Comandos());
		getCommand("mymaps").setExecutor(new Comandos());
		getCommand("nyavaults").setTabCompleter(new TabComplete());
		loadConfig();
		getDataManager().onStart();
		this.server_version = getServerVersion();
		

	
	}
    public boolean hasAC() {
    	boolean bl = false;
		if(Bukkit.getPluginManager().getPlugin("ActionsChat") != null) {
			bl = true;
		}
		return bl;
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
		return (NyaVaults)JavaPlugin.getPlugin(NyaVaults.class);
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
			version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
		} catch(Exception e) {
			return "unknown";
		}
		return version;
	}
	
	
}
