package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * HealthFileMaternal entity. @author MyEclipse Persistence Tools
 */

public class HealthFileMaternal implements java.io.Serializable {

	// Fields

	private String id;
	private String fileNo;
	private String name;
	private Timestamp birthday;
	private String tel;
	private String firstAidTel;
	private String addressProvence;
	private String addressCity;
	private String addressCounty;
	private String addressTownship;
	private String addressVillage;
	private String addressGroup;
	private String residenceProvence;
	private String residenceCity;
	private String residenceCounty;
	private String residenceTownship;
	private String residenceVillage;
	private String residenceGroup;
	private String highRiskCode;
	private String buildUnit;
	private Timestamp buildDate;
	private String nationality;
	private String nationalityOther;
	private String idnumber;
	private String workUnit;
	private String resideType;
	private String areaOfResidence;
	private Double distance;
	private String folk;
	private String folkOther;
	private String education;
	private String occupation;
	private String recuperateProvence;
	private String recuperateCity;
	private String recuperateCounty;
	private String recuperateTownship;
	private String recuperateVillage;
	private String recuperateGroup;
	private String relatedInfoSearch;
	private String husbandName;
	private Timestamp husbandBirthday;
	private String husbandTel;
	private String husbandEducation;
	private String husbandOccupation;
	private String husbandOccupationOther;
	private String husbandWorkUnit;
	private String inputPersonId;
	private Timestamp inputDate;
	private Integer gravidity;
	private String isClosed;
	private Timestamp closedDate;
	private String barCode;
	// Constructors

	/** default constructor */
	public HealthFileMaternal() {
	}

	/** minimal constructor */
	public HealthFileMaternal(String fileNo, String name, String idnumber) {
		this.fileNo = fileNo;
		this.name = name;
		this.idnumber = idnumber;
	}

