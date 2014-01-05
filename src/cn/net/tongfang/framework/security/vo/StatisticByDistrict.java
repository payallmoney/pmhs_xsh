package cn.net.tongfang.framework.security.vo;

public class StatisticByDistrict {
	// Fields
	private String flagId;
	private String id;
	private String name;
	private String parentId;
	private Integer level;
	private Integer isDetail;
	private String districtNumber;
	private Long vHealthFileCount;
	private Long cHealthFileCount;
	private Long babyVisitCount;
	private Long childrenMediExam01count;
	private Long childrenMediExam02count;
	private Long childrenMediExam36count;
	private Long childrenMdeiExamTotals;
	private Long firstVistBeforeBornCount;
	private Long visitBeforeBornCount;
	private Long visitBeforeBornTotals;
	private Long visitAfterBornCount;
	private Long visitAfterBorn42count;
	private Long hypertensionVisitCount;
	private Long diabetesVisitCount;
	private Long furiousVisitCount;
	private Long medicalExamCount;
	private String inputPersonId;

	private Long chileHealthFileCount;
	private Long childHighRiskHealthFileCount;
	private Long oldManHeathFileCount;
	private Long hypertensionHealthFileCount;
	private Long diabetesHealthFileCount;
	private Long holergasiaHealthFileCount;
	private Long womanInitBirthHealthFileCount;
	private Long womanAreadyBirthHealthFileCount;
	private Long womanExceptionBirthHealthFileCount;
	private Long womanHighRiskInitBirthHealthFileCount;
	private Long womanHighRiskAreadyBirthHealthFileCount;
	private Long womanHighRiskExceptionBirthHealthFileCount;	
	// Constructors

	/** default constructor */
	public StatisticByDistrict() {
	}

	/** full constructor */
	public StatisticByDistrict(String flagId, String id, String name,
			String parentId, Integer level, Integer isDetail,
			String districtNumber, Long vHealthFileCount,
			Long cHealthFileCount, Long babyVisitCount,
			Long childrenMediExam01count, Long childrenMediExam02count,
			Long childrenMediExam36count, Long childrenMdeiExamTotals,
			Long firstVistBeforeBornCount, Long visitBeforeBornCount,
			Long visitBeforeBornTotals, Long visitAfterBornCount,
			Long visitAfterBorn42count, Long hypertensionVisitCount,
			Long diabetesVisitCount, Long furiousVisitCount,
			Long medicalExamCount, String inputPersonId,
			Long chileHealthFileCount, Long childHighRiskHealthFileCount,
			Long oldManHeathFileCount, Long hypertensionHealthFileCount,
			Long diabetesHealthFileCount, Long holergasiaHealthFileCount,
			Long womanInitBirthHealthFileCount,
			Long womanAreadyBirthHealthFileCount,
			Long womanExceptionBirthHealthFileCount,
			Long womanHighRiskInitBirthHealthFileCount,
			Long womanHighRiskAreadyBirthHealthFileCount,
			Long womanHighRiskExceptionBirthHealthFileCount) {
		super();
		this.flagId = flagId;
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.level = level;
		this.isDetail = isDetail;
		this.districtNumber = districtNumber;
		this.vHealthFileCount = vHealthFileCount;
		this.cHealthFileCount = cHealthFileCount;
		this.babyVisitCount = babyVisitCount;
		this.childrenMediExam01count = childrenMediExam01count;
		this.childrenMediExam02count = childrenMediExam02count;
		this.childrenMediExam36count = childrenMediExam36count;
		this.childrenMdeiExamTotals = childrenMdeiExamTotals;
		this.firstVistBeforeBornCount = firstVistBeforeBornCount;
		this.visitBeforeBornCount = visitBeforeBornCount;
		this.visitBeforeBornTotals = visitBeforeBornTotals;
		this.visitAfterBornCount = visitAfterBornCount;
		this.visitAfterBorn42count = visitAfterBorn42count;
		this.hypertensionVisitCount = hypertensionVisitCount;
		this.diabetesVisitCount = diabetesVisitCount;
		this.furiousVisitCount = furiousVisitCount;
		this.medicalExamCount = medicalExamCount;
		this.inputPersonId = inputPersonId;
		this.chileHealthFileCount = chileHealthFileCount;
		this.childHighRiskHealthFileCount = childHighRiskHealthFileCount;
		this.oldManHeathFileCount = oldManHeathFileCount;
		this.hypertensionHealthFileCount = hypertensionHealthFileCount;
		this.diabetesHealthFileCount = diabetesHealthFileCount;
		this.holergasiaHealthFileCount = holergasiaHealthFileCount;
		this.womanInitBirthHealthFileCount = womanInitBirthHealthFileCount;
		this.womanAreadyBirthHealthFileCount = womanAreadyBirthHealthFileCount;
		this.womanExceptionBirthHealthFileCount = womanExceptionBirthHealthFileCount;
		this.womanHighRiskInitBirthHealthFileCount = womanHighRiskInitBirthHealthFileCount;
		this.womanHighRiskAreadyBirthHealthFileCount = womanHighRiskAreadyBirthHealthFileCount;
		this.womanHighRiskExceptionBirthHealthFileCount = womanHighRiskExceptionBirthHealthFileCount;
	}

