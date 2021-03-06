package it.artmistech.pathfinder.commands.teleport;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class TpaCommand extends AbstractCommand {
    private static HashMap<String, Long> cooldown;
    private static HashMap<String, String> inProgress;

    private final int maxTime;

    public TpaCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "tpa");
        cooldown = new HashMap<>();
        inProgress = new HashMap<>();

        maxTime = configInt("tpa.max-time");

        BukkitTask asyncTask = Bukkit.getScheduler().runTaskTimerAsynchronously(getPathFinder(), () -> {
            try {
                long now = System.currentTimeMillis() / 1000;

                cooldown.forEach((player, executionSeconds) -> {
                    if (now - executionSeconds >= maxTime) {
                        Player target = Bukkit.getPlayerExact(inProgress.get(player));
                        Player executor = Bukkit.getPlayerExact(player);

                        if (target != null && executor != null) {
                            target.sendMessage("§cTime expired to accept the tpa");
                            executor.sendMessage("§cTime expired to accept the tpa");
                        }

                        Bukkit.getScheduler().runTask(getPathFinder(), () -> {
                            cooldown.remove(player);
                            inProgress.remove(player);
                        });
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 60);
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player) sender;
        if (!player.hasPermission("pathfinder.tpa")) return;

        if (cooldown.containsKey(player.getName())) {
            player.sendMessage("§cYour previous request has not yet expired");
            return;
        }

        if (strings.length == 1) {
            if (strings[0].equals(player.getName())) return;
            Player target = Bukkit.getPlayerExact(strings[0]);

            if (target == null || !target.isOnline()) {
                player.sendMessage("§cPlayer offline!");
                return;
            }

            player.sendMessage("§aTpa request sent");
            target.sendMessage("§aYou have a tpa request from §e" + player.getName() + " §athis will expire in " + maxTime + " seconds\n§aUse command /tpaccept for accept the tpa request");

            inProgress.put(player.getName(), strings[0]);
            cooldown.put(player.getName(), System.currentTimeMillis() / 1000);
        }
    }

    public static HashMap<String, Long> getCooldown() {
        return cooldown;
    }

    public static HashMap<String, String> getInProgress() {
        return inProgress;
    }
}
