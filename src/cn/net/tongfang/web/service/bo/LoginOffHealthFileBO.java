package cn.net.tongfang.web.service.bo;

import java.sql.Timestamp;
import java.util.List;

public class LoginOffHealthFileBO {
	private Integer loginOffReason;
	private String loginOffRemark;
	private Timestamp loginOffDate;
	private List<String> fileNos;
	
	public LoginOffHealthFileBO() {
		super();
	}

	public LoginOffHealthFileBO(Integer loginOffReason, String loginOffRemark,
			Timestamp loginOffDate, List<String> fileNos) {
		super();
		this.loginOffReason = loginOffReason;
		this.loginOffRemark = loginOffRemark;
		this.loginOffDate = loginOffDate;
		this.fileNos = fileNos;
	}

	public Integer getLoginOffReason() {
		return loginOffReason;
	}

	public void setLoginOffReason(Integer loginOffReason) {
		this.loginOffReason = loginOffReason;
	}

	public String getLoginOffRemark() {
		return loginOffRemark;
	}

	public void setLoginOffRemark(String loginOffRemark) {
		this.loginOffRemark = loginOffRemark;
	}

	public Timestamp getLoginOffDate() {
		return loginOffDate;
	}

	public void setLoginOffDate(Timestamp loginOffDate) {
		this.loginOffDate = loginOffDate;
	}

	public List<String> getFileNos() {
		return fileNos;
	}

	public void setFileNos(List<String> fileNos) {
		this.fileNos = fileNos;
	}
	
}
