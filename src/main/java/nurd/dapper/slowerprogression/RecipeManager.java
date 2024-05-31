package nurd.dapper.slowerprogression;

import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;

public class RecipeManager {
    SlowerProgression plugin;

    int nuggetCount;

    public RecipeManager(SlowerProgression _plugin) {
        plugin = _plugin;
        nuggetCount = plugin.getCustomConfig().getInt("nugget_amount");
        if(nuggetCount < 1) nuggetCount = 1;
    }

    public void Initialize() {
        if(plugin.getCustomConfig().getBoolean("enable_smelting_tweaks")) {
            loadIronNuggets();
            loadGoldNuggets();
        }
        if(plugin.getCustomConfig().getBoolean("enable_diamond_tweaks")) {
            loadDiamond();
        }
        if(plugin.getCustomConfig().getBoolean("enable_saddle_crafting")) {
            loadSaddle();
        }
        if(plugin.getCustomConfig().getBoolean("enable_altered_recipes")) {
            loadAnvil();
            loadBlastFurnace();
            loadChain();
            loadCompass();
            loadLantern();
            loadSoulLantern();
            loadIronBars();
            loadRail();
        }
    }

    private void loadDiamond() {
        // This recipe is needed to prevent duplication (it uses non-meta diamonds, but it gets overwritten in AttemptCraftListener)
        ShapelessRecipe diamond = new ShapelessRecipe(
                new NamespacedKey(plugin, "diamond"),
                new ItemStack(Material.DIAMOND, 1))
                .addIngredient(Material.MAGMA_CREAM)
                .addIngredient(Material.DIAMOND);
        plugin.getServer().addRecipe(diamond);
    }

    private void loadSaddle() {
        ShapedRecipe saddle1 = new ShapedRecipe(
                new NamespacedKey(plugin, "saddle_1"),
                new ItemStack(Material.SADDLE, 1))
                .shape("LLL", "N N", "   ")
                .setIngredient('L', Material.LEATHER)
                .setIngredient('N', Material.IRON_NUGGET);

        ShapedRecipe saddle2 = new ShapedRecipe(
                new NamespacedKey(plugin, "saddle_2"),
                new ItemStack(Material.SADDLE, 1))
                .shape("   ", "LLL", "N N")
                .setIngredient('L', Material.LEATHER)
                .setIngredient('N', Material.IRON_NUGGET);

        plugin.getServer().addRecipe(saddle1);
        plugin.getServer().addRecipe(saddle2);
    }

    private void loadLantern() {
        plugin.RemoveRecipe(Material.LANTERN);
        ShapelessRecipe lantern = new ShapelessRecipe(
                new NamespacedKey(plugin, "lantern"),
                new ItemStack(Material.LANTERN, 1))
                .addIngredient(Material.IRON_NUGGET)
                .addIngredient(Material.TORCH);
        plugin.getServer().addRecipe(lantern);
    }

    private void loadSoulLantern() {
        plugin.RemoveRecipe(Material.SOUL_LANTERN);
        ShapelessRecipe soulLantern = new ShapelessRecipe(
                new NamespacedKey(plugin, "soul_lantern"),
                new ItemStack(Material.SOUL_LANTERN, 1))
                .addIngredient(Material.IRON_NUGGET)
                .addIngredient(Material.SOUL_TORCH);
        plugin.getServer().addRecipe(soulLantern);
    }

    private void loadAnvil() {
        plugin.RemoveRecipe(Material.ANVIL);
        ShapedRecipe anvil = new ShapedRecipe(
                new NamespacedKey(plugin, "anvil"),
                new ItemStack(Material.ANVIL, 1))
                .shape("IBI", " I ", "III")
                .setIngredient('I', Material.IRON_INGOT)
                .setIngredient('B', Material.IRON_BLOCK);
        plugin.getServer().addRecipe(anvil);
    }

