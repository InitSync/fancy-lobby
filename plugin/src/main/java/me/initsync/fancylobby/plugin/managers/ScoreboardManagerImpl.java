package me.initsync.fancylobby.plugin.managers;

import me.initsync.fancylobby.api.event.visual.ScoreboardTitleChangeEvent;
import me.initsync.fancylobby.api.event.visual.ScoreboardToggleEvent;
import me.initsync.fancylobby.api.model.manager.LanguageManager;
import me.initsync.fancylobby.api.model.manager.ScoreboardManager;
import me.initsync.fancylobby.api.model.visual.scoreboard.FancyBoard;
import me.initsync.fancylobby.plugin.FancyLobby;
import me.initsync.fancylobby.plugin.tasks.ScoreboardTitleTask;
import me.initsync.fancylobby.plugin.utils.PlaceholderUtils;
import net.xconfig.bukkit.model.config.ConfigurationHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class ScoreboardManagerImpl implements ScoreboardManager {
	private final FancyLobby plugin;
	private final ConfigurationHandler configurationHandler;
	private final LanguageManager languageManager;
	private final Map<UUID, FancyBoard> cachedBoards;
	
	private Map<UUID, BukkitTask> cachedBoardTasks;
	private Map<UUID, ScoreboardTitleTask> cachedTitleTasks;
	
	public ScoreboardManagerImpl(FancyLobby plugin, ConfigurationHandler configurationHandler, LanguageManager languageManager) {
		this.plugin = Objects.requireNonNull(plugin, "The FancyLobby instance is null.");
		this.configurationHandler = Objects.requireNonNull(configurationHandler, "The ConfigurationHandler object is null.");
		this.languageManager = Objects.requireNonNull(languageManager, "The LanguageManager object is null.");
		
		cachedBoards = new HashMap<>();
		if (configurationHandler.condition("", "scoreboard.yml", "allow-body-refresh")) cachedBoardTasks = new HashMap<>();
		if (configurationHandler.condition("", "scoreboard.yml", "allow-animated-title")) cachedTitleTasks = new HashMap<>();
	}
	
	@Override
	public FancyBoard getScoreboard(UUID uuid) {
		return cachedBoards.getOrDefault(uuid, null);
	}
	
	@Override
	public void create(Player player) {
		checkNotNull(player, "The player is null.");
		
		if (!configurationHandler.textList("", "scoreboard.yml", "allowed-worlds", false).contains(player.getWorld().getName())) {
			return;
		}
		
		final UUID playerId = player.getUniqueId();
		
		final FancyBoard board = new FancyBoard(player);
		cachedBoards.put(playerId, board);
		
		final FileConfiguration languageConfig = languageManager.getPlayerLanguage(playerId).getLanguageConfig();
		
		if (configurationHandler.condition("", "scoreboard.yml", "allow-animated-title")) {
			cachedTitleTasks.put(
				 playerId,
				 new ScoreboardTitleTask(
					  board,
					  languageConfig.getStringList("scoreboard.title-animated"),
					  configurationHandler.number("", "scoreboard.yml", "title-refresh-rate")
				 )
			);
			
			board.updateTitle(board.getTitle());
		}
		else board.updateTitle(PlaceholderUtils.parse(player, languageConfig.getString("scoreboard.default-title")));
		
		Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
			board.updateLines(PlaceholderUtils.parse(player, languageConfig.getString("scoreboard.lines")));
		}, 0L, configurationHandler.number("", "scoreboard.yml", "body-refresh-rate"));
	}
	
	@Override
	public boolean toggle(Player player) {
		checkNotNull(player, "The player is null.");
		
		final UUID playerId = player.getUniqueId();
		FancyBoard board = getScoreboard(playerId);
		
		final ScoreboardToggleEvent event = new ScoreboardToggleEvent(board);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled()) return false;
		
		if (board != null) {
			event.setEnabled(false);
			board = null;
			remove(playerId);
			return false;
		}
		
		event.setEnabled(true);
		create(player);
		return true;
	}
	
	@Override
	public void modifyTitle(UUID uuid, String newTitle) {
		checkNotNull(uuid, "The uuid is null.");
		checkNotNull(newTitle, "The title text is null.");
		
		if (configurationHandler.condition("", "scoreboard.yml", "allow-animated-title")) return;
		
		final FancyBoard board = getScoreboard(uuid);
		if (board == null) return;
		
		final ScoreboardTitleChangeEvent event = new ScoreboardTitleChangeEvent(board, board.getTitle(), newTitle);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled()) return;
		
		board.updateTitle(PlaceholderUtils.parse(board.getPlayer(), newTitle));
	}
	
	@Override
	public void remove(UUID uuid) {
		checkNotNull(uuid, "The uuid is null.");
		
		if (!cachedBoards.containsKey(uuid)) return;
		
		if (configurationHandler.condition("", "scoreboard.yml", "allow-animated-title")) {
			ScoreboardTitleTask titleTask = cachedTitleTasks.remove(uuid);
			if (titleTask == null) return;
			
			titleTask.cancel();
			titleTask = null;
		}
		
		if (configurationHandler.condition("", "scoreboard.yml", "allow-body-refresh")) {
			BukkitTask bodyTask = cachedBoardTasks.remove(uuid);
			if (bodyTask == null) return;
			
			bodyTask.cancel();
			bodyTask = null;
		}
		
		FancyBoard board = cachedBoards.remove(uuid);
		if (board == null) return;
		
		board.delete();
		board = null;
	}
	
	@Override
	public void clearCachedData() {
		if (configurationHandler.condition("", "scoreboard.yml", "allow-body-refresh")) cachedBoardTasks.clear();
		if (configurationHandler.condition("", "scoreboard.yml", "allow-animated-title")) cachedTitleTasks.clear();
		
		cachedBoards.clear();
	}
}
