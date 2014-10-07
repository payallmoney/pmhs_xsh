package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * VisitBeforeBorn entity. @author MyEclipse Persistence Tools
 */

public class VisitBeforeBorn implements java.io.Serializable {

	// Fields

	private String id;
	private String fileNo;
	private String inputPersonId;
	private Timestamp inputDate;
	private Integer item;
	private Timestamp visitDate;
	private Double diastolicPressure;
	private Double systolicPressure;
	private String result;
	private String resultOther;
	private String beforeBornDirectOther;
	private Boolean transfer;
	private String transReason;
	private String transUnit;
	private String visitDoctor;
	private Timestamp nextVisitDate;
	private String weeks;
	private String cc;
	private Double weight;
	private Double exam01;
	private Double exam02;
	private String exam03;
	private Double exam04;
	private String exam05;
	private String exam06;
	private String exam07;
	private String highRisk;
	private String highRiskRemark;
	private String execDistrictNum;
	private String highRiskSearch;
	private Integer gravidity;
	private String edema;
	private String diagnosisRemark;
	private String bornBirthAddressPlan;
	private Double pelvis01;
	private Double pelvis02;
	private Double pelvis03;
	private Double pelvis04;
	private String foreignId;

	// Constructors

	/** default constructor */
	public VisitBeforeBorn() {
	}

	/** minimal constructor */
	public VisitBeforeBorn(String id, String fileNo, String inputPersonId,
			Timestamp inputDate) {
		this.id = id;
		this.fileNo = fileNo;
		this.inputPersonId = inputPersonId;
		this.inputDate = inputDate;
	}

