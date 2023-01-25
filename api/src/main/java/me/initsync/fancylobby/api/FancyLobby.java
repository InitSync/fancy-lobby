package me.initsync.fancylobby.api;

import me.initsync.fancylobby.api.model.adapt.ServerAdapt;
import me.initsync.fancylobby.api.model.manager.ScoreboardManager;
import net.xconfig.bukkit.model.config.ConfigurationHandler;
import net.xconfig.bukkit.model.config.ConfigurationManager;

public interface FancyLobby {
	/**
	 * Returns the current release/version of plugin.
	 *
	 * @return The current release that's being used.
	 */
	String getRelease();
	
	/**
	 * Returns the ConfigurationManager object.
	 *
	 * @return The ConfigurationManager
	 */
	ConfigurationManager getConfigurationManager();
	
	/**
	 * Returns the ConfigurationHandler object.
	 *
	 * @return The ConfigurationHandler.
	 */
	ConfigurationHandler getConfigurationHandler();
	
	/**
	 * Returns the ServerAdapt object for this server.
	 *
	 * @return The adapt for this server.
	 */
	ServerAdapt getServerAdapt();
	
	/**
	 * Returns the ScoreboardManager object.
	 *
	 * @return The ScoreboardManager.
	 */
	ScoreboardManager getScoreboardManager();
}
