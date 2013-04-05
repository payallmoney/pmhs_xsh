package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * ExamBaseinfo entity. @author MyEclipse Persistence Tools
 */

public class ExamBaseinfo implements java.io.Serializable {

	// Fields

	private String id;
	private String fileno;
	private Timestamp visitdate;
	private Timestamp nextvisitdate;
	private String visitdoctor;
	private String inputpersonid;
	private Timestamp inputdate;
	private String fkid;
	private Timestamp checkdate;
	private String examname;

	// Constructors

	/** default constructor */
	public ExamBaseinfo() {
	}

	/** minimal constructor */
	public ExamBaseinfo(String id, String fileno, Timestamp visitdate,
			String visitdoctor, String inputpersonid, Timestamp inputdate,
			Timestamp checkdate, String examname) {
		this.id = id;
		this.fileno = fileno;
		this.visitdate = visitdate;
		this.visitdoctor = visitdoctor;
		this.inputpersonid = inputpersonid;
		this.inputdate = inputdate;
		this.checkdate = checkdate;
		this.examname = examname;
	}

	/** full constructor */
	public ExamBaseinfo(String id, String fileno, Timestamp visitdate,
			Timestamp nextvisitdate, String visitdoctor, String inputpersonid,
			Timestamp inputdate, String fkid, Timestamp checkdate,
			String examname) {
		this.id = id;
		this.fileno = fileno;
		this.visitdate = visitdate;
		this.nextvisitdate = nextvisitdate;
		this.visitdoctor = visitdoctor;
		this.inputpersonid = inputpersonid;
		this.inputdate = inputdate;
		this.fkid = fkid;
		this.checkdate = checkdate;
		this.examname = examname;
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

	public Timestamp getVisitdate() {
		return this.visitdate;
	}

	public void setVisitdate(Timestamp visitdate) {
		this.visitdate = visitdate;
	}

	public Timestamp getNextvisitdate() {
		return this.nextvisitdate;
	}

	public void setNextvisitdate(Timestamp nextvisitdate) {
		this.nextvisitdate = nextvisitdate;
	}

	public String getVisitdoctor() {
		return this.visitdoctor;
	}

	public void setVisitdoctor(String visitdoctor) {
		this.visitdoctor = visitdoctor;
	}

	public String getInputpersonid() {
		return this.inputpersonid;
	}

	public void setInputpersonid(String inputpersonid) {
		this.inputpersonid = inputpersonid;
	}

	public Timestamp getInputdate() {
		return this.inputdate;
	}

	public void setInputdate(Timestamp inputdate) {
		this.inputdate = inputdate;
	}

	public String getFkid() {
		return this.fkid;
	}

	public void setFkid(String fkid) {
		this.fkid = fkid;
	}

	public Timestamp getCheckdate() {
		return this.checkdate;
	}

	public void setCheckdate(Timestamp checkdate) {
		this.checkdate = checkdate;
	}

	public String getExamname() {
		return this.examname;
	}

	public void setExamname(String examname) {
		this.examname = examname;
	}

}