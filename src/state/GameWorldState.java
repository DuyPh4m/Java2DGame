package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import bullet.BulletManager;
import display.GameFrame;
import display.GamePanel;
import enemies.Boss;
import enemies.Bull;
import enemies.DarkDuck;
import enemies.Enemy;
import enemies.LittleGirl;
import enemies.Slime;
import enemies.Snail;
import gameobject.ParticularObject;
import gameobject.ParticularObjectManager;
import map.BackgroundMap;
import map.Camera;
import map.PhysicalMap;
import player.Player;

public class GameWorldState extends State {
	
    private BufferedImage bufferedImage;
    private int lastState;
    
    private PhysicalMap phys_map;
    private BackgroundMap map;
    private Camera camera;
	private Player player;
	private Enemy boss;

    private BulletManager bulletManager;
    private ParticularObjectManager particularObjectManager;
        
    public static final int INIT_GAME = 0;
    public static final int TUTORIAL = 1;
    public static final int GAMEPLAY = 2;
    public static final int GAMEOVER = 3;
    public static final int GAMEWIN = 4;
    public static final int PAUSEGAME = 5;
    
    public static final int INTROGAME = 0;
    public static final int MEETFINALBOSS = 1;
    
    public int openIntroGameY = 0;
    public int state = INIT_GAME;
    public int previousState = state;
    public int tutorialState = INTROGAME;
    
    public int storyTutorial = 0; 
    public String[] texts1 = new String[4];

    public String textTutorial;
    public int currentSize = 1; 
    private Image image = Toolkit.getDefaultToolkit().getImage("data/player/player_017.png");
    private Image heart = Toolkit.getDefaultToolkit().getImage("data/player/heart.png");
    private boolean changeMap2 = true;
    
    public GameWorldState(GamePanel gamePanel){
        super(gamePanel);
        
        texts1[0] = "\nYou are a HERO, and your mission is protecting your Village....";
        texts1[1] = "There were Monsters from University on this Village in 10 years\n"
                  + "and you lived in the scare in that 10 years...\n";
        texts1[2] = "\nNow is the time for you, find and kill all the Monters and BOSS DRAGON!\n";
        texts1[3] = "\n         LET'S GO!....";
        textTutorial = texts1[0];

        bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        
		player = new Player(500, 500, this);
		phys_map = new PhysicalMap(0 ,0, this);
		map = new BackgroundMap(0, 0, this);
		camera = new Camera(0, 50, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, this );
		
		particularObjectManager = new ParticularObjectManager(this);
		bulletManager = new BulletManager(this);
        initEnemies1(); 
        particularObjectManager.addObject(player);     
    }
    
    public PhysicalMap getPhys_map (PhysicalMap phys_map) {
    	return phys_map;
    }    
    
    private void initEnemies1(){        
    	Enemy[] snail = new Snail[7];
        snail[0] = new Snail(680, 1645, this);
        snail[1] = new Snail(1720, 316, this);
        snail[2] = new Snail(2132, 564, this);
        snail[3] = new Snail(1762, 2255, this);
        snail[4] = new Snail(2010, 2340, this);
        snail[5] = new Snail(2466, 2283, this);
        snail[6] = new Snail(4480, 1950, this);
        for(int i = 0; i < snail.length; i++)
        	particularObjectManager.addObject(snail[i]);
        
        
        Enemy [] bull = new Bull[6];
        bull[0] = new Bull(2736, 612, this);
        bull[1] = new Bull(315, 1900, this);
        bull[2] = new Bull(2140, 1945, this);
        bull[3] = new Bull(4320, 2266, this);
        bull[4] = new Bull(4600, 1550, this);
        bull[5] = new Bull(4630, 1816, this);
        for(int i = 0; i < bull.length; i++)
        	particularObjectManager.addObject(bull[i]);
       
        
        Enemy[] slime = new Slime[3];
        //slime[0] = new Slime(120, 1050, this);
        slime[0] = new Slime(518, 2136, this);
        slime[1] = new Slime(260, 1080, this);
        slime[2] = new Slime(270, 1400, this);
        for(int i = 0; i < slime.length; i++)
            particularObjectManager.addObject(slime[i]);
        
       
        DarkDuck[] duck = new DarkDuck[10];
        duck[0] = new DarkDuck(2226, 192, this);
        duck[0].setXY(2036, 2390, 128, 344);
        duck[1] = new DarkDuck(1110, 2020, this);
        duck[1].setXY(970, 1370, 2015, 2230);
        duck[2] = new DarkDuck(2000, 150, this);
        duck[2].setXY(2000, 2400, 150, 400);
        duck[3] = new DarkDuck(1400, 1900, this);
        duck[3].setXY(1400, 1950, 1850, 2200);
        duck[4] = new DarkDuck(2300, 1900, this);
        duck[4].setXY(2300, 2850, 1850, 2200);
        duck[5] = new DarkDuck(4490, 1830, this);
        duck[5].setXY(4372, 4670, 1680, 2000);
        duck[6] = new DarkDuck(3850, 1657, this);
        duck[6].setXY(3623, 4230, 1585, 1700);
        duck[7] = new DarkDuck(546, 2200, this);
        duck[7].setXY(470, 666, 2130, 2347);
        duck[8] = new DarkDuck(4460, 1600, this);
        duck[8].setXY(4267, 4760, 1484, 1700);
        duck[9] = new DarkDuck(2875, 1970, this);
        duck[9].setXY(2728, 3080, 1824, 2210);
        for(int i = 0; i < duck.length; i++)
        	particularObjectManager.addObject(duck[i]);

    }
    
