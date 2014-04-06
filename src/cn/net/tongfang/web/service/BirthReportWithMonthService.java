package cn.net.tongfang.web.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.BirthReportWithMonthFile;
import cn.net.tongfang.framework.security.vo.OrganizationBySetReportFlag;
import cn.net.tongfang.framework.security.vo.SystemInformation;
import cn.net.tongfang.framework.util.CommonConvertUtils;
import cn.net.tongfang.framework.util.SystemInformationUtils;
import cn.net.tongfang.framework.util.service.ModuleMgr;
import cn.net.tongfang.framework.util.service.vo.PagingParam;
import cn.net.tongfang.framework.util.service.vo.PagingResult;
import cn.net.tongfang.web.service.bo.BirthReportWithMonthBO;
import cn.net.tongfang.web.service.bo.ControlJobForBirthReportBO;

public class BirthReportWithMonthService extends HibernateDaoSupport{
	private SystemInformationUtils sysInfos;
	public void setSysInfos(SystemInformationUtils sysInfos) {
		this.sysInfos = sysInfos;
	}

	
	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	public List getReportData(BirthReportWithMonthBO bean){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Session session = getSession();
			conn = session.connection();
			ps = conn.prepareStatement("Exec BirthCertificateReportWithMonth ?,?,?,?");
			TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
			String inputPersonId = "";
			if(user == null){
				inputPersonId = "admin";
			}else{
				inputPersonId = user.getUsername();
			}
			ps.setObject(1, bean.getReportYear());
			ps.setObject(2, bean.getReportMonth());
			ps.setObject(3, bean.getOrgId());
			ps.setObject(4, inputPersonId);
			rs = ps.executeQuery();
			List list = new ArrayList();
			if (rs.next()) {
				ResultSetMetaData md = rs.getMetaData();
				int num = md.getColumnCount();
				Map mapOfColValues = new HashMap(num);
				for (int i = 1; i <= num; i++) {
					if(rs.getObject(i).getClass().getName().equals("java.sql.Timestamp")){
						mapOfColValues.put(md.getColumnName(i), CommonConvertUtils.dateToStringWithDelimiter((Timestamp)rs.getObject(i)));
					}else{
						mapOfColValues.put(md.getColumnName(i), rs.getObject(i));
					}
					
				}
				list.add(mapOfColValues);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
				if(conn != null)
					conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
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
	private String getDownloadURL() {
		String ret = sysInfos.getVal(5);
		if (!ret.substring(ret.length() - 1, ret.length()).equals("/")) {
			ret += "/";
		}
		return ret;
	}
	public String generateWord(BirthReportWithMonthBO bean){
		ByteArrayInputStream bais = null;
		FileOutputStream fos = null;
		String path = getWebRootAbsolutePath() + "birthReportData/"; // 根据实际情况写路径
		UUID uuid = UUID.randomUUID(); 
		String filename = uuid + ".doc";
		try {
			//查看是否已经统计表
			String queryHql = " From BirthReportWithMonthFile Where reportYear = ? And reportMonth = ? And orgId = ?";
			Query query = getSession().createQuery(queryHql);
			query.setParameter(0, bean.getReportYear());
			query.setParameter(1, bean.getReportMonth());
			query.setParameter(2, bean.getOrgId());
			List list = query.list();
			if(list.size() > 0){
				for(Object obj : list){
					BirthReportWithMonthFile files = (BirthReportWithMonthFile)obj;
					String storeFilename = files.getStoreFileName();
					getHibernateTemplate().delete(files);
					File f = new File(path + storeFilename);
					if(f.exists()){
						f.delete();
					}
				}
			}
			if (!"".equals(path)) {
				File fileDir = new File(path);
				if (fileDir.exists()) {
					String content = bean.getHtmls();
					byte b[] = content.getBytes("UTF-8");
					bais = new ByteArrayInputStream(b);
					POIFSFileSystem poifs = new POIFSFileSystem();
					DirectoryEntry directory = poifs.getRoot();
					DocumentEntry documentEntry = directory.createDocument(
							"WordDocument", bais);
					fos = new FileOutputStream(path + filename);
					poifs.writeFilesystem(fos);
					bais.close();
					fos.close();
				}
			}
			BirthReportWithMonthFile birthfile = new BirthReportWithMonthFile();
			BeanUtils.copyProperties(bean, birthfile);
			birthfile.setStoreFileName(filename);
			birthfile.setFileName(bean.getTitles());
			TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
			String inputPersonId = "";
			if(user == null){
				inputPersonId = "admin";
			}else{
				inputPersonId = user.getUsername();
			}
			birthfile.setInputPersonId(inputPersonId);
			birthfile.setInputDate(new Timestamp(System.currentTimeMillis()));
			getHibernateTemplate().save(birthfile);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
				if (bais != null)
					bais.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return getDownloadURL() + filename;
	}
	
	public PagingResult<BirthReportWithMonthFile> getHistoryReportFiles(BirthReportWithMonthBO qryCond,PagingParam pp){
		String hql = " From BirthReportWithMonthFile Where orgId = ? And fileName Like ? ";
		Query query = getSession().createQuery(" Select Count(*) " + hql);
		query.setParameter(0, qryCond.getOrgId());
		query.setParameter(1, "%" + qryCond.getQueryCondition() + "%");
		int totalSize = ((Long) query.uniqueResult()).intValue();
		query = getSession().createQuery(hql + " Order By inputDate ");
		query.setParameter(0, qryCond.getOrgId());
		query.setParameter(1, "%" + qryCond.getQueryCondition() + "%");
		if(pp == null) pp = new PagingParam();
		query.setFirstResult(pp.getStart()).setMaxResults(pp.getLimit());
		PagingResult<BirthReportWithMonthFile> result = new PagingResult<BirthReportWithMonthFile>(
				totalSize, query.list());
		return result;
	}
	
	public List<String> getBirthReportUrl(List<String> storefilenames){
		if(storefilenames.size() > 0){
			String serviceUrl = getDownloadURL();
			List<String> result = new ArrayList<String>();
			for(String url : storefilenames){
				result.add(serviceUrl + url);
			}
			return result;
		}
		return null;
	}
	
	public void controlJobForBirthReport(ControlJobForBirthReportBO bean){
		//在之前先保存配置数据
		List l = getHibernateTemplate().find(" From SystemInformation Where id = ?",6);
		if(l.size() > 0){
			SystemInformation info = (SystemInformation)l.get(0);
			info.setVal(bean.getIsStartJob());
			getHibernateTemplate().update(info);
		}
		
		List organizations = bean.getOrganizations();
		if ( organizations != null && organizations.size() != 0 ) {
			List userOrgList = new ArrayList();		
			for (int i = 0; i < organizations.size(); i++) {
				System.out.println(organizations.get(i));
				String orgIdStr = (String) (organizations.get(i));
				Integer orgId = Integer.parseInt(orgIdStr);
				OrganizationBySetReportFlag org = new OrganizationBySetReportFlag();
				org.setOrgId(orgId);
				userOrgList.add (org);
			}
			List orgs = getHibernateTemplate().find("from OrganizationBySetReportFlag  ");
			getHibernateTemplate().deleteAll(orgs);
			getHibernateTemplate().flush();
			getHibernateTemplate().saveOrUpdateAll(userOrgList);
		}
	}
	
	public List getUnSetOrganization(){
		String hql = " From Organization Where id Not In (Select orgId From OrganizationBySetReportFlag )";
		List result = getHibernateTemplate().find(hql);
		return result;
	}
	public List getSetOrganization(){
		String hql = " From Organization Where id In (Select orgId From OrganizationBySetReportFlag )";
		List result = getHibernateTemplate().find(hql);
		return result;
	}
}
