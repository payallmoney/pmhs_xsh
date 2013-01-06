package cn.net.tongfang.framework.security.vo;

/**
 * CodModuleMap entity. @author MyEclipse Persistence Tools
 */

public class CodModuleMap implements java.io.Serializable {

	// Fields

	private String id;
	private String mainmoduleid;
	private String submoduleid;

	// Constructors

	/** default constructor */
	public CodModuleMap() {
	}

	/** full constructor */
	public CodModuleMap(String id, String mainmoduleid, String submoduleid) {
		this.id = id;
		this.mainmoduleid = mainmoduleid;
		this.submoduleid = submoduleid;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMainmoduleid() {
		return this.mainmoduleid;
	}

	public void setMainmoduleid(String mainmoduleid) {
		this.mainmoduleid = mainmoduleid;
	}

	public String getSubmoduleid() {
		return this.submoduleid;
	}

	public void setSubmoduleid(String submoduleid) {
		this.submoduleid = submoduleid;
	}

}