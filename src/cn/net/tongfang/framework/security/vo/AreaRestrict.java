package cn.net.tongfang.framework.security.vo;

/**
 * AreaRestrict entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AreaRestrict implements java.io.Serializable {

	// Fields

	private String id;
	private String sw;
	private String ne;
	private String centerPoint;
	private String zoomLevel;

	// Constructors

	/** default constructor */
	public AreaRestrict() {
	}

	/** full constructor */
	public AreaRestrict(String id, String sw, String ne, String centerPoint,
			String zoomLevel) {
		this.id = id;
		this.sw = sw;
		this.ne = ne;
		this.centerPoint = centerPoint;
		this.zoomLevel = zoomLevel;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSw() {
		return this.sw;
	}

	public void setSw(String sw) {
		this.sw = sw;
	}

	public String getNe() {
		return this.ne;
	}

	public void setNe(String ne) {
		this.ne = ne;
	}

	public String getCenterPoint() {
		return this.centerPoint;
	}

	public void setCenterPoint(String centerPoint) {
		this.centerPoint = centerPoint;
	}

	public String getZoomLevel() {
		return this.zoomLevel;
	}

	public void setZoomLevel(String zoomLevel) {
		this.zoomLevel = zoomLevel;
	}

}