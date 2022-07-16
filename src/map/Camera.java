package map;

import gameobject.GameObject;
import player.Player;
import state.GameWorldState;

public class Camera extends GameObject {

    private float widthView;
    private float heightView;
    
    public Camera(float x, float y, float widthView, float heightView, GameWorldState gameWorld) {
        super(x, y, gameWorld);
        this.widthView = widthView;
        this.heightView = heightView;
    }

    @Override
    public void Update() {  
        Player mainCharacter = getGameWorld().getPlayer();

        if(mainCharacter.getPosX() - getPosX() > 400) setPosX(mainCharacter.getPosX() - 400);
        if(mainCharacter.getPosX() - getPosX() < 200) setPosX(mainCharacter.getPosX() - 200);

        if(mainCharacter.getPosY() - getPosY() > 400) setPosY(mainCharacter.getPosY() - 400); 
        else if(mainCharacter.getPosY() - getPosY() < 250) setPosY(mainCharacter.getPosY() - 250);
        
        if(mainCharacter.getPosX() <= 200) setPosX(0);
        if(mainCharacter.getPosY() <= 250) setPosY(0);
        
        if(mainCharacter.getPosX() >= 50 * (getGameWorld().getPhys_map().getMatrix()[0].length) - 590) setPosX(50 * (getGameWorld().getPhys_map().getMatrix()[0].length) - 990);
        if(mainCharacter.getPosY() >= 50 * (getGameWorld().getPhys_map().getMatrix().length) - 165) setPosY(50 * (getGameWorld().getPhys_map().getMatrix().length) - 565);
    }

    public float getWidthView() {
        return widthView;
    }

    public void setWidthView(float widthView) {
        this.widthView = widthView;
    }

    public float getHeightView() {
        return heightView;
    }

    public void setHeightView(float heightView) {
        this.heightView = heightView;
    }
    
}