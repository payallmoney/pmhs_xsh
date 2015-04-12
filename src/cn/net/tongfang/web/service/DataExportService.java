package cn.net.tongfang.web.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.csv.CSVFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.net.tongfang.framework.security.bo.QryCondition;
import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.BabyVisit;
import cn.net.tongfang.framework.security.vo.BirthCertificate;
import cn.net.tongfang.framework.security.vo.ChildLastMedicalExamRecord;
import cn.net.tongfang.framework.security.vo.ChildrenMediExam;
import cn.net.tongfang.framework.security.vo.ChildrenMediExam36;
import cn.net.tongfang.framework.security.vo.ExportDiv;
import cn.net.tongfang.framework.security.vo.ExportJson;
import cn.net.tongfang.framework.security.vo.ExportMain;
import cn.net.tongfang.framework.security.vo.ExportSub;
import cn.net.tongfang.framework.security.vo.FirstVistBeforeBorn;
import cn.net.tongfang.framework.security.vo.HealthFile;
import cn.net.tongfang.framework.security.vo.PersonalInfo;
import cn.net.tongfang.framework.security.vo.SamTaxempcode;
import cn.net.tongfang.framework.security.vo.SamTaxorgcode;
import cn.net.tongfang.framework.security.vo.VisitAfterBorn;
import cn.net.tongfang.framework.security.vo.VisitBeforeBorn;
import cn.net.tongfang.framework.security.vo.WomanLastMedicalExamRecord;
import cn.net.tongfang.framework.util.BusiUtils;
import cn.net.tongfang.framework.util.CommonConvertUtils;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.framework.util.ExcelUtils;
import cn.net.tongfang.framework.util.SystemInformationUtils;
import cn.net.tongfang.framework.util.service.ModuleMgr;
import cn.net.tongfang.web.service.bo.BirthCertifiQry;
import cn.net.tongfang.web.service.util.BaseParamUtil;

import com.csvreader.CsvWriter;
import com.google.gson.Gson;

/**
 * @author Jackstraw
 * 
 */
public class DataExportService extends HibernateDaoSupport {
	// 1岁以内、1~2岁、3~6岁儿童数据导出标题
	private static String[] headerChildren = { "执行机构", "档案编号", "姓名", "性别",
			"出生日期", "项目", "高危", "随访日期", "下次随访日期", "随访医生", "操作人员" };
	// 儿童档案导出标题
	private static String[] headerChildrenFile = { "档案编号", "姓名", "性别", "出生日期",
			"身份证号", "家庭住址", "行政区划", "联系人电话", "联系人" };
	// 孕产妇档案导出标题
	private static String[] headerWomanBirthFile = { "档案编号", "姓名", "性别",
			"出生日期", "身份证号", "家庭住址", "行政区划", "联系人电话", "联系人" };
	// 高危孕产妇信息导出标题
	private static String[] headerHighRiskInfo = { "档案编号", "姓名", "性别", "出生日期",
			"身份证号", "最近一次产检时间", "高危因素", "家庭住址", "联系人", "电话" };
	// 高危儿童信息导出标题
	private static String[] headerChildHighRiskInfo = { "档案编号", "姓名", "性别",
			"出生日期", "身份证号", "最近一次体检时间", "高危因素", "家庭住址", "联系人", "电话" };

	// 第1次产前随访
	private static String[] headerFirstVistBeforeBorn = { "执行机构", "档案编号", "姓名",
			"性别", "出生日期", "孕周", "高危", "随访日期", "下次随访日期", "随访医生", "操作人员" };

	// 基本档案
	// denc(a.fileNo) as fileNo ,denc(a.name) as
	// name,b.sex,b.birthday,b.idnumber,a.address,b.linkman,a.tel
	private static String[] headerHealthFile = { "档案编号", "姓名", "性别", "出生日期",
			"身份证号码", "地址", "联系人", "联系电话", "纸质档案编号", "建档人", "责任医生" ,"建档日期","户籍"};
	// 第2至5次产前随访
	private static String[] headerVistBeforeBorn = { "执行机构", "档案编号", "姓名",
			"性别", "出生日期", "孕周", "项目", "高危", "随访日期", "下次随访日期", "随访医生", "操作人员" };
	// 产后访视
	private static String[] headerVistAfterBorn = { "执行机构", "档案编号", "姓名", "性别",
			"出生日期", "高危", "随访日期", "下次随访日期", "随访医生", "操作人员" };
	// 乡镇汇总统计
	private static String[] headerStatisticTownship = { "乡镇名称", "农业人口建档数",
			"城镇人口建档数", "新生儿访视次数	", "0~6岁儿童建档数", "0~6岁儿童随访次数", "孕产妇建档数",
			"孕产妇产前访视次数", "孕产妇产后访视次数", "65岁以上老年人保健次数", "高血压建档数	", "高血压病随访次数",
			"糖尿病建档数", "糖尿病随访次数", "重性精神病建档数", "重性精神病随访次数" };
	// 高危汇总统计
	private static String[] headerStatisticHighRisk = { "乡镇名称", "第1次产前随访",
			"第2~5次产前随访", "产后访视", "产后42天访视", "新生儿家庭访视记录", "1岁以内儿童健康体检",
			"1~2岁儿童健康体检", "3~6岁儿童健康体检" };

	// 月报表统计一
	private static String[] headerStatisticMonth01 = { "卫生院名称", "常住人口数",
			"户藉人口数", "城镇居民人口数", "乡村居民人口数", "孕产妇数", "活产数", "0-6岁儿童数",
			"15岁以上人口数", "35岁以上人口数", "65岁以上人口数", "应管理高血压人数", "应管理糖尿病人数",
			"应管理精神病人数" };
	
	private static Pattern procPattern = Pattern.compile("^\\s*\\{\\s*call .*",Pattern.CASE_INSENSITIVE);
	

	public static final String VISIT_AFTER_DEFAULT = "0";
	public static final String VISIT_AFTER_42 = "1";

	private static final String BABYVISIT = "babyvisit";
	private static final String CHILDFILE = "childfile";
	private static final String CHILDREN01 = "children01";
	private static final String CHILDREN02 = "children02";
	private static final String CHILDREN36 = "children36";
	private static final String FIRSTVISIT = "firstvisit";
	private static final String HEALTHFILE = "healthfile";
	private static final String VISITBEFOREBORN = "visitbeforeborn";
	private static final String VISITAFTERBORN = "visitafterborn";
	private static final String VISITAFTERBORN42 = "visitafterborn42";
	private static final String STATISTICTOWNSHIP = "statistictownship";
	private static final String STATISTICHIGHRISK = "statistichighrisk";
	private static final String STATISTICMONTH01 = "statisticmonth01";
	private static final String STATISTICMONTH02 = "statisticmonth02";
	private static final String HIGHRISKINFO = "highriskinfo";
	private static final String CHILDHIGHRISKINFO = "childhighriskinfo";
	private static final String STATISTICDAY = "statisticday";
	private static final String STATISTICREGISTERBABY = "statisticregisterbaby";
	private static final String WOMANBIRTHFILE = "womanbirthfile";

	private static final String BABYVISIT_TITLE = "新生儿家庭访视记录";
	private static final String CHILDFILE_TITLE = "儿童档案";
	private static final String CHILDREN01_TITLE = "1岁以内儿童体检记录";
	private static final String CHILDREN02_TITLE = "1~2岁儿童体检记录";
	private static final String CHILDREN36_TITLE = "3~6岁儿童体检记录";
	private static final String FIRSTVISIT_TITLE = "第1次产前随访记录";
	private static final String HEALTHFILE_TITLE = "居民健康档案";
	private static final String VISITBEFOREBORN_TITLE = "第2~5次产前随访记录";
	private static final String VISITAFTERBORN_TITLE = "产后访视记录";
	private static final String VISITAFTERBORN42_TITLE = "产后42天访视记录";
	private static final String STATISTICTOWNSHIP_TITLE = "乡镇汇总统计";
	private static final String STATISTICHOSPITAL_TITLE = "卫生院汇总统计";
	private static final String STATISTICHIGHRISK_TITLE = "高危汇总统计";
	private static final String STATISTICMONTH01_TITLE = "月报表统计（一）";
	private static final String STATISTICMONTH02_TITLE = "月报表统计（二）";
	private static final String STATISTICDAY_TITLE = "日报表统计";
	private static final String STATISTICREGISTERBABY_TITLE = "医疗保健机构出生登记月报表";
	private static final String HIGHRISKINFO_TITLE = "高危孕产妇信息";
	private static final String CHILDHIGHRISKINFO_TITLE = "高危儿童信息";
	private static final String WOMANBIRTHFILE_TITLE = "孕产妇档案";

	private ReportService reportService;
	private SystemInformationUtils sysInfos;
	private SummaryService summaryService;
	private ModuleMgr userMenuTreeService;

