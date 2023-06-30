package rama.farmRegion.regionManager;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.World;

public class Region {

    Location point1;
    Location point2;
    int number;
    ProtectedRegion region;
    RegionType regionType;

    World world;


    public Region(Location point1, Location point2, int number, RegionType regionType, ProtectedRegion region, World world){
        this.point1 = point1;
        this.point2 = point2;
        this.number = number;
        this.regionType = regionType;
        this.region = region;
        this.world = world;
    }

    public Boolean containsLocation(Location blockLocation){
        double maxX = Math.max(point1.getX(), point2.getX());
        double minX = Math.min(point1.getX(), point2.getX());
        double maxY = Math.max(point1.getY(), point2.getY());
        double minY = Math.min(point1.getY(), point2.getY());
        double maxZ = Math.max(point1.getZ(), point2.getZ());
        double minZ = Math.min(point1.getZ(), point2.getZ());

        return blockLocation.getX() <= maxX && blockLocation.getX() >= minX
                && blockLocation.getY() <= maxY && blockLocation.getY() >= minY
                && blockLocation.getZ() <= maxZ && blockLocation.getZ() >= minZ;
    }

}
