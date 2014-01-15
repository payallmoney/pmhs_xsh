package cn.net.tongfang.web.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.BeanUtils;

import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.BasicInformation;
import cn.net.tongfang.framework.security.vo.FinishGestation;
import cn.net.tongfang.framework.security.vo.HealthFile;
import cn.net.tongfang.framework.security.vo.HealthFileMaternal;
import cn.net.tongfang.framework.security.vo.PersonalInfo;
import cn.net.tongfang.framework.security.vo.PregnancyRecord;
import cn.net.tongfang.framework.security.vo.SamTaxempcode;
import cn.net.tongfang.framework.security.vo.SamTaxorgcode;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.web.service.bo.HealthFileMaternalBO;
import cn.net.tongfang.web.service.bo.VisitAfterBornBO;

public class HealthfileMaternalBuildService extends HealthMainService<HealthFileMaternalBO>{

	@Override
	public String save(HealthFileMaternalBO data) throws Exception {
		String fileNo = EncryptionUtils.encry(data.getFileNo());
		data.setFileNo(fileNo);
		Query query = null;
		boolean flag = false;
		if(data.getId() == null){
			query = getSession().createQuery(" From HealthFileMaternal Where fileNo = ? And gravidity = ? ");
			query.setParameter(0, fileNo);
			query.setParameter(1, data.getGravidity());
		}
		
		if(query == null || query.list().size() == 0 ){
			data.setIdnumber(EncryptionUtils.encry(data.getIdnumber()));
			data.setName(EncryptionUtils.encry(data.getName()));
			if(data.getIsClosed() == null || data.getIsClosed().equals(""))
				data.setIsClosed("0");
//			query = getSession().createQuery(" Update PersonalInfo Set bornStatus = '是' Where fileNo = ? ");
			query = getSession().createQuery(" From HealthFile A,PersonalInfo B Where A.fileNo = B.fileNo And A.fileNo = ? ");
			query.setParameter(0, fileNo);
			List list = query.list();
			if(list.size() == 1){
				Object[] objs = (Object[])list.get(0);
				HealthFile file = (HealthFile)objs[0];
				PersonalInfo person = (PersonalInfo)objs[1];
				String id = person.getId();
				Timestamp inputdate = file.getInputDate();
				String createperson = file.getInputPersonId();
				BeanUtils.copyProperties(data, file); 
				BeanUtils.copyProperties(data, person); 
				person.setId(id);
				file.setInputDate(inputdate);
				file.setInputPersonId(createperson);
				person.setInputDate(inputdate);
				person.setBornStatus("是");
				person.setInputPersonId(createperson);
				String barcode = "";
				if(file.getBarCode() != null)
					barcode = file.getBarCode();
				if(data.getBarCode() == null)
					data.setBarCode("");
				file.setBarCode(data.getBarCode() + "," + barcode);
				getHibernateTemplate().update(file);
				getHibernateTemplate().update(person);
			}
			return save_(data);
		}else{
			throw new Exception("此孕产妇的第" + data.getGravidity() + "次怀孕保健手册已经建立，不能重复建立。");
		}
//		return "";
	}

	@Override
	public Object get(HealthFileMaternalBO data) throws Exception {
		data = (HealthFileMaternalBO)get_(data);
		data.setFileNo(EncryptionUtils.decipher(data.getFileNo()));
		data.setName(EncryptionUtils.decipher(data.getName()));
		data.setIdnumber(EncryptionUtils.decipher(data.getIdnumber()));
		return data;
	}

	public void finishGestation(FinishGestation gestation){
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		gestation.setInputPersonId(user.getUsername());
		gestation.setInputDate(new Timestamp(System.currentTimeMillis()));
		getHibernateTemplate().save(gestation);
		HealthFileMaternal maternal = (HealthFileMaternal)getHibernateTemplate().get(HealthFileMaternal.class, gestation.getHealthFileMaternalId());
		maternal.setIsClosed("2");//2终止妊娠
		maternal.setClosedDate(gestation.getFinishDate());//结案时间
		getHibernateTemplate().update(maternal);
		String fileNo = maternal.getFileNo();
		Query query = getSession().createQuery(" Update PersonalInfo Set bornStatus = '否' Where fileNo = ? ");
		query.setParameter(0, fileNo);
		query.executeUpdate();
	}
	
	public void PregnancyRecordService(PregnancyRecord pregnancy){
		if(pregnancy.getId() != null && !pregnancy.getId().equals("")){
			getHibernateTemplate().update(pregnancy);
		}
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		pregnancy.setInputPersonId(user.getUsername());
		pregnancy.setInputDate(new Timestamp(System.currentTimeMillis()));
		getHibernateTemplate().save(pregnancy);
	}
	
	public Map<String,Object> getPrintInfo_new(HealthFileMaternalBO data) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		data = (HealthFileMaternalBO)get_(data);
		HealthFile file = (HealthFile)getHibernateTemplate().get(HealthFile.class, data.getFileNo());
		getHibernateTemplate().evict(file);
		getHibernateTemplate().evict(file.getPersonalInfo());
		file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
		file.setName(EncryptionUtils.decipher(file.getName()));
		file.getPersonalInfo().setIdnumber(EncryptionUtils.decipher(file.getPersonalInfo().getIdnumber()));
		data.setFileNo(EncryptionUtils.decipher(data.getFileNo()));
		data.setName(EncryptionUtils.decipher(data.getName()));
		data.setIdnumber(EncryptionUtils.decipher(data.getIdnumber()));
		map.put("file", file);
		map.put("maternal", data);
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
