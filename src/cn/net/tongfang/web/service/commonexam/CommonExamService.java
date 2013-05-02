package cn.net.tongfang.web.service.commonexam;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.NullableType;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.net.tongfang.framework.security.SecurityManager;
import cn.net.tongfang.framework.security.vo.District;
import cn.net.tongfang.framework.security.vo.ExamBaseinfo;
import cn.net.tongfang.framework.security.vo.ExamItemcfg;
import cn.net.tongfang.framework.security.vo.ExamItems;
import cn.net.tongfang.framework.security.vo.ExamItemsId;
import cn.net.tongfang.web.util.FileNoGen;

public class CommonExamService extends HibernateDaoSupport  {
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
	private static String DATE_DAYMAX = " 23:59:59";
	private static String DATE_DAYMIN= " 00:00:00";
	private static String PARAMS = "params";
	private static String WHERES = "wheres";
	private static String TYPES = "types";
	private static String VALUE="value";
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
	private static Map<String ,NullableType> hbtTypeMap = new HashMap();
	{
		hbtTypeMap.put("string", Hibernate.STRING);
		hbtTypeMap.put("date", Hibernate.TIMESTAMP);
		hbtTypeMap.put("number", Hibernate.BIG_DECIMAL);
	}
	public Map newExam(String examname)  throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    Date today = cal.getTime();
		Map ret = new HashMap();
		//将当前日期、当期操作员相关信息传入前台;让前台进行相关初始化工作
		ret.put("now", new Date());
		ret.put("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		ret.put("user", SecurityManager.currentOperator());
		return ret;
	}
	
	public List<District> getDistrict(String orgid) {
		if(orgid == null){
			orgid = "root"+SecurityManager.currentOperator().getDistrictId();
		}
		System.out.println("========orgid==========="+orgid);
		return commonExamUtil.getDistrict(orgid);
	}
	
