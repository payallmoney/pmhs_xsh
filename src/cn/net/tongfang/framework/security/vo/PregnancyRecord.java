package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * PregnancyRecord entity. @author MyEclipse Persistence Tools
 */

public class PregnancyRecord implements java.io.Serializable {

	// Fields

	private String id;
	private String healthFileMaternalId;
	private Timestamp recordDate;
	private String record;
	private String dealOpinion;
	private String doctor;
	private String inputPersonId;
	private Timestamp inputDate;

	// Constructors

	/** default constructor */
	public PregnancyRecord() {
	}

	/** minimal constructor */
	public PregnancyRecord(String healthFileMaternalId) {
		this.healthFileMaternalId = healthFileMaternalId;
	}

	/** full constructor */
	public PregnancyRecord(String healthFileMaternalId, Timestamp recordDate,
			String record, String dealOpinion, String doctor,
			String inputPersonId, Timestamp inputDate) {
		this.healthFileMaternalId = healthFileMaternalId;
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

	public String getHealthFileMaternalId() {
		return this.healthFileMaternalId;
	}

	public void setHealthFileMaternalId(String healthFileMaternalId) {
		this.healthFileMaternalId = healthFileMaternalId;
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