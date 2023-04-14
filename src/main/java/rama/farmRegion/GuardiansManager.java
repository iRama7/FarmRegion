package rama.farmRegion;

import com.mojang.authlib.properties.Property;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import com.mojang.authlib.GameProfile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.lang.reflect.Field;
import java.util.UUID;

import static rama.farmRegion.FarmRegion.plugin;

public class GuardiansManager {

    public static ArmorStand guardian;

    public void summonGuardian(Location location, ItemStack head){
        guardian = location.getWorld().spawn(location, ArmorStand.class);
        guardian.setVisible(false);
        guardian.setSmall(true);
        guardian.setGravity(false);
        guardian.setArms(false);
        guardian.setMarker(true);
        guardian.setHelmet(head);

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

                guardian.teleport(location.clone().add(0, height, 0));
                float currentYaw = guardian.getLocation().getYaw();
                guardian.setRotation(currentYaw + (float)(x * 125), 0);

                angle += increment;
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    public Location getMiddleLocation(Block block1, Block block2) {
        Location loc1 = block1.getLocation();
        Location loc2 = block2.getLocation();
        double middleX = (loc1.getX() + loc2.getX()) / 2;
        double middleY = (loc1.getY() + loc2.getY()) / 2;
        double middleZ = (loc1.getZ() + loc2.getZ()) / 2;
        return new Location(loc1.getWorld(), middleX, middleY + 5, middleZ);
    }

    public ItemStack createSkull(String url) {
        ItemStack head = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) 3);
        if (url.isEmpty())
            return head;

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", url));

        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);

        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
            error.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

}
