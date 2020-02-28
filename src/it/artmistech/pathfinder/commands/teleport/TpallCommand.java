package it.artmistech.pathfinder.commands.teleport;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import it.artmistech.pathfinder.types.CustomLocation;
import it.artmistech.pathfinder.utils.LocationUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpallCommand extends AbstractCommand {
    public TpallCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "tpall");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player)sender;

        if(!player.hasPermission("pathfider.tpall")) return;

        CustomLocation location = CustomLocation.fromLocation(player.getLocation());

        if(!location.isSafe()) {
            player.sendMessage("§cLocation is unsafe");
            return;
        }

        player.getWorld().getPlayers().forEach(worldPlayer -> {
            if(!worldPlayer.getName().equals(player.getName())) {
                worldPlayer.teleport(location);
            }
        });

        player.sendMessage("§aAll players teleported here!");
    }
}
