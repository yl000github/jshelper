package enabler.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Req {
	public String sql;
	public Map<String,Object> param=new HashMap<>();
	public List<Map<String,Object>> params=new ArrayList<>();
	public Req(){
	}
	public List<Object> getPrepareParam() {
		String sql=this.sql;
		Map<String,Object> map = this.param;
		List<Object> rs=new ArrayList<>();
		Pattern pattern=Pattern.compile("@[a-zA-Z0-9]*");
		Matcher matcher=pattern.matcher(sql);
        while (matcher.find()) {
        	//顺序取出key
            String key = matcher.group();
            if(key!=null){
            	key=key.substring(1);//去掉@
            }
            Object value=map.get(key);
            rs.add(value);
        }
		return rs;
	}
	public List<List<Object>> getPrepareParams() {
		List<List<Object>> list=new ArrayList<>();
		for (Map<String, Object> map : params) {
			this.param=map;
			List<Object> rs = this.getPrepareParam();
			list.add(rs);
		}
		return list;
	}
	public String getPrepareSql() {
		return sql.replaceAll("@[a-zA-Z0-9]*", "?");
	}

}