	public List examList(String examname,String userdistrict,Map<String,Map> params,Map <String,Map<String,String>> basemap,List<String> collist) throws Exception{
		//这是默认的查询条件
		String countsql = " select count(*)";
		String sql = " select dbo.denc(info.fileno) '档案编号',dbo.denc(hf.name) '姓名' , pf.Birthday '出生日期' ,info.inputdate '录入日期' " +
				"from exam_baseinfo info , healthfile hf , PersonalInfo pf" ;
		String froms = " from exam_baseinfo info , healthfile hf , PersonalInfo pf ";
		String select = "";
		String where = " where info.fileno =hf.fileno and info.fileno = pf.fileno  and hf.DistrictNumber like '"+ userdistrict+"'%";
		//params.getBase();
		Field[] fields= ExamBaseinfo.class.getDeclaredFields();
		List sqlparams = new ArrayList();
		List<Type> sqlparamtypes = new ArrayList();
		Map<String , Field> fieldmap = new HashMap();
		for(int i= 0 ;i <fields.length;i++){
			fieldmap.put(fields[i].getName(), fields[i]);
		}
		int count = 0 ;
		Map preMap = new HashMap();
		for(Iterator it = params.keySet().iterator(); it.hasNext();){
			String key = (String)it.next();
			Map<String, String> value = (Map)params.get(key);
			if(value !=null){
				if(basemap.containsKey(key)){
					Map sqls = getBaseSql(basemap.get(key).get(COLUMN),value,fieldmap.get(key).getType());
					where += (String)sqls.get(WHERES);
					sqlparams.addAll((List)sqls.get(PARAMS));
					sqlparamtypes.addAll((List)sqls.get(TYPES));
				}else if(COMMONQUERY.equals(key)){
					//这里是通用查询
					String[] commonparams = value.get("value").split(PARAMSPLIT);
					for(int i = 0 ; i < commonparams.length;i++){
						String tmp = commonparams[i];
						while(tmp.indexOf(CONDITIONSPLIT+CONDITIONSPLIT)>=0){
							tmp = tmp.replaceAll(CONDITIONSPLIT+CONDITIONSPLIT, CONDITIONSPLIT);
						}
						String[] cols = commonparams[i].split(CONDITIONSPLIT);
						if(cols.length<3){
							throw new Exception("通用查询参数"+commonparams[i]+"格式不正确,正确格式为\"姓名 = 张三\"或\"出生日期 = 20120101-20120113\" 或 \"身份证号 = 身份证号1,身份证号2,身份证号3\"");
						}else{
							if(basemap.containsKey(cols[0]) && fieldmap.containsKey(basemap.get(cols[0]).get(COLUMN))){
								Map valuemap = new HashMap();
								valuemap.put(VALUE, cols[2]);
								Map sqls = getBaseSql(basemap.get(cols[0]).get(COLUMN),valuemap,fieldmap.get(basemap.get(cols[0]).get(COLUMN)).getType());
								where += (String)sqls.get(WHERES);
								sqlparams.addAll((List)sqls.get(PARAMS));
								sqlparamtypes.addAll((List)sqls.get(TYPES));
							}else{
								if(commonExamUtil.hasExamItem(examname, cols[0])){
									Map valuemap = new HashMap();
									valuemap.put(VALUE, cols[2]);
									String itempre = "t"+count;
									preMap.put(cols[0], count);
									count++;
									froms +=", exam_items "+itempre;
									Map sqls = getSql(examname,itempre,cols[0],valuemap);
									where += (String)sqls.get(WHERES);
									sqlparams.addAll((List)sqls.get(PARAMS));
									sqlparamtypes.addAll((List)sqls.get(TYPES));
								}
							}
						}
					}
				}else if(commonExamUtil.hasExamItem(examname, key)){
					String itempre = "t"+count;
					preMap.put(key, count);
					count++;
					froms +=", exam_items "+itempre;
					Map sqls = getSql(examname,itempre,key,value);
					where += (String)sqls.get(WHERES);
					sqlparams.addAll((List)sqls.get(PARAMS));
					sqlparamtypes.addAll((List)sqls.get(TYPES));
				}
			}
		}
		if(collist != null && !collist.isEmpty()){
			for(String key : collist){
				if(basemap.containsKey(key)){
					select +=basemap.get(key);
				}else if(commonExamUtil.hasExamItem(examname, key)){
					String itempre = "t"+count;
					froms +=", exam_items "+itempre;
					count++;
					select += ","+itempre+".value";
				}
			}
			select = "select "+select.substring(1);
			sql = select +froms+ where;
		}else{
			sql += froms+where;
		}
		//计算总数,页数
		countsql += froms+where;
		SQLQuery countquery = this.getSession().createSQLQuery(countsql);
		countquery.setParameters(sqlparams.toArray(), (Type[])sqlparamtypes.toArray());
		List countlist = countquery.list();
		int allcount = 0;
		if(countlist.size()>0){
			allcount = (Integer)countlist.get(0);
		}
		Map pageparams = params.get(PAGE);
		int pagesize = (Integer)pageparams.get(PAGESIZE);
		int currentpage = (Integer)pageparams.get(CURRENTPAGE);
		if(pagesize <=0){
			pagesize = DEFAULTPAGESIZE;
		}
		
		int pages = allcount/pagesize + (allcount%pagesize >0?1:0);
		if(currentpage>pages){
			currentpage = pages; 
		}
		if(currentpage <=0){
			currentpage = 1;
		}
		int min = pagesize*(currentpage-1);
		int max = pagesize*currentpage;
		//查询分页结果
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setParameters(sqlparams.toArray(), (Type[])sqlparamtypes.toArray());
		query.setFirstResult(min);
		query.setMaxResults(max);
		Map ret= new HashMap();
		ret.put("data", query.list());
		ret.put("currentpage", currentpage);
		ret.put("allcount", allcount);
		ret.put("pages", pages);
		return query.list();
	}
	
