package cn.net.tongfang.web.service;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.NullableType;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;

import cn.net.tongfang.framework.security.SecurityManager;
import cn.net.tongfang.framework.security.bo.HealthFileQry;
import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.BasicInformation;
import cn.net.tongfang.framework.security.vo.ExamBaseinfo;
import cn.net.tongfang.framework.security.vo.ExamExamcfgFileno;
import cn.net.tongfang.framework.security.vo.FirstVistBeforeBorn;
import cn.net.tongfang.framework.security.vo.GravidityKey;
import cn.net.tongfang.framework.security.vo.HealthFile;
import cn.net.tongfang.framework.security.vo.PersonalInfo;
import cn.net.tongfang.framework.security.vo.SamModule;
import cn.net.tongfang.framework.security.vo.SamModuleCategory;
import cn.net.tongfang.framework.security.vo.SamTaxempcode;
import cn.net.tongfang.framework.security.vo.SamTaxorgcode;
import cn.net.tongfang.framework.security.vo.VisitBeforeBorn;
import cn.net.tongfang.framework.util.CommonConvertUtils;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.framework.util.SystemInformationUtils;
import cn.net.tongfang.framework.util.service.vo.CatInfo;
import cn.net.tongfang.framework.util.service.vo.PagingParam;
import cn.net.tongfang.framework.util.service.vo.PagingResult;
import cn.net.tongfang.web.service.bo.VisitBeforeBornBO;

