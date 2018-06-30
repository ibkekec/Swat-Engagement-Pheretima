package de.paluno.game.interfaces;

public class WorldData extends GameData {

    public int gameState;
    public PlayerData[] players;
    public float shootingAngle;
    public ProjectileData[] projectiles;
    public int currentWeapon = -1;

    public WorldData() {
        super();
    }

    public WorldData(int tick) {
        super(tick);
    }

    public PlayerData getPlayer(int number) {
        return players[number];
    }
}
