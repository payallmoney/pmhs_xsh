package cn.net.tongfang.web.service.bo;

import java.sql.Timestamp;

public class TransferHealthfileInfo {
	private String fromFileNo;
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
	
	public TransferHealthfileInfo() {
		super();
	}
	
	public TransferHealthfileInfo(String fromFileNo, String name, String sex,
			Timestamp birthday, String idNumber, String fromTown,
			String fromVillage, String fromBuildDoctor, String fromBuildUnit,
			String fromBuildPerson, String residenceAddress, String address,
			String fromDistrictNumber) {
		super();
		this.fromFileNo = fromFileNo;
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
		this.fromDistrictNumber = fromDistrictNumber;
	}

	public String getFromBuildPerson() {
		return fromBuildPerson;
	}

	public void setFromBuildPerson(String fromBuildPerson) {
		this.fromBuildPerson = fromBuildPerson;
	}

	public String getResidenceAddress() {
		return residenceAddress;
	}

	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFromFileNo() {
		return fromFileNo;
	}
	public void setFromFileNo(String fromFileNo) {
		this.fromFileNo = fromFileNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Timestamp getBirthday() {
		return birthday;
	}
	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getFromTown() {
		return fromTown;
	}
	public void setFromTown(String fromTown) {
		this.fromTown = fromTown;
	}
	public String getFromVillage() {
		return fromVillage;
	}
	public void setFromVillage(String fromVillage) {
		this.fromVillage = fromVillage;
	}
	public String getFromBuildDoctor() {
		return fromBuildDoctor;
	}
	public void setFromBuildDoctor(String fromBuildDoctor) {
		this.fromBuildDoctor = fromBuildDoctor;
	}
	public String getFromBuildUnit() {
		return fromBuildUnit;
	}
	public void setFromBuildUnit(String fromBuildUnit) {
		this.fromBuildUnit = fromBuildUnit;
	}

	public String getFromDistrictNumber() {
		return fromDistrictNumber;
	}

	public void setFromDistrictNumber(String fromDistrictNumber) {
		this.fromDistrictNumber = fromDistrictNumber;
	}
	
}
