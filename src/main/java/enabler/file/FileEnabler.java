package enabler.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	public static String  getFileName(String filepath){
		File f=new File(filepath);
		if(f.exists()&&f.isFile()){
			return f.getName();
		}else{
			return "";
		}
	}
	public static void  write(String filepath,String content,boolean isAdd){
		if(isAdd){
			FileUtil.writeAdd(filepath, content);
		}else{
			FileUtil.write(filepath, content);
		}
	}
	public static String  read(String filepath){
		if(!checkFile(filepath)) return "";
		String rs=FileUtil.read(filepath);
		return rs;
	}
	public static void  copy(String src,String dest){
		if(checkFile(dest)) return ;
		String content=FileEnabler.read(src);
		FileEnabler.write(dest, content, false);
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
	public static void main(String[] args) {
		FileEnabler enabler=new FileEnabler();
//		System.out.println(enabler.getFileSize("e:/omp.txt"));
//		System.out.println(enabler.getFileName("e:/omp.txt"));
//		System.out.println(listDir("e:/download/", false, false));
		enabler.copy("e:/omp.txt", "e:/omp-copy.txt");
	}
}
