package cn.net.tongfang.framework.security.vo;

/**
 * CodTelUpdateCol entity. @author MyEclipse Persistence Tools
 */

public class CodTelUpdateCol implements java.io.Serializable {

	// Fields

	private String id;
	private String tablename;
	private String col;
	private String name;
	private Integer ord;

	// Constructors

	/** default constructor */
	public CodTelUpdateCol() {
	}

	/** full constructor */
	public CodTelUpdateCol(String tablename, String col, String name,
			Integer ord) {
		this.tablename = tablename;
		this.col = col;
		this.name = name;
		this.ord = ord;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTablename() {
		return this.tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getCol() {
		return this.col;
	}

	public void setCol(String col) {
		this.col = col;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrd() {
		return this.ord;
	}

	public void setOrd(Integer ord) {
		this.ord = ord;
	}

}