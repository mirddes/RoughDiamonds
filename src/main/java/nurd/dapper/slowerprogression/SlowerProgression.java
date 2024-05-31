package nurd.dapper.slowerprogression;

import nurd.dapper.slowerprogression.listeners.AttemptCraftListener;
import nurd.dapper.slowerprogression.listeners.BlockBreakListener;
import nurd.dapper.slowerprogression.listeners.MobDeathListener;
import nurd.dapper.slowerprogression.listeners.PlayerJoinListener;
import org.bukkit.*;
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

    RecipeManager recipeManager;

    @Override
    public void onEnable() {
        createCustomConfig();

        recipeManager = new RecipeManager(this);
        recipeManager.Initialize();

        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new AttemptCraftListener(this), this);
        getServer().getPluginManager().registerEvents(new MobDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        // Plugin startup logic
        getLogger().info("Plugin enabled.");
    }

    @Override
    public void onDisable() {
        getServer().clearRecipes();
    }

    public void RemoveRecipe(Material mat) {
        ItemStack dummyItem = new ItemStack(mat);
        for (Recipe recipe : Bukkit.getRecipesFor(dummyItem)) {
            if (recipe instanceof Keyed) {
                Bukkit.removeRecipe(((Keyed) recipe).getKey());
            }
        }
    }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "custom.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("custom.yml", false);
            getLogger().info("Creating config file.");
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
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

    static public ItemStack GetRoughDiamondItem(int count) {
        ItemStack roughDiamond = new ItemStack(Material.DIAMOND, count);
        ItemMeta meta = roughDiamond.getItemMeta();

        meta.setDisplayName(ChatColor.GRAY + "Rough Diamond");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.RED + "Combine with Magma Cream to polish");
        meta.setLore(lore);
        meta.setCustomModelData(10170001);
        roughDiamond.setItemMeta(meta);
        return roughDiamond;
    }
}
