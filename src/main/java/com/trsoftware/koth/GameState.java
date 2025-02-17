package com.trsoftware.koth;

public enum GameState {

    INACTIVE,
    SCHEDULED,
    ACTIVE;

    private static GameState gameState;

    public static GameState getGameState() {
        return gameState;
    }

    public static void setGameState(GameState gs) {
        gameState = gs;
    }

    public static boolean isGameState(GameState gs) {
        return gs.equals(gameState);
    }

}
