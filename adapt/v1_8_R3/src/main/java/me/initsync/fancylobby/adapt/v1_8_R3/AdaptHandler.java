package me.initsync.fancylobby.adapt.v1_8_R3;

import io.netty.buffer.ByteBufAllocator;
import me.initsync.fancylobby.api.model.adapt.ServerAdapt;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.base.Preconditions.checkNotNull;

public class AdaptHandler implements ServerAdapt {
	private AdaptHandler() {}
	
	@Override
	public void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		if (fadeIn < 1 || stay < 1 || fadeOut < 1) return;
		
		checkNotNull(player, "The player is null.");
		checkNotNull(title, "The title is null.");
		checkNotNull(subtitle, "The subtitle is null.");
		
		final PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutTitle(fadeIn, stay, fadeOut));
		connection.sendPacket(new PacketPlayOutTitle(
			 PacketPlayOutTitle.EnumTitleAction.TITLE,
			 IChatBaseComponent.ChatSerializer.a("{\"text\": " + title + "\"}")
		));
		connection.sendPacket(new PacketPlayOutTitle(
			 PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
			 IChatBaseComponent.ChatSerializer.a("{\"text\": " + subtitle + "\"}")
		));
	}
	
	@Override
	public void sendActionBar(JavaPlugin plugin, Player player, String message) {
		checkNotNull(plugin, "The JavaPlugin instance is null.");
		checkNotNull(player, "The player is null.");
		checkNotNull(message, "The actionbar message is null.");
		
		((CraftPlayer) player).getHandle()
			 .playerConnection
			 .sendPacket(new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\": " + message + "\"}"), (byte) 2));
	}
	
	@Override
	public void setTabList(Player player, String footer, String header) {
		checkNotNull(player, "The player is null.");
		
		final PacketDataSerializer serializer = new PacketDataSerializer(ByteBufAllocator.DEFAULT.buffer());
		final PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
		
		try {
			serializer.a(IChatBaseComponent.ChatSerializer.a("{\"text\": " + footer + "\"}"));
			serializer.a(IChatBaseComponent.ChatSerializer.a("{\"text\": " + header + "\"}"));
			packet.a(serializer);
		} catch (IOException exception) { exception.printStackTrace(); }
		
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	@Override
	public void setPlayerTag(Player player, String prefix, String suffix) {
		checkNotNull(player, "The player is null.");
		
		final PacketDataSerializer serializer = new PacketDataSerializer(ByteBufAllocator.DEFAULT.buffer());
		
		serializer.a("flt-" + Integer.toHexString(ThreadLocalRandom.current().nextInt()));
		serializer.writeByte(2);
		serializer.a(player.getDisplayName());
		serializer.a(prefix);
		serializer.a(suffix);
		serializer.a("always");
		
		final PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
		try { packet.a(serializer); }
		catch (IOException exception) { exception.printStackTrace(); }
		
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
}