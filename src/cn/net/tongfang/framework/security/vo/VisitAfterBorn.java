package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * VisitAfterBorn entity. @author MyEclipse Persistence Tools
 */

public class VisitAfterBorn implements java.io.Serializable {

	// Fields

	private String id;
	private String fileNo;
	private String inputPersonId;
	private Timestamp inputDate;
	private Timestamp visitDate;
	private Double diastolicPressure;
	private Double systolicPressure;
	private String result;
	private String resultOther;
	private String afterBornDirectOther;
	private Boolean transfer;
	private String transReason;
	private String transUnit;
	private String visitDoctor;
	private Timestamp nextVisitDate;
	private Double bodyHeat;
	private String health;
	private String mind;
	private String breast;
	private String breastOther;
	private String lochia;
	private String lochiaOther;
	private String metra;
	private String metraOther;
	private String wound;
	private String woundOther;
	private String other;
	private String recordType;
	private String highRisk;
	private String highRiskRemark;
	private String execDistrictNum;
	private String highRiskSearch;
	private String item;
	private Integer parities;
	private Integer postnatalDays;
	private Double weight;
	private String cervix;
	private String cervixOther;
	private String attachment;
	private String attachmentOther;
	private String vulva;
	private String vulvaOther;
//	private String vagina;
//	private String vaginaOther;
	private Integer pulseRate;
	private String milk;
	private String swelling;
	private String nipple;
	private Double palaceHeight;
	private String woundHealing;
	private String lochia01;
	private String lochia02;
	private String foreignId;
	// Constructors

	/** default constructor */
	public VisitAfterBorn() {
	}

	/** minimal constructor */
	public VisitAfterBorn(String fileNo, String inputPersonId,
			Timestamp inputDate) {
		this.fileNo = fileNo;
		this.inputPersonId = inputPersonId;
		this.inputDate = inputDate;
	}

