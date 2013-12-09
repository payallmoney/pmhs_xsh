package cn.net.tongfang.web.service;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.HealthFile;
import cn.net.tongfang.framework.security.vo.HealthFileLoginOff;
import cn.net.tongfang.framework.security.vo.HealthFileLoginOffExit;
import cn.net.tongfang.framework.security.vo.HealthFileTransfer;
import cn.net.tongfang.framework.security.vo.HealthFileTransferExit;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.web.service.bo.LoginOffHealthFileBO;
import cn.net.tongfang.web.service.bo.TransferHealthFileBO;
import cn.net.tongfang.web.service.bo.TransferHealthfileInfo;
import cn.net.tongfang.web.util.FileNoGen;

public class LoginOffHealthFileService extends HibernateDaoSupport{
	private FileNoGen fileNoGen;
	
	public void setFileNoGen(FileNoGen fileNoGen) {
		this.fileNoGen = fileNoGen;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void loginOffService(LoginOffHealthFileBO loginoff){
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		List<String> fileNos = loginoff.getFileNos();
		for(String fileNo : fileNos){
			HealthFileLoginOff hloff = new HealthFileLoginOff();
			BeanUtils.copyProperties(loginoff, hloff); 
			String fNo = EncryptionUtils.encry(fileNo);
			hloff.setFileNo(fNo);
			hloff.setInputDate(currentTime);
			hloff.setInputPersonId(user.getUsername());
			hloff.setLoginOffOpt(user.getTaxempname());
			hloff.setLoginOffOptOrg(user.getOrg().getName());
			getHibernateTemplate().save(hloff);
			HealthFile file = (HealthFile)getHibernateTemplate().get(HealthFile.class, fNo);
			file.setStatus(44);//44代表注销的档案
			getHibernateTemplate().update(file);
		}
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void transferService(TransferHealthFileBO transfer){
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		for(TransferHealthfileInfo transferHealthfileInfo : transfer.getFileNos()){
			HealthFileTransfer transferFile = new HealthFileTransfer();
			BeanUtils.copyProperties(transferHealthfileInfo, transferFile); 
			String fNo = EncryptionUtils.encry(transferFile.getFromFileNo());
			transferFile.setFromFileNo(fNo);
			transferFile.setIsSure("0");//0处于未审核状态
			transferFile.setToDistrictNumber(transfer.getDistrictNumber());
			transferFile.setTransferReason(transfer.getTransferReason());
			transferFile.setTransferTime(transfer.getTransferTime());
			transferFile.setFromOpt(user.getUsername());
			transferFile.setFromShowOptName(user.getTaxempname());
			transferFile.setFromOptOrg(user.getOrg().getName());
			transferFile.setInputDate(new Timestamp(System.currentTimeMillis()));
			getHibernateTemplate().save(transferFile);
			HealthFile file = (HealthFile)getHibernateTemplate().get(HealthFile.class, fNo);
			file.setStatus(45);//45代表档案处于挂起状态
			getHibernateTemplate().update(file);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void transferExitHealthfile(HealthFileTransferExit exithealthfile){
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		exithealthfile.setInputDate(new Timestamp(System.currentTimeMillis()));
		exithealthfile.setInputPersonId(user.getUsername());
		HealthFileTransfer transferhealthfile = (HealthFileTransfer)getHibernateTemplate().get(HealthFileTransfer.class, exithealthfile.getHealthFileTransferId());
		transferhealthfile.setIsSure("2");//2处于退回状态
		HealthFile file = (HealthFile)getHibernateTemplate().get(HealthFile.class, transferhealthfile.getFromFileNo());
		file.setStatus(0);//0代表档案处于正常状态
		getHibernateTemplate().save(exithealthfile);
		getHibernateTemplate().update(transferhealthfile);
		getHibernateTemplate().update(file);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void loginOffExitHealthfile(HealthFileLoginOffExit exithealthfile){
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		exithealthfile.setInputDate(new Timestamp(System.currentTimeMillis()));
		exithealthfile.setInputPersonId(user.getUsername());
		HealthFileLoginOff loginOffhealthfile = (HealthFileLoginOff)getHibernateTemplate().get(HealthFileLoginOff.class, exithealthfile.getHealthFileLoginOffId());
		HealthFile file = (HealthFile)getHibernateTemplate().get(HealthFile.class, loginOffhealthfile.getFileNo());
		file.setStatus(0);//0代表档案处于正常状态
		getHibernateTemplate().save(exithealthfile);
		getHibernateTemplate().delete(loginOffhealthfile);
		getHibernateTemplate().update(file);
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public void sureTransferService(HealthFileTransfer transferModifyInfo)throws Exception{
		String newfileNo = fileNoGen.getNextFileNo(transferModifyInfo.getToDistrictNumber());
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		String hql = " Exec Proc_HealthFile_Transfer ?,?,?,?,?,?,?,? ";
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql);
		query.setParameter(0, transferModifyInfo.getId());
		query.setParameter(1, user.getUsername());
		query.setParameter(2, EncryptionUtils.encry(newfileNo));
		query.setParameter(3, transferModifyInfo.getAddress());
		query.setParameter(4, transferModifyInfo.getResidenceAddress());
		query.setParameter(5, transferModifyInfo.getFromBuildDoctor());
		query.setParameter(6, transferModifyInfo.getFromBuildUnit());
		query.setParameter(7, transferModifyInfo.getFromBuildPerson());
		query.executeUpdate();
	}
}
