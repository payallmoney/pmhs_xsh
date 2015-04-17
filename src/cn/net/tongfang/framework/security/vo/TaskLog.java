package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * TaskLog entity. @author MyEclipse Persistence Tools
 */

public class TaskLog implements java.io.Serializable {

	// Fields

	private String id;
	private Timestamp smsdate;
	private String examname;
	private String fileno;
	private String tel;
	private String msg;
	private Integer status;
	private Timestamp sendtime;
	private String error;
	private String tablename;
	private String tableidvalue;
	private String personname;
	private String querytype;
	private String examid;
	private String parentid;

	// Constructors

	/** default constructor */
	public TaskLog() {
	}

	/** minimal constructor */
	public TaskLog(String id, Timestamp smsdate, String examname,
			String fileno, String msg, Integer status) {
		this.id = id;
		this.smsdate = smsdate;
		this.examname = examname;
		this.fileno = fileno;
		this.msg = msg;
		this.status = status;
	}

	/** full constructor */
	public TaskLog(String id, Timestamp smsdate, String examname,
			String fileno, String tel, String msg, Integer status,
			Timestamp sendtime, String error, String tablename,
			String tableidvalue, String personname, String querytype,
			String examid, String parentid) {
		this.id = id;
		this.smsdate = smsdate;
		this.examname = examname;
		this.fileno = fileno;
		this.tel = tel;
		this.msg = msg;
		this.status = status;
		this.sendtime = sendtime;
		this.error = error;
		this.tablename = tablename;
		this.tableidvalue = tableidvalue;
		this.personname = personname;
		this.querytype = querytype;
		this.examid = examid;
		this.parentid = parentid;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getSmsdate() {
		return this.smsdate;
	}

	public void setSmsdate(Timestamp smsdate) {
		this.smsdate = smsdate;
	}

	public String getExamname() {
		return this.examname;
	}

	public void setExamname(String examname) {
		this.examname = examname;
	}

	public String getFileno() {
		return this.fileno;
	}

	public void setFileno(String fileno) {
		this.fileno = fileno;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getSendtime() {
		return this.sendtime;
	}

	public void setSendtime(Timestamp sendtime) {
		this.sendtime = sendtime;
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getTablename() {
		return this.tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getTableidvalue() {
		return this.tableidvalue;
	}

	public void setTableidvalue(String tableidvalue) {
		this.tableidvalue = tableidvalue;
	}

	public String getPersonname() {
		return this.personname;
	}

	public void setPersonname(String personname) {
		this.personname = personname;
	}

	public String getQuerytype() {
		return this.querytype;
	}

	public void setQuerytype(String querytype) {
		this.querytype = querytype;
	}

	public String getExamid() {
		return this.examid;
	}

	public void setExamid(String examid) {
		this.examid = examid;
	}

	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

}