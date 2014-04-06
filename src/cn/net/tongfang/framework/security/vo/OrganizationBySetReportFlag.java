package cn.net.tongfang.framework.security.vo;

/**
 * OrganizationBySetReportFlag entity. @author MyEclipse Persistence Tools
 */

public class OrganizationBySetReportFlag implements java.io.Serializable {

	// Fields

	private String id;
	private Integer orgId;

	// Constructors

	/** default constructor */
	public OrganizationBySetReportFlag() {
	}

	/** full constructor */
	public OrganizationBySetReportFlag(Integer orgId) {
		this.orgId = orgId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

}