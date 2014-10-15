package cn.net.tongfang.web.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.HealthFileHistory2;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.web.service.bo.PagedList;
import cn.net.tongfang.web.service.bo.PersonalInfoFBO;

public class FileNumSearch extends HibernateDaoSupport {
	static int pagesize = 10;

	// OtherParamType 类型
	public static String OtherParamType_Woman = "1";// 妇女

	public static String OtherParamType_Hyp = "2";// 高血压

	public static String OtherParamType_T2dm = "3";// 2型糠尿病
	public static String OtherParamType_WomanBook = "4";// 妇女建册
	public static String OtherParamType_Husband = "5";// 丈夫信息
	public static String OtherParamType_Child = "6";// 儿童
	public static String OtherParamType_ChildOther = "7";// 儿童其他
	public static String OtherParamType_Furious = "8";// 精神病
	public static String OtherParamType_Old = "9";// 老年人
	public static String OtherParamType_Child_NoCard = "10";// 儿童未建册

	public static String OtherParamType_MarryMan = "11";// 男性婚检
	public static String OtherParamType_MarryWoman = "12";// 女性婚检

	// condVal 类型
	public static String CondVal_Barcode = "0"; // 条形码
	public static String CondVal_Fileno = "1"; // 档案编号
	public static String CondVal_Name = "2"; // 姓名
	public static String CondVal_CardId = "3"; // 身份证号
	public static String CondVal_LinkMan = "4"; // 联系人

	public static String CondVal_History_Name = "0";// 姓名
	public static String CondVal_History_CardId = "1";// 身份证号

	public PagedList listCodePage(int pageNo, String mcode, boolean startWith,
			String condVal, String otherparamtype) {
		return listCodePageSize(pageNo, pagesize, mcode, startWith, condVal,
				otherparamtype);

	}
	
	public static String safeSql(String sql){
		return sql.replaceAll("'", "");
	}

