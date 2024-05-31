package nurd.dapper.slowerprogression.listeners;

import nurd.dapper.slowerprogression.SlowerProgression;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class MobDeathListener implements Listener {

    SlowerProgression plugin;

    public MobDeathListener(SlowerProgression _plugin) {
        plugin = _plugin;
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent e) {
        if(e.getEntityType().equals(EntityType.COW) && plugin.getCustomConfig().getBoolean("enable_cow_drop_tweaks")) {
            Location pos = e.getEntity().getLocation();
            e.getEntity().getLocation().getWorld().dropItemNaturally(pos, new ItemStack(Material.LEATHER, 1));
        }
    }

}
