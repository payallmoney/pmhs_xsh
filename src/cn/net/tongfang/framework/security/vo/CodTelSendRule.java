package cn.net.tongfang.framework.security.vo;

import java.util.Date;

/**
 * CodTelSendRule entity. @author MyEclipse Persistence Tools
 */

public class CodTelSendRule implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String tablename;
	private String col;
	private Integer days;
	private Date optdate;

	// Constructors

	/** default constructor */
	public CodTelSendRule() {
	}

	/** minimal constructor */
	public CodTelSendRule(String tablename, String col, Integer days,
			Date optdate) {
		this.tablename = tablename;
		this.col = col;
		this.days = days;
		this.optdate = optdate;
	}

	/** full constructor */
	public CodTelSendRule(String name, String tablename, String col,
			Integer days, Date optdate) {
		this.name = name;
		this.tablename = tablename;
		this.col = col;
		this.days = days;
		this.optdate = optdate;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTablename() {
		return this.tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getCol() {
		return this.col;
	}

	public void setCol(String col) {
		this.col = col;
	}

	public Integer getDays() {
		return this.days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Date getOptdate() {
		return this.optdate;
	}

	public void setOptdate(Date optdate) {
		this.optdate = optdate;
	}

}