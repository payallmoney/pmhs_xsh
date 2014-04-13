package cn.net.tongfang.framework.security.vo;

import java.util.Date;

/**
 * ServiceKey entity. @author MyEclipse Persistence Tools
 */

public class ServiceKey implements java.io.Serializable {

	// Fields

	private Long id;
	private String validkey;
	private Boolean valid;
	private Date datebegin;
	private Date dateend;

	// Constructors

	/** default constructor */
	public ServiceKey() {
	}

	/** minimal constructor */
	public ServiceKey(Long id, String validkey, Boolean valid) {
		this.id = id;
		this.validkey = validkey;
		this.valid = valid;
	}

	/** full constructor */
	public ServiceKey(Long id, String validkey, Boolean valid, Date datebegin,
			Date dateend) {
		this.id = id;
		this.validkey = validkey;
		this.valid = valid;
		this.datebegin = datebegin;
		this.dateend = dateend;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValidkey() {
		return this.validkey;
	}

	public void setValidkey(String validkey) {
		this.validkey = validkey;
	}

	public Boolean getValid() {
		return this.valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public Date getDatebegin() {
		return this.datebegin;
	}

	public void setDatebegin(Date datebegin) {
		this.datebegin = datebegin;
	}

	public Date getDateend() {
		return this.dateend;
	}

	public void setDateend(Date dateend) {
		this.dateend = dateend;
	}

}