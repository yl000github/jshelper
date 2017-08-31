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
	public static Document parse(String html){
		return Jsoup.parse(html);
	}
	public static Document parseBody(String body){
		return Jsoup.parseBodyFragment(body);
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
//		System.out.println(doc.html());
//		System.out.println(doc.select("div.header").text());
//		System.out.println(doc.select("div.header").html());
//		System.out.println(doc.select("div.header").outerHtml());
		System.out.println(doc.select("div").outerHtml());
//		System.out.println(doc.select("div.header").text());
//		String path = doc.select("div.header").text();
//		System.out.println(path);
		
//		System.out.println(list.get(-2));
	}
}
