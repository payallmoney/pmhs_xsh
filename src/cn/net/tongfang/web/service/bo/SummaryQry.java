package cn.net.tongfang.web.service.bo;

import java.util.Date;

public class SummaryQry {
	private Date startDate;
	private Date endDate;
	private String statisticType;
	private String statisticResult;
	private String fileNo;
	private String isQryWipeOut;
	private Integer orgId;
	private String containLowerLevel;

	public String getFileNo() {
		return this.fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatisticType() {
		return this.statisticType;
	}

	public void setStatisticType(String statisticType) {
		this.statisticType = statisticType;
	}

	public String getStatisticResult() {
		return this.statisticResult;
	}

	public void setStatisticResult(String statisticResult) {
		this.statisticResult = statisticResult;
	}

	public String getIsQryWipeOut() {
		return this.isQryWipeOut;
	}

	public void setIsQryWipeOut(String isQryWipeOut) {
		this.isQryWipeOut = isQryWipeOut;
	}

	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getContainLowerLevel() {
		return this.containLowerLevel;
	}

	public void setContainLowerLevel(String containLowerLevel) {
		this.containLowerLevel = containLowerLevel;
	}
}