package cn.net.tongfang.web.service.sms;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.net.tongfang.framework.security.bo.Condition;
import cn.net.tongfang.framework.security.bo.QryCondition;
import cn.net.tongfang.framework.security.vo.CodSmsMsg;
import cn.net.tongfang.framework.security.vo.CodTelSendRule;
import cn.net.tongfang.framework.security.vo.CodTelUpdateCol;
import cn.net.tongfang.framework.security.vo.SmsPersonTel;
import cn.net.tongfang.framework.security.vo.SmsSendLog;
import cn.net.tongfang.framework.security.vo.SmsSendTarget;
import cn.net.tongfang.framework.security.vo.SmsSendTargetOther;
import cn.net.tongfang.framework.security.vo.SmsStatus;
import cn.net.tongfang.framework.security.vo.VisitBeforeBorn;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.framework.util.SmsUtil;
import cn.net.tongfang.framework.util.service.vo.PagingParam;
import cn.net.tongfang.framework.util.service.vo.PagingResult;

import com.jasson.im.api.APIClient;


public class SmsService extends HibernateDaoSupport {
	private static final Logger log = Logger.getLogger(SmsService.class);
	private static final int tel_len = 11;
	private static final String triggername="tel";
	public static final String Send_Status_NotCreated = "-1";
	public static final String Send_Status_CreatedNotSend = "0";
	public static final String Send_Status_Sended = "1";
	public static final String Send_Status_Sending = "2";
	public static Map<String , Integer> typemap= new HashMap();
	@Autowired
	private String districtnumber;
	static{
		typemap.put("y", GregorianCalendar.YEAR);
		typemap.put("m", GregorianCalendar.MONTH);
		typemap.put("d",GregorianCalendar.DAY_OF_MONTH);
	}
	private SmsUtil smsUtil;

	//@Cacheable(cacheName = "messageCache")
	
	public List<CodTelUpdateCol> getTelRule() {
		String hql = "from  CodTelUpdateCol order by ord desc";
		try {
			List<CodTelUpdateCol> list = getHibernateTemplate().find(hql);
			log.debug("List size is " + list.size());
			return list;
		} catch (Throwable t) {
			t.printStackTrace();
			return new ArrayList<CodTelUpdateCol>();
		}
	}
	
	//查询提取规则
	public PagingResult<CodTelUpdateCol> findRules(CodTelUpdateCol qryCond,
			PagingParam pp) throws Exception {
		if (pp == null)
			pp = new PagingParam();

		List params = new ArrayList();
		Map likemap = new HashMap();
		likemap.put("name", null);
		StringBuilder where = new StringBuilder( " where 1=1 ");
		makeParamsList(qryCond,likemap ,where,params);
		StringBuilder hql = new StringBuilder("from  CodTelUpdateCol").append(
				where);
		String orderby = (" order by ord");
		log.debug("hql: " + hql.toString());
		int totalSize = ((Long)(getHibernateTemplate().find(" select count(*) from  CodTelUpdateCol").get(0))).intValue();
		final String fhql = hql.toString()+orderby;
		final PagingParam fpp = pp;
		final List fparams = params;
		List<CodTelUpdateCol> list = getHibernateTemplate().executeFind(new HibernateCallback(){
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
		return new PagingResult<CodTelUpdateCol>(totalSize, list);
	}
	
	public static String camelcasify(String in) {
		StringBuilder sb = new StringBuilder();
		boolean capitalizeNext = false;
		int count = 0 ;
		for (char c : in.toCharArray()) {
			if(count==0){
				sb.append(Character.toUpperCase(c));
//				sb.append(c);
				capitalizeNext = false;
			}else{
				if (c == '_') {
					capitalizeNext = true;
				} else {
					if (capitalizeNext) {
						sb.append(Character.toUpperCase(c));
						capitalizeNext = false;
					} else {
						sb.append(c);
					}
				}
			}
			count++;
		}
		return sb.toString();
	}
	//增加保存提取规则
	@Transactional(propagation = Propagation.REQUIRED)
	public String editRule(CodTelUpdateCol bo) throws Exception {
		CodTelUpdateCol module = bo;
		if (module.getId() != null && module.getId().isEmpty())
			module.setId(null);
		try {
			DatabaseMetaData metaData = getHibernateTemplate()
					.getSessionFactory().getCurrentSession().connection()
					.getMetaData();
			ResultSet rs = metaData.getTables(null, "dbo",
					module.getTablename(), new String[] { "TABLE" });
			if (!rs.next()) {
				throw new Exception("表名为" + module.getTablename() + "的表不存在!");
			} else {
				rs = metaData.getColumns(null, "dbo", module.getTablename(),
						module.getCol());
				if (!rs.next()) {
					throw new Exception("表" + module.getTablename() + "中字段"
							+ module.getCol() + "不存在!");
				}
			}
			//取保存前的列表
			Map<String,List> old_tableMap = new HashMap();
			List<CodTelUpdateCol> old_rulelist = getTelRule();
			for(CodTelUpdateCol rule : old_rulelist){
				getHibernateTemplate().evict(rule);
				if(old_tableMap.containsKey(rule.getTablename())){
					List<String> s = (List) old_tableMap.get(rule.getTablename());
					s.add(rule.getCol());
				}else{
					List<String> s = new ArrayList();
					s.add(rule.getCol());
					old_tableMap.put(rule.getTablename(), s);
				}
			}
			getHibernateTemplate().saveOrUpdate(module);
			//取保存后的列表
			Map<String,List> tableMap = new HashMap();
			List<CodTelUpdateCol> rulelist = getTelRule();
			
			for(CodTelUpdateCol rule : rulelist){
				getHibernateTemplate().evict(rule);
				if(tableMap.containsKey(rule.getTablename())){
					List<String> s = (List) tableMap.get(rule.getTablename());
					s.add(rule.getCol());
				}else{
					List<String> s = new ArrayList();
					s.add(rule.getCol());
					tableMap.put(rule.getTablename(), s);
				}
				
			}
			getHibernateTemplate().bulkUpdate(" set nocount on ");
			//删除更新后的表中的触发器
			for(String key : old_tableMap.keySet()){
				if(!tableMap.containsKey(key)){
					getHibernateTemplate().bulkUpdate(" drop TRIGGER "+triggername+"_"+key);
				}
			}
			for(String key : tableMap.keySet()){
				List<String> cols = tableMap.get(key);
				try{
					getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(" drop TRIGGER "+triggername+"_"+key).executeUpdate();
				}catch(Exception ex){
					//不处理异常
				}
				String sql = getTirggerSQL(key,cols);
				getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).executeUpdate();
				
			}
			getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(" set nocount off ").executeUpdate();
			return module.getId();
		} catch (SQLException ex) {
			return null;
		}
	}
	
	//删除提取规则
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeRule(String modules) {
		if (!StringUtils.hasText(modules))
			return;
		//TODO 设置触发器
		//取保存前的列表
		Map<String,List> old_tableMap = new HashMap();
		List<CodTelUpdateCol> old_rulelist = getTelRule();
		for(CodTelUpdateCol rule : old_rulelist){
			if(old_tableMap.containsKey(rule.getTablename())){
				List<String> s = (List) old_tableMap.get(rule.getTablename());
				s.add(rule.getCol());
			}else{
				List<String> s = new ArrayList();
				s.add(rule.getCol());
				old_tableMap.put(rule.getTablename(), s);
			}
		}
		for (String id : modules.split(",")) {
			getHibernateTemplate().delete(
					getHibernateTemplate().get(CodTelUpdateCol.class, id));
		}
		//取保存后的列表
		
		Map<String,List> tableMap = new HashMap();
		List<CodTelUpdateCol> rulelist = getTelRule();
		for(CodTelUpdateCol rule : rulelist){
			if(tableMap.containsKey(rule.getTablename())){
				List<String> s = (List) tableMap.get(rule.getTablename());
				s.add(rule.getCol());
			}else{
				List<String> s = new ArrayList();
				s.add(rule.getCol());
				tableMap.put(rule.getTablename(), s);
			}
			
		}
		getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(" set nocount on ").executeUpdate();
		//删除更新后不存在的表中的触发器
		for(String key : old_tableMap.keySet()){
			if(!tableMap.containsKey(key)){
				getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(" drop TRIGGER "+triggername+"_"+key).executeUpdate();
			}
		}
		//重新设置触发器
		for(String key : tableMap.keySet()){
			List<String> cols = tableMap.get(key);
			try{
				getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(" drop TRIGGER "+triggername+"_"+key).executeUpdate();
			}catch(Exception ex){
				//不处理异常
			}
			String sql = getTirggerSQL(key,cols);
			getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).executeUpdate();
		}
		getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(" set nocount off ").executeUpdate();
	}
	
