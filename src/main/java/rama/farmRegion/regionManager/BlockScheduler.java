package rama.farmRegion.regionManager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import rama.farmRegion.ParticleMain;

import static rama.farmRegion.FarmRegion.plugin;
import static rama.farmRegion.GuardiansManager.guardian;

public class BlockScheduler {

    public void scheduleBlock(long time, Block block, Material replantMaterial, int replantAge){
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){

            @Override
            public void run() {
                block.setType(replantMaterial);
                Ageable ageable = (Ageable) block.getBlockData();
                ageable.setAge(replantAge);
                block.setBlockData(ageable);
                new ParticleMain().summonReplantParticle(block.getWorld(), block.getLocation());
                new ParticleMain().createParticleLine(guardian.getLocation().add(0, 0.4, 0), block.getLocation(), Particle.ELECTRIC_SPARK, 1);
            }

        },time);
    }

}