    private void initEnemies2() {
    	boss = new Boss(2200, 1050, this);
    	particularObjectManager.addObject(boss);
    	
    	Enemy bull = new Bull(1000, 200, this);
    	particularObjectManager.addObject(bull);
    	
    	Enemy girl = new LittleGirl(2200, 200, this);
    	particularObjectManager.addObject(girl);
    	
    	Enemy snail = new Snail(2200, 650, this);
    	particularObjectManager.addObject(snail);
    	
    	Enemy slime = new Slime(254, 831,this);
    	particularObjectManager.addObject(slime);
    	
    	Enemy litl[] = new LittleGirl[4];
    	litl[0] = new LittleGirl(600, 190, this);
    	litl[1] = new LittleGirl(1178, 583, this);
    	litl[2] = new LittleGirl(1630, 1041, this);
    	litl[3] = new LittleGirl(2290, 1041, this);
    	for(int i = 0; i < litl.length; i++)
    		particularObjectManager.addObject(litl[i]);
    	
    	Enemy bul[] = new Bull[2];
    	bul[0] = new Bull(1498, 603, this);
    	bul[1] = new Bull(746, 553, this);
    	for(int i = 0; i < bul.length; i++)
    		particularObjectManager.addObject(bul[i]);
    	
    	DarkDuck duck[] = new DarkDuck[4];
    	duck[0] = new DarkDuck(1654, 190, this);
    	duck[0].setXY(1442, 1818, 120, 279);
    	
    	duck[1] = new DarkDuck(1992, 993, this);
    	duck[1].setXY(1742, 2142, 970, 1120);
    	
    	duck[2] = new DarkDuck(422, 1037, this);
    	duck[2].setXY(274, 610, 970, 1126);
    	
    	duck[3] = new DarkDuck(794, 1030, this);
    	duck[3].setXY(590, 942, 970, 1120);
    	for(int i = 0; i < duck.length; i++)
    		particularObjectManager.addObject(duck[i]);
    	
    	Enemy snai[] = new Snail[3];
        snai[0] = new Snail(546, 190, this);
        snai[1] = new Snail(2240, 195, this);
        snai[2] = new Snail(1392, 1040, this);
        for(int i = 0; i < snai.length; i++)
        	particularObjectManager.addObject(snai[i]);

    }

    public void switchState(int state){
        previousState = this.state;
        this.state = state;
    }
    
    private void TutorialUpdate(){
        switch(tutorialState){
        
            case INTROGAME:
                if(storyTutorial == 0){
                    if(openIntroGameY < 450) {
                        openIntroGameY += 4;
                    }else storyTutorial ++;                    
                }else{                
                    if(currentSize < textTutorial.length()) currentSize++; 
                }
                break;                
        }
    }
    
    private void drawString(Graphics2D g2, String text, int x, int y){
        for(String str : text.split("\n"))
            g2.drawString(str, x, y += g2.getFontMetrics().getHeight());
    }
    
