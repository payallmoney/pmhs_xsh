package cn.net.tongfang.web.service;

import java.sql.Timestamp;

import org.hibernate.Query;

import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.FinishGestation;
import cn.net.tongfang.framework.security.vo.HealthFileMaternal;
import cn.net.tongfang.framework.security.vo.PregnancyRecord;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.web.service.bo.HealthFileMaternalBO;

public class HealthfileMaternalBuildService extends HealthMainService<HealthFileMaternalBO>{

	@Override
	public String save(HealthFileMaternalBO data) throws Exception {
		String fileNo = EncryptionUtils.encry(data.getFileNo());
		data.setFileNo(fileNo);
		Query query = getSession().createQuery(" From HealthFileMaternal Where fileNo = ? And gravidity = ? ");
		query.setParameter(0, fileNo);
		query.setParameter(1, data.getGravidity());
		if(query.list().size() == 0){
			data.setIdnumber(EncryptionUtils.encry(data.getIdnumber()));
			data.setName(EncryptionUtils.encry(data.getName()));
			if(data.getIsClosed() == null || data.getIsClosed().equals(""))
				data.setIsClosed("0");
			query = getSession().createQuery(" Update PersonalInfo Set bornStatus = '是' Where fileNo = ? ");
			query.setParameter(0, fileNo);
			query.executeUpdate();
			return save_(data);
		}else{
			throw new RuntimeException("此孕产妇的第" + data.getGravidity() + "次怀孕保健手册已经建立，不能重复建立。");
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
}
