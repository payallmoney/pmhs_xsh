package cn.net.tongfang.framework.security.vo;

/**
 * SmsSendTargetOther entity. @author MyEclipse Persistence Tools
 */

public class SmsSendTargetOther implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String tel;
	private String districtNumber;
	private Integer type;
	private Integer isTest;

	// Constructors

	/** default constructor */
	public SmsSendTargetOther() {
	}

	/** full constructor */
	public SmsSendTargetOther(String id, String name, String tel,
			String districtNumber, Integer type, Integer isTest) {
		this.id = id;
		this.name = name;
		this.tel = tel;
		this.districtNumber = districtNumber;
		this.type = type;
		this.isTest = isTest;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getDistrictNumber() {
		return this.districtNumber;
	}

	public void setDistrictNumber(String districtNumber) {
		this.districtNumber = districtNumber;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsTest() {
		return isTest;
	}

	public void setIsTest(Integer isTest) {
		this.isTest = isTest;
	}
	
	

}