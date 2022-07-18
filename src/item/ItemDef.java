package item;

import dataloader.Animation;
import gameobject.ParticularObject;
import state.GameWorldState;

import java.awt.*;

public class ItemDef extends ParticularObject {

    private boolean isExist = true;
    private int bufDef;
    private Animation def;

    public ItemDef(float x, float y, float width, float height, int blood, GameWorldState gameWorld) {
        super(x, y, width, height, 1, gameWorld);
        setTeamType(ENEMY_TEAM);
        bufDef = 1;
    }

    @Override
    public void attack() {

    }

    @Override
    public void defence() {

    }

    @Override
    public void shooting() {

    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {

    }

    public int getBufDef() { return bufDef; }

    public void setBufDef(int bufDef) {this.bufDef = bufDef; }
}
