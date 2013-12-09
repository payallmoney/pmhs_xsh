package cn.net.tongfang.web.service.score;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.reflect.MethodUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;

import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.BasicInformation;
import cn.net.tongfang.framework.security.vo.CodScoreProp;
import cn.net.tongfang.framework.security.vo.CodScoreRule;
import cn.net.tongfang.framework.security.vo.CodTelUpdateCol;
import cn.net.tongfang.framework.security.vo.SamTaxempcode;
import cn.net.tongfang.framework.security.vo.ScoreExamdate;
import cn.net.tongfang.framework.security.vo.ScorePerson;
import cn.net.tongfang.framework.security.vo.ScoreResults;
import cn.net.tongfang.framework.security.vo.ScoreResultsDetail;
import cn.net.tongfang.framework.security.vo.ScoreResultsDetailId;
import cn.net.tongfang.framework.security.vo.ScoreResultsId;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.framework.util.service.vo.PagingResult;

public class ScoreService extends HibernateDaoSupport implements
		ApplicationContextAware {
	private static final Logger log = Logger.getLogger(ScoreService.class);
	private ScoreUtil scoreUtil;
	private ApplicationContext ac;

	public void setApplicationContext(ApplicationContext ac) {
		this.ac = ac;
	}


	public Map<Integer, List<BasicInformation>> basicInformationMap() {
		return scoreUtil.getBasicInformationMap();
	}

	// 查询分数
	public List queryScore(Map param) {
		String sql = " select a.username,b.id.scorename,b.id.personid,case when b.allcount = null then 0 else b.allcount end  as allcount  from SamTaxempcode a, ScorePerson b "
				+ "where a.loginname = b.id.personid and b.examgroup = '"
				+ param.get("group") + "'" + "order by b.allcount desc ";
		List idlist = this.getHibernateTemplate().find(sql);

		return idlist;
	}

	// 生成分数规则
	public List queryGroup() {
		List ret = getHibernateTemplate().find(
						"select groupname as code,groupname as name,scoredate from ScoreExamdate order by scoredate desc ")
				;
		return ret;
	}


	// 生成分数规则
	@Transactional(propagation = Propagation.REQUIRED)
	public PagingResult<Map> getScore(Map param) {
		String examdate = "";
		if (param != null && param.containsKey("examdate")) {
			examdate = (String) param.get("examdate");
		} else {
			examdate = (new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
					.toString();
		}
		// 标准答案filno
		Map<String, Object> standardMap = (Map) scoreUtil
				.getScoreSetting("standardMap");
		// 得到考试名称
		Map<String, Object[]> persons = (Map) scoreUtil.getScoreSetting(
				"ScorePerson", (String) param.get("group"));
		List<ScorePerson> personsList = new ArrayList<ScorePerson>();
		List<Map> retList = new ArrayList<Map>();
		for (Iterator it = persons.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			Object[] personobjs = persons.get(key);
			ScorePerson person = (ScorePerson) personobjs[0];
			SamTaxempcode emp = (SamTaxempcode) personobjs[1];
			String scorestr="";
			personsList.add(person);
			// 根据考试名称得到考试项目分组
			Map<String, Map> items = (Map) scoreUtil.getScoreSetting(
					"ScoreGroup", person.getExamgroup());
			List detailList = new ArrayList();
			Map allscore = new HashMap();
			for (Iterator iter = items.keySet().iterator(); iter.hasNext();) {
				String item = (String) iter.next(); // 考试名称
				// 根据项目分组得到项目明细
				Map<String, CodScoreProp> detailsitem = (Map) scoreUtil
						.getScoreSetting("CodScoreProp", item);
				if (detailsitem == null) {
					log.error("【异常】:" + item + "的detailsitem未进行配置!");
					continue;
				}
				// 根据项目名称得到项目的获取方式
				CodScoreRule rule = (CodScoreRule) scoreUtil.getScoreSetting(
						"CodScoreRule", item);
				if (rule == null) {
					log.error("【异常】:CodScoreRule中配置的name与ScoreGroup中配置的item不同");
					continue;
				}
				String id = getIds(person, rule, examdate);
				if (id == null) {
					allscore.put(item, null);
				} else {
					String[] fun = rule.getMethod().split("\\.");
					Object obj = ac.getBean(fun[0]);
					Map scores = new HashMap();
					try {
						Method exeMethod = ScoreUtil.getExeMethod(obj, fun[1]);
						if (exeMethod == null) {
							log.error("【异常】:CodScoreRule中配置的method" + fun[1]
									+ "不存在!");
							continue;
						}
						Object bo2 = exeMethod.getParameterTypes()[0]
								.newInstance();
						ScoreUtil.setObjectValue(bo2, rule.getParamurl(), id);
						Object res2 = MethodUtils
								.invokeMethod(obj, fun[1], bo2);

						Object standard = standardMap.get(item);
						if (standard == null) {
							log.error("CodScoreRule的:" + item
									+ "的考试答案standard配置查不到数据!");
							continue;
						}
						Map map1 = BeanUtils.describe(standard);
						for (Iterator scoreiter = map1.keySet().iterator(); scoreiter
								.hasNext();) {
							String detailitem = (String) scoreiter.next();
							if (detailsitem.containsKey(detailitem)) {
								Object values1 = getValue(standard, detailitem);
								Object values2 = getValue(res2, detailitem);
								CodScoreProp detail = detailsitem
										.get(detailitem);
								int value = 0;
								// TODO 这里做打分比较
								if (detail.getPropother() != null
										&& !detail.getPropother().trim()
												.isEmpty()) {
									Object othervalues1 = getValue(standard,
											detail.getPropother());
									Object othervalues2 = getValue(res2,
											detail.getPropother());
									if (getComparedRes(othervalues1,
											othervalues2) == 0) {
										value = 0;
									} else {
										value = getComparedRes(values1, values2);
									}
								} else {
									value = getComparedRes(values1, values2);
								}
								scores.put(detailitem, value);
							}
						}
						allscore.put(item, scores);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
			scorestr = saveScores((String) param.get("group"), person, allscore, detailList);
			try {
				Map retmap = BeanUtils.describe(person);
				retmap.putAll(BeanUtils.describe(person.getId()));
				retmap.put("empname", emp.getUsername());
				retmap.put("scorestr", scorestr);
				retList.add(retmap);
				// retList.addAll(detailList);
			} catch (NoSuchMethodException ex) {
				ex.printStackTrace();
			} catch (InvocationTargetException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
		return new PagingResult<Map>(retList.size(), retList);
	}

	// 生成分数规则
	@Transactional(propagation = Propagation.REQUIRED)
	public Map getPersonScore(Map param) {
		String examdate = "";
		Map retmap=null;
		if (param != null && param.containsKey("examdate")) {
			examdate = (String) param.get("examdate");
		} else {
			examdate = (new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
					.toString();
		}
		// 标准答案filno
		Map<String, Object> standardMap = (Map) scoreUtil
				.getScoreSetting("standardMap");
		// 得到考试名称
		Map<String, Object[]> persons = (Map) scoreUtil.getScoreSetting(
				"ScorePerson", (String) param.get("group"));
		List<ScorePerson> personsList = new ArrayList<ScorePerson>();
		String key = (String) param.get("personid");
		Object[] personobjs = persons.get(key);
		ScorePerson person = (ScorePerson) personobjs[0];
		SamTaxempcode emp = (SamTaxempcode) personobjs[1];
		personsList.add(person);
		// 根据考试名称得到考试项目分组
		Map<String, Map> items = (Map) scoreUtil.getScoreSetting("ScoreGroup",
				person.getExamgroup());
		List detailList = new ArrayList();
		Map allscore = new HashMap();
		for (Iterator iter = items.keySet().iterator(); iter.hasNext();) {
			String item = (String) iter.next(); // 考试名称
			// 根据项目分组得到项目明细
			Map<String, CodScoreProp> detailsitem = (Map) scoreUtil
					.getScoreSetting("CodScoreProp", item);
			if (detailsitem == null) {
				log.error("【异常】:" + item + "的detailsitem未进行配置!");
				continue;
			}
			// 根据项目名称得到项目的获取方式
			CodScoreRule rule = (CodScoreRule) scoreUtil.getScoreSetting(
					"CodScoreRule", item);
			if (rule == null) {
				log.error("【异常】:CodScoreRule中配置的name与ScoreGroup中配置的item不同");
				continue;
			}
			String id = getIds(person, rule, examdate);
			if (id == null) {
				allscore.put(item, null);
			} else {
				String[] fun = rule.getMethod().split("\\.");
				Object obj = ac.getBean(fun[0]);
				Map scores = new HashMap();
				try {
					Method exeMethod = ScoreUtil.getExeMethod(obj, fun[1]);
					if (exeMethod == null) {
						log.error("【异常】:CodScoreRule中配置的method" + fun[1]
								+ "不存在!");
						continue;
					}
					Object bo2 = exeMethod.getParameterTypes()[0].newInstance();
					ScoreUtil.setObjectValue(bo2, rule.getParamurl(), id);
					Object res2 = MethodUtils.invokeMethod(obj, fun[1], bo2);

					Object standard = standardMap.get(item);
					if (standard == null) {
						log.error("CodScoreRule的:" + item
								+ "的考试答案standard配置查不到数据!");
						continue;
					}
					Map map1 = BeanUtils.describe(standard);
					for (Iterator scoreiter = map1.keySet().iterator(); scoreiter
							.hasNext();) {
						String detailitem = (String) scoreiter.next();
						if (detailsitem.containsKey(detailitem)) {
							Object values1 = getValue(standard, detailitem);
							Object values2 = getValue(res2, detailitem);
							CodScoreProp detail = detailsitem.get(detailitem);
							int value = 0;
							// TODO 这里做打分比较
							if (detail.getPropother() != null
									&& !detail.getPropother().trim().isEmpty()) {
								Object othervalues1 = getValue(standard,
										detail.getPropother());
								Object othervalues2 = getValue(res2,
										detail.getPropother());
								if (getComparedRes(othervalues1, othervalues2) == 0) {
									value = 0;
								} else {
									value = getComparedRes(values1, values2);
								}
							} else {
								value = getComparedRes(values1, values2);
							}
							scores.put(detailitem, value);
						}
					}
					allscore.put(item, scores);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		String scorestr = saveScores((String) param.get("group"), person, allscore, detailList);
		try {
			retmap = BeanUtils.describe(person);
			retmap.putAll(BeanUtils.describe(person.getId()));
			retmap.put("empname", emp.getUsername());
			retmap.put("scorestr", scorestr);
			// retList.addAll(detailList);
		} catch (NoSuchMethodException ex) {
			ex.printStackTrace();
		} catch (InvocationTargetException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		}
		return retmap;
	}

	private Object getValue(Object obj, String propname) throws Exception {
		Object retobj = PropertyUtils.getProperty(obj, propname);
		if (retobj == null) {
			retobj = "";
		}
		return retobj;
	}

	private String getIds(ScorePerson person, CodScoreRule rule, String examdate) {
		// TODO 根据考试人员得到考试的fileno
		String sql = " select " + rule.getQuerycolumn() + " from "
				+ rule.getQuerytable() + " where " + rule.getQuerywhere()
				+ " and " + rule.getQuerypersoncolumn() + "='"
				+ person.getId().getPersonid() + "' and "
				+ rule.getQuerydatecolumn() + " >='" + examdate + "' and "
				+ rule.getQuerydatecolumn() + " <='" + examdate
				+ " 23:59:59.999' order by " + rule.getQuerydatecolumn()
				+ " desc";
		List<String> idlist = this.getHibernateTemplate().find(sql);
		if (idlist.isEmpty()) {
			return null;
		}
		String returnid;
		if (rule.getIsfileno()) {
			returnid = EncryptionUtils.decipher(idlist.get(0));
		} else {
			returnid = idlist.get(0);
		}
		return returnid;
	}

	private int getComparedRes(Object object1, Object object2) {
		int value = 0;
		Class c1 = ClassUtils.getUserClass(object1);
		if (c1 != null && "java.util.ArrayList".equals(c1.getName())) {
			ArrayList list1 = (ArrayList) object1;
			ArrayList list2 = (ArrayList) object2;
			boolean flag = true;
			if (list2.size() == list1.size()) {
				for (int i = 0; i < list1.size(); i++) {
					if (!list2.contains(list1.get(i))) {
						flag = false;
						break;
					}
				}
				if (flag) {
					value = 1;
				}
			}
		} else {
			if (object1.equals(object2)) {
				value = 1;
			}
		}
		return value;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	private String saveScores(String scorename, ScorePerson person,
			Map allscores, List detailList) {
		String retstr = "";
		BigDecimal allcount = new BigDecimal(0);
		// 保存得分情况
		for (Iterator iter = allscores.keySet().iterator(); iter.hasNext();) {
			String item = (String) iter.next();
			Object obj = allscores.get(item);
			if (obj == null) {
				Map detailmap = new HashMap();
				detailmap.put("personid", "");
				detailmap.put("scorename", "  " + scorename);
				detailmap.put("examgroup", "  " + item);
				detailmap.put("allcount", "  0.00");
				detailmap.put("examdate",
						new java.sql.Date(new Date().getTime()));
				detailList.add(detailmap);
				retstr = retstr+item+";";
			} else {
				Map scores = (Map) obj;
				Map<String, CodScoreProp> detailsitem = (Map) scoreUtil
						.getScoreSetting("CodScoreProp", item);
				BigDecimal score = saveScores(scorename, person, item, scores,
						detailsitem);
				allcount = allcount.add(score);
				Map detailmap = new HashMap();
				detailmap.put("personid", "");
				detailmap.put("scorename", "  " + scorename);
				detailmap.put("examgroup", "  " + item);
				detailmap.put("allcount", "  " + score);
				detailmap.put("examdate",
						new java.sql.Date(new Date().getTime()));
				detailList.add(detailmap);
				if(score.equals(new BigDecimal(0))){
					retstr = retstr+item+";";
				}
//				retstr = retstr+item+":"+score.divide(new BigDecimal(detailList.size()).multiply(new BigDecimal(100)), 2,
//						RoundingMode.HALF_UP)+"分;";
			}
		}
		allcount = allcount.divide(new BigDecimal(detailList.size()), 2,
				RoundingMode.HALF_UP);
		person.setAllcount(allcount);
		person.setExamdate(new java.sql.Date(new Date().getTime()));
		saveVO(person);
		return retstr;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	private BigDecimal saveScores(String scorename, ScorePerson person,
			String item, Map scores, Map<String, CodScoreProp> detailsitem) {
		int scouresum = 0;
		BigDecimal count = new BigDecimal(scores.size());
		BigDecimal perscore = new BigDecimal(100).divide(count, 2,
				RoundingMode.HALF_UP);
		// 保存得分情况
		for (Iterator iter = scores.keySet().iterator(); iter.hasNext();) {
			String detailitem = (String) iter.next();
			ScoreResultsDetailId srdid = new ScoreResultsDetailId();
			srdid.setPersonid(person.getId().getPersonid());
			srdid.setItem(item);
			srdid.setScorename(scorename);
			srdid.setDetailitem(detailsitem.get(detailitem).getPropname());
			ScoreResultsDetail srd = new ScoreResultsDetail();
			srd.setId(srdid);
			srd.setScore(new BigDecimal((Integer) scores.get(detailitem))
					.multiply(perscore));
			scouresum += (Integer) scores.get(detailitem);
			saveVO(srd);
		}
		BigDecimal scouresumB = new BigDecimal(scouresum);
		BigDecimal res = (scouresumB.multiply(new BigDecimal(100))).divide(
				count, 2, RoundingMode.HALF_UP);
		ScoreResultsId srid = new ScoreResultsId();
		srid.setItem(item);
		srid.setPersonid(person.getId().getPersonid());
		srid.setScorename(scorename);
		ScoreResults sr = new ScoreResults();
		sr.setId(srid);
		sr.setScore(res);
		saveVO(sr);
		return res;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	private void saveVO(Object obj) {
		getHibernateTemplate().saveOrUpdate(obj);
		getHibernateTemplate().flush();
		getHibernateTemplate().evict(obj);
	}

	// 增加保存提取规则
	public String editScore(CodTelUpdateCol bo) throws Exception {
		return null;
	}

	// 删除提取规则
	public void removeScore(String modules) {
		return;
	}

	public ScoreUtil getScoreUtil() {
		return scoreUtil;
	}

	public void setScoreUtil(ScoreUtil scoreUtil) {
		this.scoreUtil = scoreUtil;
	}

}
