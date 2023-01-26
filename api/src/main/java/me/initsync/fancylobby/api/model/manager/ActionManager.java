package me.initsync.fancylobby.api.model.manager;

import me.initsync.fancylobby.api.action.ActionContext;
import me.initsync.fancylobby.api.action.ActionExecutable;
import org.bukkit.entity.Player;

import java.util.List;

public interface ActionManager {
	/**
	 * Register an array of ActionExecutable.
	 *
	 * @param executables Array of ActionExecutable (implementations).
	 */
	default void register(ActionExecutable... executables) {
		for (ActionExecutable executable : executables) {
			register(executable.getContext(), executable);
		}
	}
	
	/**
	 * Executes a list of actions.
	 *
	 * @param player Player object.
	 * @param containers The actions list.
	 */
	default void execute(Player player, List<String> containers) {
		containers.forEach(container -> execute(player, container));
	}
	
	/**
	 * Unregister multiple actions using an array of ActionContext.
	 *
	 * @param contexts An array of ActionContext that contains the context type of the actions to register.
	 */
	default void unregister(ActionContext... contexts) {
		for (ActionContext context : contexts) {
			unregister(contexts);
		}
	}
	
	/**
	 * Register a new action.
	 *
	 * @param context Context type of the action.
	 * @param executable Executable implementation of the action.
	 */
	void register(ActionContext context, ActionExecutable executable);
	
	/**
	 * Executes the action that there on the container.
	 *
	 * @param player Player object.
	 * @param container The action container.
	 */
	void execute(Player player, String container);
	
	/**
	 * Unregister an action specifying her context type.
	 *
	 * @param context Context type of the action to unregister.
	 */
	void unregister(ActionContext context);
	
	/**
	 * Unregister all the actions.
	 */
	void unregisterAll();
}
