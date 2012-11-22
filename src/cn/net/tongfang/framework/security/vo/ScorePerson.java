package cn.net.tongfang.framework.security.vo;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * ScorePerson entity. @author MyEclipse Persistence Tools
 */

public class ScorePerson implements java.io.Serializable {

	// Fields

	private ScorePersonId id;
	private String examgroup;
	private BigDecimal allcount;
	private Date examdate;

	// Constructors

	/** default constructor */
	public ScorePerson() {
	}

	/** minimal constructor */
	public ScorePerson(ScorePersonId id, String examgroup, BigDecimal allcount) {
		this.id = id;
		this.examgroup = examgroup;
		this.allcount = allcount;
	}

	/** full constructor */
	public ScorePerson(ScorePersonId id, String examgroup, BigDecimal allcount,
			Date examdate) {
		this.id = id;
		this.examgroup = examgroup;
		this.allcount = allcount;
		this.examdate = examdate;
	}

	// Property accessors

	public ScorePersonId getId() {
		return this.id;
	}

	public void setId(ScorePersonId id) {
		this.id = id;
	}

	public String getExamgroup() {
		return this.examgroup;
	}

	public void setExamgroup(String examgroup) {
		this.examgroup = examgroup;
	}

	public BigDecimal getAllcount() {
		return this.allcount;
	}

	public void setAllcount(BigDecimal allcount) {
		this.allcount = allcount;
	}

	public Date getExamdate() {
		return this.examdate;
	}

	public void setExamdate(Date examdate) {
		this.examdate = examdate;
	}

}