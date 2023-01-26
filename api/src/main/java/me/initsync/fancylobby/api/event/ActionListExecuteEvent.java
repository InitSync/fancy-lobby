package me.initsync.fancylobby.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;
import java.util.Objects;

public class ActionListExecuteEvent extends Event implements Cancellable {
	private static final HandlerList HANDLER_LIST = new HandlerList();
	
	private final Player player;
	private final List<String> containers;
	
	private boolean cancelled;
	
	public ActionListExecuteEvent(Player player, List<String> containers) {
		this.player = Objects.requireNonNull(player, "The player is null.");
		this.containers = Objects.requireNonNull(containers, "The containers list is null.");
	}
	
	/**
	 * Returns the player of this event.
	 *
	 * @return The player.
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Returns the actions list.
	 *
	 * @return The actions list.
	 */
	public List<String> getContainers() {
		return containers;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}
	
	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}
}
