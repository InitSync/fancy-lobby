package me.initsync.fancylobby.api.event.commands;

import com.google.common.base.Preconditions;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Objects;

public class CommandExecuteEvent extends Event implements Cancellable {
	private static final HandlerList HANDLER_LIST = new HandlerList();
	
	private final String customCommand;
	
	private boolean cancelled;
	
	public CommandExecuteEvent(String customCommand) {
		this.customCommand = Objects.requireNonNull(customCommand, "The custom command is null.");
		Preconditions.checkArgument(!customCommand.isEmpty(), "The custom command is empty.");
	}
	
	/**
	 * Returns the custom command of this event.
	 *
	 * @return The custom command.
	 */
	public String getCustomCommand() {
		return customCommand;
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
