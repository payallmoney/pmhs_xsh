package cn.net.tongfang.framework.security.vo;

/**
 * CodScoreRule entity. @author MyEclipse Persistence Tools
 */

public class CodScoreRule implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private String standard;
	private String method;
	private String paramurl;
	private Boolean isfileno;
	private String querytable;
	private String querypersoncolumn;
	private String querycolumn;
	private String querydatecolumn;
	private String querywhere;

	// Constructors

	/** default constructor */
	public CodScoreRule() {
	}

	/** minimal constructor */
	public CodScoreRule(Long id, String name, String standard, String method,
			String paramurl, Boolean isfileno) {
		this.id = id;
		this.name = name;
		this.standard = standard;
		this.method = method;
		this.paramurl = paramurl;
		this.isfileno = isfileno;
	}

	/** full constructor */
	public CodScoreRule(Long id, String name, String standard, String method,
			String paramurl, Boolean isfileno, String querytable,
			String querypersoncolumn, String querycolumn, String querydatecolumn,String querywhere) {
		this.id = id;
		this.name = name;
		this.standard = standard;
		this.method = method;
		this.paramurl = paramurl;
		this.isfileno = isfileno;
		this.querytable = querytable;
		this.querypersoncolumn = querypersoncolumn;
		this.querycolumn = querycolumn;
		this.querydatecolumn = querydatecolumn;
		this.querywhere = querywhere;
	}
	
	

	// Property accessors

	public String getQuerywhere() {
		return querywhere;
	}

	public void setQuerywhere(String querywhere) {
		this.querywhere = querywhere;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStandard() {
		return this.standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParamurl() {
		return this.paramurl;
	}

	public void setParamurl(String paramurl) {
		this.paramurl = paramurl;
	}

	public Boolean getIsfileno() {
		return this.isfileno;
	}

	public void setIsfileno(Boolean isfileno) {
		this.isfileno = isfileno;
	}

	public String getQuerytable() {
		return this.querytable;
	}

	public void setQuerytable(String querytable) {
		this.querytable = querytable;
	}

	public String getQuerypersoncolumn() {
		return this.querypersoncolumn;
	}

	public void setQuerypersoncolumn(String querypersoncolumn) {
		this.querypersoncolumn = querypersoncolumn;
	}

	public String getQuerycolumn() {
		return this.querycolumn;
	}

	public void setQuerycolumn(String querycolumn) {
		this.querycolumn = querycolumn;
	}

	public String getQuerydatecolumn() {
		return this.querydatecolumn;
	}

	public void setQuerydatecolumn(String querydatecolumn) {
		this.querydatecolumn = querydatecolumn;
	}

}