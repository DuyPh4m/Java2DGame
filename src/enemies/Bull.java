
package enemies;

import state.GameWorldState;
import state.LevelState;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import dataloader.Animation;
import dataloader.DataLoader;



public class Bull extends Enemy {
	
	int x = 0;
    private long lastAttackTime;
	
    private boolean isAttack = false;
    private boolean isRuning = false;

    private Animation runForwardAnim, runBackAnim, runUpAnim, runDownAnim;
    private Animation idleForwardAnim, idleUpAnim, idleDownAnim, idleBackAnim,
    idleAttackForwardAnim, idleAttackBackAnim, idleAttackUpAnim, idleAttackDownAnim;
    private Animation death;

    private long startTimeToAttack;      
    
    private float x1, x2;
    
    public Bull(float x, float y, GameWorldState gameWorld) {
        super(x, y, 80, 80, 150, gameWorld);
        int temp = LevelState.getCurrentChoice();
    	if (temp == 0) {
    		setBlood(150);
    		
    	} else if (temp == 1){ 
    		setBlood(300);
    	}
     
        runForwardAnim = DataLoader.getInstance().getAnimation("bull_runright");
        runBackAnim = DataLoader.getInstance().getAnimation("bull_runleft"); 
        runUpAnim = DataLoader.getInstance().getAnimation("bull_runup");
        runDownAnim = DataLoader.getInstance().getAnimation("bull_rundown");
        
        idleForwardAnim = DataLoader.getInstance().getAnimation("bull_idleright");
        idleBackAnim = DataLoader.getInstance().getAnimation("bull_idleleft");   
        idleUpAnim = DataLoader.getInstance().getAnimation("bull_idleup");
        idleDownAnim = DataLoader.getInstance().getAnimation("bull_idledown");        
        
        idleAttackForwardAnim = DataLoader.getInstance().getAnimation("bull_attackright");
        idleAttackBackAnim = DataLoader.getInstance().getAnimation("bull_attackleft");
        idleAttackUpAnim = DataLoader.getInstance().getAnimation("bull_attackup");
        idleAttackDownAnim = DataLoader.getInstance().getAnimation("bull_attackdown");
        
        death = DataLoader.getInstance().getAnimation("bull_death");
        
        startTimeToAttack = 0;
        if (temp == 0) {
			setDamage(6);     		
    	} else if (temp == 1){ 
    		setDamage(8);
    	}
        setTimeForImmortal(100000000);

        x1 = getPosX() - 50;
        x2 = getPosX() + 50;
        maxHP = getBlood();
        setSpeedX(1);
    }
    
