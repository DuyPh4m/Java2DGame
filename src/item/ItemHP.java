package item;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import dataloader.Animation;
import dataloader.DataLoader;
import gameobject.ParticularObject;
import state.GameWorldState;

public class ItemHP extends ParticularObject {
	
	private boolean isExist = true;
	private int bufHP;
	private Animation hp;
	public ItemHP(float x, float y, int width, int height, GameWorldState gameWorld) {
		super(x, y, width, height, 1, gameWorld);
		hp = DataLoader.getInstance().getAnimation("itemhp");
		setTeamType(ENEMY_TEAM);
		setDamage(0);
		
	}

	@Override
	public Rectangle getBoundForCollisionWithEnemy() {		
		return getBoundForCollisionWithMap();
	}
	
	@Override
	public void Update() {
		if(this.getBoundForCollisionWithEnemy().intersects(getGameWorld().getPlayer().getBoundForCollisionWithEnemy())){
			setBlood(0);
		
			if(getGameWorld().getPlayer().getBlood() + getDamage() <= getGameWorld().getPlayer().getMaxHP()) {
				getGameWorld().getPlayer().setBlood(getGameWorld().getPlayer().getBlood() + bufHP);
                setBufHP(0);
			}
			else getGameWorld().getPlayer().setBlood(getGameWorld().getPlayer().getMaxHP());
			isExist = false;
		}        
	}

	@Override
	public void draw(Graphics2D g2) {
		if(isExist == true) {
			hp.Update(System.nanoTime());                      
			hp.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
			//drawBoundForCollisionWithEnemy(g2);
		}
	}	

	@Override
	public void attack() {}

	@Override
	public void shooting() {}

	public int getBufHP() {
		return bufHP;
	}

	public void setBufHP(int bufHP) {
		this.bufHP = bufHP;
	}
    
}

