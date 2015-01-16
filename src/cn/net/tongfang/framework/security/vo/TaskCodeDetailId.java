package cn.net.tongfang.framework.security.vo;

/**
 * TaskCodeDetailId entity. @author MyEclipse Persistence Tools
 */

public class TaskCodeDetailId implements java.io.Serializable {

	// Fields

	private String id;
	private String col;
	private String tablename;

	// Constructors

	/** default constructor */
	public TaskCodeDetailId() {
	}

	/** full constructor */
	public TaskCodeDetailId(String id, String col, String tablename) {
		this.id = id;
		this.col = col;
		this.tablename = tablename;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCol() {
		return this.col;
	}

	public void setCol(String col) {
		this.col = col;
	}

	public String getTablename() {
		return this.tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TaskCodeDetailId))
			return false;
		TaskCodeDetailId castOther = (TaskCodeDetailId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getCol() == castOther.getCol()) || (this.getCol() != null
						&& castOther.getCol() != null && this.getCol().equals(
						castOther.getCol())))
				&& ((this.getTablename() == castOther.getTablename()) || (this
						.getTablename() != null
						&& castOther.getTablename() != null && this
						.getTablename().equals(castOther.getTablename())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getCol() == null ? 0 : this.getCol().hashCode());
		result = 37 * result
				+ (getTablename() == null ? 0 : this.getTablename().hashCode());
		return result;
	}

}