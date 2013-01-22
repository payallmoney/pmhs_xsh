package cn.net.tongfang.framework.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.net.tongfang.framework.security.vo.SystemInformation;

public class EncryUtils extends HibernateDaoSupport implements
		ApplicationListener, ApplicationContextAware {
	private boolean needEncry;

	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			refresh();
		}
	}
	public void setApplicationContext(ApplicationContext ac) {
		//帮助cn.net.tongfang.framework.util.EncryptionUtils初始化ApplicationContext
		EncryptionUtils.setApplicationContext(ac);
	}

	private void refresh() {
		SystemInformation sysInfo = (SystemInformation) getHibernateTemplate()
				.get(SystemInformation.class, 4);
		if (sysInfo == null) {
			needEncry = false;
		} else {
			String val = sysInfo.getVal();
			if (val.equals("true")) {
				needEncry = true;
			} else {
				needEncry = false;
			}
		}
	}

	public boolean isNeedEncry() {
		return needEncry;
	}

}
