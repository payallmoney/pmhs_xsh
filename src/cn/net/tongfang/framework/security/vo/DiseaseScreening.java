package cn.net.tongfang.framework.security.vo;

/**
 * DiseaseScreening entity. @author MyEclipse Persistence Tools
 */

public class DiseaseScreening implements java.io.Serializable {

	// Fields

	private String id;
	private String childBirthRecordId;
	private Integer diseaseScreeningId;

	// Constructors

	/** default constructor */
	public DiseaseScreening() {
	}

	/** full constructor */
	public DiseaseScreening(String childBirthRecordId,
			Integer diseaseScreeningId) {
		this.childBirthRecordId = childBirthRecordId;
		this.diseaseScreeningId = diseaseScreeningId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChildBirthRecordId() {
		return this.childBirthRecordId;
	}

	public void setChildBirthRecordId(String childBirthRecordId) {
		this.childBirthRecordId = childBirthRecordId;
	}

	public Integer getDiseaseScreeningId() {
		return this.diseaseScreeningId;
	}

	public void setDiseaseScreeningId(Integer diseaseScreeningId) {
		this.diseaseScreeningId = diseaseScreeningId;
	}

}