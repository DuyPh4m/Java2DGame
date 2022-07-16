package enemies;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import gameobject.ParticularObject;
import state.GameWorldState;

public abstract class Enemy extends ParticularObject {

	protected int maxHP;

	
	public Enemy(float x, float y, float width, float height, int blood, GameWorldState gameWorld) {
		super(x, y, width, height, blood, gameWorld);
		setTeamType(ENEMY_TEAM);
	}

	@Override
	public void Update() {
        super.Update(); 
        
        if( this instanceof Boss || this instanceof DarkDuck) {
        	
        } else {
	        if(haveCollisionWithMap()) {
	        	
	        	if(getDirection() == DOWN_DIR) {
	        		setDirection(UP_DIR);
	        		setPosY(getPosY() - 1);
	        		stopRun();
	        	}
	        	else if(getDirection() == UP_DIR) {
	        		setDirection(DOWN_DIR);
	        		setPosY(getPosY() + 1);
	        		stopRun();
	        	}
	        	else if(getDirection() == LEFT_DIR) {
	        		setDirection(RIGHT_DIR);
	        		setPosX(getPosX() + 1);
	        		stopRun();
	        	}
	        	else if(getDirection() == RIGHT_DIR) {
	        		setDirection(LEFT_DIR);
	        		setPosX(getPosX() - 1);
	        	}
	        }
        }
	}
	
	@Override
	public Rectangle getBoundForCollisionWithEnemy() {
		return null;
	}

	@Override
	public void draw(Graphics2D g2) {}
	public abstract void run();
	
    public  void stopRun() {
    	setSpeedX(0);
    	setSpeedY(0);
    }
    
    public boolean haveCollisionWithMap() {
    	Rectangle rtop = getGameWorld().getPhys_map().haveCollisionWithTop(this.getBoundForCollisionWithEnemy());
    	Rectangle rdown = getGameWorld().getPhys_map().haveCollisionWithLand(this.getBoundForCollisionWithEnemy());
    	Rectangle rleft = getGameWorld().getPhys_map().haveCollisionWithLeftWall(this.getBoundForCollisionWithEnemy());
    	Rectangle rright = getGameWorld().getPhys_map().haveCollisionWithRightWall(this.getBoundForCollisionWithEnemy());
    	if(rtop != null || rdown != null || rleft != null || rright != null) {
    		return true;
    	}
    	return false;
    }    
    
}