	public void setSysInfos(SystemInformationUtils sysInfos) {
		this.sysInfos = sysInfos;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public void setSummaryService(SummaryService summaryService) {
		this.summaryService = summaryService;
	}

	public void setUserMenuTreeService(ModuleMgr userMenuTreeService) {
		this.userMenuTreeService = userMenuTreeService;
	}

	/**
	 * 写Excel文件
	 * 
	 * @param disNo
	 * @param list
	 * @return
	 */
	private String writeExecl(String disNo, List list, String type, String title)
			throws Exception {
		// return writePoiExeclFile(disNo,list,type,title);
		// return writeExeclFile(disNo,list,type,title);
		delOldFile(getWebRootAbsolutePath() + "data/");
		return writeCsvFileNew(disNo, list, type, title);
		// return writeXLSXFile(disNo,list,type,title);
	}

	private void delOldFile(String path) {
		System.out.println("=======path=====" + path);
		File[] files = new File(path).listFiles();
		// long day = 1000* 60*60*24;
		long day = 1000 * 60 * 60;// delete file before an hour
		if (files != null) { // some JVMs return null for empty dirs
			for (File f : files) {
				try {
					String fpath = f.getAbsolutePath();
					System.out.println("=====fpath=======" + fpath);
					if (fpath.endsWith(".xls") || fpath.endsWith(".csv")
							|| fpath.endsWith(".xlsx")) {
						BasicFileAttributes attributes = Files.readAttributes(
								f.toPath(), BasicFileAttributes.class);
						FileTime creationTime = attributes.creationTime();
						if (new Date().getTime() - creationTime.toMillis() > day)
							f.delete();
					}
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String writeExeclFile(String disNo, List list, String type,
			String title) throws Exception {
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		String fileName = DateToStr(new Date()) + "_" + disNo + "_"
				+ user.getUsername() + "_" + type + ".xls";
		File file = new File(getWebRootAbsolutePath() + "data/" + fileName);

		WritableWorkbook wwb = null;
		try {
			wwb = Workbook.createWorkbook(file);
			WritableSheet sheet = wwb.createSheet("sheet1", list.size());
			Label labTitle = new Label(0, 0, title);
			sheet.addCell(labTitle);
			if (list.size() > 0) {
				if (list.get(0) instanceof Object[]) {
					sheet.mergeCells(0, 0, ((Object[]) list.get(0)).length - 1,
							0);
				} else {
					sheet.mergeCells(0, 0, ((List) list.get(0)).size() - 1, 0);
				}
				int i = 1;
				System.out.println("===list.size()===" + list.size());
				for (Object object : list) {
					// System.out.println("===i==="+i);
					if (object instanceof Object[]) {
						Object[] l = (Object[]) object;
						for (int j = 0; j < l.length; j++) {
							Label lab = new Label(j, i,
									isNull(String.valueOf(l[j])));
							sheet.addCell(lab);
						}
					} else {
						List l = (List) object;
						for (int j = 0; j < l.size(); j++) {
							Label lab = new Label(j, i, isNull(String.valueOf(l
									.get(j))));
							sheet.addCell(lab);
						}
					}
					if (i % 100 == 0) {
						wwb.write();
					}
					i++;
				}
			}
			wwb.write();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				wwb.close();
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return fileName;
	}

	private String writeXLSXFile(String disNo, List list, String type,
			String title) throws Exception {
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		String fileName = DateToStr(new Date()) + "_" + disNo + "_"
				+ user.getUsername() + "_" + type + ".xlsx";
		try {
			// File f = new File(getWebRootAbsolutePath() + "data/" + fileName);
			FileOutputStream out = new FileOutputStream(
					getWebRootAbsolutePath() + "data/" + fileName);
			// OPCPackage pkg = OPCPackage.create(f);
			// org.apache.poi.ss.usermodel.Workbook wb = new XSSFWorkbook(pkg);
			org.apache.poi.ss.usermodel.Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet();
			if (list.size() > 0) {
				int i = 0;
				for (Object object : list) {
					Row row = sheet.createRow(i);
					if (object instanceof Object[]) {
						Object[] l = (Object[]) object;
						for (int j = 0; j < l.length; j++) {
							Cell cell = row.createCell(j);
							String value = String.valueOf(l[j]);
							cell.setCellValue(value);
						}
					} else if (object instanceof List) {
						List l = (List) object;
						for (int j = 0; j < l.size(); j++) {
							Cell cell = row.createCell(j);
							String value = String.valueOf(l.get(j));
							cell.setCellValue(value);
						}
					}
					i++;
				}
			}
			wb.write(out);
			// f.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {

		}
		return fileName;
	}

	private String writeCsvFile(String disNo, List list, String type,
			String title) throws Throwable {
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		String fileName = DateToStr(new Date()) + "_" + disNo + "_"
				+ user.getUsername() + "_" + type + ".csv";
		// File file = new File(getWebRootAbsolutePath() + "data/" + fileName);
		CsvWriter fw = null;
		// CSVFormat csfformat = CSVFormat.EXCEL;
		try {
			System.out.println("====export========" + getWebRootAbsolutePath()
					+ "data/" + fileName);
			fw = new CsvWriter(getWebRootAbsolutePath() + "data/" + fileName,
					',', Charset.forName("GBK"));
			System.out.println("====export========" + getWebRootAbsolutePath()
					+ "data/" + fileName);
			fw.setForceQualifier(true);
			for (Object object : list) {
				if (object instanceof Object[]) {
					Object[] l = (Object[]) object;
					for (int j = 0; j < l.length; j++) {
						String text = String.valueOf(l[j]);
						if (!StringUtils.hasText(text)) {
							text = "";
						}
						fw.write("\t" + text);
					}
				} else {
					List l = (List) object;
					for (int j = 0; j < l.size(); j++) {
						String text = String.valueOf(l.get(j));
						if (!StringUtils.hasText(text)) {
							text = "";
						}
						fw.write("\t" + text);
					}
				}
				fw.endRecord();
			}

			fw.flush();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (fw != null) {
				fw.close();
			}
		}
		return fileName;
	}

	private String writeCsvFileNew(String disNo, List list, String type,
			String title) throws Exception {
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		String fileName = DateToStr(new Date()) + "_" + disNo + "_"
				+ user.getUsername() + "_" + type + ".csv";
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(getWebRootAbsolutePath() + "data/"
							+ fileName), "GBK"));
			for (Object object : list) {
				if (object instanceof Object[]) {
					Object[] l = (Object[]) object;
					for (int j = 0; j < l.length; j++) {
						String text = String.valueOf(l[j]);
						if (!StringUtils.hasText(text)) {
							text = "";
						}
						if (l[j] == null) {
							text = "";
						}
						if (j == 0) {
							out.write(format(text));
						} else {
							out.write("," + format(text));
						}
					}
				} else {
					List l = (List) object;
					for (int j = 0; j < l.size(); j++) {
						String text = String.valueOf(l.get(j));
						if (l.get(j) == null) {
							text = "";
						}
						if (!StringUtils.hasText(text)) {
							text = "";
						}
						if (j == 0) {
							out.write(format(text));
						} else {
							out.write("," + format(text));
						}
					}
				}
				out.write("\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return fileName;
	}

	private String format(String txt) {
		String ret = CSVFormat.EXCEL.format(txt);
		if (ret.startsWith("\"")) {
			ret = "=" + ret;
		} else {
			ret = "=\"" + ret + "\"";
		}
		return ret;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public String dataExportChildFile(String disNo, String filterKey,
			String filterVal) throws Exception {
		List params = new ArrayList();
		StringBuilder where = new StringBuilder(" where 1=1 ");
		buildExportDataGeneralWhere(disNo, filterKey, filterVal, params, where,
				" and a.districtNumber like ? ");
		Timestamp childAge = BusiUtils.getChildAge();
		// params.add(childAge);
		where.append(" and b.birthday >= '" + childAge + "'");

		// if (params.size() != 0) {
		// where.replace(0, 4, " where ");
		// }
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b ").append(where);
		System.out.println("hql==" + hql);
		List list = query(hql);

		List dataList = new ArrayList();
		List headerList = new ArrayList();
		for (int i = 0; i < headerChildrenFile.length; i++) {
			headerList.add(headerChildrenFile[i]);
		}
		dataList.add(headerList);
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];

			List contentList = new ArrayList();
			contentList.add(EncryptionUtils.decipher(file.getFileNo()));
			contentList.add(EncryptionUtils.decipher(file.getName()));
			contentList.add(person.getSex());
			contentList.add(DateToStr(person.getBirthday()));
			contentList.add(EncryptionUtils.decipher(person.getIdnumber()));
			contentList.add(file.getAddress());
			contentList.add(file.getDistrictNumber());
			contentList.add(person.getLinkmanTel());
			contentList.add(person.getLinkman());
			dataList.add(contentList);
		}

		String fileName = writeExecl(disNo, dataList, CHILDFILE,
				CHILDFILE_TITLE);
		return getDownloadURL() + fileName;
	}

	public String dataExportWomanBirthFile(String disNo, String filterKey,
			String filterVal) throws Exception {
		List params = new ArrayList();
		StringBuilder where = new StringBuilder(" where 1=1 ");
		buildExportDataGeneralWhere(disNo, filterKey, filterVal, params, where,
				" and a.districtNumber like ? ");
		// params.add("是");
		where.append(" and b.bornStatus >= '是'");

		// if (params.size() != 0) {
		// where.replace(0, 4, " where ");
		// }
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b ").append(where);
		List list = query(hql);

		List dataList = new ArrayList();
		List headerList = new ArrayList();
		for (int i = 0; i < headerWomanBirthFile.length; i++) {
			headerList.add(headerWomanBirthFile[i]);
		}
		dataList.add(headerList);
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];

			List contentList = new ArrayList();
			contentList.add(EncryptionUtils.decipher(file.getFileNo()));
			contentList.add(EncryptionUtils.decipher(file.getName()));
			contentList.add(person.getSex());
			contentList.add(DateToStr(person.getBirthday()));
			contentList.add(EncryptionUtils.decipher(person.getIdnumber()));
			contentList.add(file.getAddress());
			contentList.add(file.getDistrictNumber());
			contentList.add(person.getLinkmanTel());
			contentList.add(person.getLinkman());
			dataList.add(contentList);
		}

		String fileName = writeExecl(disNo, dataList, WOMANBIRTHFILE,
				WOMANBIRTHFILE_TITLE);
		return getDownloadURL() + fileName;
	}

	/**
	 * 新生儿家庭访视记录
	 * 
	 * @param disNo
	 * @param filterKey
	 * @param filterVal
	 * @return
	 */
	public String dataExportBabyVisit(String disNo, String filterKey,
			String filterVal) throws Exception {
		List params = new ArrayList();
		StringBuilder where = new StringBuilder(" where 1=1 ");
		buildExportDataGeneralWhere(disNo, filterKey, filterVal, params, where,
				null);
		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");

		// if (params.size() != 0) {
		// where.replace(0, 4, " where ");
		// }
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, BabyVisit c, SamTaxempcode d,SamTaxorgcode e")
				.append(where);

		List dataList = new ArrayList();
		List headerList = new ArrayList();
		for (int i = 0; i < headerVistAfterBorn.length; i++) {
			headerList.add(headerVistAfterBorn[i]);
		}
		dataList.add(headerList);
		List list = query(hql);
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			BabyVisit babyVisit = (BabyVisit) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];

			List contentList = new ArrayList();
			contentList.add(samTaxorgcode.getName());
			contentList.add(EncryptionUtils.decipher(file.getFileNo()));
			contentList.add(EncryptionUtils.decipher(file.getName()));
			contentList.add(person.getSex());
			contentList.add(DateToStr(person.getBirthday()));
			contentList.add(babyVisit.getHighRisk());
			contentList.add(DateToStr(babyVisit.getVisitDate()));
			contentList.add(DateToStr(babyVisit.getNextVisitDate()));
			contentList.add(babyVisit.getVisitDoctor());
			contentList.add(samTaxempcode.getUsername());
			dataList.add(contentList);
		}

		String fileName = writeExecl(disNo, dataList, BABYVISIT,
				BABYVISIT_TITLE);
		return getDownloadURL() + fileName;
	}

	/**
	 * 1岁以内、1~2岁儿童数据访问
	 * 
	 * @param disNo
	 * @param filterKey
	 * @param filterVal
	 * @param type
	 * @return
	 */
	private String dataExportChild(String disNo, String filterKey,
			String filterVal, Integer type, String status, String title)
			throws Exception {
		List params = new ArrayList();
		StringBuilder where = new StringBuilder(" where 1=1 ");
		buildExportDataGeneralWhere(disNo, filterKey, filterVal, params, where,
				null);
		// if (params.size() != 0) {
		// where.replace(0, 4, " where ");
		// }
		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");
		where.append(" and c.dataType = " + type);
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, ChildrenMediExam c, SamTaxempcode d,SamTaxorgcode e")
				.append(where);
		List list = query(hql);
		List dataList = new ArrayList();
		List headerList = new ArrayList();
		for (int i = 0; i < headerChildren.length; i++) {
			headerList.add(headerChildren[i]);
		}
		dataList.add(headerList);
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile healthFile = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			ChildrenMediExam childrenMediExam = (ChildrenMediExam) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];

			List contentList = new ArrayList();
			contentList.add(samTaxorgcode.getName());
			contentList.add(EncryptionUtils.decipher(healthFile.getFileNo()));
			contentList.add(EncryptionUtils.decipher(healthFile.getName()));
			contentList.add(person.getSex());
			contentList.add(DateToStr(person.getBirthday()));
			contentList.add(childrenMediExam.getCheckItem());
			contentList.add(childrenMediExam.getHighRisk());
			contentList.add(DateToStr(childrenMediExam.getVisitDate()));
			contentList.add(childrenMediExam.getNextVisitDate());
			contentList.add(childrenMediExam.getVisitDoctor());
			contentList.add(samTaxempcode.getUsername());
			dataList.add(contentList);
		}

