package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * HealthFileLoginOff entity. @author MyEclipse Persistence Tools
 */

public class HealthFileLoginOff implements java.io.Serializable {

	// Fields

	private String id;
	private String fileNo;
	private Integer loginOffReason;
	private String loginOffRemark;
	private String loginOffOpt;
	private String loginOffOptOrg;
	private Timestamp loginOffDate;
	private String inputPersonId;
	private Timestamp inputDate;

	// Constructors

	/** default constructor */
	public HealthFileLoginOff() {
	}

	/** minimal constructor */
	public HealthFileLoginOff(String fileNo, Integer loginOffReason) {
		this.fileNo = fileNo;
		this.loginOffReason = loginOffReason;
	}

	/** full constructor */
	public HealthFileLoginOff(String fileNo, Integer loginOffReason,
			String loginOffRemark, String loginOffOpt, String loginOffOptOrg,
			Timestamp loginOffDate, String inputPersonId, Timestamp inputDate) {
		this.fileNo = fileNo;
		this.loginOffReason = loginOffReason;
		this.loginOffRemark = loginOffRemark;
		this.loginOffOpt = loginOffOpt;
		this.loginOffOptOrg = loginOffOptOrg;
		this.loginOffDate = loginOffDate;
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

	public String getFileNo() {
		return this.fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public Integer getLoginOffReason() {
		return this.loginOffReason;
	}

	public void setLoginOffReason(Integer loginOffReason) {
		this.loginOffReason = loginOffReason;
	}

	public String getLoginOffRemark() {
		return this.loginOffRemark;
	}

	public void setLoginOffRemark(String loginOffRemark) {
		this.loginOffRemark = loginOffRemark;
	}

	public String getLoginOffOpt() {
		return this.loginOffOpt;
	}

	public void setLoginOffOpt(String loginOffOpt) {
		this.loginOffOpt = loginOffOpt;
	}

	public String getLoginOffOptOrg() {
		return this.loginOffOptOrg;
	}

	public void setLoginOffOptOrg(String loginOffOptOrg) {
		this.loginOffOptOrg = loginOffOptOrg;
	}

	public Timestamp getLoginOffDate() {
		return this.loginOffDate;
	}

	public void setLoginOffDate(Timestamp loginOffDate) {
		this.loginOffDate = loginOffDate;
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