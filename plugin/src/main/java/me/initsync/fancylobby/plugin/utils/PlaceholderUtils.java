package me.initsync.fancylobby.plugin.utils;

import com.google.common.base.Preconditions;
import me.clip.placeholderapi.PlaceholderAPI;
import net.xconfig.bukkit.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceholderUtils {
	private static final boolean PLACEHOLDER_API = (Bukkit.getPluginManager().getPlugin("PlaceholderAPI")) != null &&
		 Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
	
	private PlaceholderUtils() {}
	
	public static String parse(Player player, String text) {
		Preconditions.checkNotNull(player, "The player is null.");
		
		if (PLACEHOLDER_API) return TextUtils.colorize(PlaceholderAPI.setPlaceholders(player, text.replace("<br>", "\n")));
		
		return TextUtils.colorize(text.replace("<br>", "\n"));
	}
}
