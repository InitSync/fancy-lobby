package me.initsync.fancylobby.api.event.visual;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Objects;

public class TabListCreateEvent extends Event implements Cancellable {
	private static final HandlerList HANDLER_LIST = new HandlerList();
	
	private final Player player;
	private final String header;
	private final String footer;
	
	private boolean cancelled;
	
	public TabListCreateEvent(Player player, String header, String footer) {
		this.player = Objects.requireNonNull(player, "The player is null.");
		this.header = Objects.requireNonNull(header, "The header is null.");
		this.footer = Objects.requireNonNull(footer, "The footer is null.");
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
	 * Returns the header text of the tab-list.
	 *
	 * @return The header text.
	 */
	public String getHeader() {
		return header;
	}
	
	/**
	 * Returns the footer text of the tab-list.
	 *
	 * @return The footer text.
	 */
	public String getFooter() {
		return footer;
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
