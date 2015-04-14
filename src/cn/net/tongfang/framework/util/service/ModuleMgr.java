package cn.net.tongfang.framework.util.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.net.tongfang.framework.security.SecurityManager;
import cn.net.tongfang.framework.security.bo.Condition;
import cn.net.tongfang.framework.security.bo.HealthFileQry;
import cn.net.tongfang.framework.security.bo.QryCondition;
import cn.net.tongfang.framework.security.bo.TransferHealthFileQry;
import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.BabyBarrierReg;
import cn.net.tongfang.framework.security.vo.BabyDeathSurvey;
import cn.net.tongfang.framework.security.vo.BabyVisit;
import cn.net.tongfang.framework.security.vo.BasicInformation;
import cn.net.tongfang.framework.security.vo.BirthCertificate;
import cn.net.tongfang.framework.security.vo.BloodTrans;
import cn.net.tongfang.framework.security.vo.ChildBirthRecord;
import cn.net.tongfang.framework.security.vo.ChildLastMedicalExamRecord;
import cn.net.tongfang.framework.security.vo.ChildrenDeathSurvey01;
import cn.net.tongfang.framework.security.vo.ChildrenDeathSurvey02;
import cn.net.tongfang.framework.security.vo.ChildrenMediExam;
import cn.net.tongfang.framework.security.vo.ChildrenMediExam3;
import cn.net.tongfang.framework.security.vo.ChildrenMediExam36;
import cn.net.tongfang.framework.security.vo.ClinicLogs;
import cn.net.tongfang.framework.security.vo.CodModuleMap;
import cn.net.tongfang.framework.security.vo.Consultation;
import cn.net.tongfang.framework.security.vo.CureBack;
import cn.net.tongfang.framework.security.vo.CureSwitch;
import cn.net.tongfang.framework.security.vo.DiabetesVisit;
import cn.net.tongfang.framework.security.vo.DiseaseAndHearScreenConsent;
import cn.net.tongfang.framework.security.vo.District;
import cn.net.tongfang.framework.security.vo.FinishGestation;
import cn.net.tongfang.framework.security.vo.FirstVistBeforeBorn;
import cn.net.tongfang.framework.security.vo.FuriousInfo;
import cn.net.tongfang.framework.security.vo.FuriousVisit;
import cn.net.tongfang.framework.security.vo.HealthEducat;
import cn.net.tongfang.framework.security.vo.HealthFile;
import cn.net.tongfang.framework.security.vo.HealthFileChildren;
import cn.net.tongfang.framework.security.vo.HealthFileLoginOff;
import cn.net.tongfang.framework.security.vo.HealthFileMaternal;
import cn.net.tongfang.framework.security.vo.HealthFileTransfer;
import cn.net.tongfang.framework.security.vo.HearScreenReportCard;
import cn.net.tongfang.framework.security.vo.HomeInfo;
import cn.net.tongfang.framework.security.vo.HypertensionVisit;
import cn.net.tongfang.framework.security.vo.InfectionReport;
import cn.net.tongfang.framework.security.vo.MedicalExam;
import cn.net.tongfang.framework.security.vo.PersonalInfo;
import cn.net.tongfang.framework.security.vo.PregnancyRecord;
import cn.net.tongfang.framework.security.vo.PregnancyRecordChild;
import cn.net.tongfang.framework.security.vo.Reception;
import cn.net.tongfang.framework.security.vo.SamModule;
import cn.net.tongfang.framework.security.vo.SamModuleCategory;
import cn.net.tongfang.framework.security.vo.SamTaxempcode;
import cn.net.tongfang.framework.security.vo.SamTaxorgcode;
import cn.net.tongfang.framework.security.vo.SickInfo;
import cn.net.tongfang.framework.security.vo.Stat;
import cn.net.tongfang.framework.security.vo.Tuberculosis;
import cn.net.tongfang.framework.security.vo.Vaccination;
import cn.net.tongfang.framework.security.vo.VaccineImmune;
import cn.net.tongfang.framework.security.vo.VaccineInfo;
import cn.net.tongfang.framework.security.vo.VisitAfterBorn;
import cn.net.tongfang.framework.security.vo.VisitBeforeBorn;
import cn.net.tongfang.framework.security.vo.WomanLastMedicalExamRecord;
import cn.net.tongfang.framework.security.vo.WomanPhysicalItems;
import cn.net.tongfang.framework.util.BusiUtils;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.framework.util.ModuleUtil;
import cn.net.tongfang.framework.util.service.vo.CatInfo;
import cn.net.tongfang.framework.util.service.vo.ExtJSTreeNode;
import cn.net.tongfang.framework.util.service.vo.ExtJSTreeNodeDefine;
import cn.net.tongfang.framework.util.service.vo.PagingParam;
import cn.net.tongfang.framework.util.service.vo.PagingResult;
import cn.net.tongfang.web.service.bo.ChildBirthRecordBO;
import cn.net.tongfang.web.service.bo.FirstVisitBeforeBornPrintBO;

import com.google.gson.Gson;

public class ModuleMgr extends HibernateDaoSupport {
	ModuleUtil moduleUtil;

	private static final Logger log = Logger.getLogger(ModuleMgr.class);
	public static final Integer DISEASE_HYP = 2;
	public static final Integer DISEASE_FURI = 8;
	public static final Integer DISEASE_DIAB = 3;
	// public static final Integer DISEASE_HYP = 527;
	// public static final Integer DISEASE_FURI = 533;
	// public static final Integer DISEASE_DIAB = 528;
	public static final String VISIT_AFTER_DEFAULT = "0";
	public static final String VISIT_AFTER_42 = "1";
	public static Map orgnameMap = new HashMap();
	public List<CatInfo> getCatInfo() {

		String hql = "select new cn.net.tongfang.framework.util.service.vo.CatInfo(m,c)  from "
				+ " SamModule m, SamModuleCategory c where m.categoryId = c.id";
		try {
			List<CatInfo> list = getHibernateTemplate().find(hql);
			log.debug("List size is " + list.size());
			return list;
		} catch (Throwable t) {
			t.printStackTrace();
			return new ArrayList<CatInfo>();
		}

	}

