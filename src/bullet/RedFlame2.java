package bullet;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import dataloader.Animation;
import dataloader.DataLoader;
import gameobject.ParticularObject;
import state.GameWorldState;

public class RedFlame2 extends Flame {
	
	//private long timeExist, lastAttackTime;	
	private Animation redFlame;

	public RedFlame2(float x, float y, float width, float height, GameWorldState gameWorld) {
		super(x, y, width, height, 20, gameWorld);
		redFlame = DataLoader.getInstance().getAnimation("bullet07");
		timeExist = 1000*1000000;
        lastAttackTime = System.nanoTime();
        setDamage(30);
	}	
	   
    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
    	Rectangle bound = getBoundForCollisionWithMap();
    	bound.x = (int)(getPosX()-25);
        return bound;
    }

    @Override
    public void draw(Graphics2D g2) {
    	if(getBlood()==0) return;
        redFlame.Update(System.nanoTime());
        redFlame.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
    }

    @Override
    public void Update() {
    	super.Update();
    	if(getBlood() == 0) setDamage(0);
        setPosX(getPosX() + getSpeedX());
        setPosY(getPosY() + getSpeedY());
        if(System.nanoTime() - lastAttackTime > timeExist) setBlood(0);
        ParticularObject object = getGameWorld().getParticularObjectManager().getCollisionWidthEnemyObject(this);
        if(object!=null && object.getState() == ALIVE){
            object.beHurt(getDamage());
        }
    }
}

