package nurd.dapper.slowerprogression.listeners;

import jdk.javadoc.internal.doclint.HtmlTag;
import nurd.dapper.slowerprogression.SlowerProgression;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE) return;

        Block blockBroken = e.getBlock();
        if(blockBroken.getDrops(player.getInventory().getItemInMainHand()).isEmpty()) return;

        Location blockPos = blockBroken.getLocation();

        if(blockBroken.getType().equals(Material.DIAMOND_ORE) || blockBroken.getType().equals(Material.DEEPSLATE_DIAMOND_ORE)) {
            e.setDropItems(false);
            blockPos.getWorld().dropItemNaturally(blockPos, SlowerProgression.GetRoughDiamondItem());
        }
    }

}
