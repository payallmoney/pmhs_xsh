package cn.net.tongfang.framework.security.vo;

/**
 * FreeMainId entity. @author MyEclipse Persistence Tools
 */

public class FreeMainId implements java.io.Serializable {

	// Fields

	private String fileno;
	private String examname;

	// Constructors

	/** default constructor */
	public FreeMainId() {
	}

	/** full constructor */
	public FreeMainId(String fileno, String examname) {
		this.fileno = fileno;
		this.examname = examname;
	}

	// Property accessors

	public String getFileno() {
		return this.fileno;
	}

	public void setFileno(String fileno) {
		this.fileno = fileno;
	}

	public String getExamname() {
		return this.examname;
	}

	public void setExamname(String examname) {
		this.examname = examname;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof FreeMainId))
			return false;
		FreeMainId castOther = (FreeMainId) other;

		return ((this.getFileno() == castOther.getFileno()) || (this
				.getFileno() != null && castOther.getFileno() != null && this
				.getFileno().equals(castOther.getFileno())))
				&& ((this.getExamname() == castOther.getExamname()) || (this
						.getExamname() != null
						&& castOther.getExamname() != null && this
						.getExamname().equals(castOther.getExamname())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getFileno() == null ? 0 : this.getFileno().hashCode());
		result = 37 * result
				+ (getExamname() == null ? 0 : this.getExamname().hashCode());
		return result;
	}

}