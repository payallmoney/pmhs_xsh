package cn.net.tongfang.web.service;

import java.beans.PropertyDescriptor;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.BabyVisit;
import cn.net.tongfang.framework.security.vo.HealthFile;
import cn.net.tongfang.framework.security.vo.PersonalInfo;
import cn.net.tongfang.framework.security.vo.SamTaxempcode;
import cn.net.tongfang.framework.security.vo.SamTaxorgcode;
import cn.net.tongfang.framework.security.vo.WomanPhysicalItems;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.framework.util.SystemInformationUtils;
import cn.net.tongfang.framework.util.service.GenDefaultVal;
import cn.net.tongfang.web.service.bo.BabyVisitBO;
import cn.net.tongfang.web.service.bo.BabyVisitPrintBO;
import cn.net.tongfang.web.util.BOHelper;

public class BabyVisitService extends HibernateDaoSupport {
	private static final Logger log = Logger.getLogger(BabyVisitService.class);
	BOHelper boHelper = new BOHelper(BabyVisitBO.class);
	private GenDefaultVal childDirect;
	private SystemInformationUtils sysUtils;
	private ChildLastMedicalExamRecordService childRocordService;
	public void setSysUtils(SystemInformationUtils sysUtils) {
		this.sysUtils = sysUtils;
	}
    public void setChildDirect(GenDefaultVal childDirect) {
		this.childDirect = childDirect;
	}
    
	public void setChildRocordService(
			ChildLastMedicalExamRecordService childRocordService) {
		this.childRocordService = childRocordService;
	}
	private String update(BabyVisitBO data) throws Exception{
//		data.setFileNo(EncryptionUtils.encry(data.getFileNo()));
		BabyVisit babyVisit = new BabyVisit();
		WomanPhysicalItems womanItems = new WomanPhysicalItems();
		BeanUtils.copyProperties(data, babyVisit); 
		getHibernateTemplate().update(babyVisit);
		
		//如果更新照片，则把已经存在的照片删除
//		WomanPhysicalItems womanPicture = (WomanPhysicalItems)getHibernateTemplate().find("From WomanPhysicalItems Where id = ?",babyVisit.getId()).get(0);
//		String picture = womanPicture.getOnlinePhoto();
//		if(picture != null && !picture.equals("")){
//			File file = new File("/" + picture);
//			if(file.exists()){
//				file.delete();
//			}
//		}
		BeanUtils.copyProperties(data, womanItems);
		womanItems.setDirectAge(childDirect.getAge(data.getBirthday(),data.getVisitDate()));
		if(data.getMedicalExamId() != null){
			getHibernateTemplate().update(womanItems);
		}else{
			womanItems.setInputDate(new Timestamp(System.currentTimeMillis()));
			getHibernateTemplate().merge(womanItems);
		}
		
		String id = babyVisit.getId();
		boHelper.deleteDetails(data, getSession(), "babyVisitId", id);
		boHelper.setFK(data, id, "babyVisitId");
		boHelper.saveDetails(data, getHibernateTemplate());
		return id;
	}
	
	public String save(BabyVisitBO data) throws Exception {
		data.setFileNo(EncryptionUtils.encry(data.getFileNo()));
		if(data.getHighRisk().equals("是")){
			childRocordService.save(data.getFileNo(), data.getVisitDate(), data.getHighRiskRemark(), 1);
		}else if(data.getHighRisk().equals("否")){
			if(childRocordService.get(data.getFileNo())){
				childRocordService.update(data.getFileNo(), 0);
			}
		}
		
		if(data.getId() != null && !data.getId().equals("")){
			System.out.println("Updating...");
			return update(data);
		}
		
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		String inputPersonId = user.getUsername();
		Timestamp currentDate = new java.sql.Timestamp(System.currentTimeMillis());
		
		data.setExecDistrictNum(user.getDistrictId());
		BabyVisit babyVisit = new BabyVisit();
		WomanPhysicalItems womanItems = new WomanPhysicalItems();
		BeanUtils.copyProperties(data, babyVisit); 
		BeanUtils.copyProperties(data, womanItems);
		babyVisit.setInputPersonId(inputPersonId);
		babyVisit.setInputDate(currentDate);
		getHibernateTemplate().save(babyVisit);
		
		womanItems.setId(babyVisit.getId());
		womanItems.setInputPersonId(inputPersonId);
		womanItems.setInputDate(currentDate);
		womanItems.setDirectAge(childDirect.getAge(data.getBirthday(),data.getVisitDate()));
		getHibernateTemplate().save(womanItems);
		
		boHelper.setFK(data, babyVisit.getId(), "babyVisitId");
		boHelper.saveDetails(data, getHibernateTemplate());
		
//		Object o = boHelper.saveOrUpdateAll(data, getHibernateTemplate(),
//				getSession()).toString();
//		System.out.println("res is " + o);
//		log.debug("res is " + o);
//		return o.toString();
		return babyVisit.getId();
	}

