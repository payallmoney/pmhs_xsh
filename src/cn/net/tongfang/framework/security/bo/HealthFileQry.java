package cn.net.tongfang.framework.security.bo;

import java.util.Date;
import java.util.Map;

public class HealthFileQry {
    private String district;
    private String filterKey;
    private String filterValue;
    private int isFirst;
    private String filterVal01;
    private Map<String ,String> params;
    
    private Date startDate;
    private Date endDate;
    
    
    public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public HealthFileQry() {
    }
    
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public HealthFileQry(String district, String filterKey, String filterValue, int isFirst,Date startDate,Date endDate,String filterVal01) {
		this.district = district;
		this.filterKey = filterKey;
		this.filterValue = filterValue;
		this.isFirst = isFirst;
		this.startDate = startDate;
		this.endDate = endDate;
		this.filterVal01 = filterVal01;
	}

	public int getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(int isFirst) {
		this.isFirst = isFirst;
	}

	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getFilterKey() {
		return filterKey;
	}
	public void setFilterKey(String filterKey) {
		this.filterKey = filterKey;
	}
	public String getFilterValue() {
		return filterValue;
	}
	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

	public String getFilterVal01() {
		return filterVal01;
	}

	public void setFilterVal01(String filterVal01) {
		this.filterVal01 = filterVal01;
	}
}
