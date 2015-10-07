package com.shepherdjerred.stservermessages.listeners;

import java.util.List;
import java.util.Random;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.shepherdjerred.stservermessages.Main;

public class PingEvent implements Listener {
    
    final Random random = new Random();

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
	
	// Get a list of all motds, pick & set a random one
	List<String> motds = Main.getInstance().getConfig().getStringList("motds");

	String motd = motds.get(random.nextInt(motds.size())).replaceAll("&", "�").replaceAll("��", "&").replaceAll("%newline%", "\n");
	
	event.setMotd(motd);
    }

}
