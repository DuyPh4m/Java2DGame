package player;

import state.GameWorldState;
import state.LevelState;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import bullet.PlayerBullet;
import bullet.Sword;
import dataloader.Animation;
import dataloader.DataLoader;

public class Player extends Human {

	private int count = 0;
	
    public static final int RUNSPEED = 2;

    private Animation runForwardAnim, runBackAnim, runUpAnim, runDownAnim;
    private Animation idleForwardAnim, idleUpAnim, idleDownAnim, idleBackAnim,
    idleShootingForwardAnim, idleShootingBackAnim, idleShootingUpAnim, idleShootingDownAnim,
    idleAttackForwardAnim, idleAttackBackAnim, idleAttackUpAnim, idleAttackDownAnim;

    private long lastShootingTime;
    private long lastAttackTime;
    private long lastHealTime;
    private boolean isShooting = false;
    private boolean isAttack = false;
    private boolean isRuning = false;
    private int numberOfLife;
    private int currentDame;
    public Player(float x, float y, GameWorldState gameWorld) {
        super(x, y, 40, 40, 300, gameWorld);
        int temp = LevelState.getCurrentChoice();
    	if (temp == 1){ 
    		setBlood(200);
    	}
        maxHP = getBlood();
        setTimeForImmortal(200000000);        
        setDamage(0);
        setTeamType(LEAGUE_TEAM);
        numberOfLife = 3;
        runForwardAnim = DataLoader.getInstance().getAnimation("player_runright");
        runBackAnim = DataLoader.getInstance().getAnimation("player_runleft"); 
        runUpAnim = DataLoader.getInstance().getAnimation("player_runup");
        runDownAnim = DataLoader.getInstance().getAnimation("player_rundown");
        
        idleForwardAnim = DataLoader.getInstance().getAnimation("player_idleright");
        idleBackAnim = DataLoader.getInstance().getAnimation("player_idleleft");   
        idleUpAnim = DataLoader.getInstance().getAnimation("player_idleup");
        idleDownAnim = DataLoader.getInstance().getAnimation("player_idledown");
        
        idleShootingForwardAnim = DataLoader.getInstance().getAnimation("player_atkright_2");
        idleShootingBackAnim = DataLoader.getInstance().getAnimation("player_atkleft_2");
        idleShootingUpAnim = DataLoader.getInstance().getAnimation("player_atkup_2");
        idleShootingDownAnim = DataLoader.getInstance().getAnimation("player_atkdown_2");
        
        idleAttackForwardAnim = DataLoader.getInstance().getAnimation("player_atkright");
        idleAttackBackAnim = DataLoader.getInstance().getAnimation("player_atkleft");
        idleAttackUpAnim = DataLoader.getInstance().getAnimation("player_atkup");
        idleAttackDownAnim = DataLoader.getInstance().getAnimation("player_atkdown");
        setCurrentDame(25);
    }
    
    public boolean getIsAttack() {
    	return isAttack;
    }

