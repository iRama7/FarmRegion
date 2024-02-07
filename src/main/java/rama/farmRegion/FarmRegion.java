package rama.farmRegion;

import org.bukkit.*;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import rama.farmRegion.WGApi.WorldGuardApi;
import rama.farmRegion.commands.MainCommand;
import rama.farmRegion.guardiansManager.GuardiansManager;
import rama.farmRegion.regionManager.RegionManager;

import java.io.File;
import java.io.IOException;


public final class FarmRegion extends JavaPlugin {



    public static Plugin plugin;
    public static RegionManager rm;
    public static WorldGuardApi WGApi;


    private File guardiansFile;
    public static FileConfiguration guardiansConfig;

    public static GuardiansManager guardiansManager;


    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        plugin = this;
        registerCommands(this);
        createCustomConfig();
        if(Bukkit.getPluginManager().getPlugin("WorldGuard") != null && this.getConfig().getBoolean("config.worldguard")){
            sendDebug("&eEnabling WorldGuard support...");
            WGApi = new WorldGuardApi();
        }
        rm = new RegionManager();
        rm.loadRegions();
        registerEvents();
        guardiansManager = new GuardiansManager();
        Bukkit.getScheduler().runTaskLater(this, () -> {
            guardiansManager.killArmorStandsWithName("FarmRegion-Guardian");
        }, 10L);
        Bukkit.getScheduler().runTaskLater(this, () -> {
            guardiansManager.retrieveGuardians(guardiansConfig);
        }, 10L);

        new UpdateChecker(this, 109500).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                sendDebug("&aYou are running the latest version.");
            } else {
                sendDebug("&eThere is a new update available.");
            }
        });
    }

    @Override
    public void onDisable() {
        guardiansManager.writeGuardians(guardiansConfig);
        try {
            guardiansConfig.save(guardiansFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        guardiansManager.killGuardians();
        guardiansManager.killArmorStandsWithName("FarmRegion-Guardian");
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


    private void createCustomConfig() {
        sendDebug("&eLoading guardians config...");
        guardiansFile = new File(getDataFolder(), "guardians.yml");
        if (!guardiansFile.exists()) {
            guardiansFile.getParentFile().mkdirs();
            saveResource("guardians.yml", false);
        }

        guardiansConfig = new YamlConfiguration();
        try {
            guardiansConfig.load(guardiansFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static int getServerVersion(){
        String version = Bukkit.getServer().getBukkitVersion().split("-")[0];
        int versionInteger = Integer.parseInt(version.replace(".", ""));
        return versionInteger;
    }

}