	@SuppressWarnings("unchecked")
	public List<CatInfo> getUserCatInfo() {
		final String sql = "select {module.*}, {mc.*},{mc1.*} from sam_module_category mc, ( select m.* from"
				+ " sam_module m where m.id in ( select rm.module_id from sam_role_module rm "
				+ " where rm.role_id in ( select ur.id from sam_taxempcode_role ur,"
				+ " sam_taxempcode u where u.loginname = :loginname and u.loginname = ur.loginname) ) ) module," +
				" sam_module_category mc1 where mc.id = module.category_id and mc.parentid = mc1.id " +
				" order by mc1.ordinal,mc.ordinal, module.ordinal";

		try {

			List<CatInfo> list = (List<CatInfo>) this.getHibernateTemplate()
					.execute(new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							SQLQuery sqlQry = session.createSQLQuery(sql)
									.addEntity("mc", SamModuleCategory.class)
									.addEntity("module", SamModule.class)
									.addEntity("mc1",SamModuleCategory.class);

							sqlQry.setString("loginname", SecurityManager
									.currentOperator().getUsername());
							List<Object[]> objs = sqlQry.list();
							List<CatInfo> results = new ArrayList<CatInfo>();
							for (Object[] items : objs) {
								SamModule mod = (SamModule) items[1];
								
								SamModuleCategory cat = (SamModuleCategory) items[0];
								SamModuleCategory secondcat = (SamModuleCategory) items[2];
								results.add(new CatInfo(mod, cat,secondcat));
							}
							return results;
						}

					});
			Map<String,String> addmodule = new HashMap<String,String>();
			for(CatInfo cat : list){
				for(CodModuleMap item :moduleUtil.getModuleList()){
					if(item.getMainmoduleid().equals(cat.getModule().getId()) && !addmodule.containsKey(item.getMainmoduleid())){
						addmodule.put(item.getMainmoduleid(), item.getMainmoduleid());
					}
				}
			}
			List addList = new ArrayList();
			for(CatInfo cat : list){
				for(CodModuleMap item :moduleUtil.getModuleList()){
					if(item.getSubmoduleid().equals(cat.getModule().getId()) && !addmodule.containsKey(item.getMainmoduleid())){
						SamModule mod = (SamModule)getHibernateTemplate().get(SamModule.class, item.getMainmoduleid());
						addList.add(new CatInfo(mod, cat.getCategory(),cat.getRootCategory()));
						addmodule.put(item.getMainmoduleid(), item.getMainmoduleid());
					}
				}
			}
			list.addAll(addList);
			// log.debug("List size is " + list.size());
			return list;
		} catch (Throwable t) {
			t.printStackTrace();
			return new ArrayList<CatInfo>();
		}

	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public boolean hasCatInfoId(String modid) {
		final String sql = "select 1 from sam_module_category mc, ( select m.* from"
				+ " sam_module m where m.id = '"+modid+"' and m.id in ( select rm.module_id from sam_role_module rm "
				+ " where rm.role_id in ( select ur.id from sam_taxempcode_role ur,"
				+ " sam_taxempcode u where u.loginname = '"+SecurityManager
				.currentOperator().getUsername()+"' and u.loginname = ur.loginname) ) ) module," +
				" sam_module_category mc1 where mc.id = module.category_id and mc.parentid = mc1.id ";

		List ret = getSession().createSQLQuery(sql).list();
		if(ret.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public boolean hasCatInfoName(String name) {
		final String sql = "select 1 from sam_module_category mc, ( select m.* from"
				+ " sam_module m where m.name = '"+name+"' and m.id in ( select rm.module_id from sam_role_module rm "
				+ " where rm.role_id in ( select ur.id from sam_taxempcode_role ur,"
				+ " sam_taxempcode u where u.loginname = '"+SecurityManager
				.currentOperator().getUsername()+"' and u.loginname = ur.loginname) ) ) module," +
				" sam_module_category mc1 where mc.id = module.category_id and mc.parentid = mc1.id ";

		List ret = getSession().createSQLQuery(sql).list();
		if(ret.size()>0){
			return true;
		}else{
			return false;
		}
	}

	public List<ExtJSTreeNode> genUserMenuTree() {
		List<CatInfo> catInfos = getUserCatInfo();
		ExtJSTreeNode last = null;
		List<ExtJSTreeNode> res = new ArrayList<ExtJSTreeNode>();

		for (CatInfo ci : catInfos) {
			String catName = ci.getCategory().getName();
			String catId = ci.getCategory().getId();
			if (last == null) {
				last = new ExtJSTreeNode(catName, catId, "folder", false);
				last.addChild(new ExtJSTreeNode(ci.getModule().getName(), ci
						.getModule().getUrl(), "file", true));
				res.add(last);
			} else if (last.getId().equals(ci.getCategory().getId())) {
				last.addChild(new ExtJSTreeNode(ci.getModule().getName(), ci
						.getModule().getUrl(), "file", true));
			} else {
				last = new ExtJSTreeNode(catName, catId, "folder", false);
				last.addChild(new ExtJSTreeNode(ci.getModule().getName(), ci
						.getModule().getUrl(), "file", true));
				res.add(last);
			}
		}
		return res;
	}

	private List<ExtJSTreeNode> getOrgByParentId(Integer id,ExtJSTreeNode l,List<ExtJSTreeNode> res,Integer type){
		List<SamTaxorgcode> orgs = new ArrayList<SamTaxorgcode>();
		String hql = "";
		if(type.equals(0)){
			hql = "From SamTaxorgcode Where id = ?";
		}else if(type.equals(1)){
			hql = "From SamTaxorgcode Where parentId = ?";
		}
		orgs = getHibernateTemplate().find(hql,id);
		ExtJSTreeNode last = l;
		for (SamTaxorgcode org : orgs) {
			String orgName = org.getName();
			String orgId = String.valueOf(org.getId());
			if (last == null) {
				if(org.getIsDetail().equals(0)){
					last = new ExtJSTreeNode(orgName, orgId, "folder", false);
					getOrgByParentId(org.getId(),last,res,1);
					res.add(last);
				}else{
					last = new ExtJSTreeNode(orgName, orgId, "file", true);
					res.add(last);
				}
			} else {
				if(org.getIsDetail().equals(0)){
					last = new ExtJSTreeNode(orgName, orgId, "folder", false);
					getOrgByParentId(org.getId(),last,res,1);
					res.add(last);
				}else{
					last.addChild(new ExtJSTreeNode(orgName, orgId, "file", true));
				}
			}
		}
		return res;
	}
	
	public List<ExtJSTreeNode> getOrgMenuTree() {
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		Integer id = user.getOrgId();
		List<ExtJSTreeNode> res = new ArrayList<ExtJSTreeNode>();
		return getOrgByParentId(id,null,res,0);
	}
	
	private List<District> getUserDistrict() {
//		Integer orgId = SecurityManager.currentOperator().getOrgId();
		String disId = SecurityManager.currentOperator().getDistrictId();
		log.debug("currunt user disId: " + disId);
		String[] dists = disId.split(",");
		List ret = new ArrayList();
		for(int i =0 ;i<dists.length;i++){
			List<District> list = getHibernateTemplate().find(
					"from District where id=?",  dists[i]);
			ret.addAll(list);
		}
		return ret;
	}
	
	private List<District> getUserDistrictIgnore() {
//		Integer orgId = SecurityManager.currentOperator().getOrgId();
		String disId = SecurityManager.currentOperator().getDistrictId();
		String[] dists = disId.split(",");
		List ret = new ArrayList();
		for(int i =0 ;i<dists.length;i++){
			List<District> list = getHibernateTemplate().find(
					"from District where id=?", dists[i]);
			ret.addAll(list);
		}
		return ret;
	}
	
	
	/**
	 * 获得当前用户的组织机构
	 * @return
	 */
	private List<SamTaxorgcode> getUserOrganization() {
		Integer orgId = SecurityManager.currentOperator().getOrgId();
		log.debug("currunt user orgId: " + orgId);
		List<SamTaxorgcode> list = getHibernateTemplate().find(
				"from SamTaxorgcode where id=?", orgId);
		return list;
	}
	
	/**
	 * 获得当前用户的组织机构
	 * @return
	 */
	public Map<String ,String> getOrgMap() {
		List<District> list = getHibernateTemplate().find("from District");
		Map<String ,String> map = new HashMap<String ,String>();
		for(District org : list){
			map.put(""+org.getId(), org.getName());
		}
		return map;
	}

	
	
	public List<District> getSelectedUserDistrict() {
//		String disId = SecurityManager.currentOperator().getDistrictId();
		Integer level = SecurityManager.currentOperator().getDistrict().getLevel();
		String disId = "";
		if(level > 3){
			disId = SecurityManager.currentOperator().getDistrict().getParentId();
		}else{
			disId = SecurityManager.currentOperator().getDistrictId();
		}
		
		log.debug("currunt user disId: " + disId);
		List<District> list = getHibernateTemplate().find(
				"from District where id=?", disId);
		return list;
	}
	
	public List<ExtJSTreeNode> getDistricts(String pid,ExtJSTreeNode node,List<ExtJSTreeNode> nodes) {
		List<District> list = getHibernateTemplate().find(
				"from District where parentId = ?", pid);
		for (District district : list) {
			if (district.getOrgId() != null) {
				SamTaxorgcode org = (SamTaxorgcode) getHibernateTemplate().get(
						SamTaxorgcode.class, district.getOrgId());
				if (org != null)
					district.setOrg(org);
			}
			boolean isDetail = district.isIsDetail();
			if(isDetail){
				node.addChild(new ExtJSTreeNode(district.getName(),district.getId(), "file", isDetail));
				//nodes.add(node);
			}else{
				ExtJSTreeNode tmpNode = new ExtJSTreeNode(district.getName(),
						district.getId(), "folder", isDetail);
				node.addChild(tmpNode);
				getDistricts(district.getId(),tmpNode,nodes);
			}
		}
		return nodes;
	}
	
	public List<ExtJSTreeNode> getSelectedUserDistrictNodes(){
		List<ExtJSTreeNode> nodes = new ArrayList<ExtJSTreeNode>();
		List<District> list = getSelectedUserDistrict();
		if(list.size() > 0){
			for (District district : list) {
				boolean isDetail = district.isIsDetail();
				if(isDetail){
					ExtJSTreeNode node = new ExtJSTreeNode(district.getName(),
							district.getId(), "file", isDetail);
					nodes.add(node);
				}else{
					ExtJSTreeNode node = new ExtJSTreeNode(district.getName(),
							district.getId(), "folder", isDetail);
					getDistricts(district.getId(),node,nodes);
					nodes.add(node);
				}
				
			}
		}
		return nodes;
	}
	
	/**
	 * 获得组织机构的树
	 * @param orgOrDistrict
	 * @return
	 */
	public List<ExtJSTreeNode> getOrganizationNodes(String orgOrDistrict) {

		List<ExtJSTreeNode> nodes = new ArrayList<ExtJSTreeNode>();

		log.debug("orgId or DistrictId : " + orgOrDistrict);

		if (orgOrDistrict == null || "org".equalsIgnoreCase(orgOrDistrict)) {
			List<SamTaxorgcode> list = getUserOrganization();
			for (SamTaxorgcode org : list) {
				ExtJSTreeNode node = new ExtJSTreeNode(org.getName(),
						String.valueOf(org.getId()), org.getIsDetail() == 0 ? false : true, org);
				nodes.add(node);
			}
		} else {
			nodes = getOrganization(orgOrDistrict);
		}
		return nodes;
	}
	
	/**
	 * 如果参数是组织机构：字符串：'org' or NULL ，则返回该组织机构所对应的行政区域 如果参数是行政区域Id，则返回其下级
	 * 
	 * @param orgOrDistrict
	 * @return
	 */
	public List<ExtJSTreeNode> getUserDistrictNodes(String orgOrDistrict) {

		List<ExtJSTreeNode> nodes = new ArrayList<ExtJSTreeNode>();

		log.debug("orgId or DistrictId : " + orgOrDistrict);

		if (orgOrDistrict == null || "org".equalsIgnoreCase(orgOrDistrict) || "orgIgnoreDistrict".equalsIgnoreCase(orgOrDistrict)) {
			List<District> list = null;
			if("orgIgnoreDistrict".equalsIgnoreCase(orgOrDistrict))
				list = getUserDistrictIgnore();
			else
				list = getUserDistrict();
			for (District district : list) {
				ExtJSTreeNode node = new ExtJSTreeNode(district.getName(),
						district.getId(), district.isIsDetail(), district);
				nodes.add(node);
			}
		} else {
			nodes = getDistricts(orgOrDistrict);
		}
		return nodes;
	}
	
	public List<ExtJSTreeNodeDefine> getUserDistrictNodesDefine(String orgOrDistrict) {

		List<ExtJSTreeNodeDefine> nodes = new ArrayList<ExtJSTreeNodeDefine>();

		log.debug("orgId or DistrictId : " + orgOrDistrict);

		if (orgOrDistrict == null || "org".equalsIgnoreCase(orgOrDistrict) || "orgIgnoreDistrict".equalsIgnoreCase(orgOrDistrict)) {
			List<District> list = null;
			if("orgIgnoreDistrict".equalsIgnoreCase(orgOrDistrict))
				list = getUserDistrictIgnore();
			else
				list = getUserDistrict();
			for (District district : list) {
				ExtJSTreeNodeDefine node = new ExtJSTreeNodeDefine(district.getName(),
						district.getId(), district.isIsDetail(),false, district);
				nodes.add(node);
			}
		} else {
			nodes = getDistrictsDefine(orgOrDistrict);
		}
		return nodes;
	}

	/**
	 * 根据父节点取得子节点，是递归的 此方法要慎用！！
	 * 
	 * @param pid
	 * @return
	 */
	public List<ExtJSTreeNode> getUserDistrictTree(String pid) {
		List<District> list = getHibernateTemplate().find(
				"from District where parentId = ?", pid);
		List<ExtJSTreeNode> nodes = new ArrayList<ExtJSTreeNode>();
		for (District district : list) {
			List<ExtJSTreeNode> children = getUserDistrictTree(district.getId());
			if (children.size() != 0) {
				ExtJSTreeNode node = new ExtJSTreeNode(district.getName(),
						district.getId(), "folder", false);
				node.addChildren(children);
				nodes.add(node);
			} else {
				ExtJSTreeNode node = new ExtJSTreeNode(district.getName(),
						district.getId(), "file", true);
				nodes.add(node);
			}
		}
		return nodes;
	}

	/**
	 * 根据父节点取得子节点，非递归的
	 * @param pid
	 * @return
	 */
	public List<ExtJSTreeNode> getOrganization(String pid) {
		List<SamTaxorgcode> list = getHibernateTemplate().find(
				"from SamTaxorgcode where parentId = ?", Integer.parseInt(pid));
		List<ExtJSTreeNode> nodes = new ArrayList<ExtJSTreeNode>();
		for (SamTaxorgcode org : list) {
			ExtJSTreeNode node = new ExtJSTreeNode(org.getName(),
					String.valueOf(org.getId()), org.getIsDetail() == 0 ? false : true, org);
			nodes.add(node);
		}
		return nodes;
	}
	
	/**
	 * 根据父节点取得子节点，非递归的
	 * 
	 * @param pid
	 * @return
	 */
	public List<ExtJSTreeNode> getDistricts(String pid) {
		List<District> list = getHibernateTemplate().find(
				"from District where parentId = ?", pid);
		List<ExtJSTreeNode> nodes = new ArrayList<ExtJSTreeNode>();
		for (District district : list) {
			if (district.getOrgId() != null) {
				SamTaxorgcode org = (SamTaxorgcode) getHibernateTemplate().get(
						SamTaxorgcode.class, district.getOrgId());
				if (org != null)
					district.setOrg(org);
			}
			ExtJSTreeNode node = new ExtJSTreeNode(district.getName(),
					district.getId(), district.isIsDetail(), district);
			nodes.add(node);
		}
		return nodes;
	}
	public List<ExtJSTreeNodeDefine> getDistrictsDefine(String pid) {
		List<District> list = getHibernateTemplate().find(
				"from District where parentId = ?", pid);
		List<ExtJSTreeNodeDefine> nodes = new ArrayList<ExtJSTreeNodeDefine>();
		for (District district : list) {
			if (district.getOrgId() != null) {
				SamTaxorgcode org = (SamTaxorgcode) getHibernateTemplate().get(
						SamTaxorgcode.class, district.getOrgId());
				if (org != null)
					district.setOrg(org);
			}
			ExtJSTreeNodeDefine node = new ExtJSTreeNodeDefine(district.getName(),
					district.getId(), district.isIsDetail(),false, district);
			nodes.add(node);
		}
		return nodes;
	}
	/**
	 * 根据父节点取得子节点，非递归的
	 * 
	 * @param pid
	 * @return
	 */
	public List<ExtJSTreeNode> getOrgs(Integer pid) {
		List<SamTaxorgcode> list = getHibernateTemplate().find(
				"from SamTaxorgcode where parentId = ?", pid);
		List<ExtJSTreeNode> nodes = new ArrayList<ExtJSTreeNode>();
		for (SamTaxorgcode org : list) {
			ExtJSTreeNode node = new ExtJSTreeNode(org.getName(), org.getId()
					.toString(), "folder", false, org);
			nodes.add(node);
		}
		return nodes;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public District saveDistrict(District district) {
		log.debug("district: " + district);
		int level = district.getLevel();
		if (level == 6)
			district.setIsDetail(true);
		getHibernateTemplate().saveOrUpdate(district);
		district = (District) getHibernateTemplate().get(District.class,
				district.getId());
		return district;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeDistrict(String districtId) {
		log.debug("district id: " + districtId);
		District district = (District) getHibernateTemplate().get(
				District.class, districtId);
		if (district != null)
			getHibernateTemplate().delete(district);
		return;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public SamTaxorgcode saveOrg(SamTaxorgcode org) {
		org.setIsDetail(1);
		org.setIsOrgDepart(0);
		getHibernateTemplate().saveOrUpdate(org);
		District dis = (District)getHibernateTemplate().get(District.class,
				org.getDistrictNumber());
		dis.setOrgId(org.getId());
		getHibernateTemplate().update(dis);
		org = (SamTaxorgcode) getHibernateTemplate().get(SamTaxorgcode.class,
				org.getId());
		return org;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeOrg(Integer orgId) {
		SamTaxorgcode org = (SamTaxorgcode) getHibernateTemplate().get(
				SamTaxorgcode.class, orgId);
		if (org != null){
			District dis = org.getDistrict();
			dis.setOrgId(0);
			getHibernateTemplate().update(dis);
			getHibernateTemplate().delete(org);
		}
		return;
	}

	public PagingResult<SamTaxorgcode> findOrgs(SamTaxorgcode qryCond,
			PagingParam pp) {
		StringBuilder where = new StringBuilder( " where 1=1 ");
		List params = new ArrayList();
		System.out.println("==========qryCond========="+qryCond);
		System.out.println("=============qryCond.getName()======"+qryCond.getName());
		String name = qryCond.getName();
		if (StringUtils.hasText(name)) {
			System.out.println("==========name========="+name);
//			params.add(name);
			where.append(" and name like '"+name+"%'");
		}

//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder("from SamTaxorgcode").append(
				where).append(" order by id");
		System.out.println("==================="+hql);
		log.debug("hql: " + hql.toString());

		return query(hql, params, pp);
	}

	/**
	 * 特殊的，qryCond中的id，表示所查询的district只能是其本身和其孩子
	 * 
	 * @param qryCond
	 * @param pp
	 * @return
	 */
	public PagingResult<District> findDistricts(District qryCond, PagingParam pp) {
		StringBuilder where = new StringBuilder( " where 1=1 ");
		List params = new ArrayList();

		String districtId = qryCond.getId();
		if (StringUtils.hasText(districtId) && !districtId.equals("0")) {
			String districtPrefix = BusiUtils.trimTailSeq(districtId, "00");
//			params.add(districtPrefix);
			where.append(" and id like '"+districtPrefix+"%' ");
		}

		String name = qryCond.getName();
		if (StringUtils.hasText(name)) {
//			params.add(name);
			where.append(" and name like '"+name+"%' ");
		}

//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder("from District").append(where)
				.append(" order by id");
		log.debug("hql: " + hql.toString());

		return query(hql, params, pp);
	}

	private <T> PagingResult<T> query(StringBuilder hql, List params,		PagingParam pp) {
		try{
		if (pp == null)
			pp = new PagingParam();

		int fromPos = hql.indexOf("from ");
		int orderBy = hql.indexOf(" order by ");
		String countSql = "select count(*) " + hql.substring(fromPos, orderBy);
		log.debug("countSql: " + countSql);

//		Query q = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(countSql.toString());
//		for (int i = 0; i < params.size(); i++) {
//			q.setParameter(i, params.get(i));
//		}
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
		return new PagingResult<T>(totalSize, list);
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	public PagingResult<HealthFile> findHealthFiles(HealthFileQry qryCond,
			PagingParam pp)throws Exception {
		try{
		List params = new ArrayList();
		StringBuilder hql = buildHealthHql(qryCond, params,new StringBuilder());
		System.out.println("==findHealthFiles===="+hql);
		return queryHealthFiles(pp, params, hql);
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}

	public PagingResult<HealthFile> findHealthFilesEnableBuild(HealthFileQry qryCond,
			PagingParam pp) throws Exception{
		List params = new ArrayList();
		StringBuilder hql = buildHealthHql(qryCond, params,new StringBuilder().append(" and b.sex = '女' and b.birthday < convert(datetime,convert(nvarchar,(YEAR(getdate())-9))+'-01-01') And a.fileNo not in(Select Distinct fileNo From HealthFileMaternal Where isClosed = '0' )"));
		return queryHealthFiles(pp, params, hql);
	}
	
	public PagingResult<HealthFile> queryHealthFiles(PagingParam pp,
			List params, StringBuilder hql) {
		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<HealthFile> files = new ArrayList<HealthFile>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			getHibernateTemplate().evict(file);
			PersonalInfo person = (PersonalInfo) objs[1];
			getHibernateTemplate().evict(person);
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getIdnumber()));
				tmpFileNo = file.getFileNo();
			}		
			this.getOrgDistrictName(file.getFileNo());
			person.setHomeId(orgnameMap.get(file.getFileNo().substring(0,2)+"0000")+"\n"+orgnameMap.get(file.getFileNo().substring(0,4)+"00")+"\n"+orgnameMap.get(file.getFileNo().substring(0,6)));
			file.setPersonalInfo(person);
			files.add(file);
		}
		PagingResult<HealthFile> result = new PagingResult<HealthFile>(
				p.getTotalSize(), files);
		return result;
	}

	private StringBuilder buildHealthHql(HealthFileQry qryCond, List params,StringBuilder otherParams)throws Exception {
		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhereHealthFile(qryCond, params, where,0);
		
		where.append(otherParams.toString());
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b").append(where).append(
				" order by a.inputDate DESC");
		log.debug("hql: " + hql.toString());
		return hql;
	}
	
	public PagingResult<Map<String, Object>> findHealthfilesAlreadyBuild(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		List params = new ArrayList();
		StringBuilder hql = buildHealthAlreadyBuildHql(qryCond, params);

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			getHibernateTemplate().evict(file);
			HealthFileMaternal maternal = (HealthFileMaternal) objs[1];
			getHibernateTemplate().evict(maternal);
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				getHibernateTemplate().evict(file.getPersonalInfo());
				file.getPersonalInfo().setIdnumber(EncryptionUtils.decipher(file.getPersonalInfo().getIdnumber()));
				maternal.setIdnumber(EncryptionUtils.decipher(maternal.getIdnumber()));
				tmpFileNo = file.getFileNo();
			}
			
			Map map = new HashMap();
			map.put("file", file);
			map.put("maternal", maternal);
			List<FirstVistBeforeBorn> fvb = getHibernateTemplate().find(" from FirstVistBeforeBorn where foreignId = '"+maternal.getId()+"' ");
			if(fvb.size()>0){
				map.put("duedate", fvb.get(0).getEdc());
			}else{
				map.put("duedate", null);
			}
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);
		return result;

	}
	
	private StringBuilder buildHealthAlreadyBuildChildHql(HealthFileQry qryCond, List params)throws Exception {
		StringBuilder where = new StringBuilder( " where a.fileNo = c.fileNo ");
		Gson gs = new Gson();
		if(qryCond.getParams().containsKey("child_status")){
			String value = (String)qryCond.getParams().get("child_status");
			//DateUtils.toCalendar(new Date()).get(Calendar.YEAR) -  DateUtils.toCalendar(maternal.getBirthday()).get(Calendar.YEAR) >7
			if("0".equals(value.trim())){
				where.append(" and b.birthday >= convert(datetime,convert(nvarchar,(YEAR(getdate())-7))+'-01-01')  ");
			}else if("1".equals(value.trim())){
				where.append(" and b.birthday < convert(datetime,convert(nvarchar,(YEAR(getdate())-7))+'-01-01')  ");
			}
		}
		buildGeneralWhereHealthFile(qryCond, params, where,0);		
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a ,  PersonalInfo b,HealthFileChildren c ").append(where).append(
				" order by a.name ASC");
		log.debug("hql: " + hql.toString());
		return hql;
	}
	public PagingResult<Map<String, Object>> findHealthfilesAlreadyBuildChild(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		List params = new ArrayList();
		StringBuilder hql = buildHealthAlreadyBuildChildHql(qryCond, params);
		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			HealthFileChildren maternal = (HealthFileChildren) objs[2];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
//				maternal.setIdnumber(EncryptionUtils.decipher(maternal.getIdnumber()));
				tmpFileNo = file.getFileNo();
			}
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(maternal);
			Map map = new HashMap();
			map.put("file", file);
			map.put("children", maternal);
			map.put("status", DateUtils.toCalendar(new Date()).get(Calendar.YEAR) -  DateUtils.toCalendar(maternal.getBirthday()).get(Calendar.YEAR) >7 ?"结案":"未结案");
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);
		return result;

	}
	public PagingResult<Map<String, Object>> findHealthfilesFinishGestation(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		List params = new ArrayList();
		StringBuilder where = new StringBuilder(" where 1=1 ");
		buildGeneralWhereHealthFile(qryCond, params, where,0);
		where.append(" and b.id = c.healthFileMaternalId and b.isClosed = '2' ");
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, HealthFileMaternal b,FinishGestation c ").append(where).append(
				" order by a.name ASC");
		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			HealthFileMaternal maternal = (HealthFileMaternal) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				maternal.setIdnumber(EncryptionUtils.decipher(maternal.getIdnumber()));
				tmpFileNo = file.getFileNo();
			}
			FinishGestation gestation = (FinishGestation)objs[2];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(maternal);
			getHibernateTemplate().evict(gestation);
			Map map = new HashMap();
			map.put("file", file);
			map.put("maternal", maternal);
			map.put("gestation", gestation);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);
		return result;

	}
	
	private StringBuilder buildHealthAlreadyBuildHql(HealthFileQry qryCond, List params)throws Exception {
		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhereHealthFile(qryCond, params, where,0);
		if(!qryCond.getFilterVal01().equals("100")){
			where.append(" And b.isClosed = ? ");
			params.add(qryCond.getFilterVal01());
		}
		
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, HealthFileMaternal b ").append(where).append(
				" order by a.name ASC");
		log.debug("hql: " + hql.toString());
		return hql;
	}
	
	private void buildGeneralWhereHealthFile(HealthFileQry qryCond, List params,
			StringBuilder where,Integer status) throws Exception{
		String districtId = qryCond.getDistrict();
		while(districtId.endsWith("00")){
			districtId = districtId.substring(0,districtId.length()-2);
		}
		if (StringUtils.hasText(districtId)) {
//			params.add(districtId);
			where.append(" and a.districtNumber like '"+districtId+"%' ");
		}
		genQueryParams(qryCond, params, where);

		where.append(" and a.fileNo = b.fileNo and a.status =  " + status );
	}

	private void genQueryParams(HealthFileQry qryCond, List params,
			StringBuilder where) throws Exception{
		String filterKey = qryCond.getFilterKey();
		if (StringUtils.hasText(filterKey)) {
			String filterValue = qryCond.getFilterValue();
			if(filterKey.equals("a.name") || filterKey.equals("a.fileNo") || filterKey.equals("b.idnumber")){
				filterValue = EncryptionUtils.encry(filterValue);
			}
			if(filterKey.equals("a.inputDate") || filterKey.equals("b.birthday") || filterKey.equals("a.lastModifyDate") || filterKey.equals("c.edc") || filterKey.equals("b.inputDate")){
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
				try {
					String startDate = null;
					String endDate = null;
					if(filterValue.indexOf("-") > 0){
						String[] valArray = filterValue.split("-");
						if(valArray.length > 2){
							throw new Exception("请输入正确的日期范围，如：20120101-20120102或者20120101。");
						}
						startDate = valArray[0] + " 00:00:00.000";
						endDate = valArray[1] + " 23:59:59.999";
					}else{
						startDate = filterValue + " 00:00:00.000";
						endDate = filterValue + " 23:59:59.999";
					}
					//params.add(format.parse(startDate));
					//params.add(format.parse(endDate));
					if(filterKey.equals("c.edc")){
						where.append(" and exists( select 1 from FirstVistBeforeBorn c where c.foreignId = b.id and  c.edc >= ? and c.edc <= ? )");
					}else{
						where.append(" and " + filterKey + " >= '"+(startDate)+"' and " + filterKey + " <= '"+(endDate)+"' ");
					}
				} catch (ParseException e) {
					throw new Exception("请输入正确的日期范围，如：20120101-20120102或者20120101。");
				}				
			}else if(filterKey.equals("b.idnumber")){
				if (StringUtils.hasText(filterValue)) {
//					params.add(filterValue);
					where.append(" and " + filterKey + " like '"+filterValue+"%'");
				}
			}else{
				if (StringUtils.hasText(filterValue)) {
//					params.add(filterValue);
					where.append(" and " + filterKey + " like '%"+filterValue+"%'");
				}
			}
		}
		if(qryCond.getParams() !=null && qryCond.getParams().size()>0){
			for(Iterator iter = qryCond.getParams().keySet().iterator();iter.hasNext();){
				String key = (String)iter.next();
				String value = (String)qryCond.getParams().get(key);
				TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
				System.out.println(key+"======"+value);
				if("onlyself".equals(key) && "true".equals(value)){
					System.out.println("====user.getTaxempname()=="+user.getTaxempname());
					System.out.println("====getUsername=="+user.getUsername());
					where.append(" and a.inputPersonId = convert(nvarchar,'"+ user.getUsername()+"') ");
				}
				if("onlyorg".equals(key) && "true".equals(value)){
					where.append(" and a.inputPersonId in ( select loginname from SamTaxempcode where orgId = "+ user.getOrgId() +") ");
				}
				if("org_id".equals(key) && !"".equals(value.trim())){
					where.append(" and a.inputPersonId in ( select loginname from SamTaxempcode where orgId = "+ value +") ");
				}
				if("noexam".equals(key) && "true".equals(value.trim())){
					where.append(" and not exists ( select 1 from MedicalExam where fileNo = a.fileNo  ) ");
				}
				
			}
		}
		System.out.println("============"+where);
	}

	private void buildGeneralWhere(HealthFileQry qryCond, List params,
			StringBuilder where)throws Exception {
		String districtId = makeDistrict(qryCond.getDistrict());
		if (StringUtils.hasText(districtId)) {
//			params.add(districtId + '%');
//			params.add(districtId + '%');
//			where.append(" and (a.districtNumber like '"+districtId +"%' or c.execDistrictNum like '"+districtId +"%' ) ");
			where.append(" and (a.districtNumber like '"+districtId +"%' ) ");
		}
		String filterKey = qryCond.getFilterKey();
		if (StringUtils.hasText(filterKey)) {
			String filterValue = qryCond.getFilterValue();
			if(filterKey.equals("a.name") || filterKey.equals("a.fileNo")){
				filterValue = EncryptionUtils.encry(filterValue);
//				params.add(filterValue);
				where.append(" and " + filterKey + " like '"+filterValue+"%'");
			}else if(filterKey.equals("a.inputDate") || filterKey.equals("b.birthday") || filterKey.equals("a.lastModifyDate")){
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
				try {
					String startDate = null;
					String endDate = null;
					if(filterValue.indexOf("-") > 0){
						String[] valArray = filterValue.split("-");
						if(valArray.length > 2){
							throw new Exception("请输入正确的日期范围，如：20120101-20120102或者20120101。");
						}
						startDate = valArray[0] + " 00:00:00.000";
						endDate = valArray[1] + " 23:59:59.999";
					}else{
						startDate = filterValue + " 00:00:00.000";
						endDate = filterValue + " 23:59:59.999";
					}
//					params.add(format.parse(startDate));
//					params.add(format.parse(endDate));
					if(filterKey.equals("a.inputDate"))
						filterKey = "c.inputDate";
					where.append(" and " + filterKey + " >= '"+(startDate)+"' and " + filterKey + "<= '"+(endDate)+"' ");
				} catch (ParseException e) {
					throw new Exception("请输入正确的日期范围，如：20120101-20120102或者20120101。");
				}
			}else if(filterKey.equals("c.highRisk")){
				if (StringUtils.hasText(filterValue)) {
//					params.add(filterValue);
					where.append(" and " + filterKey + " = '"+filterValue+"' ");
				}
			}else{
				if (StringUtils.hasText(filterValue)) {
//					params.add(filterValue);
					where.append(" and " + filterKey + " like '"+filterValue+"%'");
				}
			}
		}
		
		if(qryCond.getParams() !=null && qryCond.getParams().size()>0){
			for(Iterator iter = qryCond.getParams().keySet().iterator();iter.hasNext();){
				String key = (String)iter.next();
				String value = (String)qryCond.getParams().get(key);
				TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
				System.out.println(key+"======"+value);
				if("onlyself".equals(key) && "true".equals(value)){
					System.out.println("====user.getTaxempname()=="+user.getTaxempname());
					System.out.println("====getUsername=="+user.getUsername());
					where.append(" and a.inputPersonId = convert(nvarchar,'"+ user.getUsername()+"') ");
				}
				if("onlyorg".equals(key) && "true".equals(value)){
					where.append(" and a.inputPersonId in ( select loginname from SamTaxempcode where orgId = "+ user.getOrgId() +") ");
				}
				if("org_id".equals(key) && !"".equals(value.trim())){
					where.append(" and a.inputPersonId in ( select loginname from SamTaxempcode where orgId = "+ value +") ");
				}
			}
		}

		where.append(" and a.fileNo = b.fileNo and a.status = 0 ");
	}

	public PagingResult<HealthFile> findChildHealthFiles(HealthFileQry qryCond,
			PagingParam pp) throws Exception{
		List params = new ArrayList();
		StringBuilder hql = buildChildHealthHql(qryCond, params);
		return queryHealthFiles(pp, params, hql);
	}
	
	//取得省市名称
	public void getOrgDistrictName(String orgid) {
		try{
			System.out.println("==进入 "+orgid);
			if(!orgnameMap.containsKey(orgid.substring(0,2)+"0000") || !orgnameMap.containsKey(orgid.substring(0,4)+"00") || !orgnameMap.containsKey(orgid.substring(0,6))){
				System.out.println("from District where id = '"+ orgid.substring(0,2)+"0000"+"' or id = '"+orgid.substring(0, 4)+"00"+"' order by level");
				List<District> list = getHibernateTemplate().find(
						"from District where id = '"+ orgid.substring(0,2)+"0000"+"' or id = '"+orgid.substring(0, 4)+"00"+"' or id = '"+orgid.substring(0, 6)+"' order by level");
				String[] orgname = new String[list.size()];
				int i = 0 ; 
				System.out.println("=size="+list.size());
				for (District district : list) {
					orgname[i++]= district.getName();
					System.out.println("=="+district.getName());
				}
				if(orgname.length >= 3){
					orgnameMap.put(orgid.substring(0,2)+"0000", orgname[0]);
					orgnameMap.put(orgid.substring(0,4)+"00", orgname[1]);
					orgnameMap.put(orgid.substring(0,6), orgname[2]);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 查询儿童打印档案信息
	 * @param qryCond
	 * @param pp
	 * @return
	 */
	public PagingResult<Map<String, Object>> findChildPrint1(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		List params = new ArrayList();
		StringBuilder where = new StringBuilder(  " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);
		where.append(" and a.fileNo = g.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");
		where.append(" and c.certifiId  = g.certifiId ");
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql;
		if(qryCond.getIsFirst() == 0){
			hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, ChildBirthRecord c, SamTaxempcode d,SamTaxorgcode e , BirthCertificate g ")
				.append(where).append(" and CONVERT(varchar(10),c.nextVistDate,112) = CONVERT(varchar(10),getDate(),112) order by a.fileNo");
		}else{
			hql = new StringBuilder(
					"from HealthFile a, PersonalInfo b, ChildBirthRecord c, SamTaxempcode d,SamTaxorgcode e , BirthCertificate g ")
				.append(where).append(" order by a.fileNo");
		}
		log.debug("hql: " + hql.toString());
		System.out.println("=="+hql);
		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
				tmpFileNo = file.getFileNo();
			}
			ChildBirthRecord birth = (ChildBirthRecord) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			BirthCertificate cert = (BirthCertificate) objs[5];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(birth);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("birth", birth);
			map.put("emp", samTaxempcode);
			map.put("org", samTaxorgcode);
			map.put("cert", cert);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}
	/**
	 * 查询儿童档案信息
	 * @param qryCond
	 * @param pp
	 * @return
	 */
	public PagingResult<Map<String, Object>> findChildPrint2(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");

//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, BabyVisit c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());
		System.out.println("=="+hql);
		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				tmpFileNo = file.getFileNo();
			}
			PersonalInfo person = (PersonalInfo) objs[1];
			BabyVisit babyVisit = (BabyVisit) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(babyVisit);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("child", babyVisit);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}

	public PagingResult<HealthFile> findWomanBirthHealthFiles(HealthFileQry qryCond,
			PagingParam pp) throws Exception{
		List params = new ArrayList();
		StringBuilder hql = buildWomanBirthHealthHql(qryCond, params);
		return queryHealthFiles(pp, params, hql);
	}
	
	public PagingResult<HealthFile> findHypHealthFiles(HealthFileQry qryCond,
			PagingParam pp)throws Exception {
		List params = new ArrayList();
		StringBuilder hql = buildDiseaseHealthHql(qryCond, params, DISEASE_HYP);
		return queryHealthFiles(pp, params, hql);
	}

	public PagingResult<HealthFile> findFuriosusHealthFiles(
			HealthFileQry qryCond, PagingParam pp) throws Exception{
		List params = new ArrayList();
		StringBuilder hql = buildDiseaseHealthHql(qryCond, params, DISEASE_FURI);
		return queryHealthFiles(pp, params, hql);
	}

	public PagingResult<HealthFile> findDiabetesHealthFiles(
			HealthFileQry qryCond, PagingParam pp) throws Exception{
		List params = new ArrayList();
		StringBuilder hql = buildDiseaseHealthHql(qryCond, params, DISEASE_DIAB);
		return queryHealthFiles(pp, params, hql);
	}

	private StringBuilder buildWomanBirthHealthHql(HealthFileQry qryCond, List params) throws Exception{
		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhereHealthFile(qryCond, params, where,0);

		where.append(" and ( b.bornStatus = '是' or b.homeId = '曾经') ");
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b").append(where).append(
				" order by a.fileNo");
		log.debug("hql: " + hql.toString());
		return hql;
	}
	
	private StringBuilder buildChildHealthHql(HealthFileQry qryCond, List params) throws Exception{
		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhereHealthFile(qryCond, params, where,0);

		Timestamp childAge = BusiUtils.getChildAge();

		where.append(" and b.birthday >= ?");
		params.add(childAge);

//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b").append(where).append(
				" order by a.fileNo");
		log.debug("hql: " + hql.toString());
		return hql;
	}

	private StringBuilder buildHypHealthHql(HealthFileQry qryCond, List params)throws Exception {
		StringBuilder where = new StringBuilder(  " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and c.diseaseId = "+DISEASE_HYP+"");
//		params.add(DISEASE_HYP);

		where.append(" and c.personalInfoId = b.id");
		
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, DiseaseHistory c").append(
				where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());
		return hql;
	}

	private StringBuilder buildDiseaseHealthHql(HealthFileQry qryCond,
			List params, Integer DiseaseType)throws Exception {
		StringBuilder where = new StringBuilder(" where 1=1 ");
		buildGeneralWhereHealthFile(qryCond, params, where,0);

//		where.append(" and c.diseaseId = ?");
		where.append(" and exists (select 1 from DiseaseHistory  where   personalInfoId = b.id and diseaseId = '"+DiseaseType+"') ");
//		params.add(DiseaseType);
		
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b ").append(
				where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());
		return hql;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeHealthFile(String fileNo,Integer type) throws Exception{//0表示需要验证登录用户与录入人是否一致,1表示不需要验证
		HealthFile file = (HealthFile) getHibernateTemplate().get(
				HealthFile.class, fileNo);
		if (file != null) {
			List personList = getHibernateTemplate().find(
					"from PersonalInfo where fileNo = ?", fileNo);
			if(type.equals(0)){
				if(SecurityManager.isValidUser(file.getInputPersonId(),getSession())){
					BusiUtils.insertLog(this,file.getFileNo(),"delete","healthfile",cn.net.tongfang.framework.security.SecurityManager.currentOperator().getUsername(),file,null);
					BusiUtils.insertLog(this,file.getFileNo(),"delete","personalinfo",cn.net.tongfang.framework.security.SecurityManager.currentOperator().getUsername(),file.getPersonalInfo(),null);
					getHibernateTemplate().deleteAll(personList);
					getHibernateTemplate().delete(file);
					getHibernateTemplate().bulkUpdate(" update HealthFileHistory2 "+
							" set linkFileno = null "+
							"  "+ 
							" where linkFileno = '"+EncryptionUtils.decipher(fileNo)+"'" );
				}else{
					throw new Exception("不是本机构的档案,不允许删除!");
				}
			}else if(type.equals(1)){
				BusiUtils.insertLog(this,file.getFileNo(),"delete","healthfile",cn.net.tongfang.framework.security.SecurityManager.currentOperator().getUsername(),file,null);
				BusiUtils.insertLog(this,file.getFileNo(),"delete","personalinfo",cn.net.tongfang.framework.security.SecurityManager.currentOperator().getUsername(),file.getPersonalInfo(),null);
				getHibernateTemplate().deleteAll(personList);
				getHibernateTemplate().delete(file);
				getHibernateTemplate().bulkUpdate(" update HealthFileHistory2 "+
						" set linkFileno = null "+
						"  "+ 
						" where linkFileno = '"+EncryptionUtils.decipher(fileNo)+"'" );
			}
		}

		return;
	}

	public void removeHealthFiles(List<String> fileNoList) throws Exception{
		for (String fileNo : fileNoList) {
			fileNo = EncryptionUtils.encry(fileNo);
			removeHealthFile(fileNo,0);
		}
		return;
	}

	/**
	 * 原密码不正确，返回失败，新密码不匹配也返回失败
	 * 
	 * @param pwd
	 * @param newPwd
	 * @param newPwd2
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean changePwd(String pwd, String newPwd, String newPwd2) {
		TaxempDetail o = SecurityManager.currentOperator();
		SamTaxempcode user = (SamTaxempcode) getHibernateTemplate().get(
				SamTaxempcode.class, o.getUsername());
		log.debug("input password: " + pwd);
		log.debug("old password: " + user.getPassword());

		if (pwd.equals(user.getPassword())) {
			if (newPwd.equals(newPwd2)) {
				user.setPassword(newPwd);
				getHibernateTemplate().update(user);
			} else {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}
	
	@Transactional
	private void removeRecords(final List<String> recordIdList,
			final Class clazz) throws Exception {
		if (recordIdList == null || recordIdList.size() == 0) {
			return;
		}

		final String pkName = "id";

		String hql = "delete from " + clazz.getName() + " where "
				+ pkName + " = ?";
		for (String id : recordIdList) {
			String inputPersonId = (String)getSession().createQuery("Select inputPersonId From " 
					+ clazz.getName() + " where " + pkName + " = ?").setParameter(0, id).list().get(0);
			if(SecurityManager.isValidUser((inputPersonId),getSession())){
				getSession().createQuery(hql).setParameter(0, id)
					.executeUpdate();
			}else{
				throw new Exception("不是本机构的数据,不允许删除!");
			}
		}
	}

	public void removeBabyVisitRecords(final List<String> recordIdList)throws Exception {
		removeRecords(recordIdList, BabyVisit.class);
	}

	public void removeChildExamRecords(final List<String> recordIdList)throws Exception {
		removeRecords(recordIdList, ChildrenMediExam.class);
	}

	public void removeChildExam3Records(final List<String> recordIdList)throws Exception {
		removeRecords(recordIdList, ChildrenMediExam3.class);
	}

	public PagingResult<Map<String, Object>> findBabyVisitRecords(
			HealthFileQry qryCond, PagingParam pp) throws Exception{
		List params = new ArrayList();

		StringBuilder where = new StringBuilder(" where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");

		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, BabyVisit c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());
		System.out.println("=findBabyVisitRecords="+hql);
		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				tmpFileNo = file.getFileNo();
			}
			PersonalInfo person = (PersonalInfo) objs[1];
			BabyVisit babyVisit = (BabyVisit) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(babyVisit);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("child", babyVisit);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}
	
	public PagingResult<Map<String, Object>> findBabyVisitPrint(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder(  " where 1=1 " );
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");

//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, BabyVisit c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());
		System.out.println("=findBabyVisitRecords="+hql);
		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				tmpFileNo = file.getFileNo();
			}
			PersonalInfo person = (PersonalInfo) objs[1];
			BabyVisit babyVisit = (BabyVisit) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(babyVisit);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("child", babyVisit);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			map.put("babyDirect", getPrintBasicInfo(babyVisit.getId(),"BabyDirect","babyDirectId","babyVisitId"));
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}

	public PagingResult<Map<String, Object>> findChildExam1Records(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		return findChildExamRecords(qryCond, pp, 0);
	}

	public PagingResult<Map<String, Object>> findChildExam2Records(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		return findChildExamRecords(qryCond, pp, 1);
	}

	private PagingResult<Map<String, Object>> findChildExamRecords(
			HealthFileQry qryCond, PagingParam pp, Integer type) throws Exception{
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		if (type != null) {
			where.append(" and c.dataType = '"+type+"'");
//			params.add(type);
		}

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, ChildrenMediExam c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				tmpFileNo = file.getFileNo();
			}
			PersonalInfo person = (PersonalInfo) objs[1];
			ChildrenMediExam childrenMediExam = (ChildrenMediExam) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(childrenMediExam);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("child", childrenMediExam);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}
	
	public PagingResult<Map<String, Object>> findChildExam1print(
			HealthFileQry qryCond, PagingParam pp) throws Exception{
		List params = new ArrayList();

		StringBuilder where = new StringBuilder(  " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and c.dataType = ?");
		params.add(0);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and c.id = f.id ");
		where.append(" and d.orgId = e.id");
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, ChildrenMediExam c, SamTaxempcode d,SamTaxorgcode e ,WomanPhysicalItems f")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				tmpFileNo = file.getFileNo();
			}
			PersonalInfo person = (PersonalInfo) objs[1];
			ChildrenMediExam childrenMediExam = (ChildrenMediExam) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			WomanPhysicalItems child1 = (WomanPhysicalItems) objs[5];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(childrenMediExam);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			getHibernateTemplate().evict(child1);
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("child", childrenMediExam);
			map.put("child1",child1);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			map.put("childrenMediExamExam09", getPrintBasicInfo(childrenMediExam.getId(),"ChildrenMediExamExam09","childrenMediExamExam09id","childrenMediExamId"));
			map.put("childrenMediExamExam10", getPrintBasicInfo(childrenMediExam.getId(),"ChildrenMediExamExam10","childrenMediExamExam10id","childrenMediExamId"));
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;
	}
	
	public PagingResult<Map<String, Object>> findChildExam2print(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and c.dataType = ?");
		params.add(1);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and c.id = f.id ");
		where.append(" and d.orgId = e.id");
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, ChildrenMediExam c, SamTaxempcode d,SamTaxorgcode e ,WomanPhysicalItems f")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				tmpFileNo = file.getFileNo();
			}
			PersonalInfo person = (PersonalInfo) objs[1];
			ChildrenMediExam childrenMediExam = (ChildrenMediExam) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			WomanPhysicalItems child1 = (WomanPhysicalItems) objs[5];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(childrenMediExam);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			getHibernateTemplate().evict(child1);
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("child", childrenMediExam);
			map.put("child1",child1);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			map.put("childrenMediExamExam09", getPrintBasicInfo(childrenMediExam.getId(),"ChildrenMediExamExam09","childrenMediExamExam09id","childrenMediExamId"));
			map.put("childrenMediExamExam10", getPrintBasicInfo(childrenMediExam.getId(),"ChildrenMediExamExam10","childrenMediExamExam10id","childrenMediExamId"));
			map.put("checkDirect", getPrintBasicInfo(childrenMediExam.getId(),"CheckDirect","checkDirectId","childrenMediExamId"));
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;
	}
	
	public PagingResult<Map<String, Object>> findChildExam3print(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);


		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and c.id = f.id ");
		where.append(" and d.orgId = e.id");
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, ChildrenMediExam36 c, SamTaxempcode d,SamTaxorgcode e ,WomanPhysicalItems f")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				tmpFileNo = file.getFileNo();
			}
			PersonalInfo person = (PersonalInfo) objs[1];
			ChildrenMediExam36 childrenMediExam = (ChildrenMediExam36) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			WomanPhysicalItems child1 = (WomanPhysicalItems) objs[5];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(childrenMediExam);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			getHibernateTemplate().evict(child1);
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("child", childrenMediExam);
			map.put("child1",child1);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			map.put("childrenMediExamExam09", getPrintBasicInfo(childrenMediExam.getId(),"ChildrenMediExamExam09","childrenMediExamExam09id","childrenMediExamId"));
			map.put("childrenMediExamExam10", getPrintBasicInfo(childrenMediExam.getId(),"ChildrenMediExamExam10","childrenMediExamExam10id","childrenMediExamId"));
			map.put("checkDirect", getPrintBasicInfo(childrenMediExam.getId(),"CheckDirect36","checkDirect36id","childrenMediExam36id"));
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;
	}
	
	public PagingResult<Map<String, Object>> findChildExam3Records(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		List params = new ArrayList();
		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);
		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");

//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, ChildrenMediExam3 c, SamTaxempcode d")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				tmpFileNo = file.getFileNo();
			}
			PersonalInfo person = (PersonalInfo) objs[1];
			ChildrenMediExam3 childrenMediExam3 = (ChildrenMediExam3) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(childrenMediExam3);
			getHibernateTemplate().evict(samTaxempcode);

			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("child", childrenMediExam3);
			map.put("samTaxempcode", samTaxempcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;
	}

	public PagingResult<Map<String, Object>> findMedicalExamRecords(
			HealthFileQry qryCond, PagingParam pp) throws Exception{
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, MedicalExam c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		String tmpFileNo = "";
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				tmpFileNo = file.getFileNo();
			}
			PersonalInfo person = (PersonalInfo) objs[1];
			MedicalExam medicalExam = (MedicalExam) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(medicalExam);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);

			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("medicalExam", medicalExam);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}

	public void removeMedicalExamRecords(List ids) throws Exception{
		removeRecords(ids, MedicalExam.class);
	}

	public PagingResult<Map<String, Object>> findHypVisitRecords(
			HealthFileQry qryCond, PagingParam pp) throws Exception{
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql;
		if(qryCond.getIsFirst() == 0){
			hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, HypertensionVisit c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" and CONVERT(varchar(10),c.nextVistDate,112) = CONVERT(varchar(10),getDate(),112) order by a.fileNo");
		}else{
			hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, HypertensionVisit c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		}
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
				tmpFileNo = file.getFileNo();
			}
			HypertensionVisit hypVisit = (HypertensionVisit) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(hypVisit);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("hyp", hypVisit);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}

	public void removeHypVisitRecords(List ids) throws Exception{
		removeRecords(ids, HypertensionVisit.class);
	}

	public PagingResult<Map<String, Object>> findDiabVisitRecords(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql;
		if(qryCond.getIsFirst() == 0){
			hql = new StringBuilder(
					"from HealthFile a, PersonalInfo b, DiabetesVisit c, SamTaxempcode d,SamTaxorgcode e")
					.append(where).append(" and CONVERT(varchar(10),c.nextVistDate,112) = CONVERT(varchar(10),getDate(),112) order by a.fileNo");
		}else{
			hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, DiabetesVisit c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		}
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
				tmpFileNo = file.getFileNo();
			}
			DiabetesVisit DiabVisit = (DiabetesVisit) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(DiabVisit);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);

			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("diab", DiabVisit);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}

	public void removeDiabVisitRecords(List ids)throws Exception {
		removeRecords(ids, DiabetesVisit.class);
	}

	public PagingResult<Map<String, Object>> findFuriousVisitRecords(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql;
		if(qryCond.getIsFirst() == 0){
			hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, FuriousVisit c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" and CONVERT(varchar(10),c.nextVistDate,112) = CONVERT(varchar(10),getDate(),112) order by a.fileNo");
		}else{
			hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, FuriousVisit c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		}
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
				tmpFileNo = file.getFileNo();
			}
			FuriousVisit furiousVisit = (FuriousVisit) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(furiousVisit);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);

			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("furiousVisit", furiousVisit);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}

	public void removeFuriousVisitRecords(List ids)throws Exception {
		removeRecords(ids, FuriousVisit.class);
	}

	public PagingResult<Map<String, Object>> findFuriousInfoRecords(
			HealthFileQry qryCond, PagingParam pp) throws Exception{
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, FuriousInfo c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
				tmpFileNo = file.getFileNo();
			}
			FuriousInfo furiousInfo = (FuriousInfo) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(furiousInfo);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("furiousInfo", furiousInfo);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}

	public void removeFuriousInfoRecords(List ids)throws Exception {
		removeRecords(ids, FuriousInfo.class);
	}

	public PagingResult<Map<String, Object>> findFirstVisitRecords(
			HealthFileQry qryCond, PagingParam pp) throws Exception{
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);
		
		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, FirstVistBeforeBorn c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());
		System.out.println("=="+hql);
		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getIdnumber()));
				tmpFileNo = file.getFileNo();
			}
			
			FirstVistBeforeBorn firstVist = (FirstVistBeforeBorn) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];	
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(firstVist);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			this.getOrgDistrictName(file.getFileNo());
			person.setHomeId(orgnameMap.get(file.getFileNo().substring(0,2)+"0000")+"\n"+orgnameMap.get(file.getFileNo().substring(0,4)+"00")+"\n"+orgnameMap.get(file.getFileNo().substring(0,6)));
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("firstVisit", firstVist);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			FirstVisitBeforeBornPrintBO feme = new FirstVisitBeforeBornPrintBO();
			String femePastHistory = getPrintBasicInfo(firstVist.getId(),"FemePastHistory","femePastHistoryId","firstVistBeforeBornId");
			String femeFamilyHistory = getPrintBasicInfo(firstVist.getId(),"FemeFamilyHistory","femeFamilyHistoryId","firstVistBeforeBornId");
			String femeSecretion = getPrintBasicInfo(firstVist.getId(),"FemeSecretion","femeSecretionId","firstVistBeforeBornId");
			String personalHistory = getPrintBasicInfo(firstVist.getId(),"PersonalHistory","personalHistoryId","firstVistBeforeBornId");
			String allergiesHistory = getPrintBasicInfo(person.getId(),"AllergiesHistory","allergiesId","personalInfoId");
			feme.setFemePastHistory(femePastHistory);
			feme.setFemeFamilyHistory(femeFamilyHistory);
			feme.setFemeSecretion(femeSecretion);
			feme.setExam01(personalHistory);
			feme.setExam02(allergiesHistory);
			StringBuilder hql1 = new StringBuilder(
					"from BloodTrans ")
					.append(" where personalInfoId = '"+person.getId()+"' ").append(" order by transDate desc ");
			List<BloodTrans> bloodTrans = getHibernateTemplate().find(hql1.toString());
			if(bloodTrans.size()>0){
				feme.setExam03(bloodTrans.get(0).getTransDate());
			}else{
				feme.setExam03("无");
			}
			String beforeBornCheckDirect = getPrintBasicInfo(firstVist.getId(),"BeforeBornCheckDirect","beforeBornCheckDirectId","firstVistBeforeBornId");
			feme.setExam04(beforeBornCheckDirect);
			map.put("feme", feme);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;
	}
	
	public String getPrintBasicInfo(String id,String tableName,String key,String tableKey)throws Exception{
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

	public void removeFirstVisitRecords(List ids)throws Exception {
		removeRecords(ids, FirstVistBeforeBorn.class);
	}

	public PagingResult<Map<String, Object>> findVisitBeforeBornRecords(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, VisitBeforeBorn c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
				tmpFileNo = file.getFileNo();
			}
			VisitBeforeBorn visitBeforeBorn = (VisitBeforeBorn) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(visitBeforeBorn);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("visit", visitBeforeBorn);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			String beforeBornDirect = getPrintBasicInfo(visitBeforeBorn.getId(),"BeforeBornDirect","beforeBornDirectId","visitBeforeBornId");
			map.put("beforeBornDirect", beforeBornDirect);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}

	public void removeVisitBeforeBornRecords(List ids)throws Exception {
		removeRecords(ids, VisitBeforeBorn.class);
	}

	public PagingResult<Map<String, Object>> findVisitAfterBornRecords(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		return findVisitAfterBornRecords(qryCond, pp, VISIT_AFTER_DEFAULT);
	}

	public PagingResult<Map<String, Object>> findVisitAfterBorn42Records(
			HealthFileQry qryCond, PagingParam pp) throws Exception{
		return findVisitAfterBornRecords(qryCond, pp, VISIT_AFTER_42);
	}

	private PagingResult<Map<String, Object>> findVisitAfterBornRecords(
			HealthFileQry qryCond, PagingParam pp, String type)throws Exception {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and c.recordType = ?");
		params.add(type);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql ;
		if(qryCond.getIsFirst() == 0){
			hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, VisitAfterBorn c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" and CONVERT(varchar(10),c.nextVisitDate,112) = CONVERT(varchar(10),getDate(),112) order by a.fileNo");
		}else{
			hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, VisitAfterBorn c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		}
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
				tmpFileNo = file.getFileNo();
			}
			VisitAfterBorn visitAfterBorn = (VisitAfterBorn) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(visitAfterBorn);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("visit", visitAfterBorn);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			String afterBornDirect = getPrintBasicInfo(visitAfterBorn.getId(),"AfterBornDirect","afterBornDirectId","visitAfterBornId");
			map.put("afterBornDirect", afterBornDirect);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}

	public void removeVisitAfterBornRecords(List<String> ids)throws Exception {
		List<VisitAfterBorn> list = new ArrayList();
		for (String id : ids) {
			VisitAfterBorn vab = (VisitAfterBorn)getHibernateTemplate().get(VisitAfterBorn.class, id);
			getHibernateTemplate().evict(vab);
			if("1".equals(vab.getRecordType())){
				list.add(vab);
			}
		}
		getHibernateTemplate().flush();
		removeRecords(ids, VisitAfterBorn.class);
		//在执行完后进行处理,如果抛出异常(表明未执行成功),则不执行
		for (VisitAfterBorn vb : list) {
			//为产后42天 , 则恢复妇保手册为未结案
			HealthFileMaternal hfm = (HealthFileMaternal)getHibernateTemplate().get(HealthFileMaternal.class, vb.getForeignId());
			hfm.setIsClosed("0");
			hfm.setClosedDate(null);
			getHibernateTemplate().update(hfm);
		}
		getHibernateTemplate().flush();
	}

	public PagingResult<Map<String, Object>> findReceptionRecords(
			HealthFileQry qryCond, PagingParam pp, String type)throws Exception {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and c.orgId = e.id");

//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, Reception c, SamTaxempcode d, SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
				tmpFileNo = file.getFileNo();
			}
			Reception reception = (Reception) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(reception);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);

			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("reception", reception);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}

	public void removeReceptionRecords(List ids)throws Exception {
		removeRecords(ids, Reception.class);
	}

	public PagingResult<Map<String, Object>> findConsultationRecords(
			HealthFileQry qryCond, PagingParam pp, String type)throws Exception {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");
		
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, Consultation c,SamTaxempcode d,SamTaxorgcode e").append(
				where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getIdnumber()));
				tmpFileNo = file.getFileNo();
			}
			Consultation consultation = (Consultation) objs[2];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(consultation);
			getHibernateTemplate().evict(samTaxorgcode);
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("consultation", consultation);
			map.put("org", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}

	public void removeConsultationRecords(List ids)throws Exception {
		removeRecords(ids, Consultation.class);
	}

	public PagingResult<Map<String, Object>> findCureswitchRecords(
			HealthFileQry qryCond, PagingParam pp, String type)throws Exception {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and c.orgId = e.id");

//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, CureSwitch c, SamTaxempcode d, SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
				tmpFileNo = file.getFileNo();
			}
			CureSwitch cureSwitch = (CureSwitch) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(cureSwitch);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);

			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("cureswitch", cureSwitch);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}

	public void removeCureswitchRecords(List ids) throws Exception{
		removeRecords(ids, CureSwitch.class);
	}

	public PagingResult<Map<String, Object>> findCurebackRecords(
			HealthFileQry qryCond, PagingParam pp, String type)throws Exception {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, CureBack c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
				tmpFileNo = file.getFileNo();
			}
			CureBack cureBack = (CureBack) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(cureBack);
			getHibernateTemplate().evict(samTaxempcode);

			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("cureback", cureBack);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}

	public void removeCurebackRecords(List ids)throws Exception {
		removeRecords(ids, CureBack.class);
	}

	public PagingResult<Map<String, Object>> findHealtheducatRecords(
			HealthFileQry qryCond, PagingParam pp, String type) {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and c.orgId = e.id");

		// if ( params.size() != 0 ) {
		// where.replace(0, 4, " where ");
		// }
		StringBuilder hql = new StringBuilder(
				"from HealthEducat c, SamTaxempcode d, SamTaxorgcode e")
				.append(where).append(" order by c.inputDate desc");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthEducat healthEducat = (HealthEducat) objs[0];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[1];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[2];
			getHibernateTemplate().evict(healthEducat);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);

			Map map = new HashMap();
			map.put("healthEducat", healthEducat);
			map.put("samTaxempcode", samTaxempcode);
			map.put("samTaxorgcode", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}

	public void removeHealtheducatRecords(List ids)throws Exception {
		removeRecords(ids, HealthEducat.class);
	}

	public PagingResult<Map<String, Object>> findVaccineInfoRecords(
			HealthFileQry qryCond, PagingParam pp, String type)throws Exception {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");

//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, VaccineInfo c").append(
				where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
				tmpFileNo = file.getFileNo();
			}
			VaccineInfo vaccineInfo = (VaccineInfo) objs[2];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(vaccineInfo);

			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("vaccineInfo", vaccineInfo);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}

	public void removeVaccineInfoRecords(List ids)throws Exception {
		removeRecords(ids, VaccineInfo.class);
	}

	public PagingResult<Map<String, Object>> findVaccinationRecords(
			HealthFileQry qryCond, PagingParam pp, String type)throws Exception {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, Vaccination c,SamTaxempcode d,SamTaxorgcode e").append(
				where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
				tmpFileNo = file.getFileNo();
			}
			Vaccination vaccination = (Vaccination) objs[2];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(vaccination);
			getHibernateTemplate().evict(samTaxorgcode);

			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("vaccination", vaccination);
			map.put("org", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}

	public void removeVaccinationRecords(List ids)throws Exception {
		removeRecords(ids, Vaccination.class);
	}

	public PagingResult<Map<String, Object>> findInfectionReportRecords(
			HealthFileQry qryCond, PagingParam pp, String type) {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and c.orgId = e.id");

//		where.replace(0, 4, " where ");

		StringBuilder hql = new StringBuilder(
				"from InfectionReport c, SamTaxempcode d, SamTaxorgcode e")
				.append(where).append(" order by c.name");
		log.debug("hql: " + hql.toString());

		PagingResult<InfectionReport> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			InfectionReport infectionReport = (InfectionReport) objs[0];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[1];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[2];
			getHibernateTemplate().evict(infectionReport);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);

			Map map = new HashMap();
			map.put("infectionReport", infectionReport);
			map.put("samTaxempcode", samTaxempcode);
			map.put("samTaxorgcode", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}

	public void removeInfectionReportRecords(List ids)throws Exception {
		removeRecords(ids, InfectionReport.class);
	}

	public PagingResult<Stat> findStats(HealthFileQry qryCond, PagingParam pp,
			String type) {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");

//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder("from Stat c").append(where)
				.append(" order by c.reportMonth desc");
		log.debug("hql: " + hql.toString());

		PagingResult<Stat> p = query(hql, params, pp);

		return p;

	}

	public void removeStats(List ids) throws Exception{
		removeRecords(ids, Stat.class);
	}

	/**
	 * 按条件获得户
	 * 
	 * @param qryCond
	 * @param pp
	 */
	public PagingResult<HomeInfo> getHomeResidents(HealthFileQry qryCond,
			PagingParam pp) throws Exception{
		List params = new ArrayList();
		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildWhereByHome(qryCond, params, where);
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder("from HomeInfo a").append(where)
				.append(" order by a.homeId");
		log.debug("hql: " + hql.toString());
		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<HomeInfo> homes = new ArrayList<HomeInfo>();
		for (Object object : list) {
			HomeInfo home = (HomeInfo) object;
			getHibernateTemplate().evict(home);
			homes.add(home);
		}
		PagingResult<HomeInfo> result = new PagingResult<HomeInfo>(
				p.getTotalSize(), homes);

		return result;
	}

	/**
	 * 按条件查询结核病人信息
	 * 
	 * @param qryCond
	 * @param pp
	 * @return
	 */
	public PagingResult<Tuberculosis> getTuberSupervise(HealthFileQry qryCond,
			PagingParam pp) {
		List params = new ArrayList();
		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildWhereByHome(qryCond, params, where);
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder("from Tuberculosis a").append(
				where).append(" order by a.id");
		log.debug("hql: " + hql.toString());
		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Tuberculosis> tubers = new ArrayList<Tuberculosis>();
		for (Object object : list) {
			Tuberculosis tuber = (Tuberculosis) object;
			getHibernateTemplate().evict(tuber);
			tubers.add(tuber);
		}
		PagingResult<Tuberculosis> result = new PagingResult<Tuberculosis>(
				p.getTotalSize(), tubers);

		return result;
	}

	/***
	 * 构建户的查询条件
	 * 
	 * @param qryCond
	 * @param params
	 * @param where
	 */
	private void buildWhereByHome(HealthFileQry qryCond, List params,
			StringBuilder where) {
		String districtId = makeDistrict(qryCond.getDistrict());
		if (StringUtils.hasText(districtId)) {
			params.add(districtId + '%');
			where.append(" and a.districtNumber like ?");
		}
		String filterKey = qryCond.getFilterKey();
		if (StringUtils.hasText(filterKey)) {
			String filterValue = qryCond.getFilterValue();
			if (StringUtils.hasText(filterValue)) {
				params.add('%' + filterValue + '%');
				where.append(" and " + filterKey + " like ?");
			}
		}
	}
	
	private String makeDistrict(String dist){
		String ret = dist;
		while(ret.endsWith("00")){
			ret = ret.substring(0,ret.length()-2);
		}
		return ret;
	}

	/**
	 * 要删除的户籍信息列表
	 * 
	 * @param homeIdList
	 */
	public void removeHomeInfos(List<String> homeIdList) {
		for (String homeId : homeIdList) {
			removeHomeInfo(homeId);
		}
		return;
	}

	/**
	 * 删除单个户籍信息
	 * 
	 * @param homeId
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeHomeInfo(String homeId) {

		HomeInfo home = (HomeInfo) getHibernateTemplate().get(HomeInfo.class,
				homeId);
		if (home != null) {
			List personList = getHibernateTemplate().find(
					"from PersonalInfo where homeId = ?", homeId);
			if (personList.size() > 0)
				return;

			getHibernateTemplate().delete(home);
		}
		return;
	}

	/**
	 * 要删除的结核病人列表
	 * 
	 * @param homeIdList
	 */
	public String removeTubers(List<String> idList) {
		String result = "";
		for (String id : idList) {
			result = result + removeTuber(id);
		}
		if (!result.equals("") && result != null)
			result = result.substring(0, result.length() - 1);
		return result;
	}

	/**
	 * 删除单个结核病人信息
	 * 
	 * @param homeId
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String removeTuber(String id) {

		Tuberculosis tuber = (Tuberculosis) getHibernateTemplate().get(
				Tuberculosis.class, id);
		if (tuber != null) {
			List tuberDetailList = getHibernateTemplate().find(
					"from TuberSuperDetail where baseId = ?", id);
			if (tuberDetailList.size() > 0)
				return id + "、";

			getHibernateTemplate().delete(tuber);
		}
		return "";
	}

	/**
	 * 建立一户式查询的条件
	 * 
	 * @param qryCond
	 * @param params
	 * @param where
	 */
	private void buildQueryHomeWhere(HealthFileQry qryCond, List params,
			StringBuilder where) {
		String districtId = makeDistrict(qryCond.getDistrict());
		// if (StringUtils.hasText(districtId)) {
		params.add(districtId);
		where.append(" and b.homeId = ?");
		// }

		where.append(" and a.fileNo = b.fileNo");
	}

	/**
	 * 建立一户式查询的SQL
	 * 
	 * @param qryCond
	 * @param params
	 * @return
	 */
	private StringBuilder buildQueryHomeHql(HealthFileQry qryCond, List params) {
		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildQueryHomeWhere(qryCond, params, where);

		// if (params.size() != 0) {
//		where.replace(0, 4, " where ");
		// }
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b").append(where).append(
				" order by a.fileNo");
		log.debug("hql: " + hql.toString());
		return hql;
	}

	/**
	 * 一户式查询
	 * 
	 * @param qryCond
	 * @param pp
	 * @return
	 */
	public PagingResult<HealthFile> queryByHome(HealthFileQry qryCond,
			PagingParam pp) {
		List params = new ArrayList();
		StringBuilder hql = buildQueryHomeHql(qryCond, params);
		return queryHealthFiles(pp, params, hql);
	}

	/**
	 * 门诊日志查询
	 * 
	 * @param pp
	 * @param params
	 * @param hql
	 * @return
	 */
	private PagingResult<ClinicLogs> ExecSQLClinicLogs(PagingParam pp,
			List params, StringBuilder hql) {
		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<ClinicLogs> files = new ArrayList<ClinicLogs>();
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			ClinicLogs clinicLogs = (ClinicLogs) objs[0];
			SickInfo sickInfo = (SickInfo) objs[1];
			getHibernateTemplate().evict(sickInfo);
			getHibernateTemplate().evict(sickInfo);
			clinicLogs.setSickInfo(sickInfo);
			files.add(clinicLogs);
		}
		PagingResult<ClinicLogs> result = new PagingResult<ClinicLogs>(
				p.getTotalSize(), files);
		return result;
	}

	/**
	 * 门诊日志查询
	 * 
	 * @param qryCond
	 * @param pp
	 * @return
	 */
	public PagingResult<ClinicLogs> queryClinicLogs(HealthFileQry qryCond,
			PagingParam pp) throws Exception{
		List params = new ArrayList();
		StringBuffer where = new StringBuffer(" where 1=1 ");
		String districtId = makeDistrict(qryCond.getDistrict());
		if (StringUtils.hasText(districtId)) {
//			params.add(EncryptionUtils.encry(districtId));
			where.append(" and a.fileNo like  '"+EncryptionUtils.encry(districtId)+"%' ");
		}
		String filterKey = qryCond.getFilterKey();
		if (StringUtils.hasText(filterKey)) {
			String filterValue = qryCond.getFilterValue();
			if (StringUtils.hasText(filterValue)) {
//				params.add(filterValue);
				where.append(" and " + filterKey + " like  '"+filterValue+"%' ");
			}
		}
		where.append(" and a.fileNo = b.fileNo");
//		if (params.size() > 0) {
//			where.replace(0, 4, "where");
//		}
		StringBuilder hql = new StringBuilder("from ClinicLogs a,SickInfo b ")
				.append(where).append(" order by a.fileNo,a.inputTime DESC");
		return ExecSQLClinicLogs(pp, params, hql);
	}
	
	/**
	 * 老年人档案查询
	 * @param qryCond
	 * @param pp
	 * @return
	 */
	public PagingResult<HealthFile> findOldManHealthFiles(HealthFileQry qryCond,
			PagingParam pp) throws Exception{
		List params = new ArrayList();
		StringBuilder hql = buildOldManHealthHql(qryCond, params);
		return queryHealthFiles(pp, params, hql);
	}
	
	/**
	 * 建立老年人档案查询的SQL语句
	 * @param qryCond
	 * @param params
	 * @param DiseaseType
	 * @return
	 */
	private StringBuilder buildOldManHealthHql(HealthFileQry qryCond,
			List params)throws Exception {
		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhereHealthFile(qryCond, params, where,0);

		where.append(" and b.birthday <  convert(datetime,convert(nvarchar,(YEAR(getdate())-64))+'-01-01')  ");
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b ").append(
				where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());
		return hql;
	}
	/**
	 * 获得老年人的健康体检记录
	 * @param qryCond
	 * @param pp
	 * @return
	 */
	public PagingResult<Map<String, Object>> findOldManExamRecords(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and c.age >= 65");
		
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		if(qryCond.getParams() !=null && qryCond.getParams().size()>0){
			for(Iterator iter = qryCond.getParams().keySet().iterator();iter.hasNext();){
				String key = (String)iter.next();
				String value = (String)qryCond.getParams().get(key);
				TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
				if("onlyself".equals(key) && "true".equals(value)){
					where.append(" and a.inputPersonId = convert(nvarchar,'"+ user.getUsername()+"') ");
				}
				if("onlyorg".equals(key) && "true".equals(value)){
					where.append(" and a.inputPersonId in ( select loginname from SamTaxempcode where orgId = "+ user.getOrgId() +") ");
				}
				if("org_id".equals(key) && !"".equals(value.trim())){
					where.append(" and a.inputPersonId in ( select loginname from SamTaxempcode where orgId = "+ value +") ");
				}
			}
		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, MedicalExam c, SamTaxempcode d")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
				tmpFileNo = file.getFileNo();
			}
			MedicalExam medicalExam = (MedicalExam) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(medicalExam);
			getHibernateTemplate().evict(samTaxempcode);

			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("medicalExam", medicalExam);
			map.put("samTaxempcode", samTaxempcode);
			files.add(map);
		}
		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;
	}
	
	/**
	 * 获得3~6岁儿童的健康体检记录
	 * @param qryCond
	 * @param pp
	 * @return
	 */
	public PagingResult<Map<String, Object>> findChildExam36Records(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		List params = new ArrayList();
		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);
		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, ChildrenMediExam36 c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
				tmpFileNo = file.getFileNo();
			}
			ChildrenMediExam36 childrenMediExam36 = (ChildrenMediExam36) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(childrenMediExam36);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("child", childrenMediExam36);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;
	}
	
	/**
	 * 移除3~6岁儿童的健康体检记录
	 * @param recordIdList
	 */
	public void removeChildExam36Records(final List<String> recordIdList)throws Exception {
		removeRecords(recordIdList, ChildrenMediExam36.class);
	}
	
	
	/**
	 * 分娩记录
	 * @param qryCond
	 * @param pp
	 * @return
	 */
	public PagingResult<Map<String, Object>> findBirthChildRecords(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		List params = new ArrayList();
		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);
		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");
		where.append(" and c.certifiId = f.certifiId");
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, ChildBirthRecord c, SamTaxempcode d,SamTaxorgcode e , BirthCertificate f")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
				tmpFileNo = file.getFileNo();
			}
			ChildBirthRecord childBirthRecord = (ChildBirthRecord) objs[2];
			ChildBirthRecordBO childBirthRecordBo = new ChildBirthRecordBO();
			BeanUtils.copyProperties(childBirthRecord, childBirthRecordBo);
			if(childBirthRecord.getChildbirthYear() != null && childBirthRecord.getChildbirthMonth() != null
					&& childBirthRecord.getChildbirthDay() != null)
				childBirthRecordBo.setBirthRecordDate(childBirthRecord.getChildbirthYear() + "年" +
					childBirthRecord.getChildbirthMonth() + "月" + childBirthRecord.getChildbirthDay() + "日");
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			BirthCertificate cert = (BirthCertificate) objs[5];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(childBirthRecord);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("birthRecord", childBirthRecordBo);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			map.put("cert", cert);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;
	}
	
	public void removeBirthChildRecords(final List<String> recordIdList)throws Exception {
		removeRecords(recordIdList, ChildBirthRecord.class);
	}
	
	public PagingResult<HealthFile> findVaccineImmune(QryCondition qryCond, PagingParam pp)throws Exception{
		StringBuilder where = new StringBuilder();
		genVaccineImmuneWhere(qryCond,where);
		String district = makeDistrict(qryCond.getDistrict());
		final String fhql = " select a.*,c.* From HealthFile a left join PersonalInfo b on a.fileNo=b.fileNo left join VaccineImmune c on a.fileNo=c.vfileNo " +
				" Where a.districtNumber like '" + district + "%' " + where.toString();
		final String countsql = " select count(*)  From HealthFile a left join PersonalInfo b on a.fileNo=b.fileNo left join VaccineImmune c on a.fileNo=c.vfileNo " +
				" Where a.districtNumber like '" + district + "%' " + where.toString();
		List totalSizelist = getHibernateTemplate().executeFind(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
				Query query = arg0.createSQLQuery(countsql);
				return query.list();
			}
		});
		int totalSize = ((Integer)(totalSizelist.get(0)));
