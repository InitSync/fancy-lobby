package me.initsync.fancylobby.api.event.visual;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Objects;

public class NameTagCreateEvent extends Event implements Cancellable {
	private static final HandlerList HANDLER_LIST = new HandlerList();
	
	private final Player player;
	private final String prefix;
	private final String suffix;
	
	private boolean cancelled;
	
	public NameTagCreateEvent(Player player, String prefix, String suffix) {
		this.player = Objects.requireNonNull(player, "The player is null.");
		this.prefix = Objects.requireNonNull(prefix, "The prefix is null.");
		this.suffix = Objects.requireNonNull(suffix, "The suffix is null.");
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
	 * Returns the prefix of the name-tag.
	 *
	 * @return The prefix.
	 */
	public String getPrefix() {
		return prefix;
	}
	
	/**
	 * Returns the suffix of the name-tag.
	 *
	 * @return The suffix.
	 */
	public String getSuffix() {
		return suffix;
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
