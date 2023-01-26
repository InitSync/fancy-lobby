package me.initsync.fancylobby.api.event.disguise;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Objects;

public class NameDisguiseEvent extends Event {
	private static final HandlerList HANDLER_LIST = new HandlerList();
	
	private final String oldName;
	private final String disguisedName;
	private final boolean random;
	
	public NameDisguiseEvent(String oldName, String disguisedName, boolean random) {
		this.oldName = Objects.requireNonNull(oldName, "The old name is null.");
		this.disguisedName = Objects.requireNonNull(disguisedName, "The disguised name is null.");
		this.random = random;
	}
	
	/**
	 * Returns the old name of player.
	 *
	 * @return The old name.
	 */
	public String getOldName() {
		return oldName;
	}
	
	/**
	 * Returns the disguised name.
	 *
	 * @return The disguised name.
	 */
	public String getDisguisedName() {
		return disguisedName;
	}
	
	/**
	 * Returns if the disguised name is random or not.
	 *
	 * @return A boolean value.
	 */
	public boolean isRandom() {
		return random;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}
	
	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}
}
