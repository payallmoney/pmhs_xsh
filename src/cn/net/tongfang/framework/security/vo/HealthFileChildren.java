package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * HealthFileChildren entity. @author MyEclipse Persistence Tools
 */

public class HealthFileChildren implements java.io.Serializable {

	// Fields

	private String id;
	private String fileNo;
	private String name;
	private String sex;
	private String allergiesHistory;
	private String fatherName;
	private String motherName;
	private Timestamp buildDate;
	private String buildUnit;
	private String residenceProvence;
	private String residenceCity;
	private String residenceCounty;
	private String residenceTownship;
	private String residenceVillage;
	private String residenceGroup;
	private String addressProvence;
	private String addressCity;
	private String addressCounty;
	private String addressTownship;
	private String addressVillage;
	private String addressGroup;
	private String tel;
	private Timestamp birthday;
	private Double weight;
	private Double height;
	private String apgarOneMinuts;
	private String apgarFiveMinuts;
	private String abo;
	private String rh;
	private String weekly;
	private Integer gravidity;
	private Integer parity;
	private String numberOfBirths;
	private String bormWays;
	private String birthOrgName;
	private Integer fatherAge;
	private String fatherNation;
	private String fatherEducational;
	private String fatherOccupation;
	private Integer motherAge;
	private String motherNation;
	private String motherEducational;
	private String motherOccupation;
	private String montherExceptions;
	private String montherExceptionOhter;
	private String childrenExceptions;
	private String childrenException1;
	private String childrenExceptionOhter;
	private String birthDefect;
	private String birthDefectOther;
	private String childIllScreening;
	private String childIllScreeningOther;
	private String childHereditary;
	private String childHereditaryOther;
	private String inputPersonId;
	private Timestamp inputDate;
	private String motherTel;
	private String fatherTel;
	// Constructors

	/** default constructor */
	public HealthFileChildren() {
	}

	/** minimal constructor */
	public HealthFileChildren(String fileNo, String name, String sex, String rh) {
		this.fileNo = fileNo;
		this.name = name;
		this.sex = sex;
		this.rh = rh;
	}

