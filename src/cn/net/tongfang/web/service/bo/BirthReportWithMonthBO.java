package cn.net.tongfang.web.service.bo;

public class BirthReportWithMonthBO {
	private Integer orgId;
	private Integer reportYear;
	private Integer reportMonth;
	private String htmls;
	private String titles;
	private String queryCondition;
	
	
	
	public String getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}
	public String getHtmls() {
		return htmls;
	}
	public void setHtmls(String htmls) {
		this.htmls = htmls;
	}
	public String getTitles() {
		return titles;
	}
	public void setTitles(String titles) {
		this.titles = titles;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Integer getReportYear() {
		return reportYear;
	}
	public void setReportYear(Integer reportYear) {
		this.reportYear = reportYear;
	}
	public Integer getReportMonth() {
		return reportMonth;
	}
	public void setReportMonth(Integer reportMonth) {
		this.reportMonth = reportMonth;
	}
	
	
}
