package enabler.picture;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PictureEnabler {
	Robot robot=null;
	private Dimension dim;
    public int width;
    public int height;
	public PictureEnabler() {
		dim=Toolkit.getDefaultToolkit().getScreenSize();
        width=(int) dim.getWidth();
        height=(int) dim.getHeight();
	}
	private void _screenShot(String path,int sX,int sY,int width,int height) throws IOException, AWTException{
		if(robot==null){
			robot=new Robot();
		}
		BufferedImage image=robot.createScreenCapture(new Rectangle(sX, sY, width, height));
		ImageIO.write(image,"png",new File(path));
	}
	public void screenshot(String path) throws AWTException, IOException{
		_screenShot(path, 0, 0, width, height);
	}
	public String recongise(String path){
		return path;
	}
	public static void main(String[] args) throws AWTException, IOException {
		PictureEnabler enabler=new PictureEnabler();
		enabler.screenshot("e:/screenshot.png");
	}
}
