package it.artmistech.pathfinder.commands.economy;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand extends AbstractCommand {
    public BalanceCommand(PathFinder pathFinder) {
        super(SenderEnum.ALL, pathFinder, "balance");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        if(sender instanceof Player) {
            Player player = (Player)sender;

            if(!player.hasPermission("pathfinder.balance")) return;

            if(strings.length == 1) {

                if(!player.hasPermission("pathfinder.balance.other")) return;

                Player target = Bukkit.getPlayerExact(strings[0]);

                if (target == null || !target.isOnline()) {
                    player.sendMessage("§cPlayer offline!");
                    return;
                }
                player.sendMessage("§aBalance: " + getPathFinder().getEconomy().getBalance(target));
                return;
            }
            player.sendMessage("§aBalance: " + getPathFinder().getEconomy().getBalance(player));
        } else if(sender instanceof ConsoleCommandSender) {
            if(strings.length == 1) {
                Player target = Bukkit.getPlayerExact(strings[0]);

                if (target == null || !target.isOnline()) {
                    sender.sendMessage("Player offline!");
                    return;
                }

                sender.sendMessage("Balance: " + getPathFinder().getEconomy().getBalance(target));
            }
        }
    }
}
