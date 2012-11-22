package cn.net.tongfang.framework.security.vo;

import java.math.BigDecimal;

/**
 * ScoreResults entity. @author MyEclipse Persistence Tools
 */

public class ScoreResults implements java.io.Serializable {

	// Fields

	private ScoreResultsId id;
	private BigDecimal score;

	// Constructors

	/** default constructor */
	public ScoreResults() {
	}

	/** minimal constructor */
	public ScoreResults(ScoreResultsId id) {
		this.id = id;
	}

	/** full constructor */
	public ScoreResults(ScoreResultsId id, BigDecimal score) {
		this.id = id;
		this.score = score;
	}

	// Property accessors

	public ScoreResultsId getId() {
		return this.id;
	}

	public void setId(ScoreResultsId id) {
		this.id = id;
	}

	public BigDecimal getScore() {
		return this.score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

}