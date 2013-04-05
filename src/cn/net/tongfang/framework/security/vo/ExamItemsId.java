package cn.net.tongfang.framework.security.vo;

/**
 * ExamItemsId entity. @author MyEclipse Persistence Tools
 */

public class ExamItemsId implements java.io.Serializable {

	// Fields

	private String id;
	private Integer idx;
	private String item;

	// Constructors

	/** default constructor */
	public ExamItemsId() {
	}

	/** full constructor */
	public ExamItemsId(String id, Integer idx, String item) {
		this.id = id;
		this.idx = idx;
		this.item = item;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIdx() {
		return this.idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
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
		if (!(other instanceof ExamItemsId))
			return false;
		ExamItemsId castOther = (ExamItemsId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getIdx() == castOther.getIdx()) || (this.getIdx() != null
						&& castOther.getIdx() != null && this.getIdx().equals(
						castOther.getIdx())))
				&& ((this.getItem() == castOther.getItem()) || (this.getItem() != null
						&& castOther.getItem() != null && this.getItem()
						.equals(castOther.getItem())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getIdx() == null ? 0 : this.getIdx().hashCode());
		result = 37 * result
				+ (getItem() == null ? 0 : this.getItem().hashCode());
		return result;
	}

}