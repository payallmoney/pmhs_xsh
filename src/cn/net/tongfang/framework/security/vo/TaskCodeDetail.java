package cn.net.tongfang.framework.security.vo;

/**
 * TaskCodeDetail entity. @author MyEclipse Persistence Tools
 */

public class TaskCodeDetail implements java.io.Serializable {

	// Fields

	private TaskCodeDetailId id;
	private String valuetype;
	private String coltext;

	// Constructors

	/** default constructor */
	public TaskCodeDetail() {
	}

	/** minimal constructor */
	public TaskCodeDetail(TaskCodeDetailId id) {
		this.id = id;
	}

	/** full constructor */
	public TaskCodeDetail(TaskCodeDetailId id, String valuetype, String coltext) {
		this.id = id;
		this.valuetype = valuetype;
		this.coltext = coltext;
	}

	// Property accessors

	public TaskCodeDetailId getId() {
		return this.id;
	}

	public void setId(TaskCodeDetailId id) {
		this.id = id;
	}

	public String getValuetype() {
		return this.valuetype;
	}

	public void setValuetype(String valuetype) {
		this.valuetype = valuetype;
	}

	public String getColtext() {
		return this.coltext;
	}

	public void setColtext(String coltext) {
		this.coltext = coltext;
	}

}