package rama.farmRegion.guardiansManager;

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
        guardian.getLocation().getChunk().setForceLoaded(true);
        guardian.setCustomName("FarmRegion-Guardian");
        guardian.setCustomNameVisible(false);
        guardian.setVisible(false);
        guardian.setSmall(true);
        guardian.setGravity(false);
        guardian.setArms(false);
        guardian.setMarker(true);
        guardian.setHelmet(guardianHead);
        guardian.setRemoveWhenFarAway(false);

        new BukkitRunnable() {
            double angle = 0;
            double y = 0;
            double yDirection = 0.009;

            @Override
            public void run() {
                if (guardian.isDead()) {
                    this.cancel();
                    return;
                }
                angle += 8.2;
                if(angle > 360){
                    angle = 0;
                }
                guardian.setRotation((float) angle, 0);

                y += yDirection;
                if(y > 0.13 || y < -0.13){
                    yDirection *= -1;
                }
                Location location = guardian.getLocation().clone();
                location.setY(location.getY() + y);
                guardian.teleport(location);

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
        return guardian.getLocation();
    }
}
