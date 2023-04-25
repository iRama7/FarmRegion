package rama.farmRegion.regionManager;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import rama.farmRegion.ParticleMain;
import rama.farmRegion.guardiansManager.GuardiansManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static rama.farmRegion.FarmRegion.plugin;
import static rama.farmRegion.FarmRegion.sendDebug;

public class RegionManager implements Listener {

    List<Region> regions = new ArrayList<>();

    public void loadRegions(){
        sendDebug("&eLoading regions...");
        int count = 0;
        FileConfiguration config = plugin.getConfig();
        if(config.getConfigurationSection("regions") == null){
            sendDebug("&cCouldn't find any regions.");
            return;
        }
        for(String i : config.getConfigurationSection("regions").getKeys(false)){
            Location point1 = config.getLocation("regions." + i + ".point1");
            Location point2 = config.getLocation("regions." + i + ".point2");
            Material break_material = Material.getMaterial(config.getString("regions." + i + ".break_block.material"));
            Material whileReplantMaterial = Material.getMaterial(config.getString("regions." + i + ".while_replant_block.material"));
            int whileReplantAge = config.getInt("regions." + i + ".while_replant_block.age");
            Material replantMaterial = Material.getMaterial(config.getString("regions." + i + ".replant_block.material"));
            int breakAge = config.getInt("regions." + i + ".break_block.age");
            int replantAge = config.getInt("regions." + i + ".replant_block.age");
            long time = config.getLong("regions." + i + ".time");
            Set<String> dropList = config.getConfigurationSection("regions." + i + ".items").getKeys(false);
            List<String> drops = new ArrayList<>();
            for(String drop : dropList){
                String material = config.getString("regions." + i + ".items." + drop + ".material");
                int amount = config.getInt("regions." + i + ".items." + drop + ".amount");
                String dropString = material + ":" + amount;
                drops.add(dropString);
            }
            String headValue = config.getString("regions." + i + ".guardian.head-value");
            RegionType regionType = new RegionType(break_material, whileReplantMaterial, replantMaterial, breakAge, whileReplantAge, replantAge, time, drops, headValue);
            regions.add(new Region(point1, point2, Integer.parseInt(i), regionType));
            count += 1;
        }
        sendDebug("&aSuccessfully loaded &c" + count + " &aregions.");
    }

    public void unloadRegions(){
        sendDebug("&eUnloading regions...");
        int count;
        if(regions.isEmpty()){
            sendDebug("&cCouldn't find any regions.");
            return;
        }
        count = regions.size();
        regions.clear();
        sendDebug("&aSuccessfully unloaded &c" + count + " &aregions.");
    }

    @EventHandler
    public void event(BlockBreakEvent e){


        Location blockLocation = e.getBlock().getLocation();

        for(Region region : regions){
            if(region.containsLocation(blockLocation)){

                Material break_material = region.regionType.break_material;
                int break_age = region.regionType.break_age;

                if(e.getBlock().getType().equals(break_material)){
                    Ageable ageable = (Ageable) e.getBlock().getBlockData();
                    int age = ageable.getAge();
                    if(age == break_age){

                        Material replantMaterial = region.regionType.replant_material;
                        int replantAge = region.regionType.replant_age;

                        Material whileReplantMaterial = region.regionType.whileReplantMaterial;
                        int whileReplantAge = region.regionType.whileReplantAge;

                        long time = region.regionType.time;

                        Block block = e.getBlock();
                        block.setType(whileReplantMaterial);
                        Ageable ageable1 = (Ageable) block.getBlockData();
                        ageable1.setAge(whileReplantAge);
                        block.setBlockData(ageable1);

                        BlockScheduler bs = new BlockScheduler();
                        bs.scheduleBlock(time, block, replantMaterial, replantAge, new GuardiansManager().getRegionGuardian(region.number));


                        for(String drop : region.regionType.drops){
                            String[] split = drop.split(":");
                            Material material = Material.getMaterial(split[0]);
                            int amount = Integer.parseInt(split[1]);
                            ItemStack item = new ItemStack(material);
                            item.setAmount(amount);
                            e.getPlayer().getInventory().addItem(item);
                        }
                    }
                    e.setCancelled(true);
                }
            }
        }
    }

}
