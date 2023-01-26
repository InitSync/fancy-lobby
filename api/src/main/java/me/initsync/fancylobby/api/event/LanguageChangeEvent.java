package me.initsync.fancylobby.api.event;

import com.google.common.base.Preconditions;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Objects;

public class LanguageChangeEvent extends Event implements Cancellable {
	private static final HandlerList HANDLER_LIST = new HandlerList();
	
	private final String oldLanguage;
	private final String newLanguage;
	
	private boolean cancelled;
	
	public LanguageChangeEvent(String oldLanguage, String newLanguage) {
		this.oldLanguage = Objects.requireNonNull(oldLanguage, "The old language is null.");
		this.newLanguage = Objects.requireNonNull(newLanguage, "The new language is null.");
		
		Preconditions.checkArgument(!oldLanguage.isEmpty(), "The old language is empty.");
		Preconditions.checkArgument(!newLanguage.isEmpty(), "The new language is empty.");
	}
	
	/**
	 * Returns the old language of player.
	 *
	 * @return The old language.
	 */
	public String getOldLanguage() {
		return oldLanguage;
	}
	
	/**
	 * Returns the new language selected.
	 *
	 * @return The new language.
	 */
	public String getNewLanguage() {
		return newLanguage;
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
