package com.shepherdjerred.stservermessages.listeners;

import java.lang.reflect.Field;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.shepherdjerred.stservermessages.Main;

public class JoinQuitEvents implements Listener {
	private final Main plugin;

	public JoinQuitEvents(Main plugin) {
		this.plugin = plugin;
	}

	public String parseConfig(String input) {
		return plugin.getConfig().getString(input).replaceAll("&", "§").replaceAll("§§", "&");
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		String join = parseConfig("messages.join");
		String header = parseConfig("tab-list.header");
		String footer = parseConfig("tab-list.footer");
		event.setJoinMessage(join.replaceAll("%player%", event.getPlayer().getName()));
		HeaderFooter(event.getPlayer(), header, footer);
		if (!plugin.getConfig().getBoolean("storage.preferences." + event.getPlayer().getName() + ".set")) {
			if (plugin.getConfig().getBoolean("announcements.tips.enabled")) {
				plugin.getConfig().set("storage.preferences." + event.getPlayer().getName() + ".tips", true);
				plugin.getConfig().set("storage.preferences." + event.getPlayer().getName() + ".set", true);
			}
			if (plugin.getConfig().getBoolean("announcements.ads.enabled")) {
				plugin.getConfig().set("storage.preferences." + event.getPlayer().getName() + ".ads", true);
				plugin.getConfig().set("storage.preferences." + event.getPlayer().getName() + ".set", true);
			}
			plugin.saveConfig();
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		String leave = parseConfig("messages.leave");
		event.setQuitMessage(leave.replaceAll("%player%", event.getPlayer().getName()));
	}

	public static void HeaderFooter(Player arg0, String arg1, String arg2) {
		if (arg1 == null) {
			arg1 = "";
		}
		if (arg2 == null) {
			arg2 = "";
		}
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(ChatSerializer.a("{\"text\": \"" + arg1 + "\"}"));
		try {
			Field b = packet.getClass().getDeclaredField("b");
			b.setAccessible(true);
			b.set(packet, ChatSerializer.a("{\"text\": \"" + arg2 + "\"}"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		((CraftPlayer) arg0).getHandle().playerConnection.sendPacket(packet);
	}
}
