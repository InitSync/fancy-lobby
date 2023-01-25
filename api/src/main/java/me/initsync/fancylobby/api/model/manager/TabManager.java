package me.initsync.fancylobby.api.model.manager;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface TabManager {
	/**
	 * Creates a tab-list using the type specified on the configuration.
	 *
	 * @param player Player object.
	 */
	void select(Player player);
	
	/**
	 * Creates a tab-list using the global format.
	 *
	 * @param player Player object.
	 */
	void createGlobal(Player player);
	
	/**
	 * Creates a tab-list using the format of current world.
	 *
	 * @param player Player object.
	 */
	void createByWorld(Player player);
	
	/**
	 * Removes the tab-list for that player.
	 *
	 * @param uuid UUID of player.
	 */
	void remove(UUID uuid);
	
	/**
	 * Reloads all the tab-list tasks.
	 */
	void reload();
	
	/**
	 * Removes all the cached tasks.
	 */
	void clearCachedTasks();
}
