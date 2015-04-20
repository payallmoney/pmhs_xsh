package cn.net.tongfang.web.service.commonexam;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.NullableType;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.net.tongfang.framework.security.SecurityManager;
import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.District;
import cn.net.tongfang.framework.security.vo.ExamBaseinfo;
import cn.net.tongfang.framework.security.vo.ExamCrud;
import cn.net.tongfang.framework.security.vo.ExamExamcfg;
import cn.net.tongfang.framework.security.vo.ExamExamcfgFileno;
import cn.net.tongfang.framework.security.vo.ExamItemcfg;
import cn.net.tongfang.framework.security.vo.ExamItems;
import cn.net.tongfang.framework.security.vo.ExamItemsId;
import cn.net.tongfang.framework.security.vo.ExamQueryCfg;
import cn.net.tongfang.framework.security.vo.ExamQuerySql;
import cn.net.tongfang.framework.security.vo.SamTaxorgcode;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.web.util.FileNoGen;

public class CommonExamService extends HibernateDaoSupport {
	private static final Logger log = Logger.getLogger(CommonExamService.class);
	private static String OPT_NEW = "new";
	private static String OPT_LIST = "list";
	private static String VALUE_TYPE_STRING = "string";
	private static String VALUE_TYPE_DATE = "date";
	private static String VALUE_TYPE_NUMBER = "number";
	private static SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
	private static SimpleDateFormat shortdateformat = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat longdateformat = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
	private CommonExamUtil commonExamUtil;
	private FileNoGen fileNoGen;
	private static String SQL_OPT_LIKE = "like";
	private static String SQL_OPT_LEFTLIKE = "leftlike";
	private static String SQL_OPT_RIGHTLIKE = "rightlike";
	private static String DATE_DAYMAX = " 23:59:59.999";
	private static String DATE_DAYMIN = " 00:00:00";
	private static String PARAMS = "params";
	private static String WHERES = "wheres";
	private static String TYPES = "types";
	private static String VALUE = "value";
	private static String PARAMSPLIT = "##";
	private static String CONDITIONSPLIT = " ";
	private static String COMMONQUERY = "__query__";
	private static String PRE = "pre";
	private static String COLUMN = "col";
	private static String PAGE = "page";
	private static String PAGESIZE = "pagesize";
	private static String CURRENTPAGE = "currentpage";
	private static String COLTYPE = "coltype";
	private static int DEFAULTPAGESIZE = 20;
	private static Map<String, NullableType> hbtTypeMap = new HashMap();
	{
		hbtTypeMap.put("string", Hibernate.STRING);
		hbtTypeMap.put("date", Hibernate.TIMESTAMP);
		hbtTypeMap.put("number", Hibernate.BIG_DECIMAL);
	}

	public Map newExam(String examname) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date today = cal.getTime();
		Map ret = new HashMap();
		// 将当前日期、当期操作员相关信息传入前台;让前台进行相关初始化工作
		ret.put("now", new Date());
		ret.put("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		ret.put("user", SecurityManager.currentOperator());
		ret.put("items", get_cfg_list(examname));
		return ret;
	}

	public List<District> getDistrict(String orgid) {
		if (orgid == null) {
			orgid = "root" + SecurityManager.currentOperator().getDistrictId();
		}
		System.out.println("========orgid===========" + orgid);
		return commonExamUtil.getDistrict(orgid);
	}

	public Map examList(String examname, String userdistrict, Map<String, Map> params, Map<String, Map<String, String>> basemap, List<String> collist) throws Exception {
		
		// 这是默认的查询条件
		String countsql = " select count(*)";
		String sql = "";
		String froms = " from exam_baseinfo info , healthfile hf , PersonalInfo pf ";
		String select = "";

		String where = " where info.examname='" + examname + "' and  info.fileno =hf.fileno and info.fileno = pf.fileno and info.status=1 "
				+ "  and hf.DistrictNumber like '" + userdistrict + "%' ";
		String orderby = " order by info.inputdate desc ";
		
		
		Query filenoquery = getSession().createQuery("from ExamExamcfgFileno where examname = ? and itemname <>'mainfile' ");
		filenoquery.setParameter(0, examname);
		List<ExamExamcfgFileno> filenolist = filenoquery.list();
		for(int i = 0 ; i<filenolist.size();i++){
			ExamExamcfgFileno filenovo = (ExamExamcfgFileno)filenolist.get(i);
			froms += ", exam_items it"+filenovo.getOrd()+", healthfile hf"+filenovo.getOrd()+" , PersonalInfo pf"+filenovo.getOrd();
			where += " and info.id = it"+filenovo.getOrd()+".id and it"+filenovo.getOrd()+".item='配偶_编号' and it"+filenovo.getOrd()+".value = hf"+filenovo.getOrd()+".fileno  and hf"+filenovo.getOrd()+".fileno = pf"+filenovo.getOrd()+".fileno  ";
		}
		// params.getBase();
		Field[] fields = ExamBaseinfo.class.getDeclaredFields();
		List sqlparams = new ArrayList();
		List<Type> sqlparamtypes = new ArrayList();
		Map<String, Field> fieldmap = new HashMap();
		for (int i = 0; i < fields.length; i++) {
			fieldmap.put(fields[i].getName(), fields[i]);
		}
		int count = 0;
		Map preMap = new HashMap();
		System.out.println("======" + params.size());
		Map itemsMap = new HashMap();
		for (Iterator it = params.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			System.out.println("===key===" + key);
			Map<String, String> value = (Map) params.get(key);
			System.out.println("==value====" + value);
			if (value != null) {
				if (basemap.containsKey(key)) {
					if (!basemap.get(key).get("isitem").equals("true")) {
						Map sqls = getBaseSql(basemap.get(key), value, basemap.get(key).getClass());
						where += (String) sqls.get(WHERES);
						sqlparams.addAll((List) sqls.get(PARAMS));
						sqlparamtypes.addAll((List) sqls.get(TYPES));
					} else {
						itemsMap.put(key, value);
					}
				} else if (COMMONQUERY.equals(key)) {
					// 这里是通用查询
					String[] commonparams = value.get("value").split(PARAMSPLIT);
					for (int i = 0; i < commonparams.length; i++) {
						String tmp = commonparams[i];
						while (tmp.indexOf(CONDITIONSPLIT + CONDITIONSPLIT) >= 0) {
							tmp = tmp.replaceAll(CONDITIONSPLIT + CONDITIONSPLIT, CONDITIONSPLIT);
						}
						String[] cols = commonparams[i].split(CONDITIONSPLIT);
						if (cols.length < 3) {
							throw new Exception("通用查询参数" + commonparams[i] + "格式不正确,正确格式为\"姓名 = 张三\"或\"出生日期 = 20120101-20120113\" 或 \"身份证号 = 身份证号1,身份证号2,身份证号3\"");
						} else {
							if (basemap.containsKey(cols[0]) && fieldmap.containsKey(basemap.get(cols[0]).get(COLUMN))) {
								Map valuemap = new HashMap();
								valuemap.put(VALUE, cols[2]);
								Map sqls = getBaseSql(basemap.get(cols[0]).get(COLUMN), valuemap, fieldmap.get(basemap.get(cols[0]).get(COLUMN)).getType());
								where += (String) sqls.get(WHERES);
								sqlparams.addAll((List) sqls.get(PARAMS));
								sqlparamtypes.addAll((List) sqls.get(TYPES));

							} else {
								if (commonExamUtil.hasExamItem(examname, cols[0])) {
									Map valuemap = new HashMap();
									valuemap.put(VALUE, cols[2]);
									String itempre = "t" + count;
									preMap.put(cols[0], count);
									count++;
									froms += ", exam_items " + itempre;
									Map sqls = getSql(examname, itempre, cols[0], valuemap);
									where += (String) sqls.get(WHERES);
									sqlparams.addAll((List) sqls.get(PARAMS));
									sqlparamtypes.addAll((List) sqls.get(TYPES));
								}
							}
						}
					}
				} else if (commonExamUtil.hasExamItem(examname, key)) {
					String itempre = "t" + count;
					preMap.put(key, count);
					count++;
					froms += ", exam_items " + itempre;
					Map sqls = getSql(examname, itempre, key, value);
					where += (String) sqls.get(WHERES);
					sqlparams.addAll((List) sqls.get(PARAMS));
					sqlparamtypes.addAll((List) sqls.get(TYPES));
				}
			}
		}
		Map<String, NullableType> scalarmap = new HashMap();
		if (collist != null && !collist.isEmpty()) {
			int colnum = 0;
			for (String key : collist) {

				if (basemap.containsKey(key)) {
					if (basemap.get(key).get("isitem").equals("true")) {
						String itempre = "t" + count;
						froms += ", exam_items " + itempre;
						count++;
						if (basemap.get(key).get("encflag").equals("true")) {
							select += ",dbo.denc(" + itempre + ".value) 'col" + colnum + "'";
						}else{
							select += "," + itempre + ".value 'col" + colnum + "'";
						}
						
						if (itemsMap.containsKey(key)) {
							Map sqls = getItemWhere(basemap.get(key).get(COLUMN), (Map) itemsMap.get(key), itempre,basemap.get(key).get("encflag"));
							where += (String) sqls.get(WHERES);
							sqlparams.addAll((List) sqls.get(PARAMS));
							sqlparamtypes.addAll((List) sqls.get(TYPES));
							where += " and info.id= " + itempre + ".id and " + itempre + ".idx=0 and " + itempre + ".item = '" + basemap.get(key).get(COLUMN) + "'";
						} else {
							where += " and info.id= " + itempre + ".id and " + itempre + ".idx=0 and " + itempre + ".item = '" + basemap.get(key).get(COLUMN) + "'";
						}
					} else {
						if (basemap.get(key).get("encflag").equals("true")) {
							select += ",dbo.denc(" + basemap.get(key).get(COLUMN) + ") 'col" + colnum + "'";
						}else{
							select += "," + basemap.get(key).get(COLUMN) + " 'col" + colnum + "'";
						}
					}
				} else if (commonExamUtil.hasExamItem(examname, key)) {
					String itempre = "t" + count;
					froms += ", exam_items " + itempre;
					count++;
					select += "," + itempre + ".value 'col" + colnum + "'";
				}
				scalarmap.put("col" + colnum, hbtTypeMap.get(basemap.get(key).get(COLTYPE)));
				colnum++;
			}
			select = "select " + select.substring(1);
			sql = select + froms + where + orderby;
		} else {
			sql += froms + where + orderby;
		}
		// 计算总数,页数
		countsql += froms + where;
		SQLQuery countquery = this.getSession().createSQLQuery(countsql);
		if (sqlparamtypes.size() > 0) {
			NullableType[] types = new NullableType[sqlparamtypes.size()];
			types = sqlparamtypes.toArray(types);
			countquery.setParameters(sqlparams.toArray(), types);
		}
		List countlist = countquery.list();
		int allcount = 0;
		System.out.println("==countlist.size()====" + countlist.size());
		if (countlist.size() > 0) {
			allcount = (Integer) countlist.get(0);
		}
		Map pageparams = params.get(PAGE);
		int pagesize = Integer.parseInt((String) pageparams.get(PAGESIZE));
		int currentpage = Integer.parseInt((String) pageparams.get(CURRENTPAGE));
		if (pagesize <= 0) {
			pagesize = DEFAULTPAGESIZE;
		}

		int pages = allcount / pagesize + (allcount % pagesize > 0 ? 1 : 0);
		if (currentpage > pages) {
			currentpage = pages;
		}
		if (currentpage <= 0) {
			currentpage = 1;
		}
		int min = pagesize * (currentpage - 1);
		int max = pagesize * currentpage;
		// 查询分页结果
		SQLQuery query = this.getSession().createSQLQuery(sql);
		System.out.println("==sql====" + sql);
		if (sqlparamtypes.size() > 0) {
			NullableType[] types = new NullableType[sqlparamtypes.size()];
			types = sqlparamtypes.toArray(types);
			query.setParameters(sqlparams.toArray(), types);
		}
		if (scalarmap.size() > 0) {
			for (Iterator it = scalarmap.keySet().iterator(); it.hasNext();) {
				String key = (String) it.next();
				NullableType type = (NullableType) scalarmap.get(key);
				query.addScalar(key, type);
			}
		}
		// query.setParameters(sqlparams.toArray(),
		// (Type[])sqlparamtypes.toArray());
		query.setFirstResult(min);
		query.setMaxResults(max);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map ret = new HashMap();
		List retlist = query.list();
		System.out.println("======" + retlist.size());
		ret.put("rows", retlist);
		ret.put("currentpage", currentpage);
		ret.put("total", allcount);
		ret.put("pages", pages);
		return ret;
	}
	