	public CommonVO loadExam(String id)  throws Exception{
		ExamBaseinfo base = (ExamBaseinfo)this.getHibernateTemplate().get(ExamBaseinfo.class, id);
		List<ExamItems> queryitems = this.getHibernateTemplate().find(" from ExamItems where id.id =?  ",id);
		List<Map> items = new ArrayList();
		for(ExamItems obj:queryitems){
			while((obj.getId().getIdx()+1) > items.size()){
				Map item = new HashMap();
				items.add(item);
			}
			items.get(obj.getId().getIdx()).put(obj.getId().getItem(), obj.getValue());
		}
		CommonVO ret = new CommonVO();
		ret.setBase(base);
		ret.setItems(items);
		//取出男女方的基础数据
		String sql = "select dbo.denc(hf.fileNo) as fileno, dbo.denc(hf.name) as name, p.sex, p.birthday,(year(getDate()) - year(p.birthday)) as age," +
        			" dbo.denc(p.idnumber) as idcard,hf.barCode,hf.address ,hf.tel as linkmanTel,hf.township,hf.village,p.workUnit,p.folk,p.folkOther," +
        			" p.education,p.occupation,p.idnumber,hf.residenceAddress,hf.districtNumber" +
        			" from HealthFile hf, PersonalInfo  p where  p.fileNo = hf.fileNo " +
        			" and hf.fileNo = ? and hf.status = 0 " ; 
		SQLQuery womanquery = this.getSession().createSQLQuery(sql);
		womanquery.setParameters(new Object[]{base.getFileno()}, new Type[]{Hibernate.STRING});
		womanquery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		womanquery.addScalar("fileno",Hibernate.STRING);
		womanquery.addScalar("name",Hibernate.STRING);
		womanquery.addScalar("sex",Hibernate.STRING);
		womanquery.addScalar("birthday",Hibernate.TIMESTAMP);
		womanquery.addScalar("age",Hibernate.INTEGER);
		womanquery.addScalar("idcard",Hibernate.STRING);
		womanquery.addScalar("barCode",Hibernate.STRING);
		womanquery.addScalar("address",Hibernate.STRING);
		womanquery.addScalar("linkmanTel",Hibernate.STRING);
		womanquery.addScalar("township",Hibernate.STRING);
		womanquery.addScalar("village",Hibernate.STRING);
		womanquery.addScalar("workUnit",Hibernate.STRING);
		womanquery.addScalar("folk",Hibernate.STRING);
		womanquery.addScalar("folkOther",Hibernate.STRING);
		womanquery.addScalar("education",Hibernate.STRING);
		womanquery.addScalar("occupation",Hibernate.STRING);
		womanquery.addScalar("idnumber",Hibernate.STRING);
		womanquery.addScalar("residenceAddress",Hibernate.STRING);
		womanquery.addScalar("districtNumber",Hibernate.STRING);
		List<Map> womanlist= womanquery.list();
		if(womanlist.size()>0){
			ret.setWoman(womanlist.get(0));
		}
		SQLQuery manquery = this.getSession().createSQLQuery(sql);
		manquery.setParameters(new Object[]{items.get(0).get("男方_编号")}, new Type[]{Hibernate.STRING});
		manquery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		manquery.addScalar("fileno",Hibernate.STRING);
		manquery.addScalar("name",Hibernate.STRING);
		manquery.addScalar("sex",Hibernate.STRING);
		manquery.addScalar("birthday",Hibernate.TIMESTAMP);
		manquery.addScalar("age",Hibernate.INTEGER);
		manquery.addScalar("idcard",Hibernate.STRING);
		manquery.addScalar("barCode",Hibernate.STRING);
		manquery.addScalar("address",Hibernate.STRING);
		manquery.addScalar("linkmanTel",Hibernate.STRING);
		manquery.addScalar("township",Hibernate.STRING);
		manquery.addScalar("village",Hibernate.STRING);
		manquery.addScalar("workUnit",Hibernate.STRING);
		manquery.addScalar("folk",Hibernate.STRING);
		manquery.addScalar("folkOther",Hibernate.STRING);
		manquery.addScalar("education",Hibernate.STRING);
		manquery.addScalar("occupation",Hibernate.STRING);
		manquery.addScalar("idnumber",Hibernate.STRING);
		manquery.addScalar("residenceAddress",Hibernate.STRING);
		manquery.addScalar("districtNumber",Hibernate.STRING);
		List<Map> manlist= manquery.list();
		if(manlist.size()>0){
			ret.setMan(manlist.get(0));
		}
		return ret;
	}
	
