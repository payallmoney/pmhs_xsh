package cn.net.tongfang.framework.security.vo;

/**
 * HealthfileWechat entity. @author MyEclipse Persistence Tools
 */

public class HealthfileWechat implements java.io.Serializable {

	// Fields

	private String fileno;
	private String wechat;
	private String orgwechat;

	// Constructors

	/** default constructor */
	public HealthfileWechat() {
	}

	/** full constructor */
	public HealthfileWechat(String fileno, String wechat, String orgwechat) {
		this.fileno = fileno;
		this.wechat = wechat;
		this.orgwechat = orgwechat;
	}

	// Property accessors

	public String getFileno() {
		return this.fileno;
	}

	public void setFileno(String fileno) {
		this.fileno = fileno;
	}

	public String getWechat() {
		return this.wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getOrgwechat() {
		return this.orgwechat;
	}

	public void setOrgwechat(String orgwechat) {
		this.orgwechat = orgwechat;
	}

}