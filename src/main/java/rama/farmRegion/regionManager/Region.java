package rama.farmRegion.regionManager;

import org.bukkit.Location;

public class Region {

    Location point1;
    Location point2;
    int number;

    public Region(Location point1, Location point2, int number){
        this.point1 = point1;
        this.point2 = point2;
        this.number = number;
    }

    public Boolean containsLocation(Location loc){

        return true;
    }

}
