package cn.net.tongfang.framework.security.vo;

// Generated by Hibernate Tools 3.2.4.GA

/**
 * District generated by hbm2java
 */
public class District implements java.io.Serializable {

	private String id;
	private String name;
	private String parentId;
	private String description;
	private Integer level;
	private boolean isDetail;
	private String namePng;
	private String parentName;
	private Integer orgId;
	
	private SamTaxorgcode org;

	public District() {
	}

	public District(String id, String name, Integer level, boolean isDetail,
			Integer orgId) {
		this.id = id;
		this.name = name;
		this.level = level;
		this.isDetail = isDetail;
		this.orgId = orgId;
	}

	public District(String id, String name, String parentId,
			String description, Integer level, boolean isDetail,
			String namePng, String parentName, Integer orgId) {
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.description = description;
		this.level = level;
		this.isDetail = isDetail;
		this.namePng = namePng;
		this.parentName = parentName;
		this.orgId = orgId;
	}

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

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public boolean isIsDetail() {
		return this.isDetail;
	}

	public void setIsDetail(boolean isDetail) {
		this.isDetail = isDetail;
	}

	public String getNamePng() {
		return this.namePng;
	}

	public void setNamePng(String namePng) {
		this.namePng = namePng;
	}

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public SamTaxorgcode getOrg() {
		return org;
	}

	public void setOrg(SamTaxorgcode org) {
		this.org = org;
	}

}
