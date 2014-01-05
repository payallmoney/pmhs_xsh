package cn.net.tongfang.framework.security.vo;

/**
 * FreeMain entity. @author MyEclipse Persistence Tools
 */

public class FreeMain implements java.io.Serializable {

	// Fields

	private FreeMainId id;
	private Integer maxnum;
	private Integer leftnum;

	// Constructors

	/** default constructor */
	public FreeMain() {
	}

	/** full constructor */
	public FreeMain(FreeMainId id, Integer maxnum, Integer leftnum) {
		this.id = id;
		this.maxnum = maxnum;
		this.leftnum = leftnum;
	}

	// Property accessors

	public FreeMainId getId() {
		return this.id;
	}

	public void setId(FreeMainId id) {
		this.id = id;
	}

	public Integer getMaxnum() {
		return this.maxnum;
	}

	public void setMaxnum(Integer maxnum) {
		this.maxnum = maxnum;
	}

	public Integer getLeftnum() {
		return this.leftnum;
	}

	public void setLeftnum(Integer leftnum) {
		this.leftnum = leftnum;
	}

}