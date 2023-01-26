package me.initsync.fancylobby.api.action;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public interface ActionExecutable {
	/**
	 * Returns the context type of this action.
	 *
	 * @return The context type.
	 */
	ActionContext getContext();
	
	/**
	 * Executes the action content.
	 *
	 * @param plugin A JavaPlugin instance.
	 * @param player Player object.
	 * @param container The action container.
	 */
	void execute(JavaPlugin plugin, Player player, String container);
}
