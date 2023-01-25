package me.initsync.fancylobby.api.model.manager;

import org.bukkit.entity.Player;

public interface NameTagManager {
	/**
	 * Establish a new name-tag format to the player.
	 *
	 * @param player Player object.
	 * @param prefix New Prefix Format.
	 * @param suffix New Suffix Format.
	 */
	void setTag(Player player, String prefix, String suffix);
	
	/**
	 * Removes the current name-tag format to the player.
	 *
	 * @param player Player object.
	 */
	void removeTag(Player player);
}
