package nurd.dapper.slowerprogression.listeners;

import nurd.dapper.slowerprogression.SlowerProgression;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    SlowerProgression plugin;

    public PlayerJoinListener(SlowerProgression _plugin) {
        plugin = _plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if(plugin.getCustomConfig().getBoolean("download_resourcepack")) {
            e.getPlayer().setResourcePack("https://www.dropbox.com/scl/fo/3yal5of9zr2zv73ry9xla/ALdlIH6eSMluEQNoWT8FW8o?rlkey=bs48tslz6o4se3vpaglzp0xte&st=pjbjeiiu&dl=1");
        }
    }

}
