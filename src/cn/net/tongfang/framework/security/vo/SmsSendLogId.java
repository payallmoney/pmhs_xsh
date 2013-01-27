package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * SmsSendLogId entity. @author MyEclipse Persistence Tools
 */

public class SmsSendLogId implements java.io.Serializable {

	// Fields

	private String examname;
	private String fileno;
	private Timestamp smsdate;

	// Constructors

	/** default constructor */
	public SmsSendLogId() {
	}

	/** full constructor */
	public SmsSendLogId(String examname, String fileno, Timestamp smsdate) {
		this.examname = examname;
		this.fileno = fileno;
		this.smsdate = smsdate;
	}

	// Property accessors

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

	public Timestamp getSmsdate() {
		return this.smsdate;
	}

	public void setSmsdate(Timestamp smsdate) {
		this.smsdate = smsdate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SmsSendLogId))
			return false;
		SmsSendLogId castOther = (SmsSendLogId) other;

		return ((this.getExamname() == castOther.getExamname()) || (this
				.getExamname() != null && castOther.getExamname() != null && this
				.getExamname().equals(castOther.getExamname())))
				&& ((this.getFileno() == castOther.getFileno()) || (this
						.getFileno() != null && castOther.getFileno() != null && this
						.getFileno().equals(castOther.getFileno())))
				&& ((this.getSmsdate() == castOther.getSmsdate()) || (this
						.getSmsdate() != null && castOther.getSmsdate() != null && this
						.getSmsdate().equals(castOther.getSmsdate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getExamname() == null ? 0 : this.getExamname().hashCode());
		result = 37 * result
				+ (getFileno() == null ? 0 : this.getFileno().hashCode());
		result = 37 * result
				+ (getSmsdate() == null ? 0 : this.getSmsdate().hashCode());
		return result;
	}

}