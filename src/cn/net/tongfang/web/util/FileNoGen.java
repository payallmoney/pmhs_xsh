package cn.net.tongfang.web.util;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.net.tongfang.framework.security.vo.ExamId;
import cn.net.tongfang.framework.security.vo.HealthFileKey;

public class FileNoGen extends HibernateDaoSupport{
	private static final Logger log = Logger.getLogger(FileNoGen.class);
	
	@Autowired
	private String districtnumber;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public String getNextFileNo(String districtNumber) throws Exception{
		List<HealthFileKey> c = getHibernateTemplate().find("select p from HealthFileKey p where p.districtNumber = ?" , districtNumber);
		int key = 1;
		HealthFileKey hf = null;
		if (c.size() > 0 ) {
			hf = c.get(0);
			key =  1 + c.get(0).getMaxKey();
			hf.setMaxKey(key);
		} else {
			hf = new HealthFileKey();
			hf.setDistrictNumber(districtNumber);
			hf.setMaxKey(key);
		}
		
		getHibernateTemplate().saveOrUpdate(hf);
		
		String strKey = key + "";
		
		int spaces = 5 - strKey.length();
		
		if (spaces < 0) {
			String msg = "流水号超长！！！！！";
			log.error(msg);
			throw new Exception(msg);
		}
		
		for(int i = 0; i < spaces; i++) {
			strKey = "0" + strKey;
		}
		
		return districtNumber + strKey;
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public String getNextExamId() throws Exception{
		String year = ""+Calendar.getInstance().get(Calendar.YEAR);
		List<ExamId> list = getHibernateTemplate().find("from ExamId where id.year = ?" , year);
		int key = 1;
		ExamId id = null;
		if (list.size() > 0 ) {
			id = list.get(0);
			key =  1 + list.get(0).getMax();
			id.setMax(key);
		} else {
			id = new ExamId();
			id.setYear(year);
			id.setMax(key);
		}
		getHibernateTemplate().saveOrUpdate(id);
		getHibernateTemplate().flush();
		String strKey = key + "";
		int spaces = 8 - strKey.length();
		
		if (spaces < 0) {
			String msg = "流水号超长！！！！！";
			log.error(msg);
			throw new Exception(msg);
		}
		for(int i = 0; i < spaces; i++) {
			strKey = "0" + strKey;
		}
		return districtnumber.substring(0,4)+year + strKey;
	}
}