public class VisitBeforeBornService extends
		HealthMainService<VisitBeforeBornBO> {
	private SystemInformationUtils sysInfo;
	private WomanLastMedicalExamRecordService womanRocordService;
	private static Map freemap = new HashMap();
	private static String freecodes[] = { "bloodroutine", "bloodSugar",
			"bloodTypeAbo", "bloodTypeRh", "bscan", "hepatitisB", "hiv",
			"kidney", "liver", "syphilis", "urineroutine", "vaginalfluid" };
	private static String freenames[] = { "血常规", "血糖", "abo血型", "rh血型", "B超",
			"乙肝", "艾滋病", "肾功", "肝功", "梅毒", "尿常规", "阴道分泌物" };
	static {
		freemap.put("bloodroutine", "血常规");
		freemap.put("bloodSugar", "血糖");
		freemap.put("bloodTypeAbo", "abo血型");
		freemap.put("bloodTypeRh", "rh血型");
		freemap.put("bscan", "B超");
		freemap.put("hepatitisB", "乙肝");
		freemap.put("hiv", "艾滋病");
		freemap.put("kidney", "肾功");
		freemap.put("liver", "肝功");
		freemap.put("syphilis", "梅毒");
		freemap.put("urineroutine", "尿常规");
		freemap.put("vaginalfluid", "阴道分泌物");
	}

	public void setSysInfo(SystemInformationUtils sysInfo) {
		this.sysInfo = sysInfo;
	}

	public void setWomanRocordService(
			WomanLastMedicalExamRecordService womanRocordService) {
		this.womanRocordService = womanRocordService;
	}

	@Override
	public String save(VisitBeforeBornBO data) throws Exception {
		data.setFileNo(EncryptionUtils.encry(data.getFileNo()));
		if (sysInfo.hasHealthFileMaternal(data.getForeignId()) == null) {
			throw new Exception("请先建立孕产妇保健手册。");
		}
		if (data.getItem().equals(2)) {
			FirstVistBeforeBorn fvb = sysInfo.hasFirstVistBeforeBorn(
					data.getForeignId(), data.getGravidity());
			if (fvb != null) {
				Timestamp preVisitDate = fvb.getVisitDate();
				Timestamp CurrentVisitDate = data.getVisitDate();
				long day = CommonConvertUtils.compareDate(preVisitDate,
						CurrentVisitDate);
				if (day < 28)
					throw new Exception("第2次产前随访与第1次产前随访的时间间隔至少为4周。");
			}
		}

		if (data.getHighRisk().equals("是")) {
			womanRocordService.save(data.getFileNo(), data.getVisitDate(),
					data.getHighRiskRemark(), 1);
		} else if (data.getHighRisk().equals("否")) {
			if (womanRocordService.get(data.getFileNo())) {
				womanRocordService.update(data.getFileNo(), 0);
			}
		}

		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		data.setExecDistrictNum(user.getDistrictId());
		return save_(data);
	}

	@Override
	public Object get(VisitBeforeBornBO data) throws Exception {
		data = (VisitBeforeBornBO) get_(data);
		;
		data.setFileNo(EncryptionUtils.decipher(data.getFileNo()));
		return data;
	}

	public VisitBeforeBornBO getPrintInfo(VisitBeforeBornBO data) {
		try {
			data = (VisitBeforeBornBO) get(data);
			Integer items = data.getItem();
			if (items > 5) {
				return null;
			}
		} catch (Exception ex) {
			return null;
		}
		return data;
	}

	public Map<String, Object> getPrintInfo_new(VisitBeforeBornBO data) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			data = (VisitBeforeBornBO) get(data);
			// HealthFile a, PersonalInfo b, VisitBeforeBorn c, SamTaxempcode
			// d,SamTaxorgcode
			HealthFile file = (HealthFile) getHibernateTemplate().get(
					HealthFile.class, data.getFileNo());
			map.put("file", file);
			PersonalInfo person = (PersonalInfo) getSession()
					.createQuery("from PersonalInfo where fileno=?")
					.setParameter(0, EncryptionUtils.encry(data.getFileNo()))
					.list().get(0);
			getSession().evict(person);
			person.setIdnumber(EncryptionUtils.decipher(person.getIdnumber()));
			map.put("person", person);
			map.put("visit", data);
			SamTaxempcode samTaxempcode = (SamTaxempcode) getHibernateTemplate()
					.get(SamTaxempcode.class, data.getInputPersonId());
			map.put("samTaxempcode", samTaxempcode);
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) getHibernateTemplate()
					.get(SamTaxorgcode.class, samTaxempcode.getOrgId());
			map.put("org", samTaxorgcode);
			String beforeBornDirect = getPrintBasicInfo(data.getId(),
					"BeforeBornDirect", "beforeBornDirectId",
					"visitBeforeBornId");
			map.put("beforeBornDirect", beforeBornDirect);
			Integer items = data.getItem();
			if (items > 5) {
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return map;
	}

	public String getPrintBasicInfo(String id, String tableName, String key,
			String tableKey) {
		String hql = "From BasicInformation A," + tableName
				+ " B Where A.id = B." + key + " And B." + tableKey + " = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, id);
		List list = query.list();
		String ret = "未测";
		if (list.size() > 0) {
			ret = "";
			for (Object objs : list) {
				Object[] obj = (Object[]) objs;
				BasicInformation basicInformation = (BasicInformation) obj[0];
				ret = ret + basicInformation.getName() + ",";
			}
			if (!ret.equals(""))
				ret = ret.substring(0, ret.length() - 1);
		}
		return ret;
	}

	public String getItems(String fileNo) {
		List list = getHibernateTemplate()
				.find("From VisitBeforeBorn A Where A.foreignId = ? Order By item ASC",
						fileNo);
		String result = "";
		if (list.size() > 0) {
			for (Object obj : list) {
				// Object[] objs = (Object[])obj;
				VisitBeforeBorn visitBeforeBorn = (VisitBeforeBorn) obj;
				result += visitBeforeBorn.getItem() + ",";
			}
		}
		if (!result.equals(""))
			result = result.substring(0, result.length() - 1);
		return result;
	}

	public String getLastMensesAndDay(String fileNo, Integer item) {
		List list = getHibernateTemplate().find(
				"From FirstVistBeforeBorn Where fileNo = ?",
				EncryptionUtils.encry(fileNo));
		if (list.size() > 0) {
			FirstVistBeforeBorn firstVistBeforeBorn = (FirstVistBeforeBorn) list
					.get(0);
			if (firstVistBeforeBorn.getLastMenses() != null) {
				Calendar cal = CommonConvertUtils
						.timestampToCalendar(firstVistBeforeBorn
								.getLastMenses());
				if (item.equals(2) || item.equals(3)) {
					cal.add(Calendar.DATE, (item - 1) * 30 + 140);
					return CommonConvertUtils.dateToString(cal.getTime());
				} else if (item >= 4 && item <= 6) {
					cal.add(Calendar.DATE, (item - 3) * 14 + 210);
					return CommonConvertUtils.dateToString(cal.getTime());
				} else {
					cal.add(Calendar.DATE, (item - 6) * 7 + 252);
					return CommonConvertUtils.dateToString(cal.getTime());
				}
			}
		}
		return null;
	}

	// 检查主表是否有数据,如果没有,则新增记录
	public void checkHasFree(final String fileno, final String examcode) {
		List list = getHibernateTemplate().find(
				" select 1 From FreeMain Where fileNo = '" + fileno
						+ "' and examname = '" + examcode + "'");
		if (list.size() <= 0) {
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					return session
							.createSQLQuery(
									"insert into free_main select '"
											+ fileno
											+ "','"
											+ examcode
											+ "', maxnum , maxnum from free_code where code='"
											+ examcode + "'").executeUpdate();
				}

			});
		}
	}

	// 检查是否超出了上限了
	public boolean checkcanfree(String fileno, String examcode) {
		checkHasFree(fileno, examcode);
		List list = getHibernateTemplate().find(
				" select 1 From FreeMain Where fileNo = '" + fileno
						+ "' and examname = '" + examcode + "' and leftnum>0");
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List checkALLcanfree(String fileno, String[] codelist) {
		List msglist = new ArrayList();
		for (int i = 0; i < codelist.length; i++) {
			checkHasFree(fileno, codelist[i]);
			List list = getHibernateTemplate().find(
					" select 1 From FreeMain Where fileNo = '" + fileno
							+ "' and examname = '" + codelist[i]
							+ "' and leftnum>0");
			if (list.size() <= 0) {
				msglist.add(freemap.get(codelist[i]) + "已达到免费数量上限!");
			}
		}
		return msglist;
	}

	public List checkEditfree(String fileno, String examid, String[] codelist,
			final boolean[] values) {
		List msglist = new ArrayList();
		for (int i = 0; i < codelist.length; i++) {
			checkHasFree(fileno, codelist[i]);
			if (values[i]) {
				List sublist = getHibernateTemplate().find(
						" select 1 From FreeSub Where fileNo = '" + fileno
								+ "' and examname = '" + codelist[i]
								+ "' and examid = '" + examid
								+ "' and status = 1");
				if (sublist.size() > 0) {
					continue;
				} else {
					List list = getHibernateTemplate().find(
							" select 1 From FreeMain Where fileNo = '" + fileno
									+ "' and examname = '" + codelist[i]
									+ "' and leftnum>0");
					if (list.size() <= 0) {
						msglist.add(freemap.get(codelist[i]) + "已达到免费数量上限!");
					}
				}
			}
		}
		return msglist;
	}
	
	public List loadFree(String fileno , String examid){
		return getHibernateTemplate().find(
				" select examname From FreeSub Where fileNo = '" + fileno
				+ "' and examid = '" + examid
				+ "' and status = 1");
	}

	// 保存
	public Object updateFrees(final String fileno, final String[] examcodes,
			final String examid, final boolean[] values) {
		final String userid = SecurityManager.currentOperator().getUsername();
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				for (int i = 0; i < examcodes.length; i++) {
					List sublist = getHibernateTemplate().find(
							" select 1 From FreeSub Where fileNo = '" + fileno
									+ "' and examname = '" + examcodes[i]
									+ "' and examid = '" + examid
									+ "' and status = 1");
					if ((values[i] && sublist.size() > 0)
							|| (!values[i] && sublist.size() <= 0)) {
						continue;
					} else if (values[i] && sublist.size() <= 0) {
						String id = UUID.randomUUID().toString();
						session.createSQLQuery(
								"insert into free_sub values('" + id + "','"
										+ fileno + "','" + examcodes[i] + "','"
										+ examid + "',getDate(),'" + userid
										+ "',1)").executeUpdate();
						session.createSQLQuery(
								"update free_main set leftnum = leftnum -1 Where fileNo = '"
										+ fileno + "' and examname = '"
										+ examcodes[i] + "' ").executeUpdate();
					} else if (!values[i] && sublist.size() > 0) {
						session.createSQLQuery(
								"delete free_sub where fileno = '" + fileno
										+ "' and examname='" + examcodes[i]
										+ "' and examid = '" + examid
										+ "' and status = 1").executeUpdate();
						session.createSQLQuery(
								"update free_main set leftnum = leftnum +1 Where fileNo = '"
										+ fileno + "' and examname = '"
										+ examcodes[i] + "' ").executeUpdate();
					}
				}
				return null;
			}
		});
	}

	// 保存
	public Object saveFrees(final String fileno, final String[] examcodes,
			final String examid) {
		final String userid = SecurityManager.currentOperator().getUsername();
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				for (int i = 0; i < examcodes.length; i++) {
					final String examcode = examcodes[i];
					checkHasFree(fileno, examcode);
					String finalid = examid;
					String id = UUID.randomUUID().toString();
					session.createSQLQuery(
							"insert into free_sub values('" + id + "','"
									+ fileno + "','" + examcode + "','"
									+ finalid + "',getDate(),'" + userid
									+ "',1)").executeUpdate();
					session.createSQLQuery(
							"update free_main set leftnum = leftnum -1 Where fileNo = '"
									+ fileno + "' and examname = '" + examcode
									+ "' ").executeUpdate();
				}
				return null;
			}
		});
	}

	public Map querFreeList(String examname, String userdistrict,
			Map<String, Map> params, Map<String, Map<String, String>> basemap,
			List<String> collist) throws Exception {

		// 计算总数,页数
		String countsql = " select count(*) from free_main ";
		SQLQuery countquery = this.getSession().createSQLQuery(countsql);
		List countlist = countquery.list();
		int allcount = 0;
		if (countlist.size() > 0) {
			allcount = (Integer) countlist.get(0);
		}
		Map pageparams = params.get("page");
		int pagesize = 20;
		int currentpage = Integer.parseInt((String) pageparams.get("pagesize"));
		int pages = allcount / pagesize + (allcount % pagesize > 0 ? 1 : 0);
		if (currentpage > pages) {
			currentpage = pages;
		}
		if (currentpage <= 0) {
			currentpage = 1;
		}
		int min = pagesize * (currentpage - 1);
		int max = pagesize * currentpage;
		// 查询分页结果
		SQLQuery query = this.getSession().createSQLQuery(
				" select * from free_main ");
		query.setFirstResult(min);
		query.setMaxResults(max);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map ret = new HashMap();
		List retlist = query.list();
		ret.put("rows", retlist);
		ret.put("currentpage", currentpage);
		ret.put("total", allcount);
		ret.put("pages", pages);
		return ret;
	}
}
