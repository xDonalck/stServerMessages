package com.shepherdjerred.stservermessages.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.shepherdjerred.stservermessages.Main;

public class MainCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

	if (cmd.getName().equalsIgnoreCase("stsm")) { // Start stsm command
	    if (sender instanceof Player) { // Check if the sender is a player
		Player player = (Player) sender;
		if (args.length > 0) { // Check if the argument length is greater than 0
		    if (args[0].equalsIgnoreCase("reload")) { // Reload Command
			if (player.hasPermission("stServerMessages.reload")) { // Check if player has permission to use this command

			    // Reload the config, send a message
			    Main.getInstance().reloadConfig();
			    sender.sendMessage(Main.getInstance().messagesPrefix + "§aConfig reloaded");

			    return true;

			} else {

			    // Send an error message
			    sender.sendMessage(Main.getInstance().messagesPrefix + Main.getInstance().messagesNoPerms);

			    return false;

			}
		    } else if (args[0].equalsIgnoreCase("ads")) { // Ads Preference Command
			if (Main.getInstance().getConfig().getBoolean("announcements.ads.enabled")) { // Check if ads are enabled
			    if (player.hasPermission("stServerMessages.preferences.ads")) { // Check if player has permission to use this command
				if (args.length > 1) { // Check if the argument length is greater than 1
				    if (args[1].equalsIgnoreCase("true")) {

					// Set the players preference to true, save the config
					Main.getInstance().getConfig().set("storage.preferences." + player.getName() + ".ads", true);
					Main.getInstance().saveConfig();

					// Send a message
					sender.sendMessage(Main.getInstance().messagesPrefix + Main.getInstance().messagesAdsSetTrue);

					return true;

				    } else if (args[1].equalsIgnoreCase("false")) {

					// Set the players preference to false, save the config
					Main.getInstance().getConfig().set("storage.preferences." + player.getName() + ".ads", false);
					Main.getInstance().saveConfig();

					// Send a message
					sender.sendMessage(Main.getInstance().messagesPrefix + Main.getInstance().messagesAdsSetFalse);

					return true;

				    } else { // If the argument doesn't match any of the options

					// Send an error message
					sender.sendMessage(Main.getInstance().messagesPrefix + Main.getInstance().messagesInvalidArg + "<true|false>");

					return false;

				    }
				} else { // If there are no arguments

				    // Send an error message
				    sender.sendMessage(Main.getInstance().messagesPrefix + Main.getInstance().messagesNoArgs + "<true|false>");

				    return false;

				}
			    } else { // If the player doesn't have permission to run the command

				// Send an error message
				sender.sendMessage(Main.getInstance().messagesPrefix + Main.getInstance().messagesNoPerms);

				return false;

			    }
			}
		    } else if (args[0].equalsIgnoreCase("tips")) { // Tips Preference Command
			if (Main.getInstance().getConfig().getBoolean("announcements.tips.enabled")) { // Check if tips are enabled
			    if (player.hasPermission("stServerMessages.preferences.tips")) { // Check if player has permission to use this command
				if (args.length > 1) { // Check if the argument length is greater than 1
				    if (args[1].equalsIgnoreCase("true")) {

					// Set the players preference to true, save the config
					Main.getInstance().getConfig().set("storage.preferences." + player.getName() + ".tips", true);
					Main.getInstance().saveConfig();

					// Send a message
					sender.sendMessage(Main.getInstance().messagesPrefix + Main.getInstance().messagesTipsSetTrue);

					return true;

				    } else if (args[1].equalsIgnoreCase("false")) {

					// Set the players preference to true, save the config
					Main.getInstance().getConfig().set("storage.preferences." + player.getName() + ".tips", false);
					Main.getInstance().saveConfig();

					// Send a message
					sender.sendMessage(Main.getInstance().messagesPrefix + Main.getInstance().messagesTipsSetTrue);

					return true;

				    } else { // If the argument doesn't match any of the options

					// Send an error message
					sender.sendMessage(Main.getInstance().messagesPrefix + Main.getInstance().messagesInvalidArg + "<true|false>");

					return false;

				    }
				} else { // If there are no arguments

				    // Send an error message
				    sender.sendMessage(Main.getInstance().messagesPrefix + Main.getInstance().messagesNoArgs + "<true|false>");

				    return false;

				}
			    } else { // If the player doesn't have permission to run the command

				// Send an error message
				sender.sendMessage(Main.getInstance().messagesPrefix + Main.getInstance().messagesNoPerms);

				return false;

			    }
			}
		    } else { // If the argument doesn't match any of the options

			// Send an error message
			sender.sendMessage(Main.getInstance().messagesPrefix + Main.getInstance().messagesInvalidArg + "<tips|ads>");

			return false;

		    }
		} else { // If there are no arguments

		    // Send an error message
		    sender.sendMessage(Main.getInstance().messagesPrefix + Main.getInstance().messagesNoArgs + "<tips|ads>");

		    return false;

		}
	    } else { // If the sender isn't a player, IE console
		if (args.length > 0) { // Check if the argument length is greater than 0
		    if (args[0].equalsIgnoreCase("reload")) { // Reload Command

			// Reload the config, send a message
			Main.getInstance().reloadConfig();
			Main.getInstance().getLogger().info("Config reloaded");

			return true;

		    } else { // If the argument doesn't match any of the options

			// Send an error message
			Main.getInstance().getLogger().info(Main.getInstance().messagesInvalidArg + "<reload>");

			return false;

		    }
		} else { // If there are no arguments

		    // Send an error message
		    Main.getInstance().getLogger().info(Main.getInstance().messagesPrefix + Main.getInstance().messagesNoArgs + "<reload>");

		    return false;

		}
	    }
	}

	return false;

    }
}
