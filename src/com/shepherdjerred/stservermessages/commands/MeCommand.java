package com.shepherdjerred.stservermessages.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.shepherdjerred.stservermessages.Main;

public class MeCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

	if (cmd.getName().equalsIgnoreCase("me")) { // Start me command
	    if (sender instanceof Player) { // Check if the sender is a player
		Player player = (Player) sender;
		if (player.hasPermission("stServerMessages.me")) { // Check if player has permission to use this command
		    if (args.length > 0) { // Check if the argument length is greater than 0

			// Get all the arguments, make a string with them
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < args.length; i++) {
			    sb.append(args[i]).append(" ");
			}

			String allArgs = sb.toString().trim();

			// Broadcast the message
			// TODO: Send this message as a player, not a broadcast
			Bukkit.broadcastMessage(Main.getInstance().messagesMe.replaceAll("%player%", player.getName()).replaceAll("%message%", allArgs));

			return true;

		    } else { // If there are no arguments

			// Send an error message
			sender.sendMessage(Main.getInstance().messagesPrefix + Main.getInstance().messagesNoArgs + "<message>");

			return false;

		    }
		} else { // If the player doesn't have permission to run the command

		    // Send an error message
		    sender.sendMessage(Main.getInstance().messagesPrefix + Main.getInstance().messagesNoPerms);

		    return false;

		}
	    } else { // If the sender isn't a player, IE console
		
		// Send an error message
		Main.getInstance().getLogger().info(Main.getInstance().messageNoConsole);
		
		return false;
		
	    }
	}
	
	return false;
	
    }
}
