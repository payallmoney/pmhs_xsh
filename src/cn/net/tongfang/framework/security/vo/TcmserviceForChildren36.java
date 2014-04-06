package cn.net.tongfang.framework.security.vo;

/**
 * TcmserviceForChildren36 entity. @author MyEclipse Persistence Tools
 */

public class TcmserviceForChildren36 implements java.io.Serializable {

	// Fields

	private String id;
	private String childrenMediExam36id;
	private Integer manageService36id;

	// Constructors

	/** default constructor */
	public TcmserviceForChildren36() {
	}

	/** full constructor */
	public TcmserviceForChildren36(String childrenMediExam36id,
			Integer manageService36id) {
		this.childrenMediExam36id = childrenMediExam36id;
		this.manageService36id = manageService36id;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChildrenMediExam36id() {
		return this.childrenMediExam36id;
	}

	public void setChildrenMediExam36id(String childrenMediExam36id) {
		this.childrenMediExam36id = childrenMediExam36id;
	}

	public Integer getManageService36id() {
		return this.manageService36id;
	}

	public void setManageService36id(Integer manageService36id) {
		this.manageService36id = manageService36id;
	}

}