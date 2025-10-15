package me.dary;

import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;


public class MigraciónListener implements Listener {
	
    NyaVaults plugin = NyaVaults.getPlugin(NyaVaults.class);

    
	@EventHandler(priority = EventPriority.LOWEST)
	public void signInteract(PlayerInteractEvent e) {
		if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		Player p = e.getPlayer();
		if(!p.getWorld().getName().equals("Nya")) return;
		Block block = e.getClickedBlock();		
		if(block == null || !(block.getState() instanceof Sign sign)) return;
		e.setCancelled(true);
	
		String name = sign.getSide(Side.FRONT).getLines()[1];
		String uuid = sign.getSide(Side.FRONT).getLines()[3];
		plugin.getDataManager().savePlayerName(UUID.fromString(uuid), name);
		p.sendMessage(name+" §a"+uuid);
		
		TextComponent msg = new TextComponent("§a§l[abrir]");
		ComponentBuilder cba1 = new ComponentBuilder();
		cba1.append("§7§o/galletas " + uuid);
		HoverEvent ev1 = new HoverEvent(HoverEvent.Action.SHOW_TEXT, cba1.create());
		msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/caja "+uuid));
		msg.setHoverEvent(ev1);
		p.spigot().sendMessage(msg);
	}
}