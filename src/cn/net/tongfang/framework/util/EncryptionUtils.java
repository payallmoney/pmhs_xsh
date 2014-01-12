package cn.net.tongfang.framework.util;

import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class EncryptionUtils {
	private static ApplicationContext applicationContext;//静态方法自己无法初始化,由cn.net.tongfang.framework.util.EncryUtils帮助初始化ApplicationContext
	private static final HashMap<String, String> ENCODE_HASHMAP = new HashMap<String, String>();
	private static final HashMap<String, String> DECODE_HASHMAP = new HashMap<String, String>();
	private static final int STEP = 2;
	private static char[] charArray = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 't' };
	static {
		// 数字
		ENCODE_HASHMAP.put("0", "$");
		ENCODE_HASHMAP.put("1", "&");
		ENCODE_HASHMAP.put("2", "@");
		ENCODE_HASHMAP.put("3", "*");
		ENCODE_HASHMAP.put("4", "!");
		ENCODE_HASHMAP.put("5", ".");
		ENCODE_HASHMAP.put("6", ":");
		ENCODE_HASHMAP.put("7", "=");
		ENCODE_HASHMAP.put("8", ">");
		ENCODE_HASHMAP.put("9", "}");
		// 小写字母
		ENCODE_HASHMAP.put("a", "€");
		ENCODE_HASHMAP.put("b", "‚");
		ENCODE_HASHMAP.put("c", "ƒ");
		ENCODE_HASHMAP.put("d", "ˆ");
		ENCODE_HASHMAP.put("e", "‰");
		ENCODE_HASHMAP.put("f", "Š");
		ENCODE_HASHMAP.put("g", "‹");
		ENCODE_HASHMAP.put("h", "Œ");
		ENCODE_HASHMAP.put("i", "Ž");
		ENCODE_HASHMAP.put("j", "‘");
		ENCODE_HASHMAP.put("k", "’");
		ENCODE_HASHMAP.put("l", "•");
		ENCODE_HASHMAP.put("m", "–");
		ENCODE_HASHMAP.put("n", "à");
		ENCODE_HASHMAP.put("o", "á");
		ENCODE_HASHMAP.put("p", "â");
		ENCODE_HASHMAP.put("q", "ã");
		ENCODE_HASHMAP.put("r", "ä");
		ENCODE_HASHMAP.put("s", "æ");
		ENCODE_HASHMAP.put("t", "ç");
		ENCODE_HASHMAP.put("u", "ç");
		ENCODE_HASHMAP.put("v", "è");
		ENCODE_HASHMAP.put("w", "é");
		ENCODE_HASHMAP.put("x", "ê");
		ENCODE_HASHMAP.put("y", "ë");
		ENCODE_HASHMAP.put("z", "ì");
		// 大写字母
		ENCODE_HASHMAP.put("A", "ß");
		ENCODE_HASHMAP.put("B", "Þ");
		ENCODE_HASHMAP.put("C", "Ý");
		ENCODE_HASHMAP.put("D", "Ü");
		ENCODE_HASHMAP.put("E", "Û");
		ENCODE_HASHMAP.put("F", "Ü");
		ENCODE_HASHMAP.put("G", "Û");
		ENCODE_HASHMAP.put("H", "Ú");
		ENCODE_HASHMAP.put("I", "Ù");
		ENCODE_HASHMAP.put("J", "Ø");
		ENCODE_HASHMAP.put("K", "Ö");
		ENCODE_HASHMAP.put("L", "Õ");
		ENCODE_HASHMAP.put("M", "Ô");
		ENCODE_HASHMAP.put("N", "Ó");
		ENCODE_HASHMAP.put("O", "Ò");
		ENCODE_HASHMAP.put("P", "Ñ");
		ENCODE_HASHMAP.put("Q", "Ð");
		ENCODE_HASHMAP.put("R", "Ï");
		ENCODE_HASHMAP.put("S", "Ê");
		ENCODE_HASHMAP.put("T", "É");
		ENCODE_HASHMAP.put("U", "Ç");
		ENCODE_HASHMAP.put("V", "Æ");
		ENCODE_HASHMAP.put("W", "Ä");
		ENCODE_HASHMAP.put("X", "Ã");
		ENCODE_HASHMAP.put("Y", "£");
		ENCODE_HASHMAP.put("Z", "Á");
	}
	static {
		for (String key : ENCODE_HASHMAP.keySet()) {
			String value = ENCODE_HASHMAP.get(key);
			DECODE_HASHMAP.put(value, key);
		}
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		EncryptionUtils.applicationContext = applicationContext;
	}

	/**
	 * 加密
	 * 
	 * @param str
	 * @return
	 */
	public static final String encry(String str) {
		return str;
//		if (str == null) {
//			return "";
//		}
//		EncryUtils util = (EncryUtils)applicationContext.getBean("EncryUtils");
//		if(util!=null && util.isNeedEncry()){
//			String result = "";
//			String tmpStr = "";
//			char[] charData = str.toCharArray();
//			for (int i = 0; i < charData.length; i++) {
//				tmpStr = String.copyValueOf(charData, i, 1);
//				if (!tmpStr.equals("%") && tmpStr != "%") {
//					if (ENCODE_HASHMAP.get(tmpStr) == null) {
//						tmpStr = encryChinese(tmpStr);
//					} else {
//						tmpStr = ENCODE_HASHMAP.get(tmpStr);
//					}
//				}
//				result = result + tmpStr;
//			}
//			return result;
//		}else{
//			return str;
//		}
	}
	
	public static final String testEncry(String str) {
		if (str == null) {
			return "";
		}
		String result = "";
		String tmpStr = "";
		char[] charData = str.toCharArray();
		for (int i = 0; i < charData.length; i++) {
			tmpStr = String.copyValueOf(charData, i, 1);
			if (!tmpStr.equals("%") && tmpStr != "%") {
				if (ENCODE_HASHMAP.get(tmpStr) == null) {
					tmpStr = encryChinese(tmpStr);
				} else {
					tmpStr = ENCODE_HASHMAP.get(tmpStr);
				}
			}
			result = result + tmpStr;
		}
		return result;
	}

	/**
	 * 解密
	 * 
	 * @param str
	 * @return
	 */
	public static final String decipher(String str) {
		return str;
//		if (str == null) {
//			return "";
//		}
//		EncryUtils util = (EncryUtils)applicationContext.getBean("EncryUtils");
//		if(util!=null && util.isNeedEncry()){
//			String result = "";
//			String tmpStr = "";
//			char[] charData = str.toCharArray();
//			for (int i = 0; i < charData.length; i++) {
//				tmpStr = String.copyValueOf(charData, i, 1);
//				if (!tmpStr.equals("%") && tmpStr != "%") {
//					if (DECODE_HASHMAP.get(tmpStr) == null) {
//						tmpStr = decipherChinese(tmpStr);
//					} else {
//						tmpStr = DECODE_HASHMAP.get(tmpStr);
//					}
//				}
//				result = result + tmpStr;
//			}
//			return result;
//		}else{
//			return str;
//		}
	}
	
	public static final String testDecipher(String str) {
		if (str == null) {
			return "";
		}
		String result = "";
		String tmpStr = "";
		char[] charData = str.toCharArray();
		for (int i = 0; i < charData.length; i++) {
			tmpStr = String.copyValueOf(charData, i, 1);
			if (!tmpStr.equals("%") && tmpStr != "%") {
				if (DECODE_HASHMAP.get(tmpStr) == null) {
					tmpStr = decipherChinese(tmpStr);
				} else {
					tmpStr = DECODE_HASHMAP.get(tmpStr);
				}
			}
			result = result + tmpStr;
		}
		return result;
	}

	/**
	 * 其它字符加密
	 * 
	 * @param str
	 * @return
	 */
	private static final String encryChinese(String str) {
		return str;
//		int len = 0;
//		char[] chars = str.toCharArray();
//		for (int i = 0; i < chars.length; i++) {
//			if (i >= charArray.length) {
//				len = 0;
//			} else {
//				len = i + STEP;
//			}
//			chars[i] = (char) (chars[i] ^ charArray[len]);
//		}
//		String result = new String(chars);
//		return result;
	}

	/**
	 * 其它字符加密
	 * 
	 * @param str
	 * @return
	 */
	private static final String decipherChinese(String str) {
		return str;
//		int len = 0;
//		char[] chars = str.toCharArray();
//		for (int i = 0; i < chars.length; i++) {
//			if (i >= charArray.length) {
//				len = 0;
//			} else {
//				len = i + STEP;
//			}
//			chars[i] = (char) (chars[i] ^ charArray[len]);
//		}
//		String result = new String(chars);
//		return result;
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	/**/
	public static void main(String[] args) {
		String str1 = testEncry("张延姍");
		System.out.println(str1);
		String str2 = testDecipher(str1);
		System.out.println(str2);

		// String str3 = encry("2001901991");
		// System.out.println(str3);
		// String str4 = decipher(str3);
		// System.out.println(str4);
		//
		// String str5 = encry("我们_?%");
		// System.out.println(str5);
		// String str6 = decipher(str5);
		// System.out.println(str6);
	}
}
