package cn.net.tongfang.web.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.BasicInformation;
import cn.net.tongfang.framework.security.vo.FirstVistBeforeBorn;
import cn.net.tongfang.framework.security.vo.GravidityKey;
import cn.net.tongfang.framework.security.vo.HealthFile;
import cn.net.tongfang.framework.security.vo.PersonalInfo;
import cn.net.tongfang.framework.security.vo.SamTaxempcode;
import cn.net.tongfang.framework.security.vo.SamTaxorgcode;
import cn.net.tongfang.framework.security.vo.VisitBeforeBorn;
import cn.net.tongfang.framework.util.CommonConvertUtils;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.framework.util.SystemInformationUtils;
import cn.net.tongfang.web.service.bo.VisitBeforeBornBO;

public class VisitBeforeBornService extends HealthMainService<VisitBeforeBornBO> {
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
	public String save(VisitBeforeBornBO data) throws Exception {
		data.setFileNo(EncryptionUtils.encry(data.getFileNo()));
		if(sysInfo.hasHealthFileMaternal(data.getForeignId()) == null){
			throw new Exception("请先建立孕产妇保健手册。");
		}
		if(data.getItem().equals(2)){
			FirstVistBeforeBorn fvb = sysInfo.hasFirstVistBeforeBorn(data.getForeignId(),data.getGravidity());
			if(fvb != null){
				Timestamp preVisitDate = fvb.getVisitDate();
				Timestamp CurrentVisitDate = data.getVisitDate();
				long day = CommonConvertUtils.compareDate(preVisitDate, CurrentVisitDate);
				if(day < 28)
					throw new Exception("第2次产前随访与第1次产前随访的时间间隔至少为4周。");
			}
		}
		
		if(data.getHighRisk().equals("是")){
			womanRocordService.save(data.getFileNo(), data.getVisitDate(), data.getHighRiskRemark(), 1);
		}else if(data.getHighRisk().equals("否")){
			if(womanRocordService.get(data.getFileNo())){
				womanRocordService.update(data.getFileNo(), 0);
			}
		}
		
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		data.setExecDistrictNum(user.getDistrictId());
		return save_(data);
	}

	@Override
	public Object get(VisitBeforeBornBO data) throws Exception {
		data = (VisitBeforeBornBO)get_(data);;
		data.setFileNo(EncryptionUtils.decipher(data.getFileNo()));
		return data;
	}
	
	public VisitBeforeBornBO getPrintInfo(VisitBeforeBornBO data){
		try{
			data = (VisitBeforeBornBO)get(data);
			Integer items = data.getItem();
			if(items > 5){
				return null;
			}
		}catch(Exception ex){
			return null;
		}
		return data;
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Map<String,Object> getPrintInfo_new(VisitBeforeBornBO data){
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			data = (VisitBeforeBornBO)get(data);
			//HealthFile a, PersonalInfo b, VisitBeforeBorn c, SamTaxempcode d,SamTaxorgcode
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
			String beforeBornDirect = getPrintBasicInfo(data.getId(),"BeforeBornDirect","beforeBornDirectId","visitBeforeBornId");
			map.put("beforeBornDirect", beforeBornDirect);
			Integer items = data.getItem();
			if(items > 5){
				return null;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
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
	
	public String getItems(String fileNo){
		List list = getHibernateTemplate().find("From VisitBeforeBorn A Where A.foreignId = ? Order By item ASC", fileNo);
		String result = "";
		if(list.size() > 0){
			for(Object obj : list){
//				Object[] objs = (Object[])obj;
				VisitBeforeBorn visitBeforeBorn = (VisitBeforeBorn)obj;
				result += visitBeforeBorn.getItem() + ",";				
			}
		}
		if(!result.equals(""))
			result = result.substring(0,result.length() - 1);
		return result;
	}
	
	public String getLastMensesAndDay(String fileNo,Integer item){
		List list = getHibernateTemplate().find("From FirstVistBeforeBorn Where fileNo = ?", EncryptionUtils.encry(fileNo));
		if(list.size() > 0){
			FirstVistBeforeBorn firstVistBeforeBorn = (FirstVistBeforeBorn)list.get(0);
			if(firstVistBeforeBorn.getLastMenses() != null){
				Calendar cal = CommonConvertUtils.timestampToCalendar(firstVistBeforeBorn.getLastMenses());
				if(item.equals(2) || item.equals(3)){
					cal.add(Calendar.DATE, (item - 1) * 30 + 140);
					return CommonConvertUtils.dateToString(cal.getTime());
				}else if(item >= 4 && item <= 6){
					cal.add(Calendar.DATE, (item - 3) * 14 + 210);
					return CommonConvertUtils.dateToString(cal.getTime());
				}else{
					cal.add(Calendar.DATE, (item - 6) * 7 + 252);
					return CommonConvertUtils.dateToString(cal.getTime());
				}
			}
		}
		return null;
	}
}
