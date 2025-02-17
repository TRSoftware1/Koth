package com.trsoftware.koth.utils;

import com.trsoftware.koth.Koth;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;

public class Utils {

    public Koth plugin;

    public Utils(Koth plugin) {
        this.plugin = plugin;
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Determines if the current server version is above or below 1.16.5.
     *
     * @return true if the server version is above 1.16.5, false otherwise.
     */
    public static boolean isServerVersion1165OrAbove() {
        String version = Bukkit.getBukkitVersion();

        String[] versionParts = version.split("-")[0].split("\\.");
        try {
            int major = Integer.parseInt(versionParts[0]);
            int minor = Integer.parseInt(versionParts[1]);

            if (major > 1) {
                return true;
            } else if (major == 1 && minor > 16) {
                return true;
            } else if (major == 1 && minor == 16) {
                int patch = versionParts.length > 2 ? Integer.parseInt(versionParts[2]) : 0;
                return patch > 4;
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void sendMessage(CommandSender sender, String s) {
        sender.sendMessage(ColorUtils.translateColorCodes(s));
    }

    public void sendMessageToPlayer(Player p, String s) {
        p.sendMessage(ColorUtils.translateColorCodes(s));
    }

}
