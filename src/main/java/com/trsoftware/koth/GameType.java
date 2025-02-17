package com.trsoftware.koth;

public enum GameType {

    COMPLETE_CONTROL,
    FIXED_TIME;

    private static GameType gameType;

    public static GameType getGameType() {
        return gameType;
    }

    public static void setGameType(GameType type) {
        gameType = type;
    }

    public static boolean isGameType(GameType type) {
        return gameType.equals(type);
    }

}
