package rama.farmRegion;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.Vector;

import static rama.farmRegion.FarmRegion.plugin;

public class ParticleMain {

    FileConfiguration config = plugin.getConfig();

    private final Boolean replantParticleEnabled = config.getBoolean("config.replant_effect.enable");
    private Effect replantEffect;

    public void summonReplantParticle(World world, Location location){
        try{
            replantEffect = Effect.valueOf(config.getString("config.replant_effect.effect"));
        }catch(IllegalArgumentException e) {
            replantEffect = null;
        }

        if(!replantParticleEnabled || replantEffect == null){
            return;
        }
        world.playEffect(location, replantEffect, 10);
    }

    public void createParticleLine(Location startLoc, Location endLoc, Particle particle, int particleCount) {

        if(particle == null){
            return;
        }

        World world = startLoc.getWorld();

        Vector direction = endLoc.toVector().subtract(startLoc.toVector());
        double length = direction.length();
        direction.normalize();

        for (double i = 0; i < length; i += 0.5) {
            Location loc = startLoc.clone().add(direction.clone().multiply(i));
            world.spawnParticle(particle, loc, particleCount, 0, 0, 0, 0);
        }
    }

}
