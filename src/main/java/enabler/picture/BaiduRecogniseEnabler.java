package enabler.picture;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

public class BaiduRecogniseEnabler {
	  //设置APPID/AK/SK
    public static final String APP_ID = "9818283";
    public static final String API_KEY = "ga3Un4ogGoljO34hUhDro2ZV";
    public static final String SECRET_KEY = "SkTv7N0ldgWPFKTIx7vyFWKcinTpheN2";

	AipOcr client;
	HashMap<String, String> options;
	public BaiduRecogniseEnabler(){
		// 初始化一个OcrClient
        client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 自定义参数定义
        options = new HashMap<String, String>();
        options.put("detect_direction", "false");
        options.put("language_type", "CHN_ENG");
	}
	public String recognise(String path){
        // 参数为本地图片路径
        String imagePath = path;
//        String imagePath = "e:/data.png";
        JSONObject response = client.basicGeneral(imagePath, options);
//        System.out.println(response.toString());
		return response.toString();
	}

    public static void main(String[] args) {
       

//        // 参数为本地图片文件二进制数组
//        byte[] file = readImageFile(imagePath);
//        JSONObject response = client.basicGeneral(file, options);
//        System.out.println(response.toString());
    }
}
