package cn.net.tongfang.framework.util;
import java.util.List;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.net.tongfang.framework.security.vo.CodModuleMap;

public class ModuleUtil extends HibernateDaoSupport implements ApplicationListener,CacheUtil {
	private List<CodModuleMap> moduleList;
	
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			refresh();
		}
	}
	
	public void refresh() {
		List<CodModuleMap> lists = getSession().createQuery(
				"from CodModuleMap ").list();
		moduleList = lists;
		System.out.println("===============ModuleUtil==刷新结束==");
	}

	public List<CodModuleMap> getModuleList() {
		return moduleList;
	}
	
}