	/** full constructor */
	public HealthFileMaternal(String fileNo, String name, Timestamp birthday,
			String tel, String firstAidTel, String addressProvence,
			String addressCity, String addressCounty, String addressTownship,
			String addressVillage, String addressGroup,
			String residenceProvence, String residenceCity,
			String residenceCounty, String residenceTownship,
			String residenceVillage, String residenceGroup,
			String highRiskCode, String buildUnit, Timestamp buildDate,
			String nationality, String idnumber, String workUnit,
			String resideType, String areaOfResidence, Double distance,
			String folk, String folkOther, String education, String occupation,
			String recuperateProvence, String recuperateCity,
			String recuperateCounty, String recuperateTownship,
			String recuperateVillage, String recuperateGroup,
			String husbandName, Timestamp husbandBirthday, String husbandTel,
			String husbandEducation, String husbandOccupation,
			String husbandOccupationOther, String husbandWorkUnit,
			String inputPersonId, Timestamp inputDate, Integer gravidity,
			String nationalityOther,String isClosed,Timestamp closedDate,
			String relatedInfoSearch,String barCode) {
		this.fileNo = fileNo;
		this.name = name;
		this.birthday = birthday;
		this.tel = tel;
		this.firstAidTel = firstAidTel;
		this.addressProvence = addressProvence;
		this.addressCity = addressCity;
		this.addressCounty = addressCounty;
		this.addressTownship = addressTownship;
		this.addressVillage = addressVillage;
		this.addressGroup = addressGroup;
		this.residenceProvence = residenceProvence;
		this.residenceCity = residenceCity;
		this.residenceCounty = residenceCounty;
		this.residenceTownship = residenceTownship;
		this.residenceVillage = residenceVillage;
		this.residenceGroup = residenceGroup;
		this.highRiskCode = highRiskCode;
		this.buildUnit = buildUnit;
		this.buildDate = buildDate;
		this.nationality = nationality;
		this.idnumber = idnumber;
		this.workUnit = workUnit;
		this.resideType = resideType;
		this.areaOfResidence = areaOfResidence;
		this.distance = distance;
		this.folk = folk;
		this.folkOther = folkOther;
		this.education = education;
		this.occupation = occupation;
		this.recuperateProvence = recuperateProvence;
		this.recuperateCity = recuperateCity;
		this.recuperateCounty = recuperateCounty;
		this.recuperateTownship = recuperateTownship;
		this.recuperateVillage = recuperateVillage;
		this.recuperateGroup = recuperateGroup;
		this.husbandName = husbandName;
		this.husbandBirthday = husbandBirthday;
		this.husbandTel = husbandTel;
		this.husbandEducation = husbandEducation;
		this.husbandOccupation = husbandOccupation;
		this.husbandOccupationOther = husbandOccupationOther;
		this.husbandWorkUnit = husbandWorkUnit;
		this.inputPersonId = inputPersonId;
		this.inputDate = inputDate;
		this.gravidity = gravidity;
		this.nationalityOther = nationalityOther;
		this.isClosed = isClosed;
		this.relatedInfoSearch = relatedInfoSearch;
		this.barCode = barCode;
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

	public Timestamp getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFirstAidTel() {
		return this.firstAidTel;
	}

	public void setFirstAidTel(String firstAidTel) {
		this.firstAidTel = firstAidTel;
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

	public String getHighRiskCode() {
		return this.highRiskCode;
	}

	public void setHighRiskCode(String highRiskCode) {
		this.highRiskCode = highRiskCode;
	}

	public String getBuildUnit() {
		return this.buildUnit;
	}

	public void setBuildUnit(String buildUnit) {
		this.buildUnit = buildUnit;
	}

	public Timestamp getBuildDate() {
		return this.buildDate;
	}

	public void setBuildDate(Timestamp buildDate) {
		this.buildDate = buildDate;
	}

	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getIdnumber() {
		return this.idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public String getWorkUnit() {
		return this.workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getResideType() {
		return this.resideType;
	}

	public void setResideType(String resideType) {
		this.resideType = resideType;
	}

	public String getAreaOfResidence() {
		return this.areaOfResidence;
	}

	public void setAreaOfResidence(String areaOfResidence) {
		this.areaOfResidence = areaOfResidence;
	}

	public Double getDistance() {
		return this.distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getFolk() {
		return this.folk;
	}

	public void setFolk(String folk) {
		this.folk = folk;
	}

	public String getFolkOther() {
		return this.folkOther;
	}

	public void setFolkOther(String folkOther) {
		this.folkOther = folkOther;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getOccupation() {
		return this.occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getRecuperateProvence() {
		return this.recuperateProvence;
	}

	public void setRecuperateProvence(String recuperateProvence) {
		this.recuperateProvence = recuperateProvence;
	}

	public String getRecuperateCity() {
		return this.recuperateCity;
	}

	public void setRecuperateCity(String recuperateCity) {
		this.recuperateCity = recuperateCity;
	}

	public String getRecuperateCounty() {
		return this.recuperateCounty;
	}

	public void setRecuperateCounty(String recuperateCounty) {
		this.recuperateCounty = recuperateCounty;
	}

	public String getRecuperateTownship() {
		return this.recuperateTownship;
	}

	public void setRecuperateTownship(String recuperateTownship) {
		this.recuperateTownship = recuperateTownship;
	}

	public String getRecuperateVillage() {
		return this.recuperateVillage;
	}

	public void setRecuperateVillage(String recuperateVillage) {
		this.recuperateVillage = recuperateVillage;
	}

	public String getRecuperateGroup() {
		return this.recuperateGroup;
	}

	public void setRecuperateGroup(String recuperateGroup) {
		this.recuperateGroup = recuperateGroup;
	}

	public String getHusbandName() {
		return this.husbandName;
	}

	public void setHusbandName(String husbandName) {
		this.husbandName = husbandName;
	}

	public Timestamp getHusbandBirthday() {
		return this.husbandBirthday;
	}

	public void setHusbandBirthday(Timestamp husbandBirthday) {
		this.husbandBirthday = husbandBirthday;
	}

	public String getHusbandTel() {
		return this.husbandTel;
	}

	public void setHusbandTel(String husbandTel) {
		this.husbandTel = husbandTel;
	}

	public String getHusbandEducation() {
		return this.husbandEducation;
	}

	public void setHusbandEducation(String husbandEducation) {
		this.husbandEducation = husbandEducation;
	}

	public String getHusbandOccupation() {
		return this.husbandOccupation;
	}

	public void setHusbandOccupation(String husbandOccupation) {
		this.husbandOccupation = husbandOccupation;
	}

	public String getHusbandOccupationOther() {
		return this.husbandOccupationOther;
	}

	public void setHusbandOccupationOther(String husbandOccupationOther) {
		this.husbandOccupationOther = husbandOccupationOther;
	}

	public String getHusbandWorkUnit() {
		return this.husbandWorkUnit;
	}

	public void setHusbandWorkUnit(String husbandWorkUnit) {
		this.husbandWorkUnit = husbandWorkUnit;
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

	public Integer getGravidity() {
		return this.gravidity;
	}

	public void setGravidity(Integer gravidity) {
		this.gravidity = gravidity;
	}

	public String getNationalityOther() {
		return nationalityOther;
	}

	public void setNationalityOther(String nationalityOther) {
		this.nationalityOther = nationalityOther;
	}

	public String getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed;
	}

	public Timestamp getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Timestamp closedDate) {
		this.closedDate = closedDate;
	}

	public String getRelatedInfoSearch() {
		return relatedInfoSearch;
	}

	public void setRelatedInfoSearch(String relatedInfoSearch) {
		this.relatedInfoSearch = relatedInfoSearch;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
}