    private void loadChain() {
        plugin.RemoveRecipe(Material.CHAIN);
        ShapedRecipe chain1 = new ShapedRecipe(
                new NamespacedKey(plugin, "chain_1"),
                new ItemStack(Material.CHAIN, 1))
                .shape("N  ", "N  ", "N  ")
                .setIngredient('N', Material.IRON_NUGGET);
        ShapedRecipe chain2 = new ShapedRecipe(
                new NamespacedKey(plugin, "chain_2"),
                new ItemStack(Material.CHAIN, 1))
                .shape(" N ", " N ", " N ")
                .setIngredient('N', Material.IRON_NUGGET);
        ShapedRecipe chain3 = new ShapedRecipe(
                new NamespacedKey(plugin, "chain_3"),
                new ItemStack(Material.CHAIN, 1))
                .shape("  N", "  N", "  N")
                .setIngredient('N', Material.IRON_NUGGET);
        plugin.getServer().addRecipe(chain1);
        plugin.getServer().addRecipe(chain2);
        plugin.getServer().addRecipe(chain3);

    }

    private void loadIronBars() {
        plugin.RemoveRecipe(Material.IRON_BARS);
        ShapedRecipe ironBars1 = new ShapedRecipe(
                new NamespacedKey(plugin, "iron_bars_1"),
                new ItemStack(Material.IRON_BARS, 16))
                .shape("NIN", "NIN", "   ")
                .setIngredient('I', Material.IRON_INGOT)
                .setIngredient('N', Material.IRON_NUGGET);
        ShapedRecipe ironBars2 = new ShapedRecipe(
                new NamespacedKey(plugin, "iron_bars_2"),
                new ItemStack(Material.IRON_BARS, 16))
                .shape("   ", "NIN", "NIN")
                .setIngredient('I', Material.IRON_INGOT)
                .setIngredient('N', Material.IRON_NUGGET);
        plugin.getServer().addRecipe(ironBars1);
        plugin.getServer().addRecipe(ironBars2);
    }

    private void loadRail() {
        plugin.RemoveRecipe(Material.RAIL);
        ShapedRecipe rails = new ShapedRecipe(
                new NamespacedKey(plugin, "rails"),
                new ItemStack(Material.RAIL, 16))
                .shape("N N", "ISI", "N N")
                .setIngredient('I', Material.IRON_INGOT)
                .setIngredient('N', Material.IRON_NUGGET)
                .setIngredient('S', Material.STICK);
        plugin.getServer().addRecipe(rails);
    }

    private void loadCompass() {
        plugin.RemoveRecipe(Material.COMPASS);
        ShapedRecipe compass = new ShapedRecipe(
                new NamespacedKey(plugin, "compass"),
                new ItemStack(Material.COMPASS, 1))
                .shape(" N ", "NRN", " N ")
                .setIngredient('N', Material.IRON_NUGGET)
                .setIngredient('R', Material.REDSTONE);
        plugin.getServer().addRecipe(compass);
    }

    private void loadBlastFurnace() {
        plugin.RemoveRecipe(Material.BLAST_FURNACE);
        ShapedRecipe blastFurnace = new ShapedRecipe(
                new NamespacedKey(plugin, "blast_furnace"),
                new ItemStack(Material.BLAST_FURNACE, 1))
                .shape("SSS", "IFI", "SSS")
                .setIngredient('S', Material.SMOOTH_STONE)
                .setIngredient('I', Material.IRON_INGOT)
                .setIngredient('F', Material.FURNACE);
        plugin.getServer().addRecipe(blastFurnace);
    }

