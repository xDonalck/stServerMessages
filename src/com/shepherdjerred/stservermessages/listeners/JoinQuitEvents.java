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

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
	
	// Set the join message
	String join = Main.getInstance().getConfigString("messages.join");
	event.setJoinMessage(join.replaceAll("%player%", event.getPlayer().getName()));
	
	// Set the tab list header and footer
	String header = Main.getInstance().getConfigString("tab-list.header");
	String footer = Main.getInstance().getConfigString("tab-list.footer");
	
	HeaderFooter(event.getPlayer(), header, footer);
	
	// Set the default values for tips/ads if they aren't already there
	if (!Main.getInstance().getConfig().getBoolean("storage.preferences." + event.getPlayer().getName() + ".set")) {
	    
	    if (Main.getInstance().getConfig().getBoolean("announcements.tips.enabled")) {
		Main.getInstance().getConfig().set("storage.preferences." + event.getPlayer().getName() + ".tips", true);
		Main.getInstance().getConfig().set("storage.preferences." + event.getPlayer().getName() + ".set", true);
	    }
	    
	    if (Main.getInstance().getConfig().getBoolean("announcements.ads.enabled")) {
		Main.getInstance().getConfig().set("storage.preferences." + event.getPlayer().getName() + ".ads", true);
		Main.getInstance().getConfig().set("storage.preferences." + event.getPlayer().getName() + ".set", true);
	    }
	    
	    Main.getInstance().saveConfig();
	    
	}
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
	
	// Set the quit message
	String leave = Main.getInstance().getConfigString("messages.leave");
	
	event.setQuitMessage(leave.replaceAll("%player%", event.getPlayer().getName()));
	
    }

    // Method to set the tab list header/footer
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
