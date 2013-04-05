package cn.net.tongfang.framework.security.vo;

/**
 * ExamId entity. @author MyEclipse Persistence Tools
 */

public class ExamId implements java.io.Serializable {

	// Fields

	private String year;
	private Integer max;

	// Constructors

	/** default constructor */
	public ExamId() {
	}

	/** full constructor */
	public ExamId(String year, Integer max) {
		this.year = year;
		this.max = max;
	}

	// Property accessors

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Integer getMax() {
		return this.max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

}