		String fileName = writeExecl(disNo, dataList, status, title);
		return getDownloadURL() + fileName;
	}

	/**
	 * 1岁以内儿童
	 * 
	 * @param disNo
	 * @param filterKey
	 * @param filterVal
	 * @return
	 */
	public String dataExportChildExam(String disNo, String filterKey,
			String filterVal) throws Exception {
		return dataExportChild(disNo, filterKey, filterVal, 0, CHILDREN01,
				CHILDREN01_TITLE);
	}

	/**
	 * 1~2岁儿童
	 * 
	 * @param disNo
	 * @param filterKey
	 * @param filterVal
	 * @return
	 */
	public String dataExportChildExam2(String disNo, String filterKey,
			String filterVal) throws Exception {
		return dataExportChild(disNo, filterKey, filterVal, 1, CHILDREN02,
				CHILDREN02_TITLE);
	}

	/**
	 * 3~6岁儿童数据导出
	 * 
	 * @param disNo
	 * @param filterKey
	 * @param filterVal
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public String dataExportChildExam36(String disNo, String filterKey,
			String filterVal) throws Exception {
		List params = new ArrayList();
		StringBuilder where = new StringBuilder(" where 1=1 ");
		buildExportDataGeneralWhere(disNo, filterKey, filterVal, params, where,
				null);
		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");
		// if (params.size() != 0) {
		// where.replace(0, 4, " where ");
		// }
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, ChildrenMediExam36 c, SamTaxempcode d,SamTaxorgcode e")
				.append(where);

		List list = query(hql);
		List dataList = new ArrayList();
		List headerList = new ArrayList();
		for (int i = 0; i < headerChildren.length; i++) {
			headerList.add(headerChildren[i]);
		}
		dataList.add(headerList);
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			ChildrenMediExam36 childrenMediExam36 = (ChildrenMediExam36) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			List contentList = new ArrayList();
			contentList.add(samTaxorgcode.getName());
			contentList.add(EncryptionUtils.decipher(file.getFileNo()));
			contentList.add(EncryptionUtils.decipher(file.getName()));
			contentList.add(person.getSex());
			contentList.add(DateToStr(person.getBirthday()));
			contentList.add(childrenMediExam36.getCheckItem());
			contentList.add(childrenMediExam36.getHighRisk());
			contentList.add(DateToStr(childrenMediExam36.getVisitDate()));
			contentList.add(DateToStr(childrenMediExam36.getNextVisitDate()));
			contentList.add(childrenMediExam36.getVisitDoctor());
			contentList.add(samTaxempcode.getUsername());
			dataList.add(contentList);
		}
		String fileName = writeExecl(disNo, dataList, CHILDREN36,
				CHILDREN36_TITLE);
		return getDownloadURL() + fileName;
	}

	/**
	 * TODO 居民健康档案导出
	 * 
	 * @param disNo
	 * @param filterKey
	 * @param filterVal
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public String dataExportHealthFile(String disNo, String filterKey,
			String filterVal) throws Exception {
		try {
			TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
					.currentOperator();
			List params = new ArrayList();
			StringBuilder where = new StringBuilder(" where 1=1 ");
			buildExportHealthfileWhere(disNo, filterKey, filterVal, params,
					where, null);
			where.append(" and emp.org_id = e.id ");
			where.append(" and a.inputPersonId = emp.loginname ");
			where.append(" and emp.org_id = " + user.getOrgId() + " ");
			// if (params.size() != 0) {
			// where.replace(0, 4, " where ");
			// }
			System.out.println("======where=============" + where);
			StringBuilder hql = new StringBuilder(
					"select dbo.denc(a.fileNo) as  fileNo ,"
							+ "dbo.denc(a.name) as name,"
							+ "b.sex, "
							+ "REPLACE( CONVERT( CHAR(10), b.birthday, 102), '.', '-') as birthday,"
							+ "dbo.denc(b.idnumber) idnumber,"
							+ "a.address,"
							+ "b.linkman,"
							+ "a.tel , a.paperFileNo,emp.username , a.doctor , REPLACE( CONVERT( CHAR(10), a.buildDate, 102), '.', '-') as builddate , case when  b.resideType = '户籍' then '户籍' else '非户籍' end 'resideType' "
							+ "from HealthFile a, PersonalInfo b,  Sam_Taxempcode emp , Organization e")
					.append(where);
			System.out.println("=========hql==========" + hql);
			List dataList = new ArrayList();
			List headerList = new ArrayList();
			for (int i = 0; i < headerHealthFile.length; i++) {
				headerList.add(headerHealthFile[i]);
			}
			dataList.add(headerList);
			// List list = query(hql,params);

			SQLQuery query = getSession().createSQLQuery(hql.toString());
			String[] resultname = new String[] { "fileNo", "name", "sex",
					"birthday", "idnumber", "address", "linkman", "tel",
					"paperFileNo", "username","doctor","builddate","resideType" };
			for (int i = 0; i < resultname.length; i++) {
				query.addScalar(resultname[i], Hibernate.STRING);
			}
			// for (int i = 0; i < params.size(); i++) {
			// if(params.get(i) instanceof java.util.Date || params.get(i)
			// instanceof java.sql.Date || params.get(i) instanceof
			// java.sql.Timestamp){
			// query.setParameter(i, params.get(i),Hibernate.DATE);
			// }else if(params.get(i) instanceof BigDecimal){
			// query.setParameter(i, params.get(i),Hibernate.BIG_DECIMAL);
			// }else if(params.get(i) instanceof Integer){
			// query.setParameter(i, params.get(i),Hibernate.INTEGER);
			// }else if(params.get(i) instanceof String){
			// query.setParameter(i, params.get(i),Hibernate.STRING);
			// }else{
			// query.setParameter(i, params.get(i),Hibernate.STRING);
			// }
			// // query.setParameter(i, params.get(i));
			// }
			List list = query.list();
			System.out.println("===sql end ===");
			// for (Object object : list) {
			// Object[] objs = (Object[]) object;
			// // HealthFile file = (HealthFile) objs[0];
			// // PersonalInfo person = (PersonalInfo) objs[1];
			// // SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			// // SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			//
			// List contentList = new ArrayList();
			// contentList.add(EncryptionUtils.decipher((String)objs[0]));
			// contentList.add(EncryptionUtils.decipher((String)objs[1]));
			// contentList.add((String)objs[2]);
			// contentList.add((String)objs[3]);
			// contentList.add((String)objs[4]);
			// contentList.add((String)objs[5]);
			// contentList.add((String)objs[6]);
			// contentList.add((String)objs[7]);
			// dataList.add(contentList);
			// }
			dataList.addAll(list);

			String fileName = writeExecl(disNo, dataList, HEALTHFILE,
					HEALTHFILE_TITLE);

			return getDownloadURL() + fileName;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 第一次产前随访
	 * 
	 * @param disNo
	 * @param filterKey
	 * @param filterVal
	 * @return
	 */
	public String dataExportFirstBabyVisit(String disNo, String filterKey,
			String filterVal) throws Exception {
		List params = new ArrayList();
		StringBuilder where = new StringBuilder(" where 1=1 ");
		buildExportDataGeneralWhere(disNo, filterKey, filterVal, params, where,
				null);
		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");
		// if (params.size() != 0) {
		// where.replace(0, 4, " where ");
		// }
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, FirstVistBeforeBorn c, SamTaxempcode d,SamTaxorgcode e")
				.append(where);
		List dataList = new ArrayList();
		List headerList = new ArrayList();
		for (int i = 0; i < headerFirstVistBeforeBorn.length; i++) {
			headerList.add(headerFirstVistBeforeBorn[i]);
		}
		dataList.add(headerList);
		List list = query(hql);
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			FirstVistBeforeBorn firstVist = (FirstVistBeforeBorn) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];

			List contentList = new ArrayList();
			contentList.add(samTaxorgcode.getName());
			contentList.add(EncryptionUtils.decipher(file.getFileNo()));
			contentList.add(EncryptionUtils.decipher(file.getName()));
			contentList.add(person.getSex());
			contentList.add(DateToStr(person.getBirthday()));
			contentList.add(firstVist.getWeeks());
			contentList.add(firstVist.getHighRisk());
			contentList.add(DateToStr(firstVist.getVisitDate()));
			contentList.add(DateToStr(firstVist.getNextVisitDate()));
			contentList.add(firstVist.getVisitDoctor());
			contentList.add(samTaxempcode.getUsername());
			dataList.add(contentList);
		}
		String fileName = writeExecl(disNo, dataList, FIRSTVISIT,
				FIRSTVISIT_TITLE);
		return getDownloadURL() + fileName;
	}

	/**
	 * 第2至5次产前随访数据导出
	 * 
	 * @param disNo
	 * @param filterKey
	 * @param filterVal
	 * @return
	 */
	public String dataExportVisitBeforeBorn(String disNo, String filterKey,
			String filterVal) throws Exception {
		List params = new ArrayList();
		StringBuilder where = new StringBuilder(" where 1=1 ");
		buildExportDataGeneralWhere(disNo, filterKey, filterVal, params, where,
				null);
		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");
		// if (params.size() != 0) {
		// where.replace(0, 4, " where ");
		// }
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, VisitBeforeBorn c, SamTaxempcode d,SamTaxorgcode e")
				.append(where);
		List dataList = new ArrayList();
		List headerList = new ArrayList();
		for (int i = 0; i < headerVistBeforeBorn.length; i++) {
			headerList.add(headerVistBeforeBorn[i]);
		}
		dataList.add(headerList);
		List list = query(hql);
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			VisitBeforeBorn visitBeforeBorn = (VisitBeforeBorn) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			List contentList = new ArrayList();
			contentList.add(samTaxorgcode.getName());
			contentList.add(EncryptionUtils.decipher(file.getFileNo()));
			contentList.add(EncryptionUtils.decipher(file.getName()));
			contentList.add(person.getSex());
			contentList.add(DateToStr(person.getBirthday()));
			contentList.add(visitBeforeBorn.getWeeks());
			contentList.add("第" + visitBeforeBorn.getItem() + "次");
			contentList.add(visitBeforeBorn.getHighRisk());
			contentList.add(DateToStr(visitBeforeBorn.getVisitDate()));
			contentList.add(DateToStr(visitBeforeBorn.getNextVisitDate()));
			contentList.add(visitBeforeBorn.getVisitDoctor());
			contentList.add(samTaxempcode.getUsername());
			dataList.add(contentList);
		}
		String fileName = writeExecl(disNo, dataList, VISITBEFOREBORN,
				VISITBEFOREBORN_TITLE);
		return getDownloadURL() + fileName;
	}

	/**
	 * 产后访视记录数据导出
	 * 
	 * @param disNo
	 * @param filterKey
	 * @param filterVal
	 * @param type
	 * @return
	 */
	private String dataExportVisitAfter(String disNo, String filterKey,
			String filterVal, String type, String status, String title)
			throws Exception {
		List params = new ArrayList();
		StringBuilder where = new StringBuilder(" where 1=1 ");
		buildExportDataGeneralWhere(disNo, filterKey, filterVal, params, where,
				null);

		where.append(" and c.recordType = '" + type + "'");
		// params.add(type);
		where.append(" and a.fileNo = c.fileNo");
		where.append(" and c.inputPersonId = d.loginname");
		where.append(" and d.orgId = e.id");
		// if (params.size() != 0) {
		// where.replace(0, 4, " where ");
		// }
		StringBuilder hql = new StringBuilder(
				"from HealthFile a, PersonalInfo b, VisitAfterBorn c, SamTaxempcode d,SamTaxorgcode e")
				.append(where);
		List dataList = new ArrayList();
		List headerList = new ArrayList();
		for (int i = 0; i < headerVistAfterBorn.length; i++) {
			headerList.add(headerVistAfterBorn[i]);
		}
		dataList.add(headerList);
		List list = query(hql);
		for (Object object : list) {
			Object[] objs = (Object[]) object;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			VisitAfterBorn visitAfterBorn = (VisitAfterBorn) objs[2];
			SamTaxempcode samTaxempcode = (SamTaxempcode) objs[3];
			SamTaxorgcode samTaxorgcode = (SamTaxorgcode) objs[4];
			List contentList = new ArrayList();
			contentList.add(samTaxorgcode.getName());
			contentList.add(EncryptionUtils.decipher(file.getFileNo()));
			contentList.add(EncryptionUtils.decipher(file.getName()));
			contentList.add(person.getSex());
			contentList.add(DateToStr(person.getBirthday()));
			contentList.add(visitAfterBorn.getHighRisk());
			contentList.add(DateToStr(visitAfterBorn.getVisitDate()));
			contentList.add(visitAfterBorn.getNextVisitDate());
			contentList.add(visitAfterBorn.getVisitDoctor());
			contentList.add(samTaxempcode.getUsername());
			dataList.add(contentList);
		}
		String fileName = writeExecl(disNo, dataList, status, title);
		return getDownloadURL() + fileName;
	}

	/**
	 * 产后访视记录
	 * 
	 * @param disNo
	 * @param filterKey
	 * @param filterVal
	 * @return
	 */
	public String dataExportVisitAfterBorn(String disNo, String filterKey,
			String filterVal) throws Exception {
		return dataExportVisitAfter(disNo, filterKey, filterVal,
				VISIT_AFTER_DEFAULT, VISITAFTERBORN, VISITAFTERBORN_TITLE);
	}

	/**
	 * 产后42天
	 * 
	 * @param disNo
	 * @param filterKey
	 * @param filterVal
	 * @return
	 */
	public String dataExportVisitAfterBorn42(String disNo, String filterKey,
			String filterVal) throws Exception {
		return dataExportVisitAfter(disNo, filterKey, filterVal,
				VISIT_AFTER_42, VISITAFTERBORN42, VISITAFTERBORN42_TITLE);
	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 */
	public void removeDataExportFile(String fileName) {
		File file = new File(getWebRootAbsolutePath() + "data/" + fileName);
		file.delete();
	}

	/**
	 * 获得Webroot的路径
	 * 
	 * @return
	 */
	private String getWebRootAbsolutePath() {
		String path = null;
		String folderPath = ModuleMgr.class.getProtectionDomain()
				.getCodeSource().getLocation().getPath();
		System.out.println("========folderPath====" + folderPath);
		if (folderPath.indexOf("WEB-INF") > 0) {
			path = folderPath.substring(0,
					folderPath.indexOf("WEB-INF/classes"));
		}
		System.out.println("========path====" + path);
		return path;
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return
	 */
	private String DateToStr(Date date) {
		if (date != null) {
			SimpleDateFormat fomart = new SimpleDateFormat("yyyy-MM-dd");
			return fomart.format(date);
		}
		return "";
	}

	private String DateToStr(Date date, String format) {
		if (date != null) {
			SimpleDateFormat fomart = new SimpleDateFormat(format);
			return fomart.format(date);
		}
		return "";
	}

	/**
	 * 构建查询条件
	 * 
	 * @param disNo
	 * @param filterKey
	 * @param filterVal
	 * @param params
	 * @param where
	 */
	private void buildExportDataGeneralWhere(String disNo, String filterKey,
			String filterVal, List params, StringBuilder where, String appendVal)
			throws Exception {
		while (disNo.endsWith("00")) {
			disNo = disNo.substring(0, disNo.length() - 2);
		}
		System.out.println("===========disNo========" + disNo);
		if (appendVal == null) {
			// params.add(disNo + '%');
			appendVal = " and (a.districtNumber like '" + disNo + "%') ";
		}
		if (StringUtils.hasText(disNo)) {
			// params.add(disNo + '%');
			where.append(appendVal.replaceAll("\\?", "'" + disNo + "%'"));
		}
		if (StringUtils.hasText(filterKey)) {

			if (filterKey.equals("a.name") || filterKey.equals("a.fileNo")) {
				filterVal = EncryptionUtils.encry(filterVal);
			} else if (filterKey.equals("a.inputDate")
					|| filterKey.equals("b.birthday")
					|| filterKey.equals("a.lastModifyDate")) {
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyyMMdd HH:mm:ss");
				try {
					Date startDate = null;
					Date endDate = null;
					if (filterVal.indexOf("-") > 0) {
						String[] valArray = filterVal.split("-");
						if (valArray.length > 2) {
							throw new Exception(
									"请输入正确的日期范围，如：20120101-20120102或者20120101。");
						}
						startDate = format.parse(valArray[0] + " 00:00:00");
						endDate = format.parse(valArray[1] + " 23:59:59");
					} else if (filterVal.indexOf("－") > 0) {
						String[] valArray = filterVal.split("－");
						if (valArray.length > 2) {
							throw new Exception(
									"请输入正确的日期范围，如：20120101-20120102或者20120101。");
						}
						startDate = format.parse(valArray[0] + " 00:00:00");
						endDate = format.parse(valArray[1] + " 23:59:59");
					} else {
						startDate = format.parse(filterVal + " 00:00:00");
						endDate = format.parse(filterVal + " 23:59:59");
					}
					// params.add(startDate);
					// params.add(endDate);
					where.append(" and " + filterKey + " between '"
							+ format.format(startDate) + "' and  '"
							+ format.format(endDate) + "' ");
				} catch (ParseException e) {
					throw new Exception(
							"请输入正确的日期范围，如：20120101-20120102或者20120101。");
				}
			} else if (filterKey.equals("c.highRisk")) {
				if (StringUtils.hasText(filterVal)) {
					// params.add(filterVal);
					where.append(" and " + filterKey + " = '" + filterVal + "'");
				}
			} else {
				if (StringUtils.hasText(filterVal)) {
					// params.add('%' + filterVal + '%');
					where.append(" and " + filterKey + " like '%" + filterVal
							+ "%'");
				}
			}
		}

		where.append(" and a.fileNo = b.fileNo and a.status = 0 ");

	}

	private void buildExportHealthfileWhere(String disNo, String filterKey,
			String filterVal, List params, StringBuilder where, String appendVal)
			throws Exception {
		while (disNo.endsWith("00")) {
			disNo = disNo.substring(0, disNo.length() - 2);
		}
		if (appendVal == null) {
			// params.add(disNo + '%');
			appendVal = " and (a.districtNumber like '" + disNo + "%') ";
		}
		if (StringUtils.hasText(disNo)) {
			where.append(appendVal);
		}
		if (StringUtils.hasText(filterKey)) {

			if (filterKey.equals("a.name") || filterKey.equals("a.fileNo")) {
				filterVal = EncryptionUtils.encry(filterVal);
			} else if (filterKey.equals("a.inputDate")
					|| filterKey.equals("b.birthday")
					|| filterKey.equals("a.lastModifyDate")) {
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyyMMdd HH:mm:ss");
				try {
					Date startDate = null;
					Date endDate = null;
					if (filterVal.indexOf("-") > 0) {
						String[] valArray = filterVal.split("-");
						if (valArray.length > 2) {
							throw new Exception(
									"请输入正确的日期范围，如：20120101-20120102或者20120101。");
						}
						startDate = format.parse(valArray[0] + " 00:00:00");
						endDate = format.parse(valArray[1] + " 23:59:59");
					} else if (filterVal.indexOf("－") > 0) {
						String[] valArray = filterVal.split("－");
						if (valArray.length > 2) {
							throw new Exception(
									"请输入正确的日期范围，如：20120101-20120102或者20120101。");
						}
						startDate = format.parse(valArray[0] + " 00:00:00");
						endDate = format.parse(valArray[1] + " 23:59:59");
					} else {
						startDate = format.parse(filterVal + " 00:00:00");
						endDate = format.parse(filterVal + " 23:59:59");
					}
					// params.add(startDate);
					// params.add(endDate);
					where.append(" and " + filterKey + " between '"
							+ format.format(startDate) + "' and  '"
							+ format.format(endDate) + "' ");
				} catch (ParseException e) {
					throw new Exception(
							"请输入正确的日期范围，如：20120101-20120102或者20120101。");
				}
			} else if (filterKey.equals("c.highRisk")) {
				if (StringUtils.hasText(filterVal)) {
					// params.add(filterVal);
					where.append(" and " + filterKey + " = '" + filterVal + "'");
				}
			} else {
				if (StringUtils.hasText(filterVal)) {
					// params.add('%' + filterVal + '%');
					where.append(" and " + filterKey + " like '%" + filterVal
							+ "%'");
				}
			}
		}

		where.append(" and a.fileNo = b.fileNo and a.status = 0 ");

	}

	/**
	 * 执行HQL
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	private List query(StringBuilder hql, List params) {
		return getHibernateTemplate().find(hql.toString(), params.toArray());
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	private List query(StringBuilder hql) {
		return getHibernateTemplate().find(hql.toString());
	}

	/**
	 * 乡镇汇总统计数据导出
	 * 
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public String dataExportByTownship(String startDay, String endDay)
			throws Exception {
		List dataList = new ArrayList();
		List headerList = new ArrayList();
		for (int i = 0; i < headerStatisticTownship.length; i++) {
			headerList.add(headerStatisticTownship[i]);
		}
		dataList.add(headerList);

		List list = reportService.getDataInfoByTownship(startDay, endDay);
		for (Object obj : list) {
			List l = (List) obj;
			dataList.add(l);
		}
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		return getDownloadURL()
				+ writeExecl(user.getDistrictId(), dataList, STATISTICTOWNSHIP,
						STATISTICHOSPITAL_TITLE);
	}

	/**
	 * 卫生院汇总统计数据导出
	 * 
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public String dataExportByHospital(String startDay, String endDay)
			throws Exception {
		List dataList = new ArrayList();
		List headerList = new ArrayList();
		for (int i = 0; i < headerStatisticTownship.length; i++) {
			headerList.add(headerStatisticTownship[i]);
		}
		dataList.add(headerList);

		List list = reportService.getDataInfoByHospital(startDay, endDay);
		for (Object obj : list) {
			List l = (List) obj;
			dataList.add(l);
		}
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		return getDownloadURL()
				+ writeExecl(user.getDistrictId(), dataList, STATISTICTOWNSHIP,
						STATISTICTOWNSHIP_TITLE);
	}

	/**
	 * 高危汇总统计
	 * 
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public String dataExportByHighRisk(String startDay, String endDay)
			throws Exception {
		List dataList = new ArrayList();
		List headerList = new ArrayList();
		for (int i = 0; i < headerStatisticHighRisk.length; i++) {
			headerList.add(headerStatisticHighRisk[i]);
		}
		dataList.add(headerList);

		List list = reportService.getHighRiskData(startDay, endDay);
		for (Object obj : list) {
			List l = (List) obj;
			List tmp = new ArrayList();
			for (int i = 1; i < l.size(); i++) {
				tmp.add(l.get(i));
			}
			dataList.add(tmp);
		}
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		return getDownloadURL()
				+ writeExecl(user.getDistrictId(), dataList, STATISTICHIGHRISK,
						STATISTICHIGHRISK_TITLE);
	}

	/**
	 * @param qryCond
	 * @return
	 */
	public String dataExportByHighRisk(QryCondition qryCond) throws Exception {
		List dataList = new ArrayList();
		List headerList = new ArrayList();
		for (int i = 0; i < headerHighRiskInfo.length; i++) {
			headerList.add(headerHighRiskInfo[i]);
		}
		dataList.add(headerList);

		List list = userMenuTreeService.getPrintHighRiskRecords(qryCond);

		for (Object obj : list) {
			List tmp = new ArrayList();
			Object[] objs = (Object[]) obj;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			WomanLastMedicalExamRecord record = (WomanLastMedicalExamRecord) objs[2];
			tmp.add(EncryptionUtils.decipher(file.getFileNo()));
			tmp.add(EncryptionUtils.decipher(file.getName()));
			tmp.add(person.getSex());
			tmp.add(CommonConvertUtils.dateToStringWithDelimiter(person
					.getBirthday()));
			tmp.add(EncryptionUtils.decipher(person.getIdnumber()));
			tmp.add(CommonConvertUtils.dateToStringWithDelimiter(record
					.getLastExamDate()));
			tmp.add(record.getHighRiskRemarks());
			tmp.add(file.getAddress());
			tmp.add(person.getLinkman());
			tmp.add(file.getTel());
			dataList.add(tmp);
		}
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		String url = getDownloadURL()
				+ writeExecl(user.getDistrictId(), dataList, HIGHRISKINFO,
						HIGHRISKINFO_TITLE);
		return url;
	}

	public String dataExportByChildHighRisk(QryCondition qryCond)
			throws Exception {
		List dataList = new ArrayList();
		List headerList = new ArrayList();
		for (int i = 0; i < headerChildHighRiskInfo.length; i++) {
			headerList.add(headerChildHighRiskInfo[i]);
		}
		dataList.add(headerList);

		List list = userMenuTreeService.getChildPrintHighRiskRecords(qryCond);

		for (Object obj : list) {
			List tmp = new ArrayList();
			Object[] objs = (Object[]) obj;
			HealthFile file = (HealthFile) objs[0];
			PersonalInfo person = (PersonalInfo) objs[1];
			ChildLastMedicalExamRecord record = (ChildLastMedicalExamRecord) objs[2];
			tmp.add(EncryptionUtils.decipher(file.getFileNo()));
			tmp.add(EncryptionUtils.decipher(file.getName()));
			tmp.add(person.getSex());
			tmp.add(CommonConvertUtils.dateToStringWithDelimiter(person
					.getBirthday()));
			tmp.add(EncryptionUtils.decipher(person.getIdnumber()));
			tmp.add(CommonConvertUtils.dateToStringWithDelimiter(record
					.getLastExamDate()));
			tmp.add(record.getHighRiskRemarks());
			tmp.add(file.getAddress());
			tmp.add(person.getLinkman());
			tmp.add(file.getTel());
			dataList.add(tmp);
		}
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		String url = getDownloadURL()
				+ writeExecl(user.getDistrictId(), dataList, CHILDHIGHRISKINFO,
						CHILDHIGHRISKINFO_TITLE);
		return url;
	}

	/**
	 * 月报表统计一
	 * 
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public String dataExportByMonth01(String startDay, String endDay)
			throws Exception {
		List dataList = new ArrayList();
		List headerList = new ArrayList();
		for (int i = 0; i < headerStatisticMonth01.length; i++) {
			headerList.add(headerStatisticMonth01[i]);
		}
		dataList.add(headerList);

		List list = reportService.getDataInfoByMonth01(startDay, endDay);
		for (Object obj : list) {
			List l = (List) obj;
			dataList.add(l);
		}
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		return getDownloadURL()
				+ writeExecl(user.getDistrictId(), dataList, STATISTICMONTH01,
						STATISTICMONTH01_TITLE);
	}

	public String dataExportByMonth02(String startDay, String endDay) {
		List dataList = new ArrayList();
		List headerList = new ArrayList();
		ExcelUtils col01 = new ExcelUtils(2, 1, "卫生院名称", new String[] {});
		ExcelUtils col02 = new ExcelUtils(1, 3, "城镇居民规范化电子建档", new String[] {
				"人数", "%" });
		ExcelUtils col03 = new ExcelUtils(1, 5, "农村居民规范化电子建档", new String[] {
				"人数", "%" });
		ExcelUtils col04 = new ExcelUtils(1, 7, "0-6岁儿童管理", new String[] {
				"人数", "%" });
		ExcelUtils col05 = new ExcelUtils(1, 9, "孕产妇系统管理", new String[] { "人数",
				"%" });
		ExcelUtils col06 = new ExcelUtils(1, 11, "老年人管理", new String[] { "人数",
				"%" });
		ExcelUtils col07 = new ExcelUtils(1, 13, "高血压管理", new String[] { "人数",
				"%" });
		ExcelUtils col08 = new ExcelUtils(1, 15, "糖尿病管理", new String[] { "人数",
				"%" });
		ExcelUtils col09 = new ExcelUtils(1, 17, "精神病管理", new String[] { "人数",
				"%" });
		headerList.add(col01);
		headerList.add(col02);
		headerList.add(col03);
		headerList.add(col04);
		headerList.add(col05);
		headerList.add(col06);
		headerList.add(col07);
		headerList.add(col08);
		headerList.add(col09);

		List list = reportService.getDataInfoByMonth02(startDay, endDay);
		for (Object obj : list) {
			List l = (List) obj;
			dataList.add(l);
		}
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		return getDownloadURL()
				+ writeExecl(user.getDistrictId(), dataList, headerList,
						STATISTICMONTH02, STATISTICMONTH02_TITLE);
	}

	/**
	 * 日统计报表数据导出
	 * 
	 * @param day
	 * @return
	 */
	public String dataExportByDay(String day) {
		List dataList = new ArrayList();
		List headerList = new ArrayList();
		ExcelUtils col01 = new ExcelUtils(2, 1, "卫生院名称", new String[] {});
		ExcelUtils col02 = new ExcelUtils(1, 5, "居民健康档案合计", new String[] {
				"常驻人口(人)", "累计建档(人)", "建档率%", "其中电子建档率%" });
		ExcelUtils col03 = new ExcelUtils(1, 11, "居民健康档案合计", new String[] {
				"城镇居民(人)", "累计建档（人）", "建档率%", "其中电子建档率%", "当日建档（人）",
				"当日电子建档（人）" });
		ExcelUtils col04 = new ExcelUtils(1, 17, "乡村居民建档情况", new String[] {
				"农村居民(人)", "累计建档（人）", "建档率%", "其中电子建档率%", "当日建档（人）",
				"当日电子建档（人）" });
		headerList.add(col01);
		headerList.add(col02);
		headerList.add(col03);
		headerList.add(col04);
		List list = reportService.getDataInfoByDay(day);
		for (Object obj : list) {
			List l = (List) obj;
			dataList.add(l);
		}
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		return getDownloadURL()
				+ writeExecl(user.getDistrictId(), dataList, headerList,
						STATISTICDAY, STATISTICDAY_TITLE);
	}

	/**
	 * 写Excel重构
	 * 
	 * @param disNo
	 * @param list
	 * @param headerList
	 * @param type
	 * @param title
	 * @return
	 */
	private String writeExecl(String disNo, List list,
			List<ExcelUtils> headerList, String type, String title) {
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		String fileName = DateToStr(new Date()) + "_" + disNo + "_"
				+ user.getUsername() + "_" + type + ".xls";
		File file = new File(getWebRootAbsolutePath() + "data/" + fileName);

		WritableWorkbook wwb = null;
		try {
			wwb = Workbook.createWorkbook(file);
			WritableSheet sheet = wwb.createSheet("sheet1", 1000000);
			Label labTitle = new Label(0, 0, title);
			sheet.addCell(labTitle);
			sheet.mergeCells(0, 0, ((List) list.get(0)).size() - 1, 0);

			int c = 0;
			int cSub = 0;
			for (ExcelUtils excel : headerList) {
				Label labHeader = new Label(cSub, 1, excel.getTitle());
				sheet.addCell(labHeader);
				int col = excel.getCol() - 1;
				int row = excel.getRow();
				sheet.mergeCells(cSub, 1, col, row);
				String[] titleSub = excel.getTitleSub();
				int len = titleSub.length;
				if (len > 0) {
					for (int i = 0; i < len; i++) {
						Label labHeaderSub = new Label(cSub, 2, titleSub[i]);
						sheet.addCell(labHeaderSub);
						cSub++;
					}
				} else {
					cSub++;
				}
				c++;
			}

			int i = 3;
			for (Object object : list) {
				List l = (List) object;
				for (int j = 0; j < l.size(); j++) {
					Label lab = new Label(j, i,
							isNull(String.valueOf(l.get(j))));
					sheet.addCell(lab);
				}
				i++;
			}
			wwb.write();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} finally {
			try {
				wwb.close();
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return fileName;
	}

	/**
	 * 判断是否为NULL
	 * 
	 * @param val
	 * @return
	 */
	private String isNull(String val) {
		if (val == null || val.equals("null") || val.equals("0"))
			return "";
		return val;
	}

	/**
	 * 出生登记月报表（新）
	 * 
	 * @param qry
	 * @return
	 */
	public String dataExportAllRegisterBaby(BirthCertifiQry qry) {
		List dataList = new ArrayList();
		List headerList = new ArrayList();
		List list = summaryService.printAllBirth(qry);
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		String orgName = user.getOrg().getName();
		String username = user.getTaxempname();
		ExcelUtils col01 = new ExcelUtils(1, 1, "卫生院名称",
				new String[] { "出生医学证明编号" });
		ExcelUtils col02 = new ExcelUtils(1, 4, orgName + "（公章）", new String[] {
				"新生儿姓名", "性别", "出生日期" });
		ExcelUtils col03 = new ExcelUtils(1, 5, "填表人", new String[] { "孕周" });
		ExcelUtils col04 = new ExcelUtils(1, 8, username, new String[] {
				"新生儿出生状况", "母亲姓名", "母亲身份证号码" });
		ExcelUtils col05 = new ExcelUtils(1, 9, "填表日期", new String[] { "父亲姓名" });
		ExcelUtils col06 = new ExcelUtils(1, 11, getCurrentDay(), new String[] {
				"父亲身份证号码", "家庭住址" });
		headerList.add(col01);
		headerList.add(col02);
		headerList.add(col03);
		headerList.add(col04);
		headerList.add(col05);
		headerList.add(col06);
		for (Object obj : list) {
			BirthCertificate birthCertifi = (BirthCertificate) obj;
			List tmp = new ArrayList();
			tmp.add(birthCertifi.getCertifiId());
			tmp.add(birthCertifi.getName());
			tmp.add(birthCertifi.getSex());
			tmp.add(DateToStr(birthCertifi.getBirthday()));
			tmp.add(birthCertifi.getBorthWeekly());
			tmp.add(birthCertifi.getHealthStatus());
			tmp.add(birthCertifi.getMotherName());
			tmp.add(birthCertifi.getMotherIdCard());
			tmp.add(birthCertifi.getFatherName());
			tmp.add(birthCertifi.getFatherIdCard());
			tmp.add(birthCertifi.getFamilyAddress());
			dataList.add(tmp);
		}
		return getDownloadURL()
				+ writeExecl(user.getDistrictId(), dataList, headerList,
						STATISTICREGISTERBABY, STATISTICREGISTERBABY_TITLE);
	}

	/**
	 * 出生登记月报表
	 * 
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public String dataExportRegisterBaby(String startDay, String endDay,
			String orgname, String orgid) {

		List dataList = new ArrayList();
		List headerList = new ArrayList();
		List list = reportService.getDataInfoByRegister(startDay, endDay,
				orgname, orgid);
		String orgName = (String) list.get(0);
		String username = (String) list.get(1);
		ExcelUtils col01 = new ExcelUtils(1, 1, "卫生院名称",
				new String[] { "出生医学证明编号" });
		ExcelUtils col02 = new ExcelUtils(1, 4, orgName + "（公章）", new String[] {
				"新生儿姓名", "性别", "出生日期" });
		ExcelUtils col03 = new ExcelUtils(1, 5, "填表人", new String[] { "孕周" });
		ExcelUtils col04 = new ExcelUtils(1, 8, username, new String[] {
				"新生儿出生状况", "母亲姓名", "母亲身份证号码" });
		ExcelUtils col05 = new ExcelUtils(1, 9, "填表日期", new String[] { "父亲姓名" });
		ExcelUtils col06 = new ExcelUtils(1, 11, getCurrentDay(), new String[] {
				"父亲身份证号码", "家庭住址" });
		headerList.add(col01);
		headerList.add(col02);
		headerList.add(col03);
		headerList.add(col04);
		headerList.add(col05);
		headerList.add(col06);
		List ldata = (List) list.get(2);
		for (Object obj : ldata) {
			BirthCertificate birthCertifi = (BirthCertificate) obj;
			List tmp = new ArrayList();
			tmp.add(birthCertifi.getCertifiId());
			tmp.add(birthCertifi.getName());
			tmp.add(birthCertifi.getSex());
			tmp.add(DateToStr(birthCertifi.getBirthday()));
			tmp.add(birthCertifi.getBorthWeekly());
			tmp.add(birthCertifi.getHealthStatus());
			tmp.add(birthCertifi.getMotherName());
			tmp.add(birthCertifi.getMotherIdCard());
			tmp.add(birthCertifi.getFatherName());
			tmp.add(birthCertifi.getFatherIdCard());
			tmp.add(birthCertifi.getFamilyAddress());
			dataList.add(tmp);
		}
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		return getDownloadURL()
				+ writeExecl(user.getDistrictId(), dataList, headerList,
						STATISTICREGISTERBABY, STATISTICREGISTERBABY_TITLE);
	}

	/**
	 * 获得文件下载地址
	 * 
	 * @return
	 */
	private String getDownloadURL() {
		String ret = sysInfos.getVal(2);
		if (!ret.substring(ret.length() - 1, ret.length()).equals("/")) {
			ret += "/";
		}
		return "/data/";
	}

	/**
	 * 获得当前日期并格式化
	 * 
	 * @return
	 */
	private String getCurrentDay() {
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		return format.format(now);
	}


	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Map<String, List> get_Export_Param(String type) throws Exception {
		Map<String, List> ret = new HashMap<String, List>();
		String where = "";
		if (type == null) {
			where = " 1=1 ";
		} else {
			where = " type=" + type;
		}
		List main = getSession().createQuery(
				"select id ,name,orgparamtype,pageable,pagesize from ExportMain where "
						+ where + " order by id").list();
		List sub = getSession().createQuery(
				"from ExportSub order by mainid , ord").list();
		ret.put("sub", sub);
		ret.put("main", main);
		List spottype = getSession()
				.createQuery(
						"from SpotcheckType where exists(select 1 from ExportMain  where id=mainid)  order by code ")
				.list();
		ret.put("spottype", spottype);
		return ret;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Map<String, List> get_Export_ParamById(String id) throws Exception {
		Map<String, List> ret = new HashMap<String, List>();
		String where = "";
		if (id == null) {
			where = " 1=1 ";
		} else {
			where = " id=" + id;
		}
		List main = getSession().createQuery(
				"select id ,name,orgparamtype,pageable,pagesize from ExportMain where "
						+ where + " order by id").list();
		List sub = getSession().createQuery(
				"from ExportSub order by mainid , ord").list();
		ret.put("sub", sub);
		ret.put("main", main);
		List spottype = getSession()
				.createQuery(
						"from SpotcheckType where exists(select 1 from ExportMain  where id=mainid)  order by code ")
				.list();
		ret.put("spottype", spottype);
		return ret;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public String sqlExport(String disid, String id, String name, Map params)
			throws Exception {

		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		String fileName = DateToStr(new Date()) + "_" + user.getUsername()
				+ "_" + id + ".xls";
		//
		HSSFWorkbook wb = new HSSFWorkbook();
		FileOutputStream fileOut = new FileOutputStream(
				getWebRootAbsolutePath() + "data/" + fileName);

		Sheet sheet1 = wb.createSheet(name);
		// No DataSource so we must handle Connections manually
		Connection conn = null;
		try {
			conn = getSession().connection();
			List sqllist = getSession().createQuery(
					"from ExportMain where id = " + id + " order by id").list();
			List sublist = getSession().createQuery(
					"from ExportSub where  mainid = " + id
							+ " order by mainid , ord").list();
			Map<Integer, ExportSub> submap = new HashMap();
			for (int i = 0; i < sublist.size(); i++) {
				ExportSub vo = (ExportSub) sublist.get(i);
				submap.put(vo.getId(), vo);
			}
			ExportMain main = (ExportMain) sqllist.get(0);
			String sql = main.getSql();
			for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
				Object key = iter.next();
				Object value = params.get(key);
				ExportSub vo = submap.get(Integer.parseInt((String) key));
				sql = sql + " and " + vo.getColstr();
			}
			sql = sql + " " + main.getGroupby() + " " + main.getOrderby();

			sql = sql.replaceAll("\"", "'");
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, disid + "%");
			int paramidx = 2;
			SimpleDateFormat inputfomart1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat inputfomart2 = new SimpleDateFormat("yyyyMMdd");
			for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
				Object key = iter.next();
				Object value = params.get(key);
				ExportSub vo = submap.get(Integer.parseInt((String) key));
				if (vo.getType().equals("date")) {
					stmt.setTimestamp(paramidx++, BusiUtils.parseDate((String) value));
				} else if (vo.getType().equals("string")) {
					stmt.setString(paramidx++, (String) value);
				} else {
					stmt.setFloat(paramidx++, Float.parseFloat((String) value));
				}
			}
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int numberOfColumns = rsMetaData.getColumnCount();
			int rowidx = 0;
			Row rowtitle = sheet1.createRow((short) rowidx++);

			int cellidx = 0;
			for (int i = 1; i <= numberOfColumns; i++) {
				Cell cell = rowtitle.createCell(cellidx++);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(rsMetaData.getColumnLabel(i));
			}
			SimpleDateFormat fomart = new SimpleDateFormat("yyyy-MM-dd");
			int count = 0;
			while (rs.next()) {
				count++;
				Row row = sheet1.createRow((short) rowidx++);
				cellidx = 0;
				for (int i = 1; i <= numberOfColumns; i++) {
					Class cls = Class.forName(rsMetaData.getColumnClassName(i));
					Cell cell = row.createCell(cellidx++);
					if (cls.isAssignableFrom(String.class)) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(rs.getString(i));
					} else if (cls.isAssignableFrom(Float.class)) {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(rs.getFloat(i));
					} else if (cls.isAssignableFrom(Integer.class)) {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(rs.getInt(i));
					} else if (cls.isAssignableFrom(Long.class)) {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(rs.getLong(i));
					} else if (cls.isAssignableFrom(Date.class)) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						Date obj = rs.getDate(i);
						cell.setCellValue(fomart.format(obj));
					} else if (cls.isAssignableFrom(java.sql.Timestamp.class)) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						Date obj = rs.getDate(i);
						cell.setCellValue(fomart.format(obj));
					}
				}
			}
			stmt.close();
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return getDownloadURL() + fileName;
	}

	private String getFilterSql(String filterKey, String filterVal)
			throws Exception {
		String ret = "";
		if (StringUtils.hasText(filterKey)) {
			if (filterKey.equals("a.name") || filterKey.equals("a.fileNo")) {
				filterVal = EncryptionUtils.encry(filterVal);
			} else if (filterKey.equals("a.inputDate")
					|| filterKey.equals("b.birthday")
					|| filterKey.equals("a.lastModifyDate")) {
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyyMMdd HH:mm:ss");
				try {
					Date startDate = null;
					Date endDate = null;
					if (filterVal.indexOf("-") > 0) {
						String[] valArray = filterVal.split("-");
						if (valArray.length > 2) {
							throw new Exception(
									"请输入正确的日期范围，如：20120101-20120102或者20120101。");
						}
						startDate = format.parse(valArray[0] + " 00:00:00");
						endDate = format.parse(valArray[1] + " 23:59:59");
					} else if (filterVal.indexOf("－") > 0) {
						String[] valArray = filterVal.split("－");
						if (valArray.length > 2) {
							throw new Exception(
									"请输入正确的日期范围，如：20120101-20120102或者20120101。");
						}
						startDate = format.parse(valArray[0] + " 00:00:00");
						endDate = format.parse(valArray[1] + " 23:59:59");
					} else {
						startDate = format.parse(filterVal + " 00:00:00");
						endDate = format.parse(filterVal + " 23:59:59");
					}
					// params.add(startDate);
					// params.add(endDate);
					ret += (" and " + filterKey + " between '"
							+ format.format(startDate) + "' and  '"
							+ format.format(endDate) + "' ");
				} catch (ParseException e) {
					throw new Exception(
							"请输入正确的日期范围，如：20120101-20120102或者20120101。");
				}
			} else if (filterKey.equals("c.highRisk")) {
				if (StringUtils.hasText(filterVal)) {
					// params.add(filterVal);
					ret += (" and " + filterKey + " = '" + filterVal + "'");
				}
			} else {
				if (StringUtils.hasText(filterVal)) {
					// params.add('%' + filterVal + '%');
					ret += (" and " + filterKey + " like '%" + filterVal + "%'");
				}
			}
		}
		return ret;

	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public String sqlExportCsv(String disid, String name, String filterKey,
			String filterVal) throws Exception {
		while (disid.endsWith("00")) {
			disid = disid.substring(0, disid.length() - 2);
		}
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		String fileName = DateToStr(new Date()) + "_" + disid + "_"
				+ user.getUsername() + "_" + name + ".csv";
		PrintWriter out = null;
		Connection conn = null;
		try {
			out = new PrintWriter(getWebRootAbsolutePath() + "data/" + fileName);
			conn = getSession().connection();
			List sqllist = getSession().createQuery(
					"from ExportMain where name = '" + name + "' order by id")
					.list();
			if (sqllist.size() == 0) {
				throw new Exception(name + "-导出功能未配置,请与系统管理员联系!");
			}
			ExportMain main = (ExportMain) sqllist.get(0);
			String sql = main.getSql();
			sql = sql + getFilterSql(filterKey, filterVal);
			sql += " and emp.org_id = " + user.getOrgId() + " ";
			sql = sql + " " + main.getGroupby() + " " + main.getOrderby();

			sql = sql.replaceAll("\"", "'");
			System.out.println("==sql==" + sql);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, disid + "%");
			int paramidx = 2;
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int numberOfColumns = rsMetaData.getColumnCount();
			int rowidx = 0;

			int cellidx = 0;
			for (int i = 1; i <= numberOfColumns; i++) {
				if (i == 1) {
					out.write(rsMetaData.getColumnLabel(i));
				} else {
					out.write("," + rsMetaData.getColumnLabel(i));
				}
			}
			out.write("\r\n");
			System.out.println("==disid==" + disid);
			SimpleDateFormat fomart = new SimpleDateFormat("yyyy-MM-dd");
			int count = 0;
			while (rs.next()) {
				System.out.println("==????==");
				count++;
				cellidx = 0;
				for (int i = 1; i <= numberOfColumns; i++) {
					Class cls = Class.forName(rsMetaData.getColumnClassName(i));
					if (i == 1) {
						if (cls.isAssignableFrom(String.class)) {
							out.write(format(rs.getString(i)));
						} else if (cls.isAssignableFrom(Float.class)) {
							out.write(format(String.valueOf(rs.getFloat(i))));
						} else if (cls.isAssignableFrom(Integer.class)) {
							out.write(format(String.valueOf(rs.getInt(i))));
						} else if (cls.isAssignableFrom(Long.class)) {
							out.write(format(String.valueOf(rs.getLong(i))));
						} else if (cls.isAssignableFrom(Date.class)) {
							Date obj = rs.getDate(i);
							out.write(format(fomart.format(obj)));
						} else if (cls
								.isAssignableFrom(java.sql.Timestamp.class)) {
							Date obj = rs.getDate(i);
							out.write(format(fomart.format(obj)));
						}
					} else {
						if (cls.isAssignableFrom(String.class)) {
							out.write("," + format(rs.getString(i)));
						} else if (cls.isAssignableFrom(Float.class)) {
							out.write(","
									+ format(String.valueOf(rs.getFloat(i))));
						} else if (cls.isAssignableFrom(Integer.class)) {
							out.write(","
									+ format(String.valueOf(rs.getInt(i))));
						} else if (cls.isAssignableFrom(Long.class)) {
							out.write(","
									+ format(String.valueOf(rs.getLong(i))));
						} else if (cls.isAssignableFrom(Date.class)) {
							Date obj = rs.getDate(i);
							out.write("," + format(fomart.format(obj)));
						} else if (cls
								.isAssignableFrom(java.sql.Timestamp.class)) {
							Date obj = rs.getDate(i);
							out.write("," + format(fomart.format(obj)));
						}
					}
				}
				out.write("\r\n");
			}
			stmt.close();

		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (out != null) {
				out.close();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return getDownloadURL() + fileName;
	}

	// String examname, String userdistrict, Map<String, Map> params,
	// Map<String, Map<String, String>> basemap, List<String> collist
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Map sqlList(String disid, String id, Map params) throws Exception {
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		// No DataSource so we must handle Connections manually
		Connection conn = null;
		try {
			conn = getSession().connection();
			List sqllist = getSession().createQuery(
					"from ExportMain where id = " + id + " order by id").list();
			List sublist = getSession().createQuery(
					"from ExportSub where  mainid = " + id
							+ " order by mainid , ord").list();
			Map<Integer, ExportSub> submap = new HashMap();
			for (int i = 0; i < sublist.size(); i++) {
				ExportSub vo = (ExportSub) sublist.get(i);
				submap.put(vo.getId(), vo);
			}
			ExportMain main = (ExportMain) sqllist.get(0);
			String sql = main.getSql();
			for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
				Object key = iter.next();
				ExportSub vo = submap.get(Integer.parseInt((String) key));
				sql = sql + " and " + vo.getColstr();
			}
			sql = sql + " " + main.getGroupby() + " " + main.getOrderby();

			sql = sql.replaceAll("\"", "'");
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, disid + "%");
			int paramidx = 2;
			SimpleDateFormat inputfomart2 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat inputfomarttime = new SimpleDateFormat(
					"yyyyMMdd hh:mm:ss");

			for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
				Object key = iter.next();
				Object value = params.get(key);
				ExportSub vo = submap.get(Integer.parseInt((String) key));
				if (vo.getType().equals("date")) {
					stmt.setTimestamp(paramidx++, BusiUtils.parseDate((String) value));
				} else if (vo.getType().equals("time")) {
					stmt.setDate(paramidx++, new java.sql.Date(inputfomarttime
							.parse((String) value + " 00:00:00").getTime()));
				} else if (vo.getType().equals("string")) {
					stmt.setString(paramidx++, (String) value);
				} else {
					stmt.setFloat(paramidx++, Float.parseFloat((String) value));
				}
			}
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int numberOfColumns = rsMetaData.getColumnCount();
			SimpleDateFormat fomart = new SimpleDateFormat("yyyy-MM-dd");
			List retlist = new ArrayList();
			while (rs.next()) {
				// List row = new ArrayList();
				Map row = new HashMap();
				for (int i = 1; i <= numberOfColumns; i++) {
					Class cls = Class.forName(rsMetaData.getColumnClassName(i));
					if (cls.isAssignableFrom(String.class)) {
						// row.add(rs.getString(i));
						row.put("col" + i, rs.getString(i));
					} else if (cls.isAssignableFrom(Float.class)) {
						// row.add(rs.getFloat(i));
						row.put("col" + i, rs.getFloat(i));
					} else if (cls.isAssignableFrom(Integer.class)) {
						// row.add(rs.getFloat(i));
						row.put("col" + i, rs.getInt(i));
					} else if (cls.isAssignableFrom(Long.class)) {
						// row.add(rs.getFloat(i));
						row.put("col" + i, rs.getLong(i));
					} else if (cls.isAssignableFrom(Date.class)) {
						Date obj = rs.getDate(i);
						// row.add(fomart.format(obj));
						row.put("col" + i, fomart.format(obj));
					} else if (cls.isAssignableFrom(java.sql.Timestamp.class)) {
						Date obj = rs.getDate(i);
						// row.add(fomart.format(obj));
						row.put("col" + i, fomart.format(obj));
					}
				}
				retlist.add(row);
			}

			Map ret = new HashMap();
			ret.put("rows", retlist);
			ret.put("currentpage", 1);
			ret.put("total", 1);
			ret.put("pages", 1);
			stmt.close();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List sqlListHead(String id) throws Exception {
		Connection conn = null;
		try {
			conn = getSession().connection();
			List sqllist = getSession().createQuery(
					"from ExportMain where id = " + id + " order by id").list();
			ExportMain main = (ExportMain) sqllist.get(0);
			String sql = main.getSql();
			if(!DataExportService.isproc(main.getSql())){
				sql = sql + " and 1=2 " + main.getGroupby() + " "
						+ main.getOrderby();
			}
			sql = sql.replaceAll("\"", "'");
			
			System.out.println("===sql========="+sql);
			PreparedStatement  stmt = null ;
			if(DataExportService.isproc(main.getSql())){
				 stmt = conn.prepareCall(sql);
			}else{
				 stmt = conn.prepareStatement(sql);
			}
			int idx = sql.indexOf("?");
			int idxbegin = 1;
			while (idx > 0) {
				System.out.println("============"+idx);
				stmt.setString(idxbegin++, null);
				idx = sql.indexOf("?",idx+1);
			}
//			if(DataExportService.isproc(main.getSql())){
//				int paramsnum = Integer.parseInt(main.getOrderby());
//				for(int i = 1 ;i <paramsnum ;i++){
//					stmt.setObject(i+1, null);
//				}
//			}
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int numberOfColumns = rsMetaData.getColumnCount();
			List retlist = new ArrayList();
			for (int i = 1; i <= numberOfColumns; i++) {
				Map colmap = new HashMap();
				colmap.put("field", "col" + i);
				String title = rsMetaData.getColumnLabel(i);
				System.out.println("========title====" + title);
				if (title.trim().toLowerCase().startsWith("button:")) {
					title = title.trim().substring(7);
					colmap.put("format", "buttonColumn");
				} else if (title.trim().toLowerCase().startsWith("funcbutton:")) {
					title = title.trim().substring(11);
					colmap.put("format", "funcButtonColumn");
					System.out.println("========1111====");
				}
				colmap.put("title", title);
				retlist.add(colmap);
			}
			stmt.close();
			return retlist;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Map getQuestionsByOrg() {
		Map ret = new HashMap();
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		if (getSession()
				.createSQLQuery(
						"select 1  from net_quests where orgid = "
								+ user.getOrgId() + " ").list().size() > 0) {
			ret.put("needquest", false);
		} else {
			ret.put("needquest", true);
		}
		return ret;
	}

	@Transactional
	public Map saveQuestion(Map params) {
		Map ret = new HashMap();
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		String nettype = (String) params.get("nettype");
		String netspeed = (String) params.get("netspeed");
		String usespeed = (String) params.get("usespeed");
		String usernum = (String) params.get("usernum");
		if (getSession()
				.createSQLQuery(
						"select 1  from net_quests where orgid = '"
								+ user.getOrgId() + "' ").list().size() > 0) {
			ret.put("saved", false);
			ret.put("msg", "此用户已完成调查!(请刷新页面或重新登录来关闭调查页面)");
		} else {
			String sql = " insert into net_quests(loginname,nettype,netspeed,usespeed,usernum,orgid)values('"
					+ user.getUsername()
					+ "','"
					+ nettype
					+ "',"
					+ netspeed
					+ ",'"
					+ usespeed
					+ "',"
					+ usernum
					+ ","
					+ user.getOrgId()
					+ ")";
			getSession().createSQLQuery(sql).executeUpdate();
			ret.put("saved", true);
		}
		return ret;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Map sqlListnew(String orgs, String id, Map params, Map pager)
			throws Exception {
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		// No DataSource so we must handle Connections manually
		while (orgs.endsWith("00")) {
			orgs = orgs.substring(0, orgs.length() - 2);
		}
		SimpleDateFormat fomart = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat fomarttime = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat fomarttime1 = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss.SSS");
		try {
			Connection conn = getSession().connection();
			List sqllist = getSession().createQuery(
					"from ExportMain where id = " + id + " order by id").list();
			List sublist = getSession().createQuery(
					"from ExportSub where  mainid = " + id
							+ " order by mainid , ord").list();
			Map<String, ExportSub> submap = new HashMap();
			for (int i = 0; i < sublist.size(); i++) {
				ExportSub vo = (ExportSub) sublist.get(i);
				submap.put(vo.getCode(), vo);
			}
			ExportMain main = (ExportMain) sqllist.get(0);
			String sql = main.getSql();
			String mainsql = sql;
			String countsql = "";
			String pagesql = "";
			if(!DataExportService.isproc(main.getSql())){
				sql += " and emp.org_id = " + user.getOrgId() + " ";
				if ("in".equals(main.getOrgparamtype())) {
					sql = sql.replaceAll("\\?", orgs);
				}
				mainsql = sql;
				for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
					Object key = iter.next();
					if (submap.containsKey(key)) {
						ExportSub vo = submap.get(key);
						if (vo.getType().equals("exists")) {
							if ("1".equals(params.get(key).toString())) {
								sql = sql + " and not " + vo.getColstr();
							} else if ("2".equals(params.get(key).toString())) {
								sql = sql + " and  " + vo.getColstr();
							} else if ("0".equals(params.get(key).toString())) {
								// 不加入
							}
						} else if (vo.getType().equals("select")) {
							String value = params.get(key).toString();
							Gson gs = new Gson();
							Map options = gs
									.fromJson(vo.getColstr(), HashMap.class);
							sql = sql + " and  " + options.get(value);
						} else {
							sql = sql + " and  " + vo.getColstr();
						}
					}
				}
				countsql = sql.replaceAll("\"", "'");
				countsql = " select count(*) "
						+ sql.substring(sql.toLowerCase().indexOf("from"));
				pagesql = sql.replaceAll("\"", "'");
				sql = sql + " " + main.getGroupby() + " " + main.getOrderby();
				sql = sql.replaceAll("\"", "'");
			}
			int paramidx = 1;
			Map ret = new HashMap();
			if (!main.getPageable()) {
				PreparedStatement stmt = conn.prepareStatement(sql,
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				if ("like".equals(main.getOrgparamtype())) {
					stmt.setString(paramidx++, orgs + "%");
				} else if (mainsql.indexOf("?") > 0) {
					stmt.setString(paramidx++, orgs);
				} else {

				}
				if(DataExportService.isproc(main.getSql())){
					for(int i = 0 ;i < sublist.size() ;i++){
						ExportSub sub = (ExportSub)sublist.get(i);
						System.out.println("============"+paramidx +"=="+params.get(sub.getCode()));
						if(params.containsKey(sub.getCode())){
							stmt.setObject(paramidx++, params.get(sub.getCode()));
						}else{
							stmt.setObject(paramidx++, null);
						}
					}
				}else{
					for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
						Object key = iter.next();
						if (submap.containsKey(key)) {
							Object value = params.get(key);
							ExportSub vo = submap.get(key);
							if (vo.getType().equals("date")) {
								stmt.setTimestamp(paramidx++, BusiUtils.parseDate((String) value));
							} else if (vo.getType().equals("time")) {
								stmt.setDate(
										paramidx++,
										new java.sql.Date(fomarttime.parse(
												(String) value + " 00:00:00")
												.getTime()));
							} else if (vo.getType().equals("string")) {
								stmt.setString(paramidx++, (String) value);
							} else if (vo.getType().equals("exists")) {
								// 没有参数
							} else {
								stmt.setFloat(paramidx++,
										Float.parseFloat((String) value));
							}
						}
					}
				}
				ResultSet rs = stmt.executeQuery();
				ResultSetMetaData rsMetaData = rs.getMetaData();
				int numberOfColumns = rsMetaData.getColumnCount();

				List retlist = new ArrayList();
				// 移动到最后一行,得到总数
				rs.last();
				int rowcount = rs.getRow();
				System.out.println("======rowcount======"+rowcount);
				boolean flag = rs.first();
				// 移动到取数位置
				while (flag) {
					// List row = new ArrayList();
					Map row = new HashMap();
					for (int i = 1; i <= numberOfColumns; i++) {
						Class cls = Class.forName(rsMetaData
								.getColumnClassName(i));
						if (cls.isAssignableFrom(String.class)) {
							row.put("col" + i, (rs.getString(i)));
						} else if (cls.isAssignableFrom(Float.class)) {
							row.put("col" + i, rs.getFloat(i));
						} else if (cls.isAssignableFrom(Integer.class)) {
							row.put("col" + i, rs.getInt(i));
						} else if (cls.isAssignableFrom(Long.class)) {
							row.put("col" + i, rs.getLong(i));
						} else if (cls.isAssignableFrom(Date.class)) {
							Date obj = rs.getDate(i);
							row.put("col" + i, fomart.format(obj));
						} else if (cls
								.isAssignableFrom(java.sql.Timestamp.class)) {
							Date obj = rs.getDate(i);
							row.put("col" + i, fomart.format(obj));
						} else {
							Object obj = rs.getObject(i);
							row.put("col" + i, (obj));
						}
					}
					retlist.add(row);
					flag = rs.next();
				}
				System.out.println("============"+new Gson().toJson(retlist));
				ret.put("rows", retlist);
				stmt.close();
			} else {
				SQLQuery countquery = getSession().createSQLQuery(countsql);
				if ("like".equals(main.getOrgparamtype())) {
					countquery.setString(0, orgs + "%");
					paramidx = 1;
				} else if (countsql.indexOf("\\?") > 0) {
					countquery.setString(0, orgs);
					paramidx = 1;
				} else {

				}
				for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
					Object key = iter.next();
					if (submap.containsKey(key)) {
						Object value = params.get(key);
						ExportSub vo = submap.get(key);
						if (vo.getType().equals("date")) {
							countquery.setTimestamp(paramidx++, BusiUtils.parseDate((String) value));
						} else if (vo.getType().equals("time")) {
							countquery.setDate(
									paramidx++,
									new java.sql.Date(fomarttime.parse(
											(String) value + " 00:00:00")
											.getTime()));
						} else if (vo.getType().equals("string")) {
							countquery.setString(paramidx++, (String) value);
						} else if (vo.getType().equals("exists")
								|| vo.getType().equals("select")) {
							// 没有参数
						} else {
							countquery.setFloat(paramidx++,
									Float.parseFloat((String) value));
						}
					}
				}
				int rowcount = (Integer) countquery.uniqueResult();
				int pageNum = Integer
						.parseInt((String) pager.get("pagenumber"));
				int rowsNum = Integer.parseInt((String) pager.get("pagesize"));
				int pagecount = rowcount / rowsNum;
				int startNum = (pageNum - 1) * rowsNum + 1;
				if (startNum > rowcount) {
					startNum = 1;
					pageNum = 1;
				}
				int endNum = startNum + rowsNum;
				String selecttxt = pagesql.substring(pagesql.toLowerCase()
						.indexOf("select") + 6,
						pagesql.toLowerCase().indexOf("from"));
				String fromtxt = pagesql.substring(pagesql.toLowerCase()
						.indexOf("from") + 4);
				pagesql = " SELECT * FROM  ( SELECT  * FROM (SELECT TOP "
						+ (endNum - 1) + " row_number() over("
						+ main.getOrderby() + ") rownum, " + selecttxt
						+ " from " + fromtxt + " " + main.getOrderby()
						+ ")zzzz where rownum>=" + (startNum)
						+ " )zzzzz order by rownum";
				PreparedStatement stmt = conn.prepareStatement(pagesql,
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				if ("like".equals(main.getOrgparamtype())) {
					stmt.setString(1, orgs + "%");
					paramidx = 2;
				} else if (pagesql.indexOf("?") > 0) {
					stmt.setString(1, orgs);
					paramidx = 2;
				} else {

				}
				for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
					Object key = iter.next();
					if (submap.containsKey(key)) {
						Object value = params.get(key);
						ExportSub vo = submap.get(key);
						if (vo.getType().equals("date")) {
							stmt.setTimestamp(paramidx++, BusiUtils.parseDate((String) value));
						} else if (vo.getType().equals("time")) {
							stmt.setDate(
									paramidx++,
									new java.sql.Date(fomarttime.parse(
											(String) value + " 00:00:00")
											.getTime()));
						} else if (vo.getType().equals("string")) {
							stmt.setString(paramidx++, (String) value);
						} else if (vo.getType().equals("exists")
								|| vo.getType().equals("select")) {
							// 没有参数
						} else {
							stmt.setFloat(paramidx++,
									Float.parseFloat((String) value));
						}
					}
				}
				ResultSet rs = stmt.executeQuery();
				ResultSetMetaData rsMetaData = rs.getMetaData();
				int numberOfColumns = rsMetaData.getColumnCount();
				List retlist = new ArrayList();
				// 移动到取数位置
				while (rs.next()) {
					// List row = new ArrayList();
					Map row = new HashMap();
					for (int i = 1; i <= numberOfColumns; i++) {
						Class cls = Class.forName(rsMetaData
								.getColumnClassName(i));
						if (cls.isAssignableFrom(String.class)) {
							row.put("col" + (i - 1), (rs.getString(i)));
						} else if (cls.isAssignableFrom(Float.class)) {
							row.put("col" + (i - 1), rs.getFloat(i));
						} else if (cls.isAssignableFrom(Integer.class)) {
							row.put("col" + (i - 1), rs.getInt(i));
						} else if (cls.isAssignableFrom(Long.class)) {
							row.put("col" + (i - 1), rs.getLong(i));
						} else if (cls.isAssignableFrom(Date.class)) {
							Date obj = rs.getDate(i);
							row.put("col" + (i - 1), fomart.format(obj));
						} else if (cls
								.isAssignableFrom(java.sql.Timestamp.class)) {
							Date obj = rs.getDate(i);
							row.put("col" + (i - 1), fomart.format(obj));
						} else {
							Object obj = rs.getObject(i);
							row.put("col" + (i - 1), (obj));
						}
					}
					retlist.add(row);
				}
				ret.put("rows", retlist);
				ret.put("currentpage", pageNum);
				ret.put("total", rowcount);
				ret.put("pages", pagecount);
				stmt.close();
			}
			conn.close();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public ExportDiv getDiv(String id) throws Exception {
		ExportDiv div = (ExportDiv) getSession().get(ExportDiv.class,
				Integer.parseInt(id));
		return div;
	}

	@Transactional
	public Map saveTable(String tablename, Map olddata, String jsonid)
			throws Exception {
		try {
			TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
					.currentOperator();
			Gson gs = new Gson();
			ExportJson json = (ExportJson) getSession().get(ExportJson.class,
					jsonid);
			System.out.println("============" + json.getJson());
			String jsonstr = json.getJson();
			// if(jsonstr.trim().startsWith("{")){
			// jsonstr = "("+jsonstr+")";
			// }
			System.out.println("============" + jsonstr);
			Map jsonmap = gs.fromJson(jsonstr, HashMap.class);

			List ids = (List) jsonmap.get("pk");

			Map ret = new HashMap();
			System.out.println("============"
					+ BaseParamUtil.hasId(ids, olddata));
			if (!BaseParamUtil.hasId(ids, olddata)) {
				List addparam = (List) jsonmap.get("addparam");
				Map data = BaseParamUtil.makeBaseParam(addparam);
				data.putAll(olddata);
				// BeanUtils.copyProperties(olddata,data);
				// 如果id存在,则
				String cols = "";
				String values = "";
				for (Iterator iter = data.keySet().iterator(); iter.hasNext();) {
					Object key = iter.next();
					cols += "," + key;
					values += ",'"
							+ String.valueOf(data.get(key)).replaceAll("'",
									"''") + "'";
				}
				String sql = " insert into " + tablename + "("
						+ cols.substring(1) + ") values(" + values.substring(1)
						+ ")";
				int result = getSession().createSQLQuery(sql).executeUpdate();
				if (result > 0) {
					ret.putAll(data);
					ret.put("success", true);
					ret.put("msg", "保存成功!");
				} else {
					ret.put("success", false);
					ret.put("msg", "保存失败!");
				}
			} else {
				List editparam = (List) jsonmap.get("editparam");
				Map data = BaseParamUtil.makeBaseParam(editparam);
				data.putAll(olddata);
				String updates = "";
				for (Iterator iter = data.keySet().iterator(); iter.hasNext();) {
					Object key = iter.next();
					updates += ","
							+ key
							+ "='"
							+ String.valueOf(data.get(key)).replaceAll("'",
									"''") + "'";
				}
				String sql = " update  " + tablename + " set "
						+ updates.substring(1) + " where id = '"
						+ data.get("id") + "' ";
				int result = getSession().createSQLQuery(sql).executeUpdate();
				if (result > 0) {
					ret.putAll(data);
					ret.put("success", true);
					ret.put("msg", "保存成功!");
				} else {
					ret.put("success", false);
					ret.put("msg", "保存失败!");
				}
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List getList(String tablename, Map params, String inputdate)
			throws Exception {
		System.out.println("=======params=====" + params);
		String where = "";
		for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
			Object key = iter.next();
			where += "," + key + "='" + params.get(key) + "'";
		}
		System.out.println("============" + " select * from " + tablename
				+ " where " + where.substring(1).replaceAll(",", " and ")
				+ " order by " + inputdate);
		List ret = getSession()
				.createSQLQuery(
						" select * from " + tablename + " where "
								+ where.substring(1).replaceAll(",", " and ")
								+ " order by " + inputdate)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return procList(ret);
	}

	private List procList(List data) {
		List ret = new ArrayList();
		SimpleDateFormat fomarttime = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < data.size(); i++) {
			Map row = (Map) data.get(i);
			for (Iterator iter = row.keySet().iterator(); iter.hasNext();) {
				Object col = iter.next();
				Object value = row.get(col);
				if (value instanceof Date || value instanceof Timestamp) {
					row.put(col, fomarttime.format(value));
				}
			}
		}
		return data;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Map get_task_Param() throws Exception {
		Map ret = new HashMap();
		String where = "";
		List main = getSession()
				.createSQLQuery("select id ,name from task_code  order by id")
				.addScalar("id", Hibernate.STRING)
				.addScalar("name", Hibernate.STRING).list();
		ret.put("task_code",main);
		List task_code_detail = getSession()
				.createSQLQuery("select id,col,tablename,valuetype,coltext from task_code_detail  order by id")
				.addScalar("id", Hibernate.STRING)
				.addScalar("col", Hibernate.STRING)
				.addScalar("tablename", Hibernate.STRING)
				.addScalar("valuetype", Hibernate.STRING)
				.addScalar("coltext", Hibernate.STRING).list();
		Map detailmap = new HashMap();
		for(int i =0; i<task_code_detail.size();i++){
			Map row = new HashMap();
			Object[] item = (Object[])task_code_detail.get(i);
			row.put("id", item[0]);
			row.put("col", item[1]);
			row.put("tablename", item[2]);
			row.put("valuetype", item[3]);
			row.put("coltext", item[4]);
			if(!detailmap.containsKey(item[0])){
				detailmap.put(item[0], new ArrayList());
			}
			List idlist = (List)detailmap.get(item[0]);
			idlist.add(row);
		}
		ret.put("task_code_detail",detailmap);
		
		return ret;
	}

	public Map saveTask(Map params) {
		Map ret = new HashMap();
		if (params.containsKey("id") && params.get("id") != null
				&& ((String) params.get("id")).trim().length() > 0) {
			// update
			String update = "update task_task set ";
			String sql = "";
			for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				Object value = params.get(key);
				if (!"id".equals(key)) {
					sql += "," + key + "='" + value + "'";
				}
			}
			update = update + sql.substring(1) + " where id = '"
					+ params.get("id") + "'";
			System.out.println("=====update=======" + update);
			getSession().createSQLQuery(update).executeUpdate();
		} else {
			// insert
			SimpleDateFormat sf = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss.SSS");
			String id = UUID.randomUUID().toString();
			params.put("id", id);
			params.put("inputdate", sf.format(new Date()));
			// update
			String insert = "insert into  task_task ( ";
			String cols = "";
			String values = "";
			for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				Object value = params.get(key);
				cols += "," + key;
				values += ",'" + value + "'";

			}
			insert = insert + cols.substring(1) + " ) values ("
					+ values.substring(1) + ")";
			getSession().createSQLQuery(insert).executeUpdate();

		}
		// SQLQuery query = getSession().createSQLQuery();
		ret.put("msg", "保存成功");
		return ret;
	}
	
	public Map delTask(Map params) {
		Map ret = new HashMap();
		if (params.containsKey("id") && params.get("id") != null
				&& ((String) params.get("id")).trim().length() > 0) {
			// update
			String delsql = "delete task_task where id = '"
					+ params.get("id") + "'";
			System.out.println("=======del====="+delsql);
			getSession().createSQLQuery(delsql).executeUpdate();
		}
		// SQLQuery query = getSession().createSQLQuery();
		ret.put("msg", "删除成功");
		ret.put("success", true);
		return ret;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Map querytask(String orgs, String id, Map params, Map pager)
			throws Exception {
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		System.out.println("==========111==");
		// No DataSource so we must handle Connections manually
		while (orgs.endsWith("00")) {
			orgs = orgs.substring(0, orgs.length() - 2);
		}
		SimpleDateFormat fomart = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat fomarttime = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat fomarttime1 = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss.SSS");
		try {
			Connection conn = getSession().connection();
			List sqllist = getSession().createQuery(
					"from ExportMain where id = " + id + " order by id").list();
			List sublist = getSession().createQuery(
					"from ExportSub where  mainid = " + id
							+ " order by mainid , ord").list();
			Map<String, ExportSub> submap = new HashMap();
			for (int i = 0; i < sublist.size(); i++) {
				ExportSub vo = (ExportSub) sublist.get(i);
				submap.put(vo.getCode(), vo);
			}
			ExportMain main = (ExportMain) sqllist.get(0);
			String sql = main.getSql();
			sql += " and emp.org_id = " + user.getOrgId() + " ";
			if ("in".equals(main.getOrgparamtype())) {
				sql = sql.replaceAll("\\?", orgs);
			}
			String mainsql = sql;
			for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
				Object key = iter.next();
				if (submap.containsKey(key)) {
					ExportSub vo = submap.get(key);
					if (vo.getType().equals("exists")) {
						if ("1".equals(params.get(key).toString())) {
							sql = sql + " and not " + vo.getColstr();
						} else if ("2".equals(params.get(key).toString())) {
							sql = sql + " and  " + vo.getColstr();
						} else if ("0".equals(params.get(key).toString())) {
							// 不加入
						}
					} else if (vo.getType().equals("select")) {
						String value = params.get(key).toString();
						Gson gs = new Gson();
						Map options = gs
								.fromJson(vo.getColstr(), HashMap.class);
						sql = sql + " and  " + options.get(value);
					} else {
						sql = sql + " and  " + vo.getColstr();
					}
				}
			}
			String countsql = sql.replaceAll("\"", "'");
			countsql = " select count(*) "
					+ sql.substring(sql.toLowerCase().indexOf("from"));
			String pagesql = sql.replaceAll("\"", "'");
			sql = sql + " " + main.getGroupby() + " " + main.getOrderby();
			sql = sql.replaceAll("\"", "'");
			int paramidx = 1;
			Map ret = new HashMap();
			if (!main.getPageable()) {
				PreparedStatement stmt = conn.prepareStatement(sql,
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				if ("like".equals(main.getOrgparamtype())) {
					stmt.setString(1, orgs + "%");
					paramidx = 2;
				} else if (mainsql.indexOf("?") > 0) {
					stmt.setString(1, orgs);
					paramidx = 2;
				} else {

				}
				for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
					Object key = iter.next();
					if (submap.containsKey(key)) {
						Object value = params.get(key);
						ExportSub vo = submap.get(key);
						if (vo.getType().equals("date")) {
							stmt.setTimestamp(paramidx++, BusiUtils.parseDate((String) value));
						} else if (vo.getType().equals("time")) {
							stmt.setDate(
									paramidx++,
									new java.sql.Date(fomarttime.parse(
											(String) value + " 00:00:00")
											.getTime()));
						} else if (vo.getType().equals("string")) {
							stmt.setString(paramidx++, (String) value);
						} else if (vo.getType().equals("exists")) {
							// 没有参数
						} else {
							stmt.setFloat(paramidx++,
									Float.parseFloat((String) value));
						}
					}
				}
				ResultSet rs = stmt.executeQuery();
				ResultSetMetaData rsMetaData = rs.getMetaData();
				int numberOfColumns = rsMetaData.getColumnCount();

				List retlist = new ArrayList();
				// 移动到最后一行,得到总数
				rs.last();
				int rowcount = rs.getRow();
				// 移动到取数位置
				while (rs.next()) {
					// List row = new ArrayList();
					Map row = new HashMap();
					for (int i = 1; i <= numberOfColumns; i++) {
						Class cls = Class.forName(rsMetaData
								.getColumnClassName(i));
						if (cls.isAssignableFrom(Date.class)) {
							Date obj = rs.getDate(i);
							row.put(rsMetaData.getColumnLabel(i), fomart.format(obj));
						} else if (cls
								.isAssignableFrom(java.sql.Timestamp.class)) {
							Date obj = rs.getDate(i);
							row.put(rsMetaData.getColumnLabel(i), fomart.format(obj));
						} else {
							Object obj = rs.getObject(i);
							row.put(rsMetaData.getColumnLabel(i), (obj));
						}
					}
					retlist.add(row);
				}
				ret.put("rows", retlist);
				stmt.close();
			} else {
				SQLQuery countquery = getSession().createSQLQuery(countsql);
				if ("like".equals(main.getOrgparamtype())) {
					countquery.setString(0, orgs + "%");
					paramidx = 1;
				} else if (countsql.indexOf("\\?") > 0) {
					countquery.setString(0, orgs);
					paramidx = 1;
				} else {

				}
				for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
					Object key = iter.next();
					if (submap.containsKey(key)) {
						Object value = params.get(key);
						ExportSub vo = submap.get(key);
						if (vo.getType().equals("date")) {
							countquery.setTimestamp(paramidx++, BusiUtils.parseDate((String) value));
						} else if (vo.getType().equals("time")) {
							countquery.setDate(
									paramidx++,
									new java.sql.Date(fomarttime.parse(
											(String) value + " 00:00:00")
											.getTime()));
						} else if (vo.getType().equals("string")) {
							countquery.setString(paramidx++, (String) value);
						} else if (vo.getType().equals("exists")
								|| vo.getType().equals("select")) {
							// 没有参数
						} else {
							countquery.setFloat(paramidx++,
									Float.parseFloat((String) value));
						}
					}
				}
				int rowcount = (Integer) countquery.uniqueResult();
				int pageNum = Integer
						.parseInt((String) pager.get("pagenumber"));
				int rowsNum = Integer.parseInt((String) pager.get("pagesize"));
				int pagecount = rowcount / rowsNum;
				int startNum = (pageNum - 1) * rowsNum + 1;
				if (startNum > rowcount) {
					startNum = 1;
					pageNum = 1;
				}
				int endNum = startNum + rowsNum;
				String selecttxt = pagesql.substring(pagesql.toLowerCase()
						.indexOf("select") + 6,
						pagesql.toLowerCase().indexOf("from"));
				String fromtxt = pagesql.substring(pagesql.toLowerCase()
						.indexOf("from") + 4);
				pagesql = " SELECT * FROM  ( SELECT  * FROM (SELECT TOP "
						+ (endNum - 1) + " row_number() over("
						+ main.getOrderby() + ") rownum, " + selecttxt
						+ " from " + fromtxt + " " + main.getOrderby()
						+ ")zzzz where rownum>=" + (startNum)
						+ " )zzzzz order by rownum";
				PreparedStatement stmt = conn.prepareStatement(pagesql,
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				if ("like".equals(main.getOrgparamtype())) {
					stmt.setString(1, orgs + "%");
					paramidx = 2;
				} else if (pagesql.indexOf("?") > 0) {
					stmt.setString(1, orgs);
					paramidx = 2;
				} else {

				}
				for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
					Object key = iter.next();
					if (submap.containsKey(key)) {
						Object value = params.get(key);
						ExportSub vo = submap.get(key);
						if (vo.getType().equals("date")) {
							stmt.setTimestamp(paramidx++, BusiUtils.parseDate((String) value));
							
						} else if (vo.getType().equals("time")) {
							stmt.setDate(
									paramidx++,
									new java.sql.Date(fomarttime.parse(
											(String) value + " 00:00:00")
											.getTime()));
						} else if (vo.getType().equals("string")) {
							stmt.setString(paramidx++, (String) value);
						} else if (vo.getType().equals("exists")
								|| vo.getType().equals("select")) {
							// 没有参数
						} else {
							stmt.setFloat(paramidx++,
									Float.parseFloat((String) value));
						}
					}
				}
				ResultSet rs = stmt.executeQuery();
				ResultSetMetaData rsMetaData = rs.getMetaData();
				int numberOfColumns = rsMetaData.getColumnCount();
				List retlist = new ArrayList();
				// 移动到取数位置
				while (rs.next()) {
					// List row = new ArrayList();
					Map row = new HashMap();
					for (int i = 1; i <= numberOfColumns; i++) {
						Class cls = Class.forName(rsMetaData
								.getColumnClassName(i));
						if (cls.isAssignableFrom(Date.class)) {
							Date obj = rs.getDate(i);
							row.put(rsMetaData.getColumnLabel(i), fomart.format(obj));
						} else if (cls
								.isAssignableFrom(java.sql.Timestamp.class)) {
							Date obj = rs.getDate(i);
							row.put(rsMetaData.getColumnLabel(i), fomart.format(obj));
						} else {
							Object obj = rs.getObject(i);
							row.put(rsMetaData.getColumnLabel(i), (obj));
						}
					}
					retlist.add(row);
				}
				ret.put("rows", retlist);
				ret.put("currentpage", pageNum);
				ret.put("total", rowcount);
				ret.put("pages", pagecount);
				stmt.close();
			}
			conn.close();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static boolean isproc(String sql){
		return DataExportService.procPattern.matcher(sql).matches();
	}
	public static void main(String[] args){
		System.out.println("============"+ "abc?def?ccc".indexOf("?",4));
	}
}
