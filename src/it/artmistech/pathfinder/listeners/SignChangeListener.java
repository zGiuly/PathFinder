package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener extends AbstractListener {
    public SignChangeListener(PathFinder pathFinder) {
        super(pathFinder);
    }

    @EventHandler
    public void signChange(SignChangeEvent event) {
        if(!configBoolean("sign.colored-sign")) return;

        for (int i = 0; i < event.getLines().length; i++) {
            String text = event.getLine(i);
            event.setLine(i, ChatColor.translateAlternateColorCodes('&', text));
        }
    }
}
