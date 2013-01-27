package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

import cn.net.tongfang.framework.util.EncryptionUtils;

/**
 * SmsSendLog entity. @author MyEclipse Persistence Tools
 */

public class SmsSendLog implements java.io.Serializable {

	// Fields

	private SmsSendLogId id;
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

	public String getQuerytype() {
		return querytype;
	}

	public void setQuerytype(String querytype) {
		this.querytype = querytype;
	}

	/** default constructor */
	public SmsSendLog() {
	}

	/** minimal constructor */
	public SmsSendLog(SmsSendLogId id, String tel, String msg, Integer status) {
		this.id = id;
		this.tel = tel;
		this.msg = msg;
		this.status = status;
	}
	
	

	/** full constructor */
	public SmsSendLog(SmsSendLogId id, String tel, String msg, Integer status,
			Timestamp sendtime, String error, String tablename,
			String tableidvalue,String personname,String querytype) {
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
	}
	
	
	public String getPersonname() {
		return personname;
	}

	public void setPersonname(String personname) {
		this.personname = personname;
	}

	public SmsSendLog(SmsSendLog vo,String flag) {
		//取出加解密的结果  true 为进行解密, false为进行加密
		if("denc".equals(flag)){
			this.id = new SmsSendLogId();
			this.id.setExamname(vo.getId().getExamname());
			this.id.setFileno(EncryptionUtils.decipher(vo.getId().getFileno()));
			this.id.setSmsdate(vo.getId().getSmsdate());
			this.tel = vo.getTel();
			this.msg = vo.getMsg();
			this.status = vo.getStatus();
			this.sendtime = vo.getSendtime();
			this.tableidvalue = vo.getTableidvalue();
			this.tablename = vo.getTablename();
			this.error = vo.getError();
		}else{
			this.id = new SmsSendLogId();
			this.id.setExamname(vo.getId().getExamname());
			this.id.setFileno(EncryptionUtils.encry(vo.getId().getFileno()));
			this.id.setSmsdate(vo.getId().getSmsdate());
			this.tel = vo.getTel();
			this.msg = vo.getMsg();
			this.status = vo.getStatus();
			this.sendtime = vo.getSendtime();
			this.error = vo.getError();
			this.tableidvalue = vo.getTableidvalue();
			this.tablename = vo.getTablename();
		}
	}

	// Property accessors

	public SmsSendLogId getId() {
		return this.id;
	}

	public void setId(SmsSendLogId id) {
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