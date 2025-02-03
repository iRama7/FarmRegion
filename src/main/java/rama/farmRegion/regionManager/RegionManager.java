package rama.farmRegion.regionManager;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Cocoa;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.CocoaPlant;
import org.bukkit.material.MaterialData;
import rama.farmRegion.guardiansManager.GuardiansManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static rama.farmRegion.FarmRegion.*;

public class RegionManager implements Listener {

    List<Region> regions = new ArrayList<>();
    Random r = new Random();
    String no_perm_message;

    public void loadRegions(){
        sendDebug("&eLoading regions...");
        int count = 0;
        FileConfiguration config = plugin.getConfig();

        no_perm_message = config.getString("config.no_perm");
        ChatColor.translateAlternateColorCodes('&', no_perm_message);

        if(config.getConfigurationSection("regions") == null){
            sendDebug("&cCouldn't find any regions.");
            return;
        }
        for(String i : config.getConfigurationSection("regions").getKeys(false)){

            Boolean worldguardEnabled = config.getBoolean("regions." + i + ".area.worldguard.enable");
            String regionName = config.getString("regions." + i + ".area.worldguard.region_name");

            Location point1 = config.getLocation("regions." + i + ".area.point1");
            Location point2 = config.getLocation("regions." + i + ".area.point2");
            Material break_material = Material.getMaterial(config.getString("regions." + i + ".break_block.material"));
            Material whileReplantMaterial = Material.getMaterial(config.getString("regions." + i + ".while_replant_block.material"));
            int whileReplantAge = config.getInt("regions." + i + ".while_replant_block.age");
            Material replantMaterial = Material.getMaterial(config.getString("regions." + i + ".replant_block.material"));
            int breakAge = config.getInt("regions." + i + ".break_block.age");
            int replantAge = config.getInt("regions." + i + ".replant_block.age");
            String timeString = config.getString("regions." + i + ".time");
            Set<String> dropList;
            List<String> drops = new ArrayList<>();
            if(config.getConfigurationSection("regions." + i + ".items") != null) {
                dropList = config.getConfigurationSection("regions." + i + ".items").getKeys(false);
                for(String drop : dropList){
                    String material = config.getString("regions." + i + ".items." + drop + ".material");
                    int amount = config.getInt("regions." + i + ".items." + drop + ".amount");
                    int chance = config.getInt("regions." + i + ".items." + drop + ".chance");
                    String dropString = material + ":" + amount + ":" + chance;
                    drops.add(dropString);
                }
            }
            String headValue = config.getString("regions." + i + ".guardian.head-value");
            RegionType regionType = new RegionType(break_material, whileReplantMaterial, replantMaterial, breakAge, whileReplantAge, replantAge, timeString, drops, headValue);


            if(worldguardEnabled){
                World world = Bukkit.getWorld(config.getString("regions." + i + ".area.worldguard.world"));
                if(WGApi == null){
                    sendDebug(" ");
                    sendDebug("WorldGuard is enabled but the plugin didn't found WorldGuard installed.");
                    sendDebug(" ");
                }else {
                    regions.add(new Region(point1, point2, Integer.parseInt(i), regionType, WGApi.buildRegion(regionName, world), world));
                }
            }else {
                World world = point1.getWorld();
                regions.add(new Region(point1, point2, Integer.parseInt(i), regionType, null, world));
            }
            count += 1;
        }
        sendDebug("&aSuccessfully loaded &c" + count + " &aregions.");
    }

    public void unloadRegions(){
        sendDebug("&eUnloading regions...");
        int count;
        if(regions.isEmpty()){
            sendDebug("&cCouldn't find any regions.");
            return;
        }
        count = regions.size();
        regions.clear();
        sendDebug("&aSuccessfully unloaded &c" + count + " &aregions.");
    }

