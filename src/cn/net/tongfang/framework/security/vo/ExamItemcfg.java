package cn.net.tongfang.framework.security.vo;

/**
 * ExamItemcfg entity. @author MyEclipse Persistence Tools
 */

public class ExamItemcfg implements java.io.Serializable {

	// Fields

	private String code;
	private String name;
	private String valuetype;

	// Constructors

	/** default constructor */
	public ExamItemcfg() {
	}

	/** full constructor */
	public ExamItemcfg(String code, String name, String valuetype) {
		this.code = code;
		this.name = name;
		this.valuetype = valuetype;
	}

	// Property accessors

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValuetype() {
		return this.valuetype;
	}

	public void setValuetype(String valuetype) {
		this.valuetype = valuetype;
	}

}