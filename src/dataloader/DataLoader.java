package dataloader;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import javax.imageio.ImageIO;

public class DataLoader {
    
    private static DataLoader instance = null;
    
    private String framefile = "data/frame.txt";
    private String animationfile = "data/animation.txt";
    private String physmapfile = "data/phys_map.txt";
    private String backgroundmapfile = "data/background_map.txt";
    private String physmapfile2 = "data/phys_map2.txt";
    private String backgroundmapfile2 = "data/background_map2.txt";
    
    private Hashtable<String, FrameImage> frameImages; 
    private Hashtable<String, Animation> animations;
    
    private int[][] phys_map;
    private int[][] background_map;
    
    private int[][] phys_map2;
    private int[][] background_map2;
    
    private DataLoader() {}

    public static DataLoader getInstance(){
        if(instance == null)
            instance = new DataLoader();
        return instance;
    }
    
    public BufferedImage getImage(String path) {
		
    	BufferedImage image = null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {}
    	return image;
	}	
    
    public Animation getAnimation(String name) {    	
    	Animation animation = new Animation(instance.animations.get(name));
    	return animation;    	
    }
    
    public FrameImage getFrameImage(String name){
        FrameImage frameImage = new FrameImage(instance.frameImages.get(name));
        return frameImage;
    }
    
    public int[][] getPhysicalMap(){
        return instance.phys_map;
    }   
    
    public int[][] getBackgroundMap(){
        return instance.background_map;
    }
    
    public int[][] getPhysicalMap2(){
        return instance.phys_map2;
    }   
    
    public int[][] getBackgroundMap2(){
        return instance.background_map2;
    }
    
    public void LoadData()throws IOException{        
        LoadFrame();
        LoadAnimation();
        LoadPhysMap();
        LoadBackgroundMap();        
    }
    
    public void LoadAnimation() throws IOException{		
		animations = new Hashtable<String, Animation>();
		FileReader fr = new FileReader(animationfile);
		BufferedReader br = new BufferedReader(fr);		
		String line = null;
		
		if(br.readLine() == null) {			
			System.out.println("No data");
			extracted();			
		}
		else {			
			fr = new FileReader(animationfile);
			br = new BufferedReader(fr);
			
			while((line = br.readLine()).equals(""));
			int n = Integer.parseInt(line);
			
			for(int i = 0; i < n; i++)
			{
				Animation animation = new Animation();
				
				while((line = br.readLine()).equals(""));
				animation.setName(line);
				
				while((line = br.readLine()).equals(""));
				String [] str = line.split(" ");
				
				for(int j = 0; j < str.length; j+=2) {
					
					animation.add(getImage(str[j]), Double.parseDouble(str[j + 1]));
					instance.animations.put(animation.getName(), animation);
				}
				
			}
		}
		fr.close();		
	}

	private void extracted() throws IOException {
		throw new IOException();
	}    
    
    public void LoadBackgroundMap() throws IOException{
        
        FileReader fr = new FileReader(backgroundmapfile);
        BufferedReader br = new BufferedReader(fr);        
        String line = null;
        
        line = br.readLine();
        int numberOfRows = Integer.parseInt(line);
        line = br.readLine();
        int numberOfColumns = Integer.parseInt(line);            
        
        instance.background_map = new int[numberOfRows][numberOfColumns];
        
        for(int i = 0;i < numberOfRows;i++){
            line = br.readLine();
            String [] str = line.split(" |  ");
            for(int j = 0;j<numberOfColumns;j++)
            	instance.background_map[i][j] = Integer.parseInt(str[j]);
        }    
        
        br.close();        
        FileReader fr2 = new FileReader(backgroundmapfile2);
        BufferedReader br2 = new BufferedReader(fr2);        
        String line2 = null;
        
        line2 = br2.readLine();
        int numberOfRows2 = Integer.parseInt(line2);
        line2 = br2.readLine();
        int numberOfColumns2 = Integer.parseInt(line2);
            
        
        instance.background_map2 = new int[numberOfRows2][numberOfColumns2];
        
        for(int i = 0;i < numberOfRows2;i++){
            line2 = br2.readLine();
            String [] str = line2.split(" |  ");
            for(int j = 0;j<numberOfColumns2;j++)
            	instance.background_map2[i][j] = Integer.parseInt(str[j]);
        }            
        br2.close();        
    }
    
    public void LoadPhysMap() throws IOException{
        
        FileReader fr = new FileReader(physmapfile);
        BufferedReader br = new BufferedReader(fr);        
        String line = null;
        
        line = br.readLine();
        int numberOfRows = Integer.parseInt(line);
        line = br.readLine();
        int numberOfColumns = Integer.parseInt(line);            
        
        instance.phys_map = new int[numberOfRows][numberOfColumns];
        
        for(int i = 0;i < numberOfRows;i++){
            line = br.readLine();
            String [] str = line.split(" ");
            for(int j = 0;j<numberOfColumns;j++)
            	instance.phys_map[i][j] = Integer.parseInt(str[j]);
        }        
        br.close();        
        
        FileReader fr2 = new FileReader(physmapfile2);
        BufferedReader br2 = new BufferedReader(fr2);        
        String line2 = null;
        
        line2 = br2.readLine();
        int numberOfRows2 = Integer.parseInt(line2);
        line2 = br2.readLine();
        int numberOfColumns2 = Integer.parseInt(line2);            
        
        instance.phys_map2 = new int[numberOfRows2][numberOfColumns2];
        
        for(int i = 0;i < numberOfRows2;i++){
            line2 = br2.readLine();
            String [] str = line2.split(" ");
            for(int j = 0;j<numberOfColumns2;j++)
            	instance.phys_map2[i][j] = Integer.parseInt(str[j]);
        }               
        br2.close();        
    }	
    
    public void LoadFrame() throws IOException{
        
        frameImages = new Hashtable<String, FrameImage>();
        
        FileReader fr = new FileReader(framefile);
        BufferedReader br = new BufferedReader(fr);        
        String line = null;
        
        if(br.readLine()==null) {
            System.out.println("No data");
            extracted();
        }
        else {            
            fr = new FileReader(framefile);
            br = new BufferedReader(fr);
            
            while((line = br.readLine()).equals(""));
            
            int n = Integer.parseInt(line);
            String path = null;
            BufferedImage imageData = null;
            int i2 = 0;
            for(int i = 0;i < n; i ++){
                
                FrameImage frame = new FrameImage();
                while((line = br.readLine()).equals(""));
                frame.setName(line);
                
                while((line = br.readLine()).equals(""));
                String[] str = line.split(" ");
                
                boolean refreshImage = (path == null || !path.equals(str[1]));
                path = str[1];
                
                while((line = br.readLine()).equals(""));
                str = line.split(" ");
                int x = Integer.parseInt(str[1]);
                
                while((line = br.readLine()).equals(""));
                str = line.split(" ");
                int y = Integer.parseInt(str[1]);
                
                while((line = br.readLine()).equals(""));
                str = line.split(" ");
                int w = Integer.parseInt(str[1]);
                
                while((line = br.readLine()).equals(""));
                str = line.split(" ");
                int h = Integer.parseInt(str[1]);
                
                if(refreshImage) {
                    refreshImage = false;
                    imageData = ImageIO.read(new File(path));
                }
                if(imageData != null) {
                    BufferedImage image = imageData.getSubimage(x, y, w, h);
                    frame.setImage(image);
                }                
                instance.frameImages.put(frame.getName(), frame);
            }            
        }        
        br.close();        
    }  
    
}
