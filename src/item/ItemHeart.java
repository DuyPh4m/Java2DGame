package item;


import java.awt.Graphics2D;
import java.awt.Rectangle;

import dataloader.Animation;
import dataloader.DataLoader;
import gameobject.ParticularObject;
import state.GameWorldState;

public class ItemHeart extends ParticularObject {
	
	private boolean isExist = true;
	
	private Animation heart;
	private int bufHeart;
	public ItemHeart(float x, float y, int width, int height, GameWorldState gameWorld) {
		super(x, y, width, height, 1, gameWorld);
		heart = DataLoader.getInstance().getAnimation("itemheart");
		setTeamType(ENEMY_TEAM);
		setDamage(0);
		bufHeart = 1;
	}

	@Override
	public Rectangle getBoundForCollisionWithEnemy() {		
		return getBoundForCollisionWithMap();
	}
	
	@Override
	public void Update() {
		if(this.getBoundForCollisionWithEnemy().intersects(getGameWorld().getPlayer().getBoundForCollisionWithEnemy())){
			setBlood(0);
			if(getGameWorld().getPlayer().getNumberOfLife() >= 6)
				bufHeart = 0;
			getGameWorld().getPlayer().setNumberOfLife(getGameWorld().getPlayer().getNumberOfLife() + bufHeart);
			bufHeart = 0;
			isExist = false;
		}        
	}

	@Override
	public void draw(Graphics2D g2) {
		if(isExist == true) {
			heart.Update(System.nanoTime());                      
			heart.draw(dx_cam(), dy_cam(), (int)getWidth(), (int)getHeight(), g2);
			//drawBoundForCollisionWithEnemy(g2);
		}
	}	

	@Override
	public void attack() {}

	@Override
	public void shooting() {}

}
