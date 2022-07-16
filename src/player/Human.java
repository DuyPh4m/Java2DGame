package player;

import state.GameWorldState;
import java.awt.Rectangle;

import gameobject.ParticularObject;

public abstract class Human extends ParticularObject{

	protected int maxHP;
    public Human(float x, float y, float width, float height, int blood, GameWorldState gameWorld) {
        super(x, y, width, height, blood, gameWorld);
        setState(ALIVE);
    }
    
    public int getMaxHP() {
    	return maxHP;
    }

    public abstract void run();
    public abstract void stopRun();

    @Override
    public void Update(){
        
    	super.Update();
    	if(getDirection() == LEFT_DIR && getGameWorld().getPhys_map().haveCollisionWithLeftWall(getBoundForCollisionWithMap())!=null){

            Rectangle rectLeftWall = getGameWorld().getPhys_map().haveCollisionWithLeftWall(getBoundForCollisionWithMap());
            setSpeedX(0);
            setPosX(rectLeftWall.x + rectLeftWall.width + getWidth()/2);
        } else {
        	setPosX(getPosX() + getSpeedX());
        }
    	
        if(getDirection() == RIGHT_DIR && getGameWorld().getPhys_map().haveCollisionWithRightWall(getBoundForCollisionWithMap())!=null){

            Rectangle rectRightWall = getGameWorld().getPhys_map().haveCollisionWithRightWall(getBoundForCollisionWithMap());
            setSpeedX(0);
            setPosX(rectRightWall.x - getWidth()/2-1);
        } else {
        	setPosX(getPosX() + getSpeedX());
        }
        
        if(getDirection() == UP_DIR && getGameWorld().getPhys_map().haveCollisionWithTop(getBoundForCollisionWithMap())!=null){

            Rectangle rectTopWall = getGameWorld().getPhys_map().haveCollisionWithTop(getBoundForCollisionWithMap());
            setSpeedY(0);
            setPosY(rectTopWall.y + getHeight()/2+50);
        } else {
        	setPosY(getPosY() + getSpeedY());
        }
        
        if(getDirection() == DOWN_DIR && getGameWorld().getPhys_map().haveCollisionWithLand(getBoundForCollisionWithMap())!=null){

            Rectangle rectDownWall = getGameWorld().getPhys_map().haveCollisionWithLand(getBoundForCollisionWithMap());
            setSpeedY(0);
            setPosY(rectDownWall.y - getHeight()/2-1);
        }else {
        	setPosY(getPosY() + getSpeedY());
        }
    } 
    
}
