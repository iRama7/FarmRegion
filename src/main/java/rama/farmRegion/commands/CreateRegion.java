package rama.farmRegion.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

import static rama.farmRegion.FarmRegion.sendDebug;

public class CreateRegion implements TabExecutor {

    private Material break_material;
    private Material replace_material;
    private int break_age;
    private int replace_age;
    private long time;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender == Bukkit.getConsoleSender()){
            sendDebug("&eYou can't send this command through console.");
            return false;
        }

        if(args.length == 1 && args[0].equalsIgnoreCase("createRegion")){
            Player p = (Player) sender;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
