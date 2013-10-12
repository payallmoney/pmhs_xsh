package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * HealthFileHistory entity. @author MyEclipse Persistence Tools
 */

public class HealthFileHistory implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private String sex;
	private String idcard;
	private Timestamp birthday;
	private String address;
	private String raddress;
	private String tel;
	private String xz;
	private String cwh;
	private String jddw;
	private String jdr;
	private String zrys;
	private Timestamp jdrq;

	// Constructors

	/** default constructor */
	public HealthFileHistory() {
	}

	/** minimal constructor */
	public HealthFileHistory(Long id) {
		this.id = id;
	}

	/** full constructor */
	public HealthFileHistory(Long id, String name, String sex, String idcard,
			Timestamp birthday, String address, String raddress, String tel,
			String xz, String cwh, String jddw, String jdr, String zrys,
			Timestamp jdrq) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.idcard = idcard;
		this.birthday = birthday;
		this.address = address;
		this.raddress = raddress;
		this.tel = tel;
		this.xz = xz;
		this.cwh = cwh;
		this.jddw = jddw;
		this.jdr = jdr;
		this.zrys = zrys;
		this.jdrq = jdrq;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Timestamp getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRaddress() {
		return this.raddress;
	}

	public void setRaddress(String raddress) {
		this.raddress = raddress;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getXz() {
		return this.xz;
	}

	public void setXz(String xz) {
		this.xz = xz;
	}

	public String getCwh() {
		return this.cwh;
	}

	public void setCwh(String cwh) {
		this.cwh = cwh;
	}

	public String getJddw() {
		return this.jddw;
	}

	public void setJddw(String jddw) {
		this.jddw = jddw;
	}

	public String getJdr() {
		return this.jdr;
	}

	public void setJdr(String jdr) {
		this.jdr = jdr;
	}

	public String getZrys() {
		return this.zrys;
	}

	public void setZrys(String zrys) {
		this.zrys = zrys;
	}

	public Timestamp getJdrq() {
		return this.jdrq;
	}

	public void setJdrq(Timestamp jdrq) {
		this.jdrq = jdrq;
	}

}