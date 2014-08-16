package cn.net.tongfang.web.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.StringUtils;

import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.BarCode;
import cn.net.tongfang.framework.security.vo.BarCodeHistory;
import cn.net.tongfang.framework.security.vo.BasicInformation;
import cn.net.tongfang.framework.security.vo.BirthCertifiDestroyReason;
import cn.net.tongfang.framework.security.vo.BirthCertificate;
import cn.net.tongfang.framework.security.vo.BirthCertificateBefore2012;
import cn.net.tongfang.framework.security.vo.BirthCertificateChange;
import cn.net.tongfang.framework.security.vo.BirthCertificateCondition;
import cn.net.tongfang.framework.security.vo.BirthCertificateOrg;
import cn.net.tongfang.framework.security.vo.BirthCertificateRemarks;
import cn.net.tongfang.framework.security.vo.District;
import cn.net.tongfang.framework.security.vo.HealthFile;
import cn.net.tongfang.framework.security.vo.PersonalInfo;
import cn.net.tongfang.framework.security.vo.SamTaxempcodeRole;
import cn.net.tongfang.framework.util.CommonConvertUtils;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.framework.util.SystemInformationUtils;
import cn.net.tongfang.framework.util.service.ModuleMgr;
import cn.net.tongfang.framework.util.service.vo.PagingParam;
import cn.net.tongfang.framework.util.service.vo.PagingResult;
import cn.net.tongfang.web.service.bo.BarCodeBo;
import cn.net.tongfang.web.service.bo.BirthCertificateBO;
import cn.net.tongfang.web.service.bo.CertificateBO;
import cn.net.tongfang.web.service.bo.CertificateChangedParams;
import cn.net.tongfang.web.service.bo.QueryCertificateBO;
import cn.net.tongfang.web.util.FileNoGen;

/**
 * @author Jackstraw
 * 
 */
public class BirthCertificateMsgService extends HibernateDaoSupport {
	private static final Integer INIT_REMARKS = 0;
	private static final Integer DELETE_REMARKS = 1;
	private static final Integer DISTRIBUTE_REMARKS = 2;
	private static final Integer UNDISTRIBUTE_REMARKS = 3;
	
	private static final Integer DESTROY_REASON_TYPE = 0;//出生证明作废
	private static final Integer UNDESTROY_REASON_TYPE = 1;//出生证明作废还原
	
	
	private static final String ERROR_CODE_MIN_MAX = "-1";//流水号范围最小值大于最大值
	private static final String ERROR_CODE_OVERRIDE = "-2";//流水号长度大于系统设置的长度
	
	private static final Integer BIRTH_UNUSED = 1;//未使用的出生证明
	private static final Integer BIRTH_USED = 2;//已使用的出生证明
	private static final Integer EFFECTIVED_DESTROIED = 3;//已作废的出生证明
	
	private static final Integer BIRTH_CERTIFICATE_LENGTH = 10;//出生证明长度
	
	private static final Integer INIT_OPTION = 0;
	private static final Integer DELETE_OPTION = -1;
	private SystemInformationUtils sysInfoUtils;
	private FileNoGen fileNoGen;
	private ModuleMgr moduleMgr;
	public void setModuleMgr(ModuleMgr moduleMgr) {
		this.moduleMgr = moduleMgr;
	}
	public void setFileNoGen(FileNoGen fileNoGen) {
		this.fileNoGen = fileNoGen;
	}

	public void setSysInfoUtils(SystemInformationUtils sysInfoUtils) {
		this.sysInfoUtils = sysInfoUtils;
	}
	
	private String fillBirhCertifiZero(String prex,long val,Integer len){
		Integer realLen = prex.length() + String.valueOf(val).length();
		if(realLen.equals(len)){
			return prex + String.valueOf(val);
		}else{
			int subLen = len - realLen;
			if(subLen > 0){
				while(subLen > 0){
					prex = prex + "0";
					subLen--;
				}
				return prex + String.valueOf(val);
			}
		}
		return "";
	}
	
	private CertificateBO initDeleteOption(String json,Integer optionType){
		String[] propeties = json.split(",");
		String prex = getSecondProp(propeties[0]);
		String birthCertificateId = "";
		String otherCondition = getSecondProp(propeties[propeties.length - 1]);
		String remarkId = saveBirthCertificateRemarks(prex, otherCondition,INIT_REMARKS,-1);
		int successNum = 0;
		int failureNum = 0;
		String failureCertificate = "";
		for (int i = 1; i < propeties.length - 1; i = i + 2) {
			String tmpStartCondition = getSecondProp(propeties[i]);
			String tmpEndCondition = getSecondProp(propeties[i + 1]);
			if (tmpStartCondition.equals("") || tmpEndCondition.equals(""))
				continue;
			long startCondition = Long.valueOf(tmpStartCondition);
			long endCondition = Long.valueOf(tmpEndCondition);
			for (long j = startCondition; j <= endCondition; j++) {
				birthCertificateId = fillBirhCertifiZero(prex,j,BIRTH_CERTIFICATE_LENGTH);
				if(optionType == 0){
					saveCertificate(birthCertificateId);
					successNum++;
				}else if(optionType == -1){
					if(deteleCertificate(birthCertificateId)){
						failureNum++;
					}else{
						failureCertificate += birthCertificateId + ",";
					}
				}
			}
			saveBirthCertificateConditions(remarkId, startCondition, endCondition);
		}
		if (!otherCondition.equals("")) {
			String[] other = otherCondition.split(";");
			for (int i = 0; i < other.length; i++) {
				birthCertificateId = prex + other[i];
				if(optionType == 0){
					saveCertificate(birthCertificateId);
					successNum++;
				}else if(optionType == -1){
					if(deteleCertificate(birthCertificateId)){
						failureNum++;
					}else{
						failureCertificate += birthCertificateId + ",";
					}
				}
			}
		}
		CertificateBO certBo = new CertificateBO();
		certBo.setSuccessNum(successNum);
		certBo.setFailureNum(failureNum);
		certBo.setFailureCertificate(failureCertificate);
		return certBo;
	}
	
	/**
	 * @param json
	 * @return
	 */
	public CertificateBO initCertificateId(String json) {
		return initDeleteOption(json,INIT_OPTION);
	}

	public CertificateBO deleteInitCertificateId(String json){
		return initDeleteOption(json,DELETE_OPTION);
	}
	
	/**
	 * @param prop
	 * @return
	 */
	private String getSecondProp(String prop) {
		String[] keyVal = prop.split(":");
		if (keyVal.length == 2) {
			return keyVal[1];
		}
		return "";
	}

	/**
	 * @param birthCertificateId
	 */
	private void saveCertificate(String birthCertificateId) {
		BirthCertificate birthCertificate = new BirthCertificate();
		birthCertificate.setCertifiId(birthCertificateId);
		birthCertificate.setIsEffectived(0);
		getHibernateTemplate().saveOrUpdate(birthCertificate);
	}

	private boolean deteleCertificate(String birthCertificateId) {
		BirthCertificate birthCertificate = (BirthCertificate)getHibernateTemplate().get(BirthCertificate.class, birthCertificateId);
		if(birthCertificate == null){
			return false;
		}else{
			getHibernateTemplate().delete(birthCertificate);
			return true;
		}
		
	}