	// Property accessors

	public String getFlagId() {
		return flagId;
	}


	public void setFlagId(String flagId) {
		this.flagId = flagId;
	}
	public String getId() {
		return this.id;
	}


	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
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

	public String getDistrictNumber() {
		return this.districtNumber;
	}

	public void setDistrictNumber(String districtNumber) {
		this.districtNumber = districtNumber;
	}

	public Long getvHealthFileCount() {
		return vHealthFileCount;
	}

	public void setvHealthFileCount(Long vHealthFileCount) {
		this.vHealthFileCount = vHealthFileCount;
	}

	public Long getcHealthFileCount() {
		return cHealthFileCount;
	}

	public void setcHealthFileCount(Long cHealthFileCount) {
		this.cHealthFileCount = cHealthFileCount;
	}

	public Long getChildrenMdeiExamTotals() {
		return childrenMdeiExamTotals;
	}

	public void setChildrenMdeiExamTotals(Long childrenMdeiExamTotals) {
		this.childrenMdeiExamTotals = childrenMdeiExamTotals;
	}

	public Long getVisitBeforeBornTotals() {
		return visitBeforeBornTotals;
	}

	public void setVisitBeforeBornTotals(Long visitBeforeBornTotals) {
		this.visitBeforeBornTotals = visitBeforeBornTotals;
	}

	public Long getBabyVisitCount() {
		return this.babyVisitCount;
	}

	public void setBabyVisitCount(Long babyVisitCount) {
		this.babyVisitCount = babyVisitCount;
	}

	public Long getChildrenMediExam01count() {
		return this.childrenMediExam01count;
	}

	public void setChildrenMediExam01count(Long childrenMediExam01count) {
		this.childrenMediExam01count = childrenMediExam01count;
	}

	public Long getChildrenMediExam02count() {
		return this.childrenMediExam02count;
	}

	public void setChildrenMediExam02count(Long childrenMediExam02count) {
		this.childrenMediExam02count = childrenMediExam02count;
	}

	public Long getChildrenMediExam36count() {
		return this.childrenMediExam36count;
	}

	public void setChildrenMediExam36count(Long childrenMediExam36count) {
		this.childrenMediExam36count = childrenMediExam36count;
	}

	public Long getFirstVistBeforeBornCount() {
		return this.firstVistBeforeBornCount;
	}

	public void setFirstVistBeforeBornCount(Long firstVistBeforeBornCount) {
		this.firstVistBeforeBornCount = firstVistBeforeBornCount;
	}

	public Long getVisitBeforeBornCount() {
		return this.visitBeforeBornCount;
	}

	public void setVisitBeforeBornCount(Long visitBeforeBornCount) {
		this.visitBeforeBornCount = visitBeforeBornCount;
	}

	public Long getVisitAfterBornCount() {
		return this.visitAfterBornCount;
	}

	public void setVisitAfterBornCount(Long visitAfterBornCount) {
		this.visitAfterBornCount = visitAfterBornCount;
	}

	public Long getVisitAfterBorn42count() {
		return this.visitAfterBorn42count;
	}

	public void setVisitAfterBorn42count(Long visitAfterBorn42count) {
		this.visitAfterBorn42count = visitAfterBorn42count;
	}

	public Long getHypertensionVisitCount() {
		return this.hypertensionVisitCount;
	}

	public void setHypertensionVisitCount(Long hypertensionVisitCount) {
		this.hypertensionVisitCount = hypertensionVisitCount;
	}

	public Long getDiabetesVisitCount() {
		return this.diabetesVisitCount;
	}