	public CommonVO loadExam(String id) throws Exception {
		ExamBaseinfo base = (ExamBaseinfo) this.getHibernateTemplate().get(ExamBaseinfo.class, id);
		List<ExamItems> queryitems = this.getHibernateTemplate().find(" from ExamItems where id.id =?  ", id);
		List<Map> items = new ArrayList();
		for (ExamItems obj : queryitems) {
			while ((obj.getId().getIdx() + 1) > items.size()) {
				Map item = new HashMap();
				items.add(item);
			}
			items.get(obj.getId().getIdx()).put(obj.getId().getItem(), obj.getValue());
		}
		CommonVO ret = new CommonVO();
		ret.setBase(base);
		ret.setItems(items);
		// 取出男女方的基础数据
		String sql = "select dbo.denc(hf.fileNo) as fileno, dbo.denc(hf.name) as name, p.sex, p.birthday,(year(getDate()) - year(p.birthday)) as age,"
				+ " dbo.denc(p.idnumber) as idcard,hf.barCode,hf.address ,hf.tel as linkmanTel,hf.township,hf.village,p.workUnit,p.folk,p.folkOther,"
				+ " p.education,p.occupation,p.idnumber,hf.residenceAddress,hf.districtNumber" + " from HealthFile hf, PersonalInfo  p where  p.fileNo = hf.fileNo "
				+ " and hf.fileNo = ? and hf.status = 0 ";
		SQLQuery womanquery = this.getSession().createSQLQuery(sql);
		womanquery.setParameters(new Object[] { base.getFileno() }, new Type[] { Hibernate.STRING });
		womanquery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		womanquery.addScalar("fileno", Hibernate.STRING);
		womanquery.addScalar("name", Hibernate.STRING);
		womanquery.addScalar("sex", Hibernate.STRING);
		womanquery.addScalar("birthday", Hibernate.TIMESTAMP);
		womanquery.addScalar("age", Hibernate.INTEGER);
		womanquery.addScalar("idcard", Hibernate.STRING);
		womanquery.addScalar("barCode", Hibernate.STRING);
		womanquery.addScalar("address", Hibernate.STRING);
		womanquery.addScalar("linkmanTel", Hibernate.STRING);
		womanquery.addScalar("township", Hibernate.STRING);
		womanquery.addScalar("village", Hibernate.STRING);
		womanquery.addScalar("workUnit", Hibernate.STRING);
		womanquery.addScalar("folk", Hibernate.STRING);
		womanquery.addScalar("folkOther", Hibernate.STRING);
		womanquery.addScalar("education", Hibernate.STRING);
		womanquery.addScalar("occupation", Hibernate.STRING);
		womanquery.addScalar("idnumber", Hibernate.STRING);
		womanquery.addScalar("residenceAddress", Hibernate.STRING);
		womanquery.addScalar("districtNumber", Hibernate.STRING);
		List<Map> womanlist = womanquery.list();
		if (womanlist.size() > 0) {
			ret.setWoman(womanlist.get(0));
		}
		SQLQuery manquery = this.getSession().createSQLQuery(sql);
		manquery.setParameters(new Object[] { items.get(0).get("男方_编号") }, new Type[] { Hibernate.STRING });
		manquery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		manquery.addScalar("fileno", Hibernate.STRING);
		manquery.addScalar("name", Hibernate.STRING);
		manquery.addScalar("sex", Hibernate.STRING);
		manquery.addScalar("birthday", Hibernate.TIMESTAMP);
		manquery.addScalar("age", Hibernate.INTEGER);
		manquery.addScalar("idcard", Hibernate.STRING);
		manquery.addScalar("barCode", Hibernate.STRING);
		manquery.addScalar("address", Hibernate.STRING);
		manquery.addScalar("linkmanTel", Hibernate.STRING);
		manquery.addScalar("township", Hibernate.STRING);
		manquery.addScalar("village", Hibernate.STRING);
		manquery.addScalar("workUnit", Hibernate.STRING);
		manquery.addScalar("folk", Hibernate.STRING);
		manquery.addScalar("folkOther", Hibernate.STRING);
		manquery.addScalar("education", Hibernate.STRING);
		manquery.addScalar("occupation", Hibernate.STRING);
		manquery.addScalar("idnumber", Hibernate.STRING);
		manquery.addScalar("residenceAddress", Hibernate.STRING);
		manquery.addScalar("districtNumber", Hibernate.STRING);
		List<Map> manlist = manquery.list();
		if (manlist.size() > 0) {
			ret.setMan(manlist.get(0));
		}
		return ret;
	}

