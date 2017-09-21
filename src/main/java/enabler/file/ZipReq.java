package enabler.file;

import java.util.List;

public class ZipReq {
	private String dest;
	private List<String> files;
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	public List<String> getFiles() {
		return files;
	}
	public void setFiles(List<String> files) {
		this.files = files;
	}
	
}
