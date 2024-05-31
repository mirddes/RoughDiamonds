package nurd.dapper.slowerprogression.listeners;

import jdk.javadoc.internal.doclint.HtmlTag;
import nurd.dapper.slowerprogression.SlowerProgression;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class BlockBreakListener implements Listener {

    SlowerProgression plugin;

    public BlockBreakListener(SlowerProgression _plugin) {
        plugin = _plugin;
    }

    Random rand = new Random();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE) return;

        Block blockBroken = e.getBlock();
        if(blockBroken.getDrops(player.getInventory().getItemInMainHand()).isEmpty()) return;

        Location blockPos = blockBroken.getLocation();

        if(plugin.getCustomConfig().getBoolean("enable_diamond_tweaks") && (blockBroken.getType().equals(Material.DIAMOND_ORE) || blockBroken.getType().equals(Material.DEEPSLATE_DIAMOND_ORE))) {
            Map<Enchantment, Integer> enchantments = player.getInventory().getItemInMainHand().getEnchantments();
            if(enchantments.containsKey(Enchantment.SILK_TOUCH)) return; // If they have silk touch, perform regular drops

            int dropCount = 1;

            // If they have fortune, update the dropCount with the proper calculations
            if (enchantments.containsKey(Enchantment.LOOT_BONUS_BLOCKS)) {
                int fortuneLevel = enchantments.get(Enchantment.LOOT_BONUS_BLOCKS);

                // note: rand.nextInt(Max) will generate from 0 (inclusive) to Max (exclusive)
                // This means that rand.nextInt(3) will generate 0, 1, or 2

                switch (fortuneLevel) { // Calculations are based on https://minecraft.fandom.com/wiki/Fortune
                    case 1: // Fortune 1
                        if(rand.nextInt(3) == 0) { // 33% chance
                            dropCount = 2;
                        }
                        break;
                    case 2: // Fortune 2
                        if(rand.nextInt(2) == 0) { // 50% chance
                            dropCount = 2 + rand.nextInt(2); // 50% to Either drops 2 or 3 (total 25% for either)
                        }
                        break;
                    case 3: // Fortune 3
                        if(rand.nextInt(10) < 4) { // 40% chance
                            dropCount = 2 + rand.nextInt(3);  // 60% to Either drops 2, 3, or 4 (total 20% for each)
                        }
                        break;
                    default: // Edge case? Will treat as fortune 3
                        if(rand.nextInt(10) < 4) { // 40% chance
                            dropCount = 2 + rand.nextInt(3);  // 60% to Either drops 2, 3, or 4 (total 20% for each)
                        }
                        break;
                }
            }

            e.setDropItems(false);
            blockPos.getWorld().dropItemNaturally(blockPos, SlowerProgression.GetRoughDiamondItem(dropCount));
        }
    }

}
