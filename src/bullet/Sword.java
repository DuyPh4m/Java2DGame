package bullet;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import gameobject.ParticularObject;
import state.GameWorldState;

public class Sword extends Flame {
    
	//private long timeExist, lastAttackTime;
    
    public Sword(float x, float y, GameWorldState gameWorld) {
        super(x, y, 50, 50, 20, gameWorld);
        timeExist = 100*1000000;
        lastAttackTime = System.nanoTime();
        setDamage(25);
    }    
    
    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {}

    @Override
    public void Update() {
    	super.Update();
    	
    	if(getBlood() == 0) setState(DEATH);
        setPosX(getPosX() + getSpeedX());
        setPosY(getPosY() + getSpeedY());
        if(System.nanoTime() - lastAttackTime > timeExist) setBlood(0);
        ParticularObject object = getGameWorld().getParticularObjectManager().getCollisionWidthEnemyObject(this);
        if(object!=null && object.getState() == ALIVE){
            setBlood(0);
            object.setBlood(object.getBlood() - getDamage());
            object.setState(BEHURT);
            System.out.println("Bullet set behurt for enemy");
        }
    }

    @Override
    public void attack() {}
    
	@Override
	public void shooting() {}
}
