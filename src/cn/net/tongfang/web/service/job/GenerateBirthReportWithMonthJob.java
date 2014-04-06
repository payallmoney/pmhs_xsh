package cn.net.tongfang.web.service.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.net.tongfang.framework.security.vo.Organization;
import cn.net.tongfang.framework.util.SystemInformationUtils;
import cn.net.tongfang.framework.util.service.ModuleMgr;
import cn.net.tongfang.web.service.BirthReportWithMonthService;
import cn.net.tongfang.web.service.PersonalInfoService;
import cn.net.tongfang.web.service.bo.BirthReportWithMonthBO;


public class GenerateBirthReportWithMonthJob {
	private static final Logger log = Logger.getLogger(PersonalInfoService.class);
	BirthReportWithMonthService birthReportWithMonthService;
	private SystemInformationUtils sysInfos;
	public void setSysInfos(SystemInformationUtils sysInfos) {
		this.sysInfos = sysInfos;
	}
	public void setbirthReportWithMonthService(BirthReportWithMonthService birthReportWithMonthService) {
		this.birthReportWithMonthService = birthReportWithMonthService;
	}

	public void setBirthReportWithMonthService(
			BirthReportWithMonthService birthReportWithMonthService) {
		this.birthReportWithMonthService = birthReportWithMonthService;
	}

	private String getWebRootAbsolutePath() {
		String path = null;
		String folderPath = ModuleMgr.class.getProtectionDomain()
				.getCodeSource().getLocation().getPath();
		if (folderPath.indexOf("WEB-INF") > 0) {
			path = folderPath.substring(0,
					folderPath.indexOf("WEB-INF/classes"));
		}
		return path;
	}

	public void autoGenerateWordFiles() {
		if(sysInfos.getVal(6).equals("1")){
			BufferedReader reader = null;
			InputStreamReader isr = null;
			try {
				List list = birthReportWithMonthService.getSetOrganization();
				SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String date = sDateFormat.format(new java.util.Date());
				String[] ds = date.split("-");
				Integer reportYear = Integer.parseInt(ds[0]);
				Integer reportMonth = Integer.parseInt(ds[1]);
				File file = new File(getWebRootAbsolutePath()
						+ "/BirthReportTemplate.html");
				StringBuffer sbuffer = new StringBuffer();
				if (file.exists()) {
					isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
					reader = new BufferedReader(isr);
					String tempString = null;
					while ((tempString = reader.readLine()) != null) {
						sbuffer.append(tempString);
					}
				}
				if (list.size() > 0) {
					for (Object obj : list) {
						Organization org = (Organization) obj;
						Integer orgId = org.getId();
						BirthReportWithMonthBO bean = new BirthReportWithMonthBO();
						bean.setReportYear(reportYear);
						bean.setReportMonth(reportMonth);
						bean.setOrgId(orgId);
						List l = birthReportWithMonthService.getReportData(bean);
						String sourceTemplate = sbuffer.toString();
						if (l.size() > 0) {
							Map map = (Map) l.get(0);
							Iterator i = map.entrySet().iterator();
							while (i.hasNext()) {
								Object o = i.next();
								String key = o.toString();
								String[] arrays = key.split("=");
								String keys = arrays[0].toString();
								String values = arrays[1].toString();
								if(sourceTemplate.contains(keys)){
									sourceTemplate = sourceTemplate.replaceAll("\\$\\{" + keys + "\\}", values);
								}
							}
							BirthReportWithMonthBO b = new BirthReportWithMonthBO();
							b.setHtmls(sourceTemplate);
							b.setTitles(reportYear + "-" + ( reportMonth < 10 ? "0" + reportMonth : reportMonth) + "_" + org.getName());
							b.setReportYear(reportYear);
							b.setReportMonth(reportMonth);
							b.setOrgId(orgId);
							birthReportWithMonthService.generateWord(b);
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (reader != null)
						reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			log.info("出生医学证明没有启动自动执行月报表统计功能");
		}
//		System.out.println(birthReportWithMonthService.getClass().getName());
		
	}

}
