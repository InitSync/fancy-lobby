package me.initsync.fancylobby.api.event.visual;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Objects;

public class TabListToggleEvent extends Event implements Cancellable {
	private static final HandlerList HANDLER_LIST = new HandlerList();
	
	private final Player player;
	
	private boolean enabled;
	private boolean cancelled;
	
	public TabListToggleEvent(Player player) {
		this.player = Objects.requireNonNull(player, "The player is null.");
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
	 * Checks if the tab-list is enabled.
	 *
	 * @return True if it is enabled, else return false.
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * Sets as enabled/disabled the tab-list.
	 *
	 * @param enable (True) marks as 'enabled' the tab-list. (False) marks as 'disabled' the tab-list.
	 */
	public void setEnabled(boolean enable) {
		enabled = enable;
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
