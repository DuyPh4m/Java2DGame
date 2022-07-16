package gameobject;

import state.GameWorldState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;



public abstract class ParticularObject extends GameObject {

    public static final int LEAGUE_TEAM = 1;
    public static final int ENEMY_TEAM = 2;
	
    public static final int LEFT_DIR = 0;
    public static final int RIGHT_DIR = 1;
    public static final int UP_DIR = 2;
    public static final int DOWN_DIR = 3;

    public static final int ALIVE = 0;
    public static final int BEHURT = 1;
    public static final int IMMORTAL = 2;
    public static final int DEATH = 3;
    
    private int state = ALIVE;
    
    private float width;
    private float height;
    private float speedX;
    private float speedY;
    private int blood;
    private int damage;    
    private int direction;

    
    private int teamType;    
    private long startTimeImmortal;
    private long timeForImmortal;

    public ParticularObject(float x, float y, float width, float height, int blood, GameWorldState gameWorld){
        super(x, y, gameWorld);
        setWidth(width);
        setHeight(height);
        setBlood(blood);        
        direction = RIGHT_DIR;
    }
   
	
    public long getStartTimeImmortal() {
		return startTimeImmortal;
	}


	public void setStartTimeImmortal(long startTimeImmortal) {
		this.startTimeImmortal = startTimeImmortal;
	}


	public long getTimeForImmortal() {
		return timeForImmortal;
	}


	public void setTimeForImmortal(long timeForImmortal) {
		this.timeForImmortal = timeForImmortal;
	}


	public void setTeamType(int team){
        teamType = team;
    }
    
    public int getTeamType(){
        return teamType;
    }

    public void setState(int state){
        this.state = state;
    }
    
    public int getState(){
        return state;
    }
    
    public void setDamage(int damage){
            this.damage = damage;
    }

    public int getDamage(){
            return damage;
    }

    public void setSpeedX(float speedX){
        this.speedX = speedX;
    }

    public float getSpeedX(){
        return speedX;
    }

    public void setSpeedY(float speedY){
        this.speedY = speedY;
    }

    public float getSpeedY(){
        return speedY;
    }

    public void setBlood(int blood){
        if(blood>=0)
                this.blood = blood;
        else this.blood = 0;
    }

    public int getBlood(){
        return blood;
    }

    public void setWidth(float width){
        this.width = width;
    }

    public float getWidth(){
        return width;
    }

    public void setHeight(float height){
        this.height = height;
    }

    public float getHeight(){
        return height;
    }
    
    public void setDirection(int dir){
        direction = dir;
    }
    
    public int getDirection(){
        return direction;
    }
    
    public abstract void attack();
    
    public abstract void shooting();
    
    public boolean isObjectOutOfCameraView(){
        if(dx_cam() > getGameWorld().getCamera().getWidthView() 
        || dx_cam() < -50
        || dy_cam() > getGameWorld().getCamera().getHeightView()
        || dy_cam() < -50)
            return true;
        else return false;
    }

    public Rectangle getBoundForCollisionWithMap(){
        Rectangle bound = new Rectangle();
        bound.x = (int) (getPosX() - (getWidth()/2));
        bound.y = (int) (getPosY() - (getHeight()/2));
        bound.width = (int) getWidth();
        bound.height = (int) getHeight();
        return bound;
    }

    public void beHurt(int damgeEat){
        setBlood(getBlood() - damgeEat);
        state = BEHURT;
    }

    @Override
    public void Update(){

        switch(state){
            case ALIVE:
                ParticularObject object = getGameWorld().getParticularObjectManager().getCollisionWidthEnemyObject(this);
                if(object!=null){
                                        
                    if(object.getDamage() > 0){
                                                
                        System.out.println("eat damage.... from collision with enemy........ "+object.getDamage());
                        beHurt(object.getDamage());
                    }                    
                }  
                break;
                
            case BEHURT:                
                	state = IMMORTAL;
                    setStartTimeImmortal(System.nanoTime());
                    if(getBlood() == 0) state = DEATH;                                   
                break;
                
            case IMMORTAL:
            	System.out.println("state = IMMORTAL");
                if(System.nanoTime() - getStartTimeImmortal() > timeForImmortal)
                    state = ALIVE;
                break;
            
            case DEATH:     
                break;               
        }        
    }

    public void drawBoundForCollisionWithMap(Graphics2D g2){
        Rectangle rect = getBoundForCollisionWithMap();
        g2.setColor(Color.BLUE);
        g2.drawRect(rect.x - (int) getGameWorld().getCamera().getPosX(), rect.y - (int) getGameWorld().getCamera().getPosY(), rect.width, rect.height);
    }

    public void drawBoundForCollisionWithEnemy(Graphics2D g2){
        Rectangle rect = getBoundForCollisionWithEnemy();
        g2.setColor(Color.RED);
        g2.drawRect(rect.x - (int) getGameWorld().getCamera().getPosX(), rect.y - (int) getGameWorld().getCamera().getPosY(), rect.width, rect.height);
    }

    protected int dx_cam() {
    	return (int) ( getPosX() - (int) getGameWorld().getCamera().getPosX() );
    }
    
    protected int dy_cam() {
    	return (int) ( getPosY() - (int) getGameWorld().getCamera().getPosY() );
    }
    
    protected int dx() {
    	return (int) (getPosX() - getGameWorld().getPlayer().getPosX());
    }
    
    protected int dy() {
    	return (int) (getPosY() - getGameWorld().getPlayer().getPosY());
    }

	
	
	public abstract Rectangle getBoundForCollisionWithEnemy();
	public abstract void draw(Graphics2D g2);
    
}