package dataloader;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {
	
    private String name;
    private boolean isRepeated;
    private int currentImageIndex;
    private long beginTime;
    private boolean drawRectFrame;

    private ArrayList<BufferedImage> images;
    private ArrayList<Boolean> ignoreImages;
    private ArrayList<Double> delayImages;
    
    public Animation(){
    	delayImages = new ArrayList<Double>();
        beginTime = 0;
        currentImageIndex = 0;
        ignoreImages = new ArrayList<Boolean>();
        images = new ArrayList<BufferedImage>();        
        drawRectFrame = false;        
        isRepeated = true;
    }
    
    public Animation(Animation animation){
        
        beginTime = animation.beginTime;
        currentImageIndex = animation.currentImageIndex;
        drawRectFrame = animation.drawRectFrame;
        isRepeated = animation.isRepeated;        
        delayImages = new ArrayList<Double>();
        
        for(Double d : animation.delayImages){
        	delayImages.add(d);
        }
        
        ignoreImages = new ArrayList<Boolean>();
        for(boolean b : animation.ignoreImages){
        	ignoreImages.add(b);
        }
        
        images = new ArrayList<BufferedImage>();
        for(int i = 0; i < animation.images.size(); i++){
        	BufferedImage f = animation.images.get(i);
        	images.add(f);
        }
    }
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRepeated() {
		return isRepeated;
	}

	public void setRepeated(boolean isRepeated) {
		this.isRepeated = isRepeated;
	}

	public ArrayList<BufferedImage> getImages() {
		return images;
	}

	public void setImages(ArrayList<BufferedImage> images) {
		this.images = images;
	}

	public int getcurrentImageIndex() {
		return currentImageIndex;
	}

	public void setCurrentImage(int currentFrame) {
		if(currentFrame >=0 && currentFrame < images.size())
			this.currentImageIndex = currentFrame;
		else
			currentFrame = 0;
	}

	public long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}

	public boolean isDrawRectFrame() {
		return drawRectFrame;
	}

	public void setDrawRectFrame(boolean drawRectFrame) {
		this.drawRectFrame = drawRectFrame;
	}
    
	public boolean isIgnoreImage(int id) {
		return ignoreImages.get(id);
	}
	
	public void setIgnoreImage(int id) {
		if(id >= 0 && id < ignoreImages.size())
			ignoreImages.set(id, true);
	}
   
	public void unIgnoreImages(int id) {
		if(id >=0 && id < ignoreImages.size())
			ignoreImages.set(id, false);
	}
    
	public void reset() {
		currentImageIndex = 0;
		beginTime = 0;
		
		for(int i = 0; i < ignoreImages.size(); i++) {
			ignoreImages.set(i, false);
		}
	}
	
	public void add(BufferedImage image, double timeToNextFrame) {		
	     	ignoreImages.add(false);
	     	images.add(image);
	     	delayImages.add(Double.valueOf(timeToNextFrame));
	}
	
	public BufferedImage getCurrentImage() {
		return images.get(currentImageIndex);
	}
	
	public void nextImage() {		
		if(currentImageIndex >= images.size() - 1) {
			if(isRepeated == true)
				currentImageIndex = 0;
		}
		else
			currentImageIndex++;
		
		if(ignoreImages.get(currentImageIndex))
			nextImage();		
	}
	
	public void Update(long currentTime) {		
		if(beginTime == 0)
			beginTime = currentTime;
		else {
			
			if((currentTime - beginTime) > delayImages.get(currentImageIndex)) {
				nextImage();
				beginTime = currentTime;
			}		
		}		
	}
	
	public boolean isLastImage() {
		if(currentImageIndex == images.size() - 1)
			return true;
		else return false;
	}
		
	public void draw(int x, int y, int w, int h, Graphics2D g2) {
		
		BufferedImage image = getCurrentImage();		
		g2.drawImage(image, x - image.getWidth()/2, y - image.getHeight()/2, w, h, null);		
		if(drawRectFrame)
			g2.drawRect(x - image.getWidth()/2, y - image.getHeight()/2,
						image.getWidth(), image.getHeight());
	}
	
}