package com.shepherdjerred.stservermessages.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.shepherdjerred.stservermessages.Main;

public class SayCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

	if (cmd.getName().equalsIgnoreCase("say")) { // Start say command
	    if (sender instanceof Player) { // Check if the sender is a player
		Player player = (Player) sender;
		if (player.hasPermission("stServerMessages.say")) { // Check if player has permission to use this command
		    if (args.length > 0) { // Check if the argument length is greater than 0

			// Get all the arguments, make a string with them
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < args.length; i++) {
			    sb.append(args[i]).append(" ");
			}

			String allArgs = sb.toString().trim();

			// Broadcast the message
			Bukkit.broadcastMessage(Main.getInstance().messagesSay.replaceAll("%message%", allArgs));

			return true;

		    } else { // If there are no arguments

			// Send an error message
			sender.sendMessage(Main.getInstance().messagesPrefix + Main.getInstance().messagesNoArgs + "<message>");

			return false;

		    }
		} else {// Broadcast the message

		    // Send an error message
		    sender.sendMessage(Main.getInstance().messagesPrefix + Main.getInstance().messagesNoPerms);

		    return false;

		}
	    } else { // If the sender isn't a player, IE console
		if (args.length > 0) { // Check if the argument length is greater than 0

		    // Get all the arguments, make a string with them
		    StringBuilder sb = new StringBuilder();

		    for (int i = 0; i < args.length; i++) {
			sb.append(args[i]).append(" ");
		    }

		    String allArgs = sb.toString().trim();

		    // Broadcast the message
		    Bukkit.broadcastMessage(Main.getInstance().messagesSay.replaceAll("%message%", allArgs));

		    return true;

		} else { // If there are no arguments

		    // Send an error message
		    Main.getInstance().getLogger().info(Main.getInstance().messagesPrefix + Main.getInstance().messagesNoArgs + "<message>");

		    return false;

		}
	    }
	}

	return false;

    }
}
