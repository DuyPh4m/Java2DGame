package enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;


import bullet.LittleGirlBullet;
import dataloader.Animation;
import dataloader.DataLoader;
import state.GameWorldState;
import state.LevelState;

public class LittleGirl extends Enemy {

    private Animation runUp, runDown, runLeft, runRight,
                      idleLeft, idleRight, idleUp, idleDown;
   
    private boolean isAttack = false;
    private boolean isRuning = false;
    private long lastAttackTime;
    private long startTimeToAttack;   
    private float y1, y2;
	
	public LittleGirl(float x, float y, GameWorldState gameWorld) {
		super(x, y, 40, 40, 75, gameWorld);
		
		runUp = DataLoader.getInstance().getAnimation("enemy1_runup");
        runDown = DataLoader.getInstance().getAnimation("enemy1_rundown");
        runLeft = DataLoader.getInstance().getAnimation("enemy1_runleft");
        runRight = DataLoader.getInstance().getAnimation("enemy1_runright");
        
        idleLeft = DataLoader.getInstance().getAnimation("enemy1_idleleft");
        idleRight = DataLoader.getInstance().getAnimation("enemy1_idleright");
        idleUp = DataLoader.getInstance().getAnimation("enemy1_idleup");
        idleDown = DataLoader.getInstance().getAnimation("enemy1_idledown");
        DataLoader.getInstance().getAnimation("bullet05");
        setSpeedX(0.4f);
        setSpeedY(0.4f);      
        setTimeForImmortal(100000000);
        
        int temp = LevelState.getCurrentChoice();
    	if (temp == 0) {
			setDamage(3);     		
    	} else if (temp == 1){ 
    		setDamage(6);
    		setBlood(100);
    	}
        maxHP = getBlood();
        y1 = getPosY() - 50;
        y2 = getPosY() + 50;
	}
	
    public void run2() {
    	if(getPosY() < y1) {
            setSpeedY(1);
            setDirection(DOWN_DIR);
            isRuning = true;
    	}
        else if(getPosY() > y2) {
            setSpeedY(-1);
            setDirection(UP_DIR);   
            isRuning = true;
        }
        setPosY(getPosY() + getSpeedY());
    }
	
	@Override
	public void Update() {
        super.Update(); 
        if(isAttack) {
        	if(System.nanoTime() - lastAttackTime > 500*1000000) {		
        		isAttack = false;
        	}
        }
        if((dx()*dx() + dy() * dy()) >= 50000) run2();
        if((dx()*dx() + dy()*dy() < 50000  && (dx()*dx() + dy()*dy() > 1000))) {        	
        	run();
        	setDirection();
        } else {
        	if( (dx()*dx() + dy()*dy() <= 1000)
        	         && System.nanoTime() - startTimeToAttack > 1000*10000000*0.5){
        	        	stopRun();
        	        	setDirection();
        	        	attack();
        	            startTimeToAttack = System.nanoTime();              
        	        }
        }
        
        
	}
	
	@Override
	public void run() {
		isRuning = true;
		
		if(dx() == 0 && dy() > 0) {
			setSpeedY(-0.4f);
			setPosY(getPosY() + getSpeedY());
		}
		else if(dx() == 0 && dy() < 0) {
			setSpeedY(0.4f);
			setPosY(getPosY() + getSpeedY());
		}
		else if(dy() == 0 && dx() > 0) {
			setSpeedX(-0.4f);
			setPosX(getPosX() + getSpeedX());
		}
		else if(dy() == 0 && dx() < 0) {
			setSpeedX(0.4f);
			setPosX(getPosX() + getSpeedX());
		}
		else if(dx() != 0 && dy() != 0) { 
			if(Math.abs(dx()) > Math.abs(dy())) { 
				if(dy() > 0) {
					setSpeedY(-0.4f);
					setPosY(getPosY() + getSpeedY());
				} else {
					setSpeedY(0.4f);
					setPosY(getPosY() + getSpeedY());
				}
			} else {
				if(dx() > 0) { 
					setSpeedX(-0.4f);
					setPosX(getPosX() + getSpeedX());
				}
				else {
					setSpeedX(0.4f);
					setPosX(getPosX() + getSpeedX());
				}
			}
		}
	}
	
