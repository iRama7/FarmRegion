package rama.farmRegion.regionManager;

import org.bukkit.Material;

import java.util.List;

public class RegionType {

    Material break_material;
    Material whileReplantMaterial;
    int whileReplantAge;
    Material replant_material;
    int break_age;
    int replant_age;
    String timeString;
    List<String> drops;

    String headValue;


    public RegionType(Material break_material, Material whileReplantMaterial, Material replant_material, int break_age, int whileReplantAge, int replant_age, String timeString, List<String> drops, String headValue){
        this.replant_age = replant_age;
        this.replant_material = replant_material;
        this.timeString = timeString;
        this.break_material = break_material;
        this.break_age = break_age;
        this.drops = drops;
        this.whileReplantAge = whileReplantAge;
        this.whileReplantMaterial = whileReplantMaterial;
        this.headValue = headValue;
    }
}
