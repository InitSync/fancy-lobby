package me.initsync.fancylobby.plugin.factory;

import me.initsync.fancylobby.plugin.FancyLobby;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.Objects;

public interface EventFactory {
	static Loader newLoader(FancyLobby plugin) {
		return new Loader(plugin);
	}
	
	class Loader {
		private final FancyLobby plugin;
		
		private Listener[] listeners;
		
		public Loader(FancyLobby plugin) {
			this.plugin = Objects.requireNonNull(plugin, "The FancyLobby instance is null.");
		}
		
		public Loader providers(Listener... listeners) {
			this.listeners = Objects.requireNonNull(listeners, "The listeners array is null.");
			return this;
		}
		
		public void load() {
			if (listeners == null) return;
			
			final PluginManager manager = plugin.getServer().getPluginManager();
			for (Listener listener : listeners) manager.registerEvents(listener, plugin);
		}
	}
}
