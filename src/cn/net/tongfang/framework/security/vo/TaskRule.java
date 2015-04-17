package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * TaskRule entity. @author MyEclipse Persistence Tools
 */

public class TaskRule implements java.io.Serializable {

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
	private String inputpage;
	private String parent;
	private Integer ord;
	private String taskdatecol;
	private String checktable;
	private String checkdatecol;
	private String checkrule;

	// Constructors

	/** default constructor */
	public TaskRule() {
	}

	/** minimal constructor */
	public TaskRule(String id, Integer days, Timestamp optdate) {
		this.id = id;
		this.days = days;
		this.optdate = optdate;
	}

	/** full constructor */
	public TaskRule(String id, String name, String tablename, String col,
			Integer days, Timestamp optdate, String msg, String wherestr,
			String tableidname, String type, String rulestr, String teltable,
			String teljoinstr, String telcol, String idtype, String inputpage,
			String parent, Integer ord, String taskdatecol, String checktable,
			String checkdatecol, String checkrule) {
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
		this.inputpage = inputpage;
		this.parent = parent;
		this.ord = ord;
		this.taskdatecol = taskdatecol;
		this.checktable = checktable;
		this.checkdatecol = checkdatecol;
		this.checkrule = checkrule;
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

	public String getInputpage() {
		return this.inputpage;
	}

	public void setInputpage(String inputpage) {
		this.inputpage = inputpage;
	}

	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Integer getOrd() {
		return this.ord;
	}

	public void setOrd(Integer ord) {
		this.ord = ord;
	}

	public String getTaskdatecol() {
		return this.taskdatecol;
	}

	public void setTaskdatecol(String taskdatecol) {
		this.taskdatecol = taskdatecol;
	}

	public String getChecktable() {
		return this.checktable;
	}

	public void setChecktable(String checktable) {
		this.checktable = checktable;
	}

	public String getCheckdatecol() {
		return this.checkdatecol;
	}

	public void setCheckdatecol(String checkdatecol) {
		this.checkdatecol = checkdatecol;
	}

	public String getCheckrule() {
		return this.checkrule;
	}

	public void setCheckrule(String checkrule) {
		this.checkrule = checkrule;
	}

}