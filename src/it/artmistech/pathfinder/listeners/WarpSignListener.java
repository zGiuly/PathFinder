package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class WarpSignListener extends AbstractListener {
    public WarpSignListener(PathFinder pathFinder) {
        super(pathFinder);
    }

    @EventHandler
    public void signNameReplace(SignChangeEvent event) {
        if(!event.getPlayer().hasPermission("pathfinder.sign.warp.create")) return;

        if(event.getLine(0).equalsIgnoreCase("[Warp]") && event.getLines().length == 2) {
            String warpName = event.getLine(2);
            if(!warpExists(warpName)) return;

            List<String> format = configStringList("warp-sign-format");

            for (int i = 0; i < format.size(); i++) {
                event.setLine(1, parseFormat(format.get(i), warpName));
            }
        }
    }

    private String parseFormat(String data, String warpName) {
        String parsed = data.replaceAll("%warpname%", warpName);

        return ChatColor.translateAlternateColorCodes('&', parsed);
    }

    private boolean warpExists(String name) {
        return isSet("warps." + name + ".x");
    }

    @EventHandler
    public void signInteract(PlayerInteractEvent event) {
        if(event.getClickedBlock().getType() != Material.SIGN) return;
        Sign sign = (Sign)event.getClickedBlock().getState();

        List<String> format = configStringList("warp-sign-format");
    }
}
