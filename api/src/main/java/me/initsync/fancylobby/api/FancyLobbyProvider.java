package me.initsync.fancylobby.api;

import com.google.common.base.Preconditions;

public class FancyLobbyProvider {
	private static FancyLobby plugin;
	
	private FancyLobbyProvider() {}
	
	/**
	 * Sets a parameter value (if isn't null) for the 'plugin' field.
	 *
	 * @param instance A FancyLobby instance.
	 */
	public static void register(FancyLobby instance) {
		Preconditions.checkNotNull(plugin, "The instance of FancyLobby object is null.");
		
		plugin = instance;
	}
	
	/**
	 * Returns the FancyLobby instance.
	 *
	 * @return The FancyLobby instance, will be return null if the instance isn't registered (initialized).
	 */
	public static FancyLobby get() {
		return plugin;
	}
	
	/**
	 * Sets the 'plugin' field as null.
	 */
	public static void unregister() {
		plugin = null;
	}
}
