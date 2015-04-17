package cn.net.tongfang.framework.security.vo;

/**
 * ServiceInsertSub entity. @author MyEclipse Persistence Tools
 */

public class ServiceInsertSub implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer insertid;
	private String name;
	private String colname;
	private String type;
	private String colgen;
	private String val;
	private Boolean district;

	// Constructors

	/** default constructor */
	public ServiceInsertSub() {
	}

	/** minimal constructor */
	public ServiceInsertSub(Integer id, Integer insertid, String colname,
			String type) {
		this.id = id;
		this.insertid = insertid;
		this.colname = colname;
		this.type = type;
	}

	/** full constructor */
	public ServiceInsertSub(Integer id, Integer insertid, String name,
			String colname, String type, String colgen, String val,
			Boolean district) {
		this.id = id;
		this.insertid = insertid;
		this.name = name;
		this.colname = colname;
		this.type = type;
		this.colgen = colgen;
		this.val = val;
		this.district = district;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInsertid() {
		return this.insertid;
	}

	public void setInsertid(Integer insertid) {
		this.insertid = insertid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColname() {
		return this.colname;
	}

	public void setColname(String colname) {
		this.colname = colname;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColgen() {
		return this.colgen;
	}

	public void setColgen(String colgen) {
		this.colgen = colgen;
	}

	public String getVal() {
		return this.val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public Boolean getDistrict() {
		return this.district;
	}

	public void setDistrict(Boolean district) {
		this.district = district;
	}

}