	public PagedList listCodePageSize(int pageNo, int newpagesize,
			String mcode, boolean startWith, String condVal,
			String otherparamtype) {
		mcode = safeSql(mcode);

		String likePrefix = startWith ? "" : "%";
		PagedList res = new PagedList();
		String hsqlparam = "";
		String otherTables = "";
		String extendCols = "";

		String[] mcodes = mcode.split("%");
		String districtNumber = "";
		String otherCond = "";
		if (mcodes.length > 1) {
			districtNumber = mcodes[0];
			otherCond = mcodes[1];
		} else {
			otherCond = mcodes[0];
		}
		otherCond = safeSql(otherCond);
		if (otherparamtype != null) {
			if (otherparamtype.equals(OtherParamType_Woman)) {// 孕产妇随访查询
				otherTables = " ,HealthFileMaternal hm ";
				hsqlparam = " And p.sex = '女' And hm.fileNo = hf.fileNo And (hm.isClosed = '0' or p.bornStatus = '是') ";
				extendCols = " ,hm.id ";
			} else if (otherparamtype.equals(OtherParamType_Hyp)
					|| otherparamtype.equals(OtherParamType_T2dm)
					|| otherparamtype.equals(OtherParamType_Furious)) {// 慢病随访查询
				otherTables = " ,DiseaseHistory d ";
				hsqlparam = " And p.id = d.personalInfoId And diseaseId = "
						+ otherparamtype;
			} else if (otherparamtype.equals(OtherParamType_WomanBook)) {// 孕产妇建册查询
				hsqlparam = " And p.sex = '女'  ";
				extendCols = " ,hf.tel,hf.township,hf.village,p.workUnit,p.folk,p.folkOther,p.education,p.occupation,p.idnumber,hf.residenceAddress ";
			} else if (otherparamtype.equals(OtherParamType_Husband)) {// 丈夫信息
				hsqlparam = " And p.sex = '男'";
				extendCols = " ,hf.tel,p.workUnit,p.education,p.occupation ";
			} else if (otherparamtype.equals(OtherParamType_Child)) {// 儿童建册查询
																		// 条件<=7岁
				hsqlparam = " And p.birthday >=  convert(datetime,convert(nvarchar,(YEAR(getdate())-7))+'-01-01')  and  NOT EXISTS (select 1 from HealthFileChildren hfchild where hfchild.fileNo = hf.fileNo ) ";
				extendCols = " ,hf.township,hf.village,p.bloodTypeAbo,p.bloodTypeRh ";
			} else if (otherparamtype.equals(OtherParamType_ChildOther)) {//
				otherTables = " ,HealthFileChildren hc ";
				hsqlparam = " And hc.fileNo = hf.fileNo ";
				extendCols = " ,hc.id ";
			} else if (otherparamtype.equals(OtherParamType_Old)) {
				hsqlparam = " And p.birthday <  convert(datetime,convert(nvarchar,(YEAR(getdate())-64))+'-01-01') ";
			} else if (otherparamtype.equals(OtherParamType_MarryWoman)) {// 女性婚检
				hsqlparam = " And p.sex = '女'  ";
				extendCols = " ,hf.tel,hf.township,hf.village,p.workUnit,p.folk,p.folkOther,p.education,p.occupation,p.idnumber,hf.residenceAddress,hf.districtNumber ";
			} else if (otherparamtype.equals(OtherParamType_MarryMan)) {// 男性婚检
				hsqlparam = " And p.sex = '男'";
				extendCols = " ,hf.tel,hf.township,hf.village,p.workUnit,p.folk,p.folkOther,p.education,p.occupation,p.idnumber,hf.residenceAddress,hf.districtNumber ";
			}
		}

		// 改为like ,like 可以用索引 substring 不能用
		if (condVal.equals(CondVal_Fileno)) {
			likePrefix = likePrefix.replace("%", "");
			// mcode = mcode.replace("%", "");
			String fileNo = EncryptionUtils.encry(likePrefix + mcode) + "%";
			long count = getcount(
					"select count(*) from HealthFile hf,PersonalInfo p "
							+ otherTables
							+ "where  hf.fileNo = p.fileNo And hf.fileNo like '"+fileNo+"' and hf.status = 0 "
							+ hsqlparam);
			if (newpagesize == 0) {
				newpagesize = (int) count;
				newpagesize = (newpagesize == 0 ? pagesize : newpagesize);
			}
			res.totalLines = count;
			res.pageSize = newpagesize;
			res.totalPages = (int) (count / newpagesize)
					+ ((count % newpagesize > 0) ? 1 : 0);
			int from = pageNo * newpagesize;
			String sql = "select hf.fileNo, hf.name, p.sex, p.birthday,(year(getDate()) - year(p.birthday)) as age,"
					+ " p.idnumber,hf.barCode,hf.address "
					+ extendCols
					+ " from HealthFile as hf, PersonalInfo as p "
					+ otherTables
					+ "where  p.fileNo = hf.fileNo "
					+ "and hf.fileNo like '"+fileNo+"' and hf.status = 0 " + hsqlparam;
			List list = getList(sql, newpagesize, from);
			res.res = parseResult(list);
			res.currentPage = pageNo + 1;
		} else if (condVal.equals(CondVal_Name)) {
			long count = getcount(
					"select count(*) from HealthFile hf,PersonalInfo p "
							+ otherTables
							+ "where hf.fileNo = p.fileNo And hf.districtNumber like '"+districtNumber + "%' "
							+ "And hf.name like '"+EncryptionUtils.encry(otherCond) + "%'  and hf.status = 0"
							+ hsqlparam);
			if (newpagesize == 0) {
				newpagesize = (int) count;
				newpagesize = (newpagesize == 0 ? pagesize : newpagesize);
			}
			res.totalLines = count;
			res.pageSize = newpagesize;
			res.totalPages = (int) (count / newpagesize)
					+ ((count % newpagesize > 0) ? 1 : 0);
			int from = pageNo * newpagesize;
			String sql = "select hf.fileNo, hf.name, p.sex, p.birthday,(year(getDate()) - year(p.birthday)) as age,"
					+ " p.idnumber,hf.barCode,hf.address "
					+ extendCols
					+ " from HealthFile as hf, PersonalInfo as p "
					+ otherTables
					+ "where p.fileNo = hf.fileNo and hf.districtNumber like '"+districtNumber + "%' "
					+ "And hf.name like  '"+EncryptionUtils.encry(otherCond) + "%'  and hf.status = 0 " + hsqlparam;
			List list = getList(sql, newpagesize, from);
			res.res = parseResult(list);
			res.currentPage = pageNo + 1;
		} else if (condVal.equals(CondVal_LinkMan)) {
			String countsql = ("select count(*) from HealthFile hf,PersonalInfo p "
					+ otherTables
					+ "where hf.fileNo = p.fileNo And hf.districtNumber like '"+districtNumber + "%' "
					+ "And p.linkman like '%" + otherCond + "%' and hf.status = 0 " + hsqlparam);
			long count = getcount(countsql);
			if (newpagesize == 0) {
				newpagesize = (int) count;
				newpagesize = (newpagesize == 0 ? pagesize : newpagesize);
			}
			res.totalLines = count;
			res.pageSize = newpagesize;
			res.totalPages = (int) (count / newpagesize)
					+ ((count % newpagesize > 0) ? 1 : 0);
			int from = pageNo * newpagesize;
			String sql = ("select hf.fileNo, hf.name, p.sex, p.birthday,(year(getDate()) - year(p.birthday)) as age,"
					+ " p.idnumber,hf.barCode,hf.address "
					+ extendCols
					+ " from HealthFile as hf, PersonalInfo as p "
					+ otherTables
					+ "where p.fileNo = hf.fileNo and hf.districtNumber like '"+districtNumber + "%' "
					+ "And p.linkman like '%" + otherCond + "%'  and hf.status = 0 " + hsqlparam);
			List list = getList(sql, newpagesize, from);
			res.res = parseResult(list);
			res.currentPage = pageNo + 1;
		} else if (condVal.equals(CondVal_CardId)) {
			String countsql = ("select count(*) from HealthFile hf , PersonalInfo as p "
					+ otherTables
					+ "where p.fileNo = hf.fileNo And hf.districtNumber like '"+districtNumber + "%' And p.idnumber like '"+EncryptionUtils.encry(otherCond) + "%'  and hf.status = 0" + hsqlparam);
			long count = getcount(countsql);
			if (newpagesize == 0) {
				newpagesize = (int) count;
				newpagesize = (newpagesize == 0 ? pagesize : newpagesize);
			}
			res.totalLines = count;
			res.pageSize = newpagesize;
			res.totalPages = (int) (count / newpagesize)
					+ ((count % newpagesize > 0) ? 1 : 0);
			int from = pageNo * newpagesize;
			String sql = ("select hf.fileNo, hf.name, p.sex, p.birthday,(year(getDate()) - year(p.birthday)) as age,"
					+ " p.idnumber,hf.barCode,hf.address  "
					+ extendCols
					+ " from HealthFile as hf, PersonalInfo as p "
					+ otherTables
					+ "where hf.districtNumber like '"+districtNumber + "%' "
					+ "And p.idnumber like '"+EncryptionUtils.encry(otherCond) + "%'  And hf.fileNo = p.fileNo  and hf.status = 0 " + hsqlparam);
			List list = getList(sql, newpagesize, from);
			res.res = parseResult(list);
			res.currentPage = pageNo + 1;
		} else if (condVal.equals(CondVal_Barcode)) {
			String countsql = ("select count(*) from HealthFile hf , PersonalInfo as p "
					+ otherTables
					+ "where p.fileNo = hf.fileNo  And hf.barCode like '"+otherCond + "%'  and hf.status = 0" + hsqlparam);
			// qry.setParameter(0, districtNumber + "%");
			long count = getcount(countsql);
			if (newpagesize == 0) {
				newpagesize = (int) count;
				newpagesize = (newpagesize == 0 ? pagesize : newpagesize);
			}
			res.totalLines = count;
			res.pageSize = newpagesize;
			res.totalPages = (int) (count / newpagesize)
					+ ((count % newpagesize > 0) ? 1 : 0);
			int from = pageNo * newpagesize;
			String sql = ("select hf.fileNo, hf.name, p.sex, p.birthday,(year(getDate()) - year(p.birthday)) as age,"
					+ " p.idnumber,hf.barCode,hf.address "
					+ extendCols
					+ " from HealthFile as hf, PersonalInfo as p "
					+ otherTables
					+ "where p.fileNo = hf.fileNo  "
					+ "And hf.barCode like '" + otherCond + "%'  and hf.status = 0 " + hsqlparam);
			// qry.setParameter(0, districtNumber + "%");
			List list = getList(sql, newpagesize, from);
			res.res = parseResult(list);
			res.currentPage = pageNo + 1;
		}
		return res;

	}