	/**
	 * @param orgId
	 * @return
	 */
	public List<String> getDistributedCertiId(int orgId, String resTxt) {
		String hql = "Select A.certificateId From BirthCertificateOrg A,BirthCertificate B" +
				" Where A.orgId = ? And B.isEffectived = 1 And A.certificateId = B.certifiId";
		boolean flag = false;
		if (StringUtils.hasText(resTxt)) {
			hql = hql + " And A.certificateId Like ?";
			flag = true;
		}
		Query query = getSession().createQuery(hql);
		query.setParameter(0, orgId);
		if (flag)
			query.setParameter(1, resTxt + "%");
		List<String> list = query.list();
		if (list.size() > 0)
			return list;
		return null;
		// return execHql(hql,orgId,resTxt);
	}

	/**
	 * @param orgId
	 * @return
	 */
	public List<String> getUsedCertiId(int orgId) {
		String hql = "Select A.certifiId From BirthCertificate A,BirthCertificateOrg B Where A.certifiId = B.certificateId And isEffectived = " + BIRTH_USED
				+ "And B.orgId = ?";
		return execHql(hql, orgId);
	}

	/**
	 * @param hql
	 * @param orgId
	 * @return
	 */
	private List<String> execHql(String hql, int orgId) {
		Query query = getSession().createQuery(hql);
		query.setParameter(0, orgId);
		List<String> list = query.list();
		if (list.size() > 0)
			return list;
		return null;
	}

	/**
	 * @param orgId
	 * @return
	 */
	public PagingResult<BirthCertificate> getDestroyedCertiId(QueryCertificateBO qryCerti,PagingParam pp) {
		String hql = "From BirthCertificate A,BirthCertificateOrg B Where A.certifiId = B.certificateId And isEffectived = 3 "
				+ "And B.orgId = ?";
		return getBirthCertificates(hql,qryCerti,pp);
	}

	/**
	 * @return
	 */
	public List<String> getUnDistributeCertiId() {
		String hql = "Select certifiId From BirthCertificate Where isEffectived = 0";
		Query query = getSession().createQuery(hql);
		List<String> list = query.list();
		if (list.size() > 0)
			return list;
		return null;
	}

	private CertificateBO disUndisCertificate(String json,int orgId,int types){
		String[] propeties = json.split(",");
		String prex = getSecondProp(propeties[0]);
		String birthCertificateId = "";
		String msg = "";
		int successNum = 0;
		int failureNum = 0;
		String failureCertificate = "";
		String otherCondition = getSecondProp(propeties[propeties.length - 1]);
		String remarkId = saveBirthCertificateRemarks(prex, otherCondition,DISTRIBUTE_REMARKS,orgId);
		for (int i = 1; i < propeties.length - 1; i = i + 2) {
			String tmpStartCondition = getSecondProp(propeties[i]);
			String tmpEndCondition = getSecondProp(propeties[i + 1]);
			if (tmpStartCondition.equals("") || tmpEndCondition.equals(""))
				continue;
			long startCondition = Long.valueOf(tmpStartCondition);
			long endCondition = Long.valueOf(tmpEndCondition);
			for (long j = startCondition; j <= endCondition; j++) {
				birthCertificateId = fillBirhCertifiZero(prex,j,BIRTH_CERTIFICATE_LENGTH);
				if(types == 0){
					msg = distriCertificate(birthCertificateId, orgId);
					if(msg.equals("")){
						successNum ++;
					}else{
						failureNum ++;
						failureCertificate += msg;
					}
				}else if (types == 1){
					msg = execHql2(birthCertificateId,orgId);
					if(msg.equals("")){
						successNum ++;
					}else{
						failureNum ++;
						failureCertificate += msg;
					}
				}
			}
			saveBirthCertificateConditions(remarkId, startCondition, endCondition);
		}
		if (!otherCondition.equals("")) {
			String[] other = otherCondition.split(";");
			for (int i = 0; i < other.length; i++) {
				birthCertificateId = prex + other[i];
				if(types == 0){
					msg = distriCertificate(birthCertificateId, orgId);
					if(msg.equals("")){
						successNum ++;
					}else{
						failureNum ++;
						failureCertificate += msg;
					}
				}else if(types == 1){
					msg = execHql2(birthCertificateId,orgId);
					if(msg.equals("")){
						successNum ++;
					}else{
						failureNum ++;
						failureCertificate += msg;
					}
				}
			}
		}
		CertificateBO certiBo = new CertificateBO();
		certiBo.setSuccessNum(successNum);
		certiBo.setFailureNum(failureNum);
		certiBo.setFailureCertificate(failureCertificate);
		return certiBo;
	}
	
	/**
	 * @param json
	 * @param orgId
	 * @return
	 */
	public CertificateBO distriCertificateId(String json, int orgId) {
		return disUndisCertificate(json,orgId,0);
	}

	private BirthCertificateCondition saveBirthCertificateConditions(
			String remarkId, long startCondition, long endCondition) {
		BirthCertificateCondition condition = new BirthCertificateCondition();
		condition.setStartCondition(startCondition);
		condition.setEndCondition(endCondition);
		condition.setRemarkId(remarkId);
		getHibernateTemplate().save(condition);
		return condition;
	}

	private String saveBirthCertificateRemarks(String prex,
			String otherCondition,int type,int orgId) {
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		BirthCertificateRemarks remark = new BirthCertificateRemarks();
		remark.setInputDate(new Timestamp(System.currentTimeMillis()));
		remark.setInputPersonId(user.getUsername());
		remark.setOtherCondition(otherCondition);
		remark.setPrex(prex);
		remark.setType(type);
		remark.setOrgId(orgId);
		getHibernateTemplate().save(remark);
		String remarkId = remark.getId();
		return remarkId;
	}

