package enabler.redis;

import java.util.Map;

import enabler.txt.ConfigReader;
import redis.clients.jedis.Jedis;
import utils.JSONUtil;

public class RedisEnabler {
	Jedis jedis;
	public RedisEnabler() {
		String url=ConfigReader.getValue("jedis.url");
		String port=ConfigReader.getValue("jedis.port");
		jedis=new Jedis(url, Integer.parseInt(port));
		System.out.println("connect to redis successfully");
	}
	public String hgetall(String tableName){
		Map<String, String>  map=jedis.hgetAll(tableName);
		return JSONUtil.stringify(map);
	}
	public String hget(String tableName,String key){
		String rs=jedis.hget(tableName, key);
		return rs;
	}
	public void hset(String tableName,String key,String value){
		jedis.hset(tableName, key, value);
	}
	public static void main(String[] args) {
		ConfigReader.setMap("D:/git/jscontroller/");
		RedisEnabler jedis=new RedisEnabler();
		String tableName="hhhh";
		jedis.hset(tableName, "first", "11");
		jedis.hset(tableName, "second", "22");
		System.out.println(jedis.hgetall(tableName));
		System.out.println(jedis.hget(tableName,"first"));
	}
	
}
