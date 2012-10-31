package cn.net.tongfang.web.util;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import cn.net.tongfang.framework.security.vo.BasicInformation;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CacheUtil implements ApplicationContextAware {  
    private static ApplicationContext appCtx;  
    /** 
     * 此方法可以把ApplicationContext对象inject到当前类中作为一个静态成员变量。 
     * @param applicationContext ApplicationContext 对象. 
     * @throws BeansException 
     */  
    @Override  
    public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {  
        appCtx = applicationContext;  
    }  
    /** 
     * 这是一个便利的方法，帮助我们快速得到一个BEAN 
     * @param beanName bean的名字 
     * @return 返回一个bean对象 
     */  
    public static Object getBean( String beanName ) {  
        return appCtx.getBean( beanName );  
    }  

	public static Object getCache(String key){
		CacheManager ehCacheManager = (CacheManager)getBean("ehCacheManager");
		Cache cache = ehCacheManager.getCache("codeCache");
		Element element = cache.get(key);
		return element.getObjectValue();
	}
	
	public static void putCache(String key,Object obj){
		CacheManager ehCacheManager = (CacheManager)getBean("ehCacheManager");
		Cache cache = ehCacheManager.getCache("codeCache");
		Element element = new Element(key, obj);
		cache.put(element);
	}
	
	public static boolean hasCache(String key){
		CacheManager ehCacheManager = (CacheManager)getBean("ehCacheManager");
		Cache cache = ehCacheManager.getCache("codeCache");
		return cache.isKeyInCache(key);
	}
	
}
