package rama.farmRegion.regionManager;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Types {

    public RegionType wheat(){

        List<String> drops = new ArrayList<>();

        drops.add("WHEAT:2");
        drops.add("WHEAT_SEEDS:1");

        return new RegionType(Material.WHEAT, Material.WHEAT, Material.WHEAT, 7, 0, 6, 60, drops, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmE2OWMzZTE3ZmRlMjk4ODdhMzkzYzhkMmY0YmIwNTQ0YzFjNTc2ZGIwOTI1YmIwYWMxNGFjZmZhMzEyMmE2NSJ9fX0=");
    }

    public RegionType carrot(){

        List<String> drops = new ArrayList<>();

        drops.add("CARROT:1");

        return new RegionType(Material.CARROTS, Material.CARROTS, Material.CARROTS, 7, 0, 6, 60, drops, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmE2OWMzZTE3ZmRlMjk4ODdhMzkzYzhkMmY0YmIwNTQ0YzFjNTc2ZGIwOTI1YmIwYWMxNGFjZmZhMzEyMmE2NSJ9fX0=");
    }

}
