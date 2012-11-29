package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * ScoreExamdate entity. @author MyEclipse Persistence Tools
 */

public class ScoreExamdate implements java.io.Serializable {

	// Fields

	private String groupname;
	private Timestamp scoredate;

	// Constructors

	/** default constructor */
	public ScoreExamdate() {
	}

	/** full constructor */
	public ScoreExamdate(String groupname, Timestamp scoredate) {
		this.groupname = groupname;
		this.scoredate = scoredate;
	}

	// Property accessors

	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public Timestamp getScoredate() {
		return this.scoredate;
	}

	public void setScoredate(Timestamp scoredate) {
		this.scoredate = scoredate;
	}

}