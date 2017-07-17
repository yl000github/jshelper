package enabler.jrobot;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ClipboardEnabler implements ClipboardOwner{
	public static  Clipboard clip;
	public ClipboardEnabler(){
		clip = Toolkit.getDefaultToolkit().getSystemClipboard();//获取系统剪贴板
	}
	
	public String getContent(){
		 // 获取剪切板中的内容
        Transferable clipT = clip.getContents(null);
        if (clipT != null) {
	        // 检查内容是否是文本类型
	        if (clipT.isDataFlavorSupported(DataFlavor.stringFlavor))
				try {
					return (String)clipT.getTransferData(DataFlavor.stringFlavor);
				} catch (UnsupportedFlavorException | IOException e) {
					e.printStackTrace();
				} 
        }
		return "";
	}
	public void setContent(String content){
        StringSelection ss=new StringSelection(content);
        int i=5;
        while(i-->0){
        	try {
        		clip.setContents(ss, null);
        		return;
        	} catch (IllegalStateException  e) {
        		try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
        	}
        }
	}
	public static void main(String[] args) throws UnsupportedFlavorException, IOException {
		ClipboardEnabler enabler=new ClipboardEnabler();
		for (int i = 0; i < 1000; i++) {
			enabler.setContent("hello yang"+i);
			System.out.println(enabler.getContent());
		}
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		
	}
}
