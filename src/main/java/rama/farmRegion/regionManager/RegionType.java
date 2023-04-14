package rama.farmRegion.regionManager;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class RegionType {

    Material break_material;
    Material whileReplantMaterial;
    int whileReplantAge;
    Material replant_material;
    int break_age;
    int replant_age;
    long time;
    List<String> drops;


    public RegionType(Material break_material, Material whileReplantMaterial, Material replant_material, int break_age, int whileReplantAge, int replant_age, long time, List<String> drops){
        this.replant_age = replant_age;
        this.replant_material = replant_material;
        this.time = time;
        this.break_material = break_material;
        this.break_age = break_age;
        this.drops = drops;
        this.whileReplantAge = whileReplantAge;
        this.whileReplantMaterial = whileReplantMaterial;
    }
}
