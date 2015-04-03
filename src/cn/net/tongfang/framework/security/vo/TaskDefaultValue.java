package cn.net.tongfang.framework.security.vo;

/**
 * TaskDefaultValue entity. @author MyEclipse Persistence Tools
 */

public class TaskDefaultValue implements java.io.Serializable {

	// Fields

	private TaskDefaultValueId id;
	private String urlname;
	private String content;

	// Constructors

	/** default constructor */
	public TaskDefaultValue() {
	}

	/** minimal constructor */
	public TaskDefaultValue(TaskDefaultValueId id) {
		this.id = id;
	}

	/** full constructor */
	public TaskDefaultValue(TaskDefaultValueId id, String urlname,
			String content) {
		this.id = id;
		this.urlname = urlname;
		this.content = content;
	}

	// Property accessors

	public TaskDefaultValueId getId() {
		return this.id;
	}

	public void setId(TaskDefaultValueId id) {
		this.id = id;
	}

	public String getUrlname() {
		return this.urlname;
	}

	public void setUrlname(String urlname) {
		this.urlname = urlname;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}