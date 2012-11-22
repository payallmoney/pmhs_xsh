package cn.net.tongfang.framework.security.vo;

/**
 * ScorePersonId entity. @author MyEclipse Persistence Tools
 */

public class ScorePersonId implements java.io.Serializable {

	// Fields

	private String personid;
	private String scorename;

	// Constructors

	/** default constructor */
	public ScorePersonId() {
	}

	/** full constructor */
	public ScorePersonId(String personid, String scorename) {
		this.personid = personid;
		this.scorename = scorename;
	}

	// Property accessors

	public String getPersonid() {
		return this.personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public String getScorename() {
		return this.scorename;
	}

	public void setScorename(String scorename) {
		this.scorename = scorename;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ScorePersonId))
			return false;
		ScorePersonId castOther = (ScorePersonId) other;

		return ((this.getPersonid() == castOther.getPersonid()) || (this
				.getPersonid() != null && castOther.getPersonid() != null && this
				.getPersonid().equals(castOther.getPersonid())))
				&& ((this.getScorename() == castOther.getScorename()) || (this
						.getScorename() != null
						&& castOther.getScorename() != null && this
						.getScorename().equals(castOther.getScorename())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getPersonid() == null ? 0 : this.getPersonid().hashCode());
		result = 37 * result
				+ (getScorename() == null ? 0 : this.getScorename().hashCode());
		return result;
	}

}