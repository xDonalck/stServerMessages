package com.shepherdjerred.stservermessages.listeners;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.shepherdjerred.stservermessages.Main;

public class CommandEvent implements Listener {

    @EventHandler
    public void PlayerCommand(PlayerCommandPreprocessEvent event) {
	
	// Get a list of disallowed commands
	final List<String> commands = Main.getInstance().getConfig().getStringList("disallow-commands");
	
	// Split the command input, get the first argument
	String command = event.getMessage().split(" ")[0].split("/")[1];

	// Loop through the first arguments, look for a match between the input and disallowed commands
	for (String s : commands) {
	    if (command.trim().toLowerCase().equals(s.trim().toLowerCase())) {
		if (!(event.getPlayer().hasPermission("stServerMessages.commands"))) { // If the player doesn't have permission to use the disallowed command

		    // Cancel the command, send an error message
		    event.setCancelled(true);
		    event.getPlayer().sendMessage(Main.getInstance().messagesPrefix + Main.getInstance().messagesNoPerms);
		    
		    // Stop the loop
		    break;
		    
		}
	    }
	}

    }
}
