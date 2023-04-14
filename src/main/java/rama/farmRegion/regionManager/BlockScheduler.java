package rama.farmRegion.regionManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.scheduler.BukkitRunnable;

import static rama.farmRegion.FarmRegion.plugin;

public class BlockScheduler {

    public void scheduleBlock(long time, Block block, Material replantMaterial, int replantAge){
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){

            @Override
            public void run() {
                block.setType(replantMaterial);
                Ageable ageable = (Ageable) block.getBlockData();
                ageable.setAge(replantAge);
                block.setBlockData(ageable);
            }

        },time);
    }

}
