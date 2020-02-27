package it.artmistech.pathfinder.commands.staff;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FinishCommand extends AbstractCommand {
    private List<String> reasons;
    public FinishCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "finish");
        buildReasons();
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player) sender;

        if (!player.hasPermission("pathfinder.finish")) return;


        if (strings.length != 2) return;

        String reason = strings[1].toLowerCase();
        Player target = Bukkit.getPlayerExact(strings[0]);

        if (target == null || !target.isOnline()) {
            player.sendMessage("§cPlayer offline!");
            return;
        }

        if(!FreezeCommand.getFreezedPlayers().containsKey(target.getName())) {
            player.sendMessage("§cThis player is not in control");
            return;
        }

        if(!FreezeCommand.getFreezedPlayers().get(target.getName()).equals(player.getName())) {
            player.sendMessage("§cYou are not the checker of this player");
            return;
        }

        if(reasons.contains(reason)) {
            FreezeCommand.getFreezedPlayers().remove(target.getName());
            for (String s : configStringList("freeze.reasons." + reason + ".commands")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replaceAll("%p", target.getName()));
            }
        } else {
            player.sendMessage("§cReason not found!");
        }
    }

    public void buildReasons() {
        reasons = new ArrayList<>();
        reasons.addAll(getPathFinder().getConfig().getConfigurationSection("freeze.reasons").getKeys(false));
    }
}
