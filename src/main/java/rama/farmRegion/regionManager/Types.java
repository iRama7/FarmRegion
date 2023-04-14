package rama.farmRegion.regionManager;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Types {

    public RegionType wheat(){

        List<String> drops = new ArrayList<>();

        drops.add("WHEAT:2");
        drops.add("WHEAT_SEEDS:1");

        return new RegionType(Material.WHEAT, Material.WHEAT, Material.WHEAT, 7, 0, 6, 60, drops);
    }

    public RegionType carrot(){

        List<String> drops = new ArrayList<>();

        drops.add("CARROT:1");

        return new RegionType(Material.CARROTS, Material.CARROTS, Material.CARROTS, 7, 0, 6, 60, drops);
    }

}
