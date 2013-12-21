package cn.net.tongfang.web.service.bo;

import java.util.Date;

public class StatisticByDistrictQry {
	private Date startDate;
	private Date endDate;
	private String rules;
	private String qryType;
	private String districtNumber;
	private String isQryWipeOut;
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
	public String getRules() {
		return rules;
	}
	public void setRules(String rules) {
		this.rules = rules;
	}
	public String getQryType() {
		return qryType;
	}
	public void setQryType(String qryType) {
		this.qryType = qryType;
	}
	public String getDistrictNumber() {
		return districtNumber;
	}
	public void setDistrictNumber(String districtNumber) {
		this.districtNumber = districtNumber;
	}
	public String getIsQryWipeOut() {
		return isQryWipeOut;
	}
	public void setIsQryWipeOut(String isQryWipeOut) {
		this.isQryWipeOut = isQryWipeOut;
	}
	
}