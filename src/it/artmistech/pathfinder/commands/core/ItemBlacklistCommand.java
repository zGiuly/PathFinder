package it.artmistech.pathfinder.commands.core;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class ItemBlacklistCommand extends AbstractCommand {
    private static Map<String, List<String>> blockBlacklist;

    public ItemBlacklistCommand(PathFinder pathFinder) {
        super(SenderEnum.ALL, pathFinder, "pickup");
        blockBlacklist = new HashMap<>();
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player) sender;

        if (!player.hasPermission("pathfinder.pickup")) return;

        if (strings.length == 2) {
            if (strings[0].equalsIgnoreCase("add")) {
                if (!blockBlacklist.containsKey(player.getName())) {
                    String blockType = strings[1].toUpperCase();
                    Material material = null;
                    try {
                        material = Material.valueOf(blockType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (material == null) return;

                    blockBlacklist.put(player.getName(), Arrays.asList(blockType));
                } else {
                    String blockType = strings[1].toUpperCase();
                    Material material = null;
                    try {
                        material = Material.valueOf(blockType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (material == null) return;

                    for (String s : blockBlacklist.get(player.getName())) {
                        if (s.equals(blockType)) {
                            player.sendMessage("§aBlock already in blacklist");
                            return;
                        }
                    }

                    blockBlacklist.get(player.getName()).add(blockType);

                    player.sendMessage("§aBlacklist updated");
                }
            } else if (strings[0].equalsIgnoreCase("remove")) {
                if (blockBlacklist.containsKey(player.getName())) {
                    String blockType = strings[1].toUpperCase();

                    boolean find = false;

                    for (String s : blockBlacklist.get(player.getName())) {
                        if (s.equals(blockType)) {
                            find = true;
                            blockBlacklist.get(player.getName()).remove(s);
                            break;
                        }
                    }

                    if (!find) {
                        player.sendMessage("§cNo value found");
                        return;
                    }

                    player.sendMessage("§aBlacklist updated");
                }
            }
        }
    }

    public static Map<String, List<String>> getBlockBlacklist() {
        return blockBlacklist;
    }
}
