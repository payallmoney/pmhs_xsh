package cn.net.tongfang.framework.security.vo;

// Generated by Hibernate Tools 3.2.4.GA

/**
 * DiabetesSymptom generated by hbm2java
 */
public class DiabetesSymptom implements java.io.Serializable {

	private String id;
	private String diabetesVisitId;
	private Integer diabetesSymptomId;

	public DiabetesSymptom() {
	}

	public DiabetesSymptom(String diabetesVisitId, Integer diabetesSymptomId) {
		this.diabetesVisitId = diabetesVisitId;
		this.diabetesSymptomId = diabetesSymptomId;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDiabetesVisitId() {
		return this.diabetesVisitId;
	}

	public void setDiabetesVisitId(String diabetesVisitId) {
		this.diabetesVisitId = diabetesVisitId;
	}

	public Integer getDiabetesSymptomId() {
		return this.diabetesSymptomId;
	}

	public void setDiabetesSymptomId(Integer diabetesSymptomId) {
		this.diabetesSymptomId = diabetesSymptomId;
	}

}
