package rama.farmRegion;

import org.bukkit.*;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import rama.farmRegion.commands.MainCommand;


public final class FarmRegion extends JavaPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        registerCommands(this);
        registerEvents();
        plugin = this;
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
    }


    /*
    public void blockBreak(BlockBreakEvent e){
        if(e.getBlock().getType() == Material.WHEAT){
            Ageable ageable = (Ageable) e.getBlock().getBlockData();
            int age = ageable.getAge();
            if(age == 7) {
                e.setCancelled(true);
                ageable.setAge(0);
                e.getBlock().setBlockData(ageable);
                ItemStack wheat = new ItemStack(Material.WHEAT);
                wheat.setAmount(1);
                e.getPlayer().getInventory().addItem(wheat);
            }
        }
    }
     */
}
