package cn.net.tongfang.web.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.BasicInformation;
import cn.net.tongfang.framework.security.vo.FinishGestation;
import cn.net.tongfang.framework.security.vo.HealthFile;
import cn.net.tongfang.framework.security.vo.HealthFileMaternal;
import cn.net.tongfang.framework.security.vo.PersonalInfo;
import cn.net.tongfang.framework.security.vo.PregnancyRecord;
import cn.net.tongfang.framework.util.BusiUtils;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.web.service.bo.HealthFileMaternalBO;

public class HealthfileMaternalBuildService extends HealthMainService<HealthFileMaternalBO>{

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String save(HealthFileMaternalBO data) throws Exception {
		String fileNo = EncryptionUtils.encry(data.getFileNo());
		data.setFileNo(fileNo);
		List query = null;
		boolean flag = false;
		if(data.getId() == null){
			query = getHibernateTemplate().find(" From HealthFileMaternal Where fileNo = ? And gravidity = ? ",new Object[]{fileNo,data.getGravidity()});
		}
		
		if(query == null || query.size() == 0 ){
			data.setIdnumber(EncryptionUtils.encry(data.getIdnumber()));
			data.setName(EncryptionUtils.encry(data.getName()));
			if(data.getIsClosed() == null || data.getIsClosed().equals(""))
				data.setIsClosed("0");
//			query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(" Update PersonalInfo Set bornStatus = '是' Where fileNo = ? ");
			HealthFile file = (HealthFile)getHibernateTemplate().get(HealthFile.class, fileNo);
			if( file!=null){
				PersonalInfo person = file.getPersonalInfo();
				String id = person.getId();
				Timestamp inputdate = file.getInputDate();
				BusiUtils.copyProperties(data, file); 
				BusiUtils.copyProperties(data, person); 
				person.setId(id);
				file.setInputDate(inputdate);
				person.setInputDate(inputdate);
				person.setBornStatus("是");
				String barcode = "";
				file.setBarCode(data.getBarCode());
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

	@Transactional(propagation = Propagation.REQUIRED)
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
		getHibernateTemplate().bulkUpdate(" Update PersonalInfo Set bornStatus = '否' Where fileNo = ? ", fileNo);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
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
		List list = getHibernateTemplate().find(hql,id);
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
