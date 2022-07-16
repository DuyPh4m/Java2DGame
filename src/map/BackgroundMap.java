package map;

import state.GameWorldState;
import gameobject.GameObject;

import java.awt.Color;
import java.awt.Graphics2D;

import dataloader.DataLoader;
import display.GameFrame;



public class BackgroundMap extends GameObject {

    public int[][] map;
    private int tileSize;
    
    public BackgroundMap(float x, float y, GameWorldState gameWorld) {
        super(x, y, gameWorld);
        map = DataLoader.getInstance().getBackgroundMap();
        tileSize = 50;
    }
    
    public void setBackground_map2() {
    	map = DataLoader.getInstance().getBackgroundMap2();
    }

    @Override
    public void Update() {}
    
    public void draw(Graphics2D g2){
        
    	Camera camera = getGameWorld().getCamera();
        
        g2.setColor(Color.RED);
        for(int i=0; i<map.length; i++)
            for(int j=0; j<map[0].length; j++)
                if( j*tileSize - camera.getPosX() > -50 
                 && j*tileSize - camera.getPosX() < GameFrame.SCREEN_WIDTH
                 && i*tileSize - camera.getPosY() > -50 
                 && i*tileSize - camera.getPosY() < GameFrame.SCREEN_HEIGHT){ 
                    g2.drawImage(DataLoader.getInstance().getFrameImage("tiled"+map[i][j]).getImage(),
                    			(int) getPosX() + j*tileSize - (int) camera.getPosX(), 
                    			(int) getPosY() + i*tileSize - (int) camera.getPosY(), null);
                }
        
    }
    
}