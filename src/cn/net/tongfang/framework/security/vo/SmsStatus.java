package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;


/**
 * SmsStatus entity. @author MyEclipse Persistence Tools
 */

public class SmsStatus implements java.io.Serializable {

	// Fields

	private Timestamp smsdate;
	private Integer iscreated;
	private Integer issended;

	// Constructors

	/** default constructor */
	public SmsStatus() {
	}

	/** full constructor */
	public SmsStatus(Timestamp smsdate, Integer iscreated, Integer issended) {
		this.smsdate = smsdate;
		this.iscreated = iscreated;
		this.issended = issended;
	}

	// Property accessors

	public Timestamp getSmsdate() {
		return this.smsdate;
	}

	public void setSmsdate(Timestamp smsdate) {
		this.smsdate = smsdate;
	}

	public Integer getIscreated() {
		return this.iscreated;
	}

	public void setIscreated(Integer iscreated) {
		this.iscreated = iscreated;
	}

	public Integer getIssended() {
		return this.issended;
	}

	public void setIssended(Integer issended) {
		this.issended = issended;
	}

}