import java.io.IOException;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;

public class TestInterface {
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
		'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	public static void main(String[] args) {
		try{
			doPost();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) { 			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}
	
	public static String encodeByMD5(String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(str.getBytes("utf-8"));
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs.append("0").append(stmp);
			else
				hs.append(stmp);
		}
		return hs.toString().toUpperCase();
	}
	
	public static String md5Signature(String data) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			result = byte2hex(md.digest(data.getBytes("utf-8")));
		} catch (Exception e) {
			throw new java.lang.RuntimeException("sign error !");
		}
		return result;
	}

	public static String postInterface(Map<String , Object> params,String url) throws Exception{
		String key = "898292749541717766716123592084203347286665228316401312013178786189888292273188428513083985";
		String merId = "100005";
		String dataMode = "0";
		String openId = "ph000001";
		String foramt = "json";
		String ip = "119.188.126.39";
		String port="8080";
		Gson gs = new Gson();
		String data = gs.toJson(params);
		String sign = md5Signature(data+merId+dataMode+key);
		HttpPost httppost = new HttpPost("http://"+ip+":"+port+"/"+url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
        nvps.add(new BasicNameValuePair("merId", merId));  
        nvps.add(new BasicNameValuePair("dataMode", dataMode));  
        nvps.add(new BasicNameValuePair("data", data));  
        nvps.add(new BasicNameValuePair("sign", sign));  
        nvps.add(new BasicNameValuePair("openId", openId));  
        nvps.add(new BasicNameValuePair("foramt", "json"));  
		httppost.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));  
		ResponseHandler<String> rh = new ResponseHandler<String>() {
			@Override
			public String handleResponse(final HttpResponse response)
					throws IOException {
				StatusLine statusLine = response.getStatusLine();
				HttpEntity entity = response.getEntity();
				if (statusLine.getStatusCode() >= 300) {
					throw new HttpResponseException(statusLine.getStatusCode(),
							statusLine.getReasonPhrase());
				}
				if (entity == null) {
					throw new ClientProtocolException(
							"Response contains no content");
				}
				StringWriter writer = new StringWriter();
				IOUtils.copy(entity.getContent(), writer, "utf8");
				return writer.toString();
			}
		};
		CloseableHttpClient httpclient = HttpClients.createDefault();
		return httpclient.execute(httppost, rh);
	}
	
	public static String doPost() throws Exception {
		Map<String , Object> params = new HashMap<String , Object>();
//		params.put("createdDateStart", "2014-06-16 00:00:00");
//		params.put("createdDateEnd", "2014-06-16 00:00:00");
//		params.put("doctor","佟医生");
		Map<String,Integer> pagePara = new HashMap<String ,Integer>();
		pagePara.put("pageIndex", 1);
		pagePara.put("pageSize", 10);
//		pagePara.put("recordStart", 1000);
		
		params.put("pagePara", pagePara);
		
		String url = "/api/baseinfo/list_baseinfo";
		String ret = postInterface(params,url);
		System.out.println("======ret======"+ret);
		
		Map<String , String> examparams = new HashMap<String , String>();
//		examparams.put("createdDateStart", "2014-06-16 00:00:00");
//		examparams.put("createdDateEnd", "2014-06-16 00:00:00");
//		examparams.put("doctor","佟医生");
		url = "/api/physical/list_physical";
		params.put("isDel", "N");
		ret = postInterface(params,url);
		System.out.println("======ret======"+ret);
		return "";
	}
}
