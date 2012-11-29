package cn.net.tongfang.web.service.score;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.reflect.MethodUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.support.RequestHandledEvent;

import cn.net.tongfang.framework.security.vo.BasicInformation;
import cn.net.tongfang.framework.security.vo.CodScoreProp;
import cn.net.tongfang.framework.security.vo.CodScoreRule;
import cn.net.tongfang.framework.security.vo.ScoreGroup;
import cn.net.tongfang.framework.security.vo.ScorePerson;

public class ScoreUtil extends HibernateDaoSupport implements
		ApplicationContextAware , ApplicationListener {
	private static final Logger log = Logger.getLogger(ScoreUtil.class);
	private Map<String, Map> nameMaps = new HashMap<String, Map>();
	private static String ScoreName = "12月培训考试";
	private Map<Integer, List<BasicInformation>> basicInformationMap = new HashMap<Integer, List<BasicInformation>>();

	private ApplicationContext ac;

	public void setApplicationContext(ApplicationContext ac) {
		this.ac = ac;
	}
	
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			refresh();
		}
	}


	public void refresh() {
		nameMaps.clear();
		getScoreDetailItem();
		getScoreRule();
		getScorePerson();
		getScoreGroup();
		getStandard();
		buildBasicInformationMap();
		System.out.println("============初始化成功=======");
	}

	/**
	 * 得到考试科目的详细字段
	 */
	private void getScoreDetailItem() {
		Map<String, Map<String, CodScoreProp>> map = new HashMap<String, Map<String, CodScoreProp>>();
		nameMaps.put("CodScoreProp", map);
		List<CodScoreProp> list = getSession().createQuery("from CodScoreProp")
				.list();
		for (CodScoreProp cod : list) {
			Map<String, CodScoreProp> namemap;
			if (map.containsKey(cod.getId().getName())) {
				namemap = map.get(cod.getId().getName());
			} else {
				namemap = new HashMap();
				map.put(cod.getId().getName(), namemap);
			}
			namemap.put(cod.getId().getProp(), cod);
		}
	}

	/**
	 * 得到科目的取数配置
	 */
	private void getScoreRule() {
		Map<String, CodScoreRule> map = new HashMap<String, CodScoreRule>();
		nameMaps.put("CodScoreRule", map);
		List<CodScoreRule> list = getSession()
				.createQuery("from CodScoreRule ").list();
		for (CodScoreRule cod : list) {
			map.put(cod.getName(), cod);
		}
	}

	/**
	 * 得到考试的参与人员
	 */
	private void getScorePerson() {
		Map<String, Map<String, ScorePerson>> map = new HashMap<String, Map<String, ScorePerson>>();
		nameMaps.put("ScorePerson", map);
		List<ScorePerson> list = getSession().createQuery("from ScorePerson ")
				.list();
		for (ScorePerson cod : list) {
			Map<String, ScorePerson> namemap;
			if (map.containsKey(cod.getId().getScorename())) {
				namemap = map.get(cod.getId().getScorename());
			} else {
				namemap = new HashMap();
				map.put(cod.getId().getScorename(), namemap);
			}
			namemap.put(cod.getId().getPersonid(), cod);
		}
	}

	/**
	 * 考试_科目分组
	 */
	private void getScoreGroup() {
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		nameMaps.put("ScoreGroup", map);
		List<ScoreGroup> list = getSession().createQuery(
				"from ScoreGroup order by ord").list();
		for (ScoreGroup cod : list) {
			Map<String, String> namemap;
			if (map.containsKey(cod.getId().getGroupname())) {
				namemap = map.get(cod.getId().getGroupname());
			} else {
				namemap = new HashMap();
				map.put(cod.getId().getGroupname(), namemap);
				System.out.println("=groupname=" + cod.getId().getGroupname());
			}
			System.out.println("==" + cod.getId().getItem());
			namemap.put(cod.getId().getItem(), cod.getId().getItem());
		}
	}

	public Object getScoreSetting(String group, String name) {
		return nameMaps.get(group).get(name);
	}
	
	public Object getScoreSetting(String group) {
		return nameMaps.get(group);
	}

	public String getScoreName() {
		return ScoreName;
	}

	private void buildBasicInformationMap() {
		final String hql = "select p from BasicInformation p where isMain = 0 order by type, number";
		Map<Integer, List<BasicInformation>> resMap = null;
		List<BasicInformation> res = getSession().createQuery(hql).list();
		resMap = new HashMap<Integer, List<BasicInformation>>();
		for (BasicInformation i : res) {
			int type = i.getType();
			List<BasicInformation> info = resMap.get(type);
			if (info == null) {
				info = new ArrayList<BasicInformation>();
				resMap.put(type, info);
			}
			info.add(i);
			// System.out.println("==============basicinfo====="+i);
		}
		basicInformationMap = resMap;
	}

	private void getStandard() {
		Map<String, Object> standardMap = new HashMap<String, Object>();
		Map<String, Map> all = (Map) getScoreSetting("ScoreGroup", "所有");
		for (Iterator iter = all.keySet().iterator(); iter.hasNext();) {
			String item = (String) iter.next();
			// 根据项目名称得到项目的获取方式
			CodScoreRule rule = (CodScoreRule) getScoreSetting("CodScoreRule", item);
			if (rule == null) {
				log.error("ScoreGroup中的考试名称:" + item + " 与CodScoreRule表配置不相符");
				continue;
			}
			String[] fun = rule.getMethod().split("\\.");
			Object obj = ac.getBean(fun[0]);
			try {
				Method exeMethod = getExeMethod(obj, fun[1]);
				Object bo1 = exeMethod.getParameterTypes()[0].newInstance();
				setObjectValue(bo1, rule.getParamurl(), rule.getStandard());
				Object res1;
				try {
					res1 = MethodUtils.invokeMethod(obj, fun[1], bo1);
					standardMap.put(item, res1);
				} catch (Exception ex) {
					log.error("CodScoreRule中的考试[" + item + "]的标准答案["
							+ rule.getStandard() + "]在数据库中不存在!请与系统管理员联系!");
					continue;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		nameMaps.put("standardMap", standardMap);
	}

	public Map<Integer, List<BasicInformation>> getBasicInformationMap() {
		return basicInformationMap;
	}
	
	public static Method getExeMethod(Object obj, String methodname) {
		Method[] methods = ClassUtils.getUserClass(obj).getMethods();
		Method exeMethod = null;
		for (Method me : methods) {
			if (me.getName().equals(methodname)) {
				if (me.getParameterTypes()[0].getName().startsWith(
						"cn.net.tongfang.") || me.getParameterTypes()[0].getName().equals("java.lang.String")) {
					exeMethod = me;
					break;
				}
			}
		}
		return exeMethod;
	}
	
	public static void setObjectValue(Object obj, String paramurl, String value) {
		String[] parms = paramurl.split("\\.");
		if (parms.length == 1) {
			try {
				if(obj instanceof String){
					obj = value;
				}else{
					PropertyUtils.setProperty(obj, parms[0], value);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				log.error("CodScoreRule表中的paramurl配置错误!" + parms[0]
						+ "属性不存在或类型错误!");
			}
		} else {
			try {
				Object child = PropertyUtils.getPropertyType(obj, parms[0])
						.newInstance();
				setObjectValue(child,
						paramurl.substring(parms[0].length() + 1), value);
				PropertyUtils.setProperty(obj, parms[0], child);
			} catch (Exception ex) {
				ex.printStackTrace();
				log.error("CodScoreRule表中的paramurl配置错误!" + parms[0]
						+ "属性不存在或类型错误!");
			}

		}
	}

}