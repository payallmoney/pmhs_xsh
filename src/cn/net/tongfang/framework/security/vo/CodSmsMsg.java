package cn.net.tongfang.framework.security.vo;

/**
 * CodSmsMsg entity. @author MyEclipse Persistence Tools
 */

public class CodSmsMsg implements java.io.Serializable {

	// Fields

	private String id;
	private String type;
	private String name;
	private String title;
	private String content;
	private String after;

	// Constructors

	/** default constructor */
	public CodSmsMsg() {
	}

	/** full constructor */
	public CodSmsMsg(String type, String name, String title, String content,
			String after) {
		this.type = type;
		this.name = name;
		this.title = title;
		this.content = content;
		this.after = after;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAfter() {
		return this.after;
	}

	public void setAfter(String after) {
		this.after = after;
	}

}