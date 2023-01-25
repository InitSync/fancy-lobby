package me.initsync.fancylobby.plugin.managers;

import com.google.common.base.Preconditions;
import me.initsync.fancylobby.api.action.ActionContext;
import me.initsync.fancylobby.api.action.ActionExecutable;
import me.initsync.fancylobby.api.model.manager.ActionManager;
import me.initsync.fancylobby.plugin.FancyLobby;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

public class SimpleActionManager implements ActionManager {
	private final FancyLobby plugin;
	private final Map<ActionContext, ActionExecutable> actions;
	
	public SimpleActionManager(FancyLobby plugin) {
		this.plugin = Objects.requireNonNull(plugin, "The FancyLobby instance is null.");
		actions = new HashMap<>();
	}
	
	@Override
	public void register(ActionContext context, ActionExecutable executable) {
		checkNotNull(context, "The context type of that action is null.");
		checkNotNull(context, "The context type of that action is null.");
		
		actions.put(context, executable);
	}
	
	@Override
	public void execute(Player player, String container) {
		checkNotNull(player, "The player is null.");
		checkNotNull(container, "The action container is null.");
		
		Preconditions.checkArgument(!container.isEmpty(), "The action container is empty.");
		
		actions.get(ActionContext.valueOf(StringUtils.substringBetween(container, "[", "]"))).execute(plugin, player, container);
	}
	
	@Override
	public void unregister(ActionContext context) {
		checkNotNull(context, "The context type of that action is null.");
		
		actions.remove(context);
	}
	
	@Override
	public void unregisterAll() {
		actions.clear();
	}
}
