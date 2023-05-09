package rama.farmRegion.WGApi;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldGuardApi {

    public Boolean locInsideRegion(Location location, ProtectedRegion region){

        com.sk89q.worldedit.util.Location WGLocation = BukkitAdapter.adapt(location);
        if(region.contains(WGLocation.getBlockX(), WGLocation.getBlockY(), WGLocation.getBlockZ())){

            return true;
        }else {
            return false;
        }
    }

    public ProtectedRegion buildRegion(String region_name, World world){
        RegionManager regions = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));
        ProtectedRegion region = regions.getRegion(region_name);
        return region;
    }

    public List<String> getRegions(World world){
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager manager = container.get(BukkitAdapter.adapt(world));
        Map<String, ProtectedRegion> regions = manager.getRegions();
        List<String> regionNames = new ArrayList<String>();
        for (Map.Entry<String, ProtectedRegion> entry : regions.entrySet()) {
            regionNames.add(entry.getKey());
        }
        return regionNames;
    }

}