	public String delExam(String id) throws Exception{
		ExamBaseinfo base = (ExamBaseinfo)this.getHibernateTemplate().get(ExamBaseinfo.class, id);
		if(!base.getInputpersonid().equals(SecurityManager.currentOperator().getUsername())){
			throw new Exception("只允许"+base.getInputpersonid()+"进行删除!");
		}
		this.getHibernateTemplate().delete(base);
		this.getSession().createQuery(" delete ExamItems where id.id =?  ").setParameter(0, id).executeUpdate();
		return "删除成功!";
	}
	/**
	 * 由于婚检有两个人的fileno,所以另写了个查询  只有froms 和where的初始内容不同,其他一致
	 * @param examname
	 * @param params
	 * @param basemap
	 * @param collist
	 * @return
	 * @throws Exception
	 */
	
	public Map marry_examList(String examname,String userdistrict,Map<String,Map> params,Map <String,Map<String,String>> basemap,List<String> collist) throws Exception{
		String countsql = " select count(*)";
		//这里是默认的查询内容
		String sql = " select dbo.denc(info.fileno) 'col1',dbo.denc(hf.name) 'col2' , pf.Birthday 'col3' ,info.inputdate 'col4' " ;
		String froms = " from exam_baseinfo info , healthfile hf , PersonalInfo pf  , exam_items it, healthfile hf1 , PersonalInfo pf1 ";
		String where = " where info.fileno =hf.fileno and info.fileno = pf.fileno and info.id = it.id and it.item='男方_编号' and it.value = hf1.fileno  and it.value = pf1.fileno " +
				" and (hf.DistrictNumber like '"+userdistrict+"%' or hf1.DistrictNumber like '"+userdistrict+"%')";
		String select = "";
		//params.getBase();
		Field[] fields= ExamBaseinfo.class.getDeclaredFields();
		List sqlparams = new ArrayList();
		List<Type> sqlparamtypes = new ArrayList();
		Map<String , Field> fieldmap = new HashMap();
		for(int i= 0 ;i <fields.length;i++){
			fieldmap.put(fields[i].getName(), fields[i]);
		}
		int count = 0 ;
		System.out.println("=======params============"+params);
		for(Iterator it = params.keySet().iterator(); it.hasNext();){
			String key = (String)it.next();
			Map<String, String> value = (Map)params.get(key);
			if(value !=null){
				if(basemap.containsKey(key)){
					System.out.println("==================="+key);
					System.out.println("=========fieldmap.get(key).getType().getName()=========="+basemap.get(key).get(COLUMN));
					System.out.println("==========fieldmap.get(key).getType()========="+fieldmap.get(basemap.get(key).get(COLUMN)));
					Map sqls = getBaseSql(basemap.get(key).get(COLUMN),value,basemap.get(key).getClass());
					where += (String)sqls.get(WHERES);
					sqlparams.addAll((List)sqls.get(PARAMS));
					sqlparamtypes.addAll((List<Type>)sqls.get(TYPES));
				}else if(COMMONQUERY.equals(key)){
					//这里是通用查询
					String[] commonparams = value.get("value").split(PARAMSPLIT);
					for(int i = 0 ; i < commonparams.length;i++){
						String tmp = commonparams[i];
						while(tmp.indexOf(CONDITIONSPLIT+CONDITIONSPLIT)>=0){
							tmp = tmp.replaceAll(CONDITIONSPLIT+CONDITIONSPLIT, CONDITIONSPLIT);
						}
						String[] cols = commonparams[i].split(CONDITIONSPLIT);
						if(cols.length<3){
							throw new Exception("通用查询参数"+commonparams[i]+"格式不正确,正确格式为\"姓名 = 张三\"或\"出生日期 = 20120101-20120113\" 或 \"身份证号 = 身份证号1,身份证号2,身份证号3\"");
						}else{
							if(basemap.containsKey(cols[0]) && fieldmap.containsKey(basemap.get(cols[0]).get(COLUMN))){
								Map valuemap = new HashMap();
								valuemap.put(VALUE, cols[2]);
								Map sqls = getBaseSql(basemap.get(cols[0]).get(COLUMN),valuemap,fieldmap.get(basemap.get(cols[0]).get(COLUMN)).getType());
								where += (String)sqls.get(WHERES);
								sqlparams.addAll((List)sqls.get(PARAMS));
								sqlparamtypes.addAll((List<Type>)sqls.get(TYPES));
							}else{
								if(commonExamUtil.hasExamItem(examname, cols[0])){
									Map valuemap = new HashMap();
									valuemap.put(VALUE, cols[2]);
									String itempre = "t"+count;
									count++;
									froms +=", exam_items "+itempre;
									Map sqls = getSql(examname,itempre,cols[0],valuemap);
									where += (String)sqls.get(WHERES);
									sqlparams.addAll((List)sqls.get(PARAMS));
									sqlparamtypes.addAll((List<Type>)sqls.get(TYPES));
								}
							}
						}
					}
				}else if(commonExamUtil.hasExamItem(examname, key)){
					String itempre = "t"+count;
					count++;
					froms +=", exam_items "+itempre;
					Map sqls = getSql(examname,itempre,key,value);
					where += (String)sqls.get(WHERES);
					sqlparams.addAll((List)sqls.get(PARAMS));
					sqlparamtypes.addAll((List<Type>)sqls.get(TYPES));
				}
			}
		}
		//添加scalar
		Map<String,NullableType> scalarmap = new HashMap();
		if(collist != null && !collist.isEmpty()){
			int colnum =0;
			for(String key : collist){
//				if(basemap.containsKey(key)){
//					select += ","+basemap.get(key).get(COLUMN) +" '"+key+"'";
//				}else if(commonExamUtil.hasExamItem(examname, key)){
//					String itempre = "t"+count;
//					froms +=", exam_items "+itempre;
//					count++;
//					select += ","+itempre+".value '"+key+"'";
//				}
//				scalarmap.put(key,hbtTypeMap.get(basemap.get(key).get(COLTYPE)));
//				colnum++;
				
				if(basemap.containsKey(key)){
					select += ","+basemap.get(key).get(COLUMN) +" 'col"+colnum+"'";
				}else if(commonExamUtil.hasExamItem(examname, key)){
					String itempre = "t"+count;
					froms +=", exam_items "+itempre;
					count++;
					select += ","+itempre+".value 'col"+colnum+"'";
				}
				scalarmap.put("col"+colnum,hbtTypeMap.get(basemap.get(key).get(COLTYPE)));
				colnum++;
			}
			select = "select "+select.substring(1);
			sql = select +froms+ where;
		}else{
			sql += froms+where;
		}
		//计算总数,页数
		countsql += froms+where;
		SQLQuery countquery = this.getSession().createSQLQuery(countsql);
		
		if(sqlparamtypes.size()>0){
			NullableType[] types = new NullableType[sqlparamtypes.size()];
			types = sqlparamtypes.toArray(types);
			countquery.setParameters(sqlparams.toArray(), types);
		}
		List countlist = countquery.list();
		int allcount = 0;
		if(countlist.size()>0){
			allcount = (Integer)countlist.get(0);
		}
		Map pageparams = params.get(PAGE);
		int pagesize = Integer.parseInt((String)pageparams.get(PAGESIZE));
		int currentpage = Integer.parseInt((String)pageparams.get(CURRENTPAGE));
		if(pagesize <=0){
			pagesize = DEFAULTPAGESIZE;
		}
		
		int pages = allcount/pagesize + (allcount%pagesize >0?1:0);
		if(currentpage>pages){
			currentpage = pages; 
		}
		if(currentpage <=0){
			currentpage = 1;
		}
		int min = pagesize*(currentpage-1);
		int max = pagesize*currentpage;
		//查询分页结果
		SQLQuery query = this.getSession().createSQLQuery(sql);
		if(sqlparamtypes.size()>0){
			NullableType[] types = new NullableType[sqlparamtypes.size()];
			types = sqlparamtypes.toArray(types);
			query.setParameters(sqlparams.toArray(), types);
		}
		if(scalarmap.size()>0){
			for(Iterator it = scalarmap.keySet().iterator();it.hasNext();){
				String key = (String)it.next();
				NullableType type= (NullableType)scalarmap.get(key);
				query.addScalar(key,type);
			}
		}
		query.setFirstResult(min);
		query.setMaxResults(max);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map ret= new HashMap();
		List retlist = query.list();
		ret.put("rows", retlist);
		ret.put("currentpage", currentpage);
		ret.put("total", allcount);
		ret.put("pages", pages);
		return ret;
	}
	