	private List<Map> getQueryList(SQLQuery query,ExamBaseinfo base,String param){
		query.setParameters(new Object[] {param}, new Type[] { Hibernate.STRING });
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.addScalar("fileno", Hibernate.STRING);
		query.addScalar("name", Hibernate.STRING);
		query.addScalar("sex", Hibernate.STRING);
		query.addScalar("birthday", Hibernate.TIMESTAMP);
		query.addScalar("age", Hibernate.INTEGER);
		query.addScalar("idcard", Hibernate.STRING);
		query.addScalar("barCode", Hibernate.STRING);
		query.addScalar("address", Hibernate.STRING);
		query.addScalar("linkmanTel", Hibernate.STRING);
		query.addScalar("township", Hibernate.STRING);
		query.addScalar("village", Hibernate.STRING);
		query.addScalar("workUnit", Hibernate.STRING);
		query.addScalar("folk", Hibernate.STRING);
		query.addScalar("folkOther", Hibernate.STRING);
		query.addScalar("education", Hibernate.STRING);
		query.addScalar("occupation", Hibernate.STRING);
		query.addScalar("idnumber", Hibernate.STRING);
		query.addScalar("residenceAddress", Hibernate.STRING);
		query.addScalar("districtNumber", Hibernate.STRING);
		query.addScalar("nation", Hibernate.STRING);
		return query.list();
	}
	
	
	public CommonVO common_loadExam(String id) throws Exception {
		System.out.println("===id===" + id);
		ExamBaseinfo base = (ExamBaseinfo) this.getHibernateTemplate().get(ExamBaseinfo.class, id);
		List<ExamItems> queryitems = this.getHibernateTemplate().find(" from ExamItems where id.id =?  ", id);
		List<Map> items = new ArrayList();
		for (ExamItems obj : queryitems) {
			while ((obj.getId().getIdx() + 1) > items.size()) {
				Map item = new HashMap();
				items.add(item);
			}
			items.get(obj.getId().getIdx()).put(obj.getId().getItem(), obj.getValue());
		}
		CommonVO ret = new CommonVO();
		ret.setBase(base);
		ret.setItems(items);
		HashMap<String,Map> fileinfos = new HashMap<String,Map>();
		
		// 取出基础数据
		String sql = "select dbo.denc(hf.fileNo) as fileno, dbo.denc(hf.name) as name, p.sex, p.birthday,(year(getDate()) - year(p.birthday)) as age,"
				+ " dbo.denc(p.idnumber) as idcard,hf.barCode,hf.address ,hf.tel as linkmanTel,hf.township,hf.village,p.workUnit,p.folk,p.folkOther,"
				+ " p.education,p.occupation,p.idnumber,hf.residenceAddress,hf.districtNumber,hf.nation" + " from HealthFile hf, PersonalInfo  p where  p.fileNo = hf.fileNo "
				+ " and hf.fileNo = ? and hf.status = 0 ";
		Query filenoquery = getSession().createQuery("from ExamExamcfgFileno  where examname = ? ");
		filenoquery.setParameter(0, base.getExamname());
		List<ExamExamcfgFileno> filenolist = filenoquery.list();
		for(int i = 0 ; i<filenolist.size();i++){
			ExamExamcfgFileno filenovo = filenolist.get(i);
			SQLQuery query = this.getSession().createSQLQuery(sql);
			String param = base.getFileno();
			if(!"mainfile".equals(filenovo.getItemname())){
				param = (String)items.get(0).get(filenovo.getItemname());
				if(param == null){
					throw new Exception("请与系统管理员联系!检查配置表的 \""+filenovo.getItemname()+"\"配置错误!");
				}
			}
			List<Map> fileinfolist = getQueryList(query,base,param);
			if (fileinfolist.size() > 0) {
				Map fileinfo = fileinfolist.get(0);
				fileinfos.put(filenovo.getItemname(), fileinfo);
			}
		}
		ret.setFileinfo(fileinfos);
		return ret;
	}

	public String delExam(String id) throws Exception {
		ExamBaseinfo base = (ExamBaseinfo) this.getHibernateTemplate().get(ExamBaseinfo.class, id);
		if (!base.getInputpersonid().equals(SecurityManager.currentOperator().getUsername())) {
			throw new Exception("只允许" + base.getInputpersonid() + "进行删除!");
		}
		base.setStatus(-1);
		// this.getHibernateTemplate().delete(base);
		// this.getSession().createQuery(" delete ExamItems where id.id =?  ").setParameter(0,
		// id).executeUpdate();
		return "删除成功!";
	}

	/**
	 * 由于婚检有两个人的fileno,所以另写了个查询 只有froms 和where的初始内容不同,其他一致
	 * 
	 * @param examname
	 * @param params
	 * @param basemap
	 * @param collist
	 * @return
	 * @throws Exception
	 */

