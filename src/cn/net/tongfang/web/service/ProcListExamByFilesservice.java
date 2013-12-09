package cn.net.tongfang.web.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.framework.util.service.ModuleMgr;
import cn.net.tongfang.framework.util.service.vo.PagingParam;
import cn.net.tongfang.framework.util.service.vo.PagingResult;
import cn.net.tongfang.web.service.bo.ListExamBeanBO;

public class ProcListExamByFilesservice extends HibernateDaoSupport {
	private ModuleMgr userMenuTreeService;
	private VaccinationService vaccinationService;
	public void setUserMenuTreeService(ModuleMgr userMenuTreeService) {
		this.userMenuTreeService = userMenuTreeService;
	}
	
	public void setVaccinationService(VaccinationService vaccinationService) {
		this.vaccinationService = vaccinationService;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public PagingResult<ListExamBeanBO> getListExam(ListExamBeanBO listExamBeanBo,PagingParam pp){
		List retListVal = new ArrayList();
		if(listExamBeanBo.getFileNo() == null)
			listExamBeanBo.setFileNo("");

		String[] fileNoArray = listExamBeanBo.getFileNo().split(",");
		String fileNos = "";
		
		for(String fileNo : fileNoArray){
			fileNos += EncryptionUtils.encry(fileNo) + ",";
		}
		if(!fileNos.equals("")){
			fileNos = fileNos.substring(0, fileNos.length() - 1);
		}
		String sql = " Exec Proc_ListExamInfoByFile ? ";
		List list = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setResultSetMapping("procListExamByFiles")
				.setParameter(0, fileNos).list();
		for(Object obj : list){
			Object[] bean = (Object[])obj;
			ListExamBeanBO listExam = new ListExamBeanBO(bean[0].toString().trim(),
					bean[1].toString().trim(), bean[2].toString().trim(),
					bean[3].toString().trim(), bean[4].toString().trim(),
					bean[5].toString().trim(),bean[6].toString().trim(),
					bean[7].toString().trim());
			listExam.setFileNo(EncryptionUtils.decipher(listExam.getFileNo()));
			listExam.setName(EncryptionUtils.decipher(listExam.getName()));
//			bean[1] = EncryptionUtils.decipher(bean[1].toString().trim());
//			bean[2] = EncryptionUtils.decipher(bean[2].toString().trim());
			retListVal.add(listExam);
		}
		PagingResult<ListExamBeanBO> result = new PagingResult<ListExamBeanBO>(
				retListVal.size(), retListVal);
		return result;
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public void delListExam(List<ListExamBeanBO> listExamBo)throws Exception{
		for(ListExamBeanBO bean : listExamBo){
			List ids = new ArrayList();
			ids.add(bean.getId());
			if(bean.getFlag().equals("1")){
				userMenuTreeService.removeMedicalExamRecords(ids);
			}else if(bean.getFlag().equals("2")){
				userMenuTreeService.removeBabyVisitRecords(ids);
			}else if(bean.getFlag().equals("3")){
				userMenuTreeService.removeChildExamRecords(ids);
			}else if(bean.getFlag().equals("4")){
				userMenuTreeService.removeChildExamRecords(ids);
			}else if(bean.getFlag().equals("5")){
				userMenuTreeService.removeChildExam36Records(ids);
			}else if(bean.getFlag().equals("6")){
				userMenuTreeService.removeFirstVisitRecords(ids);
			}else if(bean.getFlag().equals("7")){
				userMenuTreeService.removeVisitBeforeBornRecords(ids);
			}else if(bean.getFlag().equals("8")){
				userMenuTreeService.removeVisitAfterBornRecords(ids);
			}else if(bean.getFlag().equals("9")){
				userMenuTreeService.removeVisitAfterBornRecords(ids);
			}else if(bean.getFlag().equals("10")){
				userMenuTreeService.removeFuriousVisitRecords(ids);
			}else if(bean.getFlag().equals("11")){
				userMenuTreeService.removeFuriousInfoRecords(ids);
			}else if(bean.getFlag().equals("12")){
				userMenuTreeService.removeHypVisitRecords(ids);
			}else if(bean.getFlag().equals("13")){
				userMenuTreeService.removeDiabVisitRecords(ids);
			}else if(bean.getFlag().equals("14")){
				userMenuTreeService.removeHealthfilesAlreadyBuildChild(ids);
				Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(" Exec Proc_ExecData ?,?,? ");
				query.setParameter(0, "0");
				query.setParameter(1, bean.getId());
				query.setParameter(2, "1");
				query.executeUpdate();
			}else if(bean.getFlag().equals("15")){
				userMenuTreeService.removeHealthfilesAlreadyBuild(ids);
				Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(" Exec Proc_ExecData ?,?,? ");
				query.setParameter(0, "1");
				query.setParameter(1, bean.getId());
				query.setParameter(2, "1");
				query.executeUpdate();
			}else if(bean.getFlag().equals("16")){
				vaccinationService.removeVaccineImmune(bean.getId());
				Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(" Exec Proc_ExecData ?,?,? ");
				query.setParameter(0, "2");
				query.setParameter(1, bean.getId());
				query.setParameter(2, "1");
				query.executeUpdate();
			}else if(bean.getFlag().equals("17")){
				vaccinationService.removeVacciInfo(ids);
			}
		}
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public void updateListExam(List<ListExamBeanBO> listExamBo){
		for(ListExamBeanBO bean : listExamBo){
			String hql = "";
			String fileNo = EncryptionUtils.encry(bean.getFileNo());
			if(bean.getFlag().equals("1")){
				hql = " Update MedicalExam Set fileNo = ? Where id = ? ";
			}else if(bean.getFlag().equals("2")){
				hql = " Update BabyVisit Set fileNo = ? Where id = ? ";
			}else if(bean.getFlag().equals("3")){
				hql = " Update ChildrenMediExam Set fileNo = ? Where id = ? And dataType = 0 ";
			}else if(bean.getFlag().equals("4")){
				hql = " Update ChildrenMediExam Set fileNo = ? Where id = ? And dataType = 1 ";
			}else if(bean.getFlag().equals("5")){
				hql = " Update ChildrenMediExam36 Set fileNo = ? Where id = ? ";
			}else if(bean.getFlag().equals("6")){
				hql = " Update FirstVistBeforeBorn Set fileNo = ? Where id = ? ";
			}else if(bean.getFlag().equals("7")){
				hql = " Update VisitBeforeBorn Set fileNo = ? Where id = ? ";
			}else if(bean.getFlag().equals("8")){
				hql = " Update VisitAfterBorn Set fileNo = ? Where id = ? And recordType = 0 ";
			}else if(bean.getFlag().equals("9")){
				hql = " Update VisitAfterBorn Set fileNo = ? Where id = ? And recordType = 1 ";
			}else if(bean.getFlag().equals("10")){
				hql = " Update FuriousVisit Set fileNo = ? Where id = ? ";
			}else if(bean.getFlag().equals("11")){
				hql = " Update FuriousInfo Set fileNo = ? Where id = ? ";
			}else if(bean.getFlag().equals("12")){
				hql = " Update HypertensionVisit Set fileNo = ? Where id = ? ";
			}else if(bean.getFlag().equals("13")){
				hql = " Update DiabetesVisit Set fileNo = ? Where id = ? ";
			}else if(bean.getFlag().equals("14")){
				hql = " Update HealthFileChildren Set fileNo = ? Where id = ? ";
				Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(" Exec Proc_ExecData ?,?,?,? ");
				query.setParameter(0, "0");
				query.setParameter(1, bean.getId());
				query.setParameter(2, "0");
				query.setParameter(3, fileNo);
				query.executeUpdate();
			}else if(bean.getFlag().equals("15")){
				hql = " Update HealthFileMaternal Set fileNo = ? Where id = ? ";
				Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(" Exec Proc_ExecData ?,?,?,? ");
				query.setParameter(0, "1");
				query.setParameter(1, bean.getId());
				query.setParameter(2, "0");
				query.setParameter(3, fileNo);
				query.executeUpdate();
			}else if(bean.getFlag().equals("16")){
				hql = " Update VaccineImmune Set fileNo = ? Where id = ? ";
				Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(" Exec Proc_ExecData ?,?,?,? ");
				query.setParameter(0, "2");
				query.setParameter(1, bean.getId());
				query.setParameter(2, "0");
				query.setParameter(3, fileNo);
				query.executeUpdate();
			}else if(bean.getFlag().equals("17")){
				hql = " Update VaccineImmuneInfo Set fileNo = ? Where id = ? ";
			}
			Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
			query.setParameter(0, fileNo);
			query.setParameter(1, bean.getId());
			query.executeUpdate();
		}
	}
}
