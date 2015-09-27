package com.shepherdjerred.stservermessages.listeners;

import java.util.List;
import java.util.Random;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.shepherdjerred.stservermessages.Main;

public class PingEvent implements Listener {

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
	List<String> motds = Main.getInstance().getConfig().getStringList("motds");
	final Random random = new Random();
	final String motdraw = motds.get(random.nextInt(motds.size()));
	String motdfinal = motdraw.replaceAll("&", "§").replaceAll("§§", "&").replaceAll("%newline%", "\n");
	event.setMotd(motdfinal);
    }

}
