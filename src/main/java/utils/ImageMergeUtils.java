package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
/**
 * 
 *@类功能说明:      java拼接多张图片，生成的格式是jpg、bmp、gif等，如果其他格式则报错或者photoshop或者画图工具打不开
 *@修改人员名:     yang
 *@修改日期：    2016-3-10 上午10:35:04
 *@创建时间：    2016-3-10 上午10:35:04
 * -------------------------------------------------------------------------------------------
 *  修改次数         修改人员    修改内容                       修改原因                     
 *                                                                                                                                   
 * @备注：只能拼接同类型的图片（不能连续拼接）、宽度需要一致（高度可以不限制）
 * @版本： V1.0
 */
public class ImageMergeUtils  {

         //测试类
public static void main(String[] args) {
//输入图片地址
	String pre="f:/yiji/output/";
//String[] imgs={pre+"4.png",pre+"5.png"};
	String[] imgs=new String[25];
	for (int i = 0; i < 25; i++) {
		imgs[i]=pre+i+".png";
	}
//	String[] imgs=new String[24];
//	for (int i = 0; i < imgs.length; i++) {
//		imgs[i]=pre+(i+1)+".png";
//	}
//调用方法生成图片
//JoinTwoImage.lineMerge(imgs,"png", "F:/test2.png");
ImageMergeUtils.merge(imgs,"png", "F:/test3.png",1);
}
   /**
    * 所有图片尺寸必须一致
    * @param imgs
    * @param type
    * @param dst_pic
    * @param widthNum 行的图片数
    * @return
    */
   public static boolean merge(String[] imgs, String type, String dst_pic,int widthNum) {  
	   //获取需要拼接的图片长度
	   int len = imgs.length;  
	   //判断长度是否大于0
	   if (len < 1) {  
		   return false;  
	   }  
	   File[] src = new File[len];  
	   BufferedImage[] images = new BufferedImage[len];  
	   int[][] ImageArrays = new int[len][];  
	   int comWidth=-1;
	   int comHeight=-1;
	   for (int i = 0; i < len; i++) {  
		   try {  
			   src[i] = new File(imgs[i]);  
			   images[i] = ImageIO.read(src[i]);  
		   } catch (Exception e) {  
			   e.printStackTrace();  
			   return false;  
		   }  
		   int width = images[i].getWidth();  
		   int height = images[i].getHeight();  
		   if(comWidth==-1){
			   comWidth=width;
		   }else if(comWidth!=width){
			   System.out.println("图片宽度不相等");
			   return false;
		   }
		   if(comHeight==-1){
			   comHeight=height;
		   }else if(comHeight!=height){
			   System.out.println("图片高度不相等");
			   return false;
		   }
		   // 从图片中读取RGB 像素
		   ImageArrays[i] = new int[width * height];
		   ImageArrays[i] = images[i].getRGB(0, 0, width, height,  ImageArrays[i], 0, width);  
	   }  
	   
	   int dst_height = (imgs.length/widthNum)*comHeight;  
	   if(imgs.length%widthNum!=0){
		   dst_height+=comHeight;
	   }
	   int dst_width = images[0].getWidth()*widthNum;  
	   //合成图片像素
//	   for (int i = 0; i < images.length; i++) {  
////		   dst_width = dst_width > images[i].getWidth() ? dst_width     : images[i].getWidth();  
//		   dst_height += images[i].getHeight();  
//	   }  
	   //合成后的图片
	   System.out.println("宽度:"+dst_width);  
	   System.out.println("高度:"+dst_height);  
	   if (dst_height < 1) {  
		   System.out.println("dst_height < 1");  
		   return false;  
	   }  
	   // 生成新图片   
	   try {  
		   // dst_width = images[0].getWidth();   
		   BufferedImage ImageNew = new BufferedImage(dst_width, dst_height,  
				   BufferedImage.TYPE_INT_RGB);  
		   int width_i = 0;  
		   int height_i = 0;  
		   for (int i = 0; i < images.length; i++) {  
//			   height_i += images[i].getHeight();  
			   height_i=(i/widthNum)*comHeight;
			   width_i=i%widthNum*comWidth;
//			   LogUtil.p("height_i", height_i);
//			   LogUtil.p("width_i", width_i);
			   ImageNew.setRGB(width_i, height_i, comWidth, comHeight,  
					   ImageArrays[i], 0, comWidth);  
		   }  
		   
		   File outFile = new File(dst_pic);  
		   ImageIO.write(ImageNew, type, outFile);// 写图片 ，输出到硬盘 
	   } catch (Exception e) {  
		   e.printStackTrace();  
		   return false;  
	   }  
	   return true;  
   }  
}
