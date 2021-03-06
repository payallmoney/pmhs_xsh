package cn.net.tongfang.framework.security.vo;

// Generated by Hibernate Tools 3.2.4.GA

import java.sql.Timestamp;

/**
 * CureSwitch generated by hbm2java
 */
public class CureSwitch implements java.io.Serializable {

	private String id;
	private String fileNo;
	private Timestamp date;
	private String exportOrg;
	private String exportDepartment;
	private String receptionDoctor;
	private String impress;
	private String reason;
	private String history;
	private String cureContent;
	private String doctor;
	private String inputPersonId;
	private Timestamp inputDate;
	private String tel;
	private String name;
	private Integer age;
	private String fromOrg;
	private String sex;
	private Integer orgId;
	private String execDistrictNum;

	public CureSwitch() {
	}

	public CureSwitch(String fileNo, Timestamp date, String inputPersonId,
			Timestamp inputDate) {
		this.fileNo = fileNo;
		this.date = date;
		this.inputPersonId = inputPersonId;
		this.inputDate = inputDate;
	}

	public CureSwitch(String fileNo, Timestamp date, String exportOrg,
			String exportDepartment, String receptionDoctor, String impress,
			String reason, String history, String cureContent, String doctor,
			String inputPersonId, Timestamp inputDate, String tel, String name,
			Integer age, String fromOrg, String sex, Integer orgId,String execDistrictNum) {
		this.fileNo = fileNo;
		this.date = date;
		this.exportOrg = exportOrg;
		this.exportDepartment = exportDepartment;
		this.receptionDoctor = receptionDoctor;
		this.impress = impress;
		this.reason = reason;
		this.history = history;
		this.cureContent = cureContent;
		this.doctor = doctor;
		this.inputPersonId = inputPersonId;
		this.inputDate = inputDate;
		this.tel = tel;
		this.name = name;
		this.age = age;
		this.fromOrg = fromOrg;
		this.sex = sex;
		this.orgId = orgId;
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

	public String getExportDepartment() {
		return this.exportDepartment;
	}

	public void setExportDepartment(String exportDepartment) {
		this.exportDepartment = exportDepartment;
	}

	public String getReceptionDoctor() {
		return this.receptionDoctor;
	}

	public void setReceptionDoctor(String receptionDoctor) {
		this.receptionDoctor = receptionDoctor;
	}

	public String getImpress() {
		return this.impress;
	}

	public void setImpress(String impress) {
		this.impress = impress;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getHistory() {
		return this.history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getCureContent() {
		return this.cureContent;
	}

	public void setCureContent(String cureContent) {
		this.cureContent = cureContent;
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

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getFromOrg() {
		return this.fromOrg;
	}

	public void setFromOrg(String fromOrg) {
		this.fromOrg = fromOrg;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getExecDistrictNum() {
		return execDistrictNum;
	}

	public void setExecDistrictNum(String execDistrictNum) {
		this.execDistrictNum = execDistrictNum;
	}

}
