package me.initsync.fancylobby.api.model.manager;

import me.initsync.fancylobby.api.language.AbstractLanguage;

import java.util.Map;
import java.util.UUID;

public interface LanguageManager {
	/**
	 * Returns the language of the current user.
	 *
	 * @param uuid UUID of player.
	 * @return The AbstractLanguage object of player.
	 */
	AbstractLanguage getPlayerLanguage(UUID uuid);
	
	/**
	 * Sets the default language for the new player.
	 *
	 * @param uuid UUID of player.
	 */
	void applyDefaultLanguage(UUID uuid);
	
	/**
	 * Updates the current language of player.
	 *
	 * @param uuid UUID of player.
	 * @param language New language for the player.
	 */
	void updateLanguage(UUID uuid, AbstractLanguage language);
	
	/**
	 * Returns the languages that there on cache.
	 *
	 * @return The cached languages.
	 */
	Map<UUID, AbstractLanguage> getCachedLanguages();
}
