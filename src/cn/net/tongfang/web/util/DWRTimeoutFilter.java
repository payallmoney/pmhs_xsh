package cn.net.tongfang.web.util;

import java.lang.reflect.Method;

import org.directwebremoting.AjaxFilterChain;

import cn.net.tongfang.framework.security.SecurityManager;
import cn.net.tongfang.framework.security.demo.service.TaxempDetail;

public class DWRTimeoutFilter implements org.directwebremoting.AjaxFilter {
	public Object doFilter(Object obj, Method method, Object[] params, AjaxFilterChain chain) throws Exception {
		System.out.println("===================" + params);
		TaxempDetail emp = SecurityManager.currentOperator();
		if (emp == null) {
			throw new TimeoutException("登录超时");
		}
		return chain.doFilter(obj, method, params);
	}
}