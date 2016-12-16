package enabler.office;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import utils.JSONUtil;

public class ExcelData {
	Map<String,Map<String,String>> map;
	@SuppressWarnings("unchecked")
	public ExcelData(String json) {
		map=(Map<String, Map<String, String>>) JSONUtil.parse(json, Map.class);
	}
	public Map<String,String> getRow(int index){
		return map.get(String.valueOf(index));
	}
	public int getRowCount(){
		return map.size();
	}
	public Set<String>  getRowName(){
		return map.keySet();
	}
	public Set<String>  getColName(int row){
		return getRow(row).keySet();
	}
	public String getRowCol(int row,int col){
		return getRow(row).get(String.valueOf(col));
	}
	public static void main(String[] args) {
		String json="{\"1\":{\"2\":\"ff\"}}";
		ExcelData data=new ExcelData(json);
		System.out.println(data.getRowCol(1,2));
	}
}
