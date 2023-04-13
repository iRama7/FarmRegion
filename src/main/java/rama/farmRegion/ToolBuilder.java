package rama.farmRegion;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ToolBuilder {

    public ItemStack buildTool(){
        ItemStack tool = new ItemStack(Material.GOLDEN_AXE);
        tool.setAmount(1);
        ItemMeta itemMeta = tool.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&e&nLeft-click&r &eto select point 1"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&e&nRight-click&r &eto select point 2"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        itemMeta.setLore(lore);
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lFarmRegion &7Selection tool"));
        tool.setItemMeta(itemMeta);
        return tool;
    }

}
