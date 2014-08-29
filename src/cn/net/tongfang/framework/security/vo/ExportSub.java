package cn.net.tongfang.framework.security.vo;

/**
 * ExportSub entity. @author MyEclipse Persistence Tools
 */

public class ExportSub implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mainid;
	private String name;
	private String colstr;
	private String type;
	private Integer ord;
	private String code;
	private Boolean display;

	// Constructors

	/** default constructor */
	public ExportSub() {
	}

	/** minimal constructor */
	public ExportSub(Integer id, Integer mainid, String name, String colstr,
			String type ) {
		this.id = id;
		this.mainid = mainid;
		this.name = name;
		this.colstr = colstr;
		this.type = type;
	}

	/** full constructor */
	public ExportSub(Integer id, Integer mainid, String name, String colstr,
			String type, Integer ord, String code, Boolean display) {
		this.id = id;
		this.mainid = mainid;
		this.name = name;
		this.colstr = colstr;
		this.type = type;
		this.ord = ord;
		this.code = code;
		this.display = display;
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

	public Integer getOrd() {
		return this.ord;
	}

	public void setOrd(Integer ord) {
		this.ord = ord;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean getDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}
	
	

}