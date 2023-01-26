package me.initsync.fancylobby.api.language;

import org.bukkit.configuration.file.FileConfiguration;

public abstract class AbstractLanguage {
	/**
	 * Returns the FileConfiguration object for this file
	 *
	 * @return The FileConfiguration object.
	 */
	public abstract FileConfiguration getLanguageConfig();
	
	/**
	 * Returns the enum for this language.
	 *
	 * @return The enum.
	 */
	public abstract Language getLanguage();
}
