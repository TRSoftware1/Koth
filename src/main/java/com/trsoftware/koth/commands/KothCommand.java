package com.trsoftware.koth.commands;

import com.trsoftware.koth.GameState;
import com.trsoftware.koth.Koth;
import com.trsoftware.koth.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KothCommand implements CommandExecutor {

    public Koth plugin;

    public KothCommand(Koth plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("koth")) {
            switch (args.length) {
                case 0:
                    if (plugin.kothManager.currentKoth == null) {
                        plugin.utils.sendMessage(sender, plugin.pmessages.getString("noScheduledKoth"));
                        return true;
                    }
                    plugin.utils.sendMessage(sender, plugin.pmessages.getString("kothCountdownBroadcast")
                            .replaceAll("%time%", String.valueOf(plugin.kothManager.getTimeUntilEventStarts(plugin.kothManager.currentKoth.getTimeTillStart()))));
                    break;
                case 2:
                    // Manually starting a koth event, to start in "n" seconds.
                    if (args[0].equalsIgnoreCase("start")) {
                        if (!sender.hasPermission("koth.admin.start")) {
                            plugin.utils.sendMessage(sender, plugin.pmessages.getString("noPermission"));
                            return true;
                        }
                        if (!Utils.isInteger(args[1])) {
                            plugin.utils.sendMessage(sender, plugin.pmessages.getString("invalidInteger"));
                            return true;
                        }
                        if (Integer.parseInt(args[1]) < 0) {
                            plugin.utils.sendMessage(sender, plugin.pmessages.getString("invalidInteger"));
                            return true;
                        }
                        if (!GameState.isGameState(GameState.INACTIVE)) {
                            plugin.utils.sendMessage(sender, plugin.pmessages.getString("cannotSchedule"));
                            return true;
                        }
                        plugin.kothManager.scheduleKoth(Integer.parseInt(args[1]));
                        return true;
                    }
                    break;

                default:
                    // TODO help
                    break;
            }
        }

        return true;
    }
}
