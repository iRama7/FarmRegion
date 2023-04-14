package rama.farmRegion.regionManager;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static rama.farmRegion.FarmRegion.plugin;
import static rama.farmRegion.FarmRegion.sendDebug;

public class RegionManager implements Listener {

    List<Region> regions = new ArrayList<>();
    FileConfiguration config = plugin.getConfig();

    public void loadRegions(){
        sendDebug("&eLoading regions...");
        int count = 0;
        if(config.getConfigurationSection("regions") == null){
            sendDebug("&eCouldn't find any regions.");
            return;
        }
        for(String i : config.getConfigurationSection("regions").getKeys(false)){
            Location point1 = config.getLocation("regions." + i + ".point1");
            Location point2 = config.getLocation("regions." + i + ".point2");
            regions.add(new Region(point1, point2, Integer.parseInt(i)));
            count += 1;
        }
        sendDebug("&aSuccessfully loaded &c" + count + " &aregions.");
    }

    @EventHandler
    public void event(BlockBreakEvent e){
        Location blockLocation = e.getBlock().getLocation();

        for(Region region : regions){
            if(region.containsLocation(blockLocation)){

                Material break_material = Material.getMaterial(config.getString("regions." + region.number + ".break_block.material"));
                int break_age = config.getInt("regions." + region.number + ".break_block.age");

                if(e.getBlock().getType().equals(break_material)){
                    Ageable ageable = (Ageable) e.getBlock().getBlockData();
                    int age = ageable.getAge();
                    if(age == break_age){

                        Material replantMaterial = Material.getMaterial(config.getString("regions." + region.number + ".replant_block.material"));
                        int replantAge = config.getInt("regions." + region.number + ".replant_block.age");

                        Material whileReplantMaterial = Material.getMaterial(config.getString("regions." + region.number + ".while_replant_block.material"));
                        int whileReplantAge = config.getInt("regions." + region.number + ".while_replant_block.age");

                        long time = config.getLong("regions." + region.number + ".time");

                        Block block = e.getBlock();
                        block.setType(whileReplantMaterial);
                        Ageable ageable1 = (Ageable) block.getBlockData();
                        ageable1.setAge(whileReplantAge);
                        block.setBlockData(ageable1);

                        BlockScheduler bs = new BlockScheduler();
                        bs.scheduleBlock(time, block, replantMaterial, replantAge);


                        for(String drop : config.getConfigurationSection("regions." + region.number + ".items").getKeys(false)){
                            Material material = Material.getMaterial(config.getString("regions." + region.number + ".items." + drop + ".material"));
                            int amount = config.getInt("regions." + region.number + ".items." + drop + ".amount");
                            ItemStack item = new ItemStack(material, amount);
                            e.getPlayer().getInventory().addItem(item);
                        }
                    }
                    e.setCancelled(true);
                }
            }
        }
    }

}
