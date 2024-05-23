package nurd.dapper.slowerprogression;

import nurd.dapper.slowerprogression.listeners.AttemptCraftListener;
import nurd.dapper.slowerprogression.listeners.BlockBreakListener;
import nurd.dapper.slowerprogression.listeners.MobDeathListener;
import nurd.dapper.slowerprogression.listeners.PlayerJoinListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public final class SlowerProgression extends JavaPlugin {

    private File customConfigFile;
    private FileConfiguration customConfig;

    // Furnace Recipes ///////
    // Iron
    FurnaceRecipe rawIron = new FurnaceRecipe(
            new NamespacedKey(this, "iron_nugget_furnace"),
            new ItemStack(Material.IRON_NUGGET, 1),
            Material.RAW_IRON,
            0.7f,
            200
    );
    FurnaceRecipe rawIronBlock = new FurnaceRecipe(
            new NamespacedKey(this, "iron_ingot_furnace"),
            new ItemStack(Material.IRON_INGOT, 1),
            Material.RAW_IRON_BLOCK,
            0.7f,
            200
    );
    FurnaceRecipe ironOre = new FurnaceRecipe(
            new NamespacedKey(this, "iron_ore"),
            new ItemStack(Material.IRON_NUGGET, 1),
            Material.IRON_ORE,
            0.7f,
            100
    );
    FurnaceRecipe deepIronOre = new FurnaceRecipe(
            new NamespacedKey(this, "deep_iron_ore"),
            new ItemStack(Material.IRON_NUGGET, 1),
            Material.DEEPSLATE_IRON_ORE,
            0.7f,
            100
    );
    // Gold
    FurnaceRecipe rawGold = new FurnaceRecipe(
            new NamespacedKey(this, "gold_nugget_furnace"),
            new ItemStack(Material.GOLD_NUGGET, 1),
            Material.RAW_GOLD,
            0.7f,
            200
    );
    FurnaceRecipe rawGoldBlock = new FurnaceRecipe(
            new NamespacedKey(this, "gold_ingot_furnace"),
            new ItemStack(Material.GOLD_INGOT, 1),
            Material.RAW_GOLD_BLOCK,
            0.7f,
            200
    );
    FurnaceRecipe goldOre = new FurnaceRecipe(
            new NamespacedKey(this, "gold_ore"),
            new ItemStack(Material.GOLD_NUGGET, 1),
            Material.GOLD_ORE,
            0.7f,
            100
    );
    FurnaceRecipe deepGoldOre = new FurnaceRecipe(
            new NamespacedKey(this, "deep_gold_ore"),
            new ItemStack(Material.GOLD_NUGGET, 1),
            Material.DEEPSLATE_GOLD_ORE,
            0.7f,
            100
    );

    // Blast Furnace Recipes ///////
    // Iron
    BlastingRecipe rawIron_Blast = new BlastingRecipe(
            new NamespacedKey(this, "iron_nugget_blast"),
            new ItemStack(Material.IRON_NUGGET, 1),
            Material.RAW_IRON,
            0.7f,
            100
    );
    BlastingRecipe rawIronBlock_Blast = new BlastingRecipe(
            new NamespacedKey(this, "iron_ingot_blast"),
            new ItemStack(Material.IRON_INGOT, 1),
            Material.RAW_IRON_BLOCK,
            0.7f,
            100
    );
    BlastingRecipe ironOre_Blast = new BlastingRecipe(
            new NamespacedKey(this, "iron_ore_blast"),
            new ItemStack(Material.IRON_NUGGET, 1),
            Material.IRON_ORE,
            0.7f,
            100
    );
    BlastingRecipe deepIronOre_Blast = new BlastingRecipe(
            new NamespacedKey(this, "deep_iron_ore_blast"),
            new ItemStack(Material.IRON_NUGGET, 1),
            Material.DEEPSLATE_IRON_ORE,
            0.7f,
            100
    );
    // Gold
    BlastingRecipe rawGold_Blast = new BlastingRecipe(
            new NamespacedKey(this, "gold_nugget_blast"),
            new ItemStack(Material.GOLD_NUGGET, 1),
            Material.RAW_GOLD,
            0.7f,
            200
    );
    BlastingRecipe rawGoldBlock_Blast = new BlastingRecipe(
            new NamespacedKey(this, "gold_ingot_blast"),
            new ItemStack(Material.GOLD_INGOT, 1),
            Material.RAW_GOLD_BLOCK,
            0.7f,
            200
    );
    BlastingRecipe goldOre_Blast = new BlastingRecipe(
            new NamespacedKey(this, "gold_ore_blast"),
            new ItemStack(Material.GOLD_NUGGET, 1),
            Material.GOLD_ORE,
            0.7f,
            100
    );
    BlastingRecipe deepGoldOre_Blast = new BlastingRecipe(
            new NamespacedKey(this, "deep_gold_ore_blast"),
            new ItemStack(Material.GOLD_NUGGET, 1),
            Material.DEEPSLATE_GOLD_ORE,
            0.7f,
            100
    );

    // Misc Recipes
    // This recipe is needed to prevent duplication (it uses non-meta diamonds, but it gets overwritten in AttemptCraftListener)
    ShapelessRecipe diamond = new ShapelessRecipe(
            new NamespacedKey(this, "diamond"),
            new ItemStack(Material.DIAMOND, 1))
            .addIngredient(Material.MAGMA_CREAM)
            .addIngredient(Material.DIAMOND);

    // We need two of the saddle recipes because it is shaped and can be made in the top or bottom half of the table...
    ShapedRecipe saddle1 = new ShapedRecipe(
            new NamespacedKey(this, "saddle_1"),
            new ItemStack(Material.SADDLE, 1))
            .shape("LLL", "N N", "   ")
            .setIngredient('L', Material.LEATHER)
            .setIngredient('N', Material.IRON_NUGGET);

    ShapedRecipe saddle2 = new ShapedRecipe(
            new NamespacedKey(this, "saddle_2"),
            new ItemStack(Material.SADDLE, 1))
            .shape("   ", "LLL", "N N")
            .setIngredient('L', Material.LEATHER)
            .setIngredient('N', Material.IRON_NUGGET);

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Plugin enabled.");

        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new AttemptCraftListener(), this);
        getServer().getPluginManager().registerEvents(new MobDeathListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        getServer().addRecipe(rawIron);
        getServer().addRecipe(rawIron_Blast);
        getServer().addRecipe(rawIronBlock);
        getServer().addRecipe(rawIronBlock_Blast);
        getServer().addRecipe(ironOre);
        getServer().addRecipe(ironOre_Blast);
        getServer().addRecipe(deepIronOre);
        getServer().addRecipe(deepIronOre_Blast);
        getServer().addRecipe(rawGold);
        getServer().addRecipe(rawGold_Blast);
        getServer().addRecipe(rawGoldBlock);
        getServer().addRecipe(rawGoldBlock_Blast);
        getServer().addRecipe(goldOre);
        getServer().addRecipe(goldOre_Blast);
        getServer().addRecipe(deepGoldOre);
        getServer().addRecipe(deepGoldOre_Blast);

        getServer().addRecipe(diamond);
        getServer().addRecipe(saddle1);
        getServer().addRecipe(saddle2);

        createCustomConfig();
    }

    @Override
    public void onDisable() {
        getServer().clearRecipes();
    }

    static public ItemStack GetRoughDiamondItem() {
        ItemStack roughDiamond = new ItemStack(Material.DIAMOND);
        ItemMeta meta = roughDiamond.getItemMeta();

        meta.setDisplayName(ChatColor.GRAY + "Rough Diamond");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.RED + "Combine with Magma Cream to polish");
        meta.setLore(lore);
        meta.setCustomModelData(10170001);
        roughDiamond.setItemMeta(meta);
        return roughDiamond;
    }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "custom.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("custom.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
