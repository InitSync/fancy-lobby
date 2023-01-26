package me.initsync.fancylobby.plugin.utils;

import me.initsync.fancylobby.api.FancyLobbyProvider;
import me.initsync.fancylobby.api.model.adapt.ServerAdapt;
import me.initsync.fancylobby.plugin.FancyLobby;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static me.initsync.fancylobby.plugin.utils.PlaceholderUtils.parse;

public class Utils {
	private static final ServerAdapt ADAPT = FancyLobbyProvider.get().getServerAdapt();
	private static final FancyLobby PLUGIN = FancyLobby.getPlugin();
	
	private Utils() {}
	
	public static void sendTitle(Player player, int fadeIn, int stay, int fadeOut, String title, String subtitle) {
		ADAPT.sendTitle(player, parse(player, title), parse(player, subtitle), fadeIn, stay, fadeOut);
	}
	
	public static void sendActionBar(Player player, String message, long duration) {
		if (duration < 1) return;
		
		new BukkitRunnable() {
			long repeater = duration;
			
			@Override
			public void run() {
				ADAPT.sendActionBar(PLUGIN, player, parse(player, message));
				
				repeater -= 40L;
				if (repeater - 40L < -20L) cancel();
			}
		};
	}
	
	public static void setTabList(Player player, String footer, String header) {
		ADAPT.setTabList(player, parse(player, footer), parse(player, header));
	}
	
	public static void setPlayerTag(Player player, String prefix, String suffix) {
		ADAPT.setPlayerTag(player, parse(player, prefix), parse(player, suffix));
	}
}
