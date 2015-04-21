package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * TwomanLedger entity. @author MyEclipse Persistence Tools
 */

public class TwomanLedger implements java.io.Serializable {

	// Fields

	private String id;
	private String disctrictNumber;
	private Integer years;
	private String fileNo;
	private String buildUnit;
	private String name;
	private String husbandName;
	private String currentAddress;
	private String residenceAddress;
	private String farmStatus;
	private String occupation;
	private Integer age;
	private Integer gravidity;
	private Integer parity;
	private Timestamp lastMenses;
	private Timestamp edc;
	private Timestamp buildDate;
	private String weeks;
	private Timestamp visitDate;
	private String beforeVisitDate;
	private Timestamp hivdetectDate;
	private Timestamp syphilisDetectDate;
	private Timestamp hepatitisBdetectDate;
	private Integer childbirthYear;
	private Integer childbirthMonth;
	private Integer childbirthDay;
	private String sex;
	private String borthAddressCategory;
	private String childbirthWay;
	private String deliverWay;
	private String afterVisitDate;
	private String isSystemManager;
	private String tel;

	// Constructors

	/** default constructor */
	public TwomanLedger() {
	}

	/** minimal constructor */
	public TwomanLedger(String fileNo) {
		this.fileNo = fileNo;
	}

	/** full constructor */
	public TwomanLedger(String disctrictNumber, Integer years, String fileNo,
			String buildUnit, String name, String husbandName,
			String currentAddress, String residenceAddress, String farmStatus,
			String occupation, Integer age, Integer gravidity, Integer parity,
			Timestamp lastMenses, Timestamp edc, Timestamp buildDate,
			String weeks, Timestamp visitDate, String beforeVisitDate,
			Timestamp hivdetectDate, Timestamp syphilisDetectDate,
			Timestamp hepatitisBdetectDate, Integer childbirthYear,
			Integer childbirthMonth, Integer childbirthDay, String sex,
			String borthAddressCategory, String childbirthWay,
			String deliverWay, String afterVisitDate, String isSystemManager,
			String tel) {
		this.disctrictNumber = disctrictNumber;
		this.years = years;
		this.fileNo = fileNo;
		this.buildUnit = buildUnit;
		this.name = name;
		this.husbandName = husbandName;
		this.currentAddress = currentAddress;
		this.residenceAddress = residenceAddress;
		this.farmStatus = farmStatus;
		this.occupation = occupation;
		this.age = age;
		this.gravidity = gravidity;
		this.parity = parity;
		this.lastMenses = lastMenses;
		this.edc = edc;
		this.buildDate = buildDate;
		this.weeks = weeks;
		this.visitDate = visitDate;
		this.beforeVisitDate = beforeVisitDate;
		this.hivdetectDate = hivdetectDate;
		this.syphilisDetectDate = syphilisDetectDate;
		this.hepatitisBdetectDate = hepatitisBdetectDate;
		this.childbirthYear = childbirthYear;
		this.childbirthMonth = childbirthMonth;
		this.childbirthDay = childbirthDay;
		this.sex = sex;
		this.borthAddressCategory = borthAddressCategory;
		this.childbirthWay = childbirthWay;
		this.deliverWay = deliverWay;
		this.afterVisitDate = afterVisitDate;
		this.isSystemManager = isSystemManager;
		this.tel = tel;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisctrictNumber() {
		return this.disctrictNumber;
	}

	public void setDisctrictNumber(String disctrictNumber) {
		this.disctrictNumber = disctrictNumber;
	}

	public Integer getYears() {
		return this.years;
	}

	public void setYears(Integer years) {
		this.years = years;
	}

	public String getFileNo() {
		return this.fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public String getBuildUnit() {
		return this.buildUnit;
	}

	public void setBuildUnit(String buildUnit) {
		this.buildUnit = buildUnit;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHusbandName() {
		return this.husbandName;
	}

	public void setHusbandName(String husbandName) {
		this.husbandName = husbandName;
	}

	public String getCurrentAddress() {
		return this.currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public String getResidenceAddress() {
		return this.residenceAddress;
	}

	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	public String getFarmStatus() {
		return this.farmStatus;
	}

	public void setFarmStatus(String farmStatus) {
		this.farmStatus = farmStatus;
	}

	public String getOccupation() {
		return this.occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
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

	public Timestamp getLastMenses() {
		return this.lastMenses;
	}

	public void setLastMenses(Timestamp lastMenses) {
		this.lastMenses = lastMenses;
	}

	public Timestamp getEdc() {
		return this.edc;
	}

	public void setEdc(Timestamp edc) {
		this.edc = edc;
	}


	public Timestamp getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(Timestamp buildDate) {
		this.buildDate = buildDate;
	}

	public String getWeeks() {
		return this.weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

	public Timestamp getVisitDate() {
		return this.visitDate;
	}

	public void setVisitDate(Timestamp visitDate) {
		this.visitDate = visitDate;
	}

	public String getBeforeVisitDate() {
		return this.beforeVisitDate;
	}

	public void setBeforeVisitDate(String beforeVisitDate) {
		this.beforeVisitDate = beforeVisitDate;
	}

	public Timestamp getHivdetectDate() {
		return this.hivdetectDate;
	}

	public void setHivdetectDate(Timestamp hivdetectDate) {
		this.hivdetectDate = hivdetectDate;
	}

	public Timestamp getSyphilisDetectDate() {
		return this.syphilisDetectDate;
	}

	public void setSyphilisDetectDate(Timestamp syphilisDetectDate) {
		this.syphilisDetectDate = syphilisDetectDate;
	}

	public Timestamp getHepatitisBdetectDate() {
		return this.hepatitisBdetectDate;
	}

	public void setHepatitisBdetectDate(Timestamp hepatitisBdetectDate) {
		this.hepatitisBdetectDate = hepatitisBdetectDate;
	}

	public Integer getChildbirthYear() {
		return this.childbirthYear;
	}

	public void setChildbirthYear(Integer childbirthYear) {
		this.childbirthYear = childbirthYear;
	}

	public Integer getChildbirthMonth() {
		return this.childbirthMonth;
	}

	public void setChildbirthMonth(Integer childbirthMonth) {
		this.childbirthMonth = childbirthMonth;
	}

	public Integer getChildbirthDay() {
		return this.childbirthDay;
	}

	public void setChildbirthDay(Integer childbirthDay) {
		this.childbirthDay = childbirthDay;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBorthAddressCategory() {
		return this.borthAddressCategory;
	}

	public void setBorthAddressCategory(String borthAddressCategory) {
		this.borthAddressCategory = borthAddressCategory;
	}

	public String getChildbirthWay() {
		return this.childbirthWay;
	}

	public void setChildbirthWay(String childbirthWay) {
		this.childbirthWay = childbirthWay;
	}

	public String getDeliverWay() {
		return this.deliverWay;
	}

	public void setDeliverWay(String deliverWay) {
		this.deliverWay = deliverWay;
	}

	public String getAfterVisitDate() {
		return this.afterVisitDate;
	}

	public void setAfterVisitDate(String afterVisitDate) {
		this.afterVisitDate = afterVisitDate;
	}

	public String getIsSystemManager() {
		return this.isSystemManager;
	}

	public void setIsSystemManager(String isSystemManager) {
		this.isSystemManager = isSystemManager;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}