package cn.net.tongfang.framework.security.vo;

/**
 * TcmserviceForChildren entity. @author MyEclipse Persistence Tools
 */

public class TcmserviceForChildren implements java.io.Serializable {

	// Fields

	private String id;
	private String childrenMediExamId;
	private Integer manageServiceId;

	// Constructors

	/** default constructor */
	public TcmserviceForChildren() {
	}

	/** full constructor */
	public TcmserviceForChildren(String childrenMediExamId,
			Integer manageServiceId) {
		this.childrenMediExamId = childrenMediExamId;
		this.manageServiceId = manageServiceId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChildrenMediExamId() {
		return this.childrenMediExamId;
	}

	public void setChildrenMediExamId(String childrenMediExamId) {
		this.childrenMediExamId = childrenMediExamId;
	}

	public Integer getManageServiceId() {
		return this.manageServiceId;
	}

	public void setManageServiceId(Integer manageServiceId) {
		this.manageServiceId = manageServiceId;
	}

}