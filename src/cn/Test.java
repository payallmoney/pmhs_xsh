package cn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import sun.misc.BASE64Decoder;

public class Test {

	/**
	 * @param args
	 * @throws DocumentException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public static void main(String[] args) {
		// System.out.println(EncryptionUtils.decipher("芬昰苒"));//530521101203003997
		// System.out.println(EncryptionUtils.encry("530521102214000559"));//VPSVQRQSSVRQRVQWRR
		// String str = "2012-07-01";
		// String str1 = "2012-06-01";
		// System.out.println(CommonConvertUtils.stringToDate(str).compareTo(CommonConvertUtils.stringToDate(str1)));

		// Pattern p = Pattern.compile("[0-9]+");
		// Matcher m = p.matcher("530521102214000559");
		// boolean b = m.matches();
		// System.out.println(b);
		File file = new File("H:\\SysImageFile.xml");
		// SAXReader saxReader = new SAXReader();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			// Element root = document.getRootElement();
			NodeList nodeList = document.getElementsByTagName("ROW");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				NamedNodeMap attr = node.getAttributes();
				String nodeVal = "";
//				if(i == 10)
//					break;
				for (int j = 0; j < attr.getLength(); j++) {
					Node nattr = attr.item(j);
					if (nattr.getNodeName().equals("iSI_Image")) {
						nodeVal += nattr.getNodeValue() + ":";
						BASE64Decoder decoder = new BASE64Decoder();
						byte[] data = decoder.decodeBuffer(nattr.getNodeValue());
						FileOutputStream fos = new FileOutputStream("H:\\" + i + ".jpg");
						fos.write(data);
						fos.flush();
						fos.close();
					}
				}
				System.out.println(nodeVal);
			}
			System.out.println(nodeList.getLength());
			System.out.println("working...");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
