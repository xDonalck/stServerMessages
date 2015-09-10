package com.shepherdjerred.stservermessages.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.shepherdjerred.stservermessages.Main;

public class MeCommand implements CommandExecutor {
	private final Main plugin;

	public MeCommand(Main plugin) {
		this.plugin = plugin;
	}

	public String parseConfig(String input) {
		return plugin.getConfig().getString(input).replaceAll("&", "§").replaceAll("§§", "&");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("me")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("stServerMessages.me")) {
					if (args.length == 0) {
						String prefix = parseConfig("prefix.server");
						String noargs = parseConfig("messages.no-args");
						sender.sendMessage(prefix + noargs);
						return false;
					} else {
						String message = parseConfig("messages.me");
						
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < args.length; i++){
						sb.append(args[i]).append(" ");
						}
						
						String allArgs = sb.toString().trim();
						
						Bukkit.broadcastMessage(message.replaceAll("%player%", player.getName()).replaceAll("%message%", allArgs));
						return true;
					}
				} else {
					String prefix = parseConfig("prefix.server");
					String noperms = parseConfig("messages.no-perms");
					sender.sendMessage(prefix + noperms);
					return false;
				}
			} else {
				String noconsole = parseConfig("messages.no-console");
				plugin.getLogger().info(noconsole);
				return false;
			}
		}
		return false;
	}
}
