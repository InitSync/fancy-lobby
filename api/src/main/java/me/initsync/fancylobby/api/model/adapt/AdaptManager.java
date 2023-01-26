package me.initsync.fancylobby.api.model.adapt;

public interface AdaptManager {
	/**
	 * Installs a single adapt for the server version where the plugin is running.
	 * <p>
	 * If the server version is 1.17 or higher, the adapt for the server will not be installed.
	 *
	 * @param adapt The ServerAdapt object of the version to use.
	 * @return this
	 */
	AdaptManager of(ServerAdapt adapt);
	
	/**
	 * Finds automatically an adapt for the current server version where the plugin is running.
	 * <p>
	 * If the server version is 1.17 or higher, the adapt for the server will not be searched or installed.
	 *
	 * @return this
	 */
	AdaptManager find();
	
	/**
	 * Returns the current adapt for this server.
	 *
	 * @return A ServerAdapt object. If there are not any adapt installed will be throws an IllegalStateException.
	 */
	ServerAdapt getAdapt();
}
