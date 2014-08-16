package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * BirthCertificateBefore2012 entity. @author MyEclipse Persistence Tools
 */

public class BirthCertificateBefore2012 implements java.io.Serializable {

	// Fields

	private String id;
	private String birthCertifiId;
	private String name2012;
	private String motherName2012;
	private String fatherName2012;
	private String originalBirthAddress;
	private Integer orgId;
	private String inputPersonId;
	private Timestamp inputDate;

	// Constructors

	/** default constructor */
	public BirthCertificateBefore2012() {
	}

	/** full constructor */
	public BirthCertificateBefore2012(String birthCertifiId, String name2012,
			String motherName2012, String fatherName2012, String originalBirthAddress,
			Integer orgId, String inputPersonId, Timestamp inputDate) {
		this.birthCertifiId = birthCertifiId;
		this.name2012 = name2012;
		this.motherName2012 = motherName2012;
		this.fatherName2012 = fatherName2012;
		this.originalBirthAddress = originalBirthAddress;
		this.orgId = orgId;
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

	public String getBirthCertifiId() {
		return this.birthCertifiId;
	}

	public void setBirthCertifiId(String birthCertifiId) {
		this.birthCertifiId = birthCertifiId;
	}

	public String getName2012() {
		return name2012;
	}

	public void setName2012(String name2012) {
		this.name2012 = name2012;
	}

	public String getMotherName2012() {
		return motherName2012;
	}

	public void setMotherName2012(String motherName2012) {
		this.motherName2012 = motherName2012;
	}

	public String getFatherName2012() {
		return fatherName2012;
	}

	public void setFatherName2012(String fatherName2012) {
		this.fatherName2012 = fatherName2012;
	}

	public String getOriginalBirthAddress() {
		return originalBirthAddress;
	}

	public void setOriginalBirthAddress(String originalBirthAddress) {
		this.originalBirthAddress = originalBirthAddress;
	}

	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
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