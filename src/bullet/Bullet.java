package bullet;

import state.GameWorldState;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import gameobject.ParticularObject;

public abstract class Bullet extends ParticularObject {

	protected long timeExist, lastAttackTime;
    public Bullet(float x, float y, float width, float height, int damage, GameWorldState gameWorld) {
            super(x, y, width, height, 1, gameWorld);
            setDamage(damage);
    }
    
    public void Update(){
        super.Update();
        if(this instanceof Flame) {}
        else {
	        if(getBlood() == 0) setDamage(0);
	        setPosX(getPosX() + getSpeedX());
	        setPosY(getPosY() + getSpeedY());
	        ParticularObject object = getGameWorld().getParticularObjectManager().getCollisionWidthEnemyObject(this);
	        if(object != null && object.getState() == ALIVE){
	            setBlood(0);
	            object.beHurt(getDamage());
	            System.out.println("Bullet set behurt for enemy");
	        }
        }
    }

	@Override
	public void attack() {}

	@Override
	public void shooting() {}

	@Override
	public Rectangle getBoundForCollisionWithEnemy() {
		return getBoundForCollisionWithMap();
	}

	public abstract void draw(Graphics2D g2);
}
