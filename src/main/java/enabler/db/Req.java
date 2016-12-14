package enabler.db;

import java.util.HashMap;
import java.util.Map;

public class Req {
	public String sql;
	public Map<String,String> param=new HashMap<>();
	public Req(){
	}
}
