package cn.net.tongfang.framework.security.vo;

/**
 * ScoreGroup entity. @author MyEclipse Persistence Tools
 */

public class ScoreGroup implements java.io.Serializable {

	// Fields

	private ScoreGroupId id;

	// Constructors

	/** default constructor */
	public ScoreGroup() {
	}

	/** full constructor */
	public ScoreGroup(ScoreGroupId id) {
		this.id = id;
	}

	// Property accessors

	public ScoreGroupId getId() {
		return this.id;
	}

	public void setId(ScoreGroupId id) {
		this.id = id;
	}

}