	@Override
	public void stopRun() {
		isRuning = false;
		setSpeedX(0);
		setSpeedY(0);
	}

	@Override
	public void attack() {
		System.out.println("take dame by LittleGirl!");
        LittleGirlBullet heart = new LittleGirlBullet(getGameWorld().getPlayer().getPosX() + 10, getGameWorld().getPlayer().getPosY() +3 , 15, 15, 2, getGameWorld());
		
        heart.setTeamType(getTeamType());
        getGameWorld().getBulletManager().addObject(heart);
        getGameWorld().getPlayer().beHurt(getDamage());
		isAttack = true;
		lastAttackTime = System.nanoTime();
	}

	@Override
	public void shooting() {}

	@Override
	public Rectangle getBoundForCollisionWithEnemy() {
		Rectangle rect = getBoundForCollisionWithMap();
		rect.height = (int)(getHeight() - 10);
		rect.width = (int)(getWidth() - 10);
		return rect;
	}

	@Override
	public Rectangle getBoundForCollisionWithMap() {
		Rectangle rect = super.getBoundForCollisionWithMap();
		rect.height -= 8;
		rect.width -= 8;
		return rect;
	}
	@Override
	public void draw(Graphics2D g2) {
		
		int x = (int)(dx_cam() - 15);
    	int y = (int)(dy_cam() - 30);
    	g2.setColor(Color.GRAY);
    	g2.fillRect(x, y, 25, 3);
    	g2.setColor(Color.RED);
    	g2.fillRect(x, y, getBlood()/(maxHP/25), 2);
    	
		if(getState() == ALIVE || getState() == IMMORTAL) {
			if(getDirection() == RIGHT_DIR) {
				if(isRuning) {           	
					runRight.Update(System.nanoTime());                      
					runRight.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
				}
				else if(isAttack) {
					idleRight.Update(System.nanoTime());
					idleRight.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
				}    		
				else {
					idleRight.Update(System.nanoTime());
					idleRight.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
				}
			}
			else if(getDirection() == LEFT_DIR) {
				if(isRuning) {           	
					runLeft.Update(System.nanoTime());                      
					runLeft.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
		            if(runLeft.getcurrentImageIndex() == 1) runLeft.setIgnoreImage(0);
				}
				else if(isAttack) {
					idleLeft.Update(System.nanoTime());
					idleLeft.draw(dx_cam(), dy_cam(),(int)getWidth(), (int)getHeight(), g2);
				}    		
				else {
					idleLeft.Update(System.nanoTime());
					idleLeft.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
				}
			}
			else if(getDirection() == UP_DIR) {
				if(isRuning) {           	
					runUp.Update(System.nanoTime());                      
					runUp.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
				}
				else if(isAttack) {
					idleUp.Update(System.nanoTime());
					idleUp.draw(dx_cam(), dy_cam(),(int)getWidth(), (int)getHeight(), g2);
				}   		
				else {
					idleUp.Update(System.nanoTime());
					idleUp.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
				}
			}
			else {
				if(isRuning) {           	
					runDown.Update(System.nanoTime());                      
					runDown.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
				}
				else if(isAttack) {
					idleDown.Update(System.nanoTime());
					idleDown.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
				}    		
				else {
					idleDown.Update(System.nanoTime());
					idleDown.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
				}
			}
    	}
	}
	
	public void setDirection() {
		if(dx() == 0 && dy() > 0) {
			setDirection(UP_DIR);
		}
		else if(dx() == 0 && dy() < 0) {
			setDirection(DOWN_DIR);
		}
		else if(dy() == 0 && dx() > 0) {
			setDirection(LEFT_DIR);
		}
		else if(dy() == 0 && dx() < 0) {
			setDirection(RIGHT_DIR);
		}
		else if(dx() != 0 && dy() != 0) { 
			if(Math.abs(dx()) > Math.abs(dy())) { 
				if(dy() > 0)
					setDirection(UP_DIR);
				else 
					setDirection(DOWN_DIR);
			}
			else {
				if(dx() > 0)
					setDirection(LEFT_DIR);
				else
					setDirection(RIGHT_DIR);
			}
		}
	}
	
}
