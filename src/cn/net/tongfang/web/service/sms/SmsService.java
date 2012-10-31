package cn.net.tongfang.web.service.sms;

import java.lang.reflect.Field;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.StringUtils;

import cn.net.tongfang.framework.security.vo.CodSmsMsg;
import cn.net.tongfang.framework.security.vo.CodTelSendRule;
import cn.net.tongfang.framework.security.vo.CodTelUpdateCol;
import cn.net.tongfang.framework.security.vo.SmsPersonTel;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.framework.util.service.vo.PagingParam;
import cn.net.tongfang.framework.util.service.vo.PagingResult;

import com.googlecode.ehcache.annotations.Cacheable;

public class SmsService extends HibernateDaoSupport {
	private static final Logger log = Logger.getLogger(SmsService.class);
	private static final int tel_len = 11;
	private static final String triggername="tel";

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
	
	//TODO 提取规则
	//查询提取规则
	public PagingResult<CodTelUpdateCol> findRules(CodTelUpdateCol qryCond,
			PagingParam pp) throws Exception {
		if (pp == null)
			pp = new PagingParam();

		StringBuilder where = new StringBuilder();
		List params = new ArrayList();

		Map likemap = new HashMap();
		likemap.put("name", null);
		for(Field field : SmsPersonTel.class.getFields()){
			String value = BeanUtils.getProperty(qryCond, field.getName());
			if(StringUtils.hasText(value)){
				if(!likemap.containsKey(field.getName())){
					where.append(" and " +field.getName()+ " = ? ");
					params.add(BeanUtils.getProperty(qryCond, value));
				}else{
					where.append(" and "+field.getName()+" like ?");
					params.add("%" + field.getName() + "%");
				}
			}
		}
		if (params.size() != 0) {
			where.replace(0, 4, " where ");
		}
		StringBuilder hql = new StringBuilder("from  CodTelUpdateCol").append(
				where).append(" order by ord");
		log.debug("hql: " + hql.toString());

		Query query = getSession().createQuery(hql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		int totalSize = query.list().size();

		query.setFirstResult(pp.getStart()).setMaxResults(pp.getLimit());

		List<CodTelUpdateCol> list = (List<CodTelUpdateCol>) query.list();
		return new PagingResult<CodTelUpdateCol>(totalSize, list);
	}
	
	//增加保存提取规则
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
			getHibernateTemplate().saveOrUpdate(module);
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
			getSession().createSQLQuery(" set nocount on ").executeUpdate();
			//删除更新后的表中的触发器
			for(String key : old_tableMap.keySet()){
				if(!tableMap.containsKey(key)){
					getSession().createSQLQuery(" drop TRIGGER "+triggername+"_"+key).executeUpdate();
				}
			}
			for(String key : tableMap.keySet()){
				List<String> cols = tableMap.get(key);
				try{
					getSession().createSQLQuery(" drop TRIGGER "+triggername+"_"+key).executeUpdate();
				}catch(Exception ex){
					//不处理异常
				}
				String sql = "CREATE TRIGGER "+triggername+"_"+key+" on "+key +"  FOR  INSERT ,  UPDATE AS \n begin \nif exists (select 1 from deleted) \n begin ";
				for(String col : cols){
					sql += "\n update Sms_PersonTel set tel = b."+col+" from Sms_PersonTel a , inserted b , deleted c where a.fileNo = b.fileNo and a.fileNo =c.fileNo and b."+col+" <> c."+col+" and len(b."+col+")="+tel_len+"	";
				}
				sql +=" \nend \n else \n begin \n";
				for(String col : cols){
					sql += " \n update Sms_PersonTel set tel = b."+col+" from Sms_PersonTel a , inserted b  where a.fileNo = b.fileNo and len(b."+col+")="+tel_len+"	";
				}
				sql +=" \nend \n end";
				System.out.println("=="+sql);
				getSession().createSQLQuery(sql).executeUpdate();
				
			}
			getSession().createSQLQuery(" set nocount off ").executeUpdate();
			return module.getId();
		} catch (SQLException ex) {
			return null;
		}
	}
	
	//删除提取规则
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
		getSession().createSQLQuery(" set nocount on ").executeUpdate();
		//删除更新后不存在的表中的触发器
		for(String key : old_tableMap.keySet()){
			if(!tableMap.containsKey(key)){
				getSession().createSQLQuery(" drop TRIGGER "+triggername+"_"+key).executeUpdate();
			}
		}
		//重新设置触发器
		for(String key : tableMap.keySet()){
			List<String> cols = tableMap.get(key);
			try{
				getSession().createSQLQuery(" drop TRIGGER "+triggername+"_"+key).executeUpdate();
			}catch(Exception ex){
				//不处理异常
			}
			String sql = "CREATE TRIGGER "+triggername+"_"+key+" on "+key +"  FOR  INSERT ,  UPDATE AS \n begin \nif exists (select 1 from deleted) \n begin ";
			for(String col : cols){
				sql += "\n update Sms_PersonTel set tel = b."+col+" from Sms_PersonTel a , inserted b , deleted c where a.fileNo = b.fileNo and a.fileNo =c.fileNo and b."+col+" <> c."+col+" and len(b."+col+")="+tel_len+"	";
			}
			sql +=" \nend \n else \n begin \n";
			for(String col : cols){
				sql += " \n update Sms_PersonTel set tel = b."+col+" from Sms_PersonTel a , inserted b  where a.fileNo = b.fileNo and len(b."+col+")="+tel_len+"	";
			}
			sql +=" \nend \n end";
			System.out.println("=="+sql);
			getSession().createSQLQuery(sql).executeUpdate();
		}
		getSession().createSQLQuery(" set nocount off ").executeUpdate();
	}
	
	//TODO 发送规则
	
	//查询发送规则
	public PagingResult<CodTelSendRule> findSendRules(CodTelSendRule qryCond,
			PagingParam pp) throws Exception {
		if (pp == null)
			pp = new PagingParam();

		StringBuilder where = new StringBuilder();
		List params = new ArrayList();

		Map likemap = new HashMap();
		likemap.put("name", null);
		for(Field field : SmsPersonTel.class.getFields()){
			String value = BeanUtils.getProperty(qryCond, field.getName());
			if(StringUtils.hasText(value)){
				if(!likemap.containsKey(field.getName())){
					where.append(" and " +field.getName()+ " = ? ");
					params.add(BeanUtils.getProperty(qryCond, value));
				}else{
					where.append(" and "+field.getName()+" like ?");
					params.add("%" + field.getName() + "%");
				}
			}
		}
		if (params.size() != 0) {
			where.replace(0, 4, " where ");
		}
		StringBuilder hql = new StringBuilder("from CodTelSendRule").append(
				where).append(" order by optdate");
		log.debug("hql: " + hql.toString());

		Query query = getSession().createQuery(hql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		int totalSize = query.list().size();

		query.setFirstResult(pp.getStart()).setMaxResults(pp.getLimit());

		List<CodTelSendRule> list = (List<CodTelSendRule>) query.list();
		return new PagingResult<CodTelSendRule>(totalSize, list);
	}
	
	//增加保存发送规则
	public String editSendRule(CodTelSendRule bo) throws Exception {
		CodTelSendRule module = bo;
		if (module.getId() != null && module.getId().isEmpty())
			module.setId(null);
		module.setOptdate(new Date());
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
	
	//TODO 常用短语
	//查询常用短语
	public PagingResult<CodSmsMsg> findMsgs(CodSmsMsg qryCond,
			PagingParam pp) throws Exception {
		if (pp == null)
			pp = new PagingParam();

		StringBuilder where = new StringBuilder();
		List params = new ArrayList();

		Map likemap = new HashMap();
		likemap.put("name", null);
		for(Field field : SmsPersonTel.class.getFields()){
			String value = BeanUtils.getProperty(qryCond, field.getName());
			if(StringUtils.hasText(value)){
				if(!likemap.containsKey(field.getName())){
					where.append(" and " +field.getName()+ " = ? ");
					params.add(BeanUtils.getProperty(qryCond, value));
				}else{
					where.append(" and "+field.getName()+" like ?");
					params.add("%" + field.getName() + "%");
				}
			}
		}
		if (params.size() != 0) {
			where.replace(0, 4, " where ");
		}
		StringBuilder hql = new StringBuilder("from  CodSmsMsg").append(
				where).append(" order by type");
		log.debug("hql: " + hql.toString());

		Query query = getSession().createQuery(hql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		int totalSize = query.list().size();

		query.setFirstResult(pp.getStart()).setMaxResults(pp.getLimit());

		List<CodSmsMsg> list = (List<CodSmsMsg>) query.list();
		return new PagingResult<CodSmsMsg>(totalSize, list);
	}
	
	//增加保存常用短信
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
	
	//TODO 查询联系电话
	public PagingResult<SmsPersonTel> findTels(SmsPersonTel qryCond,
			PagingParam pp) throws Exception {
		if (pp == null)
			pp = new PagingParam();
		StringBuilder where = new StringBuilder();
		List params = new ArrayList();
		Map likemap = new HashMap();
		likemap.put("name", null);
		likemap.put("tel", null);
		for(Field field : SmsPersonTel.class.getFields()){
			String value = BeanUtils.getProperty(qryCond, field.getName());
			if(StringUtils.hasText(value)){
				if(!likemap.containsKey(field.getName())){
					where.append(" and " +field.getName()+ " = ? ");
					params.add(BeanUtils.getProperty(qryCond, value));
				}else{
					where.append(" and "+field.getName()+" like ?");
					params.add("%" + field.getName() + "%");
				}
			}
		}
		if (params.size() != 0) {
			where.replace(0, 4, " where ");
		}
		StringBuilder hql = new StringBuilder("select new SmsPersonTel(vo)from SmsPersonTel vo").append(
				where);
		log.debug("hql: " + hql.toString());
		System.out.println("=="+hql);
		Query query = getSession().createQuery(hql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		int totalSize = query.list().size();

		query.setFirstResult(pp.getStart()).setMaxResults(pp.getLimit());

		List<SmsPersonTel> list = (List<SmsPersonTel>) query.list();
		return new PagingResult<SmsPersonTel>(totalSize, list);
	}
	
	//更新联系电话
	public String createTels(SmsPersonTel qryCond){
		List<CodTelUpdateCol> infos = getTelRule();
		String truncateSQL = " truncate table Sms_PersonTel ";
		getSession().createSQLQuery(truncateSQL).executeUpdate();
		String insertsql = " insert into Sms_PersonTel select fileNo,fileNo,'0',name,case when len(TEL)= "+tel_len+" then TEL else null end,'HealthFile.TEL',-1 from HealthFile ";
		int all = getSession().createSQLQuery(insertsql).executeUpdate();
		int count = 0;
		for(CodTelUpdateCol rule : infos){
			String sql = "update Sms_PersonTel set tel = b."+rule.getCol()+" from Sms_PersonTel a , "+rule.getTablename()+" b where a.fileNo = b.fileNo and a.tel = null and len(b."+rule.getCol()+")="+tel_len;
			count+=getSession().createSQLQuery(sql).executeUpdate();
		}
		String ret = "更新联系电话成功!共成功更新"+count+"户,总数"+all+"户,"+(all-count)+"户未更新!";
		return ret;
	}
	
	//新增或修改联系电话
	public String editTel(SmsPersonTel bo) throws Exception {
		SmsPersonTel module = bo.makeSaveVO();
		if (module.getId() != null && module.getId().isEmpty())
			module.setId(null);
		getHibernateTemplate().saveOrUpdate(module);
		return module.getId();
	}
	
}
