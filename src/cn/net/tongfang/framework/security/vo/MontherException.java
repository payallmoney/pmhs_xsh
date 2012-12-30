package cn.net.tongfang.framework.security.vo;

/**
 * MontherException entity. @author MyEclipse Persistence Tools
 */

public class MontherException implements java.io.Serializable {

	// Fields

	private String id;
	private String healthFileChildrenId;
	private Integer montherExceptionId;

	// Constructors

	/** default constructor */
	public MontherException() {
	}

	/** full constructor */
	public MontherException(String healthFileChildrenId,
			Integer montherExceptionId) {
		this.healthFileChildrenId = healthFileChildrenId;
		this.montherExceptionId = montherExceptionId;
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

	public Integer getMontherExceptionId() {
		return this.montherExceptionId;
	}

	public void setMontherExceptionId(Integer montherExceptionId) {
		this.montherExceptionId = montherExceptionId;
	}

}