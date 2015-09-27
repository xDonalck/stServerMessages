package com.shepherdjerred.stservermessages.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.shepherdjerred.stservermessages.Main;

public class SayCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	if (cmd.getName().equalsIgnoreCase("say")) {
	    if (sender instanceof Player) {
		Player player = (Player) sender;
		if (player.hasPermission("stServerMessages.say")) {
		    if (args.length == 0) {
			String prefix = Main.getInstance().getConfigString("prefix.server");
			String noargs = Main.getInstance().getConfigString("messages.no-args");
			sender.sendMessage(prefix + noargs);
			return false;
		    } else {
			String message = Main.getInstance().getConfigString("messages.say");

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < args.length; i++) {
			    sb.append(args[i]).append(" ");
			}

			String allArgs = sb.toString().trim();

			Bukkit.broadcastMessage(message.replaceAll("%message%", allArgs));
			return true;
		    }
		} else {
		    String prefix = Main.getInstance().getConfigString("prefix.server");
		    String noperms = Main.getInstance().getConfigString("messages.no-perms");
		    sender.sendMessage(prefix + noperms);
		    return false;
		}
	    } else {
		if (args.length == 0) {
		    String noargs = Main.getInstance().getConfigString("messages.no-args");
		    Main.getInstance().getLogger().info(noargs);
		    return false;
		} else {
		    String message = Main.getInstance().getConfigString("messages.say");

		    StringBuilder sb = new StringBuilder();
		    for (int i = 0; i < args.length; i++) {
			sb.append(args[i]).append(" ");
		    }

		    String allArgs = sb.toString().trim();

		    Bukkit.broadcastMessage(message.replaceAll("%message%", allArgs));
		    return true;
		}
	    }
	}
	return false;
    }
}
