package me.initsync.fancylobby.api.event.visual;

import me.initsync.fancylobby.api.model.visual.scoreboard.FancyBoard;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Objects;

public class ScoreboardTitleChangeEvent extends Event implements Cancellable {
	private static final HandlerList HANDLER_LIST = new HandlerList();
	
	private final FancyBoard board;
	private final String oldTitle;
	private final String newTitle;
	
	private boolean cancelled;
	
	public ScoreboardTitleChangeEvent(FancyBoard board, String oldTitle, String newTitle) {
		this.board = Objects.requireNonNull(board, "The board is null.");
		this.oldTitle = Objects.requireNonNull(oldTitle, "The old title is null.");
		this.newTitle = Objects.requireNonNull(newTitle, "The new title is null.");
	}
	
	/**
	 * Returns the FancyBoard object of the player.
	 *
	 * @return The FancyBoard object.
	 */
	public FancyBoard getBoard() {
		return board;
	}
	
	/**
	 * Returns the old title of the scoreboard.
	 *
	 * @return The old title.
	 */
	public String getOldTitle() {
		return oldTitle;
	}
	
	/**
	 * Returns the new title of the scoreboard.
	 *
	 * @return The new title.
	 */
	public String getNewTitle() {
		return newTitle;
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
