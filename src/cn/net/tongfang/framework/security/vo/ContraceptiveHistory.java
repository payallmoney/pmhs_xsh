package cn.net.tongfang.framework.security.vo;

/**
 * ContraceptiveHistory entity. @author MyEclipse Persistence Tools
 */

public class ContraceptiveHistory implements java.io.Serializable {

	// Fields

	private String id;
	private String firstVistBeforeBornId;
	private Integer contraceptiveId;

	// Constructors

	/** default constructor */
	public ContraceptiveHistory() {
	}

	/** full constructor */
	public ContraceptiveHistory(String firstVistBeforeBornId,
			Integer contraceptiveId) {
		this.firstVistBeforeBornId = firstVistBeforeBornId;
		this.contraceptiveId = contraceptiveId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstVistBeforeBornId() {
		return this.firstVistBeforeBornId;
	}

	public void setFirstVistBeforeBornId(String firstVistBeforeBornId) {
		this.firstVistBeforeBornId = firstVistBeforeBornId;
	}

	public Integer getContraceptiveId() {
		return this.contraceptiveId;
	}

	public void setContraceptiveId(Integer contraceptiveId) {
		this.contraceptiveId = contraceptiveId;
	}

}