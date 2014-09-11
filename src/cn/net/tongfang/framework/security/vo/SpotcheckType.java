package cn.net.tongfang.framework.security.vo;

/**
 * SpotcheckType entity. @author MyEclipse Persistence Tools
 */

public class SpotcheckType implements java.io.Serializable {

	// Fields

	private String code;
	private String value;
	private String remark;
	private Integer mainid;

	// Constructors

	/** default constructor */
	public SpotcheckType() {
	}

	/** minimal constructor */
	public SpotcheckType(String code) {
		this.code = code;
	}

	/** full constructor */
	public SpotcheckType(String code, String value, String remark,
			Integer mainid) {
		this.code = code;
		this.value = value;
		this.remark = remark;
		this.mainid = mainid;
	}

	// Property accessors

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getMainid() {
		return this.mainid;
	}

	public void setMainid(Integer mainid) {
		this.mainid = mainid;
	}

}