package com.shepherdjerred.stservermessages.listeners;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.shepherdjerred.stservermessages.Main;

public class CommandEvent implements Listener {
	private final Main plugin;

	public CommandEvent(Main plugin) {
		this.plugin = plugin;
	}

	public String parseConfig(String input) {
		return plugin.getConfig().getString(input).replaceAll("&", "§").replaceAll("§§", "&");
	}

	@EventHandler
	public void PlayerCommand(PlayerCommandPreprocessEvent event) {
		final List<String> commands = plugin.getConfig().getStringList("disallow-commands");
		String command = event.getMessage().split(" ")[0].split("/")[1];

		for (String s : commands) {
			if (command.trim().toLowerCase().equals(s.trim().toLowerCase())) {
				if (event.getPlayer().hasPermission("stServerMessages.commands")) {
					/* Do nothing */
				} else {
					event.setCancelled(true);
					String prefix = parseConfig("prefix.server");
					String noperms = parseConfig("messages.no-perms");
					event.getPlayer().sendMessage(prefix + noperms);
					break;
				}
			}
		}

	}
}
