package enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import dataloader.Animation;
import dataloader.DataLoader;
import state.GameWorldState;
import state.LevelState;

public class Snail extends Enemy {

	
	private Animation runUp, runDown, runLeft, runRight,
                      idleLeft, idleRight, idleUp, idleDown,
	                  atkLeft, atkRight, atkUp, atkDown;

    private boolean isAttack = false;
    private boolean isRuning = false;
    private long lastAttackTime;
    private long startTimeToAttack;
    private float x1, x2;
	
	public Snail(float x, float y, GameWorldState gameWorld) {
		super(x, y, 40, 40, 125, gameWorld);
		
		runUp = DataLoader.getInstance().getAnimation("enemy3_runup");
        runDown = DataLoader.getInstance().getAnimation("enemy3_rundown");
        runLeft = DataLoader.getInstance().getAnimation("enemy3_runleft");
        runRight = DataLoader.getInstance().getAnimation("enemy3_runright");
        
        idleLeft = DataLoader.getInstance().getAnimation("enemy3_idleleft");
        idleRight = DataLoader.getInstance().getAnimation("enemy3_idleright");
        idleUp = DataLoader.getInstance().getAnimation("enemy3_idleup");
        idleDown = DataLoader.getInstance().getAnimation("enemy3_idledown");
        
        atkLeft = DataLoader.getInstance().getAnimation("enemy3_atkleft");
        atkRight = DataLoader.getInstance().getAnimation("enemy3_atkright");
        atkUp = DataLoader.getInstance().getAnimation("enemy3_atkup");
        atkDown = DataLoader.getInstance().getAnimation("enemy3_atkdown");
        
       
        setTimeForImmortal(100000000);
		int temp = LevelState.getCurrentChoice();
    	if (temp == 0) {
			setDamage(2);     		
    	} else if (temp == 1){ 
    		setDamage(4);
    	}
        maxHP = getBlood();
        x1 = getPosX() - 50;
        x2 = getPosX() + 50;
        setSpeedX(1);
	}
	
    public void run2() {
    	if(getPosX() < x1) {
            setSpeedX(1);
            setDirection(RIGHT_DIR);
            isRuning = true;
    	}
        else if(getPosX() > x2) {
            setSpeedX(-1);
            setDirection(LEFT_DIR);   
            isRuning = true;
        }
        setPosX(getPosX() + getSpeedX());
    }

	@Override
	public void attack() {
		isAttack = true;
		stopRun();
		System.out.println("take dame by Snail!");

		getGameWorld().getPlayer().beHurt(getDamage());
		lastAttackTime = System.nanoTime();
	}

	@Override
	public void shooting() {}
    @Override
    public void run() {
    	isRuning = true;
    	
		if((dx()*dx() + dy()*dy()) <= 5000 && (dx() == 0 || dy() == 0) && (dx()*dx() + dy()*dy()) >= 400) {
			stopRun();
		}
		if(dx() == 0 && dy() > 0) {
			setDirection(UP_DIR); 
			setSpeedY(-0.4f);
			setPosY(getPosY() + getSpeedY());
		}
		else if(dx() == 0 && dy() < 0) {
			setDirection(DOWN_DIR); 
			setSpeedY(0.4f);
			setPosY(getPosY() + getSpeedY());
		}
		else if(dy() == 0 && dx() > 0) {
			setDirection(LEFT_DIR);  
			setSpeedX(-0.4f);
			setPosX(getPosX() + getSpeedX());
		}
		else if(dy() == 0 && dx() < 0) {
			setDirection(RIGHT_DIR); 
			setSpeedX(0.4f);
			setPosX(getPosX() + getSpeedX());
		}
		else if(dx() != 0 && dy() != 0) { 
			if(Math.abs(dx()) > Math.abs(dy())) { 
				if(dy() > 0) {
					setDirection(UP_DIR);
					setSpeedY(-0.4f);
					setPosY(getPosY() + getSpeedY());
				}
					
				else {
					setDirection(DOWN_DIR);
					setSpeedY(0.4f);
					setPosY(getPosY() + getSpeedY());
				}
			}
			else {
				if(dx() > 0) {
					setDirection(LEFT_DIR);
					setSpeedX(-0.4f);
					setPosX(getPosX() + getSpeedX());
				}
				else {
					setDirection(RIGHT_DIR); 
					setSpeedX(0.4f);
					setPosX(getPosX() + getSpeedX());
				}
			}
		}		
	}
	
    public void stopRun() {
		isRuning = false;
		setSpeedX(0);
		setSpeedY(0);
	}
    
    @Override
    public void Update() {
    	super.Update();
        
        if((dx()*dx() + dy() * dy()) >= 50000) run2();
         if((dx()*dx() + dy()*dy() < 50000)
          && (dx()*dx() + dy()*dy() > 2000)) {        	
         	run();
         }
         
         if(isAttack) {
         	if(System.nanoTime() - lastAttackTime > 500*1000000) {		
         		isAttack = false;       		
         	}
         }
         
     	if( (dx()*dx() + dy()*dy() <= 2000) && System.nanoTime() - startTimeToAttack > 1000*1000000) {
        	stopRun();
        	attack();
            startTimeToAttack = System.nanoTime();              
        }
         
         
    }

	@Override
	public Rectangle getBoundForCollisionWithEnemy() {
		Rectangle rect = getBoundForCollisionWithMap();
		return rect;
	}

	@Override
	public void draw(Graphics2D g2) {
		int x = (int)(dx_cam() - 8);
    	int y = (int)(dy_cam() - 30);
    	g2.setColor(Color.GRAY);
    	g2.fillRect(x, y, 25, 3);
    	g2.setColor(Color.RED);
    	g2.fillRect(x, y, getBlood()/(maxHP/25), 2);
		
		
		if(getDirection() == RIGHT_DIR) {
			if(isRuning) {           	
				runRight.Update(System.nanoTime());                      
				runRight.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
			}
			else if(isAttack) {
				atkRight.Update(System.nanoTime());
				atkRight.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
			}    		
			else if(!isRuning && !isAttack){
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
				atkLeft.Update(System.nanoTime());
				atkLeft.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
			}    		
			else if(!isRuning && !isAttack) {
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
				atkUp.Update(System.nanoTime());
				atkUp.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
			}   		
			else if(!isRuning && !isAttack){
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
				atkDown.Update(System.nanoTime());
				atkDown.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
			}    		
			else if(!isRuning && !isAttack) {
				idleDown.Update(System.nanoTime());
				idleDown.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
			}
		}
	}
}
