package me.initsync.fancylobby.api.event.visual;

import me.initsync.fancylobby.api.model.visual.scoreboard.FancyBoard;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Objects;

public class ScoreboardToggleEvent extends Event implements Cancellable {
	private static final HandlerList HANDLER_LIST = new HandlerList();
	
	private final FancyBoard board;
	
	private boolean enabled;
	private boolean cancelled;
	
	public ScoreboardToggleEvent(FancyBoard board) {
		this.board = board;
	}
	
	/**
	 * Returns the FancyBoard object of this player.
	 *
	 * @return The FancyBoard object. Can be null if the board was removed.
	 */
	public FancyBoard getBoard() {
		return board;
	}
	
	/**
	 * Checks if the scoreboard is enabled.
	 *
	 * @return True if it is enabled, else return false.
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * Sets as enabled/disabled the scoreboard.
	 *
	 * @param enable (True) marks as 'enabled' the scoreboard. (False) marks as 'disabled' the scoreboard.
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