	/**
	 * @param birthCertificateId
	 * @param orgId
	 * @return
	 */
	private String distriCertificate(String birthCertificateId, int orgId) {
		String hql = "From BirthCertificate Where certifiId = ? And isEffectived = 0";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, birthCertificateId);
		List list = query.list();
		if (list.size() > 0) {
			BirthCertificate birthCertificate = (BirthCertificate) getHibernateTemplate()
					.get(BirthCertificate.class, birthCertificateId);
			birthCertificate.setIsEffectived(1);
			BirthCertificateOrg birthCertificateOrg = new BirthCertificateOrg();
			birthCertificateOrg.setOrgId(orgId);
			birthCertificateOrg.setCertificateId(birthCertificateId);
			birthCertificateOrg.setDistriDate(new Timestamp(System.currentTimeMillis()));
			getHibernateTemplate().update(birthCertificate);
			getHibernateTemplate().save(birthCertificateOrg);
			return "";
		}
		return birthCertificateId + ",";
	}

	public PagingResult<BirthCertificate> getUsedCertificate(QueryCertificateBO qryCerti,PagingParam pp) {
		String hql = "From BirthCertificate A,BirthCertificateOrg B Where A.certifiId = B.certificateId And "
				+ "B.orgId = ? And A.isEffectived = 2";
		return getBirthCertificates(hql,qryCerti,pp);
	}
	
	public PagingResult<BirthCertificate> getPigeonholedCertiId(QueryCertificateBO qryCerti,PagingParam pp) {
		String hql = "From BirthCertificate A,BirthCertificateOrg B Where A.certifiId = B.certificateId And "
				+ "B.orgId = ? And A.isEffectived = 4";
		return getBirthCertificates(hql,qryCerti,pp);
	}
	public PagingResult<BirthCertificate> getChangedCertiId(QueryCertificateBO qryCerti,PagingParam pp) {
		String hql = "From BirthCertificate A,BirthCertificateOrg B Where A.certifiId = B.certificateId And "
				+ "B.orgId = ? And A.isChanged = 1";
		return getBirthCertificates(hql,qryCerti,pp);
	}
	public PagingResult<BirthCertificate> getBeforeChangedCertiId(QueryCertificateBO qryCerti,PagingParam pp) {
		if (StringUtils.hasText(qryCerti.getResTxt())) {
			String sourceHql = " From BirthCertificateChange Where destBirthCertifiId = ?";
			Query query = getSession().createQuery(sourceHql);
			query.setParameter(0, qryCerti.getResTxt());
			List list = query.list();
			if(list.size() > 0){
				BirthCertificateChange change = (BirthCertificateChange)list.get(0);
				qryCerti.setResTxt(change.getSourceBirthCertifiId());
			}
		}
		
		String hql = "From BirthCertificate A,BirthCertificateOrg B Where A.certifiId = B.certificateId And "
				+ "B.orgId = ? And A.isEffectived = 5";
		return getBirthCertificates(hql,qryCerti,pp);
	}
	
	private PagingResult<BirthCertificate> getBirthCertificates(String hql,QueryCertificateBO qryCerti,PagingParam pp) {
		List params = new ArrayList();
		params.add(qryCerti.getOrgId());
		if (StringUtils.hasText(qryCerti.getResTxt())) {
			hql =  hql + " And A.certifiId Like ?";
			params.add(qryCerti.getResTxt() + "%");
		}
		String countSql = "Select Count(*) " + hql;
		int totalSize = ((Long)(getHibernateTemplate().find(countSql,params.toArray()).get(0))).intValue();
		final String fhql = hql.toString();
		final PagingParam fpp = pp;
		final List fparams = params;
		List list = getHibernateTemplate().executeFind(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
				Query query = arg0.createQuery(fhql);
				for (int i = 0; i < fparams.size(); i++) {
					query.setParameter(i, fparams.get(i));
				}
				query.setFirstResult(fpp.getStart()).setMaxResults(fpp.getLimit());
				return query.list();
			}
		});
		List<BirthCertificate> birthCertificates = new ArrayList<BirthCertificate>();
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			BirthCertificate birthcertifi = (BirthCertificate) objs[0];
			getHibernateTemplate().evict(birthcertifi);
			birthCertificates.add(birthcertifi);
		}
		return new PagingResult<BirthCertificate>(totalSize, birthCertificates);
	}

//	private List<BirthCertificate> getBirthCertificates(int orgId,
//			String resTxt, String hql) {
//		boolean flag = false;
//		if (StringUtils.hasText(resTxt)) {
//			hql =  hql + " And A.certifiId Like ?";
//			flag = true;
//		}
//		Query query = getSession().createQuery(hql);
//		query.setParameter(0, orgId);
//		if (flag)
//			query.setParameter(1, resTxt + "%");
//		List list = query.list();
//		List<BirthCertificate> listCert = new ArrayList<BirthCertificate>();
//		for(Object obj : list){
//			Object[] objs = (Object[])obj;
//			listCert.add((BirthCertificate)objs[0]);
//		}
//		return listCert;
//	}
	
	/**
	 * @param d
	 * @return
	 */
	private Date convertStrToDate(String d)throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("y-m-d h:m:s");
		Date date = null;
		try {
			date = format.parse(d);
		} catch (ParseException e) {
			throw e;
		}
		return date;
	}

