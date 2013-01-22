package cn.net.tongfang.web.service.cache;

import java.util.List;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import cn.net.tongfang.framework.util.CacheUtil;

public class CacheService {
	private List<CacheUtil> cacheList;


	public void refresh() {
		for(CacheUtil util :cacheList){
			util.refresh();
		}
	}
	
	
	public void clearSession() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		System.out.println("auth is " + auth);
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	public List<CacheUtil> getCacheList() {
		return cacheList;
	}


	public void setCacheList(List<CacheUtil> cacheList) {
		this.cacheList = cacheList;
	}
	

}
