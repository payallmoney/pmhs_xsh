package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * TaskLogId entity. @author MyEclipse Persistence Tools
 */

public class TaskLogId implements java.io.Serializable {

	// Fields

	private Timestamp smsdate;
	private String examname;
	private String fileno;

	// Constructors

	/** default constructor */
	public TaskLogId() {
	}

	/** full constructor */
	public TaskLogId(Timestamp smsdate, String examname, String fileno) {
		this.smsdate = smsdate;
		this.examname = examname;
		this.fileno = fileno;
	}

	// Property accessors

	public Timestamp getSmsdate() {
		return this.smsdate;
	}

	public void setSmsdate(Timestamp smsdate) {
		this.smsdate = smsdate;
	}

	public String getExamname() {
		return this.examname;
	}

	public void setExamname(String examname) {
		this.examname = examname;
	}

	public String getFileno() {
		return this.fileno;
	}

	public void setFileno(String fileno) {
		this.fileno = fileno;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TaskLogId))
			return false;
		TaskLogId castOther = (TaskLogId) other;

		return ((this.getSmsdate() == castOther.getSmsdate()) || (this
				.getSmsdate() != null && castOther.getSmsdate() != null && this
				.getSmsdate().equals(castOther.getSmsdate())))
				&& ((this.getExamname() == castOther.getExamname()) || (this
						.getExamname() != null
						&& castOther.getExamname() != null && this
						.getExamname().equals(castOther.getExamname())))
				&& ((this.getFileno() == castOther.getFileno()) || (this
						.getFileno() != null && castOther.getFileno() != null && this
						.getFileno().equals(castOther.getFileno())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getSmsdate() == null ? 0 : this.getSmsdate().hashCode());
		result = 37 * result
				+ (getExamname() == null ? 0 : this.getExamname().hashCode());
		result = 37 * result
				+ (getFileno() == null ? 0 : this.getFileno().hashCode());
		return result;
	}

}