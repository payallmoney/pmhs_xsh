package cn.net.tongfang.framework.security.vo;

// Generated by Hibernate Tools 3.2.4.GA

/**
 * SamTaxorgcode generated by hbm2java
 */
public class SamTaxorgcode implements java.io.Serializable {

	private Integer id;
	private String name;
	private String address;
	private String postcode;
	private String telNumber;
	private String faxNumber;
	private String description;
	private String districtNumber;
	private Integer parentId;
	private Integer level;
	private Integer isDetail;
	private String namePng;
	private Integer isOrgDepart;
	private District district;
	
	public SamTaxorgcode() {
	}

	public SamTaxorgcode(String name, String districtNumber, Integer parentId,
			Integer level, Integer isDetail, String namePng) {
		this.name = name;
		this.districtNumber = districtNumber;
		this.parentId = parentId;
		this.level = level;
		this.isDetail = isDetail;
		this.namePng = namePng;
	}

	public SamTaxorgcode(String name, String address, String postcode,
			String telNumber, String faxNumber, String description,
			String districtNumber, Integer parentId, Integer level,
			Integer isDetail, String namePng,Integer isOrgDepart) {
		this.name = name;
		this.address = address;
		this.postcode = postcode;
		this.telNumber = telNumber;
		this.faxNumber = faxNumber;
		this.description = description;
		this.districtNumber = districtNumber;
		this.parentId = parentId;
		this.level = level;
		this.isDetail = isDetail;
		this.namePng = namePng;
		this.isOrgDepart = isOrgDepart;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getTelNumber() {
		return this.telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getFaxNumber() {
		return this.faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDistrictNumber() {
		return this.districtNumber;
	}

	public void setDistrictNumber(String districtNumber) {
		this.districtNumber = districtNumber;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getIsDetail() {
		return this.isDetail;
	}

	public void setIsDetail(Integer isDetail) {
		this.isDetail = isDetail;
	}

	public String getNamePng() {
		return this.namePng;
	}

	public void setNamePng(String namePng) {
		this.namePng = namePng;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public Integer getIsOrgDepart() {
		return isOrgDepart;
	}

	public void setIsOrgDepart(Integer isOrgDepart) {
		this.isOrgDepart = isOrgDepart;
	}

}