	private String getTirggerSQL(String key,List<String> cols){
		String sql = "CREATE TRIGGER "+triggername+"_"+key+" on "+key +"  FOR  INSERT ,  UPDATE AS \n begin \nif exists (select 1 from deleted) \n begin ";
		for(String col : cols){
			sql += "\n update Sms_PersonTel set tel = b."+col+" " +
					"from Sms_PersonTel a , inserted b , deleted c " +
					"where a.fileNo = b.fileNo and a.fileNo =c.fileNo " +
					" and b."+col+" <> c."+col+" and len(b."+col+")="+tel_len+" and left(b."+col+",1) <>'0' " +
					" \n" +
					" insert into Sms_PersonTel " +
					" select b.fileNo,b.fileNo,'0',hf.name, b."+col+",'HealthFile.TEL',-1 " +
					" from HealthFile hf ,inserted b" +
					" where len(b."+col+")= "+tel_len+"  and left(b."+col+",1) <>'0' and hf.fileNo=b.fileNo " +
					" and not exists(select 1 from Sms_PersonTel sp where sp.fileno = b.fileNo)	";
		}
		sql +=" \nend \n else \n begin \n";
		for(String col : cols){
			sql += " \n update Sms_PersonTel set tel = b."+col+" from Sms_PersonTel a , inserted b  " +
					"where a.fileNo = b.fileNo  and len(b."+col+")="+tel_len+" and left(b."+col+",1) <>'0' " +
					" \n" +
					" insert into Sms_PersonTel " +
					" select b.fileNo,b.fileNo,'0',hf.name, b."+col+",'HealthFile.TEL',-1 " +
					" from HealthFile hf ,inserted b" +
					" where len(b."+col+")= "+tel_len+"  and left(b."+col+",1) <>'0' and hf.fileNo=b.fileNo " +
					" and not exists(select 1 from Sms_PersonTel sp where sp.fileno = b.fileNo)	";
		}
		sql +=" \nend \n end";
		return sql;
	}
	
	//TODO 发送规则
	
