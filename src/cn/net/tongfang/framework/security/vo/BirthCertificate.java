package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * BirthCertificate entity. @author MyEclipse Persistence Tools
 */

public class BirthCertificate implements java.io.Serializable {

	// Fields

	private String certifiId;
	private String name;
	private String sex;
	private Timestamp birthday;
	private String borthWeekly;
	private String healthStatus;
	private Double weight;
	private Double height;
	private String motherName;
	private Integer motherAge;
	private String motherNationality;
	private String motherNation;
	private String motherIdCard;
	private String fatherName;
	private Integer fatherAge;
	private String fatherNationality;
	private String fatherNation;
	private String fatherIdCard;
	private String borthAddressCategory;
	private String borthOrganization;
	private Timestamp issuingDate;
	private String issuingOrganization;
	private Integer isEffectived;
	private String inputPersonId;
	private Timestamp inputDate;
	private String otherBorthAddressCategory;
	private String widWife;
	private String province;
	private String city;
	private String county;
	private String township;
	private String familyAddress;
	private String districtNum;
	private String fileNo;
	private String linkmanTel;
	private Integer isPrint;
	private Integer isSupply;
	
	private String motherAddress;
	private String motherCardType;
	private String motherCardTypeOther;
	private String fatherAddress;
	private String fatherCardType;
	private String fatherCardTypeOther;
	private String issuingUsers;
	private Integer isChanged;
	private String saveType;
	// Constructors

	/** default constructor */
	public BirthCertificate() {
	}

	/** minimal constructor */
	public BirthCertificate(String certifiId) {
		this.certifiId = certifiId;
	}

	/** full constructor */
	public BirthCertificate(String certifiId, String name, String sex,
			Timestamp birthday, String borthWeekly, String healthStatus,
			Double weight, Double height, String motherName, Integer motherAge,
			String motherNationality, String motherNation, String motherIdCard,
			String fatherName, Integer fatherAge, String fatherNationality,
			String fatherNation, String fatherIdCard,
			String borthAddressCategory, String borthOrganization,
			Timestamp issuingDate, String issuingOrganization,
			Integer isEffectived, String inputPersonId, Timestamp inputDate,
			String otherBorthAddressCategory, String widWife, String province,
			String city, String county, String township, String familyAddress,
			String districtNum, String fileNo, String linkmanTel,
			Integer isPrint, Integer isSupply, String motherAddress,
			String motherCardType, String motherCardTypeOther,
			String fatherAddress, String fatherCardType,
			String fatherCardTypeOther,String issuingUsers,Integer isChanged,
			String saveType) {
		super();
		this.certifiId = certifiId;
		this.name = name;
		this.sex = sex;
		this.birthday = birthday;
		this.borthWeekly = borthWeekly;
		this.healthStatus = healthStatus;
		this.weight = weight;
		this.height = height;
		this.motherName = motherName;
		this.motherAge = motherAge;
		this.motherNationality = motherNationality;
		this.motherNation = motherNation;
		this.motherIdCard = motherIdCard;
		this.fatherName = fatherName;
		this.fatherAge = fatherAge;
		this.fatherNationality = fatherNationality;
		this.fatherNation = fatherNation;
		this.fatherIdCard = fatherIdCard;
		this.borthAddressCategory = borthAddressCategory;
		this.borthOrganization = borthOrganization;
		this.issuingDate = issuingDate;
		this.issuingOrganization = issuingOrganization;
		this.isEffectived = isEffectived;
		this.inputPersonId = inputPersonId;
		this.inputDate = inputDate;
		this.otherBorthAddressCategory = otherBorthAddressCategory;
		this.widWife = widWife;
		this.province = province;
		this.city = city;
		this.county = county;
		this.township = township;
		this.familyAddress = familyAddress;
		this.districtNum = districtNum;
		this.fileNo = fileNo;
		this.linkmanTel = linkmanTel;
		this.isPrint = isPrint;
		this.isSupply = isSupply;
		this.motherAddress = motherAddress;
		this.motherCardType = motherCardType;
		this.motherCardTypeOther = motherCardTypeOther;
		this.fatherAddress = fatherAddress;
		this.fatherCardType = fatherCardType;
		this.fatherCardTypeOther = fatherCardTypeOther;
		this.issuingUsers = issuingUsers;
		this.isChanged = isChanged;
		this.saveType = saveType;
	}

