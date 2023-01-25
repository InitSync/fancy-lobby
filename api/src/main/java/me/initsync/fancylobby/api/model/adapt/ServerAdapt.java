package me.initsync.fancylobby.api.model.adapt;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public interface ServerAdapt {
	/**
	 * Just sends a title/subtitle to the player with a time defined.
	 *
	 * @param player Player object.
	 * @param title Title message.
	 * @param subtitle Subtitle message.
	 */
	default void sendTitle(Player player, String title, String subtitle) {
		sendTitle(player, title, subtitle, 10, 60, 10);
	}
	
	/**
	 * Sends a title to the player.
	 *
	 * @param player Player object.
	 * @param title Title message.
	 * @param subtitle Subtitle message.
	 * @param fadeIn In-coming title time.
	 * @param stay Staying title time.
	 * @param fadeOut Outing title time.
	 */
	void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut);
	
	/**
	 * Sends an actionbar text to the player.
	 *
	 * @param plugin A JavaPlugin instance.
	 * @param player Player object.
	 * @param message ActionBar Message.
	 */
	void sendActionBar(JavaPlugin plugin, Player player, String message);
	
	/**
	 * Modifies the header/footer of the tab-list of player.
	 *
	 * @param player Player object.
	 * @param footer Tab-List Footer Text.
	 * @param header Tab-List Header Text.
	 */
	void setTabList(Player player, String footer, String header);
	
	/**
	 * Modifies the name-tag of player adding a prefix/suffix to this.
	 *
	 * @param player Player object.
	 * @param prefix NameTag Prefix.
	 * @param suffix NameTag Suffix.
	 */
	void setPlayerTag(Player player, String prefix, String suffix);
}
