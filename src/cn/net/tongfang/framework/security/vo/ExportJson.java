package cn.net.tongfang.framework.security.vo;

/**
 * ExportJson entity. @author MyEclipse Persistence Tools
 */

public class ExportJson implements java.io.Serializable {

	// Fields

	private String id;
	private String json;

	// Constructors

	/** default constructor */
	public ExportJson() {
	}

	/** minimal constructor */
	public ExportJson(String id) {
		this.id = id;
	}

	/** full constructor */
	public ExportJson(String id, String json) {
		this.id = id;
		this.json = json;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJson() {
		return this.json;
	}

	public void setJson(String json) {
		this.json = json;
	}

}