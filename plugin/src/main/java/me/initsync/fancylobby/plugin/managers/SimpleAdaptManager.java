package me.initsync.fancylobby.plugin.managers;

import com.google.common.base.Preconditions;
import me.initsync.fancylobby.api.model.adapt.AdaptManager;
import me.initsync.fancylobby.api.model.adapt.ServerAdapt;
import me.initsync.fancylobby.plugin.utils.LogUtils;
import org.bukkit.Bukkit;

import static me.initsync.fancylobby.plugin.utils.LogUtils.error;

public class SimpleAdaptManager implements AdaptManager {
	private ServerAdapt adapt;
	
	public SimpleAdaptManager() {}
	
	@Override
	public AdaptManager of(ServerAdapt adapt) {
		Preconditions.checkNotNull(adapt, "The server adapt specified is null.");
		
		if (Integer.parseInt(Bukkit.getBukkitVersion()
			 .split("-")[0]
			 .split("\\.")[1]) < 17) {
			return null;
		}
		
		this.adapt = adapt;
		return null;
	}
	
	@Override
	public AdaptManager find() {
		if (Integer.parseInt(Bukkit.getBukkitVersion()
			 .split("-")[0]
			 .split("\\.")[1]) < 17) {
			return null;
		}
		
		String packageName = Bukkit.getServer().getClass().getPackage().getName();
		String version = packageName.substring(packageName.lastIndexOf('.') + 1);
		
		try {
			final Class<?> clazz = Class.forName("me.initsync.fancylobby.adapt." + version + ".AdaptHandler");
			if (ServerAdapt.class.isAssignableFrom(clazz)) adapt = (ServerAdapt) clazz.getConstructor().newInstance();
		} catch (Exception exception) {
			error("Has happens an error to install the adapt for the server version.");
			error("The plugin requires the latest version of the release, example: 1.8 -> 1.8.8 (1_8_R3)");
			error("Or you will can use a version lower than 1.8. The plugin doesn't support versions 1.7.10 or older!");
			exception.printStackTrace();
			return null;
		}
		
		LogUtils.info("Loading adapt for Minecraft " + version);
		
		return this;
	}
	
	@Override
	public ServerAdapt getAdapt() {
		if (adapt == null) throw new IllegalStateException("Cannot get the server adapt because isn't installed, or your server version is +1.16!");
		
		return adapt;
	}
}
