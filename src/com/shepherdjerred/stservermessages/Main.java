package com.shepherdjerred.stservermessages;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

    // Provide instance of Main class
    private static Main instance;

    public Main() {
	instance = this;
    }

    public static Main getInstance() {
	return instance;
    }

    @Override
    public void onEnable() {
	this.saveDefaultConfig();

	// Register events
	getServer().getPluginManager().registerEvents(new PingEvent(), this);
	getServer().getPluginManager().registerEvents(new JoinQuitEvents(), this);
	getServer().getPluginManager().registerEvents(new CommandEvent(), this);
	getServer().getPluginManager().registerEvents(new DeathEvent(), this);

	// Register commands
	this.getCommand("me").setExecutor(new MeCommand());
	this.getCommand("say").setExecutor(new SayCommand());
	this.getCommand("stsm").setExecutor(new MainCommand());

	// Setup variables for announcers
	BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
	final Random random = new Random();

	// Tip announcer
	if (getConfig().getBoolean("announcements.tips.enabled")) {
	    scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
		@Override
		public void run() {
		    List<String> messages = getConfig().getStringList("announcements.tips.messages");
		    final String messagesraw = messages.get(random.nextInt(messages.size()));
		    String messagesfinal = messagesraw.replaceAll("&", "§").replaceAll("§§", "&").replaceAll("%newline%", "\n");
		    String prefix = getConfigString("announcements.tips.prefix");
		    Bukkit.getConsoleSender().sendMessage(prefix + messagesfinal);
		    for (Player player : getServer().getOnlinePlayers()) {
			if (getConfig().getBoolean("storage.preferences." + player.getName() + ".tips")) {
			    player.sendMessage(prefix + messagesfinal);
			}
		    }
		}
	    }, getConfig().getInt("announcements.tips.delay") * 20 * 60, getConfig().getInt("announcements.tips.interval") * 20 * 60);
	}

	// Ad announcer
	if (getConfig().getBoolean("announcements.ads.enabled")) {
	    scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
		@Override
		public void run() {
		    List<String> messages = getConfig().getStringList("announcements.ads.messages");
		    final String messagesraw = messages.get(random.nextInt(messages.size()));
		    String messagesfinal = messagesraw.replaceAll("&", "§").replaceAll("§§", "&").replaceAll("%newline%", "\n");
		    String prefix = getConfigString("announcements.ads.prefix");
		    Bukkit.getConsoleSender().sendMessage(prefix + messagesfinal);
		    for (Player player : getServer().getOnlinePlayers()) {
			if (getConfig().getBoolean("storage.preferences." + player.getName() + "ads")) {
			    player.sendMessage(prefix + messagesfinal);
			}
		    }
		}
	    }, getConfig().getInt("announcements.ads.delay") * 20 * 60, getConfig().getInt("announcements.ads.interval") * 20 * 60);
	}

	// Death counter
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

    // Method for getting strings from the config with color codes
    public String getConfigString(String input) {
	return ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString(input));
    }
}
