package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.core.AfkCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class AfkPlayerListener extends AbstractListener {
    public AfkPlayerListener(PathFinder pathFinder) {
        super(pathFinder);
    }

    private boolean isAfk(String name) {
        return AfkCommand.getAfkPlayers().contains(name);
    }

    @EventHandler
    public void afkChat(AsyncPlayerChatEvent event) {
        if(!isAfk(event.getPlayer().getName())) return;

        AfkCommand.getAfkPlayers().remove(event.getPlayer().getName());
        event.getPlayer().sendMessage("§cYou have been removed from afk status");
    }

    @EventHandler
    public void afkMove(PlayerMoveEvent event) {
        if(!isAfk(event.getPlayer().getName())) return;
        event.getPlayer().teleport(event.getFrom());
    }

    @EventHandler
    public void afkBlockBreak(BlockBreakEvent event) {
        if(!isAfk(event.getPlayer().getName())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void afkDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player)) return;

        Player player = (Player)event.getEntity();

        if(!isAfk(player.getName())) return;

        if(configBoolean("afk.invulerable")) {
            event.setCancelled(true);
        } else {
            AfkCommand.getAfkPlayers().remove(player.getName());
            player.sendMessage("§cYou have been removed from afk status");
        }
    }

    @EventHandler
    public void afkCommands(PlayerCommandPreprocessEvent event) {
        if(!isAfk(event.getPlayer().getName())) return;
        event.setCancelled(true);
        event.getPlayer().sendMessage("§cYou have been removed from afk status");
        AfkCommand.getAfkPlayers().remove(event.getPlayer().getName());
    }
}