    private void updateMap() {
    	//particularObjectManager.getCount();
    	if( particularObjectManager.getCount() <= 1 && changeMap2 == true ) {
        	phys_map.phys_map[40][98] = 0;
        	phys_map.phys_map[41][98] = 0;
        	
        	map.map[40][98]= 37;
        	map.map[40][99]= 38;
        	map.map[41][98]= 39;
        	map.map[41][99]= 40;
        	
        	if(player.getPosX() > 4900 && player.getPosY() > 2000) {
            	phys_map.setPhys_map2();
            	map.setBackground_map2();
            	phys_map.phys_map[2][0] = 0;
            	phys_map.phys_map[2][1] = 0;
            	phys_map.phys_map[3][0] = 0;
            	phys_map.phys_map[3][1] = 0;
            	
            	map.map[2][0]= 37;
            	map.map[2][1]= 38;
            	map.map[3][0]= 39;
            	map.map[3][1]= 40;
            	
            	player.setPosX(50);
            	player.setPosY(150);
            	initEnemies2();
            	changeMap2 = false;
            }        	
        }    	
    }
    
    public void Update(){    	
        switch(state){
            case INIT_GAME:                
                break;
                
            case TUTORIAL:
                TutorialUpdate();                
                break;
                
            case GAMEPLAY:
            	particularObjectManager.UpdateObjects();
                camera.Update();
                bulletManager.UpdateObjects();
                updateMap();
                
                if(player.getState() == ParticularObject.DEATH){
                	player.setNumberOfLife(player.getNumberOfLife() - 1);
                    if(player.getNumberOfLife() >= 0){
                    	player.setBlood(player.getMaxHP());
                    	player.setPosY(player.getPosY());
                    	player.setState(ParticularObject.IMMORTAL);
                        particularObjectManager.addObject(player);
                    }else{
                        switchState(GAMEOVER);
                    }
                }
                if(changeMap2 == false) {
                	 if(boss.getState() == ParticularObject.DEATH) {
                     	switchState(GAMEWIN);
                     }
                }               
                break;
                
            case GAMEOVER:                
                break;
                
            case GAMEWIN:                
                break;
        }
    }
    
    private void TutorialRender(Graphics2D g2){
        switch(tutorialState){
        case INTROGAME:
            int yMid = GameFrame.SCREEN_HEIGHT/2 - 15;
            int y1 = yMid - GameFrame.SCREEN_HEIGHT/2 - openIntroGameY/2;
            int y2 = yMid + openIntroGameY/2;

            g2.setColor(Color.BLACK);
            g2.fillRect(0, y1, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);
            g2.fillRect(0, y2, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);

            if(storyTutorial >= 1){ 
                g2.drawImage(image, 612, 418, 100, 100, null);
                g2.setColor(Color.BLUE);
                g2.fillRect(280, 450, 350, 80); 
                g2.setColor(Color.WHITE);
                String text = textTutorial.substring(0, currentSize - 1);
                drawString(g2, text, 290, 460);
            }                
            break;
        }
    }    
    
