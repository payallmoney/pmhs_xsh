package cn.net.tongfang.framework.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.StringUtils;

import cn.net.tongfang.framework.security.vo.BasicInformation;
import cn.net.tongfang.framework.security.vo.ChildrenMediExam;
import cn.net.tongfang.framework.security.vo.ChildrenMediExam36;
import cn.net.tongfang.framework.security.vo.FirstVistBeforeBorn;
import cn.net.tongfang.framework.security.vo.HealthFileMaternal;
import cn.net.tongfang.framework.security.vo.PersonalInfo;
import cn.net.tongfang.framework.security.vo.SystemInformation;

public class SystemInformationUtils extends HibernateDaoSupport {
	
	/**
	 * 获得系统配置值
	 * @param id
	 * @return
	 */
	public String getVal(Integer id){
		SystemInformation sysInfo = (SystemInformation)getHibernateTemplate()
				.get(SystemInformation.class, id);
		return sysInfo.getVal();
	}
	
	/**
	 * 获得基础信息配置值
	 * @param type
	 * @return
	 */
	public List<BasicInformation> getBasicInformation(Integer type){
		String hql = "From BasicInformation Where type = ? And isMain = 0";
		List list = getHibernateTemplate().find(hql,type);
		List<BasicInformation> ret = new ArrayList<BasicInformation>();
		for(Object obj : list){
			BasicInformation basicInfo = (BasicInformation)obj;
			ret.add(basicInfo);
		}
		return ret;
	}
	
	public String getPrintBasicInfo(String id,String tableName,String key,String tableKey){
		String hql = "From BasicInformation A," + tableName + " B Where A.id = B." + key + " And B." + tableKey + " = ?";
		List list =  getHibernateTemplate().find(hql,id);
		String ret = "未测";
		if(list.size() > 0){
			ret = "";
			for(Object objs : list){
				Object[] obj = (Object[])objs;
				BasicInformation basicInformation = (BasicInformation)obj[0];
				ret = ret + basicInformation.getName() + ",";
			}
			if(!ret.equals(""))
				ret = ret.substring(0,ret.length() - 1);
		}
		return ret;
	}
	
	public boolean checkChildrenMedicalExamIsInput(String fileNo,String items,String tableName,Integer type){
		String typeHql = "";
		List params = new ArrayList();
		params.add(fileNo);
		params.add(items);
		if(type != null){
			typeHql = " And dataType = ?";
			params.add(type);
		}
		String hql = "From " + tableName + " Where fileNo = ? And checkItem = ? " + typeHql; 
		List list = getHibernateTemplate().find(hql,params.toArray());
		if(list.size() > 0){
			return true;
		}
		return false;
	}
	
	public Integer checkWomanMedicalExam(String fileNo){
		fileNo = EncryptionUtils.encry(fileNo);
		String hql = "From HealthFileMaternal Where fileNo = ?";
		List list = getHibernateTemplate().find(hql,fileNo);
		if(list.size() > 0){
			HealthFileMaternal gravidityKey = (HealthFileMaternal)list.get(0);
			
			return gravidityKey.getGravidity();
		}
		return null;
	}
	
	public Integer hasHealthFileMaternal(String foriegnKey){
		if(StringUtils.hasText(foriegnKey)){
			HealthFileMaternal maternal =(HealthFileMaternal) getHibernateTemplate().get(HealthFileMaternal.class, foriegnKey);
			if(maternal==null){
				return null;
			}else{
				return maternal.getGravidity();
			}
		}else{
			return null;
		}
	}
	
	public boolean checkWomanMedicalExamIsInput(String fileNo,Integer items,String tableName,String type){
		String typeHql = "";
		List params = new ArrayList();
		params.add(fileNo);
		if(type != null){
			typeHql = " And recordType = ?";
			params.add(type);
		}
		if(items != null){
			typeHql = typeHql + "  And item = ? ";
			params.add(items);
		}
		String hql = "From " + tableName + " Where fileNo = ? " + typeHql; 
		List list = getHibernateTemplate().find(hql,params.toArray());
		if(list.size() > 0)
			return true;
		return false;
	}
	
	public boolean checkWomanGravidity(String fileNo,String tableName,Integer gravidity){
		fileNo = EncryptionUtils.encry(fileNo);
		String hql = "From " + tableName + " Where fileNo = ? And gravidity = ? ";
		List list = getHibernateTemplate().find(hql,new Object[]{fileNo,gravidity});
		if(list.size() > 0)
			return true;
		return false;
	}
	