	/** full constructor */
	public HealthFileChildren(String fileNo, String name, String sex,
			String allergiesHistory, String fatherName, String motherName,
			Timestamp buildDate, String buildUnit, String residenceProvence,
			String residenceCity, String residenceCounty,
			String residenceTownship, String residenceVillage,
			String residenceGroup, String addressProvence, String addressCity,
			String addressCounty, String addressTownship,
			String addressVillage, String addressGroup, String tel,
			Timestamp birthday, Double weight, Double height,
			String apgarOneMinuts, String apgarFiveMinuts, String abo,
			String rh, String weekly, Integer gravidity, Integer parity,
			String numberOfBirths, String bormWays, String birthOrgName,
			Integer fatherAge, String fatherNation, String fatherEducational,
			String fatherOccupation, Integer motherAge, String motherNation,
			String motherEducational, String motherOccupation,
			String montherExceptions, String montherExceptionOhter,
			String childrenExceptions, String childrenException1,
			String childrenExceptionOhter, String birthDefect,
			String birthDefectOther, String childIllScreening,
			String childIllScreeningOther, String childHereditary,
			String childHereditaryOther, String inputPersonId,
			Timestamp inputDate,String fatherTel,String motherTel) {
		this.fileNo = fileNo;
		this.name = name;
		this.sex = sex;
		this.allergiesHistory = allergiesHistory;
		this.fatherName = fatherName;
		this.motherName = motherName;
		this.buildDate = buildDate;
		this.buildUnit = buildUnit;
		this.residenceProvence = residenceProvence;
		this.residenceCity = residenceCity;
		this.residenceCounty = residenceCounty;
		this.residenceTownship = residenceTownship;
		this.residenceVillage = residenceVillage;
		this.residenceGroup = residenceGroup;
		this.addressProvence = addressProvence;
		this.addressCity = addressCity;
		this.addressCounty = addressCounty;
		this.addressTownship = addressTownship;
		this.addressVillage = addressVillage;
		this.addressGroup = addressGroup;
		this.tel = tel;
		this.birthday = birthday;
		this.weight = weight;
		this.height = height;
		this.apgarOneMinuts = apgarOneMinuts;
		this.apgarFiveMinuts = apgarFiveMinuts;
		this.abo = abo;
		this.rh = rh;
		this.weekly = weekly;
		this.gravidity = gravidity;
		this.parity = parity;
		this.numberOfBirths = numberOfBirths;
		this.bormWays = bormWays;
		this.birthOrgName = birthOrgName;
		this.fatherAge = fatherAge;
		this.fatherNation = fatherNation;
		this.fatherEducational = fatherEducational;
		this.fatherOccupation = fatherOccupation;
		this.motherAge = motherAge;
		this.motherNation = motherNation;
		this.motherEducational = motherEducational;
		this.motherOccupation = motherOccupation;
		this.montherExceptions = montherExceptions;
		this.montherExceptionOhter = montherExceptionOhter;
		this.childrenExceptions = childrenExceptions;
		this.childrenException1 = childrenException1;
		this.childrenExceptionOhter = childrenExceptionOhter;
		this.birthDefect = birthDefect;
		this.birthDefectOther = birthDefectOther;
		this.childIllScreening = childIllScreening;
		this.childIllScreeningOther = childIllScreeningOther;
		this.childHereditary = childHereditary;
		this.childHereditaryOther = childHereditaryOther;
		this.inputPersonId = inputPersonId;
		this.inputDate = inputDate;
		this.motherTel = motherTel;
		this.fatherTel = fatherTel;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileNo() {
		return this.fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
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

	public String getAllergiesHistory() {
		return this.allergiesHistory;
	}

	public void setAllergiesHistory(String allergiesHistory) {
		this.allergiesHistory = allergiesHistory;
	}

	public String getFatherName() {
		return this.fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return this.motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public Timestamp getBuildDate() {
		return this.buildDate;
	}

	public void setBuildDate(Timestamp buildDate) {
		this.buildDate = buildDate;
	}

	public String getBuildUnit() {
		return this.buildUnit;
	}

	public void setBuildUnit(String buildUnit) {
		this.buildUnit = buildUnit;
	}

	public String getResidenceProvence() {
		return this.residenceProvence;
	}

	public void setResidenceProvence(String residenceProvence) {
		this.residenceProvence = residenceProvence;
	}

	public String getResidenceCity() {
		return this.residenceCity;
	}

	public void setResidenceCity(String residenceCity) {
		this.residenceCity = residenceCity;
	}

	public String getResidenceCounty() {
		return this.residenceCounty;
	}

	public void setResidenceCounty(String residenceCounty) {
		this.residenceCounty = residenceCounty;
	}

	public String getResidenceTownship() {
		return this.residenceTownship;
	}

	public void setResidenceTownship(String residenceTownship) {
		this.residenceTownship = residenceTownship;
	}

	public String getResidenceVillage() {
		return this.residenceVillage;
	}

	public void setResidenceVillage(String residenceVillage) {
		this.residenceVillage = residenceVillage;
	}

	public String getResidenceGroup() {
		return this.residenceGroup;
	}

	public void setResidenceGroup(String residenceGroup) {
		this.residenceGroup = residenceGroup;
	}

	public String getAddressProvence() {
		return this.addressProvence;
	}

	public void setAddressProvence(String addressProvence) {
		this.addressProvence = addressProvence;
	}

	public String getAddressCity() {
		return this.addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressCounty() {
		return this.addressCounty;
	}

	public void setAddressCounty(String addressCounty) {
		this.addressCounty = addressCounty;
	}

	public String getAddressTownship() {
		return this.addressTownship;
	}

	public void setAddressTownship(String addressTownship) {
		this.addressTownship = addressTownship;
	}

	public String getAddressVillage() {
		return this.addressVillage;
	}

	public void setAddressVillage(String addressVillage) {
		this.addressVillage = addressVillage;
	}

	public String getAddressGroup() {
		return this.addressGroup;
	}

	public void setAddressGroup(String addressGroup) {
		this.addressGroup = addressGroup;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Timestamp getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
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

	public String getApgarOneMinuts() {
		return this.apgarOneMinuts;
	}

	public void setApgarOneMinuts(String apgarOneMinuts) {
		this.apgarOneMinuts = apgarOneMinuts;
	}

	public String getApgarFiveMinuts() {
		return this.apgarFiveMinuts;
	}

	public void setApgarFiveMinuts(String apgarFiveMinuts) {
		this.apgarFiveMinuts = apgarFiveMinuts;
	}

	public String getAbo() {
		return this.abo;
	}

	public void setAbo(String abo) {
		this.abo = abo;
	}

	public String getRh() {
		return this.rh;
	}

	public void setRh(String rh) {
		this.rh = rh;
	}

	public String getWeekly() {
		return this.weekly;
	}

	public void setWeekly(String weekly) {
		this.weekly = weekly;
	}

	public Integer getGravidity() {
		return this.gravidity;
	}

	public void setGravidity(Integer gravidity) {
		this.gravidity = gravidity;
	}

	public Integer getParity() {
		return this.parity;
	}

	public void setParity(Integer parity) {
		this.parity = parity;
	}

	public String getNumberOfBirths() {
		return this.numberOfBirths;
	}

	public void setNumberOfBirths(String numberOfBirths) {
		this.numberOfBirths = numberOfBirths;
	}

	public String getBormWays() {
		return this.bormWays;
	}

	public void setBormWays(String bormWays) {
		this.bormWays = bormWays;
	}

	public String getBirthOrgName() {
		return this.birthOrgName;
	}

	public void setBirthOrgName(String birthOrgName) {
		this.birthOrgName = birthOrgName;
	}

	public Integer getFatherAge() {
		return this.fatherAge;
	}

	public void setFatherAge(Integer fatherAge) {
		this.fatherAge = fatherAge;
	}

	public String getFatherNation() {
		return this.fatherNation;
	}

	public void setFatherNation(String fatherNation) {
		this.fatherNation = fatherNation;
	}

	public String getFatherEducational() {
		return this.fatherEducational;
	}

	public void setFatherEducational(String fatherEducational) {
		this.fatherEducational = fatherEducational;
	}

	public String getFatherOccupation() {
		return this.fatherOccupation;
	}

	public void setFatherOccupation(String fatherOccupation) {
		this.fatherOccupation = fatherOccupation;
	}

	public Integer getMotherAge() {
		return this.motherAge;
	}

	public void setMotherAge(Integer motherAge) {
		this.motherAge = motherAge;
	}

	public String getMotherNation() {
		return this.motherNation;
	}

	public void setMotherNation(String motherNation) {
		this.motherNation = motherNation;
	}

	public String getMotherEducational() {
		return this.motherEducational;
	}

	public void setMotherEducational(String motherEducational) {
		this.motherEducational = motherEducational;
	}

	public String getMotherOccupation() {
		return this.motherOccupation;
	}

	public void setMotherOccupation(String motherOccupation) {
		this.motherOccupation = motherOccupation;
	}

	public String getMontherExceptions() {
		return this.montherExceptions;
	}

	public void setMontherExceptions(String montherExceptions) {
		this.montherExceptions = montherExceptions;
	}

	public String getMontherExceptionOhter() {
		return this.montherExceptionOhter;
	}

	public void setMontherExceptionOhter(String montherExceptionOhter) {
		this.montherExceptionOhter = montherExceptionOhter;
	}

	public String getChildrenExceptions() {
		return this.childrenExceptions;
	}

	public void setChildrenExceptions(String childrenExceptions) {
		this.childrenExceptions = childrenExceptions;
	}

	public String getChildrenException1() {
		return this.childrenException1;
	}

	public void setChildrenException1(String childrenException1) {
		this.childrenException1 = childrenException1;
	}

	public String getChildrenExceptionOhter() {
		return this.childrenExceptionOhter;
	}

	public void setChildrenExceptionOhter(String childrenExceptionOhter) {
		this.childrenExceptionOhter = childrenExceptionOhter;
	}

	public String getBirthDefect() {
		return this.birthDefect;
	}

	public void setBirthDefect(String birthDefect) {
		this.birthDefect = birthDefect;
	}

	public String getBirthDefectOther() {
		return this.birthDefectOther;
	}

	public void setBirthDefectOther(String birthDefectOther) {
		this.birthDefectOther = birthDefectOther;
	}

	public String getChildIllScreening() {
		return this.childIllScreening;
	}

	public void setChildIllScreening(String childIllScreening) {
		this.childIllScreening = childIllScreening;
	}

	public String getChildIllScreeningOther() {
		return this.childIllScreeningOther;
	}

	public void setChildIllScreeningOther(String childIllScreeningOther) {
		this.childIllScreeningOther = childIllScreeningOther;
	}

	public String getChildHereditary() {
		return this.childHereditary;
	}

	public void setChildHereditary(String childHereditary) {
		this.childHereditary = childHereditary;
	}

	public String getChildHereditaryOther() {
		return this.childHereditaryOther;
	}

	public void setChildHereditaryOther(String childHereditaryOther) {
		this.childHereditaryOther = childHereditaryOther;
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

	public String getMotherTel() {
		return motherTel;
	}

	public void setMotherTel(String motherTel) {
		this.motherTel = motherTel;
	}

	public String getFatherTel() {
		return fatherTel;
	}

	public void setFatherTel(String fatherTel) {
		this.fatherTel = fatherTel;
	}

}