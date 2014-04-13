package cn.net.tongfang.framework.security.vo;

/**
 * ServiceUpdate entity. @author MyEclipse Persistence Tools
 */

public class ServiceUpdate implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mainid;
	private String tablename;
	private String wherecol;
	private String updatecol;

	// Constructors

	/** default constructor */
	public ServiceUpdate() {
	}

	/** minimal constructor */
	public ServiceUpdate(Integer id, Integer mainid) {
		this.id = id;
		this.mainid = mainid;
	}

	/** full constructor */
	public ServiceUpdate(Integer id, Integer mainid, String tablename,
			String wherecol, String updatecol) {
		this.id = id;
		this.mainid = mainid;
		this.tablename = tablename;
		this.wherecol = wherecol;
		this.updatecol = updatecol;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMainid() {
		return this.mainid;
	}

	public void setMainid(Integer mainid) {
		this.mainid = mainid;
	}

	public String getTablename() {
		return this.tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getWherecol() {
		return this.wherecol;
	}

	public void setWherecol(String wherecol) {
		this.wherecol = wherecol;
	}

	public String getUpdatecol() {
		return this.updatecol;
	}

	public void setUpdatecol(String updatecol) {
		this.updatecol = updatecol;
	}

}