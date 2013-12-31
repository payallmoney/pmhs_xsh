package cn.net.tongfang.framework.security;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import cn.net.tongfang.framework.security.bo.CategoryBo;
import cn.net.tongfang.framework.security.bo.ModuleBo;
import cn.net.tongfang.framework.security.bo.ModuleServiceBo;
import cn.net.tongfang.framework.security.bo.OperatorBo;
import cn.net.tongfang.framework.security.bo.RoleModuleBo;
import cn.net.tongfang.framework.security.vo.District;
import cn.net.tongfang.framework.security.vo.Doctors;
import cn.net.tongfang.framework.security.vo.HighRisk;
import cn.net.tongfang.framework.security.vo.HospitalProp;
import cn.net.tongfang.framework.security.vo.ResidentPopulation;
import cn.net.tongfang.framework.security.vo.SamModule;
import cn.net.tongfang.framework.security.vo.SamModuleCategory;
import cn.net.tongfang.framework.security.vo.SamModuleService;
import cn.net.tongfang.framework.security.vo.SamModuleServiceId;
import cn.net.tongfang.framework.security.vo.SamRole;
import cn.net.tongfang.framework.security.vo.SamRoleModule;
import cn.net.tongfang.framework.security.vo.SamRoleModuleId;
import cn.net.tongfang.framework.security.vo.SamService;
import cn.net.tongfang.framework.security.vo.SamTaxempcode;
import cn.net.tongfang.framework.security.vo.SamTaxorgcode;
import cn.net.tongfang.framework.security.vo.SystemInformation;
import cn.net.tongfang.framework.util.ChineseUtils;
import cn.net.tongfang.framework.util.service.vo.PagingParam;
import cn.net.tongfang.framework.util.service.vo.PagingResult;
import cn.net.tongfang.web.service.bo.DoctorsBO;

