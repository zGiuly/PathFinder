package it.artmistech.pathfinder.commands.staff;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import it.artmistech.pathfinder.utils.VanishUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VanishCommand extends AbstractCommand {
    private static List<String> vanishPlayers;
    public VanishCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "vanish");
        vanishPlayers = new ArrayList<>();
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player)sender;

        if(!player.hasPermission("pathfinder.vanish")) return;

        if(!vanishPlayers.contains(player.getName())) {
            VanishUtils.setInvisible(getPathFinder(), player);
            player.sendMessage("§aNow you are invisible!");
        } else {
            VanishUtils.setVisible(getPathFinder(), player);
            player.sendMessage("§aNow you are visible!");
        }
    }

    public static List<String> getVanishPlayers() {
        return vanishPlayers;
    }
}
