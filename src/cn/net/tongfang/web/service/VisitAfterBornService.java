package cn.net.tongfang.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.BasicInformation;
import cn.net.tongfang.framework.security.vo.GravidityKey;
import cn.net.tongfang.framework.security.vo.HealthFile;
import cn.net.tongfang.framework.security.vo.HealthFileMaternal;
import cn.net.tongfang.framework.security.vo.PersonalInfo;
import cn.net.tongfang.framework.security.vo.SamTaxempcode;
import cn.net.tongfang.framework.security.vo.SamTaxorgcode;
import cn.net.tongfang.framework.security.vo.WomanLastMedicalExamRecord;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.framework.util.SystemInformationUtils;
import cn.net.tongfang.web.service.bo.VisitAfterBornBO;

public class VisitAfterBornService extends HealthMainService<VisitAfterBornBO> {
	private SystemInformationUtils sysInfo;
	private WomanLastMedicalExamRecordService womanRocordService; 
	public void setSysInfo(SystemInformationUtils sysInfo) {
		this.sysInfo = sysInfo;
	}
	public void setWomanRocordService(
			WomanLastMedicalExamRecordService womanRocordService) {
		this.womanRocordService = womanRocordService;
	}



	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String save(VisitAfterBornBO data) throws Exception {
		if(sysInfo.hasHealthFileMaternal(data.getForeignId()) == null){
			throw new Exception("请先建立孕产妇保健手册。");
		}
		if(data.getId() == null){
			if(sysInfo.hasVisitAfterBorn(data.getForeignId(),data.getRecordType(),data.getItem())){
				String msg = "";
				if(data.getRecordType().equals("0"))
					msg = "产后访视记录";
				else
					msg = "产后42天健康体检记录";
				throw new Exception("编号为" + data.getFileNo() + "的孕产妇的此次产期的" + 
						msg + "已经录入系统，不可以重复录入。");
			}
		}
		data.setFileNo(EncryptionUtils.encry(data.getFileNo()));
		if(data.getRecordType().equals("1")){
			PersonalInfo person = (PersonalInfo)getHibernateTemplate().find("From PersonalInfo Where fileNo = ?", data.getFileNo()).get(0);
			person.setBornStatus("否");
//			person.setHomeId("曾经");
			getHibernateTemplate().update(person);
			List maternallist = getHibernateTemplate().find("From HealthFileMaternal Where fileNo = ? And isClosed = '0' ", data.getFileNo());
			if(maternallist.size()>0){
				HealthFileMaternal maternal = (HealthFileMaternal)maternallist.get(0);
				maternal.setIsClosed("1");
				maternal.setClosedDate(data.getVisitDate());
				getHibernateTemplate().update(maternal);
			}
			
//			List list = getHibernateTemplate().find("From GravidityKey Where fileNo = ?", data.getFileNo());
//			if(list.size() > 0){
//				GravidityKey gravidityKey = (GravidityKey)list.get(0);
//				getHibernateTemplate().delete(gravidityKey);
//			}
		}
		WomanLastMedicalExamRecord records = womanRocordService.getObj(data.getFileNo());
		if(records != null && records.getType().equals(1)){
			womanRocordService.update(data.getFileNo(), 0);
		}
		
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		data.setExecDistrictNum(user.getDistrictId());
		return save_(data);
	}

	@Override
	public Object get(VisitAfterBornBO data) throws Exception {
		data = (VisitAfterBornBO)get_(data);;
		data.setFileNo(EncryptionUtils.decipher(data.getFileNo()));
		return data;
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Map<String,Object> getPrintInfo_new(VisitAfterBornBO data) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		data = (VisitAfterBornBO)get_(data);
		data.setFileNo(EncryptionUtils.decipher(data.getFileNo()));
		//"from HealthFile a, PersonalInfo b, VisitAfterBorn c, SamTaxempcode d,SamTaxorgcode e")
		HealthFile file = (HealthFile)getHibernateTemplate().get(HealthFile.class, data.getFileNo());
		map.put("file", file);
		PersonalInfo person = (PersonalInfo)getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("from PersonalInfo where fileno=?").setParameter(0,EncryptionUtils.encry(data.getFileNo())).list().get(0);
		getHibernateTemplate().getSessionFactory().getCurrentSession().evict(person);
		person.setIdnumber(EncryptionUtils.decipher(person.getIdnumber()));
		map.put("person", person);
		map.put("visit", data);
		SamTaxempcode samTaxempcode =  (SamTaxempcode)getHibernateTemplate().get(SamTaxempcode.class, data.getInputPersonId());
		map.put("samTaxempcode", samTaxempcode);
		SamTaxorgcode samTaxorgcode =  (SamTaxorgcode)getHibernateTemplate().get(SamTaxorgcode.class, samTaxempcode.getOrgId());
		map.put("org", samTaxorgcode);
		String afterBornDirect = getPrintBasicInfo(data.getId(),"AfterBornDirect","afterBornDirectId","visitAfterBornId");
		map.put("afterBornDirect", afterBornDirect);
		return map;
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public String getPrintBasicInfo(String id,String tableName,String key,String tableKey){
		String hql = "From BasicInformation A," + tableName + " B Where A.id = B." + key + " And B." + tableKey + " = ?";
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
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
