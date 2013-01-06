package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * PregnancyRecordChild entity. @author MyEclipse Persistence Tools
 */

public class PregnancyRecordChild implements java.io.Serializable {

	// Fields

	private String id;
	private String healthFileChildrenId;
	private Timestamp recordDate;
	private String record;
	private String dealOpinion;
	private String doctor;
	private String inputPersonId;
	private Timestamp inputDate;

	// Constructors

	/** default constructor */
	public PregnancyRecordChild() {
	}

	/** minimal constructor */
	public PregnancyRecordChild(String healthFileChildrenId) {
		this.healthFileChildrenId = healthFileChildrenId;
	}

	/** full constructor */
	public PregnancyRecordChild(String healthFileChildrenId,
			Timestamp recordDate, String record, String dealOpinion,
			String doctor, String inputPersonId, Timestamp inputDate) {
		this.healthFileChildrenId = healthFileChildrenId;
		this.recordDate = recordDate;
		this.record = record;
		this.dealOpinion = dealOpinion;
		this.doctor = doctor;
		this.inputPersonId = inputPersonId;
		this.inputDate = inputDate;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHealthFileChildrenId() {
		return this.healthFileChildrenId;
	}

	public void setHealthFileChildrenId(String healthFileChildrenId) {
		this.healthFileChildrenId = healthFileChildrenId;
	}

	public Timestamp getRecordDate() {
		return this.recordDate;
	}

	public void setRecordDate(Timestamp recordDate) {
		this.recordDate = recordDate;
	}

	public String getRecord() {
		return this.record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getDealOpinion() {
		return this.dealOpinion;
	}

	public void setDealOpinion(String dealOpinion) {
		this.dealOpinion = dealOpinion;
	}

	public String getDoctor() {
		return this.doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
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

}