    @Override
    public void Update() {
        super.Update();        
        heal();
        // System.out.println(getPosX() + ", " + getPosY());
        if(isAttack) {
        	if(System.nanoTime() - lastAttackTime > 500*1000000) {		
        		isAttack = false;
        	}
        }
        
        if(isShooting){
            if(System.nanoTime() - lastShootingTime > 5000*1000000){
                isShooting = false;
            }
        }
        
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {    	
        Rectangle rect = getBoundForCollisionWithMap();
        rect.height -= 8;
        rect.width -= 16;
        rect.x += 11;
        rect.y += 10;
        return rect;   
    }

    @Override
    public void draw(Graphics2D g2) {
        switch(getState()){        
            case ALIVE:
            
            case IMMORTAL:                		
            	if(getDirection() == RIGHT_DIR) {
            		if(isRuning) {           	
                        runForwardAnim.Update(System.nanoTime());                      
                        runForwardAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
                        if(runForwardAnim.getcurrentImageIndex() == 1) runForwardAnim.setIgnoreImage(0);
            		}
            		else if(isAttack) {
                        idleAttackForwardAnim.Update(System.nanoTime());
                        idleAttackForwardAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
            		}
            		else if(isShooting) {
            			idleShootingForwardAnim.Update(System.nanoTime());
            			idleShootingForwardAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
            		}
            		else {
            			idleForwardAnim.Update(System.nanoTime());
            			idleForwardAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
            		}
            	}
            	else if(getDirection() == LEFT_DIR) {
            		if(isRuning) {           	
                        runBackAnim.Update(System.nanoTime());                      
                        runBackAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
                        if(runBackAnim.getcurrentImageIndex() == 1) runBackAnim.setIgnoreImage(0);
            		}
            		else if(isAttack) {
                        idleAttackBackAnim.Update(System.nanoTime());
                        idleAttackBackAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
            		}
            		else if(isShooting) {
            			idleShootingBackAnim.Update(System.nanoTime());
            			idleShootingBackAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
            		}
            		else {
            			idleBackAnim.Update(System.nanoTime());
            			idleBackAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
            		}
            	}
            	else if(getDirection() == UP_DIR) {
            		if(isRuning) {           	
                        runUpAnim.Update(System.nanoTime());                      
                        runUpAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
                        if(runUpAnim.getcurrentImageIndex() == 1) runUpAnim.setIgnoreImage(0);
            		}
            		else if(isAttack) {
                        idleAttackUpAnim.Update(System.nanoTime());
                        idleAttackUpAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
            		}
            		else if(isShooting) {
            			idleShootingUpAnim.Update(System.nanoTime());
            			idleShootingUpAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
            		}
            		else {
            			idleUpAnim.Update(System.nanoTime());
            			idleUpAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
            		}
            	}
            	else {
            		if(isRuning) {           	
                        runDownAnim.Update(System.nanoTime());                      
                        runDownAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
                        if(runDownAnim.getcurrentImageIndex() == 1) runDownAnim.setIgnoreImage(0);
            		}
            		else if(isAttack) {
                        idleAttackDownAnim.Update(System.nanoTime());
                        idleAttackDownAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
            		}
            		else if(isShooting) {
            			idleShootingDownAnim.Update(System.nanoTime());
            			idleShootingDownAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
            		}
            		else {
            			idleDownAnim.Update(System.nanoTime());
            			idleDownAnim.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
            		}
            	}                
                break;
            
            case BEHURT:                
                break;           

        }
    }

    @Override
    public void run() {
    	isRuning = true;
        if (getDirection() == LEFT_DIR) setSpeedX(-RUNSPEED);
        else if (getDirection() == RIGHT_DIR) setSpeedX(RUNSPEED);
        else if (getDirection() == UP_DIR) setSpeedY(-RUNSPEED);
        else setSpeedY(RUNSPEED);
    }

    @Override
    public void stopRun() {
    	isRuning = false;
        setSpeedX(0);
        setSpeedY(0);
    }

    @Override
    public void shooting() {
    
        if(!isShooting && count >= 5){
        	System.out.println("shoot1");
            PlayerBullet bullet = new PlayerBullet(getPosX(), getPosY(), getGameWorld());
            if(getDirection() == LEFT_DIR) {
                bullet.setSpeedX(-10);
                bullet.setPosX(bullet.getPosX() - 40);
                
            }else if(getDirection() == RIGHT_DIR){
                bullet.setSpeedX(10);
                bullet.setPosX(bullet.getPosX() + 40);
                
            }else if(getDirection() == UP_DIR) {
            	bullet.setSpeedY(-10);
                bullet.setPosY(bullet.getPosY() - 40);
                
            }else {
            	bullet.setSpeedY(10);
                bullet.setPosY(bullet.getPosY() + 40);
                
            }
            
            bullet.setTeamType(getTeamType());
            getGameWorld().getBulletManager().addObject(bullet);
            
            lastShootingTime = System.nanoTime();
            isShooting = true;
            count = 0;
            
        }
    
    }

    @Override
    public void attack() {
		if(!isAttack){
			System.out.println("attack1");
			
            Sword sword = new Sword(getPosX(), getPosY(), getGameWorld());
            sword.setDamage(currentDame);
            if(sword != null) {
            	System.out.println("Count = " + getGameWorld().getParticularObjectManager().getCount() + "\n" + "currDame = " + currentDame);
            }
            if(getDirection() == LEFT_DIR) {
            	sword.setPosX(sword.getPosX() - 5);
                
            }else if(getDirection() == RIGHT_DIR){
            	sword.setPosX(sword.getPosX() + 5);
                
            }else if(getDirection() == UP_DIR){
            	sword.setPosY(sword.getPosY() - 5);
                
            }else {
            	sword.setPosY(sword.getPosY() + 5);
                
            }
            
            sword.setTeamType(getTeamType());
            getGameWorld().getBulletManager().addObject(sword);
            
            lastAttackTime = System.nanoTime();
            isAttack = true;
            count++;
		}
    }

    public void heal() {
    	if(System.nanoTime() - lastHealTime > 300000000
    	 && getBlood() < 100) {
    		setBlood(getBlood() + 1);
        	lastHealTime = System.nanoTime();
    	}

    }

	public int getNumberOfLife() {
		return numberOfLife;
	}

	public void setNumberOfLife(int numberOfLife) {
		this.numberOfLife = numberOfLife;
	}

	public int getCurrentDame() {
		return currentDame;
	}

	public void setCurrentDame(int currentDame) {
		this.currentDame = currentDame;
	}
    
	
}