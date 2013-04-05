package cn.net.tongfang.web.service.commonexam;

import java.util.List;
import java.util.Map;

import cn.net.tongfang.framework.security.vo.ExamBaseinfo;

public class CommonQueryVO {
	private Map<String,String> base;
	private List<Map> items;
	public Map<String, String> getBase() {
		return base;
	}
	public void setBase(Map<String, String> base) {
		this.base = base;
	}
	public List<Map> getItems() {
		return items;
	}
	public void setItems(List<Map> items) {
		this.items = items;
	}
	
}
