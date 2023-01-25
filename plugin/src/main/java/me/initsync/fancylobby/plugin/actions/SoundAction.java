package me.initsync.fancylobby.plugin.actions;

import me.initsync.fancylobby.api.action.ActionContext;
import me.initsync.fancylobby.api.action.ActionExecutable;
import me.initsync.fancylobby.plugin.utils.FancySound;
import me.initsync.fancylobby.plugin.utils.LogUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

public class SoundAction implements ActionExecutable {
	public SoundAction() {}
	
	@Override
	public ActionContext getContext() {
		return ActionContext.SOUND;
	}
	
	@Override
	public void execute(JavaPlugin plugin, Player player, String container) {
		checkNotNull(plugin, "The JavaPlugin instance is null.");
		checkNotNull(player, "The player is null.");
		checkNotNull(container, "The container is null.");
		
		if (!container.contains(";")) {
			LogUtils.error("The container doesn't contains the correct format!");
			return;
		}
		
		final String[] parts = container.split(";", 3);
		
		final Optional<FancySound> soundOptional = FancySound.matchSound(parts[0]);
		if (!soundOptional.isPresent()) {
			LogUtils.error("The sound for this action isn't specified!");
			return;
		}
		
		final Sound bukkitSound = soundOptional.get().parseSound();
		assert bukkitSound != null;
		
		int volume;
		int pitch;
		try {
			volume = Integer.parseInt(parts[1]);
			pitch = Integer.parseInt(parts[2]);
		} catch (NumberFormatException exception) {
			LogUtils.error("Cannot convert the volume parameters for the sounds. Check that are valid!");
			exception.printStackTrace();
			return;
		}
		
		player.playSound(player.getLocation(), bukkitSound, volume, pitch);
	}
}
