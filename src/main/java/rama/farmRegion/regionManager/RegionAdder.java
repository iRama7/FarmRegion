package rama.farmRegion.regionManager;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import rama.farmRegion.GuardiansManager;

import static rama.farmRegion.FarmRegion.plugin;

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

        config.set("regions." + i + ".point1", p1);
        config.set("regions." + i + ".point2", p2);

        config.set("regions." + i + ".time", type.time);

        for(String s : type.drops){
            String[] split = s.split(":");
            Material material = Material.getMaterial(split[0]);
            int amount = Integer.parseInt(split[1]);
            int i1;
            if(config.getConfigurationSection("regions." + i + ".items") == null){
                i1 = 1;
            }else{
                i1 = (config.getConfigurationSection("regions." + i + ".items").getKeys(false).size() + 1);
            }
            config.set("regions." + i + ".items." + i1 + ".material", material.toString());
            config.set("regions." + i + ".items." + i1 + ".amount", amount);
        }

        GuardiansManager gm = new GuardiansManager();
        gm.summonGuardian(gm.getMiddleLocation(p1.getBlock(), p2.getBlock()), gm.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmE2OWMzZTE3ZmRlMjk4ODdhMzkzYzhkMmY0YmIwNTQ0YzFjNTc2ZGIwOTI1YmIwYWMxNGFjZmZhMzEyMmE2NSJ9fX0="));

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
