package cn.net.tongfang.framework.security.vo;

/**
 * ServiceUpdateSub entity. @author MyEclipse Persistence Tools
 */

public class ServiceUpdateSub implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer updateid;
	private String name;
	private String colstr;
	private String type;

	// Constructors

	/** default constructor */
	public ServiceUpdateSub() {
	}

	/** full constructor */
	public ServiceUpdateSub(Integer id, Integer updateid, String name,
			String colstr, String type) {
		this.id = id;
		this.updateid = updateid;
		this.name = name;
		this.colstr = colstr;
		this.type = type;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUpdateid() {
		return this.updateid;
	}

	public void setUpdateid(Integer updateid) {
		this.updateid = updateid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColstr() {
		return this.colstr;
	}

	public void setColstr(String colstr) {
		this.colstr = colstr;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}