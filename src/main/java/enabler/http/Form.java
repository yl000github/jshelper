package enabler.http;

import java.util.Map;

public class Form {
	public Map<String,String> file;
	public Map<String,String> keyValue;
	public Form(Map<String,String> file,Map<String,String> keyValue) {
		this.file=file;
		this.keyValue=keyValue;
	}
}
