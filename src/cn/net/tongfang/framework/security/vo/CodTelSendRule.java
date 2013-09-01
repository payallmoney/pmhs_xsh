package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

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
	private Timestamp optdate;
	private String msg;
	private String wherestr;
	private String tableidname;
	private String type;
	private String rulestr;
	private String teltable;
	private String teljoinstr;
	private String telcol;
	private String idtype;

	// Constructors

	/** default constructor */
	public CodTelSendRule() {
	}

	/** minimal constructor */
	public CodTelSendRule(String id, String tablename, String col,
			Integer days, Timestamp optdate, String msg, String wherestr) {
		this.id = id;
		this.tablename = tablename;
		this.col = col;
		this.days = days;
		this.optdate = optdate;
		this.msg = msg;
		this.wherestr = wherestr;
	}

	/** full constructor */
	public CodTelSendRule(String id, String name, String tablename, String col,
			Integer days, Timestamp optdate, String msg, String wherestr,
			String tableidname, String type, String rulestr, String teltable,
			String teljoinstr, String telcol, String idtype) {
		this.id = id;
		this.name = name;
		this.tablename = tablename;
		this.col = col;
		this.days = days;
		this.optdate = optdate;
		this.msg = msg;
		this.wherestr = wherestr;
		this.tableidname = tableidname;
		this.type = type;
		this.rulestr = rulestr;
		this.teltable = teltable;
		this.teljoinstr = teljoinstr;
		this.telcol = telcol;
		this.idtype = idtype;
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

	public Timestamp getOptdate() {
		return this.optdate;
	}

	public void setOptdate(Timestamp optdate) {
		this.optdate = optdate;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getWherestr() {
		return this.wherestr;
	}

	public void setWherestr(String wherestr) {
		this.wherestr = wherestr;
	}

	public String getTableidname() {
		return this.tableidname;
	}

	public void setTableidname(String tableidname) {
		this.tableidname = tableidname;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRulestr() {
		return this.rulestr;
	}

	public void setRulestr(String rulestr) {
		this.rulestr = rulestr;
	}

	public String getTeltable() {
		return this.teltable;
	}

	public void setTeltable(String teltable) {
		this.teltable = teltable;
	}

	public String getTeljoinstr() {
		return this.teljoinstr;
	}

	public void setTeljoinstr(String teljoinstr) {
		this.teljoinstr = teljoinstr;
	}

	public String getTelcol() {
		return this.telcol;
	}

	public void setTelcol(String telcol) {
		this.telcol = telcol;
	}

	public String getIdtype() {
		return this.idtype;
	}

	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}

}