	// Property accessors
	public String getSaveType() {
		return saveType;
	}
	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}
	public String getCertifiId() {
		return this.certifiId;
	}

	

	public Integer getIsChanged() {
		return isChanged;
	}

	public void setIsChanged(Integer isChanged) {
		this.isChanged = isChanged;
	}

	public void setCertifiId(String certifiId) {
		this.certifiId = certifiId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Timestamp getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public String getBorthWeekly() {
		return this.borthWeekly;
	}

	public void setBorthWeekly(String borthWeekly) {
		this.borthWeekly = borthWeekly;
	}

	public String getHealthStatus() {
		return this.healthStatus;
	}

	public void setHealthStatus(String healthStatus) {
		this.healthStatus = healthStatus;
	}

	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getHeight() {
		return this.height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public String getMotherName() {
		return this.motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public Integer getMotherAge() {
		return this.motherAge;
	}

	public void setMotherAge(Integer motherAge) {
		this.motherAge = motherAge;
	}

	public String getMotherNationality() {
		return this.motherNationality;
	}

	public void setMotherNationality(String motherNationality) {
		this.motherNationality = motherNationality;
	}

	public String getMotherNation() {
		return this.motherNation;
	}

	public void setMotherNation(String motherNation) {
		this.motherNation = motherNation;
	}

	public String getMotherIdCard() {
		return this.motherIdCard;
	}

	public void setMotherIdCard(String motherIdCard) {
		this.motherIdCard = motherIdCard;
	}

	public String getFatherName() {
		return this.fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public Integer getFatherAge() {
		return this.fatherAge;
	}

	public void setFatherAge(Integer fatherAge) {
		this.fatherAge = fatherAge;
	}

	public String getFatherNationality() {
		return this.fatherNationality;
	}

	public void setFatherNationality(String fatherNationality) {
		this.fatherNationality = fatherNationality;
	}

	public String getFatherNation() {
		return this.fatherNation;
	}

	public void setFatherNation(String fatherNation) {
		this.fatherNation = fatherNation;
	}

	public String getFatherIdCard() {
		return this.fatherIdCard;
	}

	public void setFatherIdCard(String fatherIdCard) {
		this.fatherIdCard = fatherIdCard;
	}

	public String getBorthAddressCategory() {
		return this.borthAddressCategory;
	}

	public void setBorthAddressCategory(String borthAddressCategory) {
		this.borthAddressCategory = borthAddressCategory;
	}

	public String getBorthOrganization() {
		return this.borthOrganization;
	}

	public void setBorthOrganization(String borthOrganization) {
		this.borthOrganization = borthOrganization;
	}

	public Timestamp getIssuingDate() {
		return this.issuingDate;
	}

	public void setIssuingDate(Timestamp issuingDate) {
		this.issuingDate = issuingDate;
	}

	public String getIssuingOrganization() {
		return this.issuingOrganization;
	}

	public void setIssuingOrganization(String issuingOrganization) {
		this.issuingOrganization = issuingOrganization;
	}

	public Integer getIsEffectived() {
		return this.isEffectived;
	}

	public void setIsEffectived(Integer isEffectived) {
		this.isEffectived = isEffectived;
	}

	public String getInputPersonId() {
		return this.inputPersonId;
	}

	public void setInputPersonId(String inputPersonId) {
		this.inputPersonId = inputPersonId;
	}

	public Timestamp getInputDate() {
		return this.inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}

	public String getOtherBorthAddressCategory() {
		return this.otherBorthAddressCategory;
	}

	public void setOtherBorthAddressCategory(String otherBorthAddressCategory) {
		this.otherBorthAddressCategory = otherBorthAddressCategory;
	}

	public String getWidWife() {
		return this.widWife;
	}

	public void setWidWife(String widWife) {
		this.widWife = widWife;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getTownship() {
		return this.township;
	}

	public void setTownship(String township) {
		this.township = township;
	}

	public String getFamilyAddress() {
		return this.familyAddress;
	}

	public void setFamilyAddress(String familyAddress) {
		this.familyAddress = familyAddress;
	}

	public String getDistrictNum() {
		return this.districtNum;
	}

	public void setDistrictNum(String districtNum) {
		this.districtNum = districtNum;
	}

	public String getFileNo() {
		return this.fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public String getLinkmanTel() {
		return this.linkmanTel;
	}

	public void setLinkmanTel(String linkmanTel) {
		this.linkmanTel = linkmanTel;
	}

	public Integer getIsPrint() {
		return this.isPrint;
	}

	public void setIsPrint(Integer isPrint) {
		this.isPrint = isPrint;
	}

	public Integer getIsSupply() {
		return this.isSupply;
	}

	public void setIsSupply(Integer isSupply) {
		this.isSupply = isSupply;
	}

	public String getMotherAddress() {
		return motherAddress;
	}

	public void setMotherAddress(String motherAddress) {
		this.motherAddress = motherAddress;
	}

	public String getMotherCardType() {
		return motherCardType;
	}

	public void setMotherCardType(String motherCardType) {
		this.motherCardType = motherCardType;
	}

	public String getMotherCardTypeOther() {
		return motherCardTypeOther;
	}

	public void setMotherCardTypeOther(String motherCardTypeOther) {
		this.motherCardTypeOther = motherCardTypeOther;
	}

	public String getFatherAddress() {
		return fatherAddress;
	}

	public void setFatherAddress(String fatherAddress) {
		this.fatherAddress = fatherAddress;
	}

	public String getFatherCardType() {
		return fatherCardType;
	}

	public void setFatherCardType(String fatherCardType) {
		this.fatherCardType = fatherCardType;
	}

	public String getFatherCardTypeOther() {
		return fatherCardTypeOther;
	}

	public void setFatherCardTypeOther(String fatherCardTypeOther) {
		this.fatherCardTypeOther = fatherCardTypeOther;
	}

	public String getIssuingUsers() {
		return issuingUsers;
	}

	public void setIssuingUsers(String issuingUsers) {
		this.issuingUsers = issuingUsers;
	}

}