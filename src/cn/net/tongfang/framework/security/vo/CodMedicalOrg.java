package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * CodMedicalOrg entity. @author MyEclipse Persistence Tools
 */

public class CodMedicalOrg implements java.io.Serializable {

	// Fields

	private Long id;
	private String orgname;
	private String regaddress;
	private Timestamp startdate;
	private Timestamp enddate;
	private String legalman;
	private String tel;

	// Constructors

	/** default constructor */
	public CodMedicalOrg() {
	}

	/** full constructor */
	public CodMedicalOrg(Long id, String orgname, String regaddress,
			Timestamp startdate, Timestamp enddate, String legalman, String tel) {
		this.id = id;
		this.orgname = orgname;
		this.regaddress = regaddress;
		this.startdate = startdate;
		this.enddate = enddate;
		this.legalman = legalman;
		this.tel = tel;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgname() {
		return this.orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getRegaddress() {
		return this.regaddress;
	}

	public void setRegaddress(String regaddress) {
		this.regaddress = regaddress;
	}

	public Timestamp getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Timestamp startdate) {
		this.startdate = startdate;
	}

	public Timestamp getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
	}

	public String getLegalman() {
		return this.legalman;
	}

	public void setLegalman(String legalman) {
		this.legalman = legalman;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}