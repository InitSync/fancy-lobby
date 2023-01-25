package me.initsync.fancylobby.plugin.managers;

import me.initsync.fancylobby.api.FancyLobby;
import me.initsync.fancylobby.api.FancyLobbyProvider;
import me.initsync.fancylobby.api.language.AbstractLanguage;
import me.initsync.fancylobby.api.model.manager.LanguageManager;
import me.initsync.fancylobby.plugin.languages.EnglishLanguage;
import me.initsync.fancylobby.plugin.languages.SpanishLanguage;
import me.initsync.fancylobby.plugin.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class SimpleLanguageManager implements LanguageManager {
	private final Map<UUID, AbstractLanguage> cachedLanguages;
	
	public SimpleLanguageManager() {
		cachedLanguages = new HashMap<>();
	}
	
	@Override
	public AbstractLanguage getPlayerLanguage(UUID uuid) {
		checkNotNull(uuid, "The uuid is null.");
		
		return cachedLanguages.getOrDefault(uuid, null);
	}
	
	@Override
	public void applyDefaultLanguage(UUID uuid) {
		checkNotNull(uuid, "The uuid is null.");
		
		if (cachedLanguages.containsKey(uuid)) return;
		
		final FancyLobby provider = FancyLobbyProvider.get();
		
		switch (provider.getConfigurationHandler().text("", "config.yml", "config.default-language", "en_us", false).toLowerCase()) {
			default:
				LogUtils.error("The default language id specified isn't valid!");
				break;
			case "en_us":
				cachedLanguages.put(uuid, new EnglishLanguage(provider.getConfigurationManager()));
				break;
			case "es_es": cachedLanguages.put(uuid, new SpanishLanguage(provider.getConfigurationManager()));
		}
	}
	
	@Override
	public void updateLanguage(UUID uuid, AbstractLanguage language) {
		checkNotNull(uuid, "The uuid is null.");
		checkNotNull(language, "The language to update is null.");
		
		if (cachedLanguages.containsKey(uuid) || cachedLanguages.get(uuid).equals(language)) return;
		
		cachedLanguages.remove(uuid);
		cachedLanguages.put(uuid, language);
	}
	
	@Override
	public Map<UUID, AbstractLanguage> getCachedLanguages() {
		return cachedLanguages;
	}
}
