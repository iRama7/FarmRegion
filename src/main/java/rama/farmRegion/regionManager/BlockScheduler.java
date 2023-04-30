package rama.farmRegion.regionManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.material.CocoaPlant;
import rama.farmRegion.ParticleMain;
import rama.farmRegion.guardiansManager.Guardian;

import java.util.Random;

import static rama.farmRegion.FarmRegion.plugin;
import static rama.farmRegion.FarmRegion.rm;

public class BlockScheduler {

    private int particleCount = plugin.getConfig().getInt("config.guardian_effect.count");
    private Boolean particleEnabled = plugin.getConfig().getBoolean("config.guardian_effect.enable");
    private Particle particle = Particle.valueOf(plugin.getConfig().getString("config.guardian_effect.particle"));
    private Random random = new Random();


    public void scheduleBlock(String timeString, Block block, Material replantMaterial, int replantAge, Guardian guardian){

        String[] split = timeString.split("-");
        long minTime = Long.parseLong(split[0]);
        long maxTime = Long.parseLong(split[1]);
        long randomNum = minTime + (long)(random.nextDouble() * (maxTime - minTime + 1));
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){

            @Override
            public void run() {
                BlockFace attachedFace = null;
                if(block.getType() == Material.COCOA) {
                    attachedFace = ((CocoaPlant) block.getState().getData()).getAttachedFace();
                    switch (attachedFace) {
                        case NORTH:
                            attachedFace = BlockFace.SOUTH;
                            break;
                        case SOUTH:
                            attachedFace = BlockFace.NORTH;
                            break;
                        case EAST:
                            attachedFace = BlockFace.WEST;
                            break;
                        case WEST:
                            attachedFace = BlockFace.EAST;
                            break;
                    }
                }
                block.setType(replantMaterial);
                Ageable ageable = (Ageable) block.getBlockData();
                ageable.setAge(replantAge);
                block.setBlockData(ageable);
                if(block.getType() == Material.COCOA){
                    rm.changeCocoaDirection(block.getState(), attachedFace);
                }
                new ParticleMain().summonReplantParticle(block.getWorld(), block.getLocation());
                if(guardian.getEnabled() && particleEnabled) {
                    Location cropLocation = block.getLocation();
                    cropLocation.setX(block.getLocation().getX() + 0.5);
                    cropLocation.setZ(block.getLocation().getZ() + 0.5);
                    block.getLocation().setX(block.getLocation().getX() + 0.5);
                    block.getLocation().setZ(block.getLocation().getZ() + 0.5);
                    new ParticleMain().createParticleLine(guardian.getGuardianLocation().clone().add(0, 0.4, 0), cropLocation, particle, particleCount);
                }
            }

        },randomNum);
    }

}
