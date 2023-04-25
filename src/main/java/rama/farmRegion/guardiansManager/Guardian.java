package rama.farmRegion.guardiansManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import static rama.farmRegion.FarmRegion.plugin;

public class Guardian {


    ArmorStand guardian;
    Location guardianLocation;
    ItemStack guardianHead;
    int guardianInt;
    Boolean enabled;


    public Guardian(Location guardianLocation, ItemStack guardianHead, int guardianInt, Boolean enabled){
        this.guardianHead = guardianHead;
        this.guardianLocation = guardianLocation;
        this.guardianInt = guardianInt;
        this.enabled = enabled;
    }

    public void summon(){
        guardian = guardianLocation.getWorld().spawn(guardianLocation, ArmorStand.class);
        guardian.getLocation().getChunk().load();
        guardian.setVisible(false);
        guardian.setSmall(true);
        guardian.setGravity(false);
        guardian.setArms(false);
        guardian.setMarker(true);
        guardian.setHelmet(guardianHead);
        guardian.setRemoveWhenFarAway(false);

        new BukkitRunnable() {
            double angle = 0;
            double increment = Math.PI / 32;

            @Override
            public void run() {
                if (guardian.isDead()) {
                    this.cancel();
                    return;
                }

                double y = Math.sin(angle);
                double x = Math.cos(angle);
                double height = y * 0.4;

                guardian.teleport(guardianLocation.clone().add(0, height, 0));
                float currentYaw = guardian.getLocation().getYaw();
                guardian.setRotation(currentYaw + (float)(x * 125), 0);


                angle += increment;
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    public void kill(){
        this.guardian.remove();
    }

    public int getGuardianInt(){
        return this.guardianInt;
    }

    public Boolean getEnabled(){
        return this.enabled;
    }

    public Location getGuardianLocation(){
        return this.guardianLocation;
    }
}
