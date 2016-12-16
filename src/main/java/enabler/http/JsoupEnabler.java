package enabler.http;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JsoupEnabler {
	public static Document open(String url) throws IOException{
		return Jsoup.connect(url).timeout(30000).get();
	}
	
	public static void main(String[] args) throws IOException {
//		String word="人";
//		String url="http://www.iciba.com/"+word;
//		Document doc = Jsoup.connect(url).get(); 
////		System.out.println(doc.html());
//		Elements els=doc.getElementsByClass("container-left");
//		String rs=els.get(0).getElementsByTag("ul").get(0).text();
//		System.out.println(rs);;
		String url="http://chengyu.t086.com/chaxun.php?q1=&q2=&q3=&q4=";
		Document doc = Jsoup.connect(url).timeout(30000).get(); 
		System.out.println(doc.html());;
	}
}
