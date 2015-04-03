package cn.net.tongfang.framework.security.vo;

/**
 * TaskDefaults entity. @author MyEclipse Persistence Tools
 */

public class TaskDefaults implements java.io.Serializable {

	// Fields

	private String id;
	private String urlname;
	private String code;
	private String value;
	private String valuetype;
	private Boolean valid;

	// Constructors

	/** default constructor */
	public TaskDefaults() {
	}

	/** minimal constructor */
	public TaskDefaults(String id) {
		this.id = id;
	}

	/** full constructor */
	public TaskDefaults(String id, String urlname, String code, String value,
			String valuetype, Boolean valid) {
		this.id = id;
		this.urlname = urlname;
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

	public String getUrlname() {
		return this.urlname;
	}

	public void setUrlname(String urlname) {
		this.urlname = urlname;
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