	/** full constructor */
	public VisitAfterBorn(String fileNo, String inputPersonId,
			Timestamp inputDate, Timestamp visitDate, Double diastolicPressure,
			Double systolicPressure, String result, String resultOther,
			String afterBornDirectOther, Boolean transfer, String transReason,
			String transUnit, String visitDoctor, Timestamp nextVisitDate,
			Double bodyHeat, String health, String mind, String breast,
			String breastOther, String lochia, String lochiaOther,
			String metra, String metraOther, String wound, String woundOther,
			String other, String recordType, String highRisk,
			String highRiskRemark, String execDistrictNum,
			String highRiskSearch, String item, Integer parities,
			Integer postnatalDays, Double weight, String cervix,
			String cervixOther, String attachment, String attachmentOther,
			String vulva, String vulvaOther,
			Integer pulseRate, String milk, String swelling, String nipple,
			Double palaceHeight, String woundHealing, String lochia01,
			String lochia02,String foreignId) {
		this.fileNo = fileNo;
		this.inputPersonId = inputPersonId;
		this.inputDate = inputDate;
		this.visitDate = visitDate;
		this.diastolicPressure = diastolicPressure;
		this.systolicPressure = systolicPressure;
		this.result = result;
		this.resultOther = resultOther;
		this.afterBornDirectOther = afterBornDirectOther;
		this.transfer = transfer;
		this.transReason = transReason;
		this.transUnit = transUnit;
		this.visitDoctor = visitDoctor;
		this.nextVisitDate = nextVisitDate;
		this.bodyHeat = bodyHeat;
		this.health = health;
		this.mind = mind;
		this.breast = breast;
		this.breastOther = breastOther;
		this.lochia = lochia;
		this.lochiaOther = lochiaOther;
		this.metra = metra;
		this.metraOther = metraOther;
		this.wound = wound;
		this.woundOther = woundOther;
		this.other = other;
		this.recordType = recordType;
		this.highRisk = highRisk;
		this.highRiskRemark = highRiskRemark;
		this.execDistrictNum = execDistrictNum;
		this.highRiskSearch = highRiskSearch;
		this.item = item;
		this.parities = parities;
		this.postnatalDays = postnatalDays;
		this.weight = weight;
		this.cervix = cervix;
		this.cervixOther = cervixOther;
		this.attachment = attachment;
		this.attachmentOther = attachmentOther;
		this.vulva = vulva;
		this.vulvaOther = vulvaOther;
		this.pulseRate = pulseRate;
		this.milk = milk;
		this.swelling = swelling;
		this.nipple = nipple;
		this.palaceHeight = palaceHeight;
		this.woundHealing = woundHealing;
		this.lochia01 = lochia01;
		this.lochia02 = lochia02;
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

	public String getAfterBornDirectOther() {
		return this.afterBornDirectOther;
	}

	public void setAfterBornDirectOther(String afterBornDirectOther) {
		this.afterBornDirectOther = afterBornDirectOther;
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

	public Double getBodyHeat() {
		return this.bodyHeat;
	}

	public void setBodyHeat(Double bodyHeat) {
		this.bodyHeat = bodyHeat;
	}

	public String getHealth() {
		return this.health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getMind() {
		return this.mind;
	}

	public void setMind(String mind) {
		this.mind = mind;
	}

	public String getBreast() {
		return this.breast;
	}

	public void setBreast(String breast) {
		this.breast = breast;
	}

	public String getBreastOther() {
		return this.breastOther;
	}

	public void setBreastOther(String breastOther) {
		this.breastOther = breastOther;
	}

	public String getLochia() {
		return this.lochia;
	}

	public void setLochia(String lochia) {
		this.lochia = lochia;
	}

	public String getLochiaOther() {
		return this.lochiaOther;
	}

	public void setLochiaOther(String lochiaOther) {
		this.lochiaOther = lochiaOther;
	}

	public String getMetra() {
		return this.metra;
	}

	public void setMetra(String metra) {
		this.metra = metra;
	}

	public String getMetraOther() {
		return this.metraOther;
	}

	public void setMetraOther(String metraOther) {
		this.metraOther = metraOther;
	}

	public String getWound() {
		return this.wound;
	}

	public void setWound(String wound) {
		this.wound = wound;
	}

	public String getWoundOther() {
		return this.woundOther;
	}

	public void setWoundOther(String woundOther) {
		this.woundOther = woundOther;
	}

	public String getOther() {
		return this.other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getRecordType() {
		return this.recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
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

	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Integer getParities() {
		return this.parities;
	}

	public void setParities(Integer parities) {
		this.parities = parities;
	}

	public Integer getPostnatalDays() {
		return this.postnatalDays;
	}

	public void setPostnatalDays(Integer postnatalDays) {
		this.postnatalDays = postnatalDays;
	}

	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getCervix() {
		return this.cervix;
	}

	public void setCervix(String cervix) {
		this.cervix = cervix;
	}

	public String getCervixOther() {
		return this.cervixOther;
	}

	public void setCervixOther(String cervixOther) {
		this.cervixOther = cervixOther;
	}

	public String getAttachment() {
		return this.attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getAttachmentOther() {
		return this.attachmentOther;
	}

	public void setAttachmentOther(String attachmentOther) {
		this.attachmentOther = attachmentOther;
	}

	public String getVulva() {
		return this.vulva;
	}

	public void setVulva(String vulva) {
		this.vulva = vulva;
	}

	public String getVulvaOther() {
		return this.vulvaOther;
	}

	public void setVulvaOther(String vulvaOther) {
		this.vulvaOther = vulvaOther;
	}

	public Integer getPulseRate() {
		return this.pulseRate;
	}

	public void setPulseRate(Integer pulseRate) {
		this.pulseRate = pulseRate;
	}

	public String getMilk() {
		return this.milk;
	}

	public void setMilk(String milk) {
		this.milk = milk;
	}

	public String getSwelling() {
		return this.swelling;
	}

	public void setSwelling(String swelling) {
		this.swelling = swelling;
	}

	public String getNipple() {
		return this.nipple;
	}

	public void setNipple(String nipple) {
		this.nipple = nipple;
	}

	public Double getPalaceHeight() {
		return this.palaceHeight;
	}

	public void setPalaceHeight(Double palaceHeight) {
		this.palaceHeight = palaceHeight;
	}

	public String getWoundHealing() {
		return this.woundHealing;
	}

	public void setWoundHealing(String woundHealing) {
		this.woundHealing = woundHealing;
	}

	public String getLochia01() {
		return this.lochia01;
	}

	public void setLochia01(String lochia01) {
		this.lochia01 = lochia01;
	}

	public String getLochia02() {
		return this.lochia02;
	}

	public void setLochia02(String lochia02) {
		this.lochia02 = lochia02;
	}

	public String getForeignId() {
		return foreignId;
	}

	public void setForeignId(String foreignId) {
		this.foreignId = foreignId;
	}

}