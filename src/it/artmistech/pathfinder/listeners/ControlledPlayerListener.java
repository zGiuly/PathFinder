package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.staff.FreezeCommand;
import it.artmistech.pathfinder.utils.FreezeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class ControlledPlayerListener extends AbstractListener {
    public ControlledPlayerListener(PathFinder pathFinder) {
        super(pathFinder);
    }

    @EventHandler
    public void controlledMove(PlayerMoveEvent event) {
        if (!FreezeUtils.isBlocked(event.getPlayer().getName())) return;

        event.getPlayer().teleport(event.getFrom());
        event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', configString("freeze.move-message")));
    }


    @EventHandler
    public void controlledChat(AsyncPlayerChatEvent event) {
        if (!FreezeUtils.isBlocked(event.getPlayer().getName())) return;

        Player checker = Bukkit.getPlayerExact(FreezeCommand.getFreezedPlayers().get(event.getPlayer().getName()));

        event.setCancelled(true);

        String format = configString("freeze.controlled-format")
                .replaceAll("%p", event.getPlayer().getName())
                .replaceAll("%c", checker.getName())
                .replaceAll("%text", event.getMessage());

        checker.sendMessage(ChatColor.translateAlternateColorCodes('&', format));
        event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', format));
    }

    @EventHandler
    public void controlledCommand(PlayerCommandPreprocessEvent event) {
        if (!FreezeCommand.getFreezedPlayers().containsKey(event.getPlayer().getName())) return;

        configStringList("freeze.ignored-commands").forEach(command -> {
            if(event.getMessage().startsWith("/"+command)) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("§cYou can't use this command while you're in control");
            }
        });
    }

    @EventHandler
    public void checkerChat(AsyncPlayerChatEvent event) {
        FreezeCommand.getFreezedPlayers().forEach((controlled, checker) -> {
            if (checker.equals(event.getPlayer().getName())) {
                event.setCancelled(true);

                Player controlledPlayer = Bukkit.getPlayerExact(controlled);

                String format = configString("freeze.checker-format")
                        .replaceAll("%p", controlled)
                        .replaceAll("%c", checker)
                        .replaceAll("%text", event.getMessage());

                controlledPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', format));
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', format));
            }
        });
    }

    @EventHandler
    public void controlledBlockBreak(BlockBreakEvent event) {
        if (!FreezeUtils.isBlocked(event.getPlayer().getName())) return;
        event.setCancelled(true);
    }
}
