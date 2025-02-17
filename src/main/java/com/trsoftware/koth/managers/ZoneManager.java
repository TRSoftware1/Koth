package com.trsoftware.koth.managers;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.trsoftware.koth.Koth;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ZoneManager {

    public Koth plugin;

    public ZoneManager(Koth plugin) {
        this.plugin = plugin;
    }

    public List<String> getPlayersInRegion(String regionName) {
        List<String> playersInRegion = new ArrayList<>();
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        World world = BukkitAdapter.adapt(Bukkit.getWorld(plugin.getConfig().getString("world")));
        RegionManager regions = container.get(world);
        ProtectedRegion region = regions.getRegion(regionName);
        for (Player player : Bukkit.getWorld(plugin.getConfig().getString("world")).getPlayers()) {
            int x = player.getLocation().getBlockX();
            int y = player.getLocation().getBlockY();
            int z = player.getLocation().getBlockZ();
            if (region.contains(x, y, z)) {
                playersInRegion.add(player.getName());
            }
        }
        return playersInRegion;
    }

}
