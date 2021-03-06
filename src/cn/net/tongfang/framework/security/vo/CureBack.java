package cn.net.tongfang.framework.security.vo;

// Generated by Hibernate Tools 3.2.4.GA

import java.sql.Timestamp;

/**
 * CureBack generated by hbm2java
 */
public class CureBack implements java.io.Serializable {

	private String id;
	private String fileNo;
	private Timestamp date;
	private String exportOrg;
	private String receptionDoctor;
	private String result;
	private String recordNumber;
	private String checkResult;
	private String cureAdvice;
	private String doctor;
	private String tel;
	private String inputPersonId;
	private Timestamp inputDate;
	private String fromOrg;	
	private String execDistrictNum;

	public CureBack() {
	}

	public CureBack(String fileNo, Timestamp date, String inputPersonId,
			Timestamp inputDate) {
		this.fileNo = fileNo;
		this.date = date;
		this.inputPersonId = inputPersonId;
		this.inputDate = inputDate;
	}

	public CureBack(String fileNo, Timestamp date, String exportOrg,
			String receptionDoctor, String result, String recordNumber,
			String checkResult, String cureAdvice, String doctor, String tel,
			String inputPersonId, Timestamp inputDate, String fromOrg,
			String execDistrictNum) {
		this.fileNo = fileNo;
		this.date = date;
		this.exportOrg = exportOrg;
		this.receptionDoctor = receptionDoctor;
		this.result = result;
		this.recordNumber = recordNumber;
		this.checkResult = checkResult;
		this.cureAdvice = cureAdvice;
		this.doctor = doctor;
		this.tel = tel;
		this.inputPersonId = inputPersonId;
		this.inputDate = inputDate;
		this.fromOrg = fromOrg;
		this.execDistrictNum = execDistrictNum;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileNo() {
		return this.fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getExportOrg() {
		return this.exportOrg;
	}

	public void setExportOrg(String exportOrg) {
		this.exportOrg = exportOrg;
	}

	public String getReceptionDoctor() {
		return this.receptionDoctor;
	}

	public void setReceptionDoctor(String receptionDoctor) {
		this.receptionDoctor = receptionDoctor;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRecordNumber() {
		return this.recordNumber;
	}

	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}

	public String getCheckResult() {
		return this.checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getCureAdvice() {
		return this.cureAdvice;
	}

	public void setCureAdvice(String cureAdvice) {
		this.cureAdvice = cureAdvice;
	}

	public String getDoctor() {
		return this.doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public String getFromOrg() {
		return this.fromOrg;
	}

	public void setFromOrg(String fromOrg) {
		this.fromOrg = fromOrg;
	}

	public String getExecDistrictNum() {
		return execDistrictNum;
	}

	public void setExecDistrictNum(String execDistrictNum) {
		this.execDistrictNum = execDistrictNum;
	}

}
