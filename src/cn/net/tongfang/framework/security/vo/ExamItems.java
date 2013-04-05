package cn.net.tongfang.framework.security.vo;

/**
 * ExamItems entity. @author MyEclipse Persistence Tools
 */

public class ExamItems implements java.io.Serializable {

	// Fields

	private ExamItemsId id;
	private String value;

	// Constructors

	/** default constructor */
	public ExamItems() {
	}

	/** minimal constructor */
	public ExamItems(ExamItemsId id, String value) {
		this.id = id;
		this.value = value;
	}


	// Property accessors

	public ExamItemsId getId() {
		return this.id;
	}

	public void setId(ExamItemsId id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}