	private int getcount(final String sql, final Object param) {
		return ((Long) (getHibernateTemplate().find(sql, param).get(0)))
				.intValue();
	}

	private int getcount(final String sql) {
		return ((Long) (getHibernateTemplate().find(sql).get(0))).intValue();
	}

	private int getcount(final String sql, final List params) {
		return ((Long) (getHibernateTemplate().find(sql, params.toArray())
				.get(0))).intValue();
	}

	private int getcount(final String sql, final Object[] params) {
		return ((Long) (getHibernateTemplate().find(sql, params).get(0)))
				.intValue();
	}

	private List getList(final String sql, final List params,
			final int pagesize, final int start) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session arg0)
					throws HibernateException, SQLException {
				Query query = arg0.createQuery(sql);
				for (int i = 0; i < params.size(); i++) {
					query.setParameter(i, params.get(i));
				}
				query.setFirstResult(start).setMaxResults(pagesize);
				return query.list();
			}
		});
	}

	private List getList(final String sql, final Object[] params,
			final int pagesize, final int start) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session arg0)
					throws HibernateException, SQLException {
				Query query = arg0.createQuery(sql);
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i, params[i]);
				}
				query.setFirstResult(start).setMaxResults(pagesize);
				return query.list();
			}
		});
	}

	private List getList(final String sql, final int pagesize, final int start) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session arg0)
					throws HibernateException, SQLException {
				Query query = arg0.createQuery(sql);
				query.setFirstResult(start).setMaxResults(pagesize);
				return query.list();
			}
		});
	}

	private List getList(final String sql, final Object param,
			final int pagesize, final int start) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session arg0)
					throws HibernateException, SQLException {
				Query query = arg0.createQuery(sql);
				query.setParameter(0, param);
				query.setFirstResult(start).setMaxResults(pagesize);
				return query.list();
			}
		});
	}

	public List getItem(String code) {
		List list = getHibernateTemplate()
				.find("select hf.fileNo, hf.name, p.sex, p.birthday,(year(getDate()) - year(p.birthday)) as age,"
						+ " p.idnumber,hf.barCode"
						+ " from HealthFile as hf, PersonalInfo as p "
						+ "where p.fileNo = hf.fileNo "
						+ "and p.fileNo = '"+EncryptionUtils.encry(code)+"' and hf.status = 0 ");
		return parseResult(list);
	}

	public List getFileByIdNumber(String fileno, String idnumber) {
		if (fileno != null && fileno.trim().length() > 0) {
			List list = getHibernateTemplate().find(
					"select hf.fileNo, hf.name, p.sex "
							+ " from HealthFile as hf, PersonalInfo as p "
							+ "where p.fileNo = hf.fileNo "
							+ "and p.idnumber = '"+EncryptionUtils.encry(idnumber)+"' and p.fileNo <> '"+EncryptionUtils.encry(fileno.trim())+"' "
					);
			return list;
		} else {
			List list = getHibernateTemplate().find(
					"select hf.fileNo, hf.name, p.sex "
							+ " from HealthFile as hf, PersonalInfo as p "
							+ "where p.fileNo = hf.fileNo "
							+ "and p.idnumber = '"+EncryptionUtils.encry(idnumber)+"' ");
			if (list.size() > 0) {
				return list;
			} else {
				List list1 = getHibernateTemplate()
						.find("select hf.fileno, hf.name, hf.sex "
								+ " from HealthFileHistory2 hf "
								+ "where (linkFileno is null  or  len(linkFileno)=0 ) and  hf.idcard = '"+idnumber+"' ");
				for (int i = 0; i < list1.size(); i++) {
					Object[] obj = (Object[]) list1.get(i);
					obj[0] = EncryptionUtils.encry((String) obj[0]);
					obj[1] = EncryptionUtils.encry((String) obj[1]);
				}
				return list1;
			}
		}
	}

	public List checkFileByIdNumber(String fileno, String idnumber) {
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		String disid = user.getDistrictId();
		while (disid.endsWith("00")) {
			disid = disid.substring(0, disid.length() - 2);
		}
		String wherestr;
		wherestr = "";
		List list = new ArrayList();
		List list1 = new ArrayList();
		if (fileno != null && fileno.trim().length() > 0) {
			list = getHibernateTemplate().find(
					"select  hf.fileNo, hf.name, p.sex "
							+ " from HealthFile as hf, PersonalInfo as p "
							+ "where p.fileNo = hf.fileNo and hf.status=0 "
							+ "and p.idnumber = '"+EncryptionUtils.encry(idnumber)+"' and p.fileNo <> '"+EncryptionUtils.encry(fileno.trim())+"' ");
			if (list.size() > 0) {
				return list;
			} else {
				wherestr = "where hf.idcard = '"+idnumber+"' and  ("
						+ "  (not linkFileno is null and  linkFileno <> '' and linkFileno <> '"+fileno+"') or "
						+ "  (not districtId is null and  districtId <> '' and districtId <> substring('"+disid+"',1,len(districtId) )) "
						+ "  ) ";
				list1 = getHibernateTemplate().find(
						"select hf.fileno, hf.name, hf.sex "
								+ " from HealthFileHistory2 hf " + wherestr);
				for (int i = 0; i < list1.size(); i++) {
					Object[] obj = (Object[]) list1.get(i);
					obj[0] = EncryptionUtils.encry((String) obj[0]);
					obj[1] = EncryptionUtils.encry((String) obj[1]);
				}
				return list1;
			}
		} else {
			wherestr = "where hf.idcard = '"+idnumber+"' and  ("
					+ "  (not linkFileno is null and  linkFileno <> '' ) or "
					+ "  (not districtId is null and  districtId <> '' and districtId <> substring('"+disid+"',1,len(districtId) )) "
					+ "  ) ";
			list = getHibernateTemplate().find(
					"select  hf.fileNo, hf.name, p.sex "
							+ " from HealthFile as hf, PersonalInfo as p  "
							+ "where p.fileNo = hf.fileNo  and hf.status=0 "
							+ "and p.idnumber = '"+EncryptionUtils.encry(idnumber)+"' ");
			if (list.size() > 0) {
				return list;
			} else {
				list1 = getHibernateTemplate().find(
						"select hf.fileno, hf.name, hf.sex "
								+ " from HealthFileHistory2 hf " + wherestr);
				for (int i = 0; i < list1.size(); i++) {
					Object[] obj = (Object[]) list1.get(i);
					obj[0] = EncryptionUtils.encry((String) obj[0]);
					obj[1] = EncryptionUtils.encry((String) obj[1]);
				}
				return list1;
			}
		}
	}

	public PagedList getHistoryList(int pageNo, String mcode,
			boolean startWith, String condVal, String otherparamtype) {
		// System.out.println("=============pageNo======"+pageNo);
		// System.out.println("=============newpagesize======"+pagesize);
		// System.out.println("=============mcode======"+mcode);
		// System.out.println("=============startWith======"+startWith);
		// System.out.println("=============condVal======"+condVal);
		// System.out.println("=============otherparamtype======"+otherparamtype);
		PagedList res = new PagedList();
		String[] mcodes = mcode.split("%");
		String otherCond = "";
		if (mcodes.length > 1) {
			otherCond = mcodes[1];
		} else {
			otherCond = mcodes[0];
		}
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		String disid = user.getDistrictId();
		while (disid.endsWith("00")) {
			disid = disid.substring(0, disid.length() - 2);
		}
		String wherestr;
		if (disid.length() >= 6) {
			disid = disid.substring(0, 6);
			wherestr = "where ( linkFileno = '' or linkFileno is null ) and (districtId is null or districtId ='' or  districtId = '"
					+ disid + "' ) ";
		} else {
			wherestr = "where ( linkFileno = '' or linkFileno is null ) and (districtId is null or districtId = '' or  districtId = '"
					+ disid + "' ) ";
		}
		// 改为like ,like 可以用索引 substring 不能用
		if (condVal.equals(CondVal_History_Name)) {
			wherestr += " and  name like  '" + otherCond + "%' ";
			String countsql = ("select count(*) from HealthFileHistory2 " + wherestr);
			long count = getcount(countsql);
			if (pagesize == 0) {
				pagesize = (int) count;
				pagesize = (pagesize == 0 ? pagesize : pagesize);
			}
			res.totalLines = count;
			res.pageSize = pagesize;
			res.totalPages = (int) (count / pagesize)
					+ ((count % pagesize > 0) ? 1 : 0);
			int from = pageNo * pagesize;
			String sql = ("select id, name, sex, idcard, birthday,(year(getDate()) - year(birthday)) as age,"
					+ "address,raddress,tel,xz,cwh,jddw,jdr,zrys,jdrq"
					+ " from HealthFileHistory2 " + wherestr);
			List list = getList(sql, pagesize, from);
			res.res = list;
			res.currentPage = pageNo + 1;
		} else if (condVal.equals(CondVal_History_CardId)) {
			wherestr += " and  idcard  like  '" + otherCond + "%' ";
			String countsql = ("select count(*) from HealthFileHistory2 " + wherestr);
			long count = getcount(countsql);
			if (pagesize == 0) {
				pagesize = (int) count;
				pagesize = (pagesize == 0 ? pagesize : pagesize);
			}
			res.totalLines = count;
			res.pageSize = pagesize;
			res.totalPages = (int) (count / pagesize)
					+ ((count % pagesize > 0) ? 1 : 0);
			int from = pageNo * pagesize;
			String sql = ("select id, name, sex, idcard, birthday,(year(getDate()) - year(birthday)) as age,"
					+ "address,raddress,tel,xz,cwh,jddw,jdr,zrys,jdrq"
					+ " from HealthFileHistory2 " + wherestr);
			List list = getList(sql, pagesize, from);
			res.res = list;
			res.currentPage = pageNo + 1;
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean saveHistoryLink(String fileno, String newid)
			throws Exception {
		if (newid != null && newid.trim().length() > 0 && fileno != null
				&& fileno.trim().length() > 0) {
			List oldret = getHibernateTemplate().find(
					" from HealthFileHistory2 where linkFileno = '"+fileno+"'");
			if (oldret.size() > 0) {
				for (int i = 0; i < oldret.size(); i++) {
					HealthFileHistory2 vo = ((HealthFileHistory2) (oldret
							.get(i)));
					vo.setLinkFileno(null);
					getHibernateTemplate().update(vo);
				}
			}
			HealthFileHistory2 newvo = (HealthFileHistory2) getHibernateTemplate()
					.get(HealthFileHistory2.class, Long.parseLong(newid));
			newvo.setLinkFileno(fileno);
			getHibernateTemplate().update(newvo);
			return true;
		} else {
			return false;
		}
	}

	public List getHistoryItem(String code) {
		if (code == null) {
			return null;
		} else {
			List list = getHibernateTemplate().find(
					"select id, name, sex, idcard, birthday,0,"
							+ "address,raddress,tel,xz,cwh,jddw,jdr,zrys,jdrq"
							+ " from HealthFileHistory2 " + "where  id = "+code+" ");
			return parseResult(list);
		}
	}

	@Transactional
	public void saveHealthfileHistoryData(final String modulename,
			final String name, final String value) {
		final TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session arg0)
					throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String sql = " delete his_values where inputpersonid='"
								+ user.getUsername() + "' and modulename='"
								+ modulename + "' and name = '" + name + "'";
				arg0.createSQLQuery(sql).executeUpdate();
				sql = " insert into his_values values('"+user.getUsername()+"','"+modulename+"','"+name+"','"+value+"')";
				arg0.createSQLQuery(sql).executeUpdate();
				return null;
			}
		});

	}

	public String getHealthfileHistoryData(final String modulename,
			final String name) {
		final TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session arg0)
					throws HibernateException, SQLException {
				return arg0.createSQLQuery(
						" select value from his_values where inputpersonid='"
								+ user.getUsername() + "' and modulename='"
								+ modulename + "' and name = '" + name + "'")
						.list();
			}
		});
		if (list.size() > 0) {
			return (String) list.get(0);
		} else {
			return "";
		}
	}

	/**
	 * 解释List
	 * 
	 * @param list
	 * @return
	 */
	private List parseResult(List list) {
		List arrayList = new ArrayList();
		String sql = "";
		Query query = null;
		List l;
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				Object[] objArray = new Object[obj.length];
				String name = (String) obj[1];
				obj[1] = EncryptionUtils.decipher(name);
				String idNum = (String) obj[5];
				obj[5] = EncryptionUtils.decipher(idNum);
				String str = (String) obj[0];
				sql = "Select general11 From MedicalExam Where fileNo = '"
						+ str + "' Order By inputDate";
				l = getHibernateTemplate().find(sql);
				if (l.size() > 0) {
					Object o = l.get(0);
					if (o != null) {
						BigDecimal general11 = (BigDecimal) o;
						general11 = general11.setScale(2,
								BigDecimal.ROUND_HALF_UP);
						objArray[6] = general11;
					} else {
						objArray[6] = 0;
					}
				} else {
					objArray[6] = 0;
				}
				obj[0] = EncryptionUtils.decipher(str);
				for (int j = 0; j < obj.length; j++) {
					objArray[j] = obj[j];
				}
				arrayList.add(objArray);
			}
		}
		return arrayList;
	}

	public boolean districtIdIsExists(String disId) {
		String hql = "From District Where id = '"+disId+"'";
		List list = getHibernateTemplate().find(hql);
		if (list.size() > 0)
			return true;
		return false;
	}
}