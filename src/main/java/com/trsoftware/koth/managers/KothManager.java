package com.trsoftware.koth.manager;

import com.trsoftware.koth.Koth;

public class KothManager {

    public Koth plugin;

    public KothManager(Koth plugin) {
        this.plugin = plugin;
    }

    /**
     * Schedule a koth event.
     * This is used when either a manual event is scheduled or a koth ends and scheduled koths are enabled.
     * @param time in seconds until the koth event starts.
     */
    public void scheduleKoth(int time) {

    }

}