	private Map getBaseSql(String name, Map<String,String> value, Class type)throws Exception{
		Map<String ,Object> ret = new HashMap();
		List<NullableType> types = new ArrayList();
		int idx = value.get(VALUE).indexOf("-");
		int idx1 = value.get(VALUE).indexOf(",");
		Object typeobj = type.newInstance();
		if(idx >0){
			String[] values = value.get(VALUE).split("-");
			ret.put(WHERES, " and "+name+" >=? and "+name+" <=? ");
			if(typeobj instanceof java.util.Date){
				List<Date> dates = new ArrayList();
				dates.add(shortdateformat.parse(values[0].trim()));
				dates.add(longdateformat.parse(values[1].trim()+DATE_DAYMAX));
				types.add(Hibernate.TIMESTAMP);
				types.add(Hibernate.TIMESTAMP);
				ret.put(PARAMS, dates);
			}else if(typeobj instanceof java.lang.Number ){
				List<BigDecimal> numbers = new ArrayList();
				numbers.add(new BigDecimal(values[0].trim()));
				numbers.add(new BigDecimal(values[1].trim()));
				types.add(Hibernate.BIG_DECIMAL);
				types.add(Hibernate.BIG_DECIMAL);
				ret.put(PARAMS, numbers);
			}else{
				List<String> strs = new ArrayList();
				strs.add(values[0].trim().trim());
				strs.add(values[1].trim().trim());
				types.add(Hibernate.STRING);
				types.add(Hibernate.STRING);
				ret.put(PARAMS, strs);
			}
		}else if(idx1 >0){
			String[] values = value.get(VALUE).split(",");
			String tmp =  " and "+name+" in (";
			List hbtparams = new ArrayList();
			for(int i = 0 ; i <values.length; i++){
				tmp +="?,";
				if(typeobj instanceof java.util.Date){
					hbtparams.add(shortdateformat.parse(values[i]));
					types.add(Hibernate.TIMESTAMP);
				}else if(typeobj instanceof java.lang.Number ){
					hbtparams.add(new BigDecimal(values[i]));
					types.add(Hibernate.BIG_DECIMAL);
				}else{
					hbtparams.add(values[i].trim());
					types.add(Hibernate.STRING);
				}
			}
			ret.put(PARAMS, hbtparams);
			tmp = tmp.substring(0,tmp.length()-1)+")";
			ret.put(WHERES, tmp);
		}else{
			String opt = value.get("opt");
			if(typeobj instanceof java.lang.Number ){
				List<BigDecimal> numbers = new ArrayList();
				numbers.add(new BigDecimal(value.get(VALUE).trim()));
				ret.put(PARAMS, numbers);
				ret.put(WHERES, " and "+ name+" "+ opt +"?  ");
				types.add(Hibernate.BIG_DECIMAL);
			}else{
				List<String> params = new ArrayList();
				if(SQL_OPT_LIKE.equals(opt)){
					opt = SQL_OPT_LIKE;
					params.add("%"+value.get(VALUE).trim()+"%");
				}else if( SQL_OPT_LEFTLIKE.equals(opt)){
					opt = SQL_OPT_LIKE;
					params.add(value.get(VALUE).trim()+"%");
				}else if( SQL_OPT_RIGHTLIKE.equals(opt)){
					opt = SQL_OPT_LIKE;
					params.add("%"+value.get(VALUE).trim());
				}else{
					params.add(value.get(VALUE).trim());
				}
				types.add(Hibernate.STRING);
				ret.put(PARAMS, params);
				ret.put(WHERES, " and "+ name+" "+ opt +"?  ");
			}
		}
		ret.put(TYPES, types);
		return ret;
	}
	
