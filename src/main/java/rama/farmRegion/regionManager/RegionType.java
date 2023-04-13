package rama.farmRegion.regionManager;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class RegionType {

    Material break_material;
    Material replant_material;
    int break_age;
    int replant_age;
    long time;
    List<String> drops;


    public RegionType(Material break_material, Material replant_material, int break_age, int replant_age, long time, List<String> drops){
        this.replant_age = replant_age;
        this.replant_material = replant_material;
        this.time = time;
        this.break_material = break_material;
        this.break_age = break_age;
        this.drops = drops;
    }

    public Material getBreak_material(){
        return this.break_material;
    }

    public Material getReplant_material(){
        return this.replant_material;
    }

    public int getBreak_age(){
        return this.break_age;
    }

    public int getReplant_age(){
        return this.replant_age;
    }

    public long getTime(){
        return this.time;
    }

    public List<String> getItems(){
        return this.drops;
    }
}
