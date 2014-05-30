package cn.net.tongfang.web.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.net.tongfang.framework.security.vo.CfgFilesearch;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.web.service.bo.PagedList;

public class FileNumSearch extends HibernateDaoSupport{
	static int pagesize = 10;
	
	//OtherParamType 类型
	public static String OtherParamType_Woman = "1";//妇女
	
	public static String OtherParamType_Hyp = "2";//高血压
	
	public static String OtherParamType_T2dm = "3";//2型糠尿病
	public static String OtherParamType_WomanBook = "4";//妇女建册
	public static String OtherParamType_Husband = "5";//丈夫信息
	public static String OtherParamType_Child = "6";//儿童
	public static String OtherParamType_ChildOther = "7";//儿童其他
	public static String OtherParamType_Furious = "8";//精神病
	public static String OtherParamType_Old = "9";//老年人
	public static String OtherParamType_Child_NoCard = "10";//儿童未建册
	
	public static String OtherParamType_MarryMan = "11";//男性婚检
	public static String OtherParamType_MarryWoman = "12";//女性婚检
	public static String OtherParamType_Foreign_Disease_Check = "13";//外籍人员检查
	public static String OtherParamType_Foreign_Disease_Check_Mate = "14";//外籍人员配偶
	
	//condVal 类型
	public static String CondVal_Barcode = "0";   //条形码
	public static String CondVal_Fileno = "1";   //档案编号
	public static String CondVal_Name = "2";   //姓名
	public static String CondVal_CardId = "3";   //身份证号
	public static String CondVal_LinkMan = "4";   //联系人
	
	public  PagedList listCodePage(int pageNo, String mcode, boolean startWith,String condVal,String otherparamtype){
		return listCodePageSize(pageNo,pagesize, mcode, startWith,condVal,otherparamtype);
    	
    }
	
