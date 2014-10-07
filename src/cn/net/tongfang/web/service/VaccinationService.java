package cn.net.tongfang.web.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.net.tongfang.framework.security.bean.VacciProgram;
import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.BirthCertificate;
import cn.net.tongfang.framework.security.vo.District;
import cn.net.tongfang.framework.security.vo.HealthFile;
import cn.net.tongfang.framework.security.vo.VaccineImmune;
import cn.net.tongfang.framework.security.vo.VaccineImmuneHistoryStaticData;
import cn.net.tongfang.framework.security.vo.VaccineImmuneInfo;
import cn.net.tongfang.framework.security.vo.VaccineImmuneRules;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.framework.util.service.vo.PagingParam;
import cn.net.tongfang.framework.util.service.vo.PagingResult;
import cn.net.tongfang.web.service.bo.PrintVacciImmuneInfoBO;
import cn.net.tongfang.web.service.bo.VaccinationBO;
import cn.net.tongfang.web.service.bo.VaccineImmuneInfoBO;

public class VaccinationService extends HealthMainService<VaccinationBO> {
	private static final Logger log = Logger
			.getLogger(VaccinationService.class);

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String save(VaccinationBO data) throws Exception {
		String fileno = EncryptionUtils.encry(data.getFileNo());
		List checklist = getHibernateTemplate().find("from VaccineImmune where vfileno = '"+fileno+"'");
		if(checklist.size()>0){
			throw new Exception(""+data.getFileNo()+data.getVname()+"已建卡,不能重复建卡!");
		}
		data.setFileNo(EncryptionUtils.encry(data.getFileNo()));
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		data.setExecDistrictNum(user.getDistrictId());
		data.setInputPersonId(user.getUsername());
		if (data.getId() == null || data.getId().equals("")) {
			data.setVinputDate(new Timestamp(System.currentTimeMillis()));
		}
		
		HealthFile file = (HealthFile)getHibernateTemplate().get(HealthFile.class, data.getFileNo());
		if(file.getName().trim().equals("") && !data.getVname().trim().equals("")){
			file.setName(data.getVname().trim());
			getHibernateTemplate().update(file);
		}
		return save_(data);
	}

	@Override
	public Object get(VaccinationBO data) throws Exception {
		data = (VaccinationBO) get_(data);
		data.setFileNo(EncryptionUtils.decipher(data.getFileNo()));
		return data;
	}

	@SuppressWarnings("unchecked")
	public List<VaccineImmuneRules> getVaccineImmuneRules() {
		return getHibernateTemplate().find(" From VaccineImmuneRules ");

	}

