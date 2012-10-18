package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * FinishGestation entity. @author MyEclipse Persistence Tools
 */

public class FinishGestation implements java.io.Serializable {

	// Fields

	private String id;
	private String healthFileMaternalId;
	private String finishReason;
	private Timestamp finishDate;
	private String inputPersonId;
	private Timestamp inputDate;

	// Constructors

	/** default constructor */
	public FinishGestation() {
	}

	/** minimal constructor */
	public FinishGestation(String healthFileMaternalId, String finishReason) {
		this.healthFileMaternalId = healthFileMaternalId;
		this.finishReason = finishReason;
	}

	/** full constructor */
	public FinishGestation(String healthFileMaternalId, String finishReason,
			Timestamp finishDate, String inputPersonId, Timestamp inputDate) {
		this.healthFileMaternalId = healthFileMaternalId;
		this.finishReason = finishReason;
		this.finishDate = finishDate;
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

	public String getFinishReason() {
		return finishReason;
	}

	public void setFinishReason(String finishReason) {
		this.finishReason = finishReason;
	}

	public Timestamp getFinishDate() {
		return this.finishDate;
	}

	public void setFinishDate(Timestamp finishDate) {
		this.finishDate = finishDate;
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