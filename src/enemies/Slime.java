package enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import bullet.Flame;
import dataloader.Animation;
import dataloader.DataLoader;
import state.GameWorldState;
import state.LevelState;

public class Slime extends Enemy {

    private Animation idle;    
    private long startTimeToShoot;
    
    public Slime(float x, float y, GameWorldState gameWorld) {
        super(x, y, 100, 100, 200, gameWorld);       
        idle = DataLoader.getInstance().getAnimation("enemy4_atk");
        startTimeToShoot = 0;       
        setTimeForImmortal(100000000);
        int temp = LevelState.getCurrentChoice();
    	if (temp == 0) {
			setDamage(5);     		
    	} else if (temp == 1){ 
    		setDamage(8);
    		setBlood(300);
    	}
        maxHP = getBlood();
    }     
    
    @Override
    public void attack() {}
    
	public void shooting() {   	                 

		int dame = 5;
		if(LevelState.getCurrentChoice() == 1)
			dame = 10;
		Flame bullet2a = new Flame(getPosX(), getPosY(), 200, 50, dame, getGameWorld());        

        bullet2a.setPosX(bullet2a.getPosX());
        bullet2a.setPosY(bullet2a.getPosY()-70);            
        
        bullet2a.setTeamType(getTeamType());
        getGameWorld().getBulletManager().addObject(bullet2a);
        
        Flame bullet2b = new Flame(getPosX(), getPosY(),200, 50, 5, getGameWorld());        

        bullet2b.setPosX(bullet2b.getPosX());
        bullet2b.setPosY(bullet2b.getPosY()-120);            
        
        bullet2b.setTeamType(getTeamType());
        getGameWorld().getBulletManager().addObject(bullet2b);
        
        Flame bullet2c = new Flame(getPosX(), getPosY(), 200, 50, 5, getGameWorld());        

        bullet2c.setPosX(bullet2c.getPosX());
        bullet2c.setPosY(bullet2c.getPosY()-170);            
    
        bullet2c.setTeamType(getTeamType());
        getGameWorld().getBulletManager().addObject(bullet2c);
    
        Flame bullet2d = new Flame(getPosX(), getPosY(), 200, 50, 5, getGameWorld());        

        bullet2d.setPosX(bullet2d.getPosX()-100);
        bullet2d.setPosY(bullet2d.getPosY()-170);            
    
        bullet2d.setTeamType(getTeamType());
        getGameWorld().getBulletManager().addObject(bullet2d);
        
	}

    
    public void Update(){
    	super.Update();   		        
    	
        if(System.nanoTime() - startTimeToShoot > 1000*10000000*1.5) {
        	shooting();
            startTimeToShoot = System.nanoTime();
        }              
    }
    
    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        return getBoundForCollisionWithMap();
    }
    
    @Override
    public Rectangle getBoundForCollisionWithMap() {
    	 Rectangle bound = new Rectangle();
         bound.x = (int) (getPosX() - (getWidth()/2) - 110);
         bound.y = (int) (getPosY() - (getHeight()/2) - 100);
         bound.width = (int) getWidth();
         bound.height = (int) getHeight();
         return bound;
    }

    @Override
    public void draw(Graphics2D g2) {

    	int x = (int)(dx_cam() - 160);
    	int y = (int)(dy_cam() - 150);
    	g2.setColor(Color.GRAY);
    	g2.fillRect(x, y, 100, 4);
    	g2.setColor(Color.RED);
    	g2.fillRect(x, y, getBlood()/(maxHP/100), 3);
        idle.Update(System.nanoTime());
        idle.draw(dx_cam(), dy_cam(), (int) getWidth(), (int) getHeight(), g2);
    }


	@Override
	public void run() {}    
}
