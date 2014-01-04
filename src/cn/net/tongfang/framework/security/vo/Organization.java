package cn.net.tongfang.framework.security.vo;

/**
 * Organization entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Organization implements java.io.Serializable {

	// Fields

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
	private byte isDetail;
	private String namePng;
	private Integer isOrgDepart;

	// Constructors

	/** default constructor */
	public Organization() {
	}

	/** minimal constructor */
	public Organization(Integer id, String name, String districtNumber,
			Integer parentId, Integer level, byte isDetail, String namePng,
			Integer isOrgDepart) {
		this.id = id;
		this.name = name;
		this.districtNumber = districtNumber;
		this.parentId = parentId;
		this.level = level;
		this.isDetail = isDetail;
		this.namePng = namePng;
		this.isOrgDepart = isOrgDepart;
	}

	/** full constructor */
	public Organization(Integer id, String name, String address,
			String postcode, String telNumber, String faxNumber,
			String description, String districtNumber, Integer parentId,
			Integer level, byte isDetail, String namePng, Integer isOrgDepart) {
		this.id = id;
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

	// Property accessors

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

	public byte getIsDetail() {
		return this.isDetail;
	}

	public void setIsDetail(byte isDetail) {
		this.isDetail = isDetail;
	}

	public String getNamePng() {
		return this.namePng;
	}

	public void setNamePng(String namePng) {
		this.namePng = namePng;
	}

	public Integer getIsOrgDepart() {
		return this.isOrgDepart;
	}

	public void setIsOrgDepart(Integer isOrgDepart) {
		this.isOrgDepart = isOrgDepart;
	}

}