    public void Render(){
       Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
        if(g2 != null){  
            switch(state){
            	
            case INIT_GAME:
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("TimesRoman", Font.PLAIN, 40));
                g2.drawString("PRESS ENTER TO CONTINUE", 210, 310); 
                break;
                
            case PAUSEGAME:
                g2.setColor(Color.BLACK); 
                g2.fillRect(200, 260, 550, 70); 
                g2.setColor(Color.WHITE); 
                g2.setFont(new Font("TimesRoman", Font.PLAIN, 40));
                g2.drawString("PRESS ENTER TO CONTINUE", 210, 310);
                break;
                
            case TUTORIAL:
                map.draw(g2);
                TutorialRender(g2);
                break;
                
            case GAMEWIN:
            	Image winimage = Toolkit.getDefaultToolkit().getImage("data/win_game.png");
//            	g2.setColor(Color.BLACK);
//                g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
//                g2.setColor(Color.WHITE);
//                g2.setFont(new Font("TimesRoman", Font.PLAIN, 40));
//                g2.drawString("All monster have been slain, mission completed!", 100, 300);
            	g2.drawImage(winimage, 0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, null);
                break;
                
            case GAMEPLAY:
           	    map.draw(g2);
           	    particularObjectManager.draw(g2);
           	    bulletManager.draw(g2);
              
                g2.setColor(Color.GRAY);
                g2.fillRect(19, 59, 102, 22);
                g2.setColor(Color.red);
                g2.fillRect(20, 60, player.getBlood()/(player.getMaxHP()/100), 20); 
                g2.setColor(Color.RED);
                g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
                g2.drawString("HP: " + player.getBlood()+"/" + player.getMaxHP(), 125, 80 );
                g2.setColor(Color.YELLOW);
                g2.drawString("Dame: " + player.getCurrentDame(), 20, 100);
                g2.setColor(Color.BLUE);
                g2.drawString("Enemies: " + particularObjectManager.getCount(), 20, 120);
                for(int i = 0; i < player.getNumberOfLife(); i++){
                    g2.drawImage(heart, 15 + i*40, 18, null);
                }
                break;
                
            case GAMEOVER:
//                g2.setColor(Color.BLACK);
//                g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
//                g2.setColor(Color.WHITE);
//                g2.setFont(new Font("TimesRoman", Font.PLAIN, 40));
//                g2.drawString("GAME OVER!", 350, 300);
            	Image losegame = Toolkit.getDefaultToolkit().getImage("data/losegame.png");
            	g2.drawImage(losegame, 0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, null);

                break;

            }
            
       	   
        }

    }

    public BufferedImage getBufferedImage(){
        return bufferedImage;
    }

    @Override
    public void setPressedButton(int code) {
       switch(code){
	        case KeyEvent.VK_UP:
	        	player.setDirection(ParticularObject.UP_DIR);
	        	player.run();
	            break;
            
	        case KeyEvent.VK_DOWN:
	        	player.setDirection(ParticularObject.DOWN_DIR);
	        	player.run();
	            break;
	            
	        case KeyEvent.VK_RIGHT:
	        	player.setDirection(ParticularObject.RIGHT_DIR);
	        	player.run();
	            break;
	            
	        case KeyEvent.VK_LEFT:
	        	player.setDirection(ParticularObject.LEFT_DIR);
	        	player.run();
	            break;
	            
	        case KeyEvent.VK_ENTER:
	            if(state == INIT_GAME){
	                if(previousState == GAMEPLAY)
	                    switchState(GAMEPLAY);
	                else switchState(TUTORIAL);
	            }
	            if(state == TUTORIAL && storyTutorial >= 1){
	                if(storyTutorial<=3){
	                    storyTutorial ++;
	                    currentSize = 1;
	                    textTutorial = texts1[storyTutorial - 1];
	                }else{
	                    switchState(GAMEPLAY);
	                }
	                
	                if(tutorialState == MEETFINALBOSS){
	                    switchState(GAMEPLAY);
	                }
	            }
	            break;
	            
	        case KeyEvent.VK_SPACE:
	        	player.attack();
	            break;	 
	        case KeyEvent.VK_A:
	        	player.shooting();
	        	break;
        }
    }

    @Override
    public void setReleasedButton(int code) {
        switch(code){
            
            case KeyEvent.VK_UP:            	
            		player.stopRun();
                break;
                
            case KeyEvent.VK_DOWN:           	
            		player.stopRun();
                break;
                
            case KeyEvent.VK_RIGHT:                
            		player.stopRun();
                break;
                
            case KeyEvent.VK_LEFT:                
            		player.stopRun();
                break;
                
            case KeyEvent.VK_ENTER:
                if(state == GAMEOVER || state == GAMEWIN) {
                    gamePanel.setState(new MenuState(gamePanel));
                } else
                	if(state == PAUSEGAME) {
                    state = lastState; 
                }
                break;
                
            case KeyEvent.VK_SPACE:
                
                break;
                
            case KeyEvent.VK_ESCAPE:
                lastState = state;
                state = PAUSEGAME;
                break;
                
        }
    }

	public PhysicalMap getPhys_map() {
		return phys_map;
	}

	public void setPhys_map(PhysicalMap phys_map) {
		this.phys_map = phys_map;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public BulletManager getBulletManager() {
		return bulletManager;
	}

	public void setBulletManager(BulletManager bulletManager) {
		this.bulletManager = bulletManager;
	}

	public ParticularObjectManager getParticularObjectManager() {
		return particularObjectManager;
	}

	public void setParticularObjectManager(ParticularObjectManager particularObjectManager) {
		this.particularObjectManager = particularObjectManager;
	}

	public BackgroundMap getMap() {
		return map;
	}

	public void setMap(BackgroundMap map) {
		this.map = map;
	}
    
	@Override
	public void actionMenu() {}

	
}