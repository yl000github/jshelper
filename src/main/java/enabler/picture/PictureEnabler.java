package enabler.picture;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



import utils.ImageMergeUtils;
import utils.ImageUtils;

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
	/**
	 * 图片合并
	 * @param imgs
	 * @param type
	 * @param dst_pic
	 * @param widthNum
	 * @return
	 */
	public boolean merge(String[] imgs, String type, String dst_pic,int widthNum) {
		return ImageMergeUtils.merge(imgs, type, dst_pic, widthNum);
	}
	public void scale(String srcImageFile, String result, int height, int width){
		ImageUtils.scale2(srcImageFile, result, height, width, true);
	}
    public void pressText(String pressText,String srcImageFile, String destImageFile, String fontName){
    	int fontSize=10;
    	ImageUtils.pressText(pressText, srcImageFile, destImageFile, fontName, Font.BOLD,Color.white,fontSize, 0, 0, 0.5f);
    }
	public static void main(String[] args) throws AWTException, IOException {
		PictureEnabler enabler=new PictureEnabler();
		enabler.screenshot("e:/screenshot.png");
	}
}
