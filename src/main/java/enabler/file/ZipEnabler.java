package enabler.file;

import java.util.ArrayList;
import java.util.List;


import utils.JSONUtil;
import utils.ZipUtil;

public class ZipEnabler {
    @SuppressWarnings("unchecked")
	public static void zipFile(String json){
    	ZipReq req=(ZipReq) JSONUtil.parse(json, ZipReq.class);
    	ZipUtil.zipFile(req.getDest(), req.getFiles());
    }
    public static void unzipFile(String path, String dest){
    	ZipUtil.upzipFile(path, dest);
    }
    public static void main(String[] args) { 
    	List<String> list=new ArrayList<>();
    	list.add("d:/environment/PolicyServiceImpl.java");
    	list.add("d:/environment/QQ截图20170905151931.png");
    	String str=JSONUtil.stringify(list);
    	ZipReq req=new ZipReq();
    	req.setDest("d:/environment/kk.zip");
    	req.setFiles(list);
    	zipFile(JSONUtil.stringify(req));
    	unzipFile("d:/environment/kk.zip", "d:/environment/kk");
	}
}
