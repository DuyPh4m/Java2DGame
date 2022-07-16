package display;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

import dataloader.DataLoader;

import java.io.IOException;

public class GameFrame extends JFrame{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 600;

    GamePanel gamePanel;

    public GameFrame(){
        super("RPG Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = this.getToolkit();
        Dimension solution = toolkit.getScreenSize();

        try {
            DataLoader.getInstance().LoadData();
        } catch (IOException ex) {}

        this.setBounds((solution.width - SCREEN_WIDTH)/2, (solution.height - SCREEN_HEIGHT)/2, SCREEN_WIDTH, SCREEN_HEIGHT);

        gamePanel = new GamePanel();        
        addKeyListener(gamePanel);
        add(gamePanel);
    }

    public void startGame(){
            gamePanel.startGame();
            this.setVisible(true);
    }

    public static void main(String arg[]){
    	
            GameFrame gameFrame = new GameFrame();
            gameFrame.startGame();

    }
        
    
}
