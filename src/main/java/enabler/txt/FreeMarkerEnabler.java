package enabler.txt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import common.Constants;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import utils.JSONUtil;

public class FreeMarkerEnabler {
	Configuration cfg ; 
	Template template = null;
	public FreeMarkerEnabler() {
		cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	}
	/**
	 * 所有参数必须赋值
	 * @param src  模板文件
	 * @param dest  输出的目标文件
	 * @param json  参数（json串）
	 * @author yanglin
	 */
	public void printFile(String src,String dest,String json)throws Exception{
//		System.out.println(json);
		
		Map<String, Object> parameters=(Map<String, Object>) JSONUtil.parse(json,Map.class);
//		System.out.println(parameters);
		printFileMap(src, dest, parameters);
	}
	private String trans(String src){
		if(src.contains(":")){
			//绝对路径
			return src;
		}else{
			return Constants.jsDir+src;
		}
	}
	public void printFileMap(String src,String dest,Map<String, Object> parameters)throws Exception{
		src=trans(src);
		dest=trans(dest);
		File f=new File(src);
		String parentDir=f.getParent();
		String fileName=f.getName();
//		System.out.println(parentDir);
//		System.out.println(fileName);
		cfg.setDirectoryForTemplateLoading(new File(parentDir)); 
		template=cfg.getTemplate(fileName);
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(dest), "UTF-8");  
		template.process(parameters, writer);  
		writer.flush(); 
		writer.close();
	}
	public static void main(String[] args) throws Exception {
		String ftlPath="e:/freemarker/demo.ftl";
		String outputPath="e:/freemarker/output.txt";
		Map<String,Object> m=new HashMap<>();
		m.put("A", "hahahaha");
		List<String> l=new ArrayList<String>();
			
				l.add("1");
				l.add("2");
				l.add("3");
			
		
//		List<String> l=new ArrayList<String>(){
//			{
//				add("1");
//				add("2");
//				add("3");
//			}
//		};
//		m.put("ll", l);
		System.out.println(m);
		String json=JSONUtil.stringify(m);
		FreeMarkerEnabler en=new FreeMarkerEnabler();
		en.printFile(ftlPath, outputPath, json);
//		en.printFileMap(ftlPath, outputPath, m);
		
		String jj=JSONUtil.stringify(l);
		System.out.println(jj);
	}
}
