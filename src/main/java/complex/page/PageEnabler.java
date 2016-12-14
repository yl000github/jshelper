package complex.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

//操作html页
public class PageEnabler {
	WebDriver webDriver;
	public PageEnabler() {
		webDriver = new HtmlUnitDriver();
	}
	public void open(String url){
		webDriver.get(url);
	}
	public Dom findElement(String pattern){
		WebElement we=webDriver.findElement(By.xpath(pattern));
		return new Dom(we);
	}
	public List<Dom> findElements(String pattern){
		List<WebElement> we=webDriver.findElements(By.xpath(pattern));
		List<Dom> list=new ArrayList<>();
		for (WebElement w : we) {
			list.add(new Dom(w));
		}
		return list;
	}
}
