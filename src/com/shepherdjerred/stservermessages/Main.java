package com.shepherdjerred.stservermessages;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.shepherdjerred.stservermessages.commands.MeCommand;
import com.shepherdjerred.stservermessages.commands.SayCommand;
import com.shepherdjerred.stservermessages.commands.MainCommand;
import com.shepherdjerred.stservermessages.listeners.DeathEvent;
import com.shepherdjerred.stservermessages.listeners.PingEvent;
import com.shepherdjerred.stservermessages.listeners.JoinQuitEvents;
import com.shepherdjerred.stservermessages.listeners.CommandEvent;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		this.saveDefaultConfig();

		getServer().getPluginManager().registerEvents(new PingEvent(this), this);
		getServer().getPluginManager().registerEvents(new JoinQuitEvents(this), this);
		getServer().getPluginManager().registerEvents(new CommandEvent(this), this);
		getServer().getPluginManager().registerEvents(new DeathEvent(this), this);

		this.getCommand("me").setExecutor(new MeCommand(this));
		this.getCommand("say").setExecutor(new SayCommand(this));
		this.getCommand("stsm").setExecutor(new MainCommand(this));

		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		final Random random = new Random();

		if (getConfig().getBoolean("announcements.tips.enabled")) {
			scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
				@Override
				public void run() {
					List<String> messages = getConfig().getStringList("announcements.tips.messages");
					final String messagesraw = messages.get(random.nextInt(messages.size()));
					String messagesfinal = messagesraw.replaceAll("&", "§").replaceAll("§§", "&").replaceAll("%newline%", "\n");
					String prefix = parseConfig("announcements.tips.prefix");
					Bukkit.getConsoleSender().sendMessage(prefix + messagesfinal);
					for (Player player : getServer().getOnlinePlayers()) {
						if (getConfig().getBoolean("storage.preferences." + player.getName() + ".tips")) {
							player.sendMessage(prefix + messagesfinal);
						}
					}
				}
			}, getConfig().getInt("announcements.tips.delay") * 20 * 60, getConfig().getInt("announcements.tips.interval") * 20 * 60);
		}

		if (getConfig().getBoolean("announcements.ads.enabled")) {
			scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
				@Override
				public void run() {
					List<String> messages = getConfig().getStringList("announcements.ads.messages");
					final String messagesraw = messages.get(random.nextInt(messages.size()));
					String messagesfinal = messagesraw.replaceAll("&", "§").replaceAll("§§", "&").replaceAll("%newline%", "\n");
					String prefix = parseConfig("announcements.ads.prefix");
					Bukkit.getConsoleSender().sendMessage(prefix + messagesfinal);
					for (Player player : getServer().getOnlinePlayers()) {
						if (getConfig().getBoolean("storage.preferences." + player.getName() + "ads")) {
							player.sendMessage(prefix + messagesfinal);
						}
					}
				}
			}, getConfig().getInt("announcements.ads.delay") * 20 * 60, getConfig().getInt("announcements.ads.interval") * 20 * 60);
		}

		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				for (Player player : getServer().getOnlinePlayers()) {
					String name = player.getName();
					int counter = getConfig().getInt("storage.death-counter." + name);
					if (counter > 0) {
						--counter;
						getConfig().set("storage.death-counter." + name, counter);
					} else if (counter == 0) {
						getConfig().set("storage.death-counter." + name, null);
					}
				}
				saveConfig();
			}
		}, 0, getConfig().getInt("deaths.counter-expire-interval") * 20 * 60);
	}

	public String parseConfig(String input) {
		return getConfig().getString(input).replaceAll("&", "§").replaceAll("§§", "&");
	}
}
