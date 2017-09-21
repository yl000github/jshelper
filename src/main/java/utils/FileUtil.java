package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	public static void createFile(String filepath,boolean overwrite) throws IOException {
		File f = new File(filepath);
		if (!f.exists()) {
			String parent = f.getParent();
			File p = new File(parent);
			if (!p.exists())
				p.mkdirs();
			f.createNewFile();
		}else{
			if(overwrite){
				write(filepath, "");
			}
		}
	}
	public static void mkdir(String filepath,boolean overwrite) throws IOException {
		File dir = new File(filepath);
		if (dir.exists()) {// 判断目录是否存在
			if(overwrite){
				delDirectory(filepath);
//				mkdir(filepath, false);
			}else{
				System.out.println("创建目录失败，目标目录已存在！");
			}
		}
		if (dir.mkdirs()) {// 创建目标目录
			System.out.println("创建目录成功！" + filepath);
		} else {
			System.out.println("创建目录失败！");
		}
	}
	public static boolean delFile(String filePath) {// 删除单个文件
		boolean flag = false;
		File file = new File(filePath);
		if (file.isFile() && file.exists()) {// 路径为文件且不为空则进行删除
			file.delete();// 文件删除
			flag = true;
		}
		return flag;
	}

	public static boolean delDirectory(String dirPath) {// 删除目录（文件夹）以及目录下的文件
		boolean flag = false;
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!dirPath.endsWith(File.separator)) {
			dirPath = dirPath + File.separator;
		}
		File dirFile = new File(dirPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		flag = true;
		File[] files = dirFile.listFiles();// 获得传入路径下的所有文件
		for (int i = 0; i < files.length; i++) {// 循环遍历删除文件夹下的所有文件(包括子目录)
			if (files[i].isFile()) {// 删除子文件
				flag = delFile(files[i].getAbsolutePath());
				System.out.println(files[i].getAbsolutePath() + " 删除成功");
				if (!flag)
					break;// 如果删除失败，则跳出
			} else {// 运用递归，删除子目录
				flag = delDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;// 如果删除失败，则跳出
			}
		}
		if (!flag)
			return false;
		if (dirFile.delete()) {// 删除当前目录
			return true;
		} else {
			return false;
		}
	}
	public static void write(String filepath, String content) {
		try {
			createFile(filepath, false);
			BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));
			bw.write(content);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void write(String filepath, InputStream is) throws IOException {
		createFile(filepath, true);
		FileOutputStream fos = new FileOutputStream(filepath);
		byte[] b = new byte[1024];
		int len;
		while((len=is.read(b)) != -1){
			fos.write(b,0,len);
		}
		fos.flush();
		fos.close();
	}

	public static void writeAdd(String filepath, String content) {
		try {
			createFile(filepath,false);
			FileWriter fw = new FileWriter(filepath, true);
			fw.write(content);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String read(String filepath) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\r\n");
			}
			// System.out.println(sb.toString());
			br.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public static List<String> read2Lines(String filepath) {
		try {
			List<String> list=new ArrayList<>();
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			String line = null;
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
			// System.out.println(sb.toString());
			br.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 返回一个文件夹下所有对应类型文件的总行数
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	public static int getFilesLines(String filePath,String type) throws IOException {
		File root = new File(filePath);
		if(!root.isDirectory()) return -1;
		File[] files = root.listFiles();
		if(files.length==0) return 0;
		int s=0;
		for (File file : files) {
			if (file.isDirectory()) {
				s+=getFilesLines(file.getAbsolutePath(),type);
//				System.out.println("显示" + filePath + "下所有子目录及其文件" + file.getAbsolutePath());
			} else {
//				System.out.println("显示" + filePath + "下所有子目录" + file.getAbsolutePath());
				if(file.getName().endsWith("."+type))
					s+=readLines(file);
			}
		}
		return s;
	}
	public static int readLines(File f) throws IOException{
		InputStream input = new FileInputStream(f);BufferedReader b = new BufferedReader(new InputStreamReader(input));
		int count=0;
		while(b.readLine()!=null) count++;
		b.close();
		input.close();
		return count;
	}
	/**
	 * 将文件中某个字段的值做一定的运算
	 * @param args
	 * @throws IOException
	 */
	public static void reappearChange(){
		String path="d:/logs/move1.txt";
		int t=1600;
		try {
			StringBuffer sb=new StringBuffer();
			BufferedReader br=new BufferedReader(new FileReader(path));
			String line;
			while((line=br.readLine())!=null){
				String num=line.substring(line.lastIndexOf("=")+1, line.length());
				String ano=String.valueOf(Integer.parseInt(num)-t);
				line=line.replace(num, ano);
				sb.append(line);
				sb.append("\r\n");
			}
			br.close();
			write(path,sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) throws IOException {
//		reappearChange();
//		String c = "hello yang";
//		String filepath = "D:\\git\\myself\\src\\main\\java";
//		System.out.println(getFilesLines(filepath,"java"));
//		String filepath = "D:\\git\\myself\\src\\main\\java";
//		System.out.println(getFilesLines(filepath,"java"));
//		System.out.println(getFilesLines("D:\\HbuilderWorkspace\\omp\\server","js"));
//		System.out.println(getFilesLines("D:\\HbuilderWorkspace\\omp\\client\\app","js"));
		// write(filepath, c);
		// System.out.println(read(filepath));
//		createFile("e:/111.txt", true);
		
		
		mkdir("f:/hhhh/", true);
	}
}
