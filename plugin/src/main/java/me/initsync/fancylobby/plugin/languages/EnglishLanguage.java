package me.initsync.fancylobby.plugin.languages;

import me.initsync.fancylobby.api.language.AbstractLanguage;
import me.initsync.fancylobby.api.language.Language;
import net.xconfig.bukkit.model.config.ConfigurationManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public class EnglishLanguage extends AbstractLanguage {
	private final ConfigurationManager configurationManager;
	
	public EnglishLanguage(ConfigurationManager configurationManager) {
		this.configurationManager = Objects.requireNonNull(configurationManager, "The ConfigurationManager object is null.");
	}
	
	@Override
	public FileConfiguration getLanguageConfig() {
		return configurationManager.file("languages", "en_us.yml");
	}
	
	@Override
	public Language getLanguage() {
		return Language.ENGLISH;
	}
}
