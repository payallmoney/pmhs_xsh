package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * HealthFileLoginOffExit entity. @author MyEclipse Persistence Tools
 */

public class HealthFileLoginOffExit implements java.io.Serializable {

	// Fields

	private String id;
	private String healthFileLoginOffId;
	private String loginOffExitReasion;
	private String inputPersonId;
	private Timestamp inputDate;

	// Constructors

	/** default constructor */
	public HealthFileLoginOffExit() {
	}

	/** minimal constructor */
	public HealthFileLoginOffExit(String healthFileLoginOffId,
			String loginOffExitReasion) {
		this.healthFileLoginOffId = healthFileLoginOffId;
		this.loginOffExitReasion = loginOffExitReasion;
	}

	/** full constructor */
	public HealthFileLoginOffExit(String healthFileLoginOffId,
			String loginOffExitReasion, String inputPersonId, Timestamp inputDate) {
		this.healthFileLoginOffId = healthFileLoginOffId;
		this.loginOffExitReasion = loginOffExitReasion;
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

	public String getHealthFileLoginOffId() {
		return this.healthFileLoginOffId;
	}

	public void setHealthFileLoginOffId(String healthFileLoginOffId) {
		this.healthFileLoginOffId = healthFileLoginOffId;
	}

	public String getLoginOffExitReasion() {
		return loginOffExitReasion;
	}

	public void setLoginOffExitReasion(String loginOffExitReasion) {
		this.loginOffExitReasion = loginOffExitReasion;
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