package rama.farmRegion.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import rama.farmRegion.FarmRegion;
import rama.farmRegion.ToolBuilder;
import rama.farmRegion.regionManager.RegionAdder;
import rama.farmRegion.regionManager.Types;

import java.util.ArrayList;
import java.util.List;

import static rama.farmRegion.FarmRegion.*;
import static rama.farmRegion.ToolListener.point1;
import static rama.farmRegion.ToolListener.point2;

public class MainCommand implements TabExecutor {

    private String region_type;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission("fr.admin")){
            return false;
        }

        if(sender == Bukkit.getConsoleSender()){
            sendDebug("&eYou can't send this command through console.");
            return false;
        }

        if(args.length == 1 && args[0].equalsIgnoreCase("tool")){
            Player p = (Player) sender;
            ToolBuilder tb = new ToolBuilder();
            p.getInventory().addItem(tb.buildTool());
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou received the selection tool."));
        }

        if(args.length == 1 && args[0].equalsIgnoreCase("reload")){
            plugin.reloadConfig();
            rm.unloadRegions();
            rm.loadRegions();
            guardiansManager.writeGuardians(guardiansConfig);
            guardiansManager.unloadGuardians();
            guardiansManager.retrieveGuardians(guardiansConfig);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aReloaded config & regions!"));
        }

        if(args.length >= 1 && args[0].equalsIgnoreCase("addRegion")){

            if(args.length != 2){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are missing arguments!"));
                return false;
            }

            if(point1 == null || point2 == null){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cMake sure to select some points first!"));
                return false;
            }

            // /fr addRegion (Type)
            region_type = args[1];
            RegionAdder regionAdder = new RegionAdder();
            Types type = new Types();
            switch (region_type){
                case "WHEAT":
                    regionAdder.addRegion(type.wheat(), point1, point2);
                    break;
                case "CARROT":
                    regionAdder.addRegion(type.carrot(), point1, point2);
                    break;
                case "BEETROOT":
                    regionAdder.addRegion(type.beetroot(), point1, point2);
                    break;
                case "POTATO":
                    regionAdder.addRegion(type.potato(), point1, point2);
                    break;
                default:
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cInvalid type."));
                    return false;
            }
            point1 = null; point2 = null;
            rm.loadRegions();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aRegion added successfully!"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eRemember to edit the config files."));
        }

        if(args.length == 1 && args[0].equalsIgnoreCase("debugGuardians")){
            guardiansManager.killArmorStandsWithName("FarmRegion-Guardian");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eTrying to remove guardians right now."));
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();
        if(sender.hasPermission("fr.admin")) {
            if (args.length == 1) {
                commands.add("tool");
                commands.add("addRegion");
                commands.add("reload");
                commands.add("debugGuardians");
                StringUtil.copyPartialMatches(args[0], commands, completions);
                return completions;
            }
            if (args.length == 2 && args[0].equalsIgnoreCase("addRegion")) {
                commands.add("(Region type)");
                commands.add("WHEAT");
                commands.add("CARROT");
                commands.add("POTATO");
                commands.add("BEETROOT");
                StringUtil.copyPartialMatches(args[1], commands, completions);
                return completions;
            }
        }

        return null;
    }
}
