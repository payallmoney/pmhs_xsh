package cn.net.tongfang.web.service.score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.net.tongfang.framework.security.vo.BasicInformation;
import cn.net.tongfang.framework.security.vo.CodScoreProp;
import cn.net.tongfang.framework.security.vo.CodScoreRule;
import cn.net.tongfang.framework.security.vo.ScoreGroup;
import cn.net.tongfang.framework.security.vo.ScorePerson;

public class ScoreUtil extends HibernateDaoSupport {

	private Map<String, Map> nameMaps = new HashMap<String, Map>();
	private static String ScoreName = "12月培训考试";
	private Map<Integer, List<BasicInformation>> basicInformationMap = new HashMap<Integer, List<BasicInformation>>();

	public ScoreUtil(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
		refresh();
	}

	public void refresh() {
		nameMaps.clear();
		getScoreDetailItem();
		getScoreRule();
		getScorePerson();
		getScoreGroup();
		buildBasicInformationMap();
		System.out.println("==这里执行了几次?");
	}

	/**
	 * 得到考试科目的详细字段
	 */
	private void getScoreDetailItem() {
		Map<String, Map<String, CodScoreProp>> map = new HashMap<String, Map<String, CodScoreProp>>();
		nameMaps.put("CodScoreProp", map);
		List<CodScoreProp> list = getSession().createQuery("from CodScoreProp")
				.list();
		System.out.println("==============================");
		System.out.println("=getScoreDetailItem=" + list.size());
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
		System.out.println("==============================");
		System.out.println("=getScoreRule=" + list.size());
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
		System.out.println("==============================");
		System.out.println("=getScorePerson=" + list.size());
		for (ScorePerson cod : list) {
			Map<String, ScorePerson> namemap;
			if (map.containsKey(cod.getId().getScorename())) {
				namemap = map.get(cod.getId().getScorename());
			} else {
				namemap = new HashMap();
				map.put(cod.getId().getScorename(), namemap);
			}
			namemap.put(cod.getId().getScorename(), cod);
		}
	}

	/**
	 * 考试_科目分组
	 */
	private void getScoreGroup() {
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		nameMaps.put("ScoreGroup", map);
		List<ScoreGroup> list = getSession().createQuery("from ScoreGroup order by ord")
				.list();
		System.out.println("==============================");
		System.out.println("getScoreGroup==" + list.size());
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

	public Object getScoreMap(String group, String name) {
		return nameMaps.get(group).get(name);
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
//			System.out.println("==============basicinfo====="+i);
		}
		basicInformationMap = resMap;
	}

	public Map<Integer, List<BasicInformation>> getBasicInformationMap() {
		return basicInformationMap;
	}
	
	

}