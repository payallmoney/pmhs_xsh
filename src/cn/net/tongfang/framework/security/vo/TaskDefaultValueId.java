package cn.net.tongfang.framework.security.vo;

/**
 * TaskDefaultValueId entity. @author MyEclipse Persistence Tools
 */

public class TaskDefaultValueId implements java.io.Serializable {

	// Fields

	private String empcode;
	private String name;

	// Constructors

	/** default constructor */
	public TaskDefaultValueId() {
	}

	/** full constructor */
	public TaskDefaultValueId(String empcode, String name) {
		this.empcode = empcode;
		this.name = name;
	}

	// Property accessors

	public String getEmpcode() {
		return this.empcode;
	}

	public void setEmpcode(String empcode) {
		this.empcode = empcode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TaskDefaultValueId))
			return false;
		TaskDefaultValueId castOther = (TaskDefaultValueId) other;

		return ((this.getEmpcode() == castOther.getEmpcode()) || (this
				.getEmpcode() != null && castOther.getEmpcode() != null && this
				.getEmpcode().equals(castOther.getEmpcode())))
				&& ((this.getName() == castOther.getName()) || (this.getName() != null
						&& castOther.getName() != null && this.getName()
						.equals(castOther.getName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEmpcode() == null ? 0 : this.getEmpcode().hashCode());
		result = 37 * result
				+ (getName() == null ? 0 : this.getName().hashCode());
		return result;
	}

}