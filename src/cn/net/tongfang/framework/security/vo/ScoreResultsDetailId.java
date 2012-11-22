package cn.net.tongfang.framework.security.vo;

/**
 * ScoreResultsDetailId entity. @author MyEclipse Persistence Tools
 */

public class ScoreResultsDetailId implements java.io.Serializable {

	// Fields

	private String personid;
	private String scorename;
	private String item;
	private String detailitem;

	// Constructors

	/** default constructor */
	public ScoreResultsDetailId() {
	}

	/** full constructor */
	public ScoreResultsDetailId(String personid, String scorename, String item,
			String detailitem) {
		this.personid = personid;
		this.scorename = scorename;
		this.item = item;
		this.detailitem = detailitem;
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

	public String getDetailitem() {
		return this.detailitem;
	}

	public void setDetailitem(String detailitem) {
		this.detailitem = detailitem;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ScoreResultsDetailId))
			return false;
		ScoreResultsDetailId castOther = (ScoreResultsDetailId) other;

		return ((this.getPersonid() == castOther.getPersonid()) || (this
				.getPersonid() != null && castOther.getPersonid() != null && this
				.getPersonid().equals(castOther.getPersonid())))
				&& ((this.getScorename() == castOther.getScorename()) || (this
						.getScorename() != null
						&& castOther.getScorename() != null && this
						.getScorename().equals(castOther.getScorename())))
				&& ((this.getItem() == castOther.getItem()) || (this.getItem() != null
						&& castOther.getItem() != null && this.getItem()
						.equals(castOther.getItem())))
				&& ((this.getDetailitem() == castOther.getDetailitem()) || (this
						.getDetailitem() != null
						&& castOther.getDetailitem() != null && this
						.getDetailitem().equals(castOther.getDetailitem())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getPersonid() == null ? 0 : this.getPersonid().hashCode());
		result = 37 * result
				+ (getScorename() == null ? 0 : this.getScorename().hashCode());
		result = 37 * result
				+ (getItem() == null ? 0 : this.getItem().hashCode());
		result = 37
				* result
				+ (getDetailitem() == null ? 0 : this.getDetailitem()
						.hashCode());
		return result;
	}

}