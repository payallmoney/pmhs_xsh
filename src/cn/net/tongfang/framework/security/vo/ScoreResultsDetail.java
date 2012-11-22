package cn.net.tongfang.framework.security.vo;

/**
 * ScoreResultsDetail entity. @author MyEclipse Persistence Tools
 */

public class ScoreResultsDetail implements java.io.Serializable {

	// Fields

	private ScoreResultsDetailId id;
	private java.math.BigDecimal score;

	// Constructors

	/** default constructor */
	public ScoreResultsDetail() {
	}

	/** full constructor */
	public ScoreResultsDetail(ScoreResultsDetailId id, java.math.BigDecimal score) {
		this.id = id;
		this.score = score;
	}

	// Property accessors

	public ScoreResultsDetailId getId() {
		return this.id;
	}

	public void setId(ScoreResultsDetailId id) {
		this.id = id;
	}

	public java.math.BigDecimal getScore() {
		return this.score;
	}

	public void setScore(java.math.BigDecimal score) {
		this.score = score;
	}

}