    @EventHandler
    public void blockTrampleEvent(PlayerInteractEvent event){

        boolean disable_block_trampling = plugin.getConfig().getBoolean("config.disable_block_trampling", true);

        if(!disable_block_trampling){
            return;
        }

        if (event.getAction() == Action.PHYSICAL && event.getClickedBlock() != null){
            if (event.getClickedBlock().getType().toString().equals("FARMLAND")) {
                Location blockLocation = event.getClickedBlock().getLocation();
                for(Region region : regions) {
                    if (region.region != null) {
                        if (!WGApi.locInsideRegion(blockLocation, region.region) || !blockLocation.getWorld().getName().equals(region.world.getName())) {
                            continue;
                        }
                    }

                    if (WGApi == null && !region.containsLocation(blockLocation)) {
                        continue;
                    }
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void event(BlockBreakEvent e){

        Location blockLocation = e.getBlock().getLocation();

        for(Region region : regions){
            if(region.region != null){
                if(!WGApi.locInsideRegion(blockLocation, region.region) || !blockLocation.getWorld().getName().equals(region.world.getName())){
                    continue;
                }
            }

            if(WGApi == null && !region.containsLocation(blockLocation)){
                continue;
            }
                Material break_material = region.regionType.break_material;
                int break_age = region.regionType.break_age;

                if(e.getBlock().getType().equals(break_material)){

                    if(!e.getPlayer().hasPermission("farmregion." + break_material)){
                        e.getPlayer().sendMessage(no_perm_message.replaceAll("%material%", break_material.toString()));
                        e.setCancelled(true);
                        return;
                    }

                    Ageable ageable = (Ageable) e.getBlock().getBlockData();
                    int age = ageable.getAge();
                    if(age == break_age){

                        Material replantMaterial = region.regionType.replant_material;
                        int replantAge = region.regionType.replant_age;

                        Material whileReplantMaterial = region.regionType.whileReplantMaterial;
                        int whileReplantAge = region.regionType.whileReplantAge;

                        String timeString = region.regionType.timeString;

                        if(region.regionType.drops.isEmpty()){
                            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                                changeBlock(e.getBlock(), whileReplantMaterial, whileReplantAge);
                                BlockScheduler bs = new BlockScheduler();
                                BlockFace CocoaFace = null;
                                if(e.getBlock().getType() == Material.COCOA){
                                    CocoaFace = ((Cocoa) e.getBlock().getState().getBlockData()).getFacing();
                                }
                                bs.scheduleBlock(timeString, e.getBlock(), replantMaterial, replantAge, new GuardiansManager().getRegionGuardian(region.number), CocoaFace);
                            }, 1L);
                            e.setCancelled(false);
                            return;
                        }
                        changeBlock(e.getBlock(), whileReplantMaterial, whileReplantAge);
                        BlockScheduler bs = new BlockScheduler();
                        BlockFace CocoaFace = null;
                        if(e.getBlock().getType() == Material.COCOA){
                            CocoaFace = ((Cocoa) e.getBlock().getState().getBlockData()).getFacing();
                        }
                        bs.scheduleBlock(timeString, e.getBlock(), replantMaterial, replantAge, new GuardiansManager().getRegionGuardian(region.number), CocoaFace);

                        if(!region.regionType.drops.isEmpty()) {
                            for (String drop : region.regionType.drops) {
                                String[] split = drop.split(":");
                                Material material = Material.getMaterial(split[0]);
                                int amount = Integer.parseInt(split[1]);
                                double chance = Double.parseDouble(split[2]);
                                ItemStack item = new ItemStack(material);
                                item.setAmount(amount);
                                int entero = (r.nextInt(101));
                                int decimal = (r.nextInt(10));
                                double random = Double.parseDouble(entero + "." + decimal);
                                if(random <= chance) {
                                    e.getPlayer().getInventory().addItem(item);
                                }
                            }
                        }
                    }
                    e.setCancelled(true);
                }
            }
        }

    private void changeBlock(Block block, Material whileReplantMaterial, int whileReplantAge){
        BlockFace attachedFace = null;
        if(block.getType() == Material.COCOA) {
            attachedFace = ((Cocoa) block.getState().getBlockData()).getFacing();
        }
        block.setType(whileReplantMaterial);
        Ageable ageable1 = (Ageable) block.getBlockData();
        ageable1.setAge(whileReplantAge);
        block.setBlockData(ageable1);
        if(block.getType() == Material.COCOA){
            changeCocoaDirection(block, block.getBlockData(), attachedFace);
        }
    }

    public void changeCocoaDirection(Block block, BlockData blockData, BlockFace blockFace){
        Cocoa CocoaData = ((Cocoa) blockData);
        CocoaData.setFacing(blockFace);
        block.setBlockData(CocoaData);
    }

}
