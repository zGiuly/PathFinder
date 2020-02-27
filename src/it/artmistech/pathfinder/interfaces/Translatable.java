package it.artmistech.pathfinder.interfaces;

import org.bukkit.entity.Player;

public interface Translatable {
    /**
     * Get a message from config
     * @return string
     */
    String getMessage();

    /**
     * Get a message from config with replace %player% to player name
     * @param player
     * @return
     */
    String getMessageWithPlayer(Player player);

    /**
     * Get a message from config with replace a param with String
     * @param param
     * @param replace
     * @return
     */
    String getMessageWithReplace(String param, String replace);
}
