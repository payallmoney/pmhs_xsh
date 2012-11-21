package cn.net.tongfang.framework.security.vo;

/**
 * ScoreResultsId entity. @author MyEclipse Persistence Tools
 */

public class ScoreResultsId implements java.io.Serializable {

	// Fields

	private String personid;
	private String scorename;
	private String item;

	// Constructors

	/** default constructor */
	public ScoreResultsId() {
	}

	/** full constructor */
	public ScoreResultsId(String personid, String scorename, String item) {
		this.personid = personid;
		this.scorename = scorename;
		this.item = item;
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

	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ScoreResultsId))
			return false;
		ScoreResultsId castOther = (ScoreResultsId) other;

		return ((this.getPersonid() == castOther.getPersonid()) || (this
				.getPersonid() != null && castOther.getPersonid() != null && this
				.getPersonid().equals(castOther.getPersonid())))
				&& ((this.getScorename() == castOther.getScorename()) || (this
						.getScorename() != null
						&& castOther.getScorename() != null && this
						.getScorename().equals(castOther.getScorename())))
				&& ((this.getItem() == castOther.getItem()) || (this.getItem() != null
						&& castOther.getItem() != null && this.getItem()
						.equals(castOther.getItem())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getPersonid() == null ? 0 : this.getPersonid().hashCode());
		result = 37 * result
				+ (getScorename() == null ? 0 : this.getScorename().hashCode());
		result = 37 * result
				+ (getItem() == null ? 0 : this.getItem().hashCode());
		return result;
	}

}