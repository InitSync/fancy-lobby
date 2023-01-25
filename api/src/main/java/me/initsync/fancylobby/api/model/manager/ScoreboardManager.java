package me.initsync.fancylobby.api.model.manager;

import me.initsync.fancylobby.api.model.visual.scoreboard.FancyBoard;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface ScoreboardManager {
	/**
	 * Returns the scoreboard of this user.
	 *
	 * @param uuid UUID of player.
	 * @return The scoreboard of player, if the player doesn't have an scoreboard will be return null.
	 */
	FancyBoard getScoreboard(UUID uuid);
	
	/**
	 * Creates a scoreboard using the format established on the configuration.
	 *
	 * @param player Player object.
	 */
	void create(Player player);
	
	/**
	 * Toggles the scoreboard visibility for that player.
	 *
	 * @param player Player object.
	 * @return True if the scoreboard is enabled, else return false.
	 */
	boolean toggle(Player player);
	
	/**
	 * Modifies the scoreboard title for this user. If the title-animation is enabled or the event is cancelled, can't do this.
	 *
	 * @param uuid UUID of player.
	 * @param newTitle New Scoreboard Title.
	 */
	void modifyTitle(UUID uuid, String newTitle);
	
	/**
	 * Removes the player scoreboard.
	 *
	 * @param uuid UUID of player.
	 */
	void remove(UUID uuid);
	
	/**
	 * Clear the cached data of the scoreboards. FancyBoard objects, tasks and more.
	 */
	void clearCachedData();
}
