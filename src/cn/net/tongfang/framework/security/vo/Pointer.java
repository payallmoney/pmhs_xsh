package cn.net.tongfang.framework.security.vo;

/**
 * Pointer entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Pointer implements java.io.Serializable {

	// Fields

	private String id;
	private String organId;
	private String organName;

	// Constructors

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	/** default constructor */
	public Pointer() {
	}

	/** full constructor */
	public Pointer(String id, String organId, String organName) {
		this.id = id;
		this.organId = organId;
		this.organName = organName;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrganId() {
		return this.organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

}