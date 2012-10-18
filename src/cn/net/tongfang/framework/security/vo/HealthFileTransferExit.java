package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * HealthFileTransferExit entity. @author MyEclipse Persistence Tools
 */

public class HealthFileTransferExit implements java.io.Serializable {

	// Fields

	private String id;
	private String healthFileTransferId;
	private String exitReasion;
	private String inputPersonId;
	private Timestamp inputDate;

	// Constructors

	/** default constructor */
	public HealthFileTransferExit() {
	}

	/** minimal constructor */
	public HealthFileTransferExit(String healthFileTransferId,
			String exitReasion) {
		this.healthFileTransferId = healthFileTransferId;
		this.exitReasion = exitReasion;
	}

	/** full constructor */
	public HealthFileTransferExit(String healthFileTransferId,
			String exitReasion, String inputPersonId, Timestamp inputDate) {
		this.healthFileTransferId = healthFileTransferId;
		this.exitReasion = exitReasion;
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

	public String getHealthFileTransferId() {
		return this.healthFileTransferId;
	}

	public void setHealthFileTransferId(String healthFileTransferId) {
		this.healthFileTransferId = healthFileTransferId;
	}

	public String getExitReasion() {
		return this.exitReasion;
	}

	public void setExitReasion(String exitReasion) {
		this.exitReasion = exitReasion;
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