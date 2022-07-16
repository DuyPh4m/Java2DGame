package bullet;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import dataloader.Animation;
import dataloader.DataLoader;
import gameobject.ParticularObject;
import state.GameWorldState;
import state.LevelState;

public class DuckBullet extends Bullet {

	//private long timeExist, lastAttackTime;
	
	private Animation anim;
	public DuckBullet(float x, float y, GameWorldState gameWorld) {
        super(x, y, 26, 26, 3, gameWorld);
        anim = DataLoader.getInstance().getAnimation("bullet03");
        timeExist = 100*1000000;
        lastAttackTime = System.nanoTime();
        if(LevelState.getCurrentChoice() == 1)
        	setDamage(6);
    }    
    
    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {
    		if(getBlood() == 0) return ;
            anim.Update(System.nanoTime());
            anim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), (int) getWidth(), (int) getHeight(), g2);       
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
            setBlood(0);
            object.setBlood(object.getBlood() - getDamage());
            object.setState(BEHURT);
            System.out.println("Bullet 3 set behurt for enemy");
        }
    }

    @Override
    public void attack() {}

	@Override
	public void shooting() {}

}
