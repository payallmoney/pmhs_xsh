package cn.net.tongfang.web.service;

import java.sql.Timestamp;

import org.springframework.beans.BeanUtils;

import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.TcmServiceInfor;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.web.service.bo.TcmServiceInforBO;

public class ChildrenTcmMediExamService extends HealthMainService<TcmServiceInforBO> {
	public String update(TcmServiceInforBO data) throws Exception{
		TcmServiceInfor tcmServiceInfor = new TcmServiceInfor();
		BeanUtils.copyProperties(data, tcmServiceInfor); 
//		tcmServiceInfor.setInputDate(getInputDate(data.getId()));
		getHibernateTemplate().update(tcmServiceInfor);
		boHelper.deleteDetails(data, getSession(), "tcmServiceInforId", tcmServiceInfor.getId());
		boHelper.setFK(data, tcmServiceInfor.getId(), "tcmServiceInforId");
		boHelper.saveDetails(data, getHibernateTemplate());
		return tcmServiceInfor.getId();
	}
	@Override
	public String save(TcmServiceInforBO data) throws Exception {
		data.setFileNo(EncryptionUtils.encry(data.getFileNo()));
		if(data.getId() != null && !data.getId().equals("")){
			return update(data);
		}
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		String inputPersonId = user.getUsername();
		Timestamp currentDate = new java.sql.Timestamp(System.currentTimeMillis());
		
		data.setExecDistrictNum(user.getDistrictId());
		data.setInputPersonId(inputPersonId);
		data.setInputDate(currentDate);
		TcmServiceInfor tcmServiceInfor = new TcmServiceInfor();
		BeanUtils.copyProperties(data, tcmServiceInfor); 
		getHibernateTemplate().save(tcmServiceInfor);
		
		boHelper.setFK(data, tcmServiceInfor.getId(), "tcmServiceInforId");
		boHelper.saveDetails(data, getHibernateTemplate());
		return tcmServiceInfor.getId();
	}
	
	@Override
	public TcmServiceInforBO get(TcmServiceInforBO data) throws Exception {
		TcmServiceInfor tcmServiceInfor = (TcmServiceInfor)getHibernateTemplate().get(TcmServiceInfor.class,data.getId());
		BeanUtils.copyProperties(tcmServiceInfor, data); 
		data.setFileNo(EncryptionUtils.decipher(data.getFileNo()));
		boHelper.loadDetails(data, getHibernateTemplate(), "tcmServiceInforId", tcmServiceInfor.getId());
		return data;
	}
}
