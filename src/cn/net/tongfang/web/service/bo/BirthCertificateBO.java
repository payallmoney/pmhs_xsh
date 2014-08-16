package cn.net.tongfang.web.service.bo;

import cn.net.tongfang.framework.security.vo.BirthCertificate;

public class BirthCertificateBO extends BirthCertificate {
	private Integer type;
	private String saveType;
	private String reasonRemarks;
	private String otherDestroyReason;
	
	private String sourceBirthCertifiId;
	private String destBirthCertifiId;
	private String changeReasons;
	private String birthCertificateYears;
	private String originalBirthAddress;
	private String name2012;
	private String motherName2012;
	private String fatherName2012;
	
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
	public String getSaveType() {
		return saveType;
	}
	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getReasonRemarks() {
		return reasonRemarks;
	}
	public void setReasonRemarks(String reasonRemarks) {
		this.reasonRemarks = reasonRemarks;
	}

	public String getOtherDestroyReason() {
		return otherDestroyReason;
	}
	public void setOtherDestroyReason(String otherDestroyReason) {
		this.otherDestroyReason = otherDestroyReason;
	}
	public String getSourceBirthCertifiId() {
		return sourceBirthCertifiId;
	}
	public void setSourceBirthCertifiId(String sourceBirthCertifiId) {
		this.sourceBirthCertifiId = sourceBirthCertifiId;
	}
	public String getDestBirthCertifiId() {
		return destBirthCertifiId;
	}
	public void setDestBirthCertifiId(String destBirthCertifiId) {
		this.destBirthCertifiId = destBirthCertifiId;
	}
	public String getChangeReasons() {
		return changeReasons;
	}
	public void setChangeReasons(String changeReasons) {
		this.changeReasons = changeReasons;
	}
	public String getBirthCertificateYears() {
		return birthCertificateYears;
	}
	public void setBirthCertificateYears(String birthCertificateYears) {
		this.birthCertificateYears = birthCertificateYears;
	}
	
}