	public Map marry_examList(String examname, String userdistrict, Map<String, Map> params, Map<String, Map<String, String>> basemap, List<String> collist) throws Exception {
		String countsql = " select count(*)";
		// 这里是默认的查询内容
		String sql = " select dbo.denc(info.fileno) 'col1',dbo.denc(hf.name) 'col2' , pf.Birthday 'col3' ,info.inputdate 'col4' ";
		String froms = " from exam_baseinfo info , healthfile hf , PersonalInfo pf  , exam_items it, healthfile hf1 , PersonalInfo pf1 ";
		String where = " where info.examname='"
				+ examname
				+ "' and  info.fileno =hf.fileno and info.status=1  and info.fileno = pf.fileno and info.id = it.id and it.item='男方_编号' and it.value = hf1.fileno  and it.value = pf1.fileno "
				+ " and (hf.DistrictNumber like '" + userdistrict + "%' or hf1.DistrictNumber like '" + userdistrict + "%')";
		String orderby = " order by info.inputdate desc ";
		String select = "";
		// params.getBase();
		Field[] fields = ExamBaseinfo.class.getDeclaredFields();
		List sqlparams = new ArrayList();
		List<Type> sqlparamtypes = new ArrayList();
		Map<String, Field> fieldmap = new HashMap();
		for (int i = 0; i < fields.length; i++) {
			fieldmap.put(fields[i].getName(), fields[i]);
		}
		int count = 0;
		System.out.println("=======params============" + params);
		for (Iterator it = params.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			Map<String, String> value = (Map) params.get(key);
			if (value != null) {
				System.out.println("======"+basemap.containsKey(key));
				if (basemap.containsKey(key)) {
					Map sqls = getBaseSql(basemap.get(key).get(COLUMN), value, basemap.get(key).getClass());
					where += (String) sqls.get(WHERES);
					sqlparams.addAll((List) sqls.get(PARAMS));
					sqlparamtypes.addAll((List<Type>) sqls.get(TYPES));
				} else if (COMMONQUERY.equals(key)) {
					// 这里是通用查询
					String[] commonparams = value.get("value").split(PARAMSPLIT);
					System.out.println("======"+commonparams);
					for (int i = 0; i < commonparams.length; i++) {
						String tmp = commonparams[i];
						while (tmp.indexOf(CONDITIONSPLIT + CONDITIONSPLIT) >= 0) {
							tmp = tmp.replaceAll(CONDITIONSPLIT + CONDITIONSPLIT, CONDITIONSPLIT);
						}
						String[] cols = commonparams[i].split(CONDITIONSPLIT);
						if (cols.length < 3) {
							throw new Exception("通用查询参数" + commonparams[i] + "格式不正确,正确格式为\"姓名 = 张三\"或\"出生日期 = 20120101-20120113\" 或 \"身份证号 = 身份证号1,身份证号2,身份证号3\"");
						} else {
							if (basemap.containsKey(cols[0]) && fieldmap.containsKey(basemap.get(cols[0]).get(COLUMN))) {
								Map valuemap = new HashMap();
								valuemap.put(VALUE, cols[2]);
								Map sqls = getBaseSql(basemap.get(cols[0]).get(COLUMN), valuemap, fieldmap.get(basemap.get(cols[0]).get(COLUMN)).getType());
								where += (String) sqls.get(WHERES);
								sqlparams.addAll((List) sqls.get(PARAMS));
								sqlparamtypes.addAll((List<Type>) sqls.get(TYPES));
							} else {
								if (commonExamUtil.hasExamItem(examname, cols[0])) {
									Map valuemap = new HashMap();
									valuemap.put(VALUE, cols[2]);
									String itempre = "t" + count;
									count++;
									froms += ", exam_items " + itempre;
									Map sqls = getSql(examname, itempre, cols[0], valuemap);
									where += (String) sqls.get(WHERES);
									sqlparams.addAll((List) sqls.get(PARAMS));
									sqlparamtypes.addAll((List<Type>) sqls.get(TYPES));
								}
							}
						}
					}
				} else if (commonExamUtil.hasExamItem(examname, key)) {
					String itempre = "t" + count;
					count++;
					froms += ", exam_items " + itempre;
					Map sqls = getSql(examname, itempre, key, value);
					where += (String) sqls.get(WHERES);
					sqlparams.addAll((List) sqls.get(PARAMS));
					sqlparamtypes.addAll((List<Type>) sqls.get(TYPES));
				}
			}
		}
		// 添加scalar
		Map<String, NullableType> scalarmap = new HashMap();
		if (collist != null && !collist.isEmpty()) {
			int colnum = 0;
			for (String key : collist) {

				if (basemap.containsKey(key)) {
					select += "," + basemap.get(key).get(COLUMN) + " 'col" + colnum + "'";
				} else if (commonExamUtil.hasExamItem(examname, key)) {
					String itempre = "t" + count;
					froms += ", exam_items " + itempre;
					count++;
					select += "," + itempre + ".value 'col" + colnum + "'";
				}
				scalarmap.put("col" + colnum, hbtTypeMap.get(basemap.get(key).get(COLTYPE)));
				colnum++;
			}
			select = "select " + select.substring(1);
			sql = select + froms + where + orderby;
		} else {
			sql += froms + where + orderby;
		}
		// 计算总数,页数
		countsql += froms + where;
		SQLQuery countquery = this.getSession().createSQLQuery(countsql);

		if (sqlparamtypes.size() > 0) {
			NullableType[] types = new NullableType[sqlparamtypes.size()];
			types = sqlparamtypes.toArray(types);
			countquery.setParameters(sqlparams.toArray(), types);
		}
		List countlist = countquery.list();
		int allcount = 0;
		if (countlist.size() > 0) {
			allcount = (Integer) countlist.get(0);
		}
		Map pageparams = params.get(PAGE);
		int pagesize = Integer.parseInt((String) pageparams.get(PAGESIZE));
		int currentpage = Integer.parseInt((String) pageparams.get(CURRENTPAGE));
		if (pagesize <= 0) {
			pagesize = DEFAULTPAGESIZE;
		}

		int pages = allcount / pagesize + (allcount % pagesize > 0 ? 1 : 0);
		if (currentpage > pages) {
			currentpage = pages;
		}
		if (currentpage <= 0) {
			currentpage = 1;
		}
		int min = pagesize * (currentpage - 1);
		int max = pagesize * currentpage;
		// 查询分页结果
		SQLQuery query = this.getSession().createSQLQuery(sql);
		if (sqlparamtypes.size() > 0) {
			NullableType[] types = new NullableType[sqlparamtypes.size()];
			types = sqlparamtypes.toArray(types);
			query.setParameters(sqlparams.toArray(), types);
		}
		if (scalarmap.size() > 0) {
			for (Iterator it = scalarmap.keySet().iterator(); it.hasNext();) {
				String key = (String) it.next();
				NullableType type = (NullableType) scalarmap.get(key);
				query.addScalar(key, type);
			}
		}
		query.setFirstResult(min);
		query.setMaxResults(max);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map ret = new HashMap();
		List retlist = query.list();
		ret.put("rows", retlist);
		ret.put("currentpage", currentpage);
		ret.put("total", allcount);
		ret.put("pages", pages);
		return ret;
	}

	public Map get_query_info(String examname) throws Exception {

		return null;
	}

	private Map getBaseSql(String name, Map<String, String> value, Class type) throws Exception {
		System.out.println("====getBaseSql==");
		System.out.println("==name====" + name);
		Map<String, Object> ret = new HashMap();
		List<NullableType> types = new ArrayList();
		int idx = value.get(VALUE).indexOf("-");
		int idx1 = value.get(VALUE).indexOf(",");
		System.out.println("===idx==="+idx);
		System.out.println("===idx1===" + idx1);
		if (idx > 0) {
			String[] values = value.get(VALUE).split("-");
			ret.put(WHERES, " and " + name + " >=? and " + name + " <=? ");
			if (type.isAssignableFrom(java.util.Date.class)  || type.isAssignableFrom(java.sql.Date.class)) {
				List<Date> dates = new ArrayList();
				dates.add(shortdateformat.parse(values[0].trim()));
				dates.add(longdateformat.parse(values[1].trim() + DATE_DAYMAX));
				types.add(Hibernate.TIMESTAMP);
				types.add(Hibernate.TIMESTAMP);
				ret.put(PARAMS, dates);
			} else if (type.isAssignableFrom(java.lang.Number.class)) {
				List<BigDecimal> numbers = new ArrayList();
				numbers.add(new BigDecimal(values[0].trim()));
				numbers.add(new BigDecimal(values[1].trim()));
				types.add(Hibernate.BIG_DECIMAL);
				types.add(Hibernate.BIG_DECIMAL);
				ret.put(PARAMS, numbers);
			} else {
				List<String> strs = new ArrayList();
				strs.add(values[0].trim().trim());
				strs.add(values[1].trim().trim());
				types.add(Hibernate.STRING);
				types.add(Hibernate.STRING);
				ret.put(PARAMS, strs);
			}
		} else if (idx1 > 0) {
			String[] values = value.get(VALUE).split(",");
			String tmp = " and " + name + " in (";
			List hbtparams = new ArrayList();
			for (int i = 0; i < values.length; i++) {
				tmp += "?,";
				if (type.isAssignableFrom(java.util.Date.class) || type.isAssignableFrom(java.sql.Date.class)) {
					hbtparams.add(shortdateformat.parse(values[i]));
					types.add(Hibernate.TIMESTAMP);
				} else if (type.isAssignableFrom(java.lang.Number.class)) {
					hbtparams.add(new BigDecimal(values[i]));
					types.add(Hibernate.BIG_DECIMAL);
				} else {
					hbtparams.add(values[i].trim());
					types.add(Hibernate.STRING);
				}
			}
			ret.put(PARAMS, hbtparams);
			tmp = tmp.substring(0, tmp.length() - 1) + ")";
			ret.put(WHERES, tmp);
		} else {
			System.out.println("====else==");
			String opt = value.get("opt");
			if (type.isAssignableFrom(java.util.Date.class) || type.isAssignableFrom(java.sql.Date.class)) {
				List<Date> dates = new ArrayList();
				dates.add(shortdateformat.parse(value.get(VALUE).trim()));
				types.add(Hibernate.TIMESTAMP);
				ret.put(PARAMS, dates);
			} else if (type.isAssignableFrom(java.lang.Number.class)) {
				List<BigDecimal> numbers = new ArrayList();
				numbers.add(new BigDecimal(value.get(VALUE).trim()));
				ret.put(PARAMS, numbers);
				ret.put(WHERES, " and " + name + " " + opt + " ?  ");
				types.add(Hibernate.BIG_DECIMAL);
			} else {
				List<String> params = new ArrayList();
				if (SQL_OPT_LIKE.equals(opt)) {
					opt = SQL_OPT_LIKE;
					params.add("%" + value.get(VALUE).trim() + "%");
				} else if (SQL_OPT_LEFTLIKE.equals(opt)) {
					opt = SQL_OPT_LIKE;
					params.add(value.get(VALUE).trim() + "%");
				} else if (SQL_OPT_RIGHTLIKE.equals(opt)) {
					opt = SQL_OPT_LIKE;
					params.add("%" + value.get(VALUE).trim());
				} else {
					params.add(value.get(VALUE).trim());
				}
				types.add(Hibernate.STRING);
				ret.put(PARAMS, params);
				ret.put(WHERES, " and " + name + " " + opt + "?  ");
			}
		}
		ret.put(TYPES, types);
		return ret;
	}

