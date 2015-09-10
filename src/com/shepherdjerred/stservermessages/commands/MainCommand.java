package com.shepherdjerred.stservermessages.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.shepherdjerred.stservermessages.Main;

public class MainCommand implements CommandExecutor {
	private final Main plugin;

	public MainCommand(Main plugin) {
		this.plugin = plugin;
	}

	public String parseConfig(String input) {
		return plugin.getConfig().getString(input).replaceAll("&", "§").replaceAll("§§", "&");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("stsm")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (!(args.length == 0)) {
					if (args[0].equalsIgnoreCase("reload")) { // Reload Command
						if (player.hasPermission("stServerMessages.reload")) {
							plugin.reloadConfig();
							String prefix = parseConfig("prefix.server");
							sender.sendMessage(prefix + "§aConfig reloaded");
							return true;
						} else {
							String prefix = parseConfig("prefix.server");
							String noperms = parseConfig("messages.no-perms");
							sender.sendMessage(prefix + noperms);
							return false;
						}
					} else if (args[0].equalsIgnoreCase("ads")) { // Ads Preference Command
						if (plugin.getConfig().getBoolean("announcements.ads.enabled")) {
							if (player.hasPermission("stServerMessages.preferences")) {
								if (!(args.length >= 1)) {
									if (args[1].equalsIgnoreCase("true")) {
										plugin.getConfig().set("storage.preferences." + player.getName() + ".ads", true);
										plugin.saveConfig();
										String prefix = parseConfig("prefix.server");
										String message = parseConfig("announcements.ads.set-to-true");
										sender.sendMessage(prefix + message);
										return true;
									} else if (args[1].equalsIgnoreCase("false")) {
										plugin.getConfig().set("storage.preferences." + player.getName() + ".ads", false);
										plugin.saveConfig();
										String prefix = parseConfig("prefix.server");
										String message = parseConfig("announcements.ads.set-to-false");
										sender.sendMessage(prefix + message);
										return true;
									} else {
										String prefix = parseConfig("prefix.server");
										String invalidarg = parseConfig("messages.invalid-arg");
										sender.sendMessage(prefix + invalidarg);
										return false;
									}
								} else {
									String prefix = parseConfig("prefix.server");
									String noargs = parseConfig("messages.no-args");
									sender.sendMessage(prefix + noargs);
									return false;
								}
							} else {
								String prefix = parseConfig("prefix.server");
								String noperms = parseConfig("messages.no-perms");
								sender.sendMessage(prefix + noperms);
								return false;
							}
						}
					} else if (args[0].equalsIgnoreCase("tips")) { // Tips Preference Command
						if (plugin.getConfig().getBoolean("announcements.tips.enabled")) {
							if (player.hasPermission("stServerMessages.preferences")) {
								if (!(args.length >= 1)) {
									if (args[1].equalsIgnoreCase("true")) {
										plugin.getConfig().set("storage.preferences." + player.getName() + ".tips", true);
										plugin.saveConfig();
										String prefix = parseConfig("prefix.server");
										String message = parseConfig("announcements.tips.set-to-true");
										sender.sendMessage(prefix + message);
										return true;
									} else if (args[1].equalsIgnoreCase("false")) {
										plugin.getConfig().set("storage.preferences." + player.getName() + ".tips", false);
										plugin.saveConfig();
										String prefix = parseConfig("prefix.server");
										String message = parseConfig("announcements.tips.set-to-false");
										sender.sendMessage(prefix + message);
										return true;
									} else {
										String prefix = parseConfig("prefix.server");
										String invalidarg = parseConfig("messages.invalid-arg");
										sender.sendMessage(prefix + invalidarg);
										return false;
									}
								} else {
									String prefix = parseConfig("prefix.server");
									String noargs = parseConfig("messages.no-args");
									sender.sendMessage(prefix + noargs);
									return false;
								}
							} else {
								String prefix = parseConfig("prefix.server");
								String noperms = parseConfig("messages.no-perms");
								sender.sendMessage(prefix + noperms);
								return false;
							}
						}
					} else {
						String prefix = parseConfig("prefix.server");
						String invalidarg = parseConfig("messages.invalid-arg");
						sender.sendMessage(prefix + invalidarg);
						return false;
					}
				} else {
					String prefix = parseConfig("prefix.server");
					String noargs = parseConfig("messages.no-args");
					sender.sendMessage(prefix + noargs);
					return false;
				}
			}
		} else {
			if (!(args.length == 0)) {
				if (args[0].equalsIgnoreCase("reload")) {
					plugin.reloadConfig();
					plugin.getLogger().info("Config reloaded");
					return true;
				} else {
					String invalidarg = parseConfig("messages.invalid-arg");
					plugin.getLogger().info(invalidarg);
					return false;
				}
			} else {
				String noargs = parseConfig("messages.no-args");
				plugin.getLogger().info(noargs);
				return false;
			}
		}
		return false;
	}
}
