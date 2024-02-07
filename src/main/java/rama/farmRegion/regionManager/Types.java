package rama.farmRegion.regionManager;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Types {

    public RegionType wheat(){

        List<String> drops = new ArrayList<>();

        drops.add("WHEAT:1:100.0");
        drops.add("WHEAT_SEEDS:2:75.0");

        return new RegionType(Material.WHEAT, Material.WHEAT, Material.WHEAT, 7, 0, 6, "60-120", drops, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmE2OWMzZTE3ZmRlMjk4ODdhMzkzYzhkMmY0YmIwNTQ0YzFjNTc2ZGIwOTI1YmIwYWMxNGFjZmZhMzEyMmE2NSJ9fX0=");
    }

    public RegionType carrot(){

        List<String> drops = new ArrayList<>();

        drops.add("CARROT:1:100.0");

        return new RegionType(Material.CARROTS, Material.CARROTS, Material.CARROTS, 7, 0, 6, "60-120", drops, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmE2OWMzZTE3ZmRlMjk4ODdhMzkzYzhkMmY0YmIwNTQ0YzFjNTc2ZGIwOTI1YmIwYWMxNGFjZmZhMzEyMmE2NSJ9fX0=");
    }

    public RegionType potato(){

        List<String> drops = new ArrayList<>();

        drops.add("POTATO:1:100.0");

        return new RegionType(Material.POTATOES, Material.POTATOES, Material.POTATOES, 7, 0, 6, "60-120", drops, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmE2OWMzZTE3ZmRlMjk4ODdhMzkzYzhkMmY0YmIwNTQ0YzFjNTc2ZGIwOTI1YmIwYWMxNGFjZmZhMzEyMmE2NSJ9fX0=");
    }

    public RegionType beetroot(){

        List<String> drops = new ArrayList<>();

        drops.add("BEETROOT:1:100.0");
        drops.add("BEETROOT_SEEDS:3:75.0");

        return new RegionType(Material.BEETROOTS, Material.BEETROOTS, Material.BEETROOTS, 3, 0, 2, "60-120", drops, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmE2OWMzZTE3ZmRlMjk4ODdhMzkzYzhkMmY0YmIwNTQ0YzFjNTc2ZGIwOTI1YmIwYWMxNGFjZmZhMzEyMmE2NSJ9fX0=");
    }

    public RegionType cocoa(){

        List<String> drops = new ArrayList<>();

        drops.add("COCOA:1:100.0");

        return new RegionType(Material.COCOA, Material.COCOA, Material.COCOA, 2, 0, 1, "60-120", drops, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmE2OWMzZTE3ZmRlMjk4ODdhMzkzYzhkMmY0YmIwNTQ0YzFjNTc2ZGIwOTI1YmIwYWMxNGFjZmZhMzEyMmE2NSJ9fX0=");
    }

    public RegionType netherWart(){

        List<String> drops = new ArrayList<>();

        drops.add("NETHER_WART:2:100.0");
        drops.add("NETHER_WART:1:50.0");

        return new RegionType(Material.NETHER_WART, Material.NETHER_WART, Material.NETHER_WART, 3, 0, 2, "60-120", drops, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmE2OWMzZTE3ZmRlMjk4ODdhMzkzYzhkMmY0YmIwNTQ0YzFjNTc2ZGIwOTI1YmIwYWMxNGFjZmZhMzEyMmE2NSJ9fX0=");
    }

}