	public boolean checkWomanMedicalExamIsInput(String fileNo,String items,String recordType,Integer parities){
		String typeHql = "";
		List params = new ArrayList();
		params.add(fileNo);
		params.add(recordType);
		params.add(parities);
		if(items != null){
			typeHql = typeHql + "  And item = ? ";
			params.add(items);
		}
		String hql = "From VisitAfterBorn Where fileNo = ? And recordType = ? And parities = ? " + typeHql; 
		System.out.println("===VisitAfterBorn start===");
		List list = getHibernateTemplate().find(hql,params.toArray());
		System.out.println("===VisitAfterBorn end===");
		if(list.size() > 0)
			return true;
		return false;
	}
	
	public FirstVistBeforeBorn hasFirstVistBeforeBorn(String foreignId,Integer gravidity){
		String hql = "From FirstVistBeforeBorn Where foreignId = ? order by visitDate desc ";
		List list = getHibernateTemplate().find(hql,foreignId);
		if(list.size() > 0){
			return (FirstVistBeforeBorn)list.get(0);
		}
		return null;
	}
	
	public boolean hasVisitAfterBorn(String foreignId,String recordType,String item){
		if("0".equals(recordType)){
			String hql = "From VisitAfterBorn Where foreignId = ? and recordType = ? and item=?";
			List list = getHibernateTemplate().find(hql,new Object[]{foreignId,recordType,item});
			if(list.size() > 0){
				return true;
			}
		}else{
			String hql = "From VisitAfterBorn Where foreignId = ? and recordType = ? ";
			List list = getHibernateTemplate().find(hql,new Object[]{foreignId,recordType});
			if(list.size() > 0){
				return true;
			}
		}
		return false;
	}
	
	public PersonalInfo getPersonalInfo(String fileNo){
		fileNo = EncryptionUtils.encry(fileNo);
		return (PersonalInfo)getHibernateTemplate().find(" From PersonalInfo Where fileNo = ?", fileNo).get(0);
	}
	public boolean getPersonalInfo(String fileNo,String tableName){
		fileNo = EncryptionUtils.encry(fileNo);
		String hql = " From PersonalInfo A, " + tableName + " B Where A.fileNo = ? And A.fileNo = B.fileNo ";
		List list = getHibernateTemplate().find(hql,fileNo);
		if(list.size() > 0)
			return true;
		return false;
	}
	public String childExamInfo(String fileNo,Integer dataType){
		fileNo = EncryptionUtils.encry(fileNo);
		String hql = "From ChildrenMediExam Where fileNo = ? And dataType = ? Order By checkItem ASC ";
		List list = getHibernateTemplate().find(hql,new Object[]{fileNo,dataType});
		if(list.size() > 0){
			String result = "";
			for(Object obj : list){
				ChildrenMediExam childExam = (ChildrenMediExam)obj;
				result += childExam.getCheckItem() + ",";
			}
			result = result.equals("") ? "" : result.substring(0,result.length() - 1);
			return result;
		}
		return null;
	}
	public String childExam36Info(String fileNo,Integer dataType){
		fileNo = EncryptionUtils.encry(fileNo);
		String hql = "From ChildrenMediExam36 Where fileNo = ? Order By checkItem ASC ";
		List list = getHibernateTemplate().find(hql,fileNo);
		if(list.size() > 0){
			String result = "";
			for(Object obj : list){
				ChildrenMediExam36 childExam = (ChildrenMediExam36)obj;
				result += childExam.getCheckItem() + ",";
			}
			result = result.equals("") ? "" : result.substring(0,result.length() - 1);
			return result;
		}
		return null;
	}
	
	public List getHistoryExamRecord(String foreignId,String tableName,String where){
		List retVal = new ArrayList();
		String hql = "Select id From " + tableName + " Where foreignId = ? " + where + " Order By visitDate ";
		List list = getHibernateTemplate().find(hql,foreignId);
		if(list.size() > 0){
			retVal.add(list.size());
			retVal.add(list);
			return retVal;
		}
		return null;
	}
	
	public String getHistoryExamId(String  foreignId,String tableName){
		String where = "";
		if(tableName.equals("VisitAfterBorn")){
			where = " And recordType = 1 ";
		}
		String hql = "Select id From " + tableName + " Where foreignId = ? " + where + "Order By visitDate ";
		List list = getHibernateTemplate().find(hql,foreignId);
		if(list.size() > 0){
			return (String)list.get(0);
		}
		return null;
	}
	
	public Object getSimgleHistoryExamRecord(String  foreignId,String tableName){
		String where = "";
		if(tableName.equals("VisitAfterBorn")){
			where = " And recordType = 1 ";
		}
		String hql = " From " + tableName + " Where foreignId = ? "+where+"  Order By visitDate ";
		
		List list = getHibernateTemplate().find(hql,foreignId);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
}
