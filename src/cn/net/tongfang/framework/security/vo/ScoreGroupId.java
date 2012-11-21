package cn.net.tongfang.framework.security.vo;

/**
 * ScoreGroupId entity. @author MyEclipse Persistence Tools
 */

public class ScoreGroupId implements java.io.Serializable {

	// Fields

	private String groupname;
	private String item;

	// Constructors

	/** default constructor */
	public ScoreGroupId() {
	}

	/** full constructor */
	public ScoreGroupId(String groupname, String item) {
		this.groupname = groupname;
		this.item = item;
	}

	// Property accessors

	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
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
		if (!(other instanceof ScoreGroupId))
			return false;
		ScoreGroupId castOther = (ScoreGroupId) other;

		return ((this.getGroupname() == castOther.getGroupname()) || (this
				.getGroupname() != null && castOther.getGroupname() != null && this
				.getGroupname().equals(castOther.getGroupname())))
				&& ((this.getItem() == castOther.getItem()) || (this.getItem() != null
						&& castOther.getItem() != null && this.getItem()
						.equals(castOther.getItem())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getGroupname() == null ? 0 : this.getGroupname().hashCode());
		result = 37 * result
				+ (getItem() == null ? 0 : this.getItem().hashCode());
		return result;
	}

}