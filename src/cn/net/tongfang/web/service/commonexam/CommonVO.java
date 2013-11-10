package cn.net.tongfang.web.service.commonexam;

import java.util.List;
import java.util.Map;

import cn.net.tongfang.framework.security.vo.ExamBaseinfo;

public class CommonVO implements java.io.Serializable{
	private ExamBaseinfo base;
	private List<Map> items;
	private Map man;
	private Map woman;
	private Map districtMap;
	private Map<String , Map> fileinfo;
	public Map getDistrictMap() {
		return districtMap;
	}
	public void setDistrictMap(Map districtMap) {
		this.districtMap = districtMap;
	}
	public Map getWoman() {
		return woman;
	}
	public void setWoman(Map woman) {
		this.woman = woman;
	}
	public Map getMan() {
		return man;
	}
	public void setMan(Map man) {
		this.man = man;
	}
	public ExamBaseinfo getBase() {
		return base;
	}
	public void setBase(ExamBaseinfo base) {
		this.base = base;
	}
	public List<Map> getItems() {
		return items;
	}
	public void setItems(List<Map> items) {
		this.items = items;
	}
	public Map<String, Map> getFileinfo() {
		return fileinfo;
	}
	public void setFileinfo(Map<String, Map> fileinfo) {
		this.fileinfo = fileinfo;
	}
	
}
