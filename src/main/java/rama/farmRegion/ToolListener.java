package rama.farmRegion;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class ToolListener implements Listener {

    public static Location point1;
    public static Location point2;

    @EventHandler
    public void interactEvent(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getItem() == null){
            return;
        }
        if(e.getItem().getItemMeta() == null){
            return;
        }
        if(!e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&6&lFarmRegion &7Selection tool"))){
            return;
        }
        if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if(e.getClickedBlock() == null){
                return;
            }
            point1 = e.getClickedBlock().getLocation();
            e.setCancelled(true);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eSetting first point at: &7" + point1.getX() + "&e, &7" + point1.getY() + "&e, &7" + point1.getZ() + "&e."));
        }
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(e.getClickedBlock() == null){
                return;
            }
            point2 = e.getClickedBlock().getLocation();
            e.setCancelled(true);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eSetting second point at: &7" + point2.getX() + "&e, &7" + point2.getY() + "&e, &7" + point2.getZ() + "&e."));
        }
    }

}