@WebService(endpointInterface = "cn.net.tongfang.framework.security.SecurityService")
public class SecurityServiceImpl extends HibernateDaoSupport implements
		SecurityService {

	private static final Logger log = Logger
			.getLogger(SecurityServiceImpl.class);

	/*
	 * @see
	 * cn.net.tongfang.framework.security.SecurityService#getModuleServices()
	 */
	
	public List<String[]> getModuleServices() {

		String hql = "select m, s "
				+ "from SamModule m, SamService s, SamModuleService ms "
				+ "where m.id = ms.id.moduleId and s.id = ms.id.serviceId";

		List list = getHibernateTemplate().find(hql);

		List ms = new ArrayList();

		for (Object object : list) {
			Object[] objs = (Object[]) object;
			SamModule module = (SamModule) objs[0];
			SamService service = (SamService) objs[1];

			ms.add(new String[] { module.getId(), service.getValue() });
		}

		log.debug("map size is " + ms.size());

		return ms;
	}

	public OperatorBo getOperatorByUsername(String username) {

		Assert.notNull(username, "username is null.");

		log.debug("finding user[" + username + "]");

		List users = getHibernateTemplate().find(
				"from SamTaxempcode where loginname='"+username+"'");

		if (users.size() == 0)
			return null; // TODO: exception or errorNo?

		SamTaxempcode operator = (SamTaxempcode) users.get(0);

		log.debug("found user[" + operator + "]");

		// get district
		District district = (District) getHibernateTemplate().get(
				District.class, operator.getDistrictId());

		// get org
		SamTaxorgcode org = (SamTaxorgcode) getHibernateTemplate().get(
				SamTaxorgcode.class, operator.getOrgId());

		// String hql =
		// "select m, c from Role r, RoleModule rm, Module m, Category c " +
		// "where r.id = ? and r.id = rm.id.roleId and m.id = rm.id.moduleId " +
		// "and m.categoryId = c.id";
		// //add by Daved 2009-03-16
		String hql = "select m,c "
				+ "from SamTaxempcodeRole tr,SamRoleModule rm,SamModule m,SamModuleCategory c "
				+ "where tr.id.loginname = '"+operator.getLoginname()+"'  and tr.id.id = rm.id.roleId "
				+ "and m.id = rm.id.moduleId and m.categoryId = c.id";
		List list = getHibernateTemplate().find(hql );

		List<ModuleBo> modules = new ArrayList<ModuleBo>();
		Map categoryMap = new HashMap();

		for (Object object : list) {
			Object[] objs = (Object[]) object;
			SamModule m = (SamModule) objs[0];
			SamModuleCategory c = (SamModuleCategory) objs[1];

			ModuleBo moduleBo = new ModuleBo();
			BeanUtils.copyProperties(m, moduleBo);
			modules.add(moduleBo);

			CategoryBo categoryBo = new CategoryBo();
			BeanUtils.copyProperties(c, categoryBo);
			categoryMap.put(c.getId(), categoryBo);

		}

		List<CategoryBo> categories = new ArrayList(categoryMap.values());

		OperatorBo operatorBo = new OperatorBo();
		BeanUtils.copyProperties(operator, operatorBo);
		log.debug("operator: " + operator);
		log.debug("operatorBo: " + operatorBo);
		operatorBo.setId(operator.getLoginname());
		operatorBo.setCategories(categories);
		operatorBo.setModules(modules);
		operatorBo.setDistrict(district);
		operatorBo.setOrg(org);
		operatorBo.setOrgId(operator.getOrgId());
		operatorBo.setIsLookAuthority(operator.getIsLookAuthority());
		return operatorBo;
	}

	public List<SamModuleCategory> getAllModuleCategories() {
		return (List<SamModuleCategory>) getHibernateTemplate().find(
				"from SamModuleCategory");
	}

	public ModuleServiceBo getModuleById(String id) {
		if (id == null)
			return null;
		SamModule module = (SamModule) getHibernateTemplate().get(
				SamModule.class, id);
		if (module == null)
			return null;

		String hql = "select s from SamModuleService ms, SamService s where ms.id.moduleId = '"+id+"' and ms.id.serviceId = s.id";
		List<SamService> services = (List<SamService>) getHibernateTemplate()
				.find(hql);

		ModuleServiceBo bo = new ModuleServiceBo(module, services);

		return bo;
	}

	public List<SamService> queryServiceByName(String name) {
		if (name == null)
			name = "";
		String likename = "%" + name + "%";
		return (List<SamService>) getHibernateTemplate().find(
				"from SamService where value like '"+likename+"' ");
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public String addModule(ModuleServiceBo moduleServiceBo) {
		// TODO: validate params

		SamModule module = moduleServiceBo.getModule();
		List<SamService> services = moduleServiceBo.getServices();
		String moduleId = module.getId();

		getHibernateTemplate().save(module);

		addOnlyModuleServices(moduleId, services);

		return moduleId;

	}
	@Transactional(propagation = Propagation.REQUIRED)
	private int addOnlyModuleServices(String moduleId, List<SamService> services) {
		if (services == null)
			return 0;
		for (SamService samService : services) {

			SamModuleServiceId smsId = new SamModuleServiceId(moduleId,
					samService.getId());
			SamModuleService sms = new SamModuleService(smsId);

			getHibernateTemplate().save(sms);

		}

		return services.size();
	}

	@Transactional
	public String updateModule(ModuleServiceBo moduleServiceBo) {

		SamModule module = moduleServiceBo.getModule();
		List<SamService> services = moduleServiceBo.getServices();
		final String moduleId = module.getId();

		getHibernateTemplate().update(module);

		int count = (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query query = session.createQuery("delete SamModuleService where id.moduleId = '"+moduleId+"'");
//						query.setString(0, moduleId);
						return query.executeUpdate();
					}
				}, true);

		log.debug("for update module's services, delete all module-service first. ["
				+ count + "]");

		int updateCount = addOnlyModuleServices(moduleId, services);

		log.debug("update new module-service. [" + updateCount + "]");

		return moduleId;
	}

	public List<SamModule> queryModuleByNameCat(String moduleName, String catId) {

		if (moduleName == null)
			moduleName = "";

		String hql = "from SamModule where name like '"+"%" + moduleName + "%"+"'";
//		List params = new ArrayList();
//		params.add("%" + moduleName + "%");

		if (catId != null && !catId.equals("")) {
			hql += " and categoryId = '"+catId+"'";
//			params.add(catId);
		}

		List<SamModule> list = (List<SamModule>) getHibernateTemplate().find(
				hql);
		return list;
	}

	public PagingResult<SamModuleCategory> findModuleCategory(
			SamModuleCategory qryCond, PagingParam pp) {
		if (pp == null)
			pp = new PagingParam();
		String name = qryCond.getName().trim();
		logger.debug("call findModuleCategory(): " + name);
		String hql = "from SamModuleCategory";
		StringBuilder where = new StringBuilder(" where 1=1 ");
		List params = new ArrayList();
		if(qryCond.getIsDetail() != null){
//			params.add(qryCond.getIsDetail());
			where.append(" and isDetail = "+(qryCond.getIsDetail()?"1":"0")+" ");
		}
		if (StringUtils.hasText(name)) {
//			params.add("%" + name + "%");
			where.append(" and name like '"+"%" + name + "%"+"'");
		}
//		if(params.size() > 0){
//			where.replace(0, 4, " where ");
//		}
		hql += where.toString() ;
		String orderby = " order by ordinal, name";
		
		int totalSize = ((Long)(getHibernateTemplate().find("select count(*) "+hql).get(0))).intValue();
		final String fhql = hql+orderby;
		final PagingParam fpp = pp;
		final List fparams = params;
		List<SamModuleCategory> list = getHibernateTemplate().executeFind(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
				Query query = arg0.createQuery(fhql);
//				for (int i = 0; i < fparams.size(); i++) {
//					query.setParameter(i, fparams.get(i));
//				}
				query.setFirstResult(fpp.getStart()).setMaxResults(fpp.getLimit());
				return query.list();
			}
		});
		logger.debug(list);
		return new PagingResult<SamModuleCategory>(totalSize, list);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public SamModuleCategory editModuleCategory(SamModuleCategory category) {
		if (!StringUtils.hasText(category.getId()))
			category.setId(null);
		if(category.getIsDetail()){
			category.setLevel(2);
		}else{
			category.setLevel(1);
		}
		getHibernateTemplate().saveOrUpdate(category);
		return category;
	}

	@Override
	public void removeModuleCategory(String categoryIds) {
		if (!StringUtils.hasText(categoryIds))
			return;
		for (String id : categoryIds.split(",")) {
			removeModuleCategoryById(id);
		}
		return;
	}

	@Transactional
	private void removeModuleCategoryById(String id) {
		getHibernateTemplate().delete(
				getHibernateTemplate().get(SamModuleCategory.class, id));
	}

	@Override
	public PagingResult<SamRole> findRoles(SamRole qryCond, PagingParam pp) {
		if (pp == null)
			pp = new PagingParam();
		String name = qryCond.getName().trim();
		logger.debug("call findRoles(): " + name);
		String hql = "from SamRole";
		List params = new ArrayList();
		if (StringUtils.hasText(name)) {
//			params.add("%" + name + "%");
			hql += " where name like '"+"%" + name + "%"+"'";
		}
		String orderby = " order by name";
		int totalSize = ((Long)(getHibernateTemplate().find("select count(*) "+hql).get(0))).intValue();
		final String fhql = hql+orderby;
		final PagingParam fpp = pp;
		final List fparams = params;
		List<SamRole> list = getHibernateTemplate().executeFind(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
				Query query = arg0.createQuery(fhql);
//				for (int i = 0; i < fparams.size(); i++) {
//					query.setParameter(i, fparams.get(i));
//				}
				query.setFirstResult(fpp.getStart()).setMaxResults(fpp.getLimit());
				return query.list();
			}
		});
		return new PagingResult<SamRole>(totalSize, list);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public SamRole editRole(SamRole role) {
		if (!StringUtils.hasText(role.getId()))
			role.setId(null);
		getHibernateTemplate().saveOrUpdate(role);
		return role;
	}

	@Override
	public void removeRoles(String roles) {
		if (!StringUtils.hasText(roles))
			return;

		for (String id : roles.split(",")) {
			removeRoleById(id);
		}
	}

	@Transactional
	private void removeRoleById(String id) {
		getHibernateTemplate().delete(
				getHibernateTemplate().get(SamRole.class, id));
	}

	@Override
	public PagingResult<SamModule> findModules(SamModule qryCond, PagingParam pp) {
		if (pp == null)
			pp = new PagingParam();

		StringBuilder where = new StringBuilder(" where 1=1 ");
		List params = new ArrayList();

		String name = qryCond.getName().trim();
		if (StringUtils.hasText(name)) {
//			params.add("%" + name + "%");
			where.append(" and name like '"+"%" + name + "%"+"'");
		}
		if (StringUtils.hasText(qryCond.getCategoryId())) {
//			params.add(qryCond.getCategoryId());
			where.append(" and categoryId = '"+qryCond.getCategoryId()+"'");
		}
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder("from SamModule").append(where);
		String orderby = " order by name";
		log.debug("hql: " + hql.toString());
		int totalSize = ((Long)(getHibernateTemplate().find("select count(*) "+hql.toString()).get(0))).intValue();
		final String fhql = hql.toString()+orderby;
		final PagingParam fpp = pp;
		final List fparams = params;
		List<SamModule> list = getHibernateTemplate().executeFind(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
				Query query = arg0.createQuery(fhql);
//				for (int i = 0; i < fparams.size(); i++) {
//					query.setParameter(i, fparams.get(i));
//				}
				query.setFirstResult(fpp.getStart()).setMaxResults(fpp.getLimit());
				return query.list();
			}
		});
		return new PagingResult<SamModule>(totalSize, list);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public SamModule editModule(SamModule module) {
		if (!StringUtils.hasText(module.getId()))
			module.setId(null);
		getHibernateTemplate().saveOrUpdate(module);
		return module;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ResidentPopulation editResidentPopulation(
			ResidentPopulation population) {
		if (!StringUtils.hasText(population.getId()))
			population.setId(null);
		getHibernateTemplate().saveOrUpdate(population);
		return population;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public DoctorsBO editDoctors(DoctorsBO data) {
		Doctors doctors = new Doctors();
		BeanUtils.copyProperties(data, doctors);
		getHibernateTemplate().saveOrUpdate(doctors);
		return data;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeDoctors(String doctors) {
		if (!StringUtils.hasText(doctors))
			return;

		for (String id : doctors.split(",")) {
			getHibernateTemplate().delete(
					getHibernateTemplate().get(Doctors.class,
							Integer.valueOf(id)));
		}

	}

	@Override
	public PagingResult<DoctorsBO> findDoctors(DoctorsBO qryCond, PagingParam pp) {
		if (pp == null)
			pp = new PagingParam();

		StringBuilder where = new StringBuilder(" where 1=1 ");
		List params = new ArrayList();

		String hospId = qryCond.getHospitalPropId().trim();
		if (StringUtils.hasText(hospId)) {
//			params.add(Integer.parseInt(hospId));
			where.append(" and a.hospId = "+hospId+"");
		}
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
			where.append(" And a.hospId = b.id");
//		} else {
//			where.append(" Where a.hospId = b.id");
//		}

		StringBuilder hql = new StringBuilder("from Doctors a,SamTaxorgcode b")
				.append(where);
		String orderby = " order by a.id";
		log.debug("hql: " + hql.toString());

		int totalSize = ((Long)(getHibernateTemplate().find("select count(*) "+hql.toString()).get(0))).intValue();
		final String fhql = hql.toString()+orderby;
		final PagingParam fpp = pp;
		final List fparams = params;
		List list = getHibernateTemplate().executeFind(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
				Query query = arg0.createQuery(fhql);
//				for (int i = 0; i < fparams.size(); i++) {
//					query.setParameter(i, fparams.get(i));
//				}
				query.setFirstResult(fpp.getStart()).setMaxResults(fpp.getLimit());
				return query.list();
			}
		});
		List<DoctorsBO> doctors = new ArrayList<DoctorsBO>();
		for (Object obj : list) {
			DoctorsBO tmpDoctors = new DoctorsBO();
			Object[] objs = (Object[]) obj;
			BeanUtils.copyProperties(objs[0], tmpDoctors);
			String tmpHospName = ((SamTaxorgcode) objs[1]).getName();
			tmpDoctors.setHospitalName(tmpHospName);
			doctors.add(tmpDoctors);
		}
		return new PagingResult<DoctorsBO>(totalSize, doctors);
	}

	@Override
	public PagingResult<ResidentPopulation> findResidentPopulation(
			ResidentPopulation qryCond, PagingParam pp) {
		if (pp == null)
			pp = new PagingParam();

		StringBuilder where = new StringBuilder(" where 1=1 ");
		List params = new ArrayList();

		String name = qryCond.getName().trim();
		if (StringUtils.hasText(name)) {
			Integer orgId = Integer.parseInt(name);
//			params.add(orgId);
			where.append(" and orgId = "+orgId+"");
		}
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder("from ResidentPopulation")
				.append(where);
		String orderby = " order by name";
		log.debug("hql: " + hql.toString());

		int totalSize = ((Long)(getHibernateTemplate().find("select count(*) "+hql.toString()).get(0))).intValue();
		final String fhql = hql.toString()+orderby;
		final PagingParam fpp = pp;
		final List fparams = params;
		List<ResidentPopulation> list = getHibernateTemplate().executeFind(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
				Query query = arg0.createQuery(fhql);
//				for (int i = 0; i < fparams.size(); i++) {
//					query.setParameter(i, fparams.get(i));
//				}
				query.setFirstResult(fpp.getStart()).setMaxResults(fpp.getLimit());
				return query.list();
			}
		});
		return new PagingResult<ResidentPopulation>(totalSize, list);

	}

	@Transactional
	@Override
	public void removeModules(String modules) {
		if (!StringUtils.hasText(modules))
			return;

		for (String id : modules.split(",")) {
			getHibernateTemplate().delete(
					getHibernateTemplate().get(SamModule.class, id));
		}

	}

	@Override
	public List<RoleModuleBo> findModulesByRole(String roleId) {
		logger.debug("role id: " + roleId);
		List<RoleModuleBo> list = new ArrayList<RoleModuleBo>();
		if (!StringUtils.hasText(roleId)) {
			return list;
		}
		String hql = "select a.id.moduleId from SamRoleModule a where a.id.roleId = '"+roleId+"'";
		List<String> moduleIds = getHibernateTemplate().find(hql);
		List<SamModule> allModules = getHibernateTemplate().find(
				"from SamModule order by category.displayOrder, ordinal");
		for (SamModule m : allModules) {
			boolean checked = false;
			for (String id : moduleIds) {
				if (id.equals(m.getId())) {
					checked = true;
					break;
				}
			}
			list.add(new RoleModuleBo(m, checked));
		}
		logger.debug("return module by role: " + list);
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveRoleModules(String roleId, List<String> moduleIds) {
		getHibernateTemplate().deleteAll(
				getHibernateTemplate().find(
						"from SamRoleModule where id.roleId = '"+roleId+"'"));
		for (String modId : moduleIds) {
			SamRoleModule o = new SamRoleModule(new SamRoleModuleId(modId,
					roleId));
			getHibernateTemplate().save(o);
		}
	}

	@Override
	public String getHospitalRelatedName(int id) {
		HospitalProp hospitalProp = (HospitalProp) getHibernateTemplate().get(
				HospitalProp.class, id);
		return hospitalProp.getRelatedName();
	}

	@Override
	public PagingResult<HighRisk> findHighRisk(HighRisk qryCond, PagingParam pp) {
		if (pp == null)
			pp = new PagingParam();

		StringBuilder where = new StringBuilder(" where 1=1 ");
		List params = new ArrayList();

		String exception = qryCond.getException().trim();
		if (StringUtils.hasText(exception)) {
//			params.add("%" + exception + "%");
			where.append(" and exception like '"+"%" + exception + "%"+"'");
		}
//		if (params.size() != 0) {
//			where.replace(0, 4, " where ");
//		}
		StringBuilder hql = new StringBuilder("from HighRisk").append(where);
		String orderby = " order by number";
		log.debug("hql: " + hql.toString());

		int totalSize = ((Long)(getHibernateTemplate().find("select count(*) "+hql.toString(),params.toArray()).get(0))).intValue();
		final String fhql = hql.toString() +orderby;
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
		return new PagingResult<HighRisk>(totalSize, list);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public HighRisk editHighRisk(HighRisk data) {
		if (!StringUtils.hasText(data.getId()))
			data.setId(null);
		String exception = data.getException();
		data.setExceptionPng(ChineseUtils.getFirstLetterFromChinese(exception));
		getHibernateTemplate().saveOrUpdate(data);
		return data;
	}

	@Transactional
	@Override
	public void removeHighRisk(String highRiskId) {
		if (!StringUtils.hasText(highRiskId))
			return;
		for (String id : highRiskId.split(",")) {
			getHibernateTemplate().delete(
					getHibernateTemplate().get(HighRisk.class, id));
		}
		return;
	}
	private static Integer ENCRYPTION_ID = 4;//是否加密的主键
	private static Integer DEFAULT_VALUE = 0;//空值
	
	@Override
	public Integer getEncryptionInfo() {
		SystemInformation info = (SystemInformation)getHibernateTemplate().get(SystemInformation.class, ENCRYPTION_ID);
		if(info == null)
			return DEFAULT_VALUE;
		return Integer.parseInt(info.getVal());
	}
}
