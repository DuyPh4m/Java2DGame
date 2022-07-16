package gameobject;

import state.GameWorldState;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import enemies.Boss;
import enemies.Bull;
import enemies.DarkDuck;
import enemies.Enemy;
import enemies.LittleGirl;
import enemies.Slime;
import enemies.Snail;
import item.ItemDame;
import item.ItemHP;
import item.ItemHeart;

public class ParticularObjectManager {

    protected List<ParticularObject> particularObjects;
    private GameWorldState gameWorld;
    
    private int count = 0;
    
    public ParticularObjectManager(GameWorldState gameWorld){        
        particularObjects = Collections.synchronizedList(new LinkedList<ParticularObject>());
        this.gameWorld = gameWorld;        
    }
 
    public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public GameWorldState getGameWorld(){
        return gameWorld;
    }
    
    public void addObject(ParticularObject particularObject){       
        synchronized(particularObjects){
    		particularObjects.add(particularObject);
        	if(particularObject instanceof Enemy) 
        	    count++;
        }
    }
    
    public void RemoveObject(ParticularObject particularObject){
        synchronized(particularObjects){        
            for(int id = 0; id < particularObjects.size(); id++){                
                ParticularObject object = particularObjects.get(id);
                if(object == particularObject)
                    particularObjects.remove(id);
            }
        }
    }
    
    public ParticularObject getCollisionWidthEnemyObject(ParticularObject object){
        synchronized(particularObjects){
            for(int id = 0; id < particularObjects.size(); id++){
                
                ParticularObject objectInList = particularObjects.get(id);

                if(object.getTeamType() != objectInList.getTeamType() 
                && object.getBoundForCollisionWithEnemy().intersects(objectInList.getBoundForCollisionWithEnemy())){
                    return objectInList;
                }
            }
        }
        return null;
    }
    
    public void UpdateObjects(){
        synchronized(particularObjects){
            for(int id = 0; id < particularObjects.size(); id++){
                
                ParticularObject object = particularObjects.get(id);
                               
                if(!object.isObjectOutOfCameraView()) object.Update();
                
                if(object.getState() == ParticularObject.DEATH) {
                	Rectangle rect = object.getBoundForCollisionWithMap();
                	Random rand = new Random();
                	particularObjects.remove(id);
                	if( object instanceof Boss
                     || object instanceof LittleGirl
                     || object instanceof Snail
                     || object instanceof Slime
                     || object instanceof DarkDuck) {
                		//object = (Enemy) object;
                		count--;
                		int i = rand.nextInt(2) + 1;
                		if(i == 1) {
                			ItemHP item1 = new ItemHP(rect.x + rect.width/2, rect.y + rect.height/2, 24, 24, getGameWorld());
                    		item1.setBufHP(object.getDamage() * 8);
                    		getGameWorld().getParticularObjectManager().addObject(item1);
                		}
                		else if(i == 2) {
                			ItemDame item1 = new ItemDame(rect.x + rect.width/2, rect.y + rect.height/2, 24, 24, getGameWorld());
                    		if(object.getDamage()/4 != 0)
                			       item1.setBufDame(object.getDamage()/2 + 1);
                    		getGameWorld().getParticularObjectManager().addObject(item1);
                		}
                	}
                	else if(object instanceof Bull) {
                		count--;
                
               
                		int i = rand.nextInt(3) + 1;
                		if(i == 2) {
                			ItemHeart item2 = new ItemHeart(rect.x + rect.width/2, rect.y + rect.height/2, 24, 24, getGameWorld());
                    		getGameWorld().getParticularObjectManager().addObject(item2);
                		}
                		else if(i == 1) {
                			ItemHP item1 = new ItemHP(object.getPosX(), object.getPosY(), 24, 24, getGameWorld());
                    		item1.setBufHP(object.getDamage() * 8);
                    		getGameWorld().getParticularObjectManager().addObject(item1);
                		}
                		else {
                			ItemDame item3 = new ItemDame(rect.x + rect.width/2, rect.y + rect.height/2, 24, 24, getGameWorld());
                    		if(object.getDamage()/4 != 0)
                			       item3.setBufDame(object.getDamage()/4 + 1);
                    		getGameWorld().getParticularObjectManager().addObject(item3);
                		}
                	}
                }
            }
        }
        
    }
    
    public void draw(Graphics2D g2){
        synchronized(particularObjects){
            for(ParticularObject object: particularObjects)
            	if(!object.isObjectOutOfCameraView()) object.draw(g2);
        }
    }
	
}
