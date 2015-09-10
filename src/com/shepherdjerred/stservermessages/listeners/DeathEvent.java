package com.shepherdjerred.stservermessages.listeners;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang.WordUtils;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.shepherdjerred.stservermessages.Main;

public class DeathEvent implements Listener {
	private final Main plugin;

	public DeathEvent(Main plugin) {
		this.plugin = plugin;
	}

	public String parseConfig(String input) {
		return plugin.getConfig().getString(input).replaceAll("&", "§").replaceAll("§§", "&");
	}

	public String parseStringListRandom(String input) {
		List<String> list = plugin.getConfig().getStringList(input);
		final Random random = new Random();
		final String raw = list.get(random.nextInt(list.size()));
		return raw.replaceAll("&", "§").replaceAll("§§", "&");
	}

	/*
	 * Missing Causes: Falling Block, Magic, Poison, Thorns, Wither
	 * Partial: Entity Explosion, Projectile
	 */
	/*
	 * Missing Mobs: Elder Guardian, Chicken Jockey, Killer Bunny, Spider
	 * Jockey, Wither Skeleton
	 */

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		String name = player.getName();

		int counter = plugin.getConfig().getInt("storage.death-counter." + name);

		if (counter <= plugin.getConfig().getInt("deaths.counter-before-hidden")) {

			++counter;
			plugin.getConfig().set("storage.death-counter." + name, counter);
			plugin.saveConfig();

			DamageCause cause = player.getLastDamageCause().getCause();
			Entity killer = player.getKiller();

			if (cause == DamageCause.ENTITY_ATTACK) {

				if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
					EntityDamageByEntityEvent damageevent = (EntityDamageByEntityEvent) player.getLastDamageCause();

					if (player.getKiller() instanceof Player && (player.getKiller() != null)) {
						String attacker = killer.getName();
						String weapon = WordUtils.capitalizeFully(player.getKiller().getItemInHand().getType().toString().replaceAll("_", " "));
						event.setDeathMessage(parseStringListRandom("deaths.pvp").replaceAll("%victim%", name).replaceAll("%attacker%", attacker).replaceAll("%weapon%", weapon));
					} else {
						if (damageevent.getDamager() instanceof Zombie) {
							String mob = parseConfig("deaths.names.zombie");
							event.setDeathMessage(parseStringListRandom("deaths.mobs.zombie").replaceAll("%victim%", name).replaceAll("%attacker%", mob));
						} else if (damageevent.getDamager() instanceof Enderman) {
							String mob = parseConfig("deaths.names.enderman");
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));
						} else if (damageevent.getDamager() instanceof CaveSpider) {
							String mob = parseConfig("deaths.names.cavespider");
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));
						} else if (damageevent.getDamager() instanceof Spider) {
							String mob = parseConfig("deaths.names.spider");
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));
						} else if (damageevent.getDamager() instanceof PigZombie) {
							String mob = parseConfig("deaths.names.pigzombie");
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));
						} else if (damageevent.getDamager() instanceof Blaze) {
							String mob = parseConfig("deaths.names.blaze");
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));
						} else if (damageevent.getDamager() instanceof Guardian) {
							String mob = parseConfig("deaths.names.guardian");
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));
						} else if (damageevent.getDamager() instanceof Endermite) {
							String mob = parseConfig("deaths.names.endermite");
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));
						} else if (damageevent.getDamager() instanceof Ghast) {
							String mob = parseConfig("deaths.names.ghast");
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));
						} else if (damageevent.getDamager() instanceof MagmaCube) {
							String mob = parseConfig("deaths.names.magmacube");
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));
						} else if (damageevent.getDamager() instanceof Silverfish) {
							String mob = parseConfig("deaths.names.silverfish");
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));
						} else if (damageevent.getDamager() instanceof Skeleton) {
							String mob = parseConfig("deaths.names.skeleton");
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));
						} else if (damageevent.getDamager() instanceof Slime) {
							String mob = parseConfig("deaths.names.slime");
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));
						} else if (damageevent.getDamager() instanceof Witch) {
							String mob = parseConfig("deaths.names.witch");
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));
						} else if (damageevent.getDamager() instanceof Wither) {
							String mob = parseConfig("deaths.names.wither");
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));
						} else if (damageevent.getDamager() instanceof EnderDragon) {
							String mob = parseConfig("deaths.names.enderdragon");
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));
						} else {
							event.setDeathMessage(parseStringListRandom("deaths.unknown").replaceAll("%victim%", name));
						}
					}
				}

			} else if (cause == DamageCause.BLOCK_EXPLOSION) {
				event.setDeathMessage(parseStringListRandom("deaths.block-explosion").replaceAll("%victim%", name));
			} else if (cause == DamageCause.CONTACT) {
				event.setDeathMessage(parseStringListRandom("deaths.contact").replaceAll("%victim%", name));
			} else if (cause == DamageCause.DROWNING) {
				event.setDeathMessage(parseStringListRandom("deaths.drowning").replaceAll("%victim%", name));
			} else if (cause == DamageCause.FALL) {
				event.setDeathMessage(parseStringListRandom("deaths.fall").replaceAll("%victim%", name));
			} else if (cause == DamageCause.FIRE) {
				event.setDeathMessage(parseStringListRandom("deaths.fire").replaceAll("%victim%", name));
			} else if (cause == DamageCause.FIRE_TICK) {
				event.setDeathMessage(parseStringListRandom("deaths.fire").replaceAll("%victim%", name));
			} else if (cause == DamageCause.LAVA) {
				event.setDeathMessage(parseStringListRandom("deaths.lava").replaceAll("%victim%", name));
			} else if (cause == DamageCause.LIGHTNING) {
				event.setDeathMessage(parseStringListRandom("deaths.lightning").replaceAll("%victim%", name));
			} else if (cause == DamageCause.STARVATION) {
				event.setDeathMessage(parseStringListRandom("deaths.starvation").replaceAll("%victim%", name));
			} else if (cause == DamageCause.SUFFOCATION) {
				event.setDeathMessage(parseStringListRandom("deaths.suffocation").replaceAll("%victim%", name));
			} else if (cause == DamageCause.SUICIDE) {
				event.setDeathMessage(parseStringListRandom("deaths.suicide").replaceAll("%victim%", name));
			} else if (cause == DamageCause.VOID) {
				event.setDeathMessage(parseStringListRandom("deaths.void").replaceAll("%victim%", name));
			} else if (cause == DamageCause.ENTITY_EXPLOSION) {
				event.setDeathMessage(parseStringListRandom("deaths.mobs.creeper").replaceAll("%victim%", name));
			} else if (cause == DamageCause.PROJECTILE) {
				event.setDeathMessage(parseStringListRandom("deaths.mobs.skeleton").replaceAll("%victim%", name));
			} else {
				event.setDeathMessage(parseStringListRandom("deaths.unknown").replaceAll("%victim%", name));
			}
		} else {
			event.setDeathMessage("");
		}
	}
}
