package enabler.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import common.Constants;
import utils.FileUtil;
import utils.JSONUtil;

public class FileEnabler {
	public static long getFileSize(String filepath){
		File f=new File(filepath);
		if(f.exists()&&f.isFile()){
			return f.length();
		}else{
			return 0;
		}
	}
	public static boolean exists(String filepath){
		File f=new File(filepath);
		return f.exists();
	}
	public static String  getFileName(String filepath){
		File f=new File(filepath);
		if(f.exists()&&f.isFile()){
			return f.getName();
		}else{
			return "";
		}
	}
	private static String trans(String src){
		if(src.contains(":")){
			//绝对路径
			return src;
		}else{
			return Constants.jsDir+src;
		}
	}
	public static void mkdir(String filepath,boolean overwrite) throws IOException {
		FileUtil.mkdir(filepath, overwrite);
	}
	public static void  write(String filepath,String content,boolean isAdd){
		filepath=trans(filepath);
		if(isAdd){
			FileUtil.writeAdd(filepath, content);
		}else{
			FileUtil.write(filepath, content);
		}
	}
	public static String  read(String filepath){
		filepath=trans(filepath);
		if(!checkFile(filepath)) return "";
		String rs=FileUtil.read(filepath);
		return rs;
	}
	public static void create(String src) throws IOException{
		File destFile=new File(src);
		if(!destFile.exists()) {
			if(destFile.isFile()){
				destFile.createNewFile();
			}else if(destFile.isDirectory()){
				destFile.mkdir();
			}
		}
	}
	public static void  copy(String src,String dest,boolean overwrite) throws IOException{
		if(!overwrite){
			if(checkFile(dest)) return ;
		}
		File f=new File(src);
		create(dest);
		if(f.isDirectory()){
			File[] list = f.listFiles();
			for (File file : list) {
				copy(file.getPath(), dest+"\\"+file.getName(), overwrite);
			}
		}else{
//			String content=FileEnabler.read(src);
//			FileEnabler.write(dest, content, false);
			fileCopy(src, dest);
		}
	}
	 /** 
     * 文件拷贝的方法 
     */  
    private static void fileCopy(String src, String des) {  
      
        BufferedReader br=null;  
        PrintStream ps=null;  
         try {
			FileUtil.createFile(des, true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        try {  
            br=new BufferedReader(new InputStreamReader(new FileInputStream(src)));  
            ps=new PrintStream(new FileOutputStream(des));  
            String s=null;  
            while((s=br.readLine())!=null){  
                ps.println(s);  
                ps.flush();  
            }  
              
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
                try {  
                    if(br!=null)  br.close();  
                    if(ps!=null)  ps.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
        }  
    }  
	public static boolean  checkFile(String filepath){
		File f=new File(filepath);
		return f.exists()&&f.isFile();
	}
	public static String  listDir(String filepath,boolean recrusive,boolean isFullPath){
		File root = new File(filepath);
		if(!root.isDirectory()) return "[]";
		File[] files = root.listFiles();
		if(files.length==0) return "[]";
		List<String> list=new ArrayList<>();
		for (File file : files) {
			if (file.isDirectory()) {
				//递归暂不考虑
				if(recrusive){
					String rs = listDir(file.getAbsolutePath(), recrusive, isFullPath);
					List<String> rsList=(List<String>) JSONUtil.parse(rs, ArrayList.class);
					for (String string : rsList) {
						list.add(string);
					}
				}else{
					if(isFullPath){
						list.add(file.getAbsolutePath());
					}else{
						list.add(file.getName());
					}
				}
			} else {
				String fileName=file.getName();
				if(isFullPath){
					fileName=filepath+fileName;
				}
				list.add(fileName);
			}
		}
		return JSONUtil.stringify(list);
	}
	public static void main(String[] args) throws IOException {
		FileEnabler enabler=new FileEnabler(); 
//		System.out.println(enabler.getFileSize("e:/omp.txt"));
//		System.out.println(enabler.getFileName("e:/omp.txt"));
//		System.out.println(listDir("e:/download/", false, false));
		enabler.copy("e:/record2.txt", "e:/omp-copy1.txt",true);
//		enabler.copy("e:/11/", "e:/22/",true);
//		enabler.copy("e:/11/record2.txt", "e:/22/record3.txt",true); 
		
//		System.out.println(listDir("D:/environment/notepad++ 2017-19-19", false, false));
//		System.out.println(listDir("D:/environment/notepad++ 2017-19-19", false, true));
//		System.out.println(listDir("D:/environment/notepad++ 2017-19-19", true, false));
//		System.out.println(listDir("D:/environment/notepad++ 2017-19-19", true, true));
	}
}
