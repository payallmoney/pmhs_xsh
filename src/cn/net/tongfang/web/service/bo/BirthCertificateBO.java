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
	
}
