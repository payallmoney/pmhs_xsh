package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * FreeSub entity. @author MyEclipse Persistence Tools
 */

public class FreeSub implements java.io.Serializable {

	// Fields

	private String id;
	private String fileno;
	private String examname;
	private String examid;
	private Timestamp examtime;
	private String inputpersonid;
	private Integer status;

	// Constructors

	/** default constructor */
	public FreeSub() {
	}

	/** minimal constructor */
	public FreeSub(String id, String fileno, String examname, String examid,
			String inputpersonid, Integer status) {
		this.id = id;
		this.fileno = fileno;
		this.examname = examname;
		this.examid = examid;
		this.inputpersonid = inputpersonid;
		this.status = status;
	}

	/** full constructor */
	public FreeSub(String id, String fileno, String examname, String examid,
			Timestamp examtime, String inputpersonid, Integer status) {
		this.id = id;
		this.fileno = fileno;
		this.examname = examname;
		this.examid = examid;
		this.examtime = examtime;
		this.inputpersonid = inputpersonid;
		this.status = status;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileno() {
		return this.fileno;
	}

	public void setFileno(String fileno) {
		this.fileno = fileno;
	}

	public String getExamname() {
		return this.examname;
	}

	public void setExamname(String examname) {
		this.examname = examname;
	}

	public String getExamid() {
		return this.examid;
	}

	public void setExamid(String examid) {
		this.examid = examid;
	}

	public Timestamp getExamtime() {
		return this.examtime;
	}

	public void setExamtime(Timestamp examtime) {
		this.examtime = examtime;
	}

	public String getInputpersonid() {
		return this.inputpersonid;
	}

	public void setInputpersonid(String inputpersonid) {
		this.inputpersonid = inputpersonid;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}