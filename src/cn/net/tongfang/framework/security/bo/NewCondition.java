package cn.net.tongfang.framework.security.bo;

public class NewCondition {
	private String filterKey;
	private String filterVal;
	private String opt;
	private String type;
	private boolean notsql;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOpt() {
		return opt;
	}
	public void setOpt(String opt) {
		this.opt = opt;
	}
	public String getFilterKey() {
		return filterKey;
	}
	public void setFilterKey(String filterKey) {
		this.filterKey = filterKey;
	}
	public String getFilterVal() {
		return filterVal;
	}
	public void setFilterVal(String filterVal) {
		this.filterVal = filterVal;
	}
	public boolean isNotsql() {
		return notsql;
	}
	public void setNotsql(boolean notsql) {
		this.notsql = notsql;
	}
	
}
