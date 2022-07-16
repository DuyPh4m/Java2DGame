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

public class AboutUsState extends State{
	private BufferedImage bufferedImage;
    private Graphics graphicsPaint;
	private int currentChoice = -1;
		
	private Font font = new Font("Arial", Font.PLAIN, 20);
	private String[] options = {
			"Menu",
			"Quit"
		};
		
	public AboutUsState(GamePanel gamePanel) {
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
        Image aboutus = Toolkit.getDefaultToolkit().getImage("data/AboutUs.png");
        graphicsPaint.drawImage(aboutus, 0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, null);
//    	graphicsPaint.setFont(fontIntro);
//        graphicsPaint.setColor(Color.BLACK); 
//		graphicsPaint.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight()); 	
//
//        graphicsPaint.setColor(Color.WHITE); 
//		
//    	graphicsPaint.drawString("This is a simple RPG game project for OOP - 20202", GameFrame.SCREEN_WIDTH/4 - 70, 100);
//    	graphicsPaint.drawString("Menter: Ths. Nguyen Manh Tuan", GameFrame.SCREEN_WIDTH/3 - 30, 155);
//    	graphicsPaint.drawString("We are:", GameFrame.SCREEN_WIDTH/3 - 30, 195);
//    	graphicsPaint.drawString("Nguyen Van Bien - 20193993", GameFrame.SCREEN_WIDTH/3, 235);
//    	graphicsPaint.drawString("Nguyen Quang Linh - 20194092", GameFrame.SCREEN_WIDTH/3, 275);
//    	graphicsPaint.drawString("Nguyen Van Minh - 20194121", GameFrame.SCREEN_WIDTH/3, 315);
//    	graphicsPaint.drawString("Chu Manh Tien - 20194182", GameFrame.SCREEN_WIDTH/3, 355);	
//		
		graphicsPaint.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				graphicsPaint.setColor(Color.YELLOW);
			} else {
				graphicsPaint.setColor(Color.BLUE);
			}
			graphicsPaint.drawString(options[i], GameFrame.SCREEN_WIDTH/2 - 15, 425 + i * 30);
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
                gamePanel.setState(new MenuState(gamePanel)); 
                break; 
            case 1:
            	System.exit(1);
        }
    }
}
