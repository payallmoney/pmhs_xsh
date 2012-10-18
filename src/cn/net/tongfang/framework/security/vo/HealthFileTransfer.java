package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * HealthFileTransfer entity. @author MyEclipse Persistence Tools
 */

public class HealthFileTransfer implements java.io.Serializable {

	// Fields

	private String id;
	private String fromFileNo;
	private String transferReason;
	private Timestamp transferTime;
	private String fromOpt;
	private String fromShowOptName;
	private String fromOptOrg;
	private Timestamp inputDate;
	private String name;
	private String sex;
	private Timestamp birthday;
	private String idNumber;
	private String fromTown;
	private String fromVillage;
	private String fromBuildDoctor;
	private String fromBuildUnit;
	private String fromBuildPerson;
	private String residenceAddress;
	private String address;
	private String fromDistrictNumber;
	private String toDistrictNumber;
	private String toFileNo;
	private String isSure;
	private String isSureOpt;
	private Timestamp isSureDate;

	// Constructors

	/** default constructor */
	public HealthFileTransfer() {
	}

	/** minimal constructor */
	public HealthFileTransfer(String fromFileNo, String transferReason) {
		this.fromFileNo = fromFileNo;
		this.transferReason = transferReason;
	}

	/** full constructor */
	public HealthFileTransfer(String fromFileNo, String transferReason,
			Timestamp transferTime, String fromOpt, String fromShowOptName,
			String fromOptOrg, Timestamp inputDate, String name, String sex,
			Timestamp birthday, String idNumber, String fromTown,
			String fromVillage, String fromBuildDoctor, String fromBuildUnit,
			String fromBuildPerson, String residenceAddress, String address,
			String toDistrictNumber, String toFileNo, String isSure,
			String isSureOpt, Timestamp isSureDate,String fromDistrictNumber) {
		this.fromFileNo = fromFileNo;
		this.transferReason = transferReason;
		this.transferTime = transferTime;
		this.fromOpt = fromOpt;
		this.fromShowOptName = fromShowOptName;
		this.fromOptOrg = fromOptOrg;
		this.inputDate = inputDate;
		this.name = name;
		this.sex = sex;
		this.birthday = birthday;
		this.idNumber = idNumber;
		this.fromTown = fromTown;
		this.fromVillage = fromVillage;
		this.fromBuildDoctor = fromBuildDoctor;
		this.fromBuildUnit = fromBuildUnit;
		this.fromBuildPerson = fromBuildPerson;
		this.residenceAddress = residenceAddress;
		this.address = address;
		this.toDistrictNumber = toDistrictNumber;
		this.toFileNo = toFileNo;
		this.isSure = isSure;
		this.isSureOpt = isSureOpt;
		this.isSureDate = isSureDate;
		this.fromDistrictNumber = fromDistrictNumber;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFromFileNo() {
		return this.fromFileNo;
	}

	public void setFromFileNo(String fromFileNo) {
		this.fromFileNo = fromFileNo;
	}

	public String getTransferReason() {
		return this.transferReason;
	}

	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
	}

	public Timestamp getTransferTime() {
		return this.transferTime;
	}

	public void setTransferTime(Timestamp transferTime) {
		this.transferTime = transferTime;
	}

	public String getFromOpt() {
		return this.fromOpt;
	}

	public void setFromOpt(String fromOpt) {
		this.fromOpt = fromOpt;
	}

	public String getFromShowOptName() {
		return this.fromShowOptName;
	}

	public void setFromShowOptName(String fromShowOptName) {
		this.fromShowOptName = fromShowOptName;
	}

	public String getFromOptOrg() {
		return this.fromOptOrg;
	}

	public void setFromOptOrg(String fromOptOrg) {
		this.fromOptOrg = fromOptOrg;
	}

	public Timestamp getInputDate() {
		return this.inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Timestamp getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public String getIdNumber() {
		return this.idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getFromTown() {
		return this.fromTown;
	}

	public void setFromTown(String fromTown) {
		this.fromTown = fromTown;
	}

	public String getFromVillage() {
		return this.fromVillage;
	}

	public void setFromVillage(String fromVillage) {
		this.fromVillage = fromVillage;
	}

	public String getFromBuildDoctor() {
		return this.fromBuildDoctor;
	}

	public void setFromBuildDoctor(String fromBuildDoctor) {
		this.fromBuildDoctor = fromBuildDoctor;
	}

	public String getFromBuildUnit() {
		return this.fromBuildUnit;
	}

	public void setFromBuildUnit(String fromBuildUnit) {
		this.fromBuildUnit = fromBuildUnit;
	}

	public String getFromBuildPerson() {
		return this.fromBuildPerson;
	}

	public void setFromBuildPerson(String fromBuildPerson) {
		this.fromBuildPerson = fromBuildPerson;
	}

	public String getResidenceAddress() {
		return this.residenceAddress;
	}

	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getToDistrictNumber() {
		return this.toDistrictNumber;
	}

	public void setToDistrictNumber(String toDistrictNumber) {
		this.toDistrictNumber = toDistrictNumber;
	}

	public String getToFileNo() {
		return this.toFileNo;
	}

	public void setToFileNo(String toFileNo) {
		this.toFileNo = toFileNo;
	}

	public String getIsSure() {
		return this.isSure;
	}

	public void setIsSure(String isSure) {
		this.isSure = isSure;
	}

	public String getIsSureOpt() {
		return this.isSureOpt;
	}

	public void setIsSureOpt(String isSureOpt) {
		this.isSureOpt = isSureOpt;
	}

	public Timestamp getIsSureDate() {
		return this.isSureDate;
	}

	public void setIsSureDate(Timestamp isSureDate) {
		this.isSureDate = isSureDate;
	}

	public String getFromDistrictNumber() {
		return fromDistrictNumber;
	}

	public void setFromDistrictNumber(String fromDistrictNumber) {
		this.fromDistrictNumber = fromDistrictNumber;
	}

}