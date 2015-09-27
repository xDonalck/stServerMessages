package com.shepherdjerred.stservermessages.listeners;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.shepherdjerred.stservermessages.Main;

public class CommandEvent implements Listener {

    @EventHandler
    public void PlayerCommand(PlayerCommandPreprocessEvent event) {
	final List<String> commands = Main.getInstance().getConfig().getStringList("disallow-commands");
	String command = event.getMessage().split(" ")[0].split("/")[1];

	for (String s : commands) {
	    if (command.trim().toLowerCase().equals(s.trim().toLowerCase())) {
		if (event.getPlayer().hasPermission("stServerMessages.commands")) {
		    /* Do nothing */
		} else {
		    event.setCancelled(true);
		    String prefix = Main.getInstance().getConfigString("prefix.server");
		    String noperms = Main.getInstance().getConfigString("messages.no-perms");
		    event.getPlayer().sendMessage(prefix + noperms);
		    break;
		}
	    }
	}

    }
}