	/** full constructor */
	public VisitBeforeBorn(String id, String fileNo, String inputPersonId,
			Timestamp inputDate, Integer item, Timestamp visitDate,
			Double diastolicPressure, Double systolicPressure, String result,
			String resultOther, String beforeBornDirectOther, Boolean transfer,
			String transReason, String transUnit, String visitDoctor,
			Timestamp nextVisitDate, String weeks, String cc, Double weight,
			Double exam01, Double exam02, String exam03, Double exam04,
			String exam05, String exam06, String exam07, String highRisk,
			String highRiskRemark, String execDistrictNum,
			String highRiskSearch, Integer gravidity, String edema,
			String diagnosisRemark, String bornBirthAddressPlan,
			Double pelvis01, Double pelvis02, Double pelvis03, Double pelvis04,
			String foreignId) {
		this.id = id;
		this.fileNo = fileNo;
		this.inputPersonId = inputPersonId;
		this.inputDate = inputDate;
		this.item = item;
		this.visitDate = visitDate;
		this.diastolicPressure = diastolicPressure;
		this.systolicPressure = systolicPressure;
		this.result = result;
		this.resultOther = resultOther;
		this.beforeBornDirectOther = beforeBornDirectOther;
		this.transfer = transfer;
		this.transReason = transReason;
		this.transUnit = transUnit;
		this.visitDoctor = visitDoctor;
		this.nextVisitDate = nextVisitDate;
		this.weeks = weeks;
		this.cc = cc;
		this.weight = weight;
		this.exam01 = exam01;
		this.exam02 = exam02;
		this.exam03 = exam03;
		this.exam04 = exam04;
		this.exam05 = exam05;
		this.exam06 = exam06;
		this.exam07 = exam07;
		this.highRisk = highRisk;
		this.highRiskRemark = highRiskRemark;
		this.execDistrictNum = execDistrictNum;
		this.highRiskSearch = highRiskSearch;
		this.gravidity = gravidity;
		this.edema = edema;
		this.diagnosisRemark = diagnosisRemark;
		this.bornBirthAddressPlan = bornBirthAddressPlan;
		this.pelvis01 = pelvis01;
		this.pelvis02 = pelvis02;
		this.pelvis03 = pelvis03;
		this.pelvis04 = pelvis04;
		this.foreignId = foreignId;
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

	public Integer getItem() {
		return this.item;
	}

	public void setItem(Integer item) {
		this.item = item;
	}

	public Timestamp getVisitDate() {
		return this.visitDate;
	}

	public void setVisitDate(Timestamp visitDate) {
		this.visitDate = visitDate;
	}

	public Double getDiastolicPressure() {
		return this.diastolicPressure;
	}

	public void setDiastolicPressure(Double diastolicPressure) {
		this.diastolicPressure = diastolicPressure;
	}

	public Double getSystolicPressure() {
		return this.systolicPressure;
	}

	public void setSystolicPressure(Double systolicPressure) {
		this.systolicPressure = systolicPressure;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultOther() {
		return this.resultOther;
	}

	public void setResultOther(String resultOther) {
		this.resultOther = resultOther;
	}

	public String getBeforeBornDirectOther() {
		return this.beforeBornDirectOther;
	}

	public void setBeforeBornDirectOther(String beforeBornDirectOther) {
		this.beforeBornDirectOther = beforeBornDirectOther;
	}

	public Boolean getTransfer() {
		return this.transfer;
	}

	public void setTransfer(Boolean transfer) {
		this.transfer = transfer;
	}

	public String getTransReason() {
		return this.transReason;
	}

	public void setTransReason(String transReason) {
		this.transReason = transReason;
	}

	public String getTransUnit() {
		return this.transUnit;
	}

	public void setTransUnit(String transUnit) {
		this.transUnit = transUnit;
	}

	public String getVisitDoctor() {
		return this.visitDoctor;
	}

	public void setVisitDoctor(String visitDoctor) {
		this.visitDoctor = visitDoctor;
	}

	public Timestamp getNextVisitDate() {
		return this.nextVisitDate;
	}

	public void setNextVisitDate(Timestamp nextVisitDate) {
		this.nextVisitDate = nextVisitDate;
	}

	public String getWeeks() {
		return this.weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

	public String getCc() {
		return this.cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getExam01() {
		return this.exam01;
	}

	public void setExam01(Double exam01) {
		this.exam01 = exam01;
	}

	public Double getExam02() {
		return this.exam02;
	}

	public void setExam02(Double exam02) {
		this.exam02 = exam02;
	}

	public String getExam03() {
		return this.exam03;
	}

	public void setExam03(String exam03) {
		this.exam03 = exam03;
	}

	public Double getExam04() {
		return this.exam04;
	}

	public void setExam04(Double exam04) {
		this.exam04 = exam04;
	}

	public String getExam05() {
		return this.exam05;
	}

	public void setExam05(String exam05) {
		this.exam05 = exam05;
	}

	public String getExam06() {
		return this.exam06;
	}

	public void setExam06(String exam06) {
		this.exam06 = exam06;
	}

	public String getExam07() {
		return this.exam07;
	}

	public void setExam07(String exam07) {
		this.exam07 = exam07;
	}

	public String getHighRisk() {
		return this.highRisk;
	}

	public void setHighRisk(String highRisk) {
		this.highRisk = highRisk;
	}

	public String getHighRiskRemark() {
		return this.highRiskRemark;
	}

	public void setHighRiskRemark(String highRiskRemark) {
		this.highRiskRemark = highRiskRemark;
	}

	public String getExecDistrictNum() {
		return this.execDistrictNum;
	}

	public void setExecDistrictNum(String execDistrictNum) {
		this.execDistrictNum = execDistrictNum;
	}

	public String getHighRiskSearch() {
		return this.highRiskSearch;
	}

	public void setHighRiskSearch(String highRiskSearch) {
		this.highRiskSearch = highRiskSearch;
	}

	public Integer getGravidity() {
		return this.gravidity;
	}

	public void setGravidity(Integer gravidity) {
		this.gravidity = gravidity;
	}

	public String getEdema() {
		return this.edema;
	}

	public void setEdema(String edema) {
		this.edema = edema;
	}

	public String getDiagnosisRemark() {
		return this.diagnosisRemark;
	}

	public void setDiagnosisRemark(String diagnosisRemark) {
		this.diagnosisRemark = diagnosisRemark;
	}

	public String getBornBirthAddressPlan() {
		return this.bornBirthAddressPlan;
	}

	public void setBornBirthAddressPlan(String bornBirthAddressPlan) {
		this.bornBirthAddressPlan = bornBirthAddressPlan;
	}

	public Double getPelvis01() {
		return this.pelvis01;
	}

	public void setPelvis01(Double pelvis01) {
		this.pelvis01 = pelvis01;
	}

	public Double getPelvis02() {
		return this.pelvis02;
	}

	public void setPelvis02(Double pelvis02) {
		this.pelvis02 = pelvis02;
	}

	public Double getPelvis03() {
		return this.pelvis03;
	}

	public void setPelvis03(Double pelvis03) {
		this.pelvis03 = pelvis03;
	}

	public Double getPelvis04() {
		return this.pelvis04;
	}

	public void setPelvis04(Double pelvis04) {
		this.pelvis04 = pelvis04;
	}

	public String getForeignId() {
		return this.foreignId;
	}

	public void setForeignId(String foreignId) {
		this.foreignId = foreignId;
	}

}