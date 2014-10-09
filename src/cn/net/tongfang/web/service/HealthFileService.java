package cn.net.tongfang.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.net.tongfang.framework.security.vo.HealthFile;
import cn.net.tongfang.framework.security.vo.Opshistory;
import cn.net.tongfang.framework.security.vo.PersonalInfo;
import cn.net.tongfang.framework.security.vo.TraumaHistory;
import cn.net.tongfang.framework.util.BusiUtils;
import cn.net.tongfang.web.service.bo.HealthFileBO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HealthFileService extends HibernateDaoSupport {
	private static final Logger log = Logger.getLogger(HealthFileService.class);

	@Transactional(propagation = Propagation.REQUIRED)
	public PersonalInfo save(HealthFileBO data) {

		PersonalInfo info = new PersonalInfo();

		org.springframework.beans.BeanUtils.copyProperties(data, info); // spring beanutils

		String testFileNo = java.util.UUID.randomUUID().toString();
		if (testFileNo.length() > 20) {
			testFileNo = testFileNo.substring(0, 19);
		}
		
		info.setFileNo(testFileNo);
		info.setFileNoSub(testFileNo.substring(testFileNo.length() - 7));
		info.setInputDate(new java.sql.Timestamp(System.currentTimeMillis()));
		//info.setInputPersonId(1);
		getHibernateTemplate().saveOrUpdate(info);

		for (Opshistory ops : data.opshistory) {
			ops.setPersonalInfoId(info.getId());
		}

		for (TraumaHistory ops : data.traumaHistory) {
			ops.setPersonalInfoId(info.getId());
		}

		getHibernateTemplate().saveOrUpdateAll(data.opshistory);
		getHibernateTemplate().saveOrUpdateAll(data.traumaHistory);
		return info;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Map  restoreHealthfile(final String id ) throws Exception{
		try{
		Map ret = new HashMap();
		Gson gson = new GsonBuilder()  
		  .setDateFormat("yyyy-MM-dd HH:mm:ss")  
		  .create(); 
		String json = (String)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				try{
					SQLQuery filequery = session.createSQLQuery(" select olddata from log_main where id = ? ");
					filequery.setString(0, id);
					List data =  filequery.list();
					String json = (String)data.get(0);
					return json;
				}catch(RuntimeException ex){
					ex.printStackTrace();
				}catch(Exception ex){
					ex.printStackTrace();
				}
				return null;
			}
		});
		Map healthfile = gson.fromJson(json, HashMap.class);
		Map personalinfo = (Map)healthfile.get("personalInfo");
		HealthFile hf =  gson.fromJson(json, HealthFile.class);
		HealthFile oldhf = (HealthFile)getHibernateTemplate().get(HealthFile.class, hf.getFileNo().trim());
		getHibernateTemplate().evict(oldhf);
		if(oldhf !=null){
			ret.put("success", false);
			ret.put("msg", "档案已被恢复!无法再次恢复!");
			return ret;
		}
		PersonalInfo pinfo = new PersonalInfo();
//		BusiUtils.copyProperties(healthfile, hf);
		BeanUtils.populate(pinfo,personalinfo);
//		BeanUtils.copyProperties(personalinfo, pinfo);
//		BusiUtils.copyProperties(personalinfo, pinfo);
		getHibernateTemplate().save(hf);
		getHibernateTemplate().save(pinfo);
		ret.put("success", true);
		return ret;
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
	
	public HealthFileBO get(String id) {
		return null;
	}

}