	public void setDiabetesVisitCount(Long diabetesVisitCount) {
		this.diabetesVisitCount = diabetesVisitCount;
	}

	public Long getFuriousVisitCount() {
		return this.furiousVisitCount;
	}

	public void setFuriousVisitCount(Long furiousVisitCount) {
		this.furiousVisitCount = furiousVisitCount;
	}

	public Long getMedicalExamCount() {
		return this.medicalExamCount;
	}

	public void setMedicalExamCount(Long medicalExamCount) {
		this.medicalExamCount = medicalExamCount;
	}

	public String getInputPersonId() {
		return this.inputPersonId;
	}

	public void setInputPersonId(String inputPersonId) {
		this.inputPersonId = inputPersonId;
	}

	public Long getChileHealthFileCount() {
		return chileHealthFileCount;
	}

	public void setChileHealthFileCount(Long chileHealthFileCount) {
		this.chileHealthFileCount = chileHealthFileCount;
	}

	public Long getChildHighRiskHealthFileCount() {
		return childHighRiskHealthFileCount;
	}

	public void setChildHighRiskHealthFileCount(Long childHighRiskHealthFileCount) {
		this.childHighRiskHealthFileCount = childHighRiskHealthFileCount;
	}

	public Long getOldManHeathFileCount() {
		return oldManHeathFileCount;
	}

	public void setOldManHeathFileCount(Long oldManHeathFileCount) {
		this.oldManHeathFileCount = oldManHeathFileCount;
	}

	public Long getHypertensionHealthFileCount() {
		return hypertensionHealthFileCount;
	}

	public void setHypertensionHealthFileCount(Long hypertensionHealthFileCount) {
		this.hypertensionHealthFileCount = hypertensionHealthFileCount;
	}

	public Long getDiabetesHealthFileCount() {
		return diabetesHealthFileCount;
	}

	public void setDiabetesHealthFileCount(Long diabetesHealthFileCount) {
		this.diabetesHealthFileCount = diabetesHealthFileCount;
	}

	public Long getHolergasiaHealthFileCount() {
		return holergasiaHealthFileCount;
	}

	public void setHolergasiaHealthFileCount(Long holergasiaHealthFileCount) {
		this.holergasiaHealthFileCount = holergasiaHealthFileCount;
	}

	public Long getWomanInitBirthHealthFileCount() {
		return womanInitBirthHealthFileCount;
	}

	public void setWomanInitBirthHealthFileCount(Long womanInitBirthHealthFileCount) {
		this.womanInitBirthHealthFileCount = womanInitBirthHealthFileCount;
	}

	public Long getWomanAreadyBirthHealthFileCount() {
		return womanAreadyBirthHealthFileCount;
	}

	public void setWomanAreadyBirthHealthFileCount(
			Long womanAreadyBirthHealthFileCount) {
		this.womanAreadyBirthHealthFileCount = womanAreadyBirthHealthFileCount;
	}

	public Long getWomanExceptionBirthHealthFileCount() {
		return womanExceptionBirthHealthFileCount;
	}

	public void setWomanExceptionBirthHealthFileCount(
			Long womanExceptionBirthHealthFileCount) {
		this.womanExceptionBirthHealthFileCount = womanExceptionBirthHealthFileCount;
	}

	public Long getWomanHighRiskInitBirthHealthFileCount() {
		return womanHighRiskInitBirthHealthFileCount;
	}

	public void setWomanHighRiskInitBirthHealthFileCount(
			Long womanHighRiskInitBirthHealthFileCount) {
		this.womanHighRiskInitBirthHealthFileCount = womanHighRiskInitBirthHealthFileCount;
	}

	public Long getWomanHighRiskAreadyBirthHealthFileCount() {
		return womanHighRiskAreadyBirthHealthFileCount;
	}

	public void setWomanHighRiskAreadyBirthHealthFileCount(
			Long womanHighRiskAreadyBirthHealthFileCount) {
		this.womanHighRiskAreadyBirthHealthFileCount = womanHighRiskAreadyBirthHealthFileCount;
	}

	public Long getWomanHighRiskExceptionBirthHealthFileCount() {
		return womanHighRiskExceptionBirthHealthFileCount;
	}

	public void setWomanHighRiskExceptionBirthHealthFileCount(
			Long womanHighRiskExceptionBirthHealthFileCount) {
		this.womanHighRiskExceptionBirthHealthFileCount = womanHighRiskExceptionBirthHealthFileCount;
	}
	
}
