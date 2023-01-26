package me.initsync.fancylobby.api.event.commands;

import com.google.common.base.Preconditions;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Objects;

public class CommandUnregisterEvent extends Event implements Cancellable {
	private static final HandlerList HANDLER_LIST = new HandlerList();
	
	private final String command;
	
	private boolean cancelled;
	
	public CommandUnregisterEvent(String command) {
		this.command = Objects.requireNonNull(command, "The command is null.");
		Preconditions.checkArgument(!command.isEmpty(), "The command is empty.");
	}
	
	/**
	 * Returns the command unregistered.
	 *
	 * @return The command unregistered.
	 */
	public String getCommand() {
		return command;
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
