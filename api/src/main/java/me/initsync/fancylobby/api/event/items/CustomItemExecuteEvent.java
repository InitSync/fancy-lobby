package me.initsync.fancylobby.api.event.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Objects;

public class CustomItemExecuteEvent extends Event implements Cancellable {
	private static final HandlerList HANDLER_LIST = new HandlerList();
	
	private final Player player;
	private final String displayName;
	private final Material type;
	
	private boolean cancelled;
	
	public CustomItemExecuteEvent(Player player, String displayName, Material type) {
		this.player = Objects.requireNonNull(player, "The player is null.");
		this.displayName = Objects.requireNonNull(displayName, "The display name is null.");
		this.type = Objects.requireNonNull(type, "The material type is null.");
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
	 * Returns the display name of the item.
	 *
	 * @return The display name.
	 */
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * Returns the material type of the item.
	 *
	 * @return The material type.
	 */
	public Material getType() {
		return type;
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
