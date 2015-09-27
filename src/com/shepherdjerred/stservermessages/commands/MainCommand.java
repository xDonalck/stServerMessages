package com.shepherdjerred.stservermessages.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.shepherdjerred.stservermessages.Main;

public class MainCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	if (cmd.getName().equalsIgnoreCase("stsm")) {
	    if (sender instanceof Player) {
		Player player = (Player) sender;
		if (!(args.length == 0)) {
		    if (args[0].equalsIgnoreCase("reload")) { // Reload Command
			if (player.hasPermission("stServerMessages.reload")) {
			    Main.getInstance().reloadConfig();
			    String prefix = Main.getInstance().getConfigString("prefix.server");
			    sender.sendMessage(prefix + "�aConfig reloaded");
			    return true;
			} else {
			    String prefix = Main.getInstance().getConfigString("prefix.server");
			    String noperms = Main.getInstance().getConfigString("messages.no-perms");
			    sender.sendMessage(prefix + noperms);
			    return false;
			}
		    } else if (args[0].equalsIgnoreCase("ads")) { // Ads Preference Command
			if (Main.getInstance().getConfig().getBoolean("announcements.ads.enabled")) {
			    if (player.hasPermission("stServerMessages.preferences")) {
				if (!(args.length >= 1)) {
				    if (args[1].equalsIgnoreCase("true")) {
					Main.getInstance().getConfig().set("storage.preferences." + player.getName() + ".ads", true);
					Main.getInstance().saveConfig();
					String prefix = Main.getInstance().getConfigString("prefix.server");
					String message = Main.getInstance().getConfigString("announcements.ads.set-to-true");
					sender.sendMessage(prefix + message);
					return true;
				    } else if (args[1].equalsIgnoreCase("false")) {
					Main.getInstance().getConfig().set("storage.preferences." + player.getName() + ".ads", false);
					Main.getInstance().saveConfig();
					String prefix = Main.getInstance().getConfigString("prefix.server");
					String message = Main.getInstance().getConfigString("announcements.ads.set-to-false");
					sender.sendMessage(prefix + message);
					return true;
				    } else {
					String prefix = Main.getInstance().getConfigString("prefix.server");
					String invalidarg = Main.getInstance().getConfigString("messages.invalid-arg");
					sender.sendMessage(prefix + invalidarg);
					return false;
				    }
				} else {
				    String prefix = Main.getInstance().getConfigString("prefix.server");
				    String noargs = Main.getInstance().getConfigString("messages.no-args");
				    sender.sendMessage(prefix + noargs);
				    return false;
				}
			    } else {
				String prefix = Main.getInstance().getConfigString("prefix.server");
				String noperms = Main.getInstance().getConfigString("messages.no-perms");
				sender.sendMessage(prefix + noperms);
				return false;
			    }
			}
		    } else if (args[0].equalsIgnoreCase("tips")) { // Tips Preference Command
			if (Main.getInstance().getConfig().getBoolean("announcements.tips.enabled")) {
			    if (player.hasPermission("stServerMessages.preferences")) {
				if (!(args.length >= 1)) {
				    if (args[1].equalsIgnoreCase("true")) {
					Main.getInstance().getConfig().set("storage.preferences." + player.getName() + ".tips", true);
					Main.getInstance().saveConfig();
					String prefix = Main.getInstance().getConfigString("prefix.server");
					String message = Main.getInstance().getConfigString("announcements.tips.set-to-true");
					sender.sendMessage(prefix + message);
					return true;
				    } else if (args[1].equalsIgnoreCase("false")) {
					Main.getInstance().getConfig().set("storage.preferences." + player.getName() + ".tips", false);
					Main.getInstance().saveConfig();
					String prefix = Main.getInstance().getConfigString("prefix.server");
					String message = Main.getInstance().getConfigString("announcements.tips.set-to-false");
					sender.sendMessage(prefix + message);
					return true;
				    } else {
					String prefix = Main.getInstance().getConfigString("prefix.server");
					String invalidarg = Main.getInstance().getConfigString("messages.invalid-arg");
					sender.sendMessage(prefix + invalidarg);
					return false;
				    }
				} else {
				    String prefix = Main.getInstance().getConfigString("prefix.server");
				    String noargs = Main.getInstance().getConfigString("messages.no-args");
				    sender.sendMessage(prefix + noargs);
				    return false;
				}
			    } else {
				String prefix = Main.getInstance().getConfigString("prefix.server");
				String noperms = Main.getInstance().getConfigString("messages.no-perms");
				sender.sendMessage(prefix + noperms);
				return false;
			    }
			}
		    } else {
			String prefix = Main.getInstance().getConfigString("prefix.server");
			String invalidarg = Main.getInstance().getConfigString("messages.invalid-arg");
			sender.sendMessage(prefix + invalidarg);
			return false;
		    }
		} else {
		    String prefix = Main.getInstance().getConfigString("prefix.server");
		    String noargs = Main.getInstance().getConfigString("messages.no-args");
		    sender.sendMessage(prefix + noargs);
		    return false;
		}
	    }
	} else {
	    if (!(args.length == 0)) {
		if (args[0].equalsIgnoreCase("reload")) {
		    Main.getInstance().reloadConfig();
		    Main.getInstance().getLogger().info("Config reloaded");
		    return true;
		} else {
		    String invalidarg = Main.getInstance().getConfigString("messages.invalid-arg");
		    Main.getInstance().getLogger().info(invalidarg);
		    return false;
		}
	    } else {
		String noargs = Main.getInstance().getConfigString("messages.no-args");
		Main.getInstance().getLogger().info(noargs);
		return false;
	    }
	}
	return false;
    }
}