	public List getBirthCertificateInfo(String fileNo) {
		fileNo = EncryptionUtils.encry(fileNo);
		List list = getHibernateTemplate().find(
				" From BirthCertificate Where fileNo = ? ", fileNo);
		List retList = new ArrayList();
		if (list.size() > 0) {
			retList.add((BirthCertificate) list.get(0));
			retList.add(null);
			return retList;
		} else {
			retList.add(null);
			HealthFile file = (HealthFile) getHibernateTemplate().get(
					HealthFile.class, fileNo);
			String districtNum = file.getDistrictNumber().trim();
			districtNum = districtNum.substring(0, districtNum.length() - 3);
			District dis = (District) getHibernateTemplate().get(
					District.class, districtNum);
			retList.add(dis.getParentName());
		}
		return retList;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<VacciProgram> vacciProgram(String fileNo) {
		fileNo = EncryptionUtils.encry(fileNo);
		String sql = " EXEC Proc_Vacci ? ";
		List list = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setResultSetMapping("vacciProgramResultSet")
				.setParameter(0, fileNo).list();
		return list;
	}

	public VaccineImmuneRules getVaccineImmuneRules(String col, Integer row,
			Integer isSpecail) {
		String hql = " From VaccineImmuneRules Where vaccineName = ? And rowNumber = ? ";
		if (isSpecail.equals(1)) {
			hql = " From VaccineImmuneRules Where vaccineName = ? ";
		}
		List params = new ArrayList();
		params.add(col);
		if (!isSpecail.equals(1)) {
			params.add(row);
		}
		List list = getHibernateTemplate().find(hql,params.toArray());
		if (list.size() > 0) {
			return (VaccineImmuneRules) list.get(0);
		}
		return null;
	}
	
	public VaccineImmuneInfo getVaccineImmuneInfoObj(VaccineImmuneInfoBO vacciInfo){
		VaccineImmuneInfo vo = (VaccineImmuneInfo)getHibernateTemplate().get(VaccineImmuneInfo.class, vacciInfo.getId());
		getHibernateTemplate().evict(vo);
		return vo;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public VaccineImmuneInfo saveVaccineImmuneInfo(VaccineImmuneInfoBO vacciInfo)throws Exception {
//		if (vacciInfo.getLimitDate() != null) {
//			if (vacciInfo.getVaccinationDate().compareTo(
//					vacciInfo.getLimitDate()) > 0) {
//				throw new RuntimeException("请重新填写接种日期");
//			}
//		}
		if (vacciInfo.getVaccinationDate().compareTo(vacciInfo.getBirthday()) >= 0) {
			if (vacciInfo.getId() != null) {
				TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
						.currentOperator();
//				if (cn.net.tongfang.framework.security.SecurityManager.isValidUser(user.getUsername(),vacciInfo.getInputPersonId())) {
				if (cn.net.tongfang.framework.security.SecurityManager.isValidUser(user.getUsername(),getSession())) {
					VaccineImmuneInfo info = new VaccineImmuneInfo();
					BeanUtils.copyProperties(vacciInfo, info);
					getHibernateTemplate().update(info);
					log.debug("VaccineImmuneInfo Updated Successed...");
					return info;
				} else {
					throw new Exception("你无权限修改其他人录入的接种信息,只能登录用户为"+vacciInfo.getInputPersonId()+"的操作员进行修改!");
				}
			}
			String fileNo = EncryptionUtils.encry(vacciInfo.getFileNo());
			//检测此疫苗是否已接种
			String sql = " From VaccineImmuneInfo Where fileNo = ? And colNum = ? And rowNumber = ? And number = ? ";
			List l = getHibernateTemplate().find(sql,new Object[]{fileNo,vacciInfo.getColNum(),vacciInfo.getRowNumber(),vacciInfo.getNumber()});
			if(l.size() > 0){
				return null;
			}else{
				vacciInfo.setFileNo(fileNo);
				if (vacciInfo.getIsSpecail().equals(1)) {
					String hql = " From VaccineImmuneInfo Where fileNo = ? And colNum = ? ";
					List list = getHibernateTemplate().find(hql,new Object[]{fileNo,vacciInfo.getColNum()});
					if (list.size() == 2) {
						throw new Exception("规划内A群流脑疫苗接种完毕");
					} else if (list.size() == 1) {
						vacciInfo.setNumber(2);
					} else if (list.size() == 0) {
						vacciInfo.setNumber(1);
					}
				}
				vacciInfo.setIsPlan(0);// 计划内
				VaccineImmuneInfo info = new VaccineImmuneInfo();
				BeanUtils.copyProperties(vacciInfo, info);
				TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
						.currentOperator();
				info.setInputPersonId(user.getUsername());
				info.setInputDate(new Timestamp(System.currentTimeMillis()));
				getHibernateTemplate().save(info);
				return info;
			}
		} else {
			throw new Exception("请重新填写接种日期");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveVaccineImmuneInfoUnPlaned(VaccineImmuneInfoBO vacciInfo)throws Exception{
		if (vacciInfo.getVaccinationDate().compareTo(vacciInfo.getBirthday()) >= 0) {
			if (vacciInfo.getId() != null && !vacciInfo.getId().equals("")) {
				TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
						.currentOperator();
//				if (cn.net.tongfang.framework.security.SecurityManager.isValidUser(user.getUsername(),vacciInfo.getInputPersonId())) {
				if (cn.net.tongfang.framework.security.SecurityManager.isValidUser(user.getUsername(),getSession())) {
					VaccineImmuneInfo info = new VaccineImmuneInfo();
					BeanUtils.copyProperties(vacciInfo, info);
					info.setFileNo(info.getFileNo());
					getHibernateTemplate().update(info);
					log.debug("VaccineImmuneInfo Updated Successed...");
				} else {
					throw new Exception("你无权限修改其他人录入的接种信息,只能登录用户为"+vacciInfo.getInputPersonId()+"的操作员进行修改!");
				}
				return;
			}
			
			String fileNo = EncryptionUtils.encry(vacciInfo.getFileNo());
			vacciInfo.setFileNo(fileNo);
			vacciInfo.setIsPlan(1);// 计划外
			VaccineImmuneInfo info = new VaccineImmuneInfo();
			BeanUtils.copyProperties(vacciInfo, info);
			TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
					.currentOperator();
			info.setInputPersonId(user.getUsername());
			info.setInputDate(new Timestamp(System.currentTimeMillis()));
			getHibernateTemplate().save(info);
		} else {
			throw new Exception("请重新填写接种日期");
		}
	}
	
	public List getVaccineImmuneInfo(String col, Integer row, String fileNo,
			Integer isSpecail) {
		fileNo = EncryptionUtils.encry(fileNo);
		String hql = " From VaccineImmuneInfo A,VaccineImmuneRules B Where A.fileNo = ? And A.colNum = ? And A.rowNumber = ? "
				+ " And A.colNum = B.vaccineName AND A.rowNumber = B.rowNumber ";
		if (isSpecail.equals(1)) {
			hql = " From VaccineImmuneInfo A Where A.fileNo = ? And A.colNum = ? And A.rowNumber = ? ";
		}
		List list = getHibernateTemplate().find(hql,new Object[]{fileNo,col,row});
		List retList = new ArrayList();
		if (list.size() > 0) {
			if (isSpecail.equals(1)) {
				VaccineImmuneInfo vacciInfo = (VaccineImmuneInfo) list.get(0);
				retList.add(vacciInfo);
				return retList;
			} else {
				Object[] objs = (Object[]) list.get(0);
				VaccineImmuneInfo vacciInfo = (VaccineImmuneInfo) objs[0];
				VaccineImmuneRules vacciRules = (VaccineImmuneRules) objs[1];
				retList.add(vacciInfo);
				retList.add(vacciRules);
				return retList;
			}

		}
		return null;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public PagingResult<VaccineImmuneRules> queryVacciRulesInfo(PagingParam pp) {
		String hql = " From VaccineImmuneRules Where id in (Select min(id) From VaccineImmuneRules Where rules = 0 Group By vaccineRemark) ";
		String countHql = " Select Count(*) " + hql;
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(countHql);
		int totalSize = ((Long) query.uniqueResult()).intValue();
		query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql + " Order By id ");
		if (pp == null)
			pp = new PagingParam();
		query.setFirstResult(pp.getStart()).setMaxResults(pp.getLimit());
		List list = query.list();
		PagingResult<VaccineImmuneRules> result = new PagingResult<VaccineImmuneRules>(
				totalSize, list);
		return result;
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public PagingResult<VaccineImmuneInfo> queryVacciInfo(String fileNo,Integer type,PagingParam pp) {
		String hql = " From VaccineImmuneInfo Where fileNo = ? And isPlan = ? ";
		String countHql = " Select Count(*) " + hql;
		fileNo = EncryptionUtils.encry(fileNo);
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(countHql);
		query.setParameter(0, fileNo);
		query.setParameter(1, type);
		int totalSize = ((Long) query.uniqueResult()).intValue();
		query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql + " Order By colNum,number ");
		query.setParameter(0, fileNo);
		query.setParameter(1, type);
		if (pp == null)
			pp = new PagingParam();
		query.setFirstResult(pp.getStart()).setMaxResults(pp.getLimit());
		List list = query.list();
		PagingResult<VaccineImmuneInfo> result = new PagingResult<VaccineImmuneInfo>(
				totalSize, list);
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeVacciInfo(List<VaccineImmuneInfo> vacciInfos) throws Exception{
		String hql = " From VaccineImmuneHistoryStaticData Where fileNo = ? And colNum = ? And rowNum = ? ";
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		for(VaccineImmuneInfo info : vacciInfos){
//			if(cn.net.tongfang.framework.security.SecurityManager.isValidUser(user.getUsername(),info.getInputPersonId())){
			if(cn.net.tongfang.framework.security.SecurityManager.isValidUser(user.getUsername(),getSession())){
				Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
				query.setParameter(0, info.getFileNo());
				query.setParameter(1, info.getColNum());
				query.setParameter(2, info.getRowNumber());
				List list = query.list();
				if(list.size() > 0){
					VaccineImmuneHistoryStaticData hisdata = (VaccineImmuneHistoryStaticData)list.get(0);
					hisdata.setVaccinationDate(null);
					getHibernateTemplate().update(hisdata);
				}
				VaccineImmuneInfo vacci = (VaccineImmuneInfo)getHibernateTemplate().get(VaccineImmuneInfo.class, info.getId());
				getHibernateTemplate().delete(vacci);
			} else {
				throw new Exception("你无权限修改其他人录入的接种信息,只能登录用户为"+info.getInputPersonId()+"的操作员进行修改!");
			}
		}
	}
	
	public VaccineImmune getVaccineImmune(String id){
		VaccineImmune vacci = (VaccineImmune)getHibernateTemplate().get(VaccineImmune.class, id);
		vacci.setFileNo(EncryptionUtils.decipher(vacci.getFileNo()));
		return vacci;
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public PagingResult<VaccineImmuneInfo> getPrintVacciImmuneInfo(PrintVacciImmuneInfoBO info){
		String hql = " From VaccineImmuneInfo vii,BasicInformation bi Where vii.colNum = bi.id And bi.printPage = ? And vii.fileNo = ? Order By bi.id,vii.number ASC ";
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter(0, info.getPrintPage());
		query.setParameter(1, EncryptionUtils.encry(info.getFileNo()));
		List list = query.list();
		List retVal = new ArrayList();
		if(list.size() > 0){
			for(Object objs : list){
				Object[] obj = (Object[])objs;
				retVal.add(obj[0]);
			}
		}
		PagingResult<VaccineImmuneInfo> result = new PagingResult<VaccineImmuneInfo>(
				list.size(), retVal);
		return result;
	}
	
	public void printSuccess(List<String> ids){
		for(String id : ids){
			VaccineImmuneInfo info = (VaccineImmuneInfo)getHibernateTemplate().get(VaccineImmuneInfo.class, id);
			info.setIsPrint(1);//1代表打印成功
			getHibernateTemplate().update(info);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeVaccineImmune(String id){
		String hql = " From VaccineImmune Where id = ?";
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter(0, id);
		List list = query.list();
		if(list.size() > 0){
			VaccineImmune vacci = (VaccineImmune)list.get(0);
			String fileNo = vacci.getFileNo();
			hql = " Delete From VaccineImmuneHistoryStaticData Where fileNo = ?";
			query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
			query.setParameter(0, fileNo);
			query.executeUpdate();
			hql = " Delete From VaccineImmuneInfo Where fileNo = ?";
			query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
			query.setParameter(0, fileNo);
			query.executeUpdate();
			getHibernateTemplate().delete(vacci);
		}
	}
}
