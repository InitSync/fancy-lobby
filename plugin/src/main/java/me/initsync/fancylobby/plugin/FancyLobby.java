package me.initsync.fancylobby.plugin;

import me.initsync.fancylobby.api.FancyLobbyProvider;
import me.initsync.fancylobby.api.model.adapt.ServerAdapt;
import me.initsync.fancylobby.api.model.manager.ActionManager;
import me.initsync.fancylobby.api.model.manager.LanguageManager;
import me.initsync.fancylobby.api.model.manager.NameTagManager;
import me.initsync.fancylobby.api.model.manager.ScoreboardManager;
import me.initsync.fancylobby.api.model.manager.TabManager;
import me.initsync.fancylobby.plugin.actions.SoundAction;
import me.initsync.fancylobby.plugin.listener.PlayerJoinListener;
import me.initsync.fancylobby.plugin.loaders.EventFactory;
import me.initsync.fancylobby.plugin.managers.ScoreboardManagerImpl;
import me.initsync.fancylobby.plugin.managers.SimpleActionManager;
import me.initsync.fancylobby.plugin.managers.SimpleAdaptManager;
import me.initsync.fancylobby.plugin.managers.SimpleLanguageManager;
import net.xconfig.bukkit.XConfigBukkit;
import net.xconfig.bukkit.model.config.ConfigurationHandler;
import net.xconfig.bukkit.model.config.ConfigurationManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class FancyLobby extends JavaPlugin implements me.initsync.fancylobby.api.FancyLobby {
	private final String release = getDescription().getVersion();
	
	private static FancyLobby plugin;
	
	private ConfigurationManager configurationManager;
	private ConfigurationHandler configurationHandler;
	private ServerAdapt serverAdapt;
	private ScoreboardManager scoreboardManager;
	private ActionManager actionManager;
	private TabManager tabManager;
	private NameTagManager nameTagManager;
	private LanguageManager languageManager;
	
	public static FancyLobby getPlugin() {
		if (plugin == null) throw new IllegalStateException("The FancyLobby instance is null.");
		
		return plugin;
	}

	@Override
	public String getRelease() {
		return release;
	}
	
	@Override
	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}
	
	@Override
	public ConfigurationHandler getConfigurationHandler() {
		return configurationHandler;
	}
	
	@Override
	public ServerAdapt getServerAdapt() {
		return serverAdapt;
	}
	
	@Override
	public ScoreboardManager getScoreboardManager() {
		return scoreboardManager;
	}
	
	@Override
	public void onLoad() {
		plugin = this;
		
		FancyLobbyProvider.register(plugin);
		
		configurationManager = XConfigBukkit.newConfigurationManager(plugin);
		configurationHandler = XConfigBukkit.newConfigurationHandler(configurationManager);
		serverAdapt = new SimpleAdaptManager().find().getAdapt();
		actionManager = new SimpleActionManager(plugin);
		languageManager = new SimpleLanguageManager();
		scoreboardManager = new ScoreboardManagerImpl(plugin, configurationHandler, languageManager);
	}
	
	@Override
	public void onEnable() {
		configurationManager.build("languages", "en_us.yml", "es_es.yml");
		configurationManager.build("", "config.yml", "commands.yml", "tablist.yml", "scoreboard.yml", "items.yml", "world.yml");
	
		actionManager.register(new SoundAction());

		EventFactory.newLoader(plugin)
			 .providers(new PlayerJoinListener(languageManager, scoreboardManager))
			 .load();
	}
	
	@Override
	public void onDisable() {
		FancyLobbyProvider.unregister();
		
		if (configurationManager != null) configurationManager = null;
		if (configurationHandler != null) configurationHandler = null;
		
		if (serverAdapt != null) serverAdapt = null;
		
		if (scoreboardManager != null) {
			scoreboardManager.clearCachedData();
			scoreboardManager = null;
		}
		
		if (actionManager != null) {
			actionManager.unregisterAll();
			actionManager = null;
		}
		
		if (tabManager != null) {
			tabManager.clearCachedTasks();
			tabManager = null;
		}
		
		if (nameTagManager != null) nameTagManager = null;
		
		if (languageManager != null) {
			languageManager.getCachedLanguages().clear();
			languageManager = null;
		}
		
		if (plugin != null) plugin = null;
	}
}
