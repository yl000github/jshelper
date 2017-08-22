package complex.page;

import org.openqa.selenium.WebElement;

public class Dom {
	WebElement ele;
	public Dom(WebElement ele) {
		this.ele=ele;
	}
	public void click(){
		ele.click();
	}
	public String getText(){
		return ele.getText();
	}
//	public String getHtml(){
//		return ele.get
//	}
	public void setValue(String src){
		ele.sendKeys(src);
	}
//	public void setHtml(){
//	}
}
