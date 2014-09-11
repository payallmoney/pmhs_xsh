package cn.net.tongfang.web.service.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.cxf.common.util.StringUtils;

import cn.net.tongfang.framework.security.demo.service.TaxempDetail;

public class BaseParamUtil {
	public static Map makeBaseParam(List baseparams){
		Map ret = new HashMap();
		for(int i = 0 ; i<baseparams.size();i++){
			Map row = (Map)baseparams.get(i);
			String name = (String)row.get("name");
			String type = (String)row.get("type");
			ret.put(name, getDataByType(type));	
		}
		return ret;
	}
	
	private static Object getDataByType(String type){
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		if("opt".equals(type)){
			return user.getUsername();
		}else if("now".equals(type)){
			SimpleDateFormat fomarttime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			return fomarttime.format(new Date());
		}else if("today".equals(type)){
			SimpleDateFormat fomarttime = new SimpleDateFormat("yyyy-MM-dd");
			return fomarttime.format(new Date());
		}else if("orgid".equals(type)){
			return user.getOrgId();
		}else if("optname".equals(type)){
			return user.getTaxempname();
		}else if("uuid".equals(type)){
			return UUID.randomUUID().toString().replaceAll("-", "");
		}else{
			return null;
		}
	}
	
	public static boolean hasId(List ids , Map data){
		boolean ret = true;
		for(int i = 0 ;i <ids.size();i++){
			if(StringUtils.isEmpty((String)data.get(ids.get(i)))){
				ret =false;
			}
		}
		return ret;
	}
}
