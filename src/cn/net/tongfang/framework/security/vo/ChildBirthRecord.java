package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * ChildBirthRecord entity. @author MyEclipse Persistence Tools
 */

public class ChildBirthRecord implements java.io.Serializable {

	// Fields

	private String id;
	private String fileNo;
	private Integer childbirthYear;
	private Integer childbirthMonth;
	private Integer childbirthDay;
	private Double flooding;
	private String childbirthAddress;
	private String borthWeekly;
	private String childbirthWay;
	private String deliverWay;
	private String lacerationOfPerineum;
	private Integer outerFissure;
	private String bloodPressure;
	private String deal;
	private String comorbidity;
	private String comorbidityOther;
	private String criticalWoman;
	private String criticalSymptom;
	private String babySurvive;
	private String babyBirth;
	private String babyBirthOther;
	private String babyComorbidity;
	private String babyComorbidityOther;
	private String nurseTime;
	private String diagnosis;
	private String certifiId;
	private String inputPersonId;
	private Timestamp inputDate;
	private String execDistrictNum;
	private Integer totalLaborHours;
	private Integer oneLaborHours;
	private Integer twoLaborHours;
	private Integer twoLaborMinutes;
	private Integer threeLaborMinutes;
	private Timestamp placentaParturitionDate;
	private String placentaParturitionWay;
	private String isComplete;
	private String isNotComplete;
	private String cervicalLaceration;
	private Integer cervicalLacerationPosition01;
	private Integer cervicalLacerationPosition02;
	private Double bleeding01;
	private Double bleeding02;
	private String complication;
	private String complicationName;
	private String apgar01;
	private String apgar02;
	private String suffocation;
	private String suffocationOther;
	private String suffocationOther01;
	private String birthTrauma;
	private String birthTraumaOther;
	private String birthDefects;
	private String birthDefectsOther;
	private String dischargeDiagnosis01;
	private String dischargeDiagnosis02;
	private String diseaseScreeningOther;
	private Integer totalLaborMinutes;
	private Integer oneLaborMinutes;

	// Constructors

	/** default constructor */
	public ChildBirthRecord() {
	}

	/** minimal constructor */
	public ChildBirthRecord(String id, String fileNo, String certifiId) {
		this.id = id;
		this.fileNo = fileNo;
		this.certifiId = certifiId;
	}

