package item;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import dataloader.Animation;
import dataloader.DataLoader;
import gameobject.ParticularObject;
import state.GameWorldState;

public class ItemDame extends ParticularObject {
	
	private boolean isExist = true;
	private int bufDame;
	private Animation dame;
	public ItemDame(float x, float y, int width, int height, GameWorldState gameWorld) {
		super(x, y, width, height, 1, gameWorld);
		dame = DataLoader.getInstance().getAnimation("itemdame");
		setTeamType(ENEMY_TEAM);
		setDamage(0);
		bufDame = 1;
	}

	@Override
	public Rectangle getBoundForCollisionWithEnemy() {		
		return getBoundForCollisionWithMap();
	}
	
	@Override
	public void Update() {
		if(this.getBoundForCollisionWithEnemy().intersects(getGameWorld().getPlayer().getBoundForCollisionWithEnemy())){
			setBlood(0);
		    getGameWorld().getPlayer().setCurrentDame(getGameWorld().getPlayer().getCurrentDame() + bufDame);
		    bufDame = 0;
			isExist = false;
		}        
	}

	@Override
	public void draw(Graphics2D g2) {
		if(isExist == true) {
			dame.Update(System.nanoTime());                      
			dame.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
			//drawBoundForCollisionWithEnemy(g2);
		}
	}	

	@Override
	public void attack() {}

	@Override
	public void shooting() {}

	public int getBufDame() {
		return bufDame;
	}

	public void setBufDame(int bufDame) {
		this.bufDame = bufDame;
	}

	
    
}

