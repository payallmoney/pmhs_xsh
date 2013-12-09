package cn.net.tongfang.web.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.BirthCertificate;
import cn.net.tongfang.framework.security.vo.ChildBirthRecord;
import cn.net.tongfang.framework.security.vo.HealthFile;
import cn.net.tongfang.framework.security.vo.HealthFileChildren;
import cn.net.tongfang.framework.security.vo.PersonalInfo;
import cn.net.tongfang.framework.security.vo.PregnancyRecordChild;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.web.service.bo.HealthFileChildrenBO;

public class HealthFileChildrenService extends HealthMainService<HealthFileChildrenBO>{

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String save(HealthFileChildrenBO data) throws Exception {
		String fileNo = EncryptionUtils.encry(data.getFileNo());
		data.setFileNo(fileNo);
		data.setName(EncryptionUtils.encry(data.getName()));
		HealthFile file = (HealthFile)getHibernateTemplate().get(HealthFile.class, fileNo);
		file.setIsOverCount(1);//标记为已经建立儿保手册
		getHibernateTemplate().update(file);
//		Query query = null;
		String hql = " From HealthFileChildren Where fileNo = ?";
		List list = getHibernateTemplate().find(hql,fileNo);
		if(list.size() > 0){
			HealthFileChildren child = (HealthFileChildren)list.get(0);
			data.setId(child.getId());
		}
		return save_(data);
	}

	@Override
	public Object get(HealthFileChildrenBO data) throws Exception {
		data = (HealthFileChildrenBO)get_(data);
		data.setFileNo(EncryptionUtils.decipher(data.getFileNo()));
		data.setName(EncryptionUtils.decipher(data.getName()));
		return data;
	}

	public List getChildrenOtherInfo(String fileNo){
		Query query = null;
		String hql = " From BirthCertificate Where fileNo = ?";
		List list = getHibernateTemplate().find(hql,EncryptionUtils.encry(fileNo));
		if(list.size() > 0){
			List retList = new ArrayList();
			BirthCertificate birth = (BirthCertificate)list.get(0);
//			retList.add(birth);
			retList.add(birth.getMotherName());
			retList.add(birth.getFatherName());
			retList.add(birth.getWeight());
			retList.add(birth.getHeight());
			retList.add(birth.getBorthWeekly());
			retList.add(birth.getMotherNation());
			retList.add(birth.getFatherNation());
			retList.add(birth.getBorthOrganization());
			hql = " From ChildBirthRecord A,HealthFile B,PersonalInfo C Where A.certifiId = ? And A.fileNo = B.fileNo And B.fileNo = C.fileNo ";
			List l = getHibernateTemplate().find(hql,birth.getCertifiId());
			if(l.size() > 0){
				Object objs = l.get(0);
				Object[] obj = (Object[])objs;
				ChildBirthRecord childBirthRecord = (ChildBirthRecord)obj[0];
				HealthFile file = (HealthFile)obj[1];
				PersonalInfo person = (PersonalInfo)obj[2];
				retList.add(file.getTel());//母亲电话
				retList.add(childBirthRecord.getChildbirthWay());
				retList.add(person.getEducation());
				retList.add(person.getOccupation());
			}
			return retList;
		}
		return null;
	}
	
	 public Map<String, Object> getPrintInfo_new(HealthFileChildrenBO data) throws Exception
	  {
	    data = (HealthFileChildrenBO)get_(data);
	    Map map = new HashMap();
	    HealthFile file = (HealthFile)getHibernateTemplate().get(HealthFile.class, data.getFileNo());
	    getHibernateTemplate().evict(file);
	    getHibernateTemplate().evict(file.getPersonalInfo());
	    map.put("file", file);
	    data.setFileNo(EncryptionUtils.decipher(data.getFileNo()));
	    data.setName(EncryptionUtils.decipher(data.getName()));
	    map.put("children", data);
	    map.put("status", DateUtils.toCalendar(new java.util.Date()).get(1) - DateUtils.toCalendar(data.getBirthday()).get(1) > 7 ? "结案" : "未结案");
	    return map;
	  }
	
	 @Transactional(propagation = Propagation.REQUIRED)
	public void PregnancyRecordChildService(PregnancyRecordChild pregnancy){
		if(pregnancy.getId() != null && !pregnancy.getId().equals("")){
			getHibernateTemplate().update(pregnancy);
		}
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		pregnancy.setInputPersonId(user.getUsername());
		pregnancy.setInputDate(new Timestamp(System.currentTimeMillis()));
		getHibernateTemplate().save(pregnancy);
	}
}
