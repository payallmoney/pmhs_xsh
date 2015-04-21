package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * TchildrenLedger entity. @author MyEclipse Persistence Tools
 */

public class TchildrenLedger implements java.io.Serializable {

	// Fields

	private String id;
	private String disctrictNumber;
	private Integer years;
	private String fileNo;
	private String buildUnits;
	private String name;
	private String fatherName;
	private String motherName;
	private String currentAddress;
	private String residenceAddress;
	private String farmStatus;
	private Timestamp buildDate;
	private Timestamp birthday;
	private String sex;
	private Double weight;
	private Double height;
	private String feedMethod01;
	private String feedMethod02;
	private String feedMethod03;
	private String highRiskRemark;
	private Integer oneMonth;
	private Integer twoMonth;
	private Integer threeMonth;
	private Integer fourMonth;
	private Integer fiveMonth;
	private Integer sixMonth;
	private Integer eightMonth;
	private Integer tenMonth;
	private Integer twelveMonth;
	private Integer fifteenMonth;
	private Integer eighteenMonth;
	private Integer twentyOneMonth;
	private Integer twentyFourMonth;
	private Integer thirtyMonth;
	private Integer threeYear;
	private Integer fourYear;
	private Integer fiveYear;
	private Integer sixYear;

	// Constructors

	/** default constructor */
	public TchildrenLedger() {
	}

	/** minimal constructor */
	public TchildrenLedger(String fileNo) {
		this.fileNo = fileNo;
	}