//		int totalSize = 100;
		final PagingParam fpp = pp;
		List<Object[]> list = getHibernateTemplate().executeFind(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
				SQLQuery query = arg0.createSQLQuery(fhql);
				query.addEntity(HealthFile.class).addEntity(VaccineImmune.class);
				query.setFirstResult(fpp.getStart()).setMaxResults(fpp.getLimit());
				return query.list();
			}
		});
		List<HealthFile> files = new ArrayList<HealthFile>();
		String tmpFileNo = "";
		if(list.size() > 0){
			for(Object[] obj : list){
				HealthFile file = (HealthFile) obj[0];
				getHibernateTemplate().evict(file);
//				PersonalInfo person = (PersonalInfo) obj[1];
//				getHibernateTemplate().evict(person);
//				file.setPersonalInfo(person);
				List<PersonalInfo> personlist = getHibernateTemplate().find(" from PersonalInfo where fileNo=?",file.getFileNo());
				PersonalInfo person  = null;
				if(personlist.size()>0){
					person = personlist.get(0);
					getHibernateTemplate().evict(person);
				}
				file.setPersonalInfo(person);
				VaccineImmune vacc = (VaccineImmune) obj[1];
				getHibernateTemplate().evict(vacc);
				file.setVaccineImmune(vacc);
				if(!tmpFileNo.equals(file.getFileNo())){
					file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
					file.setName(EncryptionUtils.decipher(file.getName()));
					tmpFileNo = file.getFileNo();
					if(personlist.size()>0)
						person.setIdnumber(EncryptionUtils.decipher(person.getIdnumber()));
				}
				files.add(file);
			}
		}
		PagingResult<HealthFile> result = new PagingResult<HealthFile>(
				totalSize, files);
		return result;
	}
	private void genVaccineImmuneWhere(QryCondition qryConds, StringBuilder where) throws Exception{
		for(Condition qryCond : qryConds.getConditions()){
			String filterKey = qryCond.getFilterKey();
			if (StringUtils.hasText(filterKey)) {
				String filterValue = qryCond.getFilterVal();
				if(filterKey.equals("type")){
					if("0".equals(filterValue)){
						where .append( "");
					}else if("1".equals(filterValue)){
						where .append(" and c.vfileNo is null ");
					}else if("2".equals(filterValue)){
						where .append(" and c.vfileNo is not null ");
					}
				}else{
					if(filterKey.equals("a.name") || filterKey.equals("a.fileNo")){
						filterValue = EncryptionUtils.encry(filterValue);
					}
					if(filterKey.equals("a.inputDate") || filterKey.equals("b.birthday") || filterKey.equals("a.lastModifyDate")){
						String startDate = null;
						String endDate = null;
						if(filterValue.indexOf("-") > 0){
							String[] valArray = filterValue.split("-");
							if(valArray.length > 2){
								throw new Exception("请输入正确的日期范围，如：20120101-20120102或者20120101。");
							}
							startDate = valArray[0] + " 00:00:00.0";
							endDate = valArray[1] + " 23:59:59.999";
						}else{
							startDate = filterValue + " 00:00:00.0";
							endDate = filterValue + " 23:59:59.999";
						}
						where.append(" and " + filterKey + " >= '" + startDate + "' and " + filterKey + " <= '" + endDate + "'");
					}else{
						if (StringUtils.hasText(filterValue)) {
							where.append(" and " + filterKey + " like '" + filterValue + "%'");
						}
					}
				}
			}
		}
	}
	
	public PagingResult<Map<String, Object>> findHighRiskRecords(
			QryCondition qryCond, PagingParam pp) throws Exception{
        try {
            String where = genQryCondition(qryCond);
            String hql = " From HealthFile a,PersonalInfo b,WomanLastMedicalExamRecord c " + where;
            String districtId = qryCond.getDistrict();
            System.out.println("where==" + where + "hql==" + hql);
            while (districtId.endsWith("00")) {
                districtId = districtId.substring(0, districtId.length() - 2);
            }
            System.out.println(" Select Count(*)" + hql);
            int totalSize = ((Long) getHibernateTemplate().find(" Select Count(*)" + hql).get(0)).intValue();

            final String fhql = hql + " order by c.lastExamDate ASC";
            final PagingParam fpp = pp;
            final String fdistrict = districtId;
            System.out.println("fhql==" + fhql + "fdistrict==" + fdistrict);
            List list = getHibernateTemplate().executeFind(new HibernateCallback() {
                @Override
                public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
                    Query query = arg0.createQuery(fhql);
//                    query.setParameter(0, fdistrict);
                    query.setFirstResult(fpp.getStart()).setMaxResults(fpp.getLimit());
                    return query.list();
                }
            });
            List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
            String tmpFileNo = "";
            for (Object object : list) {
                Object[] objs = (Object[]) object;
                HealthFile file = (HealthFile) objs[0];
                PersonalInfo person = (PersonalInfo) objs[1];
                if (!tmpFileNo.equals(file.getFileNo())) {
                    file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
                    file.setName(EncryptionUtils.decipher(file.getName()));
                    person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
                    tmpFileNo = file.getFileNo();
                }
                person.setIdnumber(EncryptionUtils.decipher(person.getIdnumber()));
                WomanLastMedicalExamRecord record = (WomanLastMedicalExamRecord) objs[2];
                getHibernateTemplate().evict(file);
                getHibernateTemplate().evict(person);
                getHibernateTemplate().evict(record);
                Map map = new HashMap();
                map.put("file", file);
                map.put("personalInfo", person);
                map.put("record", record);
                files.add(map);
            }

            PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
                    totalSize, files);
            return result;
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }
	}

	public PagingResult<Map<String, Object>> findChildHighRiskRecords(
			QryCondition qryCond, PagingParam pp) throws Exception{
		String where = genQryCondition(qryCond);
		String districtId = qryCond.getDistrict();
		while(districtId.endsWith("00")){
			districtId = districtId.substring(0,districtId.length()-2);
		}
		String hql = " From HealthFile a,PersonalInfo b,ChildLastMedicalExamRecord c " + where;
		
		int totalSize = ((Long)getHibernateTemplate().find(" Select Count(*)" + hql,districtId).get(0)).intValue();
		
		final String fhql = hql + "  order by c.lastExamDate ASC";
		final PagingParam fpp = pp;
		final String fdistrict = districtId;
		List list = getHibernateTemplate().executeFind(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
				Query query = arg0.createQuery(fhql);
				query.setParameter(0, fdistrict);
				query.setFirstResult(fpp.getStart()).setMaxResults(fpp.getLimit());
				return query.list();
			}
		});
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				tmpFileNo = file.getFileNo();
			}
			person.setIdnumber(EncryptionUtils.decipher(person.getIdnumber()));
			ChildLastMedicalExamRecord record = (ChildLastMedicalExamRecord) objs[2];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(record);
			Map map = new HashMap();
			map.put("file", file);
			map.put("personalInfo", person);
			map.put("record", record);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				totalSize, files);
		return result;
	}
	
	public List getPrintHighRiskRecords(QryCondition qryCond)throws Exception{
		String where = genQryCondition(qryCond);
		String hql = " From HealthFile a,PersonalInfo b,WomanLastMedicalExamRecord c " + where;
		return getHibernateTemplate().find(hql + " order by c.lastExamDate ASC");
	}
	
	public List getChildPrintHighRiskRecords(QryCondition qryCond)throws Exception{
		String where = genQryCondition(qryCond);
		String hql = " From HealthFile a,PersonalInfo b,ChildLastMedicalExamRecord c " + where;
		return getHibernateTemplate().find(hql + " order by c.lastExamDate ASC ");
	}
	
	private String genQryCondition(QryCondition qryCond) throws Exception{
		String where = "";
		if(qryCond.getConditions() != null){
			for(Condition cond : qryCond.getConditions()){
				if(cond.getFilterVal() != null && !cond.getFilterVal().equals("")){
					String filterKey  = cond.getFilterKey();
					if(cond.getFilterKey().equals("a.name") || cond.getFilterKey().equals("a.fileNo") ||
							 cond.getFilterKey().equals("b.idnumber")){
						cond.setFilterVal(EncryptionUtils.encry(cond.getFilterVal()));
						where = where + " And " + cond.getFilterKey() + " like '" + cond.getFilterVal() + "%' ";
					}else if(cond.getFilterKey().equals("b.birthday")){
						filterKey = " Convert(Varchar(20)," + cond.getFilterKey() + ",23) ";
						where = where + " And " + filterKey + " = '" + cond.getFilterVal() + "' ";
					}else if(cond.getFilterKey().equals("startDate")){
						where = where + " And Convert(Varchar(30),c.lastExamDate,120) >= '" + cond.getFilterVal() + " 00:00:00' ";
						continue;
					}else if(cond.getFilterKey().equals("endDate")){
						where = where + " And Convert(Varchar(30),c.lastExamDate,120) <= '" + cond.getFilterVal() + " 23:59:59' ";
						continue;
					}else{
						where = where + " And " + filterKey + " = '" + cond.getFilterVal() + "' ";
					}
				}
			}
		}
		
		if(qryCond.getManPerson().equals("10")){//10查询在管人员
			where = where + " And c.type = 1"; 
		}else if(qryCond.getManPerson().equals("01")){//01查询转归人员
			where = where + " And c.type = 0";
		}else{//11 || 00查询所有人员
			//不需要加条件
		}
		String districtId = makeDistrict(qryCond.getDistrict());
		where = " Where " +
				" a.districtNumber like '"+districtId+"%' And a.fileNo = b.fileNo " +
				" And a.fileNo = c.fileNo " + where;
		return where;
	}
	
	/**
	 * 新生儿死亡调查表
	 * @param qryCond
	 * @param pp
	 * @return
	 */
	public PagingResult<Map<String, Object>> findBabyDeathRecords(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");

//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, BabyDeathSurvey c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
				tmpFileNo = file.getFileNo();
			}
			BabyDeathSurvey babydeath = (BabyDeathSurvey) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(babydeath);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("child", babydeath);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}
	public void removeBabyDeathRecords(final List<String> recordIdList)throws Exception {
		removeRecords(recordIdList, BabyDeathSurvey.class);
	}
	
	/**
	 * 28天至4岁儿童死亡调查表
	 * @param qryCond
	 * @param pp
	 * @return
	 */
	public PagingResult<Map<String, Object>> findChildDeath01Records(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");

//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, ChildrenDeathSurvey01 c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
				tmpFileNo = file.getFileNo();
			}
			ChildrenDeathSurvey01 childdeath = (ChildrenDeathSurvey01) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(childdeath);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("child", childdeath);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}
	public void removeChildDeath01Records(final List<String> recordIdList)throws Exception {
		removeRecords(recordIdList, ChildrenDeathSurvey01.class);
	}
	
	/**
	 * 5岁以下儿童死亡调查表
	 * @param qryCond
	 * @param pp
	 * @return
	 */
	public PagingResult<Map<String, Object>> findChildDeath02Records(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");

//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, ChildrenDeathSurvey02 c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
				tmpFileNo = file.getFileNo();
			}
			ChildrenDeathSurvey02 childdeath = (ChildrenDeathSurvey02) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(childdeath);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("child", childdeath);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;

	}
	public void removeChildDeath02Records(final List<String> recordIdList)throws Exception {
		removeRecords(recordIdList, ChildrenDeathSurvey02.class);
	}
	
	/**
	 * 听力筛查同意书
	 * @param qryCond
	 * @param pp
	 * @return
	 */
	public PagingResult<Map<String, Object>> findConsentBookRecords(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");

//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, DiseaseAndHearScreenConsent c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
				tmpFileNo = file.getFileNo();
			}
			DiseaseAndHearScreenConsent consentBook = (DiseaseAndHearScreenConsent) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(consentBook);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("consentBook", consentBook);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;
	}
	public void removeConsentBookRecords(final List<String> recordIdList)throws Exception {
		removeRecords(recordIdList, DiseaseAndHearScreenConsent.class);
	}
	/**
	 * 听力筛查同意书
	 * @param qryCond
	 * @param pp
	 * @return
	 */
	public PagingResult<Map<String, Object>> findExportCardRecords(
			HealthFileQry qryCond, PagingParam pp) throws Exception{
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");

//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, HearScreenReportCard c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
				tmpFileNo = file.getFileNo();
			}
			HearScreenReportCard exportCard = (HearScreenReportCard) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(exportCard);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("exportCard", exportCard);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;
	}
	public void removeExportCardRecords(final List<String> recordIdList)throws Exception {
		removeRecords(recordIdList, HearScreenReportCard.class);
	}
	/**
	 * 听力障碍儿童个案登记表
	 * @param qryCond
	 * @param pp
	 * @return
	 */
	public PagingResult<Map<String, Object>> findBabyBarrierRegRecords(
			HealthFileQry qryCond, PagingParam pp)throws Exception {
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		buildGeneralWhere(qryCond, params, where);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");

		if (params.size() != 0) {
			where.replace(0, 4, " where ");
		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, BabyBarrierReg c, SamTaxempcode d,SamTaxorgcode e")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				person.setIdnumber(EncryptionUtils.decipher(person.getFileNo()));
				tmpFileNo = file.getFileNo();
			}
			BabyBarrierReg babyBarrierReg = (BabyBarrierReg) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(babyBarrierReg);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			Map map = new HashMap();
			map.put("file", file);
			map.put("person", person);
			map.put("babyBarrierReg", babyBarrierReg);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;
	}
	public void removeBabyBarrierRegRecords(final List<String> recordIdList)throws Exception {
		removeRecords(recordIdList, BabyBarrierReg.class);
	}
	
	/**
	 * 档案转移
	 * @param qry
	 * @param pp
	 * @return
	 */
	public PagingResult<Map<String, Object>> getTransferHealthfile(TransferHealthFileQry qry,PagingParam pp)throws Exception{
		StringBuffer where = new StringBuffer(" where 1=1 ");
		List params = new ArrayList();
		String filterVal = qry.getFilterValue().trim();
		if(StringUtils.hasText(filterVal) && !filterVal.equals("")){
			String filterKey = qry.getFilterKey();
			if(filterKey.equals("fromFileNo") || filterKey.equals("toFileNo")){
				filterVal = EncryptionUtils.encry(filterVal);
				where.append(" And " + filterKey + " = '"+filterVal+"' ");
//				params.add(filterVal);
			}else if(filterKey.equals("birthday")){
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
				try {
					String startDate = null;
					String endDate = null;
					if(filterVal.indexOf("-") > 0){
						String[] valArray = filterVal.split("-");
						if(valArray.length > 2){
							throw new Exception("请输入正确的日期范围，如：20120101-20120102或者20120101。");
						}
						startDate = valArray[0] + " 00:00:00.000";
						endDate = valArray[1] + " 23:59:59.999";
					}else{
						startDate = filterVal + " 00:00:00.000";
						endDate = filterVal + " 23:59:59.999";
					}
//					params.add(format.parse(startDate));
//					params.add(format.parse(endDate));
					where.append(" and " + filterKey + " >= '"+(startDate)+"' and " + filterKey + " <= '"+(endDate)+"' ");
				} catch (ParseException e) {
					throw new Exception("请输入正确的日期范围，如：20120101-20120102或者20120101。");
				}
			}else{
				where.append(" And " + filterKey + " = '"+filterVal+"' ");
//				params.add(filterVal);
			}
		}
		if(!qry.getType().equals("100")){
			where.append(" And isSure = '"+qry.getType()+"' ");
//			params.add(qry.getType());
		}
		String districtId = makeDistrict(qry.getDistrict());
		if(qry.getFlowType().equals("0")){
			where.append(" And toDistrictNumber like '"+districtId+"%' ");
//			params.add(qry.getDistrict());
		}else if(qry.getFlowType().equals("1")){
			where.append(" And fromDistrictNumber like '"+districtId+"%' ");
//			params.add(qry.getDistrict());
		}
		
		where.append(" And a.toDistrictNumber = b.id ");
//		
//		if(params.size() > 0){
//			where.replace(0, 4, " Where ").append(" And a.toDistrictNumber = b.id ");
//		}
//		
		String hql = " From HealthFileTransfer a,District b " + where.toString();
		String countHql = " Select Count(*) " + hql;
		int totalSize = ((Long)(getHibernateTemplate().find(countHql,params.toArray()).get(0))).intValue();
		if(pp == null) pp = new PagingParam();
		
		final String fhql = hql+" Order By a.name";
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
		Pattern p = Pattern.compile("[0-9]+"); 
		Matcher m = null;
//		boolean b = m.matches(); 
		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		for(Object object : list){
			Object[] objs = (Object[]) object;
			HealthFileTransfer trans = (HealthFileTransfer)objs[0];
			District district = (District)objs[1];
			m = p.matcher(trans.getFromFileNo()); 
			if(!m.matches()){
				trans.setFromFileNo(EncryptionUtils.decipher(trans.getFromFileNo()));
				if(trans.getToFileNo() != null)
					trans.setToFileNo(EncryptionUtils.decipher(trans.getToFileNo()));
			}
			getHibernateTemplate().evict(trans);
			getHibernateTemplate().evict(district);
			Map map = new HashMap();
			map.put("trans", trans);
			map.put("district", district);
			retList.add(map);
		}
		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				totalSize, retList);
		return result;
	}
	
	/**
	 * 档案注销
	 * @param qryCond
	 * @param pp
	 * @return
	 */
	public PagingResult<Map<String, Object>> HealthFileLoginOff(
			TransferHealthFileQry qryCond, PagingParam pp) throws Exception{
		List params = new ArrayList();

		StringBuilder where = new StringBuilder( " where 1=1 ");
		HealthFileQry qry = new HealthFileQry();
		BeanUtils.copyProperties(qryCond, qry);
		buildGeneralWhereHealthFile(qry, params, where,44);

		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");
		
		if(!qryCond.getType().equals("0")){
			where.append(" and c.loginOffReason = '"+qryCond.getType()+"' ");
//			params.add(Integer.parseInt(qryCond.getType()));
		}
		where.append(" and c.loginOffReason = f.id");
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, HealthFileLoginOff c, SamTaxempcode d,SamTaxorgcode e,BasicInformation f ")
				.append(where).append(" order by a.fileNo");
		log.debug("hql: " + hql.toString());

		PagingResult<Object> p = query(hql, params, pp);
		List list = p.getData();
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		String tmpFileNo = "";
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			if(!tmpFileNo.equals(file.getFileNo())){
				file.setFileNo(EncryptionUtils.decipher(file.getFileNo()));
				file.setName(EncryptionUtils.decipher(file.getName()));
				tmpFileNo = file.getFileNo();
			}
			PersonalInfo person = (PersonalInfo) objs[1];
			person.setIdnumber(EncryptionUtils.decipher(person.getIdnumber()));
			HealthFileLoginOff loginoff = (HealthFileLoginOff) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			BasicInformation basicInfo = (BasicInformation) objs[5];
			getHibernateTemplate().evict(file);
			getHibernateTemplate().evict(person);
			getHibernateTemplate().evict(loginoff);
			getHibernateTemplate().evict(samTaxempcode);
			getHibernateTemplate().evict(samTaxorgcode);
			getHibernateTemplate().evict(basicInfo);
			Map map = new HashMap();
			map.put("file", file);
			map.put("personalInfo", person);
			map.put("loginoff", loginoff);
			map.put("samTaxempcode", samTaxempcode);
			map.put("org", samTaxorgcode);
			map.put("basicInfo", basicInfo);
			files.add(map);
		}

		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(
				p.getTotalSize(), files);

		return result;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeHealthfilesAlreadyBuild(final List<String> recordIdList) throws Exception{
		for(String id : recordIdList){
			HealthFileMaternal maternal = (HealthFileMaternal)getHibernateTemplate().get(HealthFileMaternal.class, id);
			if(SecurityManager.isValidUser(maternal.getInputPersonId(),getSession())){
				String fileNo = maternal.getFileNo();
				BusiUtils.insertLog(this,maternal.getFileNo(),"delete","HealthFileMaternal",cn.net.tongfang.framework.security.SecurityManager.currentOperator().getUsername(),maternal,null);
				getHibernateTemplate().bulkUpdate(" Update PersonalInfo Set bornStatus = '否' Where fileNo = ? ", fileNo);
				getHibernateTemplate().bulkUpdate(" Delete From PregnancyRecord Where healthFileMaternalId = ? ", maternal.getId());
				getHibernateTemplate().delete(maternal);
			}else{
				throw new Exception("不是本机构的档案,不允许删除!");
			}
		}
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void removehealthfilePregnancyRecord(final List<String> recordIdList) throws Exception {
		for(String id : recordIdList){
			PregnancyRecord pregnancy = (PregnancyRecord)getHibernateTemplate().get(PregnancyRecord.class, id);
			if(SecurityManager.isValidUser(pregnancy.getInputPersonId(),getSession())){
				getHibernateTemplate().delete(pregnancy);
			}else{
				throw new Exception("不是本机构的档案,不允许删除!");
			}
		}
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void removehealthfilePregnancyRecordChild(final List<String> recordIdList) throws Exception {
		for(String id : recordIdList){
			PregnancyRecordChild pregnancy = (PregnancyRecordChild)getHibernateTemplate().get(PregnancyRecordChild.class, id);
			if(SecurityManager.isValidUser(pregnancy.getInputPersonId(),getSession())){
				getHibernateTemplate().delete(pregnancy);
			}else{
				throw new Exception("不是本机构的档案,不允许删除!");
			}
		}
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeHealthfilesFinishGestation(final List<String> recordIdList) throws Exception{
		for (String id : recordIdList) {
			List l = getHibernateTemplate().find(" From FinishGestation Where healthFileMaternalId = ? ",id);
			if (l.size() > 0) {
				FinishGestation gestation = (FinishGestation) l.get(0);
				if (SecurityManager.isValidUser(gestation.getInputPersonId(),getSession())) {
					String maternalId = gestation.getHealthFileMaternalId();
					HealthFileMaternal maternal = (HealthFileMaternal) getHibernateTemplate().get(HealthFileMaternal.class, maternalId);
					maternal.setIsClosed("0");
					getHibernateTemplate().update(maternal);
					getHibernateTemplate().delete(gestation);
					getHibernateTemplate().bulkUpdate(" update PersonalInfo set bornStatus = '是'  Where fileNo = ? ", maternal.getFileNo());
				}else{
					throw new Exception("不是本机构的数据,不允许撤消!");
				}
			}
		}
	}
	
	public PagingResult<PregnancyRecord> findhealthfilePregnancyRecord(PregnancyRecord qryCond,PagingParam pp){
		String hql = " From PregnancyRecord Where healthFileMaternalId = ? ";
		String orderby = " Order By recordDate ";
		final String param = qryCond.getHealthFileMaternalId();
		int totalSize = ((Long)(getHibernateTemplate().find("select count(*) "+hql.toString(),param).get(0))).intValue();
		final String fhql = hql+orderby;
		final PagingParam fpp = pp;
		List list = getHibernateTemplate().executeFind(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
				Query query = arg0.createQuery(fhql);
				query.setParameter(0, param);
				query.setFirstResult(fpp.getStart()).setMaxResults(fpp.getLimit());
				return query.list();
			}
		});
		PagingResult<PregnancyRecord> result = new PagingResult<PregnancyRecord>(
				totalSize, list);
		return result;
	}
	
	public PagingResult<PregnancyRecordChild> findhealthfilePregnancyRecordChild(PregnancyRecordChild qryCond,PagingParam pp){
		String hql = " From PregnancyRecordChild Where healthFileChildrenId = ? ";
		String orderby = " Order By recordDate ";
		final String param = qryCond.getHealthFileChildrenId();
		int totalSize = ((Long)(getHibernateTemplate().find("select count(*) "+hql.toString(),param).get(0))).intValue();
		final String fhql = hql+orderby;
		final PagingParam fpp = pp;
		List list = getHibernateTemplate().executeFind(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
				Query query = arg0.createQuery(fhql);
				query.setParameter(0, param);
				query.setFirstResult(fpp.getStart()).setMaxResults(fpp.getLimit());
				return query.list();
			}
		});
		PagingResult<PregnancyRecordChild> result = new PagingResult<PregnancyRecordChild>(
				totalSize, list);
		return result;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeHealthfilesAlreadyBuildChild(final List<String> recordIdList) throws Exception{
		System.out.println("==========22222222=="+recordIdList);
		for(String id : recordIdList){
			HealthFileChildren child = (HealthFileChildren)getHibernateTemplate().get(HealthFileChildren.class, id);
			if(SecurityManager.isValidUser(child.getInputPersonId(),getSession())){
				String fileNo = child.getFileNo();
				System.out.println("==========1111111111111==");
				BusiUtils.insertLog(this,child.getFileNo(),"delete","HealthFileChildren",cn.net.tongfang.framework.security.SecurityManager.currentOperator().getUsername(),child,null);
				getHibernateTemplate().bulkUpdate(" Update HealthFile Set isOverCount = NULL Where fileNo = ? ", fileNo);
				getHibernateTemplate().bulkUpdate(" Delete From PregnancyRecordChild Where healthFileChildrenId = ? ", child.getId());
				getHibernateTemplate().delete(child);
			}else{					
				throw new Exception("不是本机构的档案,不允许删除!");
			}
		}
	}
	public FinishGestation getFinishGestation(String healthFileMaternalId){
		String hql = " From FinishGestation Where healthFileMaternalId = ? ";
		List list = getHibernateTemplate().find(hql,healthFileMaternalId);
		if(list.size() > 0){
			return (FinishGestation)list.get(0);
		}
		return null;
	}

	public ModuleUtil getModuleUtil() {
		return moduleUtil;
	}

	public void setModuleUtil(ModuleUtil moduleUtil) {
		this.moduleUtil = moduleUtil;
	}
	
	
}
