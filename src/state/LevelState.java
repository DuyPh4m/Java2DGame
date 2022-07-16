package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import display.GameFrame;
import display.GamePanel;

public class LevelState extends State{
	private BufferedImage bufferedImage;
    private Graphics graphicsPaint;
	private static int currentChoice = -1;
	
	private Image image = Toolkit.getDefaultToolkit().getImage("data/back-win.png");
	
	private Color titleColor = new Color(255, 0, 0);
	private Font titleFont = new Font("Century Gothic", Font.PLAIN, 80);
	private Font font = new Font("Arial", Font.PLAIN, 40);
	private String[] options = {
			"Easy",
			"Hard",
			"Menu"
		};
		
	public LevelState(GamePanel gamePanel) {
        super(gamePanel); 
        bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);    
    }
	
    @Override
    public void Render() {
    	if(bufferedImage == null) { 
            bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            return;
        }
        
        graphicsPaint = bufferedImage.getGraphics();     
        if(graphicsPaint == null) { 
            graphicsPaint = bufferedImage.getGraphics();
            return;
        }
        
        graphicsPaint.setColor(Color.CYAN); 
		graphicsPaint.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight()); 		
		graphicsPaint.drawImage(image, 0, 0, null);
		
		graphicsPaint.setColor(titleColor);
		graphicsPaint.setFont(titleFont);
		graphicsPaint.drawString("Selected Level", 180, 135);
		
		graphicsPaint.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				graphicsPaint.setColor(Color.BLACK);
			} else {
				graphicsPaint.setColor(Color.BLUE);
			}
			graphicsPaint.drawString(options[i], GameFrame.SCREEN_WIDTH/2 - 100, 290 + i * 65);
		}
    }
    
    @Override
    public void Update() {}

    @Override
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    @Override
    public void setPressedButton(int code) {
        switch(code) {
        
            case KeyEvent.VK_DOWN: 
                currentChoice++;
                if(currentChoice >= options.length) {
                    currentChoice = 0;
                }
                break;
                
            case KeyEvent.VK_UP: 
                currentChoice--;
                if(currentChoice < 0) {
                    currentChoice = options.length - 1;
                }
                break;
                
            case KeyEvent.VK_ENTER: 
                actionMenu();
                break;
        }
    }
    
    @Override
    public void setReleasedButton(int code) {}
    
    @Override
	public void actionMenu() {
        switch(currentChoice) {
            case 0:
                gamePanel.setState(new GameWorldState(gamePanel)); 
                break;                
                     	
            case 1:
            	gamePanel.setState(new GameWorldState(gamePanel)); 
                break;
            case 2: 
            	gamePanel.setState(new MenuState(gamePanel)); 
                break;
        }
    }
    
    public static int getCurrentChoice() {
    	return currentChoice;
    }
}
