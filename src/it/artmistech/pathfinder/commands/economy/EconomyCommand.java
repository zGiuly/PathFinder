package it.artmistech.pathfinder.commands.economy;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EconomyCommand extends AbstractCommand {
    protected enum CommandTypes {
        SET, REMOVE, ADD, GIVE
    }


    public EconomyCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "economy");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player) sender;

        if (!player.hasPermission("pathfinder.economy")) return;

        if (strings.length == 3) {
            CommandTypes commandTypes = null;
            try {
               commandTypes = CommandTypes.valueOf(strings[1].toUpperCase());
            }catch (Exception e) {
                player.sendMessage("§cError");
                return;
            }

            Player target = Bukkit.getPlayerExact(strings[0]);

            if (target == null || !target.isOnline()) {
                player.sendMessage("§cPlayer offline!");
                return;
            }

            double value = Double.parseDouble(strings[2]);

            switch (commandTypes) {
                case ADD:
                    getEconomy().depositPlayer(target, value);

                    player.sendMessage("§aMoney add!");
                    break;
                case SET:
                    getEconomy().withdrawPlayer(target, getEconomy().getBalance(target));
                    getEconomy().depositPlayer(target, value);

                    player.sendMessage("§aMoney set!");
                case GIVE:
                    player.sendMessage("§aStart give...");
                    Bukkit.getScheduler().runTaskAsynchronously(getPathFinder(), () -> {
                        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                            getEconomy().depositPlayer(onlinePlayer, value);
                        }
                        player.sendMessage("§aTask completed!");
                    });
                    break;
                case REMOVE:
                    getEconomy().withdrawPlayer(target, value);

                    player.sendMessage("§aMoney removed!");
            }
        }
    }
}
