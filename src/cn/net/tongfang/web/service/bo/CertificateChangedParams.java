package cn.net.tongfang.web.service.bo;

public class CertificateChangedParams {
	private String certifiId;
	private String beforeCertifiId;
	private String type;
	public String getCertifiId() {
		return certifiId;
	}
	public void setCertifiId(String certifiId) {
		this.certifiId = certifiId;
	}
	public String getBeforeCertifiId() {
		return beforeCertifiId;
	}
	public void setBeforeCertifiId(String beforeCertifiId) {
		this.beforeCertifiId = beforeCertifiId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
