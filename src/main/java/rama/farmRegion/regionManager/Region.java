package rama.farmRegion.regionManager;

import org.bukkit.Location;

public class Region {

    Location point1;
    Location point2;
    int number;

    RegionType regionType;


    public Region(Location point1, Location point2, int number, RegionType regionType){
        this.point1 = point1;
        this.point2 = point2;
        this.number = number;
        this.regionType = regionType;
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
