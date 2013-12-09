package cn.net.tongfang.framework.security.demo.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


import cn.net.tongfang.framework.security.vo.BasicInformation;

public class MetaServiceImpl extends HibernateDaoSupport implements MetaService {
	private static final Logger log = Logger.getLogger(MetaServiceImpl.class);

	@Override
	public Map<Integer, List<BasicInformation>> get(final List<Integer> code) {
		final String hql = "select p from BasicInformation p where p.type in (:codes) and isMain = 0 order by type, number";
		Map<Integer, List<BasicInformation>> resMap = null;
		List<BasicInformation> res = (List<BasicInformation>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					@Override
					public List<BasicInformation> doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query qry = session.createQuery(hql);
						qry.setParameterList("codes", code);
						return (List<BasicInformation>) qry.list();
					}
				});

		resMap = new HashMap<Integer, List<BasicInformation>>();
		for (BasicInformation i : res) {
			int type = i.getType();
			List<BasicInformation> info = resMap.get(type);
			if (info == null) {
				info = new ArrayList<BasicInformation>();
				resMap.put(type, info);
			}
			info.add(i);
		}
		return resMap;
	}
	
	@Override
	public Map<Integer, List<BasicInformation>> getAll() {
//		String key = "cod_baseinfo";
//		if(CacheUtil.hasCache(key)){
//			Map<Integer, List<BasicInformation>> value = (Map<Integer, List<BasicInformation>>)CacheUtil.getCache(key);
//			System.out.println("22222222222222222222222222222222222222");
//			return value;
//		}else{
			final String hql = "select p from BasicInformation p where isMain = 0 order by type, number";
			Map<Integer, List<BasicInformation>> resMap = null;
			List<BasicInformation> res = getHibernateTemplate().find(hql);
			resMap = new HashMap<Integer, List<BasicInformation>>();
			for (BasicInformation i : res) {
				int type = i.getType();
				List<BasicInformation> info = resMap.get(type);
				if (info == null) {
					info = new ArrayList<BasicInformation>();
					resMap.put(type, info);
				}
				info.add(i);
			}
//			CacheUtil.putCache(key, resMap);
//			System.out.println("3333333333333333333333333333");
			return resMap;
//		}
	}
	
	public boolean isInputPerson(String id,String tableName){
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		String username = "@sdfy$";
		if(user != null){
			username = user.getUsername();
		}
		String hql = "From " + tableName + " Where id = ? And inputPersonId In(?,'admin')";
		List list = getHibernateTemplate().find(hql,new Object[]{id,username});
		if(list.size() > 0)
			return true;
		return false;
	}
}
