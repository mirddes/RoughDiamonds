package nurd.dapper.slowerprogression.listeners;

import nurd.dapper.slowerprogression.SlowerProgression;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class AttemptCraftListener implements Listener {

    @EventHandler
    public void onCrafting(PrepareItemCraftEvent e) {
//        if (e.getInventory().getResult() == null) return;
        Player player = e.getView().getPlayer() instanceof Player ? ((Player) e.getView().getPlayer()) : null;
        if(player == null) return;

        int roughDiamondCount = 0;
        int diamondCount = 0;
        int magmaCreamCount = 0;
        CraftingInventory craftingInventory = e.getInventory();

        for(ItemStack item : craftingInventory.getMatrix()) {
            if(item == null) continue;
            if(item.getItemMeta().hasLore() && item.getItemMeta().getLore().equals(SlowerProgression.GetRoughDiamondItem().getItemMeta().getLore()))
                roughDiamondCount++; // we specifically compare lore because we want to ignore the item's name, in case someone potentially renames the item
            else if (item.getType().equals(Material.MAGMA_CREAM))
                magmaCreamCount++;
            else if (item.getType().equals(Material.DIAMOND))
                diamondCount++;
        }

        if(roughDiamondCount == 1 && magmaCreamCount == 1) { // If recipe is shapeless for diamond polishing (1 magma cream, 1 rough diamond)
            craftingInventory.setResult(new ItemStack(Material.DIAMOND, 1));
        }
        else if (diamondCount == 1 && magmaCreamCount == 1) { // If recipe is diamond polishing but using regular diamond (this is required because I had to add the default shaped recipe to fix the duplication error)
            craftingInventory.setResult(null);
        }
        else if (roughDiamondCount > 0) { // This cancels out regular diamond crafting using the rough diamond
            craftingInventory.setResult(null);
        }
    }

}