	/** full constructor */
	public ChildBirthRecord(String id, String fileNo, Integer childbirthYear,
			Integer childbirthMonth, Integer childbirthDay, Double flooding,
			String childbirthAddress, String borthWeekly, String childbirthWay,
			String deliverWay, String lacerationOfPerineum,
			Integer outerFissure, String bloodPressure, String deal,
			String comorbidity, String comorbidityOther, String criticalWoman,
			String criticalSymptom, String babySurvive, String babyBirth,
			String babyBirthOther, String babyComorbidity,
			String babyComorbidityOther, String nurseTime, String diagnosis,
			String certifiId, String inputPersonId, Timestamp inputDate,
			String execDistrictNum, Integer totalLaborHours,
			Integer oneLaborHours, Integer twoLaborHours,
			Integer twoLaborMinutes, Integer threeLaborMinutes,
			Timestamp placentaParturitionDate, String placentaParturitionWay,
			String isComplete, String isNotComplete, String cervicalLaceration,
			Integer cervicalLacerationPosition01,
			Integer cervicalLacerationPosition02, Double bleeding01,
			Double bleeding02, String complication, String complicationName,
			String apgar01, String apgar02, String suffocation,
			String suffocationOther, String suffocationOther01,
			String birthTrauma, String birthTraumaOther, String birthDefects,
			String birthDefectsOther, String dischargeDiagnosis01,
			String dischargeDiagnosis02, String diseaseScreeningOther,
			Integer totalLaborMinutes, Integer oneLaborMinutes) {
		this.id = id;
		this.fileNo = fileNo;
		this.childbirthYear = childbirthYear;
		this.childbirthMonth = childbirthMonth;
		this.childbirthDay = childbirthDay;
		this.flooding = flooding;
		this.childbirthAddress = childbirthAddress;
		this.borthWeekly = borthWeekly;
		this.childbirthWay = childbirthWay;
		this.deliverWay = deliverWay;
		this.lacerationOfPerineum = lacerationOfPerineum;
		this.outerFissure = outerFissure;
		this.bloodPressure = bloodPressure;
		this.deal = deal;
		this.comorbidity = comorbidity;
		this.comorbidityOther = comorbidityOther;
		this.criticalWoman = criticalWoman;
		this.criticalSymptom = criticalSymptom;
		this.babySurvive = babySurvive;
		this.babyBirth = babyBirth;
		this.babyBirthOther = babyBirthOther;
		this.babyComorbidity = babyComorbidity;
		this.babyComorbidityOther = babyComorbidityOther;
		this.nurseTime = nurseTime;
		this.diagnosis = diagnosis;
		this.certifiId = certifiId;
		this.inputPersonId = inputPersonId;
		this.inputDate = inputDate;
		this.execDistrictNum = execDistrictNum;
		this.totalLaborHours = totalLaborHours;
		this.oneLaborHours = oneLaborHours;
		this.twoLaborHours = twoLaborHours;
		this.twoLaborMinutes = twoLaborMinutes;
		this.threeLaborMinutes = threeLaborMinutes;
		this.placentaParturitionDate = placentaParturitionDate;
		this.placentaParturitionWay = placentaParturitionWay;
		this.isComplete = isComplete;
		this.isNotComplete = isNotComplete;
		this.cervicalLaceration = cervicalLaceration;
		this.cervicalLacerationPosition01 = cervicalLacerationPosition01;
		this.cervicalLacerationPosition02 = cervicalLacerationPosition02;
		this.bleeding01 = bleeding01;
		this.bleeding02 = bleeding02;
		this.complication = complication;
		this.complicationName = complicationName;
		this.apgar01 = apgar01;
		this.apgar02 = apgar02;
		this.suffocation = suffocation;
		this.suffocationOther = suffocationOther;
		this.suffocationOther01 = suffocationOther01;
		this.birthTrauma = birthTrauma;
		this.birthTraumaOther = birthTraumaOther;
		this.birthDefects = birthDefects;
		this.birthDefectsOther = birthDefectsOther;
		this.dischargeDiagnosis01 = dischargeDiagnosis01;
		this.dischargeDiagnosis02 = dischargeDiagnosis02;
		this.diseaseScreeningOther = diseaseScreeningOther;
		this.totalLaborMinutes = totalLaborMinutes;
		this.oneLaborMinutes = oneLaborMinutes;
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

	public Double getFlooding() {
		return this.flooding;
	}

	public void setFlooding(Double flooding) {
		this.flooding = flooding;
	}

	public String getChildbirthAddress() {
		return this.childbirthAddress;
	}

	public void setChildbirthAddress(String childbirthAddress) {
		this.childbirthAddress = childbirthAddress;
	}

	public String getBorthWeekly() {
		return this.borthWeekly;
	}

	public void setBorthWeekly(String borthWeekly) {
		this.borthWeekly = borthWeekly;
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

	public String getLacerationOfPerineum() {
		return this.lacerationOfPerineum;
	}

	public void setLacerationOfPerineum(String lacerationOfPerineum) {
		this.lacerationOfPerineum = lacerationOfPerineum;
	}

	public Integer getOuterFissure() {
		return this.outerFissure;
	}

	public void setOuterFissure(Integer outerFissure) {
		this.outerFissure = outerFissure;
	}

	public String getBloodPressure() {
		return this.bloodPressure;
	}

	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	public String getDeal() {
		return this.deal;
	}

	public void setDeal(String deal) {
		this.deal = deal;
	}

	public String getComorbidity() {
		return this.comorbidity;
	}

	public void setComorbidity(String comorbidity) {
		this.comorbidity = comorbidity;
	}

	public String getComorbidityOther() {
		return this.comorbidityOther;
	}

	public void setComorbidityOther(String comorbidityOther) {
		this.comorbidityOther = comorbidityOther;
	}

	public String getCriticalWoman() {
		return this.criticalWoman;
	}

	public void setCriticalWoman(String criticalWoman) {
		this.criticalWoman = criticalWoman;
	}

	public String getCriticalSymptom() {
		return this.criticalSymptom;
	}

	public void setCriticalSymptom(String criticalSymptom) {
		this.criticalSymptom = criticalSymptom;
	}

	public String getBabySurvive() {
		return this.babySurvive;
	}

	public void setBabySurvive(String babySurvive) {
		this.babySurvive = babySurvive;
	}

	public String getBabyBirth() {
		return this.babyBirth;
	}

	public void setBabyBirth(String babyBirth) {
		this.babyBirth = babyBirth;
	}

	public String getBabyBirthOther() {
		return this.babyBirthOther;
	}

	public void setBabyBirthOther(String babyBirthOther) {
		this.babyBirthOther = babyBirthOther;
	}

	public String getBabyComorbidity() {
		return this.babyComorbidity;
	}

	public void setBabyComorbidity(String babyComorbidity) {
		this.babyComorbidity = babyComorbidity;
	}

	public String getBabyComorbidityOther() {
		return this.babyComorbidityOther;
	}

	public void setBabyComorbidityOther(String babyComorbidityOther) {
		this.babyComorbidityOther = babyComorbidityOther;
	}

	public String getNurseTime() {
		return this.nurseTime;
	}

	public void setNurseTime(String nurseTime) {
		this.nurseTime = nurseTime;
	}

	public String getDiagnosis() {
		return this.diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getCertifiId() {
		return this.certifiId;
	}

	public void setCertifiId(String certifiId) {
		this.certifiId = certifiId;
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

	public String getExecDistrictNum() {
		return this.execDistrictNum;
	}

	public void setExecDistrictNum(String execDistrictNum) {
		this.execDistrictNum = execDistrictNum;
	}

	public Integer getTotalLaborHours() {
		return this.totalLaborHours;
	}

	public void setTotalLaborHours(Integer totalLaborHours) {
		this.totalLaborHours = totalLaborHours;
	}

	public Integer getOneLaborHours() {
		return this.oneLaborHours;
	}

	public void setOneLaborHours(Integer oneLaborHours) {
		this.oneLaborHours = oneLaborHours;
	}

	public Integer getTwoLaborHours() {
		return this.twoLaborHours;
	}

	public void setTwoLaborHours(Integer twoLaborHours) {
		this.twoLaborHours = twoLaborHours;
	}

	public Integer getTwoLaborMinutes() {
		return this.twoLaborMinutes;
	}

	public void setTwoLaborMinutes(Integer twoLaborMinutes) {
		this.twoLaborMinutes = twoLaborMinutes;
	}

	public Integer getThreeLaborMinutes() {
		return this.threeLaborMinutes;
	}

	public void setThreeLaborMinutes(Integer threeLaborMinutes) {
		this.threeLaborMinutes = threeLaborMinutes;
	}

	public Timestamp getPlacentaParturitionDate() {
		return this.placentaParturitionDate;
	}

	public void setPlacentaParturitionDate(Timestamp placentaParturitionDate) {
		this.placentaParturitionDate = placentaParturitionDate;
	}

	public String getPlacentaParturitionWay() {
		return this.placentaParturitionWay;
	}

	public void setPlacentaParturitionWay(String placentaParturitionWay) {
		this.placentaParturitionWay = placentaParturitionWay;
	}

	public String getIsComplete() {
		return this.isComplete;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}

	public String getIsNotComplete() {
		return this.isNotComplete;
	}

	public void setIsNotComplete(String isNotComplete) {
		this.isNotComplete = isNotComplete;
	}

	public String getCervicalLaceration() {
		return this.cervicalLaceration;
	}

	public void setCervicalLaceration(String cervicalLaceration) {
		this.cervicalLaceration = cervicalLaceration;
	}

	public Integer getCervicalLacerationPosition01() {
		return this.cervicalLacerationPosition01;
	}

	public void setCervicalLacerationPosition01(
			Integer cervicalLacerationPosition01) {
		this.cervicalLacerationPosition01 = cervicalLacerationPosition01;
	}

	public Integer getCervicalLacerationPosition02() {
		return this.cervicalLacerationPosition02;
	}

	public void setCervicalLacerationPosition02(
			Integer cervicalLacerationPosition02) {
		this.cervicalLacerationPosition02 = cervicalLacerationPosition02;
	}

	public Double getBleeding01() {
		return this.bleeding01;
	}

	public void setBleeding01(Double bleeding01) {
		this.bleeding01 = bleeding01;
	}

	public Double getBleeding02() {
		return this.bleeding02;
	}

	public void setBleeding02(Double bleeding02) {
		this.bleeding02 = bleeding02;
	}

	public String getComplication() {
		return this.complication;
	}

	public void setComplication(String complication) {
		this.complication = complication;
	}

	public String getComplicationName() {
		return this.complicationName;
	}

	public void setComplicationName(String complicationName) {
		this.complicationName = complicationName;
	}

	public String getApgar01() {
		return this.apgar01;
	}

	public void setApgar01(String apgar01) {
		this.apgar01 = apgar01;
	}

	public String getApgar02() {
		return this.apgar02;
	}

	public void setApgar02(String apgar02) {
		this.apgar02 = apgar02;
	}

	public String getSuffocation() {
		return this.suffocation;
	}

	public void setSuffocation(String suffocation) {
		this.suffocation = suffocation;
	}

	public String getSuffocationOther() {
		return this.suffocationOther;
	}

	public void setSuffocationOther(String suffocationOther) {
		this.suffocationOther = suffocationOther;
	}

	public String getSuffocationOther01() {
		return this.suffocationOther01;
	}

	public void setSuffocationOther01(String suffocationOther01) {
		this.suffocationOther01 = suffocationOther01;
	}

	public String getBirthTrauma() {
		return this.birthTrauma;
	}

	public void setBirthTrauma(String birthTrauma) {
		this.birthTrauma = birthTrauma;
	}

	public String getBirthTraumaOther() {
		return this.birthTraumaOther;
	}

	public void setBirthTraumaOther(String birthTraumaOther) {
		this.birthTraumaOther = birthTraumaOther;
	}

	public String getBirthDefects() {
		return this.birthDefects;
	}

	public void setBirthDefects(String birthDefects) {
		this.birthDefects = birthDefects;
	}

	public String getBirthDefectsOther() {
		return this.birthDefectsOther;
	}

	public void setBirthDefectsOther(String birthDefectsOther) {
		this.birthDefectsOther = birthDefectsOther;
	}

	public String getDischargeDiagnosis01() {
		return this.dischargeDiagnosis01;
	}

	public void setDischargeDiagnosis01(String dischargeDiagnosis01) {
		this.dischargeDiagnosis01 = dischargeDiagnosis01;
	}

	public String getDischargeDiagnosis02() {
		return this.dischargeDiagnosis02;
	}

	public void setDischargeDiagnosis02(String dischargeDiagnosis02) {
		this.dischargeDiagnosis02 = dischargeDiagnosis02;
	}

	public String getDiseaseScreeningOther() {
		return this.diseaseScreeningOther;
	}

	public void setDiseaseScreeningOther(String diseaseScreeningOther) {
		this.diseaseScreeningOther = diseaseScreeningOther;
	}

	public Integer getTotalLaborMinutes() {
		return this.totalLaborMinutes;
	}

	public void setTotalLaborMinutes(Integer totalLaborMinutes) {
		this.totalLaborMinutes = totalLaborMinutes;
	}

	public Integer getOneLaborMinutes() {
		return this.oneLaborMinutes;
	}

	public void setOneLaborMinutes(Integer oneLaborMinutes) {
		this.oneLaborMinutes = oneLaborMinutes;
	}

}