package rama.farmRegion;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;

import static rama.farmRegion.FarmRegion.plugin;

public class ParticleMain {

    FileConfiguration config = plugin.getConfig();

    private final Boolean replantParticleEnabled = config.getBoolean("config.replant_particles.enable");
    private final Effect replantEffect = Effect.valueOf(config.getString("config.replant_effect.effect"));

    public void summonReplantParticle(World world, Location location){
        if(!replantParticleEnabled){
            return;
        }
        world.playEffect(location, replantEffect, 10);

    }
}
