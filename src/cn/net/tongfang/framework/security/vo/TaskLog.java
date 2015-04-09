package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * TaskLog entity. @author MyEclipse Persistence Tools
 */

public class TaskLog implements java.io.Serializable {

	// Fields

	private TaskLogId id;
	private String tel;
	private String msg;
	private Integer status;
	private Timestamp sendtime;
	private String error;
	private String tablename;
	private String tableidvalue;
	private String personname;
	private String querytype;
	private String id_1;
	private String examid;
	private String parentid;

	// Constructors

	/** default constructor */
	public TaskLog() {
	}

	/** minimal constructor */
	public TaskLog(TaskLogId id, String msg, Integer status, String id_1) {
		this.id = id;
		this.msg = msg;
		this.status = status;
		this.id_1 = id_1;
	}

	/** full constructor */
	public TaskLog(TaskLogId id, String tel, String msg, Integer status,
			Timestamp sendtime, String error, String tablename,
			String tableidvalue, String personname, String querytype,
			String id_1, String examid, String parentid) {
		this.id = id;
		this.tel = tel;
		this.msg = msg;
		this.status = status;
		this.sendtime = sendtime;
		this.error = error;
		this.tablename = tablename;
		this.tableidvalue = tableidvalue;
		this.personname = personname;
		this.querytype = querytype;
		this.id_1 = id_1;
		this.examid = examid;
		this.parentid = parentid;
	}

	// Property accessors

	public TaskLogId getId() {
		return this.id;
	}

	public void setId(TaskLogId id) {
		this.id = id;
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

	public String getId_1() {
		return this.id_1;
	}

	public void setId_1(String id_1) {
		this.id_1 = id_1;
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