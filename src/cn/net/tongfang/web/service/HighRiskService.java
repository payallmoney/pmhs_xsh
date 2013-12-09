package cn.net.tongfang.web.service;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HighRiskService extends HibernateDaoSupport {
	public List getException(String type,String filterVal){
		type = type.equals("0") ? "儿童" : "妇女";
		String sql = "From HighRisk Where type = ? And (number = ? Or exception Like ? Or exceptionPng Like ?)";
		List list = getHibernateTemplate().find(sql,new Object[]{type,filterVal,"%" + filterVal + "%","%" + filterVal + "%"});
		if(list.size() > 0)
			return list;
		return null;
	}
}
