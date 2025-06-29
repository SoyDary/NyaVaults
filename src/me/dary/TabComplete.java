package me.dary;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
public class TabComplete implements TabCompleter {
  static NyaVaults plugin = NyaVaults.getInstance();
  
  public List<String> onTabComplete(CommandSender s, Command c, String l, String[] args) {
    if (l.equalsIgnoreCase("nyavaults") || l.equalsIgnoreCase("nv")) {
      if (args.length == 1) {
        List<String> commandsList = new ArrayList<>();
        List<String> preCommands = new ArrayList<>();
        commandsList.add("admin");
        commandsList.add("test");
        commandsList.add("seassonitem");
        for (String text : commandsList) {
          if (text.toLowerCase().startsWith(args[0].toLowerCase()))
            preCommands.add(text); 
        } 
        return preCommands;
      } 
    } 
    return null;
  }
}
  