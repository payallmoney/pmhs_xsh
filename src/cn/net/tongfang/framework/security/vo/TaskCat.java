package cn.net.tongfang.framework.security.vo;

/**
 * TaskCat entity. @author MyEclipse Persistence Tools
 */

public class TaskCat implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private Integer ord;

	// Constructors

	/** default constructor */
	public TaskCat() {
	}

	/** minimal constructor */
	public TaskCat(String id) {
		this.id = id;
	}

	/** full constructor */
	public TaskCat(String id, String name, Integer ord) {
		this.id = id;
		this.name = name;
		this.ord = ord;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrd() {
		return this.ord;
	}

	public void setOrd(Integer ord) {
		this.ord = ord;
	}

}