    private void loadIronNuggets() {
        // Furnace
        FurnaceRecipe rawIron = new FurnaceRecipe(
                new NamespacedKey(plugin, "iron_nugget_furnace"),
                new ItemStack(Material.IRON_NUGGET, nuggetCount),
                Material.RAW_IRON, 0.7f, 200
        );
        FurnaceRecipe ironOre = new FurnaceRecipe(
                new NamespacedKey(plugin, "iron_ore"),
                new ItemStack(Material.IRON_NUGGET, nuggetCount),
                Material.IRON_ORE, 0.7f, 100
        );
        FurnaceRecipe deepIronOre = new FurnaceRecipe(
                new NamespacedKey(plugin, "deep_iron_ore"),
                new ItemStack(Material.IRON_NUGGET, nuggetCount),
                Material.DEEPSLATE_IRON_ORE, 0.7f, 100
        );
        plugin.getServer().addRecipe(rawIron);
        plugin.getServer().addRecipe(ironOre);
        plugin.getServer().addRecipe(deepIronOre);

        // Blast Furnace
        BlastingRecipe rawIron_Blast = new BlastingRecipe(
                new NamespacedKey(plugin, "iron_nugget_blast"),
                new ItemStack(Material.IRON_NUGGET, nuggetCount),
                Material.RAW_IRON, 0.7f, 100
        );
        BlastingRecipe ironOre_Blast = new BlastingRecipe(
                new NamespacedKey(plugin, "iron_ore_blast"),
                new ItemStack(Material.IRON_NUGGET, nuggetCount),
                Material.IRON_ORE, 0.7f, 100
        );
        BlastingRecipe deepIronOre_Blast = new BlastingRecipe(
                new NamespacedKey(plugin, "deep_iron_ore_blast"),
                new ItemStack(Material.IRON_NUGGET, nuggetCount),
                Material.DEEPSLATE_IRON_ORE, 0.7f, 100
        );
        plugin.getServer().addRecipe(rawIron_Blast);
        plugin.getServer().addRecipe(ironOre_Blast);
        plugin.getServer().addRecipe(deepIronOre_Blast);
    }

    private void loadGoldNuggets() {
        // Furnace
        FurnaceRecipe rawGold = new FurnaceRecipe(
                new NamespacedKey(plugin, "gold_nugget_furnace"),
                new ItemStack(Material.GOLD_NUGGET, nuggetCount),
                Material.RAW_GOLD, 0.7f, 200
        );
        FurnaceRecipe goldOre = new FurnaceRecipe(
                new NamespacedKey(plugin, "gold_ore"),
                new ItemStack(Material.GOLD_NUGGET, nuggetCount),
                Material.GOLD_ORE, 0.7f, 100
        );
        FurnaceRecipe deepGoldOre = new FurnaceRecipe(
                new NamespacedKey(plugin, "deep_gold_ore"),
                new ItemStack(Material.GOLD_NUGGET, nuggetCount),
                Material.DEEPSLATE_GOLD_ORE, 0.7f, 100
        );
        plugin.getServer().addRecipe(rawGold);
        plugin.getServer().addRecipe(goldOre);
        plugin.getServer().addRecipe(deepGoldOre);

        // Blast Furnace
        BlastingRecipe rawGold_Blast = new BlastingRecipe(
                new NamespacedKey(plugin, "gold_nugget_blast"),
                new ItemStack(Material.GOLD_NUGGET, nuggetCount),
                Material.RAW_GOLD, 0.7f, 200
        );
        BlastingRecipe goldOre_Blast = new BlastingRecipe(
                new NamespacedKey(plugin, "gold_ore_blast"),
                new ItemStack(Material.GOLD_NUGGET, nuggetCount),
                Material.GOLD_ORE, 0.7f, 100
        );
        BlastingRecipe deepGoldOre_Blast = new BlastingRecipe(
                new NamespacedKey(plugin, "deep_gold_ore_blast"),
                new ItemStack(Material.GOLD_NUGGET, nuggetCount),
                Material.DEEPSLATE_GOLD_ORE, 0.7f, 100
        );
        plugin.getServer().addRecipe(rawGold_Blast);
        plugin.getServer().addRecipe(goldOre_Blast);
        plugin.getServer().addRecipe(deepGoldOre_Blast);
    }
}
