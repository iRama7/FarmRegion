package rama.farmRegion.regionManager;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import rama.farmRegion.guardiansManager.Guardian;

import static rama.farmRegion.FarmRegion.*;

public class RegionAdder {

    private final FileConfiguration config = plugin.getConfig();

    public void addRegion(RegionType type, Location p1, Location p2){

        int i = nextRegionInt();

        config.set("regions." + i + ".break_block.material", type.break_material.toString());
        config.set("regions." + i + ".break_block.age", type.break_age);

        config.set("regions." + i + ".while_replant_block.material", type.whileReplantMaterial.toString());
        config.set("regions." + i + ".while_replant_block.age", type.whileReplantAge);

        config.set("regions." + i + ".replant_block.material", type.replant_material.toString());
        config.set("regions." + i + ".replant_block.age", type.replant_age);

        config.set("regions." + i + ".guardian.enable", true);
        config.set("regions." + i + ".guardian.head-value", type.headValue);

        config.set("regions." + i + ".area.point1", p1);
        config.set("regions." + i + ".area.point2", p2);

        config.set("regions." + i + ".area.worldguard.enable", false);
        config.set("regions." + i + ".area.worldguard.region_name", "null");

        config.set("regions." + i + ".time", type.timeString);

        for(String s : type.drops){
            String[] split = s.split(":");
            Material material = Material.getMaterial(split[0]);
            int amount = Integer.parseInt(split[1]);
            double chance = Double.parseDouble(split[2]);
            int i1;
            if(config.getConfigurationSection("regions." + i + ".items") == null){
                i1 = 1;
            }else{
                i1 = (config.getConfigurationSection("regions." + i + ".items").getKeys(false).size() + 1);
            }
            config.set("regions." + i + ".items." + i1 + ".material", material.toString());
            config.set("regions." + i + ".items." + i1 + ".amount", amount);
            config.set("regions." + i + ".items." + i1 + ".chance", chance);
        }


        Guardian guardian = new Guardian(guardiansManager.getMiddleLocation(p1.getBlock(), p2.getBlock()), guardiansManager.createSkull(type.headValue), i, true, type.headValue);
        guardian.summon();
        guardiansManager.saveGuardian(guardian);
        plugin.saveConfig();
    }

    public void addWGRegion(RegionType type, String region_name, World world){

        int i = nextRegionInt();

        config.set("regions." + i + ".break_block.material", type.break_material.toString());
        config.set("regions." + i + ".break_block.age", type.break_age);

        config.set("regions." + i + ".while_replant_block.material", type.whileReplantMaterial.toString());
        config.set("regions." + i + ".while_replant_block.age", type.whileReplantAge);

        config.set("regions." + i + ".replant_block.material", type.replant_material.toString());
        config.set("regions." + i + ".replant_block.age", type.replant_age);

        config.set("regions." + i + ".guardian.enable", true);
        config.set("regions." + i + ".guardian.head-value", type.headValue);

        config.set("regions." + i + ".area.point1", "null");
        config.set("regions." + i + ".area.point2", "null");

        config.set("regions." + i + ".area.worldguard.enable", true);
        config.set("regions." + i + ".area.worldguard.region_name", region_name);
        config.set("regions." + i + ".area.worldguard.world", world.getName());

        config.set("regions." + i + ".time", type.timeString);

        for(String s : type.drops){
            String[] split = s.split(":");
            Material material = Material.getMaterial(split[0]);
            int amount = Integer.parseInt(split[1]);
            int chance = Integer.parseInt(split[2]);
            int i1;
            if(config.getConfigurationSection("regions." + i + ".items") == null){
                i1 = 1;
            }else{
                i1 = (config.getConfigurationSection("regions." + i + ".items").getKeys(false).size() + 1);
            }
            config.set("regions." + i + ".items." + i1 + ".material", material.toString());
            config.set("regions." + i + ".items." + i1 + ".amount", amount);
            config.set("regions." + i + ".items." + i1 + ".chance", chance);
        }

        ProtectedRegion region = WGApi.buildRegion(region_name, world);
        Block block1 = world.getBlockAt(region.getMaximumPoint().getX(), region.getMaximumPoint().getY(), region.getMaximumPoint().getZ());
        Block block2 = world.getBlockAt(region.getMinimumPoint().getX(), region.getMinimumPoint().getY(), region.getMinimumPoint().getZ());
        Guardian guardian = new Guardian(guardiansManager.getMiddleLocation(block1, block2), guardiansManager.createSkull(type.headValue), i, true, type.headValue);
        guardian.summon();
        guardiansManager.saveGuardian(guardian);
        plugin.saveConfig();
    }

    public Integer nextRegionInt() {
        if(config.getConfigurationSection("regions") == null){
            return 1;
        }else{
            return (config.getConfigurationSection("regions").getKeys(false).size() + 1);
        }
    }
}
