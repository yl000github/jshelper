package jrobot.enabler;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyMap {
	static Map<String,Integer> maps=new HashMap<>();
	static{
//		maps.put("A", KeyEvent.VK_A);
		//字母数字
		maps.put("0", KeyEvent.VK_0);
		maps.put("1", KeyEvent.VK_1);
		maps.put("2", KeyEvent.VK_2);
		maps.put("3", KeyEvent.VK_3);
		maps.put("4", KeyEvent.VK_4);
		maps.put("5", KeyEvent.VK_5);
		maps.put("6", KeyEvent.VK_6);
		maps.put("7", KeyEvent.VK_7);
		maps.put("8", KeyEvent.VK_8);
		maps.put("9", KeyEvent.VK_9);
		maps.put("A", KeyEvent.VK_A);
		maps.put("B", KeyEvent.VK_B);
		maps.put("C", KeyEvent.VK_C);
		maps.put("D", KeyEvent.VK_D);
		maps.put("E", KeyEvent.VK_E);
		maps.put("F", KeyEvent.VK_F);
		maps.put("G", KeyEvent.VK_G);
		maps.put("H", KeyEvent.VK_H);
		maps.put("I", KeyEvent.VK_I);
		maps.put("J", KeyEvent.VK_J);
		maps.put("K", KeyEvent.VK_K);
		maps.put("L", KeyEvent.VK_L);
		maps.put("M", KeyEvent.VK_M);
		maps.put("N", KeyEvent.VK_N);
		maps.put("O", KeyEvent.VK_O);
		maps.put("P", KeyEvent.VK_P);
		maps.put("Q", KeyEvent.VK_Q);
		maps.put("R", KeyEvent.VK_R);
		maps.put("S", KeyEvent.VK_S);
		maps.put("T", KeyEvent.VK_T);
		maps.put("U", KeyEvent.VK_U);
		maps.put("V", KeyEvent.VK_V);
		maps.put("W", KeyEvent.VK_W);
		maps.put("X", KeyEvent.VK_X);
		maps.put("Y", KeyEvent.VK_Y);
		maps.put("Z", KeyEvent.VK_Z);
		//特殊符号
		maps.put("Esc", KeyEvent.VK_ESCAPE);
		maps.put("Tab", KeyEvent.VK_TAB);
		maps.put("CapsLock", KeyEvent.VK_CAPS_LOCK);
		maps.put("Shift", KeyEvent.VK_SHIFT);
		maps.put("Control", KeyEvent.VK_CONTROL);
		maps.put("Win", KeyEvent.VK_WINDOWS);
		maps.put("Alt", KeyEvent.VK_ALT);
		maps.put("Space", KeyEvent.VK_SPACE);
		maps.put("Comma", KeyEvent.VK_COMMA);
		maps.put("Period", KeyEvent.VK_PERIOD);
		maps.put("Slash", KeyEvent.VK_SLASH);
		maps.put("BackSlash", KeyEvent.VK_BACK_SLASH);
		maps.put("Semicolon", KeyEvent.VK_SEMICOLON);
		maps.put("Quote", KeyEvent.VK_QUOTE);
		maps.put("LBracket", KeyEvent.VK_OPEN_BRACKET);
		maps.put("RBracket", KeyEvent.VK_CLOSE_BRACKET);
		maps.put("Enter", KeyEvent.VK_ENTER);
		maps.put("=", KeyEvent.VK_EQUALS);
		maps.put("-", KeyEvent.VK_MINUS);
		maps.put("Insert", KeyEvent.VK_INSERT);
		maps.put("Delete", KeyEvent.VK_DELETE);
	}
	public static int map(String src){
		return maps.get(src);
	}
	public static void main(String[] args) {
		String template="maps.put(\"{{A}}\", KeyEvent.VK_{{A}});";
		for (int i = 48; i < 91; i++) {
			String t=template.replace("{{A}}",  String.valueOf((char)i));
			System.out.println(t);
		}
	}
}
