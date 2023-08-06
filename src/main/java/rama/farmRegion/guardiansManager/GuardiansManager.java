package rama.farmRegion.guardiansManager;

import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import com.mojang.authlib.GameProfile;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static rama.farmRegion.FarmRegion.plugin;
import static rama.farmRegion.FarmRegion.sendDebug;


public class GuardiansManager {


    public static List<Guardian> guardians = new ArrayList<>();

    public Location getMiddleLocation(Block block1, Block block2) {
        Location loc1 = block1.getLocation();
        Location loc2 = block2.getLocation();
        double middleX = (loc1.getX() + loc2.getX()) / 2;
        double middleY = (loc1.getY() + loc2.getY()) / 2;
        double middleZ = (loc1.getZ() + loc2.getZ()) / 2;
        Location middleLoc = new Location(loc1.getWorld(), middleX, middleY + 5, middleZ);
        Block middleBlock = middleLoc.getBlock();
        middleX = middleBlock.getLocation().getX() + 0.5;
        middleY = middleBlock.getLocation().getY() + 0.5;
        middleZ = middleBlock.getLocation().getZ() + 0.5;
        return new Location(loc1.getWorld(), middleX, middleY, middleZ);
    }

    public ItemStack createSkull(String url) {
        ItemStack head = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) 3);
        if (url.isEmpty())
            return head;

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", url));

        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);

        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
            error.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

    public void saveGuardian(Guardian guardian){
        guardians.add(guardian);
    }

    public void writeGuardians(FileConfiguration guardiansConfig) {
        for (Guardian guardian : guardians) {
            if(plugin.getConfig().getString("regions." + guardian.guardianInt + ".guardian.head-value") == null){
                return;
            }
            guardiansConfig.set("guardians." + guardian.guardianInt + ".location", guardian.guardianLocation);
            guardiansConfig.set("guardians." + guardian.guardianInt + ".head", createSkull(plugin.getConfig().getString("regions." + guardian.guardianInt + ".guardian.head-value")));
        }
    }

    public void retrieveGuardians(FileConfiguration guardiansConfig){

        Location l;
        ItemStack h;



        if(guardiansConfig.getConfigurationSection("guardians") == null){
            return;
        }

        for(String i : guardiansConfig.getConfigurationSection("guardians").getKeys(false)){
            l = guardiansConfig.getLocation("guardians." + i + ".location");
            h = guardiansConfig.getItemStack("guardians." + i + ".head");
            Boolean enabled = plugin.getConfig().getBoolean("regions." + i + ".guardian.enable");
            Guardian guardian = new Guardian(l, h, Integer.parseInt(i), enabled);
            if(enabled) {
                guardian.summon();
            }
            guardians.add(guardian);
        }
    }

    public void unloadGuardians(){
        sendDebug("&eUnloading guardians...");
        killGuardians();
        guardians.clear();
    }

    public Guardian getRegionGuardian(int regionNumber){
        for(Guardian g : guardians){
            if(g.guardianInt == regionNumber){
                return g;
            }
        }
        return null;
    }

    public void killGuardians(){
        for(Guardian g : guardians){
            if(g.enabled) {
                g.kill();
            }
        }
    }

    public void killArmorStandsWithName(String name) {
        for(World world : Bukkit.getWorlds()){
            for(Entity entity : world.getEntities()){
                if(entity instanceof ArmorStand){
                    ArmorStand armorStand = (ArmorStand) entity;
                    if(armorStand.getCustomName() != null && armorStand.getCustomName().equals(name)){
                        armorStand.remove();
                    }
                }
            }
        }
    }
}
