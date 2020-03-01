package it.artmistech.pathfinder.commands.fun;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

public class NukeCommand extends AbstractCommand {
    public NukeCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "nuke");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        if (!sender.hasPermission("pathfinder.nuke")) return;

        Player player = (Player) sender;

        if (strings.length == 1) {
            Player target = Bukkit.getPlayerExact(strings[0]);

            if (target == null || !target.isOnline()) {
                player.sendMessage("§cPlayer offline!");
                return;
            }

            Location loc = target.getLocation();

            for (int x = -10; x <= 10; x += 5) {
                for (int z = -10; z <= 10; z += 5) {
                    Location finalLocation = new Location(loc.getWorld(), loc.getX() + x, loc.getWorld().getHighestBlockYAt(loc) + 32, loc.getZ() + z);

                    TNTPrimed primed = loc.getWorld().spawn(finalLocation, TNTPrimed.class);

                    primed.setCustomNameVisible(true);
                    primed.setIsIncendiary(false);
                    primed.setCustomName("§c§lNuke");
                }
            }
        }
    }
}