	private Map getBaseSql(Map cfg, Map<String, String> value, Class type) throws Exception {
		Map<String, Object> ret = new HashMap();
		List<NullableType> types = new ArrayList();
		int idx = value.get(VALUE).indexOf("-");
		int idx1 = value.get(VALUE).indexOf(",");
		String name = (String) cfg.get(COLUMN);

		Object typeobj = type.newInstance();
		if (idx > 0) {
			String[] values = value.get(VALUE).split("-");
			ret.put(WHERES, " and " + cfg.get(COLUMN) + " >=? and " + cfg.get(COLUMN) + " <=? ");
			if (typeobj instanceof java.util.Date) {
				List<Date> dates = new ArrayList();
				dates.add(shortdateformat.parse(values[0].trim()));
				dates.add(longdateformat.parse(values[1].trim() + DATE_DAYMAX));
				types.add(Hibernate.TIMESTAMP);
				types.add(Hibernate.TIMESTAMP);
				ret.put(PARAMS, dates);
			} else if (typeobj instanceof java.lang.Number) {
				List<BigDecimal> numbers = new ArrayList();
				numbers.add(new BigDecimal(values[0].trim()));
				numbers.add(new BigDecimal(values[1].trim()));
				types.add(Hibernate.BIG_DECIMAL);
				types.add(Hibernate.BIG_DECIMAL);
				ret.put(PARAMS, numbers);
			} else {
				List<String> strs = new ArrayList();
				if (cfg.get("encflag").equals("true")) {
					strs.add(EncryptionUtils.encry(values[0].trim().trim()));
					strs.add(EncryptionUtils.encry(values[1].trim().trim()));
				} else {
					strs.add(values[0].trim().trim());
					strs.add(values[1].trim().trim());
				}
				types.add(Hibernate.STRING);
				types.add(Hibernate.STRING);
				ret.put(PARAMS, strs);
			}
		} else if (idx1 > 0) {
			String[] values = value.get(VALUE).split(",");
			String tmp = " and " + cfg.get(COLUMN) + " in (";
			List hbtparams = new ArrayList();
			for (int i = 0; i < values.length; i++) {
				tmp += "?,";
				if (typeobj instanceof java.util.Date) {
					hbtparams.add(shortdateformat.parse(values[i]));
					types.add(Hibernate.TIMESTAMP);
				} else if (typeobj instanceof java.lang.Number) {
					hbtparams.add(new BigDecimal(values[i]));
					types.add(Hibernate.BIG_DECIMAL);
				} else {
					if (cfg.get("encflag").equals("true")) {
						hbtparams.add(EncryptionUtils.encry(values[i].trim()));
					} else {
						hbtparams.add(values[i].trim());
					}
					types.add(Hibernate.STRING);
				}
			}
			ret.put(PARAMS, hbtparams);
			tmp = tmp.substring(0, tmp.length() - 1) + ")";
			ret.put(WHERES, tmp);
		} else {
			String opt = value.get("opt");
			if (typeobj instanceof java.lang.Number) {
				List<BigDecimal> numbers = new ArrayList();
				numbers.add(new BigDecimal(value.get(VALUE).trim()));
				ret.put(PARAMS, numbers);
				ret.put(WHERES, " and " + cfg.get(COLUMN) + " " + opt + "?  ");
				types.add(Hibernate.BIG_DECIMAL);
			} else {
				List<String> params = new ArrayList();
				String valuestr = (String) value.get(VALUE).trim();
				if (cfg.get("encflag").equals("true")) {
					valuestr = EncryptionUtils.encry(valuestr);
				}
				if (SQL_OPT_LIKE.equals(opt)) {
					opt = SQL_OPT_LIKE;
					params.add("%" + valuestr + "%");
				} else if (SQL_OPT_LEFTLIKE.equals(opt)) {
					opt = SQL_OPT_LIKE;
					params.add(valuestr + "%");
				} else if (SQL_OPT_RIGHTLIKE.equals(opt)) {
					opt = SQL_OPT_LIKE;
					params.add("%" + valuestr);
				} else {
					params.add(valuestr);
				}
				System.out.println("====valuestr==" + valuestr);
				types.add(Hibernate.STRING);
				ret.put(PARAMS, params);
				ret.put(WHERES, " and " + cfg.get(COLUMN) + " " + opt + "?  ");
			}
		}
		ret.put(TYPES, types);
		return ret;
	}

	private Map getItemWhere(String name, Map<String, String> value, String pre,String encflag) throws Exception {
		Map<String, Object> ret = new HashMap();
		List<NullableType> types = new ArrayList();
		int idx = value.get(VALUE).indexOf("-");
		int idx1 = value.get(VALUE).indexOf(",");
		if (idx > 0) {
			String[] values = value.get(VALUE).split("-");
			ret.put(WHERES, " and " + pre + ".value >=? and value <=? ");
			List<String> strs = new ArrayList();
			if("true".equals(encflag)){
				strs.add(EncryptionUtils.encry(values[0].trim()));
				strs.add(EncryptionUtils.encry(values[1].trim()));
			}else{
				strs.add(values[0].trim());
				strs.add(values[1].trim());
			}
			types.add(Hibernate.STRING);
			types.add(Hibernate.STRING);
			ret.put(PARAMS, strs);
		} else if (idx1 > 0) {
			String[] values = value.get(VALUE).split(",");
			String tmp = " and " + pre + ".value in (";
			List hbtparams = new ArrayList();
			for (int i = 0; i < values.length; i++) {
				tmp += "?,";
				if("true".equals(encflag)){
					hbtparams.add(EncryptionUtils.encry(values[i].trim()));
				}else{
					hbtparams.add(values[i].trim());
				}
				types.add(Hibernate.STRING);
			}
			ret.put(PARAMS, hbtparams);
			tmp = tmp.substring(0, tmp.length() - 1) + ")";
			ret.put(WHERES, tmp);
		} else {
			String opt = value.get("opt");
			List<String> params = new ArrayList();
			if (SQL_OPT_LIKE.equals(opt)) {
				opt = SQL_OPT_LIKE;
				if("true".equals(encflag)){
					params.add("%" + EncryptionUtils.encry(value.get(VALUE).trim()) + "%");
				}else{
					params.add("%" + value.get(VALUE).trim() + "%");
				}
			} else if (SQL_OPT_LEFTLIKE.equals(opt)) {
				opt = SQL_OPT_LIKE;
				if("true".equals(encflag)){
					params.add(EncryptionUtils.encry(value.get(VALUE).trim()) + "%");
				}else{
					params.add(value.get(VALUE).trim() + "%");
				}
			} else if (SQL_OPT_RIGHTLIKE.equals(opt)) {
				opt = SQL_OPT_LIKE;
				if("true".equals(encflag)){
					params.add("%" + EncryptionUtils.encry(value.get(VALUE).trim()) );
				}else{
					params.add("%" + value.get(VALUE).trim());
				}
			} else {
				if("true".equals(encflag)){
					params.add( EncryptionUtils.encry(value.get(VALUE).trim() ));
				}else{
					params.add(value.get(VALUE).trim());
				}
			}
			types.add(Hibernate.STRING);
			ret.put(PARAMS, params);
			ret.put(WHERES, " and " + pre + ".value " + opt + "?  ");
		}
		ret.put(TYPES, types);
		return ret;
	}