	public  PagedList listCodePageSize(int pageNo,int newpagesize, String mcode, boolean startWith,String condVal,String otherparamtype){
//		System.out.println("=============pageNo======"+pageNo);
//		System.out.println("=============newpagesize======"+newpagesize);
//		System.out.println("=============mcode======"+mcode);
//		System.out.println("=============startWith======"+startWith);
//		System.out.println("=============condVal======"+condVal);
//		System.out.println("=============otherparamtype======"+otherparamtype);
		
    	String likePrefix = startWith ? "" : "%";
    	PagedList res = new PagedList();
    	String hsqlparam = "";
    	String otherTables = "";
    	String extendCols = "";
    	
    	String[] mcodes = mcode.split("%");
    	String districtNumber = "";
		String otherCond = "";
		if(mcodes.length > 1){
			districtNumber = mcodes[0];
			otherCond = mcodes[1];
		}else{
			otherCond = mcodes[0];
		}
//		System.out.println("========otherCond==========="+otherCond);
    	if(otherparamtype != null){
    		if(otherparamtype.equals(OtherParamType_Woman)){//孕产妇随访查询
    			otherTables = " ,HealthFileMaternal hm ";
    			hsqlparam = " And p.sex = '女' And hm.fileNo = hf.fileNo And (hm.isClosed = '0' or p.bornStatus = '是') ";
    			extendCols = " ,hm.id ";
    		}else if(otherparamtype.equals(OtherParamType_Hyp) || otherparamtype.equals(OtherParamType_T2dm) || otherparamtype.equals(OtherParamType_Furious)){//慢病随访查询
    			otherTables = " ,DiseaseHistory d ";
    			hsqlparam = " And p.id = d.personalInfoId And diseaseId = " + otherparamtype ;
        	}else if(otherparamtype.equals(OtherParamType_WomanBook)){//孕产妇建册查询
        		hsqlparam = " And p.sex = '女'  ";
        		extendCols = " ,hf.tel,hf.township,hf.village,p.workUnit,p.folk,p.folkOther,p.education,p.occupation,p.idnumber,hf.residenceAddress ";
        	}else if(otherparamtype.equals(OtherParamType_Husband)){//丈夫信息
        		hsqlparam = " And p.sex = '男'";
        		extendCols = " ,hf.tel,p.workUnit,p.education,p.occupation ";
        	}else if(otherparamtype.equals(OtherParamType_Child)){//儿童建册查询 条件<=7岁
        		hsqlparam = " And (year(getdate()) -  year(p.birthday)) <=7  and  NOT EXISTS (select 1 from HealthFileChildren hfchild where hfchild.fileNo = hf.fileNo ) ";
        		extendCols = " ,hf.township,hf.village,p.bloodTypeAbo,p.bloodTypeRh ";
        	}else if(otherparamtype.equals(OtherParamType_ChildOther)){//
        		otherTables = " ,HealthFileChildren hc ";
        		hsqlparam = " And hc.fileNo = hf.fileNo ";
        		extendCols = " ,hc.id ";
        	}else if(otherparamtype.equals(OtherParamType_Old)){
        		hsqlparam = " And (year(getDate()) - year(p.birthday)) >= 65 ";
        	}else if(otherparamtype.equals(OtherParamType_MarryWoman)){//女性婚检
        		hsqlparam = " And p.sex = '女'  ";
        		extendCols = " ,hf.tel,hf.township,hf.village,p.workUnit,p.folk,p.folkOther,p.education,p.occupation,p.idnumber,hf.residenceAddress,hf.districtNumber ";
        	}else if(otherparamtype.equals(OtherParamType_MarryMan)){//男性婚检
        		hsqlparam = " And p.sex = '男'";
        		extendCols = " ,hf.tel,hf.township,hf.village,p.workUnit,p.folk,p.folkOther,p.education,p.occupation,p.idnumber,hf.residenceAddress,hf.districtNumber ";
        	}else if(otherparamtype.equals(OtherParamType_Foreign_Disease_Check)){//外籍人员检查
        		hsqlparam = " And hf.nation <> '中国'";
        		extendCols = " ,hf.tel,hf.township,hf.village,p.workUnit,p.folk,p.folkOther,p.education,p.occupation,p.idnumber,hf.residenceAddress,hf.districtNumber,hf.nation ";
        	}else if(otherparamtype.equals(OtherParamType_Foreign_Disease_Check_Mate)){//外籍人员检查
        		hsqlparam = " ";
        		extendCols = " ,hf.tel,hf.township,hf.village,p.workUnit,p.folk,p.folkOther,p.education,p.occupation,p.idnumber,hf.residenceAddress,hf.districtNumber,hf.nation ";
        	}
    	}
    	System.out.println("===condVal==="+condVal);
    	//改为like ,like 可以用索引 substring 不能用
    	if(condVal.equals(CondVal_Fileno)){
    		likePrefix = likePrefix.replace("%", "");
//    		mcode = mcode.replace("%", "");
    		String fileNo = EncryptionUtils.encry(otherCond)+"%";
    		long count = (Long)getHibernateTemplate().find("select count(*) from HealthFile hf,PersonalInfo p " + otherTables +
        			"where  hf.fileNo = p.fileNo And hf.fileNo like ? and hf.status = 0 " + hsqlparam,fileNo).get(0);
        	if(newpagesize == 0 ){
        		newpagesize = (int)count;
        		newpagesize = (newpagesize == 0 ? pagesize : newpagesize);
        	}
        	res.totalLines = count;
        	res.pageSize = newpagesize;
        	res.totalPages = (int) (count / newpagesize) + ((count % newpagesize > 0) ? 1 : 0);
        	int from = pageNo * newpagesize;
        	String sql = "select hf.fileNo, hf.name, p.sex, p.birthday,(year(getDate()) - year(p.birthday)) as age," +
        			" p.idnumber,hf.barCode,hf.address " + extendCols + " from HealthFile as hf, PersonalInfo as p " + otherTables +
        			"where  p.fileNo = hf.fileNo " +
        			"and hf.fileNo like ? and hf.status = 0 "  + hsqlparam;
        	Query qry = getSession().createQuery(sql);
        	qry.setParameter(0, fileNo);
        	qry.setMaxResults(newpagesize);
        	qry.setFirstResult(from);
        	List list = qry.list();
        	System.out.println("res line is : " + list.size());
        	res.res = parseResult(list);
        	res.currentPage = pageNo + 1;
    	}else if(condVal.equals(CondVal_Name)){
    		Query qry = getSession().createQuery("select count(*) from HealthFile hf,PersonalInfo p " + otherTables +
        			"where hf.fileNo = p.fileNo And hf.districtNumber like ? " +
        			"And hf.name like ?  and hf.status = 0" +  hsqlparam);
    		qry.setParameter(0, districtNumber+"%");
        	qry.setParameter(1, EncryptionUtils.encry(otherCond)+"%");
    		long count = (Long)qry.list().get(0);
    		if(newpagesize == 0 ){
        		newpagesize = (int)count;
        		newpagesize = (newpagesize == 0 ? pagesize : newpagesize);
        	}
        	System.out.println("total line is : " + count);
        	res.totalLines = count;
        	res.pageSize = newpagesize;
        	res.totalPages = (int) (count / newpagesize) + ((count % newpagesize > 0) ? 1 : 0);
        	int from = pageNo * newpagesize;
        	qry = getSession().createQuery("select hf.fileNo, hf.name, p.sex, p.birthday,(year(getDate()) - year(p.birthday)) as age," +
        			" p.idnumber,hf.barCode,hf.address " + extendCols + " from HealthFile as hf, PersonalInfo as p " + otherTables +
        			"where p.fileNo = hf.fileNo and hf.districtNumber like ? " +
        			"And hf.name like  ?  and hf.status = 0 " + hsqlparam);
        	qry.setParameter(0, districtNumber+"%");
        	qry.setParameter(1, EncryptionUtils.encry(otherCond)+"%");
        	qry.setMaxResults(newpagesize);
        	qry.setFirstResult(from);
        	List list = qry.list();
        	System.out.println("res line is : " + list.size());
        	res.res = parseResult(list);
        	res.currentPage = pageNo + 1;
    	}else if(condVal.equals(CondVal_LinkMan)){
    		Query qry = getSession().createQuery("select count(*) from HealthFile hf,PersonalInfo p " + otherTables +
        			"where hf.fileNo = p.fileNo And hf.districtNumber like ? " +
        			"And p.linkman like ? and hf.status = 0 " +  hsqlparam);
    		qry.setParameter(0, districtNumber+"%");
        	qry.setParameter(1, "%"+otherCond+"%");
    		long count = (Long)qry.list().get(0);
    		if(newpagesize == 0 ){
        		newpagesize = (int)count;
        		newpagesize = (newpagesize == 0 ? pagesize : newpagesize);
        	}
        	System.out.println("total line is : " + count);
        	res.totalLines = count;
        	res.pageSize = newpagesize;
        	res.totalPages = (int) (count / newpagesize) + ((count % newpagesize > 0) ? 1 : 0);
        	int from = pageNo * newpagesize;
        	qry = getSession().createQuery("select hf.fileNo, hf.name, p.sex, p.birthday,(year(getDate()) - year(p.birthday)) as age," +
        			" p.idnumber,hf.barCode,hf.address " + extendCols + " from HealthFile as hf, PersonalInfo as p " + otherTables +
        			"where p.fileNo = hf.fileNo and hf.districtNumber like ? " +
        			"And p.linkman like ?  and hf.status = 0 " + hsqlparam);
        	qry.setParameter(0, districtNumber+"%");
        	qry.setParameter(1, "%"+otherCond+"%");
        	qry.setMaxResults(newpagesize);
        	qry.setFirstResult(from);
        	List list = qry.list();
        	System.out.println("res line is : " + list.size());
        	res.res = parseResult(list);
        	res.currentPage = pageNo + 1;
    	}else if(condVal.equals(CondVal_CardId)){
    		Query qry = getSession().createQuery("select count(*) from HealthFile hf , PersonalInfo as p " + otherTables +
        			"where p.fileNo = hf.fileNo And hf.districtNumber like ? And p.idnumber like ?  and hf.status = 0" + hsqlparam);
    		qry.setParameter(0, districtNumber+"%");
        	qry.setParameter(1, EncryptionUtils.encry(otherCond)+"%");
    		long count = (Long)qry.list().get(0);
    		if(newpagesize == 0 ){
        		newpagesize = (int)count;
        		newpagesize = (newpagesize == 0 ? pagesize : newpagesize);
        	}
        	System.out.println("total line is : " + count);
        	res.totalLines = count;
        	res.pageSize = newpagesize;
        	res.totalPages = (int) (count / newpagesize) + ((count % newpagesize > 0) ? 1 : 0);
        	int from = pageNo * newpagesize;
        	qry = getSession().createQuery("select hf.fileNo, hf.name, p.sex, p.birthday,(year(getDate()) - year(p.birthday)) as age," +
        			" p.idnumber,hf.barCode,hf.address " + extendCols + "  from HealthFile as hf, PersonalInfo as p " + otherTables +
        			"where hf.districtNumber like ? " +
        			"And p.idnumber like ?  And hf.fileNo = p.fileNo  and hf.status = 0 " + hsqlparam);
        	qry.setParameter(0, districtNumber+"%");
        	qry.setParameter(1, EncryptionUtils.encry(otherCond)+"%");
        	qry.setMaxResults(newpagesize);
        	qry.setFirstResult(from);
        	List list = qry.list();
        	System.out.println("res line is : " + list.size());
        	res.res = parseResult(list);
        	res.currentPage = pageNo + 1;
    	}else if(condVal.equals(CondVal_Barcode)){
    		Query qry = getSession().createQuery("select count(*) from HealthFile hf , PersonalInfo as p " + otherTables +
        			"where p.fileNo = hf.fileNo  And hf.barCode like ?  and hf.status = 0" + hsqlparam);
//    		qry.setParameter(0, districtNumber + "%");
        	qry.setParameter(0, otherCond+ "%");
    		long count = (Long)qry.list().get(0);
    		if(newpagesize == 0 ){
        		newpagesize = (int)count;
        		newpagesize = (newpagesize == 0 ? pagesize : newpagesize);
        	}
        	System.out.println("total line is : " + count);
        	res.totalLines = count;
        	res.pageSize = newpagesize;
        	res.totalPages = (int) (count / newpagesize) + ((count % newpagesize > 0) ? 1 : 0);
        	int from = pageNo * newpagesize;
        	qry = getSession().createQuery("select hf.fileNo, hf.name, p.sex, p.birthday,(year(getDate()) - year(p.birthday)) as age," +
        			" p.idnumber,hf.barCode,hf.address " + extendCols + " from HealthFile as hf, PersonalInfo as p " + otherTables +
        			"where p.fileNo = hf.fileNo  " +
        			"And hf.barCode like ?  and hf.status = 0 " + hsqlparam);
//        	qry.setParameter(0, districtNumber + "%");
        	qry.setParameter(0, "%"+otherCond+"%");
        	qry.setMaxResults(newpagesize);
        	qry.setFirstResult(from);
        	List list = qry.list();
        	System.out.println("res line is : " + list.size());
        	res.res = parseResult(list);
        	res.currentPage = pageNo + 1;
    	}
    	return res;
    }
	
