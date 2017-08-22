package enabler.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import enabler.file.FileEnabler;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.FileUtil;
import utils.JSONUtil;
import utils.StringUtil;

public class HttpEnabler {
	OkHttpClient client = new OkHttpClient();
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	public String get(String url) throws IOException {
		Request request = new Request.Builder().url(url).build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	public String post(String url, String json) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder().url(url).post(body).build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	/**
	 * 
	 * @param url
	 * @param json
	 * @return
	 * @throws IOException
	 *             { file:{ "":"" }, keyValue:{ "":"" } }
	 */
	public String submitForm(String url, String json) throws IOException {
		Form form = (Form) JSONUtil.parse(json, Form.class);
		// String filepath;

		// RequestBody requestBody = new MultipartBody.Builder()
		// .setType(MultipartBody.FORM)
		// .addFormDataPart("image", "test.jpg", fileBody)
		// .build();
		Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
		Map<String, String> fileMap = form.file;
		Set<String> fileSet = fileMap.keySet();
		for (String key : fileSet) {
			String filepath = fileMap.get(key);
			File file = new File(filepath);
			String fileName = file.getName();
			RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
			builder.addFormDataPart(key, fileName, fileBody);
		}
		Map<String, String> keyValueMap = form.keyValue;
		Set<String> keyValueSet = keyValueMap.keySet();
		for (String key : keyValueSet) {
			String value = keyValueMap.get(key);
			builder.addFormDataPart(key, value);
		}
		RequestBody requestBody = builder.build();
		System.out.println(requestBody.toString());
		Request request = new Request.Builder().url(url).post(requestBody).build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}
	public void download(String url, String filepath) throws IOException {
		download(url, filepath, null);
	}
	public void download(String url, String filepath,String headersStr) throws IOException {
		Request.Builder builder = new Request.Builder();
		if(headersStr!=null){
			Map<String,String> headersMap=(Map<String, String>) JSONUtil.parse(headersStr, Map.class);
			Set<String> keySet = headersMap.keySet();
			for (String key : keySet) {
				String value=headersMap.get(key);
				builder.addHeader(key, value);
			}
		}
		Request request = builder.url(url).build();
		 client.newCall(request)
		 	.enqueue(new Callback(){

				@Override
				public void onFailure(Call call, IOException e) {
					System.out.println("文件下载失败");
				}

				@Override
				public void onResponse(Call call, Response response) throws IOException {
					InputStream is=response.body().byteStream();
					System.out.println(response.body().contentLength());
					FileUtil.write(filepath, is);
					is.close();
				}
		 		
		 	});
				
//				execute();
	}

	public static void main(String[] args) throws IOException {
		HttpEnabler enabler = new HttpEnabler();
//		String filepath = "e:/demo.jpg";
//		Map<String, String> file = new HashMap<>();
//		long fileSize=FileEnabler.getFileSize(filepath);
//		file.put("file", filepath);
//		Map<String, String> keyValue = new HashMap<>();
//		keyValue.put("fileSize", String.valueOf(fileSize));
//		keyValue.put("fileName", FileEnabler.getFileName(filepath));
//		Form form=new Form(file,keyValue);
//		String json=JSONUtil.stringify(form);
//		String url="http://192.168.1.21:10500/miResourceMgr/upload";
//		String rs=enabler.submitForm(url, json);
//		System.out.println(rs);
		
		String url="http://img.mmjpg.com/2015/1/2.jpg";
		Map<String,String> headersMap=new HashMap<String, String>();
		headersMap.put("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
		headersMap.put("Referer", "http://www.mmjpg.com");
		String headersStr=JSONUtil.stringify(headersMap);
		enabler.download(url, "f:/java.jpg",headersStr);
		
		System.out.println("complete");
		
		
	}
}