//	public boolean save(String json) {
//		JSONObject jsonObject = JSONObject.fromObject(json);
//		String birthday = jsonObject.getString("birthday").trim();
//		if (StringUtils.hasText(birthday)) {
//			jsonObject.put("birthday", convertStrToDate(birthday));
//		} else {
//			jsonObject.remove("birthday");
//		}
//		String issuingDate = jsonObject.getString("issuingDate").trim();
//		if (StringUtils.hasText(issuingDate)) {
//			jsonObject.put("issuingDate", convertStrToDate(birthday));
//		} else {
//			jsonObject.remove("issuingDate");
//		}
//		
//		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
//			.currentOperator();
//		BirthCertifiDestroyReason reason = null;
//		int type = -1;
//		if(jsonObject.has("reasonRemarks")){
//			reason = new BirthCertifiDestroyReason();
//			reason.setCertifiId(jsonObject.getString("certifiId"));
//			reason.setInputPersonId(user.getUsername());
//			reason.setReasonDate(new Timestamp(System.currentTimeMillis()));
//			type = 0;
//			reason.setType(type);
//			reason.setReasonRemarks(jsonObject.getString("reasonRemarks"));
//			reason.setOtherDestroyReason(jsonObject.getString("otherDestroyReason"));
//			jsonObject.remove("reasonRemarks");
//			jsonObject.remove("otherDestroyReason");
//		}
//		
//		if(jsonObject.has("unReasonRemarks")){
//			reason = new BirthCertifiDestroyReason();
//			reason.setCertifiId(jsonObject.getString("certifiId"));
//			reason.setInputPersonId(user.getUsername());
//			reason.setReasonDate(new Timestamp(System.currentTimeMillis()));
//			type = 1;
//			reason.setType(type);
//			reason.setReasonRemarks(jsonObject.getString("unReasonRemarks"));
//			jsonObject.remove("unReasonRemarks");
//		}
//		
//		
//		//新生儿建档
//		String f = "";
//		BirthCertificate birth = (BirthCertificate)getHibernateTemplate().get(BirthCertificate.class,jsonObject.getString("certifiId") );
//		if(reason == null){
//			String disNo = jsonObject.getString("districtNum");
//			String birthDistrictNum = birth.getDistrictNum();
//			if(birthDistrictNum == null || (!birthDistrictNum.equals(disNo) && !disNo.equals(""))){
//				String fileNo = fileNoGen.getNextFileNo(disNo);
//				HealthFile hf = new HealthFile();
//				PersonalInfo info = new PersonalInfo();
//				f = EncryptionUtils.encry(fileNo);
//				hf.setFileNo(f);
//				hf.setName(EncryptionUtils.encry(""));
//				hf.setDistrictNumber(disNo);
//				District district = (District)getHibernateTemplate().get(District.class, disNo);
//				String village = district.getName();
//				hf.setVillage(village);
//				district = (District)getHibernateTemplate().get(District.class, district.getParentId());
//				String township = district.getName();
//				hf.setTownship(township);
//				hf.setAddress(township + village);
//				hf.setResidenceAddress(township + village);
//				hf.setInputDate(new Timestamp(System.currentTimeMillis()));
//				hf.setInputPersonId(user.getUsername());
//				hf.setBuildDate(new Timestamp(System.currentTimeMillis()));
//				
//				info.setFileNo(f);
//				info.setFileNoSub(EncryptionUtils.encry(fileNo.substring(fileNo.length() - 7)));
//				info.setSex(jsonObject.getString("sex"));
//				info.setBirthday(new Timestamp(convertStrToDate(birthday).getTime()));
//				info.setLinkman(jsonObject.getString("motherName"));
//				info.setInputPersonId(user.getUsername());
//				info.setIdnumber(EncryptionUtils.encry(""));
//				info.setInputDate(new Timestamp(System.currentTimeMillis()));
//				getHibernateTemplate().save(hf);
//				getHibernateTemplate().save(info);
//			}
//		}else{
//			f = birth.getFileNo();
//		}
//		jsonObject.put("fileNo", f);
//		BirthCertificate birthCertificate = (BirthCertificate) JSONObject
//			.toBean(jsonObject, BirthCertificate.class);
//		
//		
//		try {
//			if(type == 0)
//				birthCertificate.setIsEffectived(3);
//			else
//				birthCertificate.setIsEffectived(2);
//			birthCertificate.setInputPersonId(user.getUsername());
//			if(birthCertificate.getInputDate() == null)
//				birthCertificate.setInputDate(new Date());
//			getHibernateTemplate().merge(birthCertificate);
//			if(reason != null)
//				getHibernateTemplate().save(reason);
//		} catch (Exception ex) {
//			throw new RuntimeException(ex);
//		}
//		return true;
//	}

	/**
	 * @return
	 */
	public List getBirthAdress() {
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		String districtId = user.getDistrictId();
		List list = new ArrayList();
		execHql1(districtId, list);
		Collections.reverse(list);
		return list;
	}

	/**
	 * @param disId
	 * @return
	 */
	private void execHql1(String disId, List borthAddress) {
		String hql = "From District Where id = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, disId);
		List list = query.list();
		if (list.size() > 0) {
			District district = (District) list.get(0);
			String parentId = district.getParentId();
			if (!parentId.equals("0")) {
				borthAddress.add(district.getName());
				execHql1(parentId, borthAddress);
			} else {
				borthAddress.add(district.getName());
			}
		}
	}

	/**
	 * @param certifiId
	 * @return
	 */
	public boolean getCertificateStatus(String certifiId) {
		BirthCertificate birthCertificate = getBirthCertificateInfo(certifiId);
		if(birthCertificate != null){
			int isEffectived = birthCertificate.getIsEffectived();
			if (isEffectived <= 1) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * @param certifiId
	 * @return
	 */
	public BirthCertificate getBirthCertificateInfo(String certifiId) {
		String hql = "From BirthCertificate Where certifiId = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, certifiId);
		List list = query.list();
		if (list.size() > 0) {
			BirthCertificate birthCertificate = (BirthCertificate) list.get(0);
			return birthCertificate;
		}
		return null;
	}
	
	public List getDestroyBirthCertificateInfo(String certifiId) {
		String hql = "From BirthCertificate A,BirthCertifiDestroyReason B Where A.certifiId = ? And A.certifiId = B.certifiId";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, certifiId);
		List list = query.list();
		List ret = new ArrayList();
		if (list.size() > 0) {
			Object[] objs = (Object[]) list.get(list.size() - 1);
			BirthCertificate birthCertificate = (BirthCertificate) objs[0];
			BirthCertifiDestroyReason birthCertifiDestroyReason = (BirthCertifiDestroyReason) objs[1];
			ret.add(birthCertificate);
			ret.add(birthCertifiDestroyReason);
		}
		return ret;
	}
	/**
	 * @param certifiId
	 * @return
	 */
	public boolean setPigeonhole(String certifiId,int type){
		BirthCertificate birthCertificate = (BirthCertificate)getHibernateTemplate().get(BirthCertificate.class, certifiId);
		if(type == 0)
			birthCertificate.setIsEffectived(4);
		else if(type == 1)
			birthCertificate.setIsEffectived(2);
		getHibernateTemplate().update(birthCertificate);
		return true;
	}
	
	
	public List getBirthCertificateRemarks(int orgId){
		
		return null;
	}
	
	public CertificateBO restoreDistriCertificateId(String json,int orgId){
		return disUndisCertificate(json,orgId,1);
	}
	
	private String execHql2(String certifiId,int orgId){
		String hql = "From BirthCertificateOrg Where orgId = " + orgId + " And certificateId = '" + certifiId + "'";
		Query query = getSession().createQuery(hql);
		List list = query.list();
		if(list.size() > 0){
			getHibernateTemplate().delete((BirthCertificateOrg)list.get(0));
			BirthCertificate birthCertificate = (BirthCertificate)getHibernateTemplate().get(BirthCertificate.class, certifiId);
			birthCertificate.setIsEffectived(0);
			getHibernateTemplate().update(birthCertificate);
			return "";
		}
		return certifiId + ",";	
	}
	
	
	/**
	 * 条形码初始化
	 * @param json
	 * @return
	 */
	public BarCodeBo barCodeInit(String json){
		JSONObject jsonObject = JSONObject.fromObject(json);
		BarCodeHistory barCodeHistory = (BarCodeHistory) JSONObject
			.toBean(jsonObject, BarCodeHistory.class);
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		barCodeHistory.setInputPersonId(user.getUsername());
		barCodeHistory.setInputDate(new Timestamp(System.currentTimeMillis()));
		barCodeHistory.setType("0");//0代表初始化
		
		Integer len = Integer.parseInt(sysInfoUtils.getVal(1));
		String min = jsonObject.getString("minVal");
		String max = jsonObject.getString("maxVal");
		BarCodeBo barcodebo = new BarCodeBo();
		if(min.length() > len || max.length() > 4){
			barcodebo.setErrorCode(ERROR_CODE_OVERRIDE);
			return barcodebo;
		}
		Integer minVal = Integer.parseInt(min);
		Integer maxVal = Integer.parseInt(max);
		
		if(minVal > maxVal || minVal < 0 || maxVal < 0){
			barcodebo.setErrorCode(ERROR_CODE_MIN_MAX);
			return barcodebo;
		}
		
		String flag = "";
		String distriNo = jsonObject.getString("distriNo");
		String years = jsonObject.getString("years");
		if(!years.equals("")){
			years = years.substring(2,years.length());
		}
		int successNum = 0;
		for(int i = minVal;i<= maxVal; i++){
			flag = generateBarCode(String.valueOf(i),len,distriNo,years);
			BarCode barcode = new BarCode();
			barcode.setId(flag);
			barcode.setInitDate(new Timestamp(System.currentTimeMillis()));
			barcode.setInitPersonId(user.getUsername());
			barcode.setStatus("0");// 0代表初始化
			getHibernateTemplate().saveOrUpdate(barcode);
			successNum++;
		}
		getHibernateTemplate().save(barCodeHistory);
		barcodebo.setSuccessNum(successNum);
		return barcodebo;
	}
	
	/**
	 * 删除条形码
	 * @param json
	 * @return
	 */
	public BarCodeBo barCodeDel(String json){
		JSONObject jsonObject = JSONObject.fromObject(json);
		BarCodeHistory barCodeHistory = (BarCodeHistory) JSONObject
			.toBean(jsonObject, BarCodeHistory.class);
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		barCodeHistory.setInputPersonId(user.getUsername());
		barCodeHistory.setInputDate(new Timestamp(System.currentTimeMillis()));
		barCodeHistory.setType("1");//1代表删除
		
		Integer len = Integer.parseInt(sysInfoUtils.getVal(1));
		String min = jsonObject.getString("minVal");
		String max = jsonObject.getString("maxVal");
		BarCodeBo barcodebo = new BarCodeBo();
		if(min.length() > len || max.length() > 4){
			barcodebo.setErrorCode(ERROR_CODE_OVERRIDE);
			return barcodebo;
		}
		Integer minVal = Integer.parseInt(min);
		Integer maxVal = Integer.parseInt(max);
		
		if(minVal > maxVal || minVal < 0 || maxVal < 0){
			barcodebo.setErrorCode(ERROR_CODE_MIN_MAX);
			return barcodebo;
		}
		
		String flag = "";
		String distriNo = jsonObject.getString("distriNo");
		String years = jsonObject.getString("years");
		if(!years.equals("")){
			years = years.substring(2,years.length());
		}
		int successNum = 0;
		int failureNum = 0;
		String failureStr = "";
		for(int i = minVal;i<= maxVal; i++){
			flag = generateBarCode(String.valueOf(i),len,distriNo,years);
			BarCode barcode = (BarCode)getHibernateTemplate().get(BarCode.class, flag);
			if(barcode != null){
				if(barcode.getStatus().equals("0")){//0代表初始化，没有被使用，可以删除。
					getHibernateTemplate().delete(barcode);
					successNum++;
				}else{
					failureNum++;
					failureStr += flag + ",";
				}
			}else{
				failureNum++;
				failureStr += flag + ",";
			}
		}
		getHibernateTemplate().save(barCodeHistory);
		barcodebo.setSuccessNum(successNum);
		barcodebo.setFailureNum(failureNum);
		barcodebo.setFailureStr(failureStr.equals("") ? "" : failureStr.substring(0,failureStr.length() - 1));
		return barcodebo;
	}
	
	/**
	 * 重置条形码
	 * @param json
	 * @return
	 */
	public BarCodeBo barCodeRestore(String json){
		JSONObject jsonObject = JSONObject.fromObject(json);
		BarCodeHistory barCodeHistory = (BarCodeHistory) JSONObject
			.toBean(jsonObject, BarCodeHistory.class);
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		barCodeHistory.setInputPersonId(user.getUsername());
		barCodeHistory.setInputDate(new Timestamp(System.currentTimeMillis()));
		barCodeHistory.setType("2");//2代表重置
		
		Integer len = Integer.parseInt(sysInfoUtils.getVal(1));
		String min = jsonObject.getString("minVal");
		String max = jsonObject.getString("maxVal");
		BarCodeBo barcodebo = new BarCodeBo();
		if(min.length() > len || max.length() > 4){
			barcodebo.setErrorCode(ERROR_CODE_OVERRIDE);
			return barcodebo;
		}
		Integer minVal = Integer.parseInt(min);
		Integer maxVal = Integer.parseInt(max);
		
		if(minVal > maxVal || minVal < 0 || maxVal < 0){
			barcodebo.setErrorCode(ERROR_CODE_MIN_MAX);
			return barcodebo;
		}
		
		String flag = "";
		String distriNo = jsonObject.getString("distriNo");
		String years = jsonObject.getString("years");
		if(!years.equals("")){
			years = years.substring(2,years.length());
		}
		int successNum = 0;
		int failureNum = 0;
		String failureStr = "";
		for(int i = minVal;i<= maxVal; i++){
			flag = generateBarCode(String.valueOf(i),len,distriNo,years);
			BarCode barcode = (BarCode)getHibernateTemplate().get(BarCode.class, flag);
			if(barcode != null){
				getHibernateTemplate().delete(barcode);
				successNum++;
			}else{
				failureNum++;
				failureStr += flag + ",";
			}
		}
		getHibernateTemplate().save(barCodeHistory);
		barcodebo.setSuccessNum(successNum);
		barcodebo.setFailureNum(failureNum);
		barcodebo.setFailureStr(failureStr.equals("") ? "" : failureStr.substring(0,failureStr.length() - 1));
		return barcodebo;
	}
	
	/**
	 * 生成流水号
	 * @param val
	 * @param len
	 * @return
	 */
	private String generateBarCode(String val,Integer len,String distriNo,String years){
		int valLen = val.length();
		String tmp = "";
		for (int i = 0; i < len - valLen; i++)
			tmp += "0";
		return distriNo + years + tmp + val;
	}
	
	/**
	 * 获得基础信息配置值
	 * @param type
	 * @return
	 */
	public List<BasicInformation> getBasicInformation(Integer type){
		return sysInfoUtils.getBasicInformation(type);
	}
	
	public String getURL(Integer id){
		return sysInfoUtils.getVal(id);
	}
	
	/**
	 * 是否管理员权限
	 * @return
	 */
	public List getAuthority(){
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		String username = user.getUsername();
		Query query = getSession().createQuery("From SamTaxempcodeRole Where id.loginname = ?");
		query.setParameter(0, username);
		List list = query.list();
		boolean flag = false;
		if(list.size() > 0){
			for(Object obj : list){
				SamTaxempcodeRole samRole = (SamTaxempcodeRole)obj;
				if(samRole.getId().getId().equals("role_admin")){
					flag = true;
					break;
				}
			}
		}
		List l = new ArrayList();
		l.add(flag);
		l.add(user.getOrg().getIsOrgDepart());
		return l;
	}
	
	/**
	 * 打印成功
	 * @param certifiId
	 */
	public void printSuccess(String certifiId){
		BirthCertificate birthCertifi = (BirthCertificate)getHibernateTemplate().get(BirthCertificate.class, certifiId);
		birthCertifi.setIsPrint(1);//1代表出生证明打印成功
		birthCertifi.setIsEffectived(4);//4代表该出生证明归档
		getHibernateTemplate().update(birthCertifi);
	}
	
	/**
	 * 根据出生证明编号取得出生证明的信息
	 * @param certifiId
	 * @return
	 */
	public BirthCertificate getBirthCertificate(String certifiId){
		BirthCertificate birthCertifi = (BirthCertificate)getHibernateTemplate().get(BirthCertificate.class, certifiId);
		return birthCertifi;
	}
	
	public BirthCertificateBO get(BirthCertificateBO birthCertificate){
		BirthCertificate birthCertifi = (BirthCertificate)getHibernateTemplate().get(BirthCertificate.class, birthCertificate.getCertifiId());
		Integer tmpType = birthCertificate.getType();
		boolean flag = false;
		Integer type = null;
		if(tmpType.equals(5)){
			flag = true;
			type = DESTROY_REASON_TYPE;
		}else if(tmpType.equals(4)){
			flag = true;
			type = UNDESTROY_REASON_TYPE;
		}else if(tmpType.equals(6)){
			String hql = "From BirthCertificateChange Where destBirthCertifiId = ?";
			Query query = getSession().createQuery(hql);
			query.setParameter(0, birthCertificate.getCertifiId());
			List list = query.list();
			if(list.size() > 0){
				BirthCertificateChange change = (BirthCertificateChange)list.get(0);
				BeanUtils.copyProperties(change,birthCertificate);
			}
			hql = " From BirthCertificateBefore2012 Where birthCertifiId = ? ";
			query = getSession().createQuery(hql);
			query.setParameter(0, birthCertificate.getSourceBirthCertifiId());
			list = query.list();
			if(list.size() > 0 ){
				BirthCertificateBefore2012 before = (BirthCertificateBefore2012)list.get(0);
				birthCertificate.setOriginalBirthAddress(before.getOriginalBirthAddress());
				birthCertificate.setName2012(before.getName2012());
				birthCertificate.setFatherName2012(before.getFatherName2012());
				birthCertificate.setMotherName2012(before.getMotherName2012());
			}
		}
		if(flag){
			String hql = "From BirthCertifiDestroyReason Where certifiId = ? And type = ? Order By reasonDate DESC";
			Query query = getSession().createQuery(hql);
			query.setParameter(0, birthCertificate.getCertifiId());
			query.setParameter(1, type);
			List list = query.list();
			if(list.size() > 0){
				BirthCertifiDestroyReason reason = (BirthCertifiDestroyReason)list.get(0);
				reason.setType(tmpType);
				BeanUtils.copyProperties(reason,birthCertificate);
			}
		}
		
		BeanUtils.copyProperties(birthCertifi,birthCertificate);
		return birthCertificate;
	}
	
	public BirthCertificateBO getUnUsed(BirthCertificateBO birthCertificate){
		birthCertificate.setMotherNation("汉");
		birthCertificate.setFatherNation("汉");
		BirthCertificate birthCertifi = (BirthCertificate)getHibernateTemplate().get(BirthCertificate.class, birthCertificate.getCertifiId());		
		if(!birthCertifi.getIsEffectived().equals(1))
			BeanUtils.copyProperties(birthCertifi,birthCertificate);
		return birthCertificate;
	}
	
	public String saveSupply(BirthCertificateBO birthCertificate){
		BirthCertificate birthCertifi = new BirthCertificate();
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		BeanUtils.copyProperties(birthCertificate,birthCertifi);
		if(birthCertificate.getSaveType() != null && birthCertificate.getSaveType().equals("0"))
			birthCertifi.setIsSupply(1);
		else if(birthCertificate.getSaveType() != null && birthCertificate.getSaveType().equals("10"))
			birthCertifi.setIsSupply(10);
		birthCertifi.setInputPersonId(user.getUsername());
		birthCertifi.setInputDate(new Timestamp(System.currentTimeMillis()));
		birthCertifi.setIsEffectived(2);
		getHibernateTemplate().update(birthCertifi);
		return birthCertifi.getCertifiId();
	}
	public String saveChange(BirthCertificateBO birthCertificate){
		String checkhql = " From BirthCertificateChange Where sourceBirthCertifiId = ?";
		Query checkquery = getSession().createQuery(checkhql);
		checkquery.setParameter(0, birthCertificate.getSourceBirthCertifiId());
		List checklist = checkquery.list();
		if(checklist.size() > 0){
			return "0";
		}
		
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		BirthCertificate birthCertifi = new BirthCertificate();
		if(birthCertificate.getBirthCertificateYears() != null && birthCertificate.getBirthCertificateYears().equals("2012年以前的出生医学证明")){
			BeanUtils.copyProperties(birthCertificate,birthCertifi);
			birthCertifi.setInputPersonId(user.getUsername());
			birthCertifi.setInputDate(new Timestamp(System.currentTimeMillis()));
			birthCertifi.setIsEffectived(2);
			birthCertifi.setIsChanged(1);
			getHibernateTemplate().update(birthCertifi);
			
			String hql = " From BirthCertificateChange Where destBirthCertifiId = ?";
			Query query = getSession().createQuery(hql);
			query.setParameter(0, birthCertificate.getCertifiId());
			List list = query.list();
			if(list.size() > 0){
				BirthCertificateChange change = (BirthCertificateChange)list.get(0);
				BeanUtils.copyProperties(birthCertificate,change);
				getHibernateTemplate().update(change);
			}else{
				BirthCertificateChange birthCertifiChange = new BirthCertificateChange();
				BeanUtils.copyProperties(birthCertificate,birthCertifiChange);
				birthCertifiChange.setDestBirthCertifiId(birthCertifi.getCertifiId());
				getHibernateTemplate().save(birthCertifiChange);
			}
			
			String h = " From BirthCertificateBefore2012 Where birthCertifiId = ?";
			Query q = getSession().createQuery(h);
			q.setParameter(0, birthCertificate.getSourceBirthCertifiId());
			List l = q.list();
			if(l.size() > 0 ){
				BirthCertificateBefore2012 before = new BirthCertificateBefore2012();
				BeanUtils.copyProperties(birthCertificate,before);
				before.setBirthCertifiId(birthCertificate.getSourceBirthCertifiId());
				before.setInputDate(new Timestamp(System.currentTimeMillis()));
				getHibernateTemplate().save(before);
			}else{
				BirthCertificateBefore2012 before = new BirthCertificateBefore2012();
				BeanUtils.copyProperties(birthCertificate,before);
				before.setBirthCertifiId(birthCertificate.getSourceBirthCertifiId());
				before.setOrgId(user.getOrgId());
				before.setInputPersonId(user.getUsername());
				before.setInputDate(new Timestamp(System.currentTimeMillis()));
				getHibernateTemplate().save(before);
			}
		}else{
			BirthCertificate sourceBirthCertifi = (BirthCertificate)getHibernateTemplate().get(BirthCertificate.class, birthCertificate.getSourceBirthCertifiId());
			if(sourceBirthCertifi != null && sourceBirthCertifi.getIsEffectived().equals(4)){
				BeanUtils.copyProperties(birthCertificate,birthCertifi);
				birthCertifi.setInputPersonId(user.getUsername());
				birthCertifi.setInputDate(new Timestamp(System.currentTimeMillis()));
				birthCertifi.setIsEffectived(2);
				birthCertifi.setIsChanged(1);
				
				sourceBirthCertifi.setIsEffectived(5);
				getHibernateTemplate().update(birthCertifi);
				getHibernateTemplate().update(sourceBirthCertifi);
				String hql = " From BirthCertificateChange Where destBirthCertifiId = ?";
				Query query = getSession().createQuery(hql);
				query.setParameter(0, birthCertificate.getCertifiId());
				List list = query.list();
				if(list.size() > 0){
					BirthCertificateChange change = (BirthCertificateChange)list.get(0);
					BeanUtils.copyProperties(birthCertificate,change);
					getHibernateTemplate().update(change);
				}else{
					BirthCertificateChange birthCertifiChange = new BirthCertificateChange();
					BeanUtils.copyProperties(birthCertificate,birthCertifiChange);
					birthCertifiChange.setDestBirthCertifiId(birthCertifi.getCertifiId());
					getHibernateTemplate().save(birthCertifiChange);
				}
				
				String h = " From BirthCertificateBefore2012 Where birthCertifiId = ?";
				Query q = getSession().createQuery(h);
				q.setParameter(0, birthCertificate.getSourceBirthCertifiId());
				List l = q.list();
				if(l.size() > 0){
					BirthCertificateBefore2012 before = (BirthCertificateBefore2012)l.get(0);
					getHibernateTemplate().delete(before);
				}
				
			}else{
				return "0";
//				throw new RuntimeException("原出生医学证明编号填写不正确！");
			}
		}
		
		return birthCertifi.getCertifiId();
	}
	public BirthCertificateBO getChangeInfo(BirthCertificateBO birthCertificate){
		birthCertificate.setMotherNation("汉");
		birthCertificate.setFatherNation("汉");
		BirthCertificate birthCertifi = (BirthCertificate)getHibernateTemplate().get(BirthCertificate.class, birthCertificate.getCertifiId());
		if(!birthCertifi.getIsEffectived().equals(1))
			BeanUtils.copyProperties(birthCertifi,birthCertificate);
		BirthCertificateBO retVal = new BirthCertificateBO();
		BeanUtils.copyProperties(birthCertificate,retVal);
		String hql = " From BirthCertificateChange Where destBirthCertifiId = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, birthCertificate.getCertifiId());
		List list = query.list();
		if(list.size() > 0){
			BirthCertificateChange change = (BirthCertificateChange)list.get(0);
			BeanUtils.copyProperties(change,retVal);
			if(change.getBirthCertificateYears() != null && change.getBirthCertificateYears().equals("2012年以前的出生医学证明")){
				hql = "From BirthCertificateBefore2012 Where birthCertifiId = ?";
				query = getSession().createQuery(hql);
				query.setParameter(0, birthCertificate.getCertifiId());
				list = query.list();
				if(list.size() > 0){
					BirthCertificateBefore2012 before = (BirthCertificateBefore2012)list.get(0);
					retVal.setOriginalBirthAddress(before.getOriginalBirthAddress());
				}
			}
		}
		
		return retVal;
	}
	public String save(BirthCertificateBO birthCertificate)throws Exception{	
		Integer tmpType = birthCertificate.getType();
		birthCertificate.setInputDate(new Timestamp(System.currentTimeMillis()));
		BirthCertificate birthCertifi01 = (BirthCertificate)getHibernateTemplate().get(BirthCertificate.class,birthCertificate.getCertifiId());		
		if(birthCertifi01.getIsSupply() != null && birthCertifi01.getIsSupply().equals(1)){
			BeanUtils.copyProperties(birthCertificate,birthCertifi01);
			birthCertifi01.setIsSupply(1);
			//处理补发作废的情况
			if(tmpType.equals(3) || tmpType.equals(7)){
				birthCertifi01.setIsEffectived(EFFECTIVED_DESTROIED);
				birthCertifi01.setFileNo("");
			}
			getHibernateTemplate().update(birthCertifi01);
			return birthCertifi01.getCertifiId();
		}
		if(CommonConvertUtils.birthCertifiIsSupply(birthCertificate.getBirthday(),birthCertificate.getIssuingDate()))
			return "1";
//			throw new Exception("您没有权限补发出生医学证明！");
		BirthCertificate birthCertifi = new BirthCertificate();
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		boolean flag = false;
		Integer type = null;
		
		BeanUtils.copyProperties(birthCertificate,birthCertifi);
		if(tmpType == null){
			Timestamp currentDate = new Timestamp(System.currentTimeMillis());
			String inputPersonId = user.getUsername();
			birthCertifi.setInputPersonId(inputPersonId);
			birthCertifi.setInputDate(currentDate);
			birthCertifi.setIsEffectived(BIRTH_USED);
			String disNo = birthCertificate.getDistrictNum().trim();
			String tmpFileNo = fileNoGen.getNextFileNo(disNo);
			String fileNo = EncryptionUtils.encry(tmpFileNo);
//			childBirthRecord.setFileNo(fileNo);
			birthCertificate.setFileNo(fileNo);
			
			HealthFile hf = new HealthFile();
			PersonalInfo info = new PersonalInfo();
			hf.setFileNo(fileNo);
			hf.setName(EncryptionUtils.encry(birthCertificate.getName()));
			hf.setDistrictNumber(disNo);
			hf.setStatus(0);//档案正常
			District district = (District)getHibernateTemplate().get(District.class, disNo);
			String village = district.getName();
			hf.setVillage(village);
			district = (District)getHibernateTemplate().get(District.class, district.getParentId());
			String township = district.getName();
			hf.setTownship(township);
			hf.setAddress(township + village);
			hf.setResidenceAddress(township + village);
			hf.setInputDate(currentDate);
			hf.setInputPersonId(inputPersonId);
			hf.setBuildDate(currentDate);
			
			info.setFileNo(fileNo);
			info.setFileNoSub(EncryptionUtils.encry(tmpFileNo.substring(tmpFileNo.length() - 7)));
			info.setSex(birthCertificate.getSex());
			info.setBirthday(birthCertificate.getBirthday());
			info.setLinkman(birthCertificate.getMotherName() + "," + birthCertificate.getFatherName());
			info.setLinkmanTel(birthCertificate.getLinkmanTel());
			info.setInputPersonId(inputPersonId);
			info.setIdnumber(EncryptionUtils.encry(""));
			info.setInputDate(currentDate);
			hf.setPersonalInfo(info);
			
			getHibernateTemplate().save(hf);
			getHibernateTemplate().save(info);
		}else if(tmpType.equals(3) || tmpType.equals(7)){
			flag = true;
			type = DESTROY_REASON_TYPE;
			birthCertifi.setIsEffectived(EFFECTIVED_DESTROIED);
			birthCertifi.setIsChanged(0);
			String fileNo = birthCertifi.getFileNo();
			if(fileNo != null && !fileNo.equals("")){
				moduleMgr.removeHealthFile(fileNo,1);
				birthCertifi.setFileNo("");
			}
		}else if(tmpType.equals(4)){
			flag = true;
			type = UNDESTROY_REASON_TYPE;
			birthCertifi.setIsEffectived(BIRTH_USED);
			String disNo = birthCertifi.getDistrictNum().trim();
			String tmpFileNo = fileNoGen.getNextFileNo(disNo);
			String fileNo = EncryptionUtils.encry(tmpFileNo);
			Timestamp currentDate = new Timestamp(System.currentTimeMillis());
			String inputPersonId = user.getUsername();
			HealthFile hf = new HealthFile();
			PersonalInfo info = new PersonalInfo();
			hf.setFileNo(fileNo);
			hf.setName(EncryptionUtils.encry(birthCertifi.getName()));
			hf.setDistrictNumber(disNo);
			District district = (District)getHibernateTemplate().get(District.class, disNo);
			String village = district.getName();
			hf.setVillage(village);
			district = (District)getHibernateTemplate().get(District.class, district.getParentId());
			String township = district.getName();
			hf.setTownship(township);
			hf.setAddress(township + village);
			hf.setResidenceAddress(township + village);
			hf.setInputDate(currentDate);
			hf.setInputPersonId(inputPersonId);
			hf.setBuildDate(currentDate);
			hf.setStatus(0);//档案正常
			info.setFileNo(fileNo);
			info.setFileNoSub(EncryptionUtils.encry(tmpFileNo.substring(tmpFileNo.length() - 7)));
			info.setSex(birthCertifi.getSex());
			info.setBirthday(birthCertifi.getBirthday());
			info.setLinkman(birthCertifi.getMotherName() + "," + birthCertifi.getFatherName());
			info.setLinkmanTel(birthCertifi.getLinkmanTel());
			info.setInputPersonId(inputPersonId);
			info.setIdnumber(EncryptionUtils.encry(""));
			info.setInputDate(currentDate);
			hf.setPersonalInfo(info);
			
			getHibernateTemplate().save(hf);
			getHibernateTemplate().save(info);
			birthCertifi.setFileNo(fileNo);
		}
		if(flag){
			BirthCertifiDestroyReason reason = new BirthCertifiDestroyReason();
			BeanUtils.copyProperties(birthCertificate,reason);
			reason.setType(type);
			reason.setReasonDate(new Timestamp(System.currentTimeMillis()));
			getHibernateTemplate().save(reason);
		}		
		
		if(tmpType.equals(7)){
			CertificateChangedParams changedParams = new CertificateChangedParams();
			changedParams.setCertifiId(birthCertifi.getCertifiId());
			changedParams.setType("1");
			setCancelChanged(changedParams);
		}
		getHibernateTemplate().merge(birthCertifi);
		return birthCertificate.getCertifiId();
	}
	
	/**
	 * 将出生证明置为未使用状态
	 * @param certifiId
	 * @return
	 */
	public boolean setCancelUsed(String certifiId)throws Exception{
		BirthCertificate birthCertifi = (BirthCertificate)getHibernateTemplate().get(BirthCertificate.class, certifiId);
		Class clazz = birthCertifi.getClass();
		Method[] method = clazz.getMethods();
		Pattern pattern = Pattern.compile("^set.*");
		String methodName = "";
		for(Method m : method){
			try {
				methodName = m.getName();
				Matcher matcher = pattern.matcher(methodName);
				if(matcher.matches() && !methodName.equals("setCertifiId") && !methodName.equals("setIsEffectived")){
					Class<Object>[] params = (Class<Object>[])m.getParameterTypes();
					Method tmpMethod = clazz.getMethod(methodName, params);
					tmpMethod.invoke(birthCertifi, new Object[]{null});
//					m.invoke(birthCertifi, null);
				}
			} catch (IllegalArgumentException e) {
				throw e;
			} catch (IllegalAccessException e) {
				throw e;
			} catch (InvocationTargetException e) {
				throw e;
			} catch (SecurityException e) {
				throw e;
			} catch (NoSuchMethodException e) {
				throw e;
			}
		}
		birthCertifi.setIsEffectived(BIRTH_UNUSED);
		getHibernateTemplate().update(birthCertifi);
		return true;
	}
	
	
	public boolean setCancelChanged(CertificateChangedParams changedParams){
		String certifiId = "";
		if(changedParams.getType() != null && changedParams.getType().equals("0")){
			try {
				setCancelUsed(changedParams.getCertifiId());
				certifiId = changedParams.getBeforeCertifiId();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(changedParams.getType() != null && changedParams.getType().equals("1")){
			String hql = " From BirthCertificateChange Where destBirthCertifiId = ?";
			Query q = getSession().createQuery(hql);
			q.setParameter(0, changedParams.getCertifiId());
			List list = q.list();
			if(list.size() > 0){
				BirthCertificateChange change = (BirthCertificateChange)list.get(0);
				certifiId = change.getSourceBirthCertifiId();
			}
		}
		BirthCertificate birthCertifi = (BirthCertificate)getHibernateTemplate().get(BirthCertificate.class, certifiId);
		if(birthCertifi != null){
			birthCertifi.setIsEffectived(4);
			getHibernateTemplate().update(birthCertifi);
		}
		String deleteHql = " Delete From BirthCertificateChange Where destBirthCertifiId = ?";
		Query query = getSession().createQuery(deleteHql);
		query.setParameter(0, changedParams.getCertifiId());
		query.executeUpdate();
		return true;
	}
	
	public PagingResult<BirthCertificateBefore2012> get2012BeforeChangedCertiId(QueryCertificateBO qryCerti,PagingParam pp) {
		String sourceCertiBirth = "";
		if (StringUtils.hasText(qryCerti.getResTxt())) {
			String sourceHql = " From BirthCertificateChange Where destBirthCertifiId = ?";
			Query query = getSession().createQuery(sourceHql);
			query.setParameter(0, qryCerti.getResTxt());
			List list = query.list();
			if(list.size() > 0){
				BirthCertificateChange change = (BirthCertificateChange)list.get(0);
				sourceCertiBirth = change.getSourceBirthCertifiId();
			}
		}
		System.out.println(sourceCertiBirth);
		String sourceHql = " From BirthCertificateBefore2012 Where birthCertifiId = ?";
		String countHql = "Select Count(*) " + sourceHql;
		Query q = getSession().createQuery(countHql);
		q.setParameter(0, sourceCertiBirth);
		int totalSize = ((Long)(q.list()).get(0)).intValue();
		
		Query query = getSession().createQuery(sourceHql);
		query.setParameter(0, sourceCertiBirth);
		query.setFirstResult(pp.getStart()).setMaxResults(pp.getLimit());
		List list = query.list();
		List<BirthCertificateBefore2012> birthCertificates = new ArrayList<BirthCertificateBefore2012>();
		for (Object object : list) {
//			Object[] objs = (Object[]) object;
			BirthCertificateBefore2012 birthcertifi = (BirthCertificateBefore2012) object;
			getHibernateTemplate().evict(birthcertifi);
			birthCertificates.add(birthcertifi);
		}
		return new PagingResult<BirthCertificateBefore2012>(totalSize, birthCertificates);
	}
	public BirthCertificateBefore2012 getBirthCertificateBefore2012(BirthCertificateBefore2012 bean){
		BirthCertificateBefore2012 before = (BirthCertificateBefore2012)getHibernateTemplate().get(BirthCertificateBefore2012.class, bean.getId());
		return before;
	}
	public void saveBirthCertificateBefore2012(BirthCertificateBefore2012 bean){
		String sourceHql = " From BirthCertificateChange Where sourceBirthCertifiId = ?";
		Query q = getSession().createQuery(sourceHql);
		q.setParameter(0, bean.getBirthCertifiId());
		List list = q.list();
		if(list.size() > 0){
			BirthCertificateChange change = (BirthCertificateChange)list.get(0);
			change.setSourceBirthCertifiId(bean.getBirthCertifiId());
			getHibernateTemplate().update(change);
		}
		bean.setInputDate(new Timestamp(System.currentTimeMillis()));
		getHibernateTemplate().update(bean);
	}
}
