package cn.net.tongfang.web.port.shangdong;

import java.io.IOException;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.net.tongfang.web.port.BaseInter;
import cn.net.tongfang.web.util.FileNoGen;

import com.google.gson.Gson;

public class ShangDongInterface implements BaseInter {
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static Gson gs = new Gson();
	@Autowired
	private FileNoGen fileNoGen;
	private static Map<String, String> cfgmap = new HashMap<String, String>();
	private static Map<String, List> colmap = new HashMap<String, List>();
	private static Map<String, Map> valuemap = new HashMap<String, Map>();
	private static List collist = new ArrayList();
	private static Map distinctInputpersonMap = new HashMap();
	private static int pagesize = 100;
	public boolean testflag = false;
	
	public void setFileNoGen(FileNoGen fileNoGen) {
		this.fileNoGen = fileNoGen;
	}

	// md5加密方法
	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

	// md5加密方法
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

	// 执行接口
	public void exec(final String interfacecode, HibernateTemplate hbt) {
		List cfgs = (List) hbt.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session
						.createSQLQuery(" select code,url,listname from map_interface_itemcfg where valid = 1 and interface='"
								+ interfacecode + "' ");
				List list = query.list();
				return list;
			}
		});
		for (Object cfg : cfgs) {
			Object[] row = (Object[]) cfg;
			final String subcode = (String) row[0];
			final String url = (String) row[1];
			final String listname = (String) row[2];
			int startpage = 1;
			Calendar c = new GregorianCalendar();
			c.set(Calendar.HOUR_OF_DAY, 0); // anything 0 - 23
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.add(Calendar.DATE, -1);
			Date begindate = c.getTime();
			c.set(Calendar.HOUR_OF_DAY, 23); // anything 0 - 23
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			c.set(Calendar.MILLISECOND, 999);
			Date enddate = c.getTime();
			// 取得params
			final Map params = getParams(interfacecode, startpage, begindate,
					enddate);
//			params.put("archiveid","13062910021300176");
			System.out.println("============"+gs.toJson(params));
			try {
				String json = postInterface(interfacecode, subcode, url,
						params, hbt);
				if(ShangDongInterfaceTestData.testMap.containsKey(subcode) && testflag){
					json = ShangDongInterfaceTestData.testMap.get(subcode);
				}
				System.out.println("=======json====="+json);
				Map ret = gs.fromJson(json, HashMap.class);
				// 对数据进行映射
				List datas = (List)ret.get(listname);
				System.out.println("======datas.size======"+datas.size());
				for(int i = 0; i <datas.size();i++){
					System.out.println("======begin======"+(Map)datas.get(i));
					Map dataobj = mapData((Map)datas.get(i), interfacecode, subcode, hbt);
					System.out.println("=====result======="+dataobj);
				}
				
				// 保存数据

			} catch (Exception ex) {
				ex.printStackTrace();
				continue;
			}
		}
	}

	// 初始化接口
	public void init(final String interfacename, HibernateTemplate hbt) {

	}

	// 生成接口配置参数
	private Map getParams(String name, int startpage, Date begin, Date end) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Map<String, Object> params = new HashMap<String, Object>();
		if ("healthfile".equals(name)) {
			params.put("createdDateStart", sf.format(begin));
			params.put("createdDateEnd", sf.format(end));
		} else if ("healthexam".equals(name)) {
			params.put("checkDateStart", sf.format(begin));
			params.put("checkDateEnd", sf.format(end));
		}
		Map<String, Integer> pagePara = new HashMap<String, Integer>();
		pagePara.put("pageIndex", startpage);
		pagePara.put("pageSize", pagesize);
		params.put("pagePara", pagePara);
		return params;
	}

	private Map<String, String> getCfg(HibernateTemplate hbt, String name) {
		return getCfg(hbt, false, name);
	}

	// 获得接口系统参数
	private Map<String, String> getCfg(HibernateTemplate hbt, boolean flag,
			final String interfacecode) {
		if (cfgmap.size() == 0 || flag) {
			cfgmap.clear();
			List cfgs = (List) hbt.execute(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query query = session
							.createSQLQuery(" select code,value from map_interface_cfg where interface='"
									+ interfacecode + "' ");
					List list = query.list();
					return list;
				}
			});
			for (int i = 0; i < cfgs.size(); i++) {
				Object[] row = (Object[]) cfgs.get(i);
				cfgmap.put((String) row[0], (String) row[1]);
			}
		}
		return cfgmap;
	}

	// 获得接口系统参数
	private Map<String, Map> getValueMap(HibernateTemplate hbt, boolean flag,
			final String interfacecode) {
		if (valuemap.size() == 0 || flag) {
			valuemap.clear();
			List values = (List) hbt.execute(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					SQLQuery query = session
							.createSQLQuery(" select typename,code,value from map_interface_valuemap where interface='"
									+ interfacecode + "' ");
					query.addScalar("typename", Hibernate.STRING);
					query.addScalar("code", Hibernate.STRING);
					query.addScalar("value", Hibernate.STRING);
					List list = query.list();
					return list;
				}
			});
			for (int i = 0; i < values.size(); i++) {
				Object[] row = (Object[]) values.get(i);
				String typename = (String) row[0];
				String code = (String) row[1];
				String value = (String) row[2];
				if (!valuemap.containsKey(typename)) {
					valuemap.put(typename, new HashMap<String, String>());
				}
				Map<String, String> tmp = valuemap.get(typename);
				tmp.put(code, value);
			}
		}
		return valuemap;
	}

	// 获得接口系统参数
	private Map<String, Map> getValueMap(HibernateTemplate hbt,
			final String interfacecode, String name) {
		return getValueMap(hbt, false, interfacecode).get(name);
	}

	// 获得接口系统参数
	private Object getMapValue(HibernateTemplate hbt,
			final String interfacecode, String name,Object code) {
		Map map = getValueMap(hbt, interfacecode,name);
		return (map == null || code == null) ? code:map.get(code.toString());
	}

	private Map getDistrictInputpersonMap(HibernateTemplate hbt, String name) {
		return getDistrictInputpersonMap(hbt, false, name);
	}

	// 获得接口系统参数
	private Map getDistrictInputpersonMap(HibernateTemplate hbt, boolean flag,
			final String interfacecode) {
		if (distinctInputpersonMap.size() == 0 || flag) {
			distinctInputpersonMap.clear();
			List cfgs = (List) hbt.execute(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query query = session
							.createSQLQuery(" select districtid,inputperson from map_interface_org_inputperson where interface='"
									+ interfacecode + "' ");
					List list = query.list();
					return list;
				}
			});
			for (int i = 0; i < cfgs.size(); i++) {
				Object[] row = (Object[]) cfgs.get(i);
				distinctInputpersonMap.put((String) row[0], (String) row[1]);
			}
		}
		return distinctInputpersonMap;
	}

	// 得到配置列表
	private List getColList(HibernateTemplate hbt, String name, String subcode) {
		return getColList(hbt, false, name, subcode);
	}

	// 得到配置列表
	private List getColList(HibernateTemplate hbt, boolean flag,
			final String interfacecode, String subcode) {
		if (colmap.size() == 0 || flag) {
			colmap.clear();
			List cfgs = (List) hbt.execute(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query query = session
							.createSQLQuery("select * from map_interface_col_map where interface='"
									+ interfacecode + "' order by ord ");
					List list = query.list();
					return list;
				}
			});
			for (int i = 0; i < cfgs.size(); i++) {
				Object[] row = (Object[]) cfgs.get(i);
				Map data = new HashMap();
				data.put("interfacecode", row[0]);
				data.put("subcode", row[1]);
				data.put("systable", row[2]);
				data.put("syscol", row[3]);
				data.put("type", row[4]);
				data.put("value", row[5]);
				data.put("interfacecol", row[6]);
				if (!colmap.containsKey(row[1])) {
					colmap.put((String) row[1], new ArrayList());
				}
				List rowlist = (List) colmap.get(row[1]);
				rowlist.add(data);
			}
		}
		return (List) colmap.get(subcode);
	}

	// 提交接口取得json数据
	public String postInterface(final String interfacecode,
			final String subcode, final String url,
			final Map<String, Object> params, HibernateTemplate hbt)
			throws Exception {
		// 从数据库得到参数
		Map<String, String> cfg = getCfg(hbt, interfacecode);
		String key = cfg.get("key");
		String merId = cfg.get("merId");
		String dataMode = cfg.get("dataMode");
		String openId = cfg.get("openId");
		String foramt = cfg.get("foramt");
		String ip = cfg.get("ip");
		String port = cfg.get("port");
		// System.out.println("======key======"+key);
		// System.out.println("======merId======"+merId);
		// System.out.println("======dataMode======"+dataMode);
		// System.out.println("======openId======"+openId);
		// System.out.println("======foramt======"+foramt);
		// System.out.println("======ip======"+ip);
		// System.out.println("======port======"+port);
		String data = gs.toJson(params);
		String sign = encodeByMD5(data + merId + dataMode + key);
		final String posturl = "http://" + ip + ":" + port + "/" + url;
		HttpPost httppost = new HttpPost(posturl);
		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("merId", merId));
		nvps.add(new BasicNameValuePair("dataMode", dataMode));
		nvps.add(new BasicNameValuePair("data", data));
		nvps.add(new BasicNameValuePair("sign", sign));
		nvps.add(new BasicNameValuePair("openId", openId));
		nvps.add(new BasicNameValuePair("foramt", foramt));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
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
					throw new ClientProtocolException("接口配置错误!");
				}
				StringWriter writer = new StringWriter();
				IOUtils.copy(entity.getContent(), writer, "utf8");
				return writer.toString();
			}
		};
		CloseableHttpClient httpclient = HttpClients.createDefault();
		final String ret = httpclient.execute(httppost, rh);
		// 记录日志得到每次的参数及返回结果
		hbt.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session
						.createSQLQuery(" insert into map_interface_log values(?,?,getdate(),?,?,?) ");
				query.setParameter(0, interfacecode);
				query.setParameter(1, subcode);
				query.setParameter(2, ret);
				query.setParameter(3, gs.toJson(nvps));
				query.setParameter(4, posturl);
				query.executeUpdate();
				return null;
			}
		});
		return ret;
	}

	public Map mapData(Map data, final String interfacecode,
			final String subcode, HibernateTemplate hbt) throws Exception {
		List collist = getColList(hbt, interfacecode, subcode);
		Map ret = new HashMap();
		Map districtinputpersonmap = getDistrictInputpersonMap(hbt,
				interfacecode);
		for ( int i = 0 ;i < collist.size() ;i ++) {
			Map row = (Map) collist.get(i);
			String tinterfacecode = (String) row.get("interfacecode");
			String tsubcode = (String) row.get("subcode");
			String systable = (String) row.get("systable");
			String syscol = (String) row.get("syscol");
			Map<String,String> typename = gs.fromJson((String)row.get("type"), HashMap.class);
			String value = (String) row.get("value");
			String interfacecol = (String) row.get("interfacecol");
			if("list".equals(typename.get("type"))){
				ret.put(systable, getValue(row, data, districtinputpersonmap,ret,hbt));
			}else{
				if (!ret.containsKey(systable) ) {
					ret.put(systable, new HashMap());
				}
				Map values = (Map) ret.get(systable);
				values.put(syscol, getValue(row, data, districtinputpersonmap,ret,hbt));
			}
		}
		return ret;
	}

	private Object getValue(Map row, Map data, Map districtinputpersonmap,Map result,HibernateTemplate hbt)
			throws Exception {
		String tinterfacecode = (String) row.get("interfacecode");
		String tsubcode = (String) row.get("subcode");
		String systable = (String) row.get("systable");
		String syscol = (String) row.get("syscol");
		Map<String,String> typename = gs.fromJson((String)row.get("type"), HashMap.class);
		String value = (String) row.get("value");
		String interfacecol = (String) row.get("interfacecol");
		String type = (String) typename.get("type");
		String name = (String)typename.get("name");
		Object ret = null;
		if ("fileno".equals(type)) {
			String district = (String)getDataValue((String)typename.get("distinct"),result);
			ret = fileNoGen.getNextFileNo(district);
		} else if ("value".equals(type)) {
			ret = getDataValue(interfacecol, data);
		} else if ("map".equals(type)) {
			if("split".equals(typename.get("listtype"))){
				String split = typename.get("split");
				split = split==null ? ",":split;
				String listdata = (String)getDataValue(interfacecol,data);
				String retstr = "";
				if(listdata!=null){
					String[] array = listdata.split(split);
					for(int i=0 ; i <array.length;i++){
						retstr = retstr+split+ (String)getMapValue(hbt,tinterfacecode,name,array[i]);
					}
				}
				ret = retstr.substring(split.length());
			}else{
				ret = getMapValue(hbt,tinterfacecode,name,getDataValue(interfacecol, data));
			}
		} else if ("date".equals(type)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			//createdDate=2014-08-07 00:00:00
			ret = sdf.parse((String) getDataValue(interfacecol, data));
		} else if ("matchvalue".equals(type)) {
			ret = getDataMatchValue(interfacecol, data);
		} else if ("static".equals(type)) {
			ret = value;
		} else if ("inputperson".equals(type)) {
			ret = districtinputpersonmap.get(getDataMatchValue(value, data));
		} else if ("uuid".equals(type)) {
			ret = UUID.randomUUID().toString().replaceAll("-", "");
		} else if ("othervalue".equals(type)) {
			String othercol = (String)typename.get("code");
			ret = getDataValue(othercol,result);
		} else if ("list".equals(type)) {
			String mapname = typename.get("map");
			List collist = getColList(hbt, tinterfacecode, mapname);
			List<Map> arraydata = null;
			if("array".equals(typename.get("listtype"))){
				arraydata = (List)getDataValue(interfacecol,data);
			}else if("split".equals(typename.get("listtype"))){
				String split = typename.get("split");
				split = split==null ? ",":split;
				String listdata = (String)getDataValue(interfacecol,data);
				arraydata = new ArrayList();
				if(listdata!=null){
					String[] array = listdata.split(split);
					for(int i=0 ; i <array.length;i++){
						Map item = new HashMap();
						item.put("data", array[i]);
						arraydata.add(item);
					}
				}
				System.out.println("======arraydata======"+arraydata.size());
				System.out.println("======arraydata======"+arraydata);
			}else{
				arraydata = new ArrayList();
			}
			List array = new ArrayList();
			
			String checkcol = typename.get("checkcol");
			String checkvalue = typename.get("checkvalue");
			
			for(int i=0 ;i<arraydata.size();i++){
				//如果有检查列并且检查列不相等的时候,跳出
				if(checkcol !=null && checkvalue !=null && !checkvalue .equals(String.valueOf(getDataValue(checkcol,arraydata.get(i))))){
					continue;
				}
				Map arrayitem = new HashMap();
				for(int j =0 ; j<collist.size() ; j++){
					Map itemrow = (Map) collist.get(j);
					String iteminterfacecode = (String) itemrow.get("interfacecode");
					String itemsubcode = (String) itemrow.get("subcode");
					String itemsystable = (String) itemrow.get("systable");
					String itemsyscol = (String) itemrow.get("syscol");
					Map<String,String> itemtypename = gs.fromJson((String)row.get("type"), HashMap.class);
					String itemvalue = (String) itemrow.get("value");
					String iteminterfacecol = (String) itemrow.get("interfacecol");
					arrayitem.put(itemsyscol, getValue(itemrow, arraydata.get(i), districtinputpersonmap,result,hbt));
				}
				array.add(arrayitem);
			}
			ret = array;
		} else {
			ret = null;
		}
		return ret;
	}
	
	private Object cloneObj(Object t){
		if(t instanceof HashMap){
			return  gs.fromJson(gs.toJson(t), HashMap.class);
		}else{
			return  gs.fromJson(gs.toJson(t), Object.class);
		}
	}

	// 根据interfacecol得到值
	private Object getDataValue(String interfacecol,Map data) throws Exception {
		String[] cols = interfacecol.split("\\.");
		Object curobj = data;
		for (int i = 0; i < cols.length; i++) {
			Object col = cols[i].trim();
			if (curobj == null) {
				return null;
			} else {
				if (curobj instanceof Map) {
					if (((Map) curobj).containsKey(col)) {
						curobj = ((Map) curobj).get(col);
						if (i == cols.length - 1) {
							return curobj;
						}
					}else{
						return null;
					}
				} else {
					return curobj;
				}
			}
		}
		return null;
	}

	// 根据interfacecol得到值,多个值检查
	private Object getDataMatchValue(String interfacecols, Map data) throws Exception {
		String[] interfacecolarray = interfacecols.split(",");
		for (int m = 0; m < interfacecolarray.length; m++) {
			String interfacecol = interfacecolarray[m];
			Object value = getDataValue(interfacecol,data);
			if (value != null) {
				return value;
			}
		}
		return null;
	}
}
