package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * BirthReportWithMonthFile entity. @author MyEclipse Persistence Tools
 */

public class BirthReportWithMonthFile implements java.io.Serializable {

	// Fields

	private String id;
	private String fileName;
	private String storeFileName;
	private Integer reportYear;
	private Integer reportMonth;
	private Integer orgId;
	private String inputPersonId;
	private Timestamp inputDate;

	// Constructors

	/** default constructor */
	public BirthReportWithMonthFile() {
	}

	/** full constructor */
	public BirthReportWithMonthFile(String fileName, String storeFileName,
			Integer reportYear, Integer reportMonth, Integer orgId,
			String inputPersonId, Timestamp inputDate) {
		this.fileName = fileName;
		this.storeFileName = storeFileName;
		this.reportYear = reportYear;
		this.reportMonth = reportMonth;
		this.orgId = orgId;
		this.inputPersonId = inputPersonId;
		this.inputDate = inputDate;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getStoreFileName() {
		return this.storeFileName;
	}

	public void setStoreFileName(String storeFileName) {
		this.storeFileName = storeFileName;
	}

	public Integer getReportYear() {
		return this.reportYear;
	}

	public void setReportYear(Integer reportYear) {
		this.reportYear = reportYear;
	}

	public Integer getReportMonth() {
		return this.reportMonth;
	}

	public void setReportMonth(Integer reportMonth) {
		this.reportMonth = reportMonth;
	}

	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getInputPersonId() {
		return this.inputPersonId;
	}

	public void setInputPersonId(String inputPersonId) {
		this.inputPersonId = inputPersonId;
	}

	public Timestamp getInputDate() {
		return this.inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}

}