package cn.net.tongfang.framework.security.vo;

/**
 * ChildrenException entity. @author MyEclipse Persistence Tools
 */

public class ChildrenException implements java.io.Serializable {

	// Fields

	private String id;
	private String healthFileChildrenId;
	private Integer childrenExceptionId;

	// Constructors

	/** default constructor */
	public ChildrenException() {
	}

	/** full constructor */
	public ChildrenException(String healthFileChildrenId,
			Integer childrenExceptionId) {
		this.healthFileChildrenId = healthFileChildrenId;
		this.childrenExceptionId = childrenExceptionId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHealthFileChildrenId() {
		return this.healthFileChildrenId;
	}

	public void setHealthFileChildrenId(String healthFileChildrenId) {
		this.healthFileChildrenId = healthFileChildrenId;
	}

	public Integer getChildrenExceptionId() {
		return this.childrenExceptionId;
	}

	public void setChildrenExceptionId(Integer childrenExceptionId) {
		this.childrenExceptionId = childrenExceptionId;
	}

}