package cn.net.tongfang.framework.security.vo;

/**
 * HealthfileFamily entity. @author MyEclipse Persistence Tools
 */

public class HealthfileFamily implements java.io.Serializable {

	// Fields

	private String fileno;
	private String familyid;
	private String relation;

	// Constructors

	/** default constructor */
	public HealthfileFamily() {
	}

	/** full constructor */
	public HealthfileFamily(String fileno, String familyid, String relation) {
		this.fileno = fileno;
		this.familyid = familyid;
		this.relation = relation;
	}

	// Property accessors

	public String getFileno() {
		return this.fileno;
	}

	public void setFileno(String fileno) {
		this.fileno = fileno;
	}

	public String getFamilyid() {
		return this.familyid;
	}

	public void setFamilyid(String familyid) {
		this.familyid = familyid;
	}

	public String getRelation() {
		return this.relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

}