	private Map getSql(String examname, String pre, String name, Map<String, String> value) throws Exception {
		Map<String, Object> ret = new HashMap();
		List<NullableType> types = new ArrayList();
		int idx = value.get(VALUE).indexOf("-");
		int idx1 = value.get(VALUE).indexOf(",");
		String opt = value.get("opt");
		String where = " and info.id= " + pre + ".id and " + pre + ".item='" + name + "' and ";
		String type = commonExamUtil.getExamItemCfg(examname, name).getValuetype();
		if (idx > 0) {
			String[] values = value.get(VALUE).split("-");
			if ("number".equals(type)) {
				List<BigDecimal> numbers = new ArrayList();
				numbers.add(new BigDecimal(values[0].trim()));
				numbers.add(new BigDecimal(values[1].trim()));
				types.add(Hibernate.BIG_DECIMAL);
				types.add(Hibernate.BIG_DECIMAL);
				ret.put(PARAMS, numbers);
				ret.put(WHERES, where + pre + ".numbervalue >=? and " + pre + ".numbervalue <=? ");
			} else if ("date".equals(type)) {
				List<String> strs = new ArrayList();
				strs.add(values[0].trim() + DATE_DAYMIN);
				strs.add(values[1].trim() + DATE_DAYMAX);
				types.add(Hibernate.TIMESTAMP);
				types.add(Hibernate.TIMESTAMP);
				ret.put(PARAMS, strs);
				ret.put(WHERES, where + pre + ".value >=? and " + pre + ".value <=? ");
			} else {
				List<String> strs = new ArrayList();
				strs.add(values[0].trim());
				strs.add(values[1].trim());
				types.add(Hibernate.STRING);
				types.add(Hibernate.STRING);
				ret.put(PARAMS, strs);
				ret.put(WHERES, where + pre + ".value >=? and " + pre + ".value <=? ");
			}
		} else if (idx1 > 0) {
			String[] values = value.get(VALUE).split(",");
			String tmp = " and " + pre + ".value in (";
			List hbtparams = new ArrayList();
			for (int i = 0; i < values.length; i++) {
				tmp += "?,";
				if ("number".equals(type)) {
					hbtparams.add(new BigDecimal(values[i]));
					types.add(Hibernate.BIG_DECIMAL);
				} else if ("date".equals(type)) {
					hbtparams.add(values[i].trim() + DATE_DAYMIN);
					types.add(Hibernate.TIMESTAMP);
				} else {
					hbtparams.add(values[i].trim());
					types.add(Hibernate.STRING);
				}
			}
			ret.put(PARAMS, hbtparams);
			tmp = tmp.substring(0, tmp.length() - 1) + ")";
			ret.put(WHERES, tmp);
		} else {
			if ("number".equals(type)) {
				List<BigDecimal> numbers = new ArrayList();
				numbers.add(new BigDecimal(value.get(VALUE).trim()));
				ret.put(PARAMS, numbers);
				types.add(Hibernate.BIG_DECIMAL);
				ret.put(WHERES, where + pre + ".numbervalue " + opt + " ? ");
			} else {
				List<String> params = new ArrayList();
				if (SQL_OPT_LIKE.equals(opt)) {
					opt = SQL_OPT_LIKE;
					params.add("%" + value.get(VALUE).trim() + "%");
					ret.put(WHERES, where + pre + ".value " + opt + "? ");
				} else if (SQL_OPT_LEFTLIKE.equals(opt)) {
					opt = SQL_OPT_LIKE;
					params.add(value.get(VALUE).trim() + "%");
					ret.put(WHERES, where + pre + ".value " + opt + "? ");
				} else if (SQL_OPT_RIGHTLIKE.equals(opt)) {
					opt = SQL_OPT_LIKE;
					params.add("%" + value.get(VALUE).trim());
					ret.put(WHERES, where + pre + ".value " + opt + "? ");
				} else {
					if ("date".equals(type)) {
						// 这里处理日期类型的等于 ,日期不接受时分秒参数
						ret.put(WHERES, where + pre + ".value " + opt + "? ");
						params.add(value.get(VALUE).trim() + DATE_DAYMIN);
					} else {
						ret.put(WHERES, where + pre + ".value " + opt + "? ");
						params.add(value.get(VALUE).trim());
					}
				}
				types.add(Hibernate.STRING);
				ret.put(PARAMS, params);
			}
		}
		ret.put(TYPES, types);
		return ret;
	}

	public CommonVO saveExam(CommonVO savedata) throws Exception {
		// 完成baseinfo属性拷贝
		ExamBaseinfo base = savedata.getBase();
		if (StringUtils.isEmpty(base.getId())) {
			base.setId(fileNoGen.getNextExamId());
		} else {
			ExamBaseinfo old = (ExamBaseinfo) getHibernateTemplate().get(ExamBaseinfo.class, base.getId());
			getHibernateTemplate().evict(old);
			if (!old.getInputpersonid().equals(SecurityManager.currentOperator().getUsername())) {
				throw new Exception("只允许" + old.getInputpersonid() + "进行修改!");
			}
		}
		getHibernateTemplate().saveOrUpdate(savedata.getBase());
		// 处理items
		List<Map> items = (List<Map>) savedata.getItems();
		for (int i = 0; i < items.size(); i++) {
			Map vo = items.get(i);
			for (Iterator it = vo.keySet().iterator(); it.hasNext();) {
				String key = (String) it.next();
				Object value = vo.get(key);
				ExamItems item = new ExamItems();
				ExamItemsId id = new ExamItemsId();
				id.setId(base.getId());
				id.setIdx(i);
				id.setItem(key);
				item.setId(id);
				// 配置表有的字段才保存
				if (commonExamUtil.hasExamItem(base.getExamname(), key)) {
					if (value != null) {
						item.setValue(makstr(base.getExamname(), key, value));
					} else {
						item.setValue(null);
					}
					getHibernateTemplate().saveOrUpdate(item);
				} else {
					System.out.println(key + "==" + value);
				}
			}
		}
		return savedata;
	}
	
	public List getCrudCols(String crudname) throws Exception{
		List ret = new ArrayList();
		ExamCrud crud = (ExamCrud)this.getSession().load(ExamCrud.class, crudname);
		Connection conn = getSession().connection();
		String sql = "select "+crud.getColums()+" from "+crud.getTablename() +" where 1=2 ";
		System.out.println("==sql===="+sql);
		PreparedStatement stmt =  conn.prepareStatement(sql);
//		stmt.setString(1, disid+"%");
		ResultSet rs = stmt.executeQuery();
		ResultSetMetaData rsMetaData = rs.getMetaData();
		int numberOfColumns = rsMetaData.getColumnCount();
		for (int i = 1; i <= numberOfColumns; i++) {
			System.out.println("======"+rsMetaData.getColumnLabel(i));
			Map colmap = new HashMap();
			colmap.put("name", rsMetaData.getColumnLabel(i));
			colmap.put("nullable", rsMetaData.isNullable(i));
			colmap.put("editable", true);
			Class cls = Class.forName(rsMetaData.getColumnClassName(i));
			String type = "string";
			if(cls.isAssignableFrom(java.util.Date.class) || cls.isAssignableFrom(java.sql.Date.class)){
				type = "date";
			}
			colmap.put("type", type);
			if(crud.getPk().trim().equals(rsMetaData.getColumnLabel(i))){
				if(!crud.getPkeditable()){
					colmap.put("editable", false);
				}
			}
			ret.add(colmap);
//			Class cls = Class.forName(rsMetaData.getColumnClassName(i));
		}
		return ret;
	}
	
	public Map loadCrud(String id,String crudname) throws Exception{
		List ret = new ArrayList();
		ExamCrud crud = (ExamCrud)this.getSession().load(ExamCrud.class, crudname);
		Connection conn = getSession().connection();
		String sql = "select "+crud.getColums()+" from "+crud.getTablename() +" where "+ crud.getPk()+" = ? ";
		System.out.println("==sql===="+sql);
		PreparedStatement stmt =  conn.prepareStatement(sql);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		if("date".equals(crud.getPktype())){
			stmt.setDate(1, new java.sql.Date(format.parse(id).getTime()));
		}else if("int".equals(crud.getPktype())){
			stmt.setInt(1, Integer.parseInt(id));
		}else{
			stmt.setObject(1, id);
		}
		ResultSet rs = stmt.executeQuery();
		ResultSetMetaData rsMetaData = rs.getMetaData();
		int numberOfColumns = rsMetaData.getColumnCount();
		Map colmap = new HashMap();
		if (rs.next()) {
			for (int i = 1; i <= numberOfColumns; i++) {
				Class cls = Class.forName(rsMetaData.getColumnClassName(i));
				if (cls.isAssignableFrom(String.class)) {
					colmap.put(rsMetaData.getColumnLabel(i), rs.getString(i));
				} else if (cls.isAssignableFrom(Number.class)) {
					colmap.put(rsMetaData.getColumnLabel(i), rs.getFloat(i));
				} else if (cls.isAssignableFrom(Date.class) || cls.isAssignableFrom(java.sql.Timestamp.class)) {
					Date obj = rs.getDate(i);
					colmap.put(rsMetaData.getColumnLabel(i), format.format(obj));
				} else  {
					Object obj = rs.getObject(i);
					colmap.put(rsMetaData.getColumnLabel(i), obj);
				}
			}
		}
		return colmap;
	}
	
