package enabler.http;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
		
//		String url="http://chengyu.t086.com/chaxun.php?q1=&q2=&q3=&q4=";
//		Document doc = Jsoup.connect(url).timeout(30000).get(); 
//		System.out.println(doc.html());;
		
//		String url="http://www.mmjpg.com/mm/7";
//		Document doc = Jsoup.connect(url).timeout(30000).get(); 
//		Elements list = doc.select("div.page").select("a");
//		for (Element element : list) {
//			System.out.println(element.html());
//		}
//		Element element = list.get(list.size()-2);
//		System.out.println(element.html());
		
		//pic path
		String url="http://www.mmjpg.com/mm/7/2";
		Document doc = Jsoup.connect(url).timeout(30000).get(); 
		String path = doc.select("div.content").select("a").select("img").attr("src");
		System.out.println();
		
		
//		System.out.println(list.get(-2));
	}
}
