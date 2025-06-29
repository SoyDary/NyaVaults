package me.dary;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class DataManager {
	
	private static NyaVaults plugin = NyaVaults.getPlugin(NyaVaults.class);
	public File PlayersFile;
	public FileConfiguration Players;
	public File MapsFile;
	public FileConfiguration Maps;
	public File InvFile;
	public FileConfiguration Inv;
	public File CouplesFile;
	public FileConfiguration Couples;
	
	public DataManager(NyaVaults plugin) {
		this.CouplesFile = new File(plugin.getDataFolder(), "Couples.yml");
		this.Couples = (FileConfiguration)YamlConfiguration.loadConfiguration(this.CouplesFile);
		this.PlayersFile = new File("plugins/NyaVaults/Players.yml");
		this.Players = (FileConfiguration)YamlConfiguration.loadConfiguration(this.PlayersFile);
		this.MapsFile = new File("plugins/NyaVaults/Maps.yml");
		this.Maps = (FileConfiguration)YamlConfiguration.loadConfiguration(this.MapsFile);
		this.InvFile = new File("plugins/NyaVaults/Inventories.yml");
		this.Inv = (FileConfiguration)YamlConfiguration.loadConfiguration(this.InvFile);
	}
	public void savePlayerName(Player p) {
		String uuid = p.getUniqueId().toString();
		if (Players.getString("Players."+uuid+".name") == null) {
			Players.set("Players."+uuid+".name", p.getName());
			savePlayers();
		} else {
			if (!p.getName().equals("Players."+uuid+".name")) {
				Players.set("Players."+uuid+".name", p.getName());
				savePlayers();
			} else {
				Players.set("Players."+uuid+".name", p.getName());
				savePlayers();
			}
		}
		
		
	}
	public String getNameFromUUID(String uuid) {
		String name = "none";
		if (Players.getString("Players."+uuid+".name") != null) {
			name = Players.getString("Players."+uuid+".name");
		}
		
		return name;
		
		
	}
	public List<String> registedPlayers(){
		List<String> players = new ArrayList<String>();
		final File folder = new File(plugin.getDataFolder(), "PlayerVaults");
	    for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	        	if(fileEntry.getName() !=null && fileEntry.getName().length() > 30) {
	        	players.add(fileEntry.getName().replaceAll(".yml", ""));
	        	}     	
	        } 
	    }
	    return players;
		
	}

		
	public void createCoupleChest(Player p1, Player p2) {
		int id = Couples.getInt("couples_id");
		Couples.set("couple_"+(id+1), p1.getUniqueId().toString()+";"+p2.getUniqueId().toString());
		File fileMiau = new File(plugin.getDataFolder(), "PlayerVaults/couple_"+(id+1)+".yml");		
	    Players.set("Players."+p1.getUniqueId().toString()+".couple_vaultID", id+1);
		Players.set("Players."+p2.getUniqueId().toString()+".couple_vaultID", id+1);
		savePlayers();
		try {
			fileMiau.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Couples.set("couples_id", id+1);
		saveCouples();
		
	}
	public void deleteCoupleChest(Player p) {
		String id = Players.getString("Players."+p.getUniqueId().toString()+".couple_vaultID");
		String uuid1 = Couples.getString("couple_"+id).split(";")[0];
		String uuid2 = Couples.getString("couple_"+id).split(";")[1];
		Players.set("Players."+uuid1+".couple_vaultID", null);
		Players.set("Players."+uuid2+".couple_vaultID", null);		
		Couples.set("couple_"+id, null);
		File pvf = new File(plugin.getDataFolder(), "PlayerVaults/couple_"+id+".yml");
		pvf.delete();
		savePlayers();
		saveCouples();
		
	}
	public void onStart() {
		File carpetita = new File(plugin.getDataFolder(), "SavedMaps");
		if(!carpetita.exists()) {
			carpetita.mkdir();
		}
		File carpetita2 = new File(plugin.getDataFolder(), "PlayerVaults");
		if(!carpetita2.exists()) {
			carpetita2.mkdir();
		}
		if (Maps.getString("map_count") == null) {
			this.Maps.set("map_count", 30000);
			saveMaps();		
		}
	}

	public void savePlayers() {
		  try {
		    this.Players.save(this.PlayersFile);
		  } catch (IOException e) {
		    e.printStackTrace();
		  } 
	}

	public void saveCouples() {
		  try {
		    this.Couples.save(this.CouplesFile);
		  } catch (IOException e) {
		    e.printStackTrace();
		  } 
	}
	public void saveIntentories() {
		  try {
		    this.Inv.save(this.InvFile);
		  } catch (IOException e) {
		    e.printStackTrace();
		  } 
	}
	public void saveMaps() {
		  try {
		    this.Maps.save(this.MapsFile);
		  } catch (IOException e) {
		    e.printStackTrace();
		  } 
	}
	public void saveFile(File file, FileConfiguration config) {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
}


}