package utils;

import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.PrintStream;  
  
/** 
 * @author 孙潇 
 * 文件夹拷贝(文件内含有文件和文件夹) 
 * 
 */  
public class CopyUtil {  
      
    public static void main(String[] args) throws Exception {  
//        copy("e:/11/", "e:/22/");  
        copy("e:/11/record2.txt", "e:/22/record3.txt");  
//        System.out.println("文件拷贝完成!");  
//    	String str="e:/22/record3.txt";
//    	System.out.println(isDir(str));
//    	System.out.println(isFile(str));
    }  
    private static boolean isDir(String src){
    	File f=new File(src);
    	return f.isDirectory();
    }
    private static boolean isFile(String src){
    	File f=new File(src);
    	return f.isFile();
    }
    public static void copy(String src, String des) throws Exception {
    	
    	if(isDir(src)){
    		copy1(src,des);
    	}else if(isFile(src)){
    		fileCopy(src, des);
    	}else{
    		throw new Exception("复制路径错误！");
    	}
    }
    private static void copy1(String src, String des) {  
        File file1=new File(src);  
        File[] fs=file1.listFiles();  
        File file2=new File(des);  
        if(!file2.exists()){  
            file2.mkdirs();  
        }  
        for (File f : fs) {  
            if(f.isFile()){  
                fileCopy(f.getPath(),des+"\\"+f.getName()); //调用文件拷贝的方法  
            }else if(f.isDirectory()){  
                copy1(f.getPath(),des+"\\"+f.getName());  
            }  
        }  
          
    }  
  
    /** 
     * 文件拷贝的方法 
     */  
    private static void fileCopy(String src, String des) {  
      
        BufferedReader br=null;  
        PrintStream ps=null;  
          
        try {  
            br=new BufferedReader(new InputStreamReader(new FileInputStream(src)));  
            ps=new PrintStream(new FileOutputStream(des));  
            String s=null;  
            while((s=br.readLine())!=null){  
                ps.println(s);  
                ps.flush();  
            }  
              
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }finally{  
              
                try {  
                    if(br!=null)  br.close();  
                    if(ps!=null)  ps.close();  
                } catch (IOException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
                  
        }  
          
          
    }  
  
}  