	public BabyVisitBO get(BabyVisitBO data) throws Exception {
		BabyVisit babyVisit = (BabyVisit)getHibernateTemplate().get(BabyVisit.class,data.getId());
		List womanItemsList = getHibernateTemplate().find("From WomanPhysicalItems Where id = ?",data.getId());
		if(womanItemsList.size() > 0){
			WomanPhysicalItems womanItems = (WomanPhysicalItems)womanItemsList.get(0);
			BeanUtils.copyProperties(womanItems, data);
		}
		BeanUtils.copyProperties(babyVisit, data); 
//		data = (BabyVisitBO) boHelper.readAll(data, getHibernateTemplate());
		data.setFileNo(EncryptionUtils.decipher(data.getFileNo()));
//		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
//		data.setExecDistrictNum(user.getDistrictId());
		boHelper.loadDetails(data, getHibernateTemplate(), "babyVisitId", babyVisit.getId());
		return data;
	}

	public BabyVisitPrintBO getPrintInfo(BabyVisitPrintBO data) throws Exception{
		try {
			String id = data.getId();
			BabyVisitBO bo = new BabyVisitBO();
			bo.setId(id);
			BabyVisitBO d = get(bo);
			BeanUtils.copyProperties(d, data);
			String babySkin = sysUtils.getPrintBasicInfo(id,"BabySkin","babySkinId","babyVisitId");
			data.setBabySkins(babySkin);
			return data;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Map<String,Object> getPrintInfo_new(BabyVisitPrintBO data) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String id = data.getId();
			BabyVisitBO bo = new BabyVisitBO();
			bo.setId(id);
			BabyVisitBO babyVisit = get(bo);
			String babySkin = sysUtils.getPrintBasicInfo(id,"BabySkin","babySkinId","babyVisitId");
			data.setBabySkins(babySkin);
			HealthFile file = (HealthFile)getHibernateTemplate().get(HealthFile.class, babyVisit.getFileNo());
			map.put("file", file);
			PersonalInfo person = (PersonalInfo)getSession().createQuery("from PersonalInfo where fileno=?").setParameter(0,EncryptionUtils.encry(babyVisit.getFileNo())).list().get(0);
			getSession().evict(person);
			person.setIdnumber(EncryptionUtils.decipher(person.getIdnumber()));
			map.put("person", person);
			map.put("child", babyVisit);
			if(data.getInputPersonId()!=null){
				SamTaxempcode samTaxempcode =  (SamTaxempcode)getHibernateTemplate().get(SamTaxempcode.class, data.getInputPersonId());
				map.put("samTaxempcode", samTaxempcode);
				SamTaxorgcode samTaxorgcode =  (SamTaxorgcode)getHibernateTemplate().get(SamTaxorgcode.class, samTaxempcode.getOrgId());
				map.put("org", samTaxorgcode);
			}
			map.put("babyDirect", sysUtils.getPrintBasicInfo(babyVisit.getId(),"BabyDirect","babyDirectId","babyVisitId"));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<String> hasAllThese(List<String> props) {
		List<String> res = new ArrayList<String>();
		PropertyDescriptor[] ps = boHelper.getPorps();
		Set<String> propSet = new HashSet<String>();
		for (PropertyDescriptor p : ps) {
			propSet.add(p.getName());
		}
		for (String inP : props) {
			if (!propSet.contains(inP)) {
				res.add(inP);
			}
		}
		return res;
	}
	
	/**
	 * 从出生医学证明中获得相关信息
	 * @param fileNo
	 * @return
	 */
	public List<String> getRelatedInfoForChild(String fileNo){
		fileNo = EncryptionUtils.encry(fileNo);
		String hql ="Select motherName,fatherName,borthWeekly,familyAddress,motherIdCard,fatherIdCard From BirthCertificate Where fileNo = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, fileNo);
		List list = query.list();
		List<String> retVal = new ArrayList<String>();
		if(list.size() > 0){
			Object[] objs = (Object[])list.get(0);
			for(int i=0;i<objs.length;i++){
				String val = (String)objs[i];
				if (i == 4 || i == 5){
					if(val.length() == 15){
						val = "19" + val.substring(6, 12);
					}else if(val.length() == 18){
						val = val.substring(6, 14);
					}else{
						val = "";
					}
				}
				retVal.add(val);
			}
		}
		return retVal;
	}
	
}