	//查询发送规则
	public PagingResult<CodTelSendRule> findSendRules(CodTelSendRule qryCond,
			PagingParam pp) throws Exception {
		if (pp == null)
			pp = new PagingParam();
		List params = new ArrayList();
		Map likemap = new HashMap();
		likemap.put("name", null);
		StringBuilder where = new StringBuilder( " where 1=1 ");
		makeParamsList(qryCond,likemap ,where,params);
		StringBuilder hql = new StringBuilder("from CodTelSendRule").append(
				where);
		String orderby = (" order by optdate");
		log.debug("hql: " + hql.toString());

		int totalSize = ((Long)(getHibernateTemplate().find(" select count(*) from  CodTelUpdateCol",params.toArray()).get(0))).intValue();
		final String fhql = hql.toString()+orderby;
		final PagingParam fpp = pp;
		final List fparams = params;
		List<CodTelSendRule> list = getHibernateTemplate().executeFind(new HibernateCallback(){
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
		return new PagingResult<CodTelSendRule>(totalSize, list);
	}
	
	//增加保存发送规则
	@Transactional(propagation = Propagation.REQUIRED)
	public String editSendRule(CodTelSendRule bo) throws Exception {
		CodTelSendRule module = bo;
		if (module.getId() != null && module.getId().isEmpty())
			module.setId(null);
		module.setOptdate(new Timestamp(new Date().getTime()));
		DatabaseMetaData metaData = getHibernateTemplate()
				.getSessionFactory().getCurrentSession().connection()
				.getMetaData();
		System.out.println("==========camelcasify(module.getTablename())========="+camelcasify(module.getTablename()));
		String tablename = camelcasify(module.getTablename());
	    ResultSet rs = metaData.getTables(null, "dbo",
				module.getTablename(), new String[] { "TABLE" });
		 
		if (!rs.next()) {
			throw new Exception("表名为" + module.getTablename() + "的表不存在!");
		} else {
			if("0".equals(module.getType())){
				rs = metaData.getColumns(null, "dbo", module.getTablename(),
						"fileNo");
				if (!rs.next()) {
					throw new Exception("表" + module.getTablename() + "中没有fileNo字段,该表不能生成发送规则!");
				}else{
					rs = metaData.getColumns(null, "dbo", module.getTablename(),
							module.getCol());
					if (!rs.next()) {
						throw new Exception("表" + module.getTablename() + "中字段"
								+ module.getCol() + "不存在!");
					}else{
						if(!"date".equals(rs.getString(6)) && !"datetime".equals(rs.getString(6))){
							throw new Exception("表" + module.getTablename() + "中字段"
									+ module.getCol() + "不是时间类型!");
						}
					}
					rs = metaData.getColumns(null, "dbo", module.getTablename(),
							module.getTableidname());
					if (!rs.next()) {
						throw new Exception("表" + module.getTablename() + "中字段"
								+ module.getTableidname() + "不存在!");
					}
					//检查模板内容
					Class objClass = Class.forName("cn.net.tongfang.framework.security.vo."+tablename);
					Map<String,Boolean> propertys = getPropertysFromTemplate(module.getMsg());
					for(String property : propertys.keySet()){
						Field field = null;
						try{
							field = objClass.getDeclaredField(property);
						}catch(Exception ex){
							ex.printStackTrace();
							throw new Exception("表" + module.getTablename() + "中属性"
									+ property + "不存在!<br>(注:属性名和表中的列名有所不同,第一位应小写,下划线\"_\"应去掉,下划线\"_\"后面的字母应大写)");
						}
						if(field!=null){
							boolean isDate = propertys.get(property);
							if(isDate && !java.util.Date.class.isAssignableFrom(field.getType())){
									throw new Exception("表" + module.getTablename() + "中属性"
											+ property + "不是时间类型,不能使用$(date+10d)的模板格式!<br>(注:属性名和表中的列名有所不同,第一位应小写,下划线\"_\"应去掉,下划线\"_\"后面的字母应大写)");
							}
						}
					}
				}
			}else{
				rs = metaData.getColumns(null, "dbo", module.getTablename(),
						module.getCol());
				if (!rs.next()) {
					throw new Exception("表" + module.getTablename() + "中字段"
							+ module.getCol() + "不存在!");
				}else{
					if(!"date".equals(rs.getString(6)) && !"datetime".equals(rs.getString(6))){
						throw new Exception("表" + module.getTablename() + "中字段"
								+ module.getCol() + "不是时间类型!");
					}
				}
				rs = metaData.getColumns(null, "dbo", module.getTablename(),
						module.getTableidname());
				if (!rs.next()) {
					throw new Exception("表" + module.getTablename() + "中字段"
							+ module.getTableidname() + "不存在!");
				}
				//检查模板内容
				Class objClass = Class.forName("cn.net.tongfang.framework.security.vo."+tablename);
				Map<String,Boolean> propertys = getPropertysFromTemplate(module.getMsg());
				for(String property : propertys.keySet()){
					Field field = null;
					try{
						field = objClass.getDeclaredField(property);
					}catch(Exception ex){
						ex.printStackTrace();
						throw new Exception("表" + module.getTablename() + "中属性"
								+ property + "不存在!<br>(注:属性名和表中的列名有所不同,第一位应小写,下划线\"_\"应去掉,下划线\"_\"后面的字母应大写)");
					}
					if(field!=null){
						boolean isDate = propertys.get(property);
						if(isDate && !java.util.Date.class.isAssignableFrom(field.getType())){
								throw new Exception("表" + module.getTablename() + "中属性"
										+ property + "不是时间类型,不能使用$(date+10d)的模板格式!<br>(注:属性名和表中的列名有所不同,第一位应小写,下划线\"_\"应去掉,下划线\"_\"后面的字母应大写)");
						}
					}
				}
			}
			
		}
		getHibernateTemplate().saveOrUpdate(module);
		return module.getId();
	}
	
	//删除发送规则
	public void removeSendRule(String ids) {
		if (!StringUtils.hasText(ids))
			return;

		for (String id : ids.split(",")) {
			getHibernateTemplate().delete(
					getHibernateTemplate().get(CodTelSendRule.class, id));
		}
	}
	
	//查询发送人群规则
	public PagingResult<SmsSendTarget> findSendTargets(SmsSendTarget qryCond,
			PagingParam pp) throws Exception {
		if (pp == null)
			pp = new PagingParam();

		List params = new ArrayList();

		Map likemap = new HashMap();
		likemap.put("name", null);
		StringBuilder where = new StringBuilder( " where 1=1 ");
		makeParamsList(qryCond,likemap ,where,params);
		StringBuilder hql = new StringBuilder("from SmsSendTarget").append(
				where);
		String orderby = (" order by name");
		log.debug("hql: " + hql.toString());

		int totalSize = ((Long)(getHibernateTemplate().find("select count(*) "+hql,params.toArray()).get(0))).intValue();
		final String fhql = hql+orderby;
		final PagingParam fpp = pp;
		final List fparams = params;
		List<SmsSendTarget> list = getHibernateTemplate().executeFind(new HibernateCallback(){
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
		return new PagingResult<SmsSendTarget>(totalSize, list);
	}
	
	//返回下拉列表数据
	public List getSendTargetOption() throws Exception {
		
		StringBuilder where = new StringBuilder();
		List params = new ArrayList();

		if (params.size() != 0) {
			where.replace(0, 4, " where ");
		}
		StringBuilder hql = new StringBuilder("select new list(id , name,msg) from SmsSendTarget");
		List list =  getHibernateTemplate().find(hql.toString(),params.toArray());
		List addmap = new ArrayList();
		addmap.add( "99");
		addmap.add("------增加人群------");
		list.add(addmap);
		return list;
	}
	
	//增加保存发送人群规则
	@Transactional(propagation = Propagation.REQUIRED)
	public String editSendTarget(SmsSendTarget bo) throws Exception {
		SmsSendTarget module = bo;
		String tablename = this.camelcasify(module.getTablename());
		if (module.getId() != null && module.getId().isEmpty())
			module.setId(null);
		DatabaseMetaData metaData = getHibernateTemplate()
				.getSessionFactory().getCurrentSession().connection()
				.getMetaData();
		ResultSet rs = metaData.getTables(null, "dbo",
				module.getTablename(), new String[] { "TABLE" });
		if (!rs.next()) {
			throw new Exception("表名为" + module.getTablename() + "的表不存在!");
		} else {
			rs = metaData.getColumns(null, "dbo", module.getTablename(),
					"fileNo");
			if (!rs.next()) {
				throw new Exception("表" + module.getTablename() + "中没有fileNo字段,该表不能生成发送规则!");
			}else{
				rs = metaData.getColumns(null, "dbo", module.getTablename(),
						module.getTableidname());
				if (!rs.next()) {
					throw new Exception("表" + module.getTablename() + "中字段"
							+ module.getTableidname() + "不存在!");
				}
				//检查模板内容
				Class objClass = Class.forName("cn.net.tongfang.framework.security.vo."+tablename);
				Map<String,Boolean> propertys = getPropertysFromTemplate(module.getMsg());
				for(String property : propertys.keySet()){
					Field field = null;
					try{
						field = objClass.getDeclaredField(property);
					}catch(Exception ex){
						ex.printStackTrace();
						throw new Exception("表" + module.getTablename() + "中属性"
								+ property + "不存在!<br>(注:属性名和表中的列名有所不同,第一位应小写,下划线\"_\"应去掉,下划线\"_\"后面的字母应大写)");
					}
					if(field!=null){
						boolean isDate = propertys.get(property);
						if(isDate && !java.util.Date.class.isAssignableFrom(field.getType())){
								throw new Exception("表" + module.getTablename() + "中属性"
										+ property + "不是时间类型,不能使用$(date+10d)的模板格式!<br>(注:属性名和表中的列名有所不同,第一位应小写,下划线\"_\"应去掉,下划线\"_\"后面的字母应大写)");
						}
					}
				}
			}
		}
		getHibernateTemplate().saveOrUpdate(module);
		return module.getId();
	}
	
	//删除发送人群规则
	public void removeSendTarget(String ids) {
		if (!StringUtils.hasText(ids))
			return;
		for (String id : ids.split(",")) {
			getHibernateTemplate().delete(
					getHibernateTemplate().get(SmsSendTarget.class, id));
		}
	}
	
	
	//查询其他人群规则
	public PagingResult<Map> findOtherSendTargets(SmsSendTargetOther qryCond,
			PagingParam pp) throws Exception {
		if (pp == null)
			pp = new PagingParam();

		StringBuilder where = new StringBuilder();
		where.append(" where ssto.districtNumber = dist.id and ssto.type = basic.id ");
		List params = new ArrayList();
		Map likemap = new HashMap();
		likemap.put("name", null);
		Map likemap1 = new HashMap();
		likemap1.put("districtNumber", null);
		Map bean = PropertyUtils.describe(qryCond);
		for( Object key : bean.keySet()){
			String keystr = (String)key;
			if("class".equals(keystr)){
				continue;
			}
			Object value = bean.get(key);
			if(value != null ){
				if(value instanceof String){
					if(!StringUtils.hasText((String)value)){
						value = null;
					}
				}
				if(value !=null){
					if(!likemap.containsKey(keystr)){
						if(likemap1.containsKey(keystr)){
							where.append(" and ssto." +keystr+ " like ? ");
							params.add(value+"%");
						}else{
							where.append(" and ssto." +keystr+ " = ? ");
							params.add(value);
						}
					}else{
						where.append(" and ssto."+keystr+" like ?");
						params.add("%" + value + "%");
					}
				}
			}
		}
		StringBuilder hql = new StringBuilder("select new map(ssto.id as id , ssto.name as name ,ssto.tel as tel,ssto.districtNumber as districtNumber ,dist.name as districtNumber_name ,ssto.type as type,basic.name as type_name,ssto.isTest as isTest) " +
				"from SmsSendTargetOther ssto , District dist , BasicInformation basic ").append(
				where);
		String orderby = (" order by dist.id");
		StringBuilder counthql = new StringBuilder("select count(*) " +
				"from SmsSendTargetOther ssto , District dist , BasicInformation basic ").append(
				where).append(" ");
		log.debug("hql: " + hql.toString());
		int totalSize = ((Long)(getHibernateTemplate().find("select count(*) "+hql,params.toArray()).get(0))).intValue();
		final String fhql = hql+orderby;
		final PagingParam fpp = pp;
		final List fparams = params;
		List<Map> list = getHibernateTemplate().executeFind(new HibernateCallback(){
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
		return new PagingResult<Map>(totalSize, list);
	}
	
	//返回下拉列表数据
	public List getOtherSendTargetOption() throws Exception {
		StringBuilder hql = new StringBuilder("select new list(id,name) from District where id like '"+districtnumber+"%'");
		List<List<String>> list =  getHibernateTemplate().find(hql.toString());
		for(List<String> item : list){
			if(item.get(0).length() == 9){
				item.set(1, "|--"+item.get(1));
			}else if(item.get(0).length() == 12){
				item.set(1, "|--|--"+item.get(1));
			}
		}
		List addmap = new ArrayList();
		addmap.add( "99");
		addmap.add("--管理其他人群--");
		list.add(addmap);
		return list;
	}
	
	public List getDistrictOption() throws Exception {
		StringBuilder hql = new StringBuilder("select new list(id,name) from District where id like '"+districtnumber+"%'");
		List<List<String>> list = getHibernateTemplate().find(hql.toString());
		for(List<String> item : list){
			if(item.get(0).length() == 9){
				item.set(1, "|--"+item.get(1));
			}else if(item.get(0).length() == 12){
				item.set(1, "|--|--"+item.get(1));
			}
		}		
		return list;
	}
	
	public List getTypeOption() throws Exception {
		StringBuilder hql = new StringBuilder("select new list(id , name) from BasicInformation where type = '4001' and isMain = 0 and isBeforehand = 1 ");
		List list =  getHibernateTemplate().find(hql.toString());
		return list;
	}
	
	//增加保存其他人群规则
	@Transactional(propagation = Propagation.REQUIRED)
	public String editOtherSendTarget(SmsSendTargetOther bo) throws Exception {
		SmsSendTargetOther module = bo;
		if(module.getIsTest() == null){
			module.setIsTest(0);
		}
		if (module.getId() != null && module.getId().isEmpty())
			module.setId(null);
		getHibernateTemplate().saveOrUpdate(module);
		return module.getId();
	}
	
	//删除其他人群规则
	public void removeOtherSendTarget(String ids) {
		if (!StringUtils.hasText(ids))
			return;
		for (String id : ids.split(",")) {
			getHibernateTemplate().delete(
					getHibernateTemplate().get(SmsSendTargetOther.class, id));
		}
	}
	
	//TODO 常用短语
	//查询常用短语
	public PagingResult<CodSmsMsg> findMsgs(CodSmsMsg qryCond,
			PagingParam pp) throws Exception {
		if (pp == null)
			pp = new PagingParam();

		List params = new ArrayList();

		Map likemap = new HashMap();
		likemap.put("name", null);
		StringBuilder where = new StringBuilder( " where 1=1 ");
		makeParamsList(qryCond,likemap ,where,params);
		StringBuilder hql = new StringBuilder("from  CodSmsMsg").append(
				where);
		String orderby = " order by type";
		log.debug("hql: " + hql.toString());
		
		int totalSize = ((Long)(getHibernateTemplate().find("select count(*) "+hql,params.toArray()).get(0))).intValue();
		
		final String fhql = hql+orderby;
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
		return new PagingResult<CodSmsMsg>(totalSize, list);
	}
	
	//增加保存常用短信
	@Transactional(propagation = Propagation.REQUIRED)
	public String editMsg(CodSmsMsg bo) throws Exception {
		CodSmsMsg module = bo;
		if (module.getId() != null && module.getId().isEmpty())
			module.setId(null);
		getHibernateTemplate().saveOrUpdate(module);
		return module.getId();

	}
	
	//删除提取常用短信
	public void removeMsg(String modules) {
		if (!StringUtils.hasText(modules))
			return;

		for (String id : modules.split(",")) {
			getHibernateTemplate().delete(
					getHibernateTemplate().get(CodSmsMsg.class, id));
		}
	}
	
	//查询联系电话
	public PagingResult<SmsPersonTel> findTels(QryCondition qryCond,
			PagingParam pp) throws Exception {
		if (pp == null)
			pp = new PagingParam();
		StringBuilder where = new StringBuilder(" where 1=1 ");
		List params = new ArrayList();
		Map likemap = new HashMap();
		likemap.put("vo.name", null);
		likemap.put("vo.tel", null);
		Map encMap = new HashMap();
		encMap.put("vo.name", null);
		encMap.put("vo.fileNo", null);
		String district = qryCond.getDistrict();
		
		if(StringUtils.hasText(district)){
			where.append(" and vo.fileNo = hf.fileNo and hf.districtNumber like ? ");
			params.add(district+"%");
		}
		for(Condition obj : qryCond.getConditions()){
			String key = obj.getFilterKey();
			String value = obj.getFilterVal();
			if(StringUtils.hasText(value)){
				if(encMap.containsKey(key)){
					value = EncryptionUtils.encry(value);
				}
				if(!likemap.containsKey(key)){
					where.append(" and " +key+ " = ? ");
					params.add(value);
				}else{
					where.append(" and "+key+" like ?");
					params.add("%" + value + "%");
				}
			}
		}
		StringBuilder hql = new StringBuilder("select new SmsPersonTel(vo)from SmsPersonTel vo , HealthFile hf  ").append(
				where);
		

		int totalSize = ((Long)(getHibernateTemplate().find("select count(*) "+hql,params.toArray()).get(0))).intValue();
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
		return new PagingResult<SmsPersonTel>(totalSize, list);
	}
	
	//更新联系电话
	@Transactional(propagation = Propagation.REQUIRED)
	public String createTels(SmsPersonTel qryCond){
		List<CodTelUpdateCol> infos = getTelRule();
		String truncateSQL = " truncate table Sms_PersonTel ";
		getHibernateTemplate().bulkUpdate(truncateSQL);
		String insertsql = " insert into Sms_PersonTel select fileNo,fileNo,'0',name, TEL,'HealthFile.TEL',-1 from HealthFile where len(TEL)= "+tel_len+" and left(TEL,1) <>'0' ";
		int count = getHibernateTemplate().bulkUpdate(insertsql);
		for(CodTelUpdateCol rule : infos){
			String sql = " insert into Sms_PersonTel select a.fileNo,a.fileNo,'0',a.name, b."+rule.getCol()+",'"+rule.getTablename()+"."+rule.getCol()+"',-1 " +
					" from HealthFile a , "+rule.getTablename()+" b where a.fileNo = b.fileNo and left(b."+rule.getCol()+",1) <>'0' and  len(b."+rule.getCol()+")="+tel_len+
					" and not exists (select 1 from Sms_PersonTel c where c.fileNo = a.fileno) ";
			count+=getHibernateTemplate().bulkUpdate(sql);
		}
		String ret = "更新联系电话成功!共成功更新"+count+"户!";
		return ret;
	}
	
	//短信发送任务启动
	public String smsStartSend() throws Exception{
		if(!smsUtil.isStarted()){
			smsUtil.setStarted(true);
		}
		sendMsgJobonly();
		return "true";
	}
	
	public boolean isStarted(){
		return smsUtil.isStarted();
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public  PagingResult<SmsSendLog> queryLogs(QryCondition qryCond,
			PagingParam pp) throws Exception {
		if (pp == null)
			pp = new PagingParam();
		Map leftlikemap = new HashMap();
		leftlikemap.put("vo.fileNo", null);
		Map likemap = new HashMap();
		likemap.put("vo.name", null);
		likemap.put("vo.tel", null);
		Map encMap = new HashMap();
		encMap.put("vo.name", null);
		encMap.put("vo.fileNo", null);
		Map intMap = new HashMap();
		intMap.put("vo.status", null);
		Map dateMap = new HashMap();
		dateMap.put("vo.smsdate", null);
		if("0".equals(qryCond.getDistrict())){
			getHibernateTemplate().bulkUpdate("update Sms_SendLog set personname = hf.name from Sms_SendLog vo, HealthFile hf where vo.fileno = hf.fileNo and vo.personname is null and querytype='0' ");
			StringBuilder where = new StringBuilder(" where querytype='0' ");
			List params = new ArrayList();
			for(Condition obj : qryCond.getConditions()){
				String key = obj.getFilterKey();
				String value = obj.getFilterVal();
				if(StringUtils.hasText(value)){
					if(encMap.containsKey(key)){
						value = EncryptionUtils.encry(value);
					}
					if(leftlikemap.containsKey(key)){
						where.append(" and "+key+" like ?");
						params.add(value + "%");
					}else if (likemap.containsKey(key)){
						where.append(" and "+key+" like ?");
						params.add("%" + value + "%");
					}else{
						if(intMap.containsKey(key)){
							where.append(" and " +key+ " = ? ");
							params.add(Integer.parseInt(value));
						}else if(dateMap.containsKey(key)){
							where.append(" and " +key + obj.getOpt()+ "  ? ");
							params.add(new Date(Long.parseLong(value)));
						}else{
							where.append(" and " +key+ " = ? ");
							params.add(value);
						}
					}
				}
			}
			//这里的返回结果是用js进行的解密,所以没写解密的代码
			StringBuilder hql = new StringBuilder("select vo  from SmsSendLog vo   ").append(
					where) ;
			String orderby = " order by vo.smsdate desc,vo.sendtime desc ";
			StringBuilder counthql = new StringBuilder("select count(*) from SmsSendLog vo   ").append(
					where);
			log.debug("hql: " + hql.toString());
			int totalSize = ((Long)(getHibernateTemplate().find("select count(*) "+hql,params.toArray()).get(0))).intValue();
			final String fhql = hql+orderby;
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
			
			return new PagingResult<SmsSendLog>(totalSize, list);
		}else{
			getHibernateTemplate().bulkUpdate("update Sms_SendLog set personname = hf.name from Sms_SendLog vo, Sms_SendTargetOther hf where vo.fileno = hf.id and vo.personname is null  and querytype='1' ");
			StringBuilder where = new StringBuilder(" where  querytype='1' ");
			List params = new ArrayList();
			for(Condition obj : qryCond.getConditions()){
				String key = obj.getFilterKey();
				String value = obj.getFilterVal();
				if(StringUtils.hasText(value)){
					if(encMap.containsKey(key)){
						value = EncryptionUtils.encry(value);
					}
					if(leftlikemap.containsKey(key)){
						where.append(" and "+key+" like ?");
						params.add(value + "%");
					}else if (likemap.containsKey(key)){
						where.append(" and "+key+" like ?");
						params.add("%" + value + "%");
					}else{
						if(intMap.containsKey(key)){
							where.append(" and " +key+ " = ? ");
							params.add(Integer.parseInt(value));
						}else if(dateMap.containsKey(key)){
							where.append(" and " +key + obj.getOpt()+ "  ? ");
							params.add(new Date(Long.parseLong(value)));
						}else{
							where.append(" and " +key+ " = ? ");
							params.add(value);
						}
					}
				}
			}
			
			//这里的返回结果是用js进行的解密,所以没写解密的代码
			StringBuilder hql = new StringBuilder("select vo  from SmsSendLog vo   ").append(
					where);
			String orderby = " order by vo.smsdate desc,vo.sendtime desc ";
			int totalSize = ((Long)(getHibernateTemplate().find("select count(*) "+hql,params.toArray()).get(0))).intValue();
			final String fhql = hql+orderby;
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
			
			return new PagingResult<SmsSendLog>(totalSize, list);
		}
	}
	/**
	 * 生成短信的任务
	 * @throws Exception
	 */
	public void makeMsgJob() throws Exception{
		System.out.println("========生成短信的任务===========");
		smsUtil.createMsgJob();
	}
	//定时任务
	public void sendMsgJob() throws Exception{
		System.out.println("========发送短信的任务===========");
		smsUtil.createMsgJob();
		sendMsgJobonly();
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void sendMsgJobonly() throws Exception{
		System.out.println("========只发送,不生成数据,发送短信的任务===========");
		smsUtil.setStarted(true);
		//这里进行短信的发送
		APIClient handler = new APIClient();
		int connectRe = handler.init(smsUtil.getHost(), smsUtil.getName(), smsUtil.getPwd(), smsUtil.getApiId(),smsUtil.getDbName());
		String exceptStr = "";
		try{
			if(connectRe == APIClient.IMAPI_SUCC){
				List<SmsSendLog> sendinglist = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("from SmsSendLog where status = "+SmsUtil.IS_SENDED_FALSE+"").list();
				if(sendinglist.isEmpty()){
					smsUtil.setSended();
				}else{
					for(SmsSendLog log : sendinglist){
						if(smsUtil.isStarted()){
							int result = 0;
							Serializable id = null;
							String tablename = camelcasify(log.getTablename());
							Type idtype =  this.getSessionFactory().getClassMetadata(Class.forName("cn.net.tongfang.framework.security.vo."+tablename))
									.getIdentifierType();
							System.out.println("==============idtype.getClass().getName()====="+idtype.getClass().getName());
							//判断如果是数字型，则转换
							if("org.hibernate.type.LongType".equals(idtype.getClass().getName()) ){
								id = Long.parseLong(log.getTableidvalue());
							}else{
								id = log.getTableidvalue();
							}
							Object obj = this.getHibernateTemplate().get(Class.forName("cn.net.tongfang.framework.security.vo."+tablename), id);
							if(obj==null){
								exceptStr =exceptStr+"表"+log.getTablename()+"中主键为"+log.getTableidvalue()+"的数据不存在!\r\n";
							}
							String msg = getMsgFromTemplate(log.getMsg(),obj);
							log.setSendtime(new Timestamp(new Date().getTime()));
							log.setMsg(msg);
							int msglen = msg.length();
							if(msglen%70 >60){
								msg = (msg +"          ").substring(0,70*(msglen/70+1))+"#";
							}
							result = handler.sendSM(new String[]{log.getTel()}, msg, 20, 20l);
							if(result == APIClient.IMAPI_SUCC)
					        {            
								log.setError("发送成功");
								log.setStatus(SmsUtil.IS_SENDED_TRUE);
					        }else{
					        	log.setStatus(SmsUtil.IS_SENDED_ERROR);
					        	if(result == APIClient.IMAPI_INIT_ERR)
					        		log.setError("未初始化");
						        else if(result == APIClient.IMAPI_CONN_ERR)
						        	log.setError("数据库连接失败");
						        else if(result == APIClient.IMAPI_DATA_ERR)
						        	log.setError("参数错误");
						        else if(result == APIClient.IMAPI_DATA_TOOLONG)
						        	log.setError("消息内容太长");
						        else if(result == APIClient.IMAPI_INS_ERR)
						        	log.setError("数据库插入错误");
						        else
						        	log.setError("出现其他错误");
					        }
							getHibernateTemplate().getSessionFactory().getCurrentSession().saveOrUpdate(log);
						}else{
							break;
						}
					}
				}
				sendinglist = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("from SmsSendLog where status = "+SmsUtil.IS_SENDED_FALSE+"").list();
				if(sendinglist.isEmpty()){
					smsUtil.setSended();
				}
				smsUtil.setStarted(false);
				//如果有异常发生,返回异常信息
				if(StringUtils.hasText(exceptStr)){
					throw new  Exception (exceptStr);
				}
			}else if(connectRe == APIClient.IMAPI_CONN_ERR){
	        	throw new Exception("连接失败");
			}else if(connectRe == APIClient.IMAPI_API_ERR){
				throw new Exception("apiID不存在");
	        }
		}finally{
			if(handler !=null)
				handler.release();
		}
	}
	
	/**
	 * 这里用SmsSendTarget而不是SmsSendTargetOther
	 * SmsSendTarget 比 SmsSendTargetOther 多了msg属性
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String sendMsg(Map params) throws Exception{
		String ret = "";
		int flag = Integer.parseInt((String)params.get("flag"));
		String msg = (String)params.get("msg");
		String title = (String)params.get("title");
		if(flag ==0){
			String id = (String)params.get("id");
			if(!StringUtils.hasText(id)){
				return "未选择类型!";
			}
			SmsSendTarget rule = (SmsSendTarget) getHibernateTemplate().getSessionFactory().getCurrentSession().get(SmsSendTarget.class, id);
			if(rule == null){
				return "未选择类型!";
			}
			Date today = new Date();
			today = DateUtils.truncate(today, Calendar.DAY_OF_MONTH);
			String sql = 
					" insert into Sms_SendLog "
							+ "select  DATEADD(D, 0, DATEDIFF(D, 0, GETDATE())) ,'"
							+ title
							+ "', a.fileno,b.tel,'"
							+ msg
							+ "',0 , null,null,'"+rule.getTablename()+"',a."+rule.getTableidname()
							+ ",null,'0',newid() from "
							+ rule.getTablename()
							+ " a , Sms_PersonTel b ,HealthFile c where a.fileno = b.fileno and a.fileno = c.fileno and c.status = '0' and NOT EXISTS (select 1 from Sms_SendLog log where log.fileNo = a.fileNo and log.smsdate = DATEADD(D, 0, DATEDIFF(D, 0, GETDATE())) and examname ='"
							+ title
							+ "'  ) and  " + rule.getWherestr();
			getSession()
					.createSQLQuery(sql)
					.executeUpdate();
			ret = "发送成功!";
		}else{
			String where = " 1=1 ";
			String districtNumber = (String)params.get("districtNumber");
			if(StringUtils.hasText(districtNumber)){
				where +=   " and districtNumber like '"+districtNumber+"%' ";
			}
			String type = (String)params.get("type");
			if(StringUtils.hasText(type)){
				where +=   " and type = "+type+" ";
			}
			String isTest = (String)params.get("isTest");
			if(StringUtils.hasText(isTest)){
				where +=   " and isTest = "+isTest+" ";
			}
			Date today = new Date();
			today = DateUtils.truncate(today, Calendar.DAY_OF_MONTH);
			String sql = 
					" insert into Sms_SendLog "
							+ "select DATEADD(D, 0, DATEDIFF(D, 0, GETDATE())) ,'"
							+ title
							+ "', id,tel,'"
							+ msg
							+ "',0 , null,null,'SmsSendTargetOther',id "
							+ ",null,'1',newid() from Sms_SendTargetOther other  where "+where+" and NOT EXISTS (select 1 from Sms_SendLog log where log.fileNo = other.id and log.smsdate = DATEADD(D, 0, DATEDIFF(D, 0, GETDATE())) and examname ='"
							+ title
							+ "'  ) ";
			getSession().createSQLQuery(sql)
					.executeUpdate();
			ret = "发送成功!";
		}
		sendMsgJobonly();
		return ret;
	}
	
	//发送模板得到内容
	private String getMsgFromTemplate(String template,Object obj){
		String ret = template;
		String pattern = "\\$\\s*\\(\\s*([\\w$+-]+)\\s*\\)";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(template);
		while (m.find( )) {
			try{
				String str = m.group(0);
				String testStr = m.group(1);
				String pattern1 = "(\\w+)\\s*([+-])\\s*(\\d+)([ymd])";
				Pattern p1 = Pattern.compile(pattern1);
				if(testStr.startsWith("$")){
					String testStr1 = testStr.substring(1);
					Matcher m1 = p1.matcher(testStr1);
					if(m1.find()){
						if("today".equals(m1.group(1))){
							GregorianCalendar today = new GregorianCalendar();
							int num = Integer.parseInt(m1.group(3));
							if("-".equals(m1.group(2))){
								num = -num;
							}
							today.add(typemap.get(m1.group(4)), num);
							str =  new SimpleDateFormat("yyyy年M月d日").format(today.getTime());
						}
					}
				}else{
					Matcher m1 = p1.matcher(testStr);
					if(m1.find()){
						Object retobj = PropertyUtils.getProperty(obj,m1.group(1));
						if(retobj instanceof java.util.Date){
							GregorianCalendar dateobj = new GregorianCalendar();
							dateobj.setTime((Date)retobj);
							int num = Integer.parseInt(m1.group(3));
							if("-".equals(m1.group(2))){
								num = -num;
							}
							dateobj.add(typemap.get(m1.group(4)), num);
							str =  new SimpleDateFormat("yyyy年M月d日").format(dateobj.getTime());
						}else{
							str =String.valueOf(retobj);
						}
					}else{
						Object retobj = PropertyUtils.getProperty(obj,testStr);
						if(retobj instanceof java.util.Date){
							str =  new SimpleDateFormat("yyyy年M月d日").format((Date)retobj);
						}else{
							str =String.valueOf(retobj);
						}
					}
				}
				ret = ret.replace(m.group(0), str);
			}catch(NoSuchMethodException ex){
				ex.printStackTrace();
				if(m.group(1).toLowerCase().indexOf("date")>=0){
					String str =  new SimpleDateFormat("yyyy年M月d日").format(new Date());
					ret = ret.replace(m.group(0), str);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return ret;
	}
	
	//发送模板得到内容
	private Map<String,Boolean> getPropertysFromTemplate(String template){
		String pattern = "\\$\\s*\\(\\s*([\\w$+-]+)\\s*\\)";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(template);
		Map<String,Boolean> propertyList = new HashMap<String,Boolean>();
		while (m.find( )) {
			try{
				String testStr = m.group(1);
				String pattern1 = "(\\w+)\\s*([+-])\\s*(\\d+)([ymd])";
				Pattern p1 = Pattern.compile(pattern1);
				if(!testStr.startsWith("$")){
					Matcher m1 = p1.matcher(testStr);
					if(m1.find()){
						propertyList.put(m1.group(1),true);
					}else{
						propertyList.put(testStr,false);
					}
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return propertyList;
	}
	
	public void Test(){
		VisitBeforeBorn vb = new VisitBeforeBorn();
		vb.setNextVisitDate(new Timestamp(new Date().getTime()));
		System.out.println("==================="+getMsgFromTemplate("测试时间$(nextVisitDate-)",vb));
	}
	
	public  static void main(String[]args){
		SmsService ss = new SmsService();
		ss.Test();
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public String querySendStatus(){
		Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
		List isCreated = getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createQuery("from SmsStatus where smsdate = ?")
				.setParameter(0, today).list();
		if(isCreated.isEmpty()){
			return Send_Status_NotCreated;
		}else{
			SmsStatus smsstatus = (SmsStatus) isCreated.get(0);
			if(smsstatus.getIssended() == 1){
				return Send_Status_Sended;
			}else{
				if(smsUtil.isStarted()){
					return Send_Status_Sending;
				}else{
					return Send_Status_CreatedNotSend;
				}
			}
		}
	}
	
	//停止短信发送
	public String smsStopSend(){
		if(smsUtil.isStarted()){
			smsUtil.setStarted(false);
		}
		return "";
	}
	
	//新增或修改联系电话
	@Transactional(propagation = Propagation.REQUIRED)
	public String editTel(SmsPersonTel bo) throws Exception {
		SmsPersonTel module = bo.makeSaveVO();
		if (module.getId() != null && module.getId().isEmpty())
			module.setId(null);
		getHibernateTemplate().saveOrUpdate(module);
		return module.getId();
	}

	public SmsUtil getSmsUtil() {
		return smsUtil;
	}

	public void setSmsUtil(SmsUtil smsUtil) {
		this.smsUtil = smsUtil;
	}
	
	private void makeParamsList(Object qryCond,Map likemap ,StringBuilder where,List params) throws Exception{
		Map bean = PropertyUtils.describe(qryCond);
		for( Object key : bean.keySet()){
			String keystr = (String)key;
			if("class".equals(keystr)){
				continue;
			}
			Object value = bean.get(key);
			if(value != null ){
				if(value instanceof String){
					if(!StringUtils.hasText((String)value)){
						value = null;
					}
				}
				if(value !=null){
					if(!likemap.containsKey(keystr)){
						where.append(" and " +keystr+ " = ? ");
						params.add(value);
					}else{
						where.append(" and "+keystr+" like ? ");
						params.add("%" + value + "%");
					}
				}
			}
		}
	}
	
}
