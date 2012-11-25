package cn.net.tongfang.framework.security.vo;

/**
 * ScoreGroup entity. @author MyEclipse Persistence Tools
 */

public class ScoreGroup implements java.io.Serializable {

	// Fields

	private ScoreGroupId id;
	private Integer ord;

	// Constructors

	/** default constructor */
	public ScoreGroup() {
	}

	/** full constructor */
	public ScoreGroup(ScoreGroupId id, Integer ord) {
		this.id = id;
		this.ord = ord;
	}

	// Property accessors

	public ScoreGroupId getId() {
		return this.id;
	}

	public void setId(ScoreGroupId id) {
		this.id = id;
	}

	public Integer getOrd() {
		return this.ord;
	}

	public void setOrd(Integer ord) {
		this.ord = ord;
	}

}