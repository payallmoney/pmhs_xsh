package cn.net.tongfang.framework.security.vo;

/**
 * ExportDiv entity. @author MyEclipse Persistence Tools
 */

public class ExportDiv implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mainid;
	private String name;
	private String html;
	private Integer type;

	// Constructors

	/** default constructor */
	public ExportDiv() {
	}

	/** minimal constructor */
	public ExportDiv(Integer id, Integer mainid, String name) {
		this.id = id;
		this.mainid = mainid;
		this.name = name;
	}

	/** full constructor */
	public ExportDiv(Integer id, Integer mainid, String name, String html) {
		this.id = id;
		this.mainid = mainid;
		this.name = name;
		this.html = html;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMainid() {
		return this.mainid;
	}

	public void setMainid(Integer mainid) {
		this.mainid = mainid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHtml() {
		return this.html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	

}