	/** full constructor */
	public TchildrenLedger(String disctrictNumber, Integer years,
			String fileNo, String buildUnits, String name, String fatherName,
			String motherName, String currentAddress, String residenceAddress,
			String farmStatus, Timestamp buildDate, Timestamp birthday,
			String sex, Double weight, Double height, String feedMethod01,
			String feedMethod02, String feedMethod03, String highRiskRemark,
			Integer oneMonth, Integer twoMonth, Integer threeMonth,
			Integer fourMonth, Integer fiveMonth, Integer sixMonth,
			Integer eightMonth, Integer tenMonth, Integer twelveMonth,
			Integer fifteenMonth, Integer eighteenMonth,
			Integer twentyOneMonth, Integer twentyFourMonth,
			Integer thirtyMonth, Integer threeYear, Integer fourYear,
			Integer fiveYear, Integer sixYear) {
		this.disctrictNumber = disctrictNumber;
		this.years = years;
		this.fileNo = fileNo;
		this.buildUnits = buildUnits;
		this.name = name;
		this.fatherName = fatherName;
		this.motherName = motherName;
		this.currentAddress = currentAddress;
		this.residenceAddress = residenceAddress;
		this.farmStatus = farmStatus;
		this.buildDate = buildDate;
		this.birthday = birthday;
		this.sex = sex;
		this.weight = weight;
		this.height = height;
		this.feedMethod01 = feedMethod01;
		this.feedMethod02 = feedMethod02;
		this.feedMethod03 = feedMethod03;
		this.highRiskRemark = highRiskRemark;
		this.oneMonth = oneMonth;
		this.twoMonth = twoMonth;
		this.threeMonth = threeMonth;
		this.fourMonth = fourMonth;
		this.fiveMonth = fiveMonth;
		this.sixMonth = sixMonth;
		this.eightMonth = eightMonth;
		this.tenMonth = tenMonth;
		this.twelveMonth = twelveMonth;
		this.fifteenMonth = fifteenMonth;
		this.eighteenMonth = eighteenMonth;
		this.twentyOneMonth = twentyOneMonth;
		this.twentyFourMonth = twentyFourMonth;
		this.thirtyMonth = thirtyMonth;
		this.threeYear = threeYear;
		this.fourYear = fourYear;
		this.fiveYear = fiveYear;
		this.sixYear = sixYear;
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

	public String getBuildUnits() {
		return this.buildUnits;
	}

	public void setBuildUnits(String buildUnits) {
		this.buildUnits = buildUnits;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Timestamp getBuildDate() {
		return this.buildDate;
	}

	public void setBuildDate(Timestamp buildDate) {
		this.buildDate = buildDate;
	}

	public Timestamp getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public String getFeedMethod01() {
		return this.feedMethod01;
	}

	public void setFeedMethod01(String feedMethod01) {
		this.feedMethod01 = feedMethod01;
	}

	public String getFeedMethod02() {
		return this.feedMethod02;
	}

	public void setFeedMethod02(String feedMethod02) {
		this.feedMethod02 = feedMethod02;
	}

	public String getFeedMethod03() {
		return this.feedMethod03;
	}

	public void setFeedMethod03(String feedMethod03) {
		this.feedMethod03 = feedMethod03;
	}

	public String getHighRiskRemark() {
		return this.highRiskRemark;
	}

	public void setHighRiskRemark(String highRiskRemark) {
		this.highRiskRemark = highRiskRemark;
	}

	public Integer getOneMonth() {
		return this.oneMonth;
	}

	public void setOneMonth(Integer oneMonth) {
		this.oneMonth = oneMonth;
	}

	public Integer getTwoMonth() {
		return this.twoMonth;
	}

	public void setTwoMonth(Integer twoMonth) {
		this.twoMonth = twoMonth;
	}

	public Integer getThreeMonth() {
		return this.threeMonth;
	}

	public void setThreeMonth(Integer threeMonth) {
		this.threeMonth = threeMonth;
	}

	public Integer getFourMonth() {
		return this.fourMonth;
	}

	public void setFourMonth(Integer fourMonth) {
		this.fourMonth = fourMonth;
	}

	public Integer getFiveMonth() {
		return this.fiveMonth;
	}

	public void setFiveMonth(Integer fiveMonth) {
		this.fiveMonth = fiveMonth;
	}

	public Integer getSixMonth() {
		return this.sixMonth;
	}

	public void setSixMonth(Integer sixMonth) {
		this.sixMonth = sixMonth;
	}

	public Integer getEightMonth() {
		return this.eightMonth;
	}

	public void setEightMonth(Integer eightMonth) {
		this.eightMonth = eightMonth;
	}

	public Integer getTenMonth() {
		return this.tenMonth;
	}

	public void setTenMonth(Integer tenMonth) {
		this.tenMonth = tenMonth;
	}

	public Integer getTwelveMonth() {
		return this.twelveMonth;
	}

	public void setTwelveMonth(Integer twelveMonth) {
		this.twelveMonth = twelveMonth;
	}

	public Integer getFifteenMonth() {
		return this.fifteenMonth;
	}

	public void setFifteenMonth(Integer fifteenMonth) {
		this.fifteenMonth = fifteenMonth;
	}

	public Integer getEighteenMonth() {
		return this.eighteenMonth;
	}

	public void setEighteenMonth(Integer eighteenMonth) {
		this.eighteenMonth = eighteenMonth;
	}

	public Integer getTwentyOneMonth() {
		return this.twentyOneMonth;
	}

	public void setTwentyOneMonth(Integer twentyOneMonth) {
		this.twentyOneMonth = twentyOneMonth;
	}

	public Integer getTwentyFourMonth() {
		return this.twentyFourMonth;
	}

	public void setTwentyFourMonth(Integer twentyFourMonth) {
		this.twentyFourMonth = twentyFourMonth;
	}

	public Integer getThirtyMonth() {
		return this.thirtyMonth;
	}

	public void setThirtyMonth(Integer thirtyMonth) {
		this.thirtyMonth = thirtyMonth;
	}

	public Integer getThreeYear() {
		return this.threeYear;
	}

	public void setThreeYear(Integer threeYear) {
		this.threeYear = threeYear;
	}

	public Integer getFourYear() {
		return this.fourYear;
	}

	public void setFourYear(Integer fourYear) {
		this.fourYear = fourYear;
	}

	public Integer getFiveYear() {
		return this.fiveYear;
	}

	public void setFiveYear(Integer fiveYear) {
		this.fiveYear = fiveYear;
	}

	public Integer getSixYear() {
		return this.sixYear;
	}

	public void setSixYear(Integer sixYear) {
		this.sixYear = sixYear;
	}

}