	public Map saveCrud(Map param,String crudname) throws Exception,RuntimeException{
		System.out.println("====saveCrudsaveCrudsaveCrudsaveCrud==");
		List ret = new ArrayList();
		ExamCrud crud = (ExamCrud)this.getSession().load(ExamCrud.class, crudname);
		Connection conn = getSession().connection();
		String sql = "select "+crud.getColums()+" from "+crud.getTablename() +" where 1=2 ";
		Map<String,String > colsmap = getColumns(crud.getColums());
		
		String pk = colsmap.get(crud.getPk().trim());
		String pkvalue = (String)param.get(pk);
		if(pkvalue!=null && pkvalue.trim().length()>0){
			PreparedStatement stmt =  conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int numberOfColumns = rsMetaData.getColumnCount();
			Map colmap = new HashMap();
			String updatesql = " update "+crud.getTablename() +" set " ;
			String setstr = "";
			String where = " where "+pk+" = ";
			boolean identityflag = false;
			for (int i = 1; i <= numberOfColumns; i++) {
				Class cls = Class.forName(rsMetaData.getColumnClassName(i));
				if(pk.equals(rsMetaData.getColumnLabel(i))){
					if (cls.isAssignableFrom(String.class)|| cls.isAssignableFrom(Date.class) || cls.isAssignableFrom(java.sql.Timestamp.class)) {
						where+= "'"+param.get(pk)+"'";
					} else if (cls.isAssignableFrom(Number.class)) {
						where+= ""+param.get(pk)+"";
					} else  {
						where+= "'"+param.get(pk)+"'";
					}
				}else{
					if (cls.isAssignableFrom(String.class)|| cls.isAssignableFrom(Date.class) || cls.isAssignableFrom(java.sql.Timestamp.class)) {
						setstr+= ","+colsmap.get(rsMetaData.getColumnLabel(i))+"='"+param.get(rsMetaData.getColumnLabel(i))+"'";
					} else if (cls.isAssignableFrom(Number.class)) {
						setstr+= ","+colsmap.get(rsMetaData.getColumnLabel(i))+"="+param.get(rsMetaData.getColumnLabel(i))+"";
					} else  {
						setstr+= ","+colsmap.get(rsMetaData.getColumnLabel(i))+"='"+param.get(rsMetaData.getColumnLabel(i))+"'";
					}
				}
			}
			setstr = " "+setstr.substring(1)+" ";
			updatesql += setstr + where;
			getSession().createSQLQuery(updatesql).executeUpdate();
		}else{
			PreparedStatement stmt =  conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int numberOfColumns = rsMetaData.getColumnCount();
			Map colmap = new HashMap();
			String insertsql = " insert into "+crud.getTablename() ;
			String cols = "";
			String values ="";
			boolean identityflag = false;
			for (int i = 1; i <= numberOfColumns; i++) {
				if(pk.equals(rsMetaData.getColumnLabel(i))){
					if(!crud.getPkeditable()){
						identityflag = true;
						continue;
					}
				}
				cols+= ","+colsmap.get(rsMetaData.getColumnLabel(i));
				Class cls = Class.forName(rsMetaData.getColumnClassName(i));
				if (cls.isAssignableFrom(String.class)) {
					values+= ",'"+param.get(rsMetaData.getColumnLabel(i))+"'";
				} else if (cls.isAssignableFrom(Number.class)) {
					values+= ","+param.get(rsMetaData.getColumnLabel(i))+"";
				} else if (cls.isAssignableFrom(Date.class) || cls.isAssignableFrom(java.sql.Timestamp.class)) {
					values+= ",'"+param.get(rsMetaData.getColumnLabel(i))+"'";
				} else  {
					Object obj = rs.getObject(i);
					colmap.put(rsMetaData.getColumnLabel(i), obj);
				}
			}
			cols = "("+cols.substring(1)+")";
			values = " values("+values.substring(1)+")";
			insertsql +=cols+values;
			getSession().createSQLQuery(insertsql).executeUpdate();
			if(identityflag){
				List<Long> identity = getSession().createSQLQuery(" select @@IDENTITY").list();
				param.put( crud.getPk(),""+identity.get(0));
			}
		}
		return param;
	}
	
	public String deleteCrud(String id,String crudname) throws Exception,RuntimeException{
		System.out.println("====saveCrudsaveCrudsaveCrudsaveCrud==");
		List ret = new ArrayList();
		ExamCrud crud = (ExamCrud)this.getSession().load(ExamCrud.class, crudname);
		Connection conn = getSession().connection();
		String sql = "select "+crud.getColums()+" from "+crud.getTablename() +" where 1=2 ";
		Map<String,String > colsmap = getColumns(crud.getColums());
		
		String pk = colsmap.get(crud.getPk().trim());
		String pkvalue = (String)id;
		PreparedStatement stmt =  conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		ResultSetMetaData rsMetaData = rs.getMetaData();
		int numberOfColumns = rsMetaData.getColumnCount();
		Map colmap = new HashMap();
		String deletesql = " delete "+crud.getTablename() +"" ;
		String where = " where "+pk+" = ";
		boolean identityflag = false;
		for (int i = 1; i <= numberOfColumns; i++) {
			Class cls = Class.forName(rsMetaData.getColumnClassName(i));
			if(pk.equals(rsMetaData.getColumnLabel(i))){
				if (cls.isAssignableFrom(String.class)|| cls.isAssignableFrom(Date.class) || cls.isAssignableFrom(java.sql.Timestamp.class)) {
					where+= "'"+pkvalue+"'";
				} else if (cls.isAssignableFrom(Number.class)) {
					where+= ""+pkvalue+"";
				} else  {
					where+= "'"+pkvalue+"'";
				}
				break;
			}
		}
		deletesql +=  where;
		getSession().createSQLQuery(deletesql).executeUpdate();
		return "删除成功!";
	}
	
	
	private Map<String,String> getColumns(String cols) throws Exception{
		Map<String,String> ret = new HashMap<String,String>();
		String[] col = cols.split(",");
		for(int i = 0 ; i<col.length;i++){
			String str = col[i].trim().replaceAll("\\s+", " ");
			String[] names = str.split(" ");
			if(names.length==1){
				ret.put(names[0].replaceAll("\"", "").replaceAll("'", ""), names[0]);
			}else if(names.length ==2){
				ret.put(names[1].replaceAll("\"", "").replaceAll("'", ""), names[0]);
			}else if(names.length ==3 && names[1].toUpperCase().equals("AS")){
				ret.put(names[2].replaceAll("\"", "").replaceAll("'", ""), names[0]);
			}else{
				throw new Exception("请与系统管理员联系!配置错误!");
			}
		}
		return ret;
	}

	public String makstr(String examname, String itemname, Object value) {
		ExamItemcfg cfg = commonExamUtil.getExamItemCfg(examname, itemname);
		String type = VALUE_TYPE_STRING;
		if (cfg != null) {
			type = cfg.getValuetype();
		}
		// 时间类型进行处理后保存
		// if(VALUE_TYPE_DATE.equals(type)){
		// System.out.println("===========value========"+value);
		// Long date = Long.parseLong((String)value);
		// return dateformat.format(date);
		// }else{
		return (String) value;
		// }
	}

	// TODO 返回查询的设置
	public List get_query_list(String examname) {
		Query query = this.getSession().createQuery("from ExamQueryCfg where examname = ? order by ord");
		query.setParameter(0, examname);
		List ret = query.list();
		return ret;
	}
	
	
	
	// TODO 返回配置
	public Map<String,String> get_cfg_list(String examname) {
		Query query = this.getSession().createQuery("from ExamExamcfg where examname = ? ");
		query.setParameter(0, examname);
		List<ExamExamcfg> result = query.list();
		Map<String,String> ret = new HashMap<String,String>();
		for(int i = 0 ; i <result.size();i++){
			ExamExamcfg cfg = result.get(i);
			ret.put(cfg.getItemname(), cfg.getDefaultvalue());
		}
		return ret;
	}
	
