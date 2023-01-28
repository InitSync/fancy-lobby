package me.initsync.fancylobby.plugin.factory;

import com.google.common.base.Preconditions;
import me.initsync.fancylobby.plugin.FancyLobby;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import java.util.Objects;

public interface CommandFactory {
	/**
	 * Returns a new Builder.
	 *
	 * @param plugin An instance of FancyLobby.
	 * @return A new Builder instance.
	 */
	static Builder newLoader(FancyLobby plugin) {
		return new Builder(plugin);
	}
	
	class Builder {
		private final FancyLobby plugin;
		
		private String command;
		private CommandExecutor executor;
		private TabCompleter completer;
		
		public Builder(FancyLobby plugin) {
			this.plugin = Objects.requireNonNull(plugin, "The FancyLobby instance is null.");
		}
		
		public Builder command(String command) {
			this.command = Objects.requireNonNull(command, "The command is null.");
			Preconditions.checkArgument(!command.isEmpty(), "The command is empty.");
			return this;
		}
		
		public Builder executor(CommandExecutor executor) {
			this.executor = Objects.requireNonNull(executor, "The command executor is null.");
			return this;
		}
		
		public Builder completer(TabCompleter completer) {
			this.completer = Objects.requireNonNull(completer, "The tab completer is null.");
			return this;
		}
		
		public void register() {
			if (command == null || executor == null) return;
			
			final PluginCommand pluginCommand = plugin.getCommand(command);
			if (pluginCommand == null) return;
			
			pluginCommand.setExecutor(executor);
			
			if (completer != null) pluginCommand.setTabCompleter(completer);
		}
	}
}
