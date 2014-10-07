package cn.net.tongfang.web.service.commonexam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.net.tongfang.framework.security.SecurityManager;
import cn.net.tongfang.framework.security.vo.CodModuleMap;
import cn.net.tongfang.framework.security.vo.District;
import cn.net.tongfang.framework.security.vo.ExamExamcfg;
import cn.net.tongfang.framework.security.vo.ExamItemcfg;
import cn.net.tongfang.framework.util.CacheUtil;

public class CommonExamUtil extends HibernateDaoSupport implements
		 ApplicationListener, CacheUtil {
	private static final Logger loger = Logger.getLogger(CommonExamUtil.class);
	private Map<String , ExamItemcfg> itemcfg = new HashMap<String , ExamItemcfg>();
	private Map<String , Map<String,ExamExamcfg>>  examcfg = new HashMap<String , Map<String,ExamExamcfg>>();
	private Map<String , String> districtMap = new HashMap();
	private Map<String , List> districtDetailMap = new HashMap();
	@Autowired
	private String districtnumber;

	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			refresh();
		}
	}
	
	public void refresh() {
		//检查项目缓存
		itemcfg.clear();
		List<ExamItemcfg> list = getHibernateTemplate().find("from ExamItemcfg");
		for(ExamItemcfg cfg : list){
			if(!itemcfg.containsKey(cfg.getCode()))
				itemcfg.put(cfg.getCode(), cfg);
		}
		//体检表项缓存 结构为   "体检表名" -> "项目名" -> "项目配置"
		examcfg.clear();
		List<ExamExamcfg> list1 = getHibernateTemplate().find("from ExamExamcfg");
		for(ExamExamcfg cfg : list1){
			if(!examcfg.containsKey(cfg.getExamname())){
				Map<String,ExamExamcfg> items = new HashMap<String,ExamExamcfg>(); 
				items.put(cfg.getItemname(), cfg);
				examcfg.put(cfg.getExamname(), items);
			}else{
				Map<String,ExamExamcfg> item = examcfg.get(cfg.getExamname()); 
				item.put(cfg.getItemname(), cfg);
			}
		}
		
		districtMap.clear();
		districtDetailMap.clear();
		System.out.println("===districtnumber========="+districtnumber);
		List<District> districtlist = getHibernateTemplate().find("from District where id like '"+districtnumber.substring(0,4)+"%'");
		for(District cfg : districtlist){
			String id = cfg.getId();
			if(id.length()==6){
				while(id.endsWith("00")){
					id = id.substring(0,id.length()-2);
				}
			}
			if(!districtMap.containsKey(id)){
				districtMap.put(id, getDistrictName(id,cfg.getName()));
			}
//			if(!"530000".equals(cfg.getId())){
				if(cfg.getLevel() == 2){
					if(!districtDetailMap.containsKey("root"+cfg.getId())){
						List rootlist = new ArrayList();
						rootlist.add(cfg);
						districtDetailMap.put("root"+cfg.getId(), rootlist);
					}else{
						List rootlist = districtDetailMap.get("root"+cfg.getId());
						rootlist.add(cfg);
					}
				}else if(cfg.getLevel() == 3){
					if(!districtDetailMap.containsKey("root"+cfg.getId())){
						List rootlist = new ArrayList();
						rootlist.add(cfg);
						districtDetailMap.put("root"+cfg.getId(), rootlist);
					}
				}else if(cfg.getLevel() == 4 ){
					if(!districtDetailMap.containsKey("root"+cfg.getId())){
						List rootlist = new ArrayList();
						rootlist.add(cfg);
						districtDetailMap.put("root"+cfg.getId(), rootlist);
					}

				}else if(cfg.getLevel() == 5 ){
					if(!districtDetailMap.containsKey("root"+cfg.getId())){
						List rootlist = new ArrayList();
						rootlist.add(cfg);
						districtDetailMap.put("root"+cfg.getId(), rootlist);
					}
				}
				
				if(!districtDetailMap.containsKey(cfg.getParentId())){
					List parentlist = new ArrayList();
					parentlist.add(cfg);
					districtDetailMap.put(cfg.getParentId(), parentlist);
				}else{
					List parentlist = (List)districtDetailMap.get(cfg.getParentId());
					parentlist.add(cfg);
				}
//			}
		}
	}
	public List<District> getDistrict(String orgid) {
//		Integer orgId = SecurityManager.currentOperator().getOrgId();
		return districtDetailMap.get(orgid) ;
	}
	public Map<String , List> getAllDistrict(){
		return districtDetailMap;
	}
	
	public static String getDistrictName(String id , String name){
		String ret = name;
		if(id.length()==2){
			if(name.endsWith("省")){
				ret = name.substring(0,name.length()-1);
			}
		}else if(id.length()==4){
			if(name.endsWith("自治州")){
				ret = name.substring(0,name.length()-3);
			}else if(name.endsWith("地区")){
				ret = name.substring(0,name.length()-2);
			}else if(name.endsWith("州") || name.endsWith("市")){
				ret = name.substring(0,name.length()-1);
			}
			//自治州  州  市 地区
		}else if(id.length()==6){
			if(name.endsWith("自治县")){
				ret = name.substring(0,name.length()-3);
			}else if(name.endsWith("市") || name.endsWith("区") || name.endsWith("县")){
				ret = name.substring(0,name.length()-1);
			}
			//自治县 市 区 县
		}else if(id.length()==9){
			if(name.endsWith("社区")){
				ret = name.substring(0,name.length()-2);
			}else if(name.endsWith("镇") || name.endsWith("乡")){
				ret = name.substring(0,name.length()-1);
			}
		}else if(id.length()==12){
			if(name.endsWith("居委会") || name.endsWith("村委会")){
				ret = name.substring(0,name.length()-3);
			}
		}
		return ret;
	}

	public Map<String, ExamItemcfg> getItemcfg() {
		return itemcfg;
	}

	public Map<String, Map<String, ExamExamcfg>> getExamcfg() {
		return examcfg;
	}
	
	public ExamItemcfg getExamItemCfg(String examname , String itemname){
		return itemcfg.get(examcfg.get(examname).get(itemname).getItem());
	}
	
	public boolean hasExamItem(String examname , String itemname){
		return examcfg.get(examname).containsKey(itemname);
	}

	public Map<String, String> getDistrictMap() {
		return districtMap;
	}
	
	

}