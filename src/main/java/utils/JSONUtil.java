package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class JSONUtil {
	private static Gson gson;
	private static Gson getGson(){
		if(gson==null){
			gson= new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		}
		return gson;
	}
	public static JsonElement parse(String content){
		Gson gson=getGson();
		JsonElement j=gson.fromJson(content, JsonElement.class);
		return j;
	}
	public static String stringify(Object json){
		Gson gson=getGson();
		return gson.toJson(json);
	}
	public static void main(String[] args) {
		System.out.println("ok");
		parse("123");
	}
	public static boolean isJSON(String json) {
		if (StringUtil.isBlank(json)) {
			return false;
		}
		try {
			new JsonParser().parse(json);
			return true;
		} catch (JsonParseException e) {
			return false;
		}
	}
}
