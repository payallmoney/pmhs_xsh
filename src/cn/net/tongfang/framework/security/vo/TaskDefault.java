package cn.net.tongfang.framework.security.vo;

/**
 * TaskDefault entity. @author MyEclipse Persistence Tools
 */

public class TaskDefault implements java.io.Serializable {

	// Fields

	private String id;
	private String district;
	private String empcode;
	private Integer level;
	private String tablename;
	private String code;
	private String value;
	private String valuetype;
	private Boolean valid;

	// Constructors

	/** default constructor */
	public TaskDefault() {
	}

	/** minimal constructor */
	public TaskDefault(String id) {
		this.id = id;
	}

	/** full constructor */
	public TaskDefault(String id, String district, String empcode,
			Integer level, String tablename, String code, String value,
			String valuetype, Boolean valid) {
		this.id = id;
		this.district = district;
		this.empcode = empcode;
		this.level = level;
		this.tablename = tablename;
		this.code = code;
		this.value = value;
		this.valuetype = valuetype;
		this.valid = valid;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getEmpcode() {
		return this.empcode;
	}

	public void setEmpcode(String empcode) {
		this.empcode = empcode;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getTablename() {
		return this.tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

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

	public String getValuetype() {
		return this.valuetype;
	}

	public void setValuetype(String valuetype) {
		this.valuetype = valuetype;
	}

	public Boolean getValid() {
		return this.valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

}