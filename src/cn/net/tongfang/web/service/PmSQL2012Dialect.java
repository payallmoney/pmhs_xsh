package cn.net.tongfang.web.service;

import java.sql.Types;
import org.hibernate.Hibernate;
import org.hibernate.dialect.SQLServerDialect;

public class PmSQL2012Dialect extends SQLServerDialect {
	public PmSQL2012Dialect() {
		super();
		registerHibernateType(1, "string");   
        registerHibernateType(-9, "string");   
        registerHibernateType(-16, "string");   
        registerHibernateType(3, "double");   
	}
}