	private Map getSql(String examname,String pre , String name, Map<String,String> value)throws Exception{
		Map<String ,Object> ret = new HashMap();
		List<NullableType> types = new ArrayList();
		int idx = value.get(VALUE).indexOf("-");
		int idx1 = value.get(VALUE).indexOf(",");
		String opt = value.get("opt");
		String where = " and info.id= "+pre+".id and "+pre+".item='"+ name+"' and ";
		String type= commonExamUtil.getExamItemCfg(examname, name).getValuetype();
		if(idx >0){
			String[] values = value.get(VALUE).split("-");
			if("number".equals(type) ){
				List<BigDecimal> numbers = new ArrayList();
				numbers.add(new BigDecimal(values[0].trim()));
				numbers.add(new BigDecimal(values[1].trim()));
				types.add(Hibernate.BIG_DECIMAL);
				types.add(Hibernate.BIG_DECIMAL);
				ret.put(PARAMS, numbers);
				ret.put(WHERES, where+pre+".numbervalue >=? and "+pre+".numbervalue <=? ");
			}else if("date".equals(type) ){
				List<String> strs = new ArrayList();
				strs.add(values[0].trim()+DATE_DAYMIN);
				strs.add(values[1].trim()+DATE_DAYMAX);
				types.add(Hibernate.TIMESTAMP);
				types.add(Hibernate.TIMESTAMP);
				ret.put(PARAMS, strs);
				ret.put(WHERES, where+pre+".value >=? and "+pre+".value <=? ");
			}else{
				List<String> strs = new ArrayList();
				strs.add(values[0].trim());
				strs.add(values[1].trim());
				types.add(Hibernate.STRING);
				types.add(Hibernate.STRING);
				ret.put(PARAMS, strs);
				ret.put(WHERES, where+pre+".value >=? and "+pre+".value <=? ");
			}
		}else if(idx1 >0){
			String[] values = value.get(VALUE).split(",");
			String tmp =  " and "+pre+".value in (";
			List hbtparams = new ArrayList();
			for(int i = 0 ; i <values.length; i++){
				tmp +="?,";
				if("number".equals(type) ){
					hbtparams.add(new BigDecimal(values[i]));
					types.add(Hibernate.BIG_DECIMAL);
				}else if("date".equals(type) ){
					hbtparams.add(values[i].trim()+DATE_DAYMIN);
					types.add(Hibernate.TIMESTAMP);
				}else{
					hbtparams.add(values[i].trim());
					types.add(Hibernate.STRING);
				}
			}
			ret.put(PARAMS, hbtparams);
			tmp = tmp.substring(0,tmp.length()-1)+")";
			ret.put(WHERES, tmp);
		}else{
			if("number".equals(type) ){
				List<BigDecimal> numbers = new ArrayList();
				numbers.add(new BigDecimal(value.get(VALUE).trim()));
				ret.put(PARAMS, numbers);
				types.add(Hibernate.BIG_DECIMAL);
				ret.put(WHERES, where+pre+".numbervalue "+ opt +" ? ");
			}else{
				List<String> params = new ArrayList();
				if(SQL_OPT_LIKE.equals(opt)){
					opt = SQL_OPT_LIKE;
					params.add("%"+value.get(VALUE).trim()+"%");
					ret.put(WHERES, where+pre+".value "+opt+"? ");
				}else if( SQL_OPT_LEFTLIKE.equals(opt)){
					opt = SQL_OPT_LIKE;
					params.add(value.get(VALUE).trim()+"%");
					ret.put(WHERES, where+pre+".value "+opt+"? ");
				}else if( SQL_OPT_RIGHTLIKE.equals(opt)){
					opt = SQL_OPT_LIKE;
					params.add("%"+value.get(VALUE).trim());
					ret.put(WHERES, where+pre+".value "+opt+"? ");
				}else{
					if("date".equals(type)){
						//这里处理日期类型的等于 ,日期不接受时分秒参数
						ret.put(WHERES, where+pre+".value "+opt+"? ");
						params.add(value.get(VALUE).trim()+DATE_DAYMIN);
					}else{
						ret.put(WHERES, where+pre+".value "+opt+"? ");
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
	
	public CommonVO saveExam(CommonVO savedata) throws Exception{
		//完成baseinfo属性拷贝
		ExamBaseinfo base = savedata.getBase();
		if(StringUtils.isEmpty(base.getId())){
			base.setId(fileNoGen.getNextExamId());
		}else{
			ExamBaseinfo old = (ExamBaseinfo)getHibernateTemplate().get(ExamBaseinfo.class, base.getId());
			getHibernateTemplate().evict(old);
			if(!old.getInputpersonid().equals(SecurityManager.currentOperator().getUsername())){
				throw new Exception("只允许"+old.getInputpersonid()+"进行修改!");
			}
		}
		getHibernateTemplate().saveOrUpdate(savedata.getBase());
		//处理items
		List<Map> items = (List<Map>)savedata.getItems();
		for(int i = 0 ; i <items.size();i++){
			Map vo = items.get(i); 
			for(Iterator it = vo.keySet().iterator() ; it.hasNext(); ){
				String key = (String)it.next();
				Object value = vo.get(key);
				ExamItems item = new ExamItems();
				ExamItemsId id = new ExamItemsId();
				id.setId(base.getId());
				id.setIdx(i);
				id.setItem(key);
				item.setId(id);
				//配置表有的字段才保存
				if(commonExamUtil.hasExamItem(base.getExamname(), key) ){
					if(value !=null){
						item.setValue(makstr(base.getExamname(),key,value));
					}else{
						item.setValue(null);
					}
					getHibernateTemplate().saveOrUpdate(item);
				}else{
					System.out.println(key+"=="+value);
				}
			}
		}
		System.out.println("==================="+savedata);
		return savedata;
	}
	
	public String makstr(String examname,String itemname,Object value){
		ExamItemcfg cfg = commonExamUtil.getExamItemCfg(examname, itemname);
		String type= VALUE_TYPE_STRING;
		if(cfg !=null){
			type = cfg.getValuetype();
		}
		//时间类型进行处理后保存
//		if(VALUE_TYPE_DATE.equals(type)){
//			System.out.println("===========value========"+value);
//			Long date = Long.parseLong((String)value);
//			return dateformat.format(date);
//		}else{
			return (String)value;
//		}
	}
	
	public Map getDistrictMap(){
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
	

}
