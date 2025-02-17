package com.trsoftware.koth.objects;

public class KothEvent {

    private int timeTillStart;
    private String currentController;
    private int timeTillEnd;

    public KothEvent(int timeTillStart, String currentController, int timeTillEnd) {
        setTimeTillStart(timeTillStart);
        setCurrentController(currentController);
        setTimeTillEnd(timeTillEnd);
    }

    public int getTimeTillStart() {
        return timeTillStart;
    }

    public void setTimeTillStart(int timeTillStart) {
        this.timeTillStart = timeTillStart;
    }

    public String getCurrentController() {
        return currentController;
    }

    public void setCurrentController(String currentController) {
        this.currentController = currentController;
    }

    public int getTimeTillEnd() {
        return timeTillEnd;
    }

    public void setTimeTillEnd(int timeTillEnd) {
        this.timeTillEnd = timeTillEnd;
    }

}
