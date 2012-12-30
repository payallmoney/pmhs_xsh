package cn.net.tongfang.web.service;

import cn.net.tongfang.framework.security.SecurityManager;
import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.BasicInformation;
import cn.net.tongfang.framework.security.vo.BirthCertificate;
import cn.net.tongfang.framework.security.vo.HealthFile;
import cn.net.tongfang.framework.security.vo.HealthFileChildren;
import cn.net.tongfang.framework.security.vo.PersonalInfo;
import cn.net.tongfang.framework.security.vo.PregnancyRecordChild;
import cn.net.tongfang.framework.security.vo.SamTaxempcode;
import cn.net.tongfang.framework.security.vo.SamTaxorgcode;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.web.service.bo.HealthFileChildrenBO;
import cn.net.tongfang.web.service.bo.VisitBeforeBornBO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.StringUtils;

public class HealthFileChildrenService extends HealthMainService<HealthFileChildrenBO>
{
  public String save(HealthFileChildrenBO data)
    throws Exception
  {
    String fileNo = EncryptionUtils.encry(data.getFileNo());
    data.setFileNo(fileNo);
    data.setName(EncryptionUtils.encry(data.getName()));
    HealthFile file = (HealthFile)getHibernateTemplate().get(HealthFile.class, fileNo);
    file.setIsOverCount(Integer.valueOf(1));
    getHibernateTemplate().update(file);

    String hql = " From HealthFileChildren Where fileNo = ?";
    Query query = getSession().createQuery(hql);
    query.setParameter(0, fileNo);
    List list = query.list();
    if (list.size() > 0) {
      HealthFileChildren child = (HealthFileChildren)list.get(0);
      data.setId(child.getId());
    }
    return save_(data);
  }

  public Object get(HealthFileChildrenBO data) throws Exception
  {
    data = (HealthFileChildrenBO)get_(data);
    data.setFileNo(EncryptionUtils.decipher(data.getFileNo()));
    data.setName(EncryptionUtils.decipher(data.getName()));
    return data;
  }

  public List getChildrenOtherInfo(String fileNo) {
    Query query = null;
    String hql = " From BirthCertificate Where fileNo = ?";
    query = getSession().createQuery(hql);
    query.setParameter(0, EncryptionUtils.encry(fileNo));
    List list = query.list();
    if (list.size() > 0) {
      List retList = new ArrayList();
      BirthCertificate birth = (BirthCertificate)list.get(0);
      retList.add(birth);
      hql = " From ChildBirthRecord Where certifiId = ?";
      query = getSession().createQuery(hql);
      query.setParameter(0, birth.getCertifiId());
      List l = query.list();
      if (l.size() > 0) {
        retList.add(l.get(0));
      }
      return retList;
    }
    return null;
  }

  public void PregnancyRecordChildService(PregnancyRecordChild pregnancy) {
    if ((pregnancy.getId() != null) && (!pregnancy.getId().equals(""))) {
      getHibernateTemplate().update(pregnancy);
    }
    TaxempDetail user = SecurityManager.currentOperator();
    pregnancy.setInputPersonId(user.getUsername());
    pregnancy.setInputDate(new Timestamp(System.currentTimeMillis()));
    getHibernateTemplate().save(pregnancy);
  }
  
  
  public Map<String,Object> getPrintInfo_new(HealthFileChildrenBO data) throws Exception{
		System.out.println("==================="+data.getId());
		data = (HealthFileChildrenBO)get_(data);
		Map<String,Object> map = new HashMap<String,Object>();
		HealthFile file = (HealthFile)getHibernateTemplate().get(HealthFile.class, data.getFileNo());
		getHibernateTemplate().evict(file);
		getHibernateTemplate().evict(file.getPersonalInfo());
		map.put("file", file);
		data.setFileNo(EncryptionUtils.decipher(data.getFileNo()));
	    data.setName(EncryptionUtils.decipher(data.getName()));
		map.put("children", data);
		map.put("status", DateUtils.toCalendar(new Date()).get(Calendar.YEAR) -  DateUtils.toCalendar(data.getBirthday()).get(Calendar.YEAR) >7 ?"结案":"未结案");
		return map;
	}
	public String getPrintBasicInfo(String id,String tableName,String key,String tableKey){
		String hql = "From BasicInformation A," + tableName + " B Where A.id = B." + key + " And B." + tableKey + " = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, id);
		List list = query.list();
		String ret = "未测";
		if(list.size() > 0){
			ret = "";
			for(Object objs : list){
				Object[] obj = (Object[])objs;
				BasicInformation basicInformation = (BasicInformation)obj[0];
				ret = ret + basicInformation.getName() + ",";
			}
			if(!ret.equals(""))
				ret = ret.substring(0,ret.length() - 1);
		}
		return ret;
	}
}