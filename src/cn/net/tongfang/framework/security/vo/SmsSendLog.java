package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

import cn.net.tongfang.framework.util.EncryptionUtils;

/**
 * SmsSendLog entity. @author MyEclipse Persistence Tools
 */

public class SmsSendLog implements java.io.Serializable {

	// Fields

	private String id;
	private String examname;
	private String fileno;
	private Timestamp smsdate;
	private String tel;
	private String msg;
	private Integer status;
	private Timestamp sendtime;
	private String error;
	private String tablename;
	private String tableidvalue;
	private String personname;
	private String querytype;
	
	

	// Constructors

	public String getExamname() {
		return examname;
	}

	public void setExamname(String examname) {
		this.examname = examname;
	}

	public String getFileno() {
		return fileno;
	}

	public void setFileno(String fileno) {
		this.fileno = fileno;
	}

	public Timestamp getSmsdate() {
		return smsdate;
	}

	public void setSmsdate(Timestamp smsdate) {
		this.smsdate = smsdate;
	}

	public String getQuerytype() {
		return querytype;
	}

	public void setQuerytype(String querytype) {
		this.querytype = querytype;
	}

	/** default constructor */
	public SmsSendLog() {
	}
	
	public String getPersonname() {
		return personname;
	}

	public void setPersonname(String personname) {
		this.personname = personname;
	}


	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
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

}