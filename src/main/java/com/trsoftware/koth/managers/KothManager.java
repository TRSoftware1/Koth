package com.trsoftware.koth.managers;

import com.trsoftware.koth.GameState;
import com.trsoftware.koth.GameType;
import com.trsoftware.koth.Koth;
import com.trsoftware.koth.objects.KothEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.time.Instant;

public class KothManager {

    public Koth plugin;

    public KothManager(Koth plugin) {
        this.plugin = plugin;
    }

    private Instant eventTimestamp;
    private Instant endEventTimestamp;

    public KothEvent currentKoth = null;

    /**
     * Schedule a koth event.
     * This is used when either a manual event is scheduled or a koth ends and scheduled koths are enabled.
     *
     * @param time in seconds until the koth event starts.
     */
    public void scheduleKoth(int time) {

        if (GameType.isGameType(GameType.FIXED_TIME)) {
            currentKoth = new KothEvent(time, "", plugin.getConfig().getInt("totalKothTime"));
        } else {
            currentKoth = new KothEvent(time, "", plugin.getConfig().getInt("controlKothTime"));
        }
        GameState.setGameState(GameState.SCHEDULED);
        createEventTimestamp();

        new BukkitRunnable() {
            int timeTillStart = currentKoth.getTimeTillStart();

            @Override
            public void run() {
                if (getTimeUntilEventStarts(timeTillStart) > 0) {
                    if (plugin.getConfig().getIntegerList("countdownBroadcasts").contains(getTimeUntilEventStarts(timeTillStart))) {
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                                plugin.pmessages.getString("kothCountdownBroadcast")
                                        .replaceAll("%time%", String.valueOf(getTimeUntilEventStarts(timeTillStart)))));
                    }
                } else {
                    // KOTH start
                    startKothEvent();
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    /**
     * Starts the KOTH event.
     * Creates the timestamp for the start time, sets the game state, and checks every second for KOTH control.
     */
    public void startKothEvent() {
        endEventTimestamp();
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.pmessages.getString("kothStarted")
                .replaceAll("%time%", String.valueOf(currentKoth.getTimeTillEnd()))));
        GameState.setGameState(GameState.ACTIVE);
        new BukkitRunnable() {
            int timeTillEnd = currentKoth.getTimeTillEnd();
            @Override
            public void run() {
                if (getTimeUntilEventFinishes(timeTillEnd) > 0) {
                    // TODO logic for controlling koth
                } else {
                    // TODO end koth
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    /**
     * Creates a timestamp for when the event is created.
     */
    public void createEventTimestamp() {
        eventTimestamp = Instant.now();
    }

    /**
     * Calculates the time remaining (in seconds) until the event starts.
     *
     * @param secondsUntilStart The number of seconds from event creation until the event starts.
     * @return An int representing the time left (in seconds) until the event starts.
     * If the event has already started, returns 0.
     */
    public int getTimeUntilEventStarts(int secondsUntilStart) {
        if (eventTimestamp == null) {
            throw new IllegalStateException("Event timestamp has not been created yet.");
        }

        Instant now = Instant.now();
        Instant eventStartTime = eventTimestamp.plusSeconds(secondsUntilStart);

        long secondsLeft = Duration.between(now, eventStartTime).getSeconds();

        // If the event has already started, return 0
        return (int) Math.max(secondsLeft, 0);
    }

    /**
     * Creates a timestamp for when the event starts.
     */
    public void endEventTimestamp() {
        endEventTimestamp = Instant.now();
    }

    /**
     * Calculates the time remaining (in seconds) until the event finishes.
     *
     * @param secondsUntilEnd The number of seconds from event start until the event finishes.
     * @return An int representing the time left (in seconds) until the event finishes.
     * If the event has already ended, returns 0.
     */
    public int getTimeUntilEventFinishes(int secondsUntilEnd) {
        if (endEventTimestamp == null) {
            throw new IllegalStateException("Event timestamp has not been created yet.");
        }

        Instant now = Instant.now();
        Instant eventEndTime = endEventTimestamp.plusSeconds(secondsUntilEnd);

        long secondsLeft = Duration.between(now, eventEndTime).getSeconds();

        // If the event has already ended, return 0
        return (int) Math.max(secondsLeft, 0);
    }

}
