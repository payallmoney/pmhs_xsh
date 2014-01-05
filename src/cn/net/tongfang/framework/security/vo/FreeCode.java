package cn.net.tongfang.framework.security.vo;

/**
 * FreeCode entity. @author MyEclipse Persistence Tools
 */

public class FreeCode implements java.io.Serializable {

	// Fields

	private String code;
	private String name;
	private Integer maxnum;

	// Constructors

	/** default constructor */
	public FreeCode() {
	}

	/** full constructor */
	public FreeCode(String code, String name, Integer maxnum) {
		this.code = code;
		this.name = name;
		this.maxnum = maxnum;
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

	public Integer getMaxnum() {
		return this.maxnum;
	}

	public void setMaxnum(Integer maxnum) {
		this.maxnum = maxnum;
	}

}