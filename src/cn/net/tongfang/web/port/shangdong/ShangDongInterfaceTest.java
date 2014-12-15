package cn.net.tongfang.web.port.shangdong;

import java.util.HashMap;
import java.util.Map;



import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:D:/workspace/pmhs_km/webapp/WEB-INF/hbt-test.xml" })

public class ShangDongInterfaceTest extends HibernateDaoSupport {
	@Autowired
	ShangDongInterface intface;
	
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	@Test
	public void testHealthfile() throws Exception{
		System.out.println("============"+"abc".split("\\.").length);
		Map<String , Object> params = new HashMap<String , Object>();
//		params.put("createdDateStart", "2014-06-16 00:00:00");
//		params.put("createdDateEnd", "2014-06-16 00:00:00");
//		params.put("doctor","佟医生");
		Map<String,Integer> pagePara = new HashMap<String ,Integer>();
		pagePara.put("pageIndex", 1);
		pagePara.put("pageSize", 10);
//		pagePara.put("recordStart", 1000);
		params.put("pagePara", pagePara);
		String json = intface.postInterface("shandong","healthfile", "/api/baseinfo/list_baseinfo",params, getHibernateTemplate());
		Gson gs = new Gson();
		System.out.println("===json========="+json);
		Map ret =  gs.fromJson(json, HashMap.class);
		Assert.assertEquals(ret.get("respCode"), "0");
		
	}
//	@Test
//	public void testHealthExam() throws Exception{
//		Gson gs = new Gson();
//		Map<String , Object> params1 = new HashMap<String , Object>();
////		params1.put("createdDateStart", "2014-06-16 00:00:00");
////		params1.put("createdDateEnd", "2014-06-16 00:00:00");
////		params1.put("doctor","佟医生");
//		Map<String,Integer> pagePara1 = new HashMap<String ,Integer>();
//		pagePara1.put("pageIndex", 1);
//		pagePara1.put("pageSize", 2);
////		pagePara1.put("recordStart", 1000);
//		params1.put("pagePara", pagePara1);
//		String json = intface.postInterface("shandong","healthexam", "/api/physical/list_physical",params1, getHibernateTemplate());
//		Map ret1 =  gs.fromJson(json, HashMap.class);
//		System.out.println("===json===list_physical======"+json);
//		Assert.assertEquals(ret1.get("respCode"), "0");
//	}
//	
//	@Test
//	public void testHealthfileExec() throws Exception{
//		intface.exec("shandong",getHibernateTemplate());
//	}
}
