package rama.farmRegion.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import rama.farmRegion.regionManager.RegionAdder;
import rama.farmRegion.regionManager.RegionType;
import rama.farmRegion.regionManager.Types;

import java.util.ArrayList;
import java.util.List;

import static rama.farmRegion.FarmRegion.sendDebug;

public class CreateRegion implements TabExecutor {

    private String region_type;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender == Bukkit.getConsoleSender()){
            sendDebug("&eYou can't send this command through console.");
            return false;
        }

        if(args[0].equalsIgnoreCase("addRegion")){
            if(args.length != 2){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are missing arguments!"));
            }
            // /fr addRegion (Type)
            Player player = (Player) sender;
            region_type = args[1];
            RegionAdder regionAdder = new RegionAdder();
            Types type = new Types();
            switch (region_type){
                case "WHEAT":
                    regionAdder.addRegion(type.wheat());
                    break;
                case "CARROT":
                    regionAdder.addRegion(type.carrot());
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();
        if(sender.hasPermission("fr.admin")){
            if(args.length == 1){
                commands.add("addRegion");
                StringUtil.copyPartialMatches(args[0], commands, completions);
                return completions;
            }
            if(args.length == 2 && args[0].equalsIgnoreCase("addRegion")){
                commands.add("(Region type)");
                commands.add("WHEAT");
                commands.add("CARROT");
                StringUtil.copyPartialMatches(args[0], commands, completions);
                return completions;
            }

        }
        return null;
    }
}
