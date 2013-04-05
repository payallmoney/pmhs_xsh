package cn.net.tongfang.framework.util;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.SQLServerDialect;

public class SqlserverDialect extends SQLServerDialect{
	public SqlserverDialect() {
        super();
        System.out.println("===========Hibernate.STRING.getName()========"+Hibernate.STRING.getName());
//        registerColumnType(Types.NVARCHAR, Hibernate.STRING.getName());
//        registerColumnType(Types.NVARCHAR, "nvarchar");
        registerColumnType(Types.VARCHAR, "nvarchar($l)");
        registerColumnType(Types.VARCHAR, "nvarchar(max)");
    }
}
