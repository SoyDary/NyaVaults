package me.dary.Commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import me.dary.NyaVaults;

public class MapColor implements CommandExecutor {

	NyaVaults plugin = NyaVaults.getInstance();
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String l, String[] a) {
		if(!(s instanceof Player p)) return true;
		ItemStack hand = p.getInventory().getItemInMainHand();
		if(hand == null) {
			p.sendMessage(plugin.getUtils().color("&b▆ &#a64dff¡Debes tener un mapa en la mano para usar esto!"));
			return true;
		}	
		if(a.length == 0) {
			p.sendMessage(plugin.getUtils().color("&b▆ &#ffb366Usa &#ffff99/mapcolor <color hex>"));
			return true;
		}
		String hex = a[0];
		if(!hex.matches("^#([A-Fa-f0-9]{6})$")) {
			p.sendMessage(plugin.getUtils().color("&b▆ &c¡Este no es un volor válido! &7&o(Usa /rgb para ver colores)"));
			return true;
		}
		hex = hex.substring(1);
		NBTItem nbt = new NBTItem(hand);
		NBTCompound comp = nbt.getOrCreateCompound("display");
		comp.setInteger("MapColor", Integer.parseInt(hex, 16));
		nbt.mergeCompound(comp);
		p.getInventory().setItemInMainHand(nbt.getItem());
		p.playSound(p.getLocation(), Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 2, 1);
		return true;
	}

}
