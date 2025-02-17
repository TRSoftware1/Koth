package com.trsoftware.koth;

import com.trsoftware.koth.commands.KothCommand;
import com.trsoftware.koth.managers.KothManager;
import com.trsoftware.koth.managers.ZoneManager;
import com.trsoftware.koth.utils.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Koth extends JavaPlugin {


    public File messages = new File("plugins/KOTH/", "messages.yml");
    public FileConfiguration pmessages = YamlConfiguration.loadConfiguration(messages);

    public Koth plugin;
    public Utils utils;
    public KothManager kothManager;
    public ZoneManager zoneManager;
    public KothCommand kothCommand;

    @Override
    public void onEnable() {
        GameState.setGameState(GameState.INACTIVE);
        saveDefaultConfig();
        if (getConfig().getBoolean("completeControlRequired")) {
            GameType.setGameType(GameType.COMPLETE_CONTROL);
        } else {
            GameType.setGameType(GameType.FIXED_TIME);
        }
        saveDefaultMessages();
        createInstances();
        getCommand("koth").setExecutor(new KothCommand(this));
    }

    @Override
    public void onDisable() {
        removeInstances();
    }

    private void createInstances() {
        plugin = this;
        utils = new Utils(this);
        kothManager = new KothManager(this);
        zoneManager = new ZoneManager(this);
        kothCommand = new KothCommand(this);
    }

    private void removeInstances() {
        plugin = null;
        utils = null;
        kothManager = null;
        zoneManager = null;
        kothCommand = null;
    }

    public void saveDefaultMessages() {
        try {
            saveResource("messages.yml", false);
        } catch (Exception e) {

        }
        pmessages = YamlConfiguration.loadConfiguration(messages);
    }

}
