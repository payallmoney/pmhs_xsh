package cn.net.tongfang.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.BloodTrans;
import cn.net.tongfang.framework.security.vo.FirstVistBeforeBorn;
import cn.net.tongfang.framework.security.vo.GravidityKey;
import cn.net.tongfang.framework.security.vo.HealthFile;
import cn.net.tongfang.framework.security.vo.HealthFileMaternal;
import cn.net.tongfang.framework.security.vo.PersonalInfo;
import cn.net.tongfang.framework.security.vo.SamTaxempcode;
import cn.net.tongfang.framework.security.vo.SamTaxorgcode;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.framework.util.SystemInformationUtils;
import cn.net.tongfang.web.service.bo.FirstVisitBeforeBornPrintBO;
import cn.net.tongfang.web.service.bo.FirstVistBeforeBornBO;

public class FirstVistBeforeBornService extends HealthMainService<FirstVistBeforeBornBO> {
	private SystemInformationUtils sysUtils;
	private WomanLastMedicalExamRecordService womanRocordService; 
	
	public void setSysUtils(SystemInformationUtils sysUtils) {
		this.sysUtils = sysUtils;
	}
	
	public void setWomanRocordService(
			WomanLastMedicalExamRecordService womanRocordService) {
		this.womanRocordService = womanRocordService;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String save(FirstVistBeforeBornBO data) throws Exception {
		data.setFileNo(EncryptionUtils.encry(data.getFileNo()));		
//		PersonalInfo person = (PersonalInfo)getHibernateTemplate().find("From PersonalInfo Where fileNo = ?", data.getFileNo()).get(0);
//		person.setBornStatus("是");
//		GravidityKey gravidityKey = new GravidityKey();
//		gravidityKey.setFileNo(data.getFileNo());
//		gravidityKey.setCurrentGravidity(data.getGravidity());
//		getHibernateTemplate().save(person);		
//		getHibernateTemplate().save(gravidityKey);		
		if(sysUtils.hasHealthFileMaternal(data.getForeignId()) == null){
			throw new Exception("请先建立孕产妇保健手册。");
		}
		FirstVistBeforeBorn fvb = sysUtils.hasFirstVistBeforeBorn(data.getForeignId(),data.getGravidity());
		if(fvb != null &&( data.getId() == null || data.getId().trim().equals(""))){
			throw new Exception("第1次产前随访已录入。不允许重复录入!");
		}
		if(data.getHighRisk() != null && data.getHighRisk().equals("是")){
			womanRocordService.save(data.getFileNo(), data.getVisitDate(), data.getHighRiskRemark(), 1);
		}
		
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		data.setExecDistrictNum(user.getDistrictId());
		return save_(data);
	}
	
	@Override
	public Object get(FirstVistBeforeBornBO data) throws Exception {
		data = (FirstVistBeforeBornBO)get_(data);;
		data.setFileNo(EncryptionUtils.decipher(data.getFileNo()));
		return data;
	}
	public FirstVisitBeforeBornPrintBO getPrintInfo(FirstVisitBeforeBornPrintBO data){
		String id = data.getId();
		FirstVistBeforeBorn FirstVistBeforeBorn = (FirstVistBeforeBorn)getHibernateTemplate().get(FirstVistBeforeBorn.class, id);
		BeanUtils.copyProperties(FirstVistBeforeBorn, data);
		data.setFileNo(EncryptionUtils.decipher(data.getFileNo()));
		String femePastHistory = sysUtils.getPrintBasicInfo(id,"FemePastHistory","femePastHistoryId","firstVistBeforeBornId");
		String femeFamilyHistory = sysUtils.getPrintBasicInfo(id,"FemeFamilyHistory","femeFamilyHistoryId","firstVistBeforeBornId");
		String femeSecretion = sysUtils.getPrintBasicInfo(id,"FemeSecretion","femeSecretionId","firstVistBeforeBornId");
//		String beforeBornCheckDirect = sysUtils.getPrintBasicInfo(id,"BeforeBornCheckDirect","beforeBornCheckDirectId","firstVistBeforeBornId");
		data.setFemePastHistory(femePastHistory);
		data.setFemeFamilyHistory(femeFamilyHistory);
		data.setFemeSecretion(femeSecretion);
		return data;
	}
	public Map<String, Object> getPrintInfo_new(FirstVisitBeforeBornPrintBO data){
		Map<String, Object> ret = new HashMap<String,Object>();
		String id = data.getId();
		FirstVistBeforeBorn firstVistBeforeBorn = (FirstVistBeforeBorn)getHibernateTemplate().get(FirstVistBeforeBorn.class, id);
		//HealthFile a, PersonalInfo b, FirstVistBeforeBorn c, SamTaxempcode d,SamTaxorgcode
		BeanUtils.copyProperties(firstVistBeforeBorn, data);
		data.setFileNo(EncryptionUtils.decipher(data.getFileNo()));
		String femePastHistory = sysUtils.getPrintBasicInfo(id,"FemePastHistory","femePastHistoryId","firstVistBeforeBornId");
		String femeFamilyHistory = sysUtils.getPrintBasicInfo(id,"FemeFamilyHistory","femeFamilyHistoryId","firstVistBeforeBornId");
		String femeSecretion = sysUtils.getPrintBasicInfo(id,"FemeSecretion","femeSecretionId","firstVistBeforeBornId");
//		String beforeBornCheckDirect = sysUtils.getPrintBasicInfo(id,"BeforeBornCheckDirect","beforeBornCheckDirectId","firstVistBeforeBornId");
		data.setFemePastHistory(femePastHistory);
		data.setFemeFamilyHistory(femeFamilyHistory);
		data.setFemeSecretion(femeSecretion);
		ret.put("firstVisit", data);
		HealthFile file = (HealthFile)getHibernateTemplate().get(HealthFile.class, firstVistBeforeBorn.getFileNo());
		ret.put("file", file);
		HealthFileMaternal maternal = (HealthFileMaternal)getHibernateTemplate().get(HealthFileMaternal.class, firstVistBeforeBorn.getForeignId());
		ret.put("maternal", maternal);
		PersonalInfo person = (PersonalInfo)getHibernateTemplate().find("from PersonalInfo where fileno=?", firstVistBeforeBorn.getFileNo()).get(0);
		getHibernateTemplate().evict(person);
		person.setIdnumber(EncryptionUtils.decipher(person.getIdnumber()));
		ret.put("person", person);
		ret.put("firstVisit", data);
		SamTaxempcode samTaxempcode =  (SamTaxempcode)getHibernateTemplate().get(SamTaxempcode.class, firstVistBeforeBorn.getInputPersonId());
		ret.put("samTaxempcode", samTaxempcode);
		SamTaxorgcode samTaxorgcode =  (SamTaxorgcode)getHibernateTemplate().get(SamTaxorgcode.class, samTaxempcode.getOrgId());
		ret.put("org", samTaxorgcode);
		FirstVisitBeforeBornPrintBO feme = new FirstVisitBeforeBornPrintBO();
		String personalHistory = sysUtils.getPrintBasicInfo(id,"PersonalHistory","personalHistoryId","firstVistBeforeBornId");
		String allergiesHistory = sysUtils.getPrintBasicInfo(id,"AllergiesHistory","allergiesId","personalInfoId");
		feme.setFemePastHistory(femePastHistory);
		feme.setFemeFamilyHistory(femeFamilyHistory);
		feme.setFemeSecretion(femeSecretion);
		feme.setExam01(personalHistory);
		feme.setExam02(allergiesHistory);
		StringBuilder hql1 = new StringBuilder(
				"from BloodTrans ")
				.append(" where personalInfoId = '"+person.getId()+"' ").append(" order by transDate desc ");
		List<BloodTrans> bloodTrans = getHibernateTemplate().find(hql1.toString());
		String str = "";
		String str1 = "";
		if(bloodTrans.size()>0){
			feme.setExam03(bloodTrans.get(0).getTransDate());
		}else{
			feme.setExam03("无");
		}
		String beforeBornCheckDirect = sysUtils.getPrintBasicInfo(firstVistBeforeBorn.getId(),"BeforeBornCheckDirect","beforeBornCheckDirectId","firstVistBeforeBornId");
		feme.setExam04(beforeBornCheckDirect);
		ret.put("feme", feme);
		return ret;
	}
}
