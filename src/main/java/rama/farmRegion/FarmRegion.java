package rama.farmRegion;

import org.bukkit.*;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import rama.farmRegion.commands.MainCommand;
import rama.farmRegion.regionManager.RegionManager;


public final class FarmRegion extends JavaPlugin {



    public static Plugin plugin;
    public static RegionManager rm;
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        plugin = this;
        registerCommands(this);
        rm = new RegionManager();
        registerEvents();
        rm.loadRegions();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void sendDebug(String string){
        String prefix = "&c[&6FarmRegion&c] &r";
        String message = prefix + string;
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void registerCommands(JavaPlugin plugin){
        sendDebug("&eRegistering commands...");
        TabExecutor MainCommand = new MainCommand();
        plugin.getCommand("fr").setExecutor(MainCommand);
        plugin.getCommand("fr").setTabCompleter(MainCommand);

    }

    public void registerEvents(){
        sendDebug("&eRegistering events...");
        getServer().getPluginManager().registerEvents(new ToolListener(), this);
        getServer().getPluginManager().registerEvents(rm, this);
    }

}
