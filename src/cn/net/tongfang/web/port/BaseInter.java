package cn.net.tongfang.web.port;

import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

public interface BaseInter {
	public void exec(String interfacename,HibernateTemplate hbt);
}
