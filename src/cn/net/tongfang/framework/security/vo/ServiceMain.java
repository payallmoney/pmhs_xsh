package cn.net.tongfang.framework.security.vo;

/**
 * ServiceMain entity. @author MyEclipse Persistence Tools
 */

public class ServiceMain implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String sql;
	private String orderby;
	private String groupby;
	private String svrtype;
	private Boolean islist;

	// Constructors

	/** default constructor */
	public ServiceMain() {
	}

	/** minimal constructor */
	public ServiceMain(Integer id, String name, String sql) {
		this.id = id;
		this.name = name;
		this.sql = sql;
	}

	/** full constructor */
	public ServiceMain(Integer id, String name, String sql, String orderby,
			String groupby, String svrtype, Boolean islist) {
		this.id = id;
		this.name = name;
		this.sql = sql;
		this.orderby = orderby;
		this.groupby = groupby;
		this.svrtype = svrtype;
		this.islist = islist;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSql() {
		return this.sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getOrderby() {
		return this.orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public String getGroupby() {
		return this.groupby;
	}

	public void setGroupby(String groupby) {
		this.groupby = groupby;
	}

	public String getSvrtype() {
		return this.svrtype;
	}

	public void setSvrtype(String svrtype) {
		this.svrtype = svrtype;
	}

	public Boolean getIslist() {
		return this.islist;
	}

	public void setIslist(Boolean islist) {
		this.islist = islist;
	}

}