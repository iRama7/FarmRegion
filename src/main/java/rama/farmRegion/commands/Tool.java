package rama.farmRegion.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import rama.farmRegion.ToolBuilder;

import java.util.ArrayList;
import java.util.List;

import static rama.farmRegion.FarmRegion.sendDebug;


public class Tool implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender == Bukkit.getConsoleSender()){
            sendDebug("&eYou can't send this command through console.");
            return false;
        }

        if(args.length == 1 && args[0].equalsIgnoreCase("tool")){
            Player p = (Player) sender;
            ToolBuilder tb = new ToolBuilder();
            p.getInventory().addItem(tb.buildTool());
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou received the selection tool."));
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();
        if(sender.hasPermission("fr.admin")){
            if(args.length == 1){
                commands.add("tool");
                StringUtil.copyPartialMatches(args[0], commands, completions);
                return completions;
            }
        }
        return null;
    }
}
