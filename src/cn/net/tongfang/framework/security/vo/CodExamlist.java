package cn.net.tongfang.framework.security.vo;

/**
 * CodExamlist entity. @author MyEclipse Persistence Tools
 */

public class CodExamlist implements java.io.Serializable {

	// Fields

	private String examname;
	private String tablename;
	private Long ord;
	private Boolean hascount;
	private String countcol;
	private String datecol;
	private String namerule;
	private String queryMethod;
	private String html;
	private String listcol;
	private String datetypecol;

	// Constructors

	/** default constructor */
	public CodExamlist() {
	}

	/** minimal constructor */
	public CodExamlist(String examname, String tablename, Long ord,
			Boolean hascount, String datecol, String queryMethod) {
		this.examname = examname;
		this.tablename = tablename;
		this.ord = ord;
		this.hascount = hascount;
		this.datecol = datecol;
		this.queryMethod = queryMethod;
	}

	/** full constructor */
	public CodExamlist(String examname, String tablename, Long ord,
			Boolean hascount, String countcol, String datecol, String namerule,
			String queryMethod, String html, String listcol, String datetypecol) {
		this.examname = examname;
		this.tablename = tablename;
		this.ord = ord;
		this.hascount = hascount;
		this.countcol = countcol;
		this.datecol = datecol;
		this.namerule = namerule;
		this.queryMethod = queryMethod;
		this.html = html;
		this.listcol = listcol;
		this.datetypecol = datetypecol;
	}

	// Property accessors

	public String getExamname() {
		return this.examname;
	}

	public void setExamname(String examname) {
		this.examname = examname;
	}

	public String getTablename() {
		return this.tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public Long getOrd() {
		return this.ord;
	}

	public void setOrd(Long ord) {
		this.ord = ord;
	}

	public Boolean getHascount() {
		return this.hascount;
	}

	public void setHascount(Boolean hascount) {
		this.hascount = hascount;
	}

	public String getCountcol() {
		return this.countcol;
	}

	public void setCountcol(String countcol) {
		this.countcol = countcol;
	}

	public String getDatecol() {
		return this.datecol;
	}

	public void setDatecol(String datecol) {
		this.datecol = datecol;
	}

	public String getNamerule() {
		return this.namerule;
	}

	public void setNamerule(String namerule) {
		this.namerule = namerule;
	}

	public String getQueryMethod() {
		return this.queryMethod;
	}

	public void setQueryMethod(String queryMethod) {
		this.queryMethod = queryMethod;
	}

	public String getHtml() {
		return this.html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getListcol() {
		return this.listcol;
	}

	public void setListcol(String listcol) {
		this.listcol = listcol;
	}

	public String getDatetypecol() {
		return this.datetypecol;
	}

	public void setDatetypecol(String datetypecol) {
		this.datetypecol = datetypecol;
	}

}