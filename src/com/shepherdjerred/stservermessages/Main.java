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
    
    // TODO Remove this area and put it where it should be.
    // Setup message variables
    public String messagesPrefix = getConfigString("prefix.server");
    public String messagesMe = getConfigString("messages.me");
    public String messagesSay = getConfigString("messages.say");
    public String messageNoConsole = getConfigString("messages.no-console");
    public String messagesNoPerms = getConfigString("messages.no-perms");
    public String messagesInvalidArg = getConfigString("messages.invalid-arg");
    public String messagesNoArgs = getConfigString("messages.no-args");
    public String messagesAdsSetTrue = getConfigString("messages.ads-set-true");
    public String messagesAdsSetFalse = getConfigString("messages.ads-set-false");
    public String messagesTipsSetTrue = getConfigString("messages.tips-set-true");
    public String messagesTipsSetFalse = getConfigString("messages.tips-set-false");
    public String messagesJoin = getConfigString("messages.join");

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
	Random random = new Random();
	String messagesTipsPrefix = getConfigString("announcements.tips.prefix");
	String messagesAdsPrefix = getConfigString("announcements.ads.prefix");
	List<String> tipsList = getConfig().getStringList("announcements.tips.messages");
	List<String> adsList = getConfig().getStringList("announcements.ads.messages");

	// Tip announcer
	if (getConfig().getBoolean("announcements.tips.enabled")) {
	    scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
		@Override
		public void run() {
		    String randomTip = tipsList.get(random.nextInt(tipsList.size()));
		    String proccessedTip = ChatColor.translateAlternateColorCodes('&', randomTip).replaceAll("%newline%", "\n");
		    
		    // Send the tip to the console
		    Bukkit.getConsoleSender().sendMessage(messagesTipsPrefix + proccessedTip);
		    
		    // Loop through online players, send the tip
		    for (Player player : getServer().getOnlinePlayers()) {
			if (getConfig().getBoolean("storage.preferences." + player.getName() + ".tips")) {
			    player.sendMessage(messagesTipsPrefix + proccessedTip);
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
		    String randomAd = adsList.get(random.nextInt(adsList.size()));
		    String processedAd = ChatColor.translateAlternateColorCodes('&', randomAd).replaceAll("%newline%", "\n");
		    
		    // Send the ad to the console
		    Bukkit.getConsoleSender().sendMessage(messagesAdsPrefix + processedAd);
		    
		    // Loop through online players, send the ad
		    for (Player player : getServer().getOnlinePlayers()) {
			if (getConfig().getBoolean("storage.preferences." + player.getName() + ".ads")) {
			    player.sendMessage(messagesAdsPrefix + processedAd);
			}
		    }
		}
	    }, getConfig().getInt("announcements.ads.delay") * 20 * 60, getConfig().getInt("announcements.ads.interval") * 20 * 60);
	}

	// Death counter
	scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
	    @Override
	    public void run() {
		
		// Loop through online players
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
	}, getConfig().getInt("deaths.counter-expire-interval") * 20 * 60, getConfig().getInt("deaths.counter-expire-interval") * 20 * 60);
    }

    // Method for getting strings from the config with color codes
    public String getConfigString(String input) {
	return ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString(input));
    }
}