    public void teleport() {
    	if(getGameWorld().getPlayer().getDirection() == LEFT_DIR) {
    		setPosX(getGameWorld().getPlayer().getPosX()+100);
    		setPosY(getGameWorld().getPlayer().getPosY());
    		setDirection(LEFT_DIR);
    	}
    	else if(getGameWorld().getPlayer().getDirection() == RIGHT_DIR) {
    		setPosX(getGameWorld().getPlayer().getPosX()-100);
    		setPosY(getGameWorld().getPlayer().getPosY());
    		setDirection(RIGHT_DIR);
    	}
    	else if(getGameWorld().getPlayer().getDirection() == UP_DIR) {
    		setPosY(getGameWorld().getPlayer().getPosY()+100);
    		setPosX(getGameWorld().getPlayer().getPosX());
    		setDirection(UP_DIR);
    	}
    	else {
    		setPosY(getGameWorld().getPlayer().getPosY()-100);
    		setPosX(getGameWorld().getPlayer().getPosX());
    		setDirection(DOWN_DIR);
    	}
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
    
    public void stopRun() {
    	isRuning = false;
    	setSpeedX(0);
    	setSpeedY(0);
    }
    
    public void run() {
    	isAttack = false;
    	isRuning = true;
    	System.out.println("run");
    	if(dx() == 0 && dy() > 0) {
    		setSpeedY(-1);
    		setDirection(UP_DIR); 
            setPosY(getPosY() + getSpeedY());
    	}
    	else if(dx() == 0 && dy() < 0) {   		
    		setSpeedY(1);
    		setDirection(DOWN_DIR);
            setPosY(getPosY() + getSpeedY());
    	}   	
    	else if(dx() < - getGameWorld().getPlayer().getWidth()
    		 && dy() == 0) {
    		setSpeedX(1);
    		setDirection(RIGHT_DIR);
    		setPosX(getPosX() + getSpeedX());
    	}
    	else if(dx() > getGameWorld().getPlayer().getWidth()
    		 && dy() == 0) {
    		setSpeedX(-1);
    		setDirection(LEFT_DIR);
    		setPosX(getPosX() + getSpeedX());
    	}
    	else if(dy() > 0) {
    		setSpeedY(-1);
    		setDirection(UP_DIR);
            setPosY(getPosY() + getSpeedY());
    	}
    	else if(dy() < 0) {
    		setSpeedY(1);
    		setDirection(DOWN_DIR);
            setPosY(getPosY() + getSpeedY());
    	}
    	else if(dx()*dx() + dy()*dy() <= 2500  
             && (dy() == 0 || dx() == 0 || haveCollisionWithMap() == true)) stopRun();
    }
    
    @Override
    public void attack() {  

		System.out.println("attack2");
    	isAttack = true;
    	lastAttackTime = System.nanoTime();
    	getGameWorld().getPlayer().beHurt(getDamage());
	}   
    
    public void Update(){
        super.Update(); 
        
        if(isAttack) {
        	if(System.nanoTime() - lastAttackTime > 1000*1000000) {		
        		isAttack = false;       		
        	}
        }
        
        if((dx()*dx() + dy() * dy()) >= 50000) run2();
        
        if( (dx()*dx() + dy()*dy() < 50000) && (dx()*dx() + dy()*dy() > 2500 ) ) {
        	if(haveCollisionWithMap()) {
        		stopRun();
        	}
        	else run();
        }
        
        if( (dx()*dx() + dy()*dy() <= 2500)
            && (System.nanoTime() - startTimeToAttack > 1000*10000000*1.5) ){   
        	stopRun();
        	attack();
        	x++;
            startTimeToAttack = System.nanoTime(); 
        }
        
        if( (dx()*dx() + dy()*dy() <= 20000) && (x == 2) && getGameWorld().getPlayer().getIsAttack() == true) {
        	teleport();
        	isAttack = false;
        	x = 0;
        }
    }
    
    @Override
    public Rectangle getBoundForCollisionWithEnemy() {               
        Rectangle bound = new Rectangle();
        bound.x = (int)(getPosX() - 10);
        bound.y = (int)(getPosY() - 20);
        bound.height = (int)(getHeight()-30);
        bound.width = (int)(getWidth()-40);
        return bound;
    }

    @Override
    public void draw(Graphics2D g2) {  
    	int x = (int)(dx_cam() - 15);
    	int y = (int)(dy_cam() - 35);
    	g2.setColor(Color.GRAY);
    	g2.fillRect(x, y, 50, 4);
    	g2.setColor(Color.RED);
    	g2.fillRect(x, y, getBlood()/(maxHP/50), 3);

    	if(getState() == ALIVE || getState() == IMMORTAL) {
			if(getDirection() == RIGHT_DIR) {
				if(isRuning) {           	
					runForwardAnim.Update(System.nanoTime());                      
					runForwardAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
				}
				if(isAttack) {
		            idleAttackForwardAnim.Update(System.nanoTime());
		            idleAttackForwardAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
				}    		
				if(!isRuning && !isAttack) {
					idleBackAnim.Update(System.nanoTime());
					idleBackAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
				}
			}
			else if(getDirection() == LEFT_DIR) {
				if(isRuning) {           	
		            runBackAnim.Update(System.nanoTime());                      
		            runBackAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
				}
				if(isAttack) {
		            idleAttackBackAnim.Update(System.nanoTime());
		            idleAttackBackAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
				}    		
				if(!isRuning && !isAttack) {
					idleForwardAnim.Update(System.nanoTime());
					idleForwardAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
				}
			}
			else if(getDirection() == UP_DIR) {
				if(isRuning) {           	
		            runUpAnim.Update(System.nanoTime());                      
		            runUpAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
	
				}
				if(isAttack) {
		            idleAttackUpAnim.Update(System.nanoTime());
		            idleAttackUpAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
				}   		
				if(!isRuning && !isAttack) {
					idleUpAnim.Update(System.nanoTime());
					idleUpAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
				}
			}
			else {
				if(isRuning) {           	
		            runDownAnim.Update(System.nanoTime());                      
		            runDownAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
		        
				}
				if(isAttack) {
		            idleAttackDownAnim.Update(System.nanoTime());
		            idleAttackDownAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
				}    		
				if(!isRuning && !isAttack) {
					idleDownAnim.Update(System.nanoTime());
					idleDownAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
				}
			}
    	}
    	if(getState() == DEATH) {
    		death.Update(System.nanoTime());
    		death.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
    	}
    }

	@Override
	public void shooting() {
		
	}
    
}
