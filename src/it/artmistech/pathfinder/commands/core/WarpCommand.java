package it.artmistech.pathfinder.commands.core;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import it.artmistech.pathfinder.types.CustomLocation;
import it.artmistech.pathfinder.utils.PathFinderUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class WarpCommand extends AbstractCommand {
    private final List<String> cooldown;

    public WarpCommand(PathFinder pathFinder) {
        super(SenderEnum.ALL, pathFinder, "warp");
        cooldown = new ArrayList<>();
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        int damageSafeTime = configInt("warp.damage-safe-time") * 20;
        int cooldownTime = configInt("warp.cooldown") * 20;

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("pathfinder.warp")) return;

            if (cooldown.contains(player.getName())) {
                player.sendMessage("§4You have warp cooldown for §c" + cooldownTime / 20 + " §4seconds");
                return;
            }

            if (strings.length == 2) {
                if (!player.hasPermission("pathfinder.warp.other")) return;

                Player target = Bukkit.getPlayerExact(strings[1]);
                String warpName = strings[0];

                if (target == null || !target.isOnline() || target.getName().equals(player.getName())) return;

                if (!isSet("warps." + warpName + ".x")) {
                    player.sendMessage("§4Warp not found!");
                    return;
                }

                CustomLocation warplocation = PathFinderUtils.extractLocation("warps." + warpName, getPathFinder().getBaseConfig());


                if (configBoolean("warp.only-safe-location")) {
                    if (!warplocation.isSafe()) {
                        player.sendMessage("§4This location is unsafe!");
                        return;
                    }
                }

                target.teleport(warplocation);
                target.sendMessage("§aTeleported to warp §e" + warpName + " §aby §e" + player.getName());
                player.sendMessage("§aTarget teleported to warp §e" + warpName);
                target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, damageSafeTime, 255, false, false));
            } else if (strings.length == 1) {
                String warpName = strings[0];

                if (!isSet("warps." + warpName + ".x")) {
                    player.sendMessage("§4Warp not found!");
                    return;
                }

                CustomLocation warplocation = PathFinderUtils.extractLocation("warps." + warpName, getPathFinder().getBaseConfig());


                if (configBoolean("warp.only-safe-location")) {
                    if (!warplocation.isSafe()) {
                        player.sendMessage("§4This location is unsafe!");
                        return;
                    }
                }

                player.teleport(warplocation);
                player.sendMessage("§aTeleported to warp §e" + warpName);
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, damageSafeTime, 255, false, false));

                if (!player.hasPermission("warp.ignore.cooldown")) {
                    cooldown.add(player.getName());
                }

                Bukkit.getScheduler().runTaskLater(getPathFinder(), () -> cooldown.remove(player.getName()), cooldownTime);
            } else {
                player.sendMessage("§cSyntax error");
            }
        } else if (sender instanceof ConsoleCommandSender) {
            if (strings.length == 2) {
                Player player = Bukkit.getPlayerExact(strings[1]);
                String warpName = strings[0];

                if (player == null || !player.isOnline() || player.getName().equals(player.getName())) return;

                if (!isSet("warps." + warpName + ".x")) {
                    player.sendMessage("§4Warp not found!");
                    return;
                }

                CustomLocation warplocation = PathFinderUtils.extractLocation("warps." + warpName, getPathFinder().getBaseConfig());


                if (configBoolean("warp.only-safe-location")) {
                    if (!warplocation.isSafe()) {
                        player.sendMessage("§4This location is unsafe!");
                        return;
                    }
                }

                player.teleport(warplocation);
                player.sendMessage("§aTeleported to warp §e" + warpName + " §aby §e" + player.getName());
                sender.sendMessage("§aTarget teleported to warp §e" + warpName);
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, damageSafeTime, 255, false, false));
            }
        }
    }
}
