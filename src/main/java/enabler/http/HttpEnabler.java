package enabler.http;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.StringUtil;

public class HttpEnabler {
	OkHttpClient client = new OkHttpClient();
	public static final MediaType JSON
	= MediaType.parse("application/json; charset=utf-8");
	public String get(String url) throws IOException{
		  Request request = new Request.Builder()
		      .url(url)
		      .build();
		  Response response = client.newCall(request).execute();
		  return response.body().string();
	}
	public String post(String url, String json) throws IOException {
		  RequestBody body = RequestBody.create(JSON, json);
		  Request request = new Request.Builder()
		      .url(url)
		      .post(body)
		      .build();
		  Response response = client.newCall(request).execute();
		  return response.body().string();
	}
	public static void main(String[] args) throws IOException {
		HttpEnabler enabler=new HttpEnabler();
//		System.out.println(enabler.get("http://www.baidu.com"));
//		String json="{\"modelCName\":\"马自达CA7200ATE5轿车\",\"purchasePriceNotTax\":\"597184\"}";
//		System.out.println(enabler.post("http://192.168.1.17:9011/docking/dockingManager/localVehicleQuery", json));
		String url="https://polldaddy.com/n/d3e1ea5946929563171050bb86532171/9610345?1481873";
		for (int i = 0; i < 20; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						for (int j = 0; j < 1000; j++) {
							String tUrl=url+StringUtil.getFixLenthString(6);
							System.out.println(enabler.get(tUrl));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}
