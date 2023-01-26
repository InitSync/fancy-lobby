package me.initsync.fancylobby.plugin.listener;

import me.initsync.fancylobby.api.model.manager.LanguageManager;
import me.initsync.fancylobby.api.model.manager.ScoreboardManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class PlayerJoinListener implements Listener {
	private final LanguageManager languageManager;
	private final ScoreboardManager scoreboardManager;
	
	public PlayerJoinListener(LanguageManager languageManager, ScoreboardManager scoreboardManager) {
		this.languageManager = Objects.requireNonNull(languageManager, "The LanguageManager object is null.");
		this.scoreboardManager = Objects.requireNonNull(scoreboardManager, "The ScoreboardManager object is null.");
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		
		languageManager.applyDefaultLanguage(player.getUniqueId());
		scoreboardManager.create(player);
	}
}
