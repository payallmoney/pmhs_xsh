package cn.net.tongfang.web.service;

import java.sql.Timestamp;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.net.tongfang.framework.security.vo.WomanLastMedicalExamRecord;

public class WomanLastMedicalExamRecordService extends HibernateDaoSupport {
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(String fileNo,Timestamp lastExamDate,String highRiskRemarks,Integer type){
		WomanLastMedicalExamRecord records = new WomanLastMedicalExamRecord();
		records.setFileNo(fileNo);
		records.setLastExamDate(lastExamDate);
		records.setHighRiskRemarks(highRiskRemarks);
		records.setType(type);
		getHibernateTemplate().saveOrUpdate(records);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(String fileNo,Integer type){
		WomanLastMedicalExamRecord records = (WomanLastMedicalExamRecord)getHibernateTemplate().get(WomanLastMedicalExamRecord.class, fileNo);
		records.setType(type);
		getHibernateTemplate().saveOrUpdate(records);
	}
	
	public boolean get(String fileNo){
		WomanLastMedicalExamRecord records = getObj(fileNo);
		if(records == null)
			return false;
		return true;
	}
	
	public WomanLastMedicalExamRecord getObj(String fileNo){
		return (WomanLastMedicalExamRecord)getHibernateTemplate().get(WomanLastMedicalExamRecord.class, fileNo);
	}
}
