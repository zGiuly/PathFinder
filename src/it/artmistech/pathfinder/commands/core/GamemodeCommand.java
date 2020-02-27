package it.artmistech.pathfinder.commands.core;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand extends AbstractCommand {
    public GamemodeCommand(PathFinder pathFinder) {
        super(SenderEnum.ALL, pathFinder, "gamemode");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player)sender;

            if(configBoolean("gamemode.only-op") && !player.isOp()) {
                player.sendMessage("§4Only opped players!");
                return;
            }

            if(!player.hasPermission("pathfinder.gamemode")) return;

            if(strings.length == 1) {
                GameMode gameMode = GameMode.valueOf(strings[0].toUpperCase());

                switch (gameMode) {
                    case CREATIVE:
                        if(!player.hasPermission("pathfinder.gamemode.creative")) return;
                        player.setGameMode(gameMode);
                        break;
                    case SURVIVAL:
                        if(!player.hasPermission("pathfinder.gamemode.survival")) return;
                        player.setGameMode(gameMode);
                        break;
                    case SPECTATOR:
                        if(!player.hasPermission("pathfinder.gamemode.spectator")) return;
                        player.setGameMode(gameMode);
                        break;
                }

                player.setGameMode(gameMode);
                player.sendMessage("§aGamemode updated!");
            } else if(strings.length == 2) {
                Player target = Bukkit.getPlayerExact(strings[0]);
                GameMode gameMode = GameMode.valueOf(strings[1].toUpperCase());

                if(target == null || !target.isOnline()) return;

                switch (gameMode) {
                    case CREATIVE:
                        if(!player.hasPermission("pathfinder.gamemode.creative")) return;
                        target.setGameMode(gameMode);
                        break;
                    case SURVIVAL:
                        if(!player.hasPermission("pathfinder.gamemode.survival")) return;
                        target.setGameMode(gameMode);
                        break;
                    case SPECTATOR:
                        if(!player.hasPermission("pathfinder.gamemode.spectator")) return;
                        target.setGameMode(gameMode);
                        break;
                }

                target.sendMessage("§aGamemode updated!");
                player.sendMessage("§aGamemode updated!");
            }
        } else if (sender instanceof ConsoleCommandSender) {
            if(strings.length != 2) {
                sender.sendMessage("§4No gamemode selected or not player found!");
                return;
            }

            Player player = Bukkit.getPlayerExact(strings[0]);

            if(player == null || !player.isOnline()) return;

            GameMode gameMode = GameMode.valueOf(strings[1].toUpperCase());

            player.setGameMode(gameMode);
            sender.sendMessage("§aGamemode updated!");
            player.sendMessage("§aGamemode updated!");
        }
    }
}
