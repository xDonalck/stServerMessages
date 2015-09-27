package com.shepherdjerred.stservermessages.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.shepherdjerred.stservermessages.Main;

public class MeCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	if (cmd.getName().equalsIgnoreCase("me")) {
	    if (sender instanceof Player) {
		Player player = (Player) sender;
		if (player.hasPermission("stServerMessages.me")) {
		    if (args.length == 0) {
			String prefix = Main.getInstance().getConfigString("prefix.server");
			String noargs = Main.getInstance().getConfigString("messages.no-args");
			sender.sendMessage(prefix + noargs);
			return false;
		    } else {
			String message = Main.getInstance().getConfigString("messages.me");

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < args.length; i++) {
			    sb.append(args[i]).append(" ");
			}

			String allArgs = sb.toString().trim();

			Bukkit.broadcastMessage(message.replaceAll("%player%", player.getName()).replaceAll("%message%", allArgs));
			return true;
		    }
		} else {
		    String prefix = Main.getInstance().getConfigString("prefix.server");
		    String noperms = Main.getInstance().getConfigString("messages.no-perms");
		    sender.sendMessage(prefix + noperms);
		    return false;
		}
	    } else {
		String noconsole = Main.getInstance().getConfigString("messages.no-console");
		Main.getInstance().getLogger().info(noconsole);
		return false;
	    }
	}
	return false;
    }
}