	public Map<String,String> get_filequerytype(String examname) {
		Query query = this.getSession().createQuery("from ExamExamcfgFileno where examname = ? ");
		query.setParameter(0, examname);
		List<ExamExamcfgFileno> result = query.list();
		Map<String,String> ret = new HashMap<String,String>();
		for(int i = 0 ; i <result.size();i++){
			ExamExamcfgFileno cfg = result.get(i);
			ret.put(cfg.getItemname(), cfg.getQuerytype());
		}
		return ret;
	}
	
	private Class getTypeClass(String type){
		Map<String ,Class> typemap = new HashMap();
		typemap.put("string",  String.class);
		typemap.put("date",  java.sql.Timestamp.class);
		typemap.put("int",  Long.class);
		return typemap.get(type);
	}
	
	public Map crudList(String examname, Map<String, Map> params) throws Exception {
		// params.getBase();
		List sqlparams = new ArrayList();
		List<Type> sqlparamtypes = new ArrayList();
		int count = 0;
		Map preMap = new HashMap();
		Map itemsMap = new HashMap();
		List<ExamQueryCfg> collist = this.get_query_list(examname);
		ExamQuerySql sqlcfg = (ExamQuerySql)this.getSession().get(ExamQuerySql.class, examname);
		String where = " where 1=1 ";
		String select = "";
		String sql = "";
		Map<String, NullableType> scalarmap = new HashMap();
		Map<String, ExamQueryCfg> colmap = new HashMap();
		int colnum = 0;
		for (ExamQueryCfg key : collist) {
			select += "," + key.getCol() + " 'col" + colnum + "'";
			scalarmap.put("col" + colnum, hbtTypeMap.get(key.getColtype()));
			colmap.put(key.getName(), key);
			colnum++;
		}
		select = "select " + select.substring(1);
		where += " and "+sqlcfg.getWherestr();
		for (Iterator it = params.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			Map<String, String> value = (Map) params.get(key);
			if (value != null) {
				if(colmap.containsKey(key)){
					Map sqls = getBaseSql(colmap.get(key).getCol(), value, getTypeClass(colmap.get(key).getColtype()));
					where += (String) sqls.get(WHERES);
					sqlparams.addAll((List) sqls.get(PARAMS));
					sqlparamtypes.addAll((List) sqls.get(TYPES));
				}
			}
		}
		
		String orderby = "";
		if(sqlcfg.getOrderby()!=null && sqlcfg.getOrderby().trim().length()>0){
			orderby = " order by "+sqlcfg.getOrderby();
		}
		sql = select  +" from "+ sqlcfg.getSql() + where + orderby;
		// 计算总数,页数
		String countsql = " select count(*) "+ " from "+ sqlcfg.getSql()  + where;
		SQLQuery countquery = this.getSession().createSQLQuery(countsql);
		if (sqlparamtypes.size() > 0) {
			NullableType[] types = new NullableType[sqlparamtypes.size()];
			types = sqlparamtypes.toArray(types);
			countquery.setParameters(sqlparams.toArray(), types);
		}
		List countlist = countquery.list();
		int allcount = 0;
		if (countlist.size() > 0) {
			allcount = (Integer) countlist.get(0);
		}
		Map pageparams = params.get(PAGE);
		int pagesize = Integer.parseInt((String) pageparams.get(PAGESIZE));
		int currentpage = Integer.parseInt((String) pageparams.get(CURRENTPAGE));
		if (pagesize <= 0) {
			pagesize = DEFAULTPAGESIZE;
		}

		int pages = allcount / pagesize + (allcount % pagesize > 0 ? 1 : 0);
		if (currentpage > pages) {
			currentpage = pages;
		}
		if (currentpage <= 0) {
			currentpage = 1;
		}
		int min = pagesize * (currentpage - 1);
		int max = pagesize * currentpage;
		// 查询分页结果
		SQLQuery query = this.getSession().createSQLQuery(sql);
		if (sqlparamtypes.size() > 0) {
			NullableType[] types = new NullableType[sqlparamtypes.size()];
			types = sqlparamtypes.toArray(types);
			query.setParameters(sqlparams.toArray(), types);
		}
		if (scalarmap.size() > 0) {
			for (Iterator it = scalarmap.keySet().iterator(); it.hasNext();) {
				String key = (String) it.next();
				NullableType type = (NullableType) scalarmap.get(key);
				query.addScalar(key, type);
			}
		}
		// query.setParameters(sqlparams.toArray(),
		// (Type[])sqlparamtypes.toArray());
		query.setFirstResult(min);
		query.setMaxResults(max);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map ret = new HashMap();
		List retlist = query.list();
		ret.put("rows", retlist);
		ret.put("currentpage", currentpage);
		ret.put("total", allcount);
		ret.put("pages", pages);
		return ret;
	}
	
	
	public List getOrgMap() throws Exception {
		// params.getBase();
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		SamTaxorgcode org = user.getOrg();
		Map rootnode = new HashMap();
		rootnode.put("id", org.getId());
		rootnode.put("text", org.getName());
		rootnode.put("attributes", org);
		List data = getOrgSub( org.getId());
		rootnode.put("leaf", (data == null || data.size() ==0) ?"true":"false");
		rootnode.put("children", data);
		rootnode.put("cls", (data == null || data.size() ==0)?"file":"folder");
		System.out.println("============"+org.getIsDetail());
		
		System.out.println("============" + rootnode);
		List retlist =new ArrayList();
		retlist.add(rootnode);
		return retlist;
	}
	
	private List getOrgSub(int orgid){
		List<SamTaxorgcode> orglist = getHibernateTemplate().find("from SamTaxorgcode  where parentId = " + orgid);
		if(orglist.size()<=0){
			return null;
		}else{
			List retlist = new ArrayList();
			for(int i = 0 ;  i<orglist.size();i++){
				SamTaxorgcode org = orglist.get(i);
				Map rootnode = new HashMap();
				rootnode.put("id", org.getId());
				rootnode.put("text", org.getName());
				rootnode.put("attributes", org);
				List data = getOrgSub( org.getId());
				rootnode.put("leaf", (data == null || data.size() ==0) ?"true":"false");
				rootnode.put("data", data);
				rootnode.put("cls", (data == null || data.size() ==0)?"file":"folder");
				retlist.add(rootnode);
			}
			return retlist;
		}
		
	}
	

	public Map getDistrictMap() {
		return commonExamUtil.getDistrictMap();
	}

	public CommonExamUtil getCommonExamUtil() {
		return commonExamUtil;
	}

	public void setCommonExamUtil(CommonExamUtil commonExamUtil) {
		this.commonExamUtil = commonExamUtil;
	}

	public FileNoGen getFileNoGen() {
		return fileNoGen;
	}

	public void setFileNoGen(FileNoGen fileNoGen) {
		this.fileNoGen = fileNoGen;
	}


	public String getCurrentUser() throws Exception {
		return SecurityManager.currentOperator().getTaxempname();
	}

	private List getSubDistrict(String distid) throws Exception {
		List ret = new ArrayList();
		Map distmap = commonExamUtil.getDistrictMap();
		List<District> sublist = commonExamUtil.getDistrict(distid);
		if(sublist != null) {
			for (District d : sublist) {
				Map item = getNodeFromDistrict(d);
				List child = getSubDistrict(d.getId());
				if (child == null || child.size() == 0) {
					item.put("haschild", false);
					ret.add(item);
				} else {
					item.put("haschild", true);
					item.put("children", child);
					ret.add(item);
				}
			}
			return ret;
		}else{
			return null;
		}
	}
	public List getCurrentDistrict() throws Exception {
		try {
			List ret = new ArrayList();
			String key = SecurityManager.currentOperator().getDistrictId();
			String id = key;
			if ("00".equals(key.substring(key.length() - 2))) {
				id = key.substring(0,key.length()-2);
			}
			String vlaue = (String) commonExamUtil.getDistrictMap().get(id);
			Map distmap = commonExamUtil.getDistrictMap();
			Map root = new HashMap();
			root.put("id", key);
			root.put("text", vlaue);
//            root.put("parent", "#");
			List child = getSubDistrict(key);
			if (child ==null || child.size() == 0) {
				root.put("haschild", false);
				ret.add(root);
			} else {
				root.put("haschild", true);
				root.put("children",child);
				ret.add(root);
			}
			return ret;
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
	private Map getNodeFromDistrict(District d)  throws Exception {
		Map ret = new HashMap();
		ret.put("id",d.getId());
		ret.put("text",d.getName());
//        ret.put("parent",d.getParentId());
		return ret;
	}

}