	public  PagedList listCodePageSize_new(int pageNo,int newpagesize, String mcode, boolean startWith,String condVal,String otherparamtype){
    	String likePrefix = startWith ? "" : "%";
    	PagedList res = new PagedList();
    	String hsqlparam = "";
    	String otherTables = "";
    	String extendCols = "";
    	
    	String[] mcodes = mcode.split("%");
    	String districtNumber = "";
		String otherCond = "";
		if(mcodes.length > 1){
			districtNumber = mcodes[0];
			otherCond = mcodes[1];
		}else{
			otherCond = mcodes[0];
		}
		List<CfgFilesearch> searchlist = getSession().createQuery(" from CfgFilesearch ").list();
		Map<String,CfgFilesearch> searchMap = new HashMap<String,CfgFilesearch>();
		for(int i = 0 ; i<searchlist.size();i++){
			CfgFilesearch cfg = searchlist.get(i);
			searchMap.put(cfg.getId()+"", cfg);
		}
    	if(otherparamtype != null){
    		CfgFilesearch cfg = searchMap.get(otherparamtype);
    		otherTables = cfg.getOthertable();
    		hsqlparam = cfg.getParams();
    		if(hsqlparam.indexOf("?")>=0){
    			hsqlparam.replaceAll("\\?", otherparamtype);
    		}
    		extendCols = cfg.getCols();
    	}
    	System.out.println("===condVal==="+condVal);
    	//改为like ,like 可以用索引 substring 不能用
    	if(condVal.equals(CondVal_Fileno)){
    		likePrefix = likePrefix.replace("%", "");
//    		mcode = mcode.replace("%", "");
    		String fileNo = EncryptionUtils.encry(otherCond)+"%";
    		long count = (Long)getHibernateTemplate().find("select count(*) from HealthFile hf,PersonalInfo p " + otherTables +
        			"where  hf.fileNo = p.fileNo And hf.fileNo like ? and hf.status = 0 " + hsqlparam,fileNo).get(0);
        	if(newpagesize == 0 ){
        		newpagesize = (int)count;
        		newpagesize = (newpagesize == 0 ? pagesize : newpagesize);
        	}
        	res.totalLines = count;
        	res.pageSize = newpagesize;
        	res.totalPages = (int) (count / newpagesize) + ((count % newpagesize > 0) ? 1 : 0);
        	int from = pageNo * newpagesize;
        	String sql = "select hf.fileNo, hf.name, p.sex, p.birthday,(year(getDate()) - year(p.birthday)) as age," +
        			" p.idnumber,hf.barCode,hf.address " + extendCols + " from HealthFile as hf, PersonalInfo as p " + otherTables +
        			"where  p.fileNo = hf.fileNo " +
        			"and hf.fileNo like ? and hf.status = 0 "  + hsqlparam;
        	Query qry = getSession().createQuery(sql);
        	qry.setParameter(0, fileNo);
        	qry.setMaxResults(newpagesize);
        	qry.setFirstResult(from);
        	List list = qry.list();
        	System.out.println("res line is : " + list.size());
        	res.res = parseResult(list);
        	res.currentPage = pageNo + 1;
    	}else if(condVal.equals(CondVal_Name)){
    		Query qry = getSession().createQuery("select count(*) from HealthFile hf,PersonalInfo p " + otherTables +
        			"where hf.fileNo = p.fileNo And hf.districtNumber like ? " +
        			"And hf.name like ?  and hf.status = 0" +  hsqlparam);
    		qry.setParameter(0, districtNumber+"%");
        	qry.setParameter(1, EncryptionUtils.encry(otherCond)+"%");
    		long count = (Long)qry.list().get(0);
    		if(newpagesize == 0 ){
        		newpagesize = (int)count;
        		newpagesize = (newpagesize == 0 ? pagesize : newpagesize);
        	}
        	System.out.println("total line is : " + count);
        	res.totalLines = count;
        	res.pageSize = newpagesize;
        	res.totalPages = (int) (count / newpagesize) + ((count % newpagesize > 0) ? 1 : 0);
        	int from = pageNo * newpagesize;
        	qry = getSession().createQuery("select hf.fileNo, hf.name, p.sex, p.birthday,(year(getDate()) - year(p.birthday)) as age," +
        			" p.idnumber,hf.barCode,hf.address " + extendCols + " from HealthFile as hf, PersonalInfo as p " + otherTables +
        			"where p.fileNo = hf.fileNo and hf.districtNumber like ? " +
        			"And hf.name like  ?  and hf.status = 0 " + hsqlparam);
        	qry.setParameter(0, districtNumber+"%");
        	qry.setParameter(1, EncryptionUtils.encry(otherCond)+"%");
        	qry.setMaxResults(newpagesize);
        	qry.setFirstResult(from);
        	List list = qry.list();
        	System.out.println("res line is : " + list.size());
        	res.res = parseResult(list);
        	res.currentPage = pageNo + 1;
    	}else if(condVal.equals(CondVal_LinkMan)){
    		Query qry = getSession().createQuery("select count(*) from HealthFile hf,PersonalInfo p " + otherTables +
        			"where hf.fileNo = p.fileNo And hf.districtNumber like ? " +
        			"And p.linkman like ? and hf.status = 0 " +  hsqlparam);
    		qry.setParameter(0, districtNumber+"%");
        	qry.setParameter(1, "%"+otherCond+"%");
    		long count = (Long)qry.list().get(0);
    		if(newpagesize == 0 ){
        		newpagesize = (int)count;
        		newpagesize = (newpagesize == 0 ? pagesize : newpagesize);
        	}
        	System.out.println("total line is : " + count);
        	res.totalLines = count;
        	res.pageSize = newpagesize;
        	res.totalPages = (int) (count / newpagesize) + ((count % newpagesize > 0) ? 1 : 0);
        	int from = pageNo * newpagesize;
        	qry = getSession().createQuery("select hf.fileNo, hf.name, p.sex, p.birthday,(year(getDate()) - year(p.birthday)) as age," +
        			" p.idnumber,hf.barCode,hf.address,p.linkman " + extendCols + " from HealthFile as hf, PersonalInfo as p " + otherTables +
        			"where p.fileNo = hf.fileNo and hf.districtNumber like ? " +
        			"And p.linkman like ?  and hf.status = 0 " + hsqlparam);
        	qry.setParameter(0, districtNumber+"%");
        	qry.setParameter(1, "%"+otherCond+"%");
        	qry.setMaxResults(newpagesize);
        	qry.setFirstResult(from);
        	List list = qry.list();
        	System.out.println("res line is : " + list.size());
        	res.res = parseResult(list);
        	res.currentPage = pageNo + 1;
    	}else if(condVal.equals(CondVal_CardId)){
    		Query qry = getSession().createQuery("select count(*) from HealthFile hf , PersonalInfo as p " + otherTables +
        			"where p.fileNo = hf.fileNo And hf.districtNumber like ? And p.idnumber like ?  and hf.status = 0" + hsqlparam);
    		qry.setParameter(0, districtNumber+"%");
        	qry.setParameter(1, EncryptionUtils.encry(otherCond)+"%");
    		long count = (Long)qry.list().get(0);
    		if(newpagesize == 0 ){
        		newpagesize = (int)count;
        		newpagesize = (newpagesize == 0 ? pagesize : newpagesize);
        	}
        	System.out.println("total line is : " + count);
        	res.totalLines = count;
        	res.pageSize = newpagesize;
        	res.totalPages = (int) (count / newpagesize) + ((count % newpagesize > 0) ? 1 : 0);
        	int from = pageNo * newpagesize;
        	qry = getSession().createQuery("select hf.fileNo, hf.name, p.sex, p.birthday,(year(getDate()) - year(p.birthday)) as age," +
        			" p.idnumber,hf.barCode,hf.address  from HealthFile as hf, PersonalInfo as p " + otherTables +
        			"where hf.districtNumber like ? " +
        			"And p.idnumber like ?  And hf.fileNo = p.fileNo  and hf.status = 0 " + hsqlparam);
        	qry.setParameter(0, districtNumber+"%");
        	qry.setParameter(1, EncryptionUtils.encry(otherCond)+"%");
        	qry.setMaxResults(newpagesize);
        	qry.setFirstResult(from);
        	List list = qry.list();
        	System.out.println("res line is : " + list.size());
        	res.res = parseResult(list);
        	res.currentPage = pageNo + 1;
    	}else if(condVal.equals(CondVal_Barcode)){
    		Query qry = getSession().createQuery("select count(*) from HealthFile hf , PersonalInfo as p " + otherTables +
        			"where p.fileNo = hf.fileNo  And hf.barCode like ?  and hf.status = 0" + hsqlparam);
        	qry.setParameter(0, otherCond+ "%");
    		long count = (Long)qry.list().get(0);
    		if(newpagesize == 0 ){
        		newpagesize = (int)count;
        		newpagesize = (newpagesize == 0 ? pagesize : newpagesize);
        	}
        	System.out.println("total line is : " + count);
        	res.totalLines = count;
        	res.pageSize = newpagesize;
        	res.totalPages = (int) (count / newpagesize) + ((count % newpagesize > 0) ? 1 : 0);
        	int from = pageNo * newpagesize;
        	qry = getSession().createQuery("select hf.fileNo, hf.name, p.sex, p.birthday,(year(getDate()) - year(p.birthday)) as age," +
        			" p.idnumber,hf.barCode,hf.address " + extendCols + " from HealthFile as hf, PersonalInfo as p " + otherTables +
        			"where p.fileNo = hf.fileNo  " +
        			"And hf.barCode like ?  and hf.status = 0 " + hsqlparam);
        	qry.setParameter(0, "%"+otherCond+"%");
        	qry.setMaxResults(newpagesize);
        	qry.setFirstResult(from);
        	List list = qry.list();
        	System.out.println("res line is : " + list.size());
        	res.res = parseResult(list);
        	res.currentPage = pageNo + 1;
    	}
    	return res;
    }
	
	
    public  List getItem(String code){
		List list = getHibernateTemplate().find("select hf.fileNo, hf.name, p.sex, p.birthday,(year(getDate()) - year(p.birthday)) as age," +
    			" p.idnumber,hf.barCode" +
    			" from HealthFile as hf, PersonalInfo as p " +
    			"where p.fileNo = hf.fileNo " +
    			"and p.fileNo = ? and hf.status = 0 ", EncryptionUtils.encry(code));
    	
    	return parseResult(list);

    }
    /**
     * 解释List
     * @param list
     * @return
     */
    private List parseResult(List list){
    	List arrayList = new ArrayList();
    	String sql = "";
    	Query query = null;
    	List l;
    	if(list.size() > 0){
    		for(int i = 0;i < list.size();i++){
    			Object[] obj = (Object[])list.get(i);
    			Object[] objArray = new Object[obj.length];
    			String name = (String)obj[1];
        		obj[1] = EncryptionUtils.decipher(name);
        		String idNum = (String)obj[5];
        		obj[5] = EncryptionUtils.decipher(idNum);
        		String str = (String)obj[0];
        		sql = "Select general11 From MedicalExam Where fileNo = '" + str + "' Order By inputDate";
        		query = getSession().createQuery(sql);
        		l = query.list();
        		if(l.size() > 0){
        			Object o = l.get(0);
        			if(o != null){
	        			BigDecimal general11 = (BigDecimal)o;
	        			general11 = general11.setScale(2, BigDecimal.ROUND_HALF_UP);
	        			objArray[6] = general11;
        			}else{
        				objArray[6] = 0;
        			}
        		}else{
        			objArray[6] = 0;
        		}
        		obj[0] = EncryptionUtils.decipher(str);
        		for(int j = 0;j<obj.length;j++){
        			objArray[j] = obj[j]; 
        		}
        		arrayList.add(objArray);
    		}
    	}
    	return arrayList;
    }
    
    public boolean districtIdIsExists(String disId){
    	String hql = "From District Where id = ?";
    	Query query = getSession().createQuery(hql);
    	query.setParameter(0, disId);
    	List list = query.list();
    	if(list.size() > 0)
    		return true;
    	return false;
    }
}