package cn.net.tongfang.framework.security.vo;

/**
 * ChildrenHeightWeight entity. @author MyEclipse Persistence Tools
 */

public class ChildrenHeightWeight implements java.io.Serializable {

	// Fields

	private String id;
	private String sex;
	private Double height;
	private Double s;
	private Double sub2s;
	private Double sub1s;
	private Double avg;
	private Double add1s;
	private Double add2s;

	// Constructors

	/** default constructor */
	public ChildrenHeightWeight() {
	}

	/** full constructor */
	public ChildrenHeightWeight(String sex, Double height, Double s,
			Double sub2s, Double sub1s, Double avg, Double add1s, Double add2s) {
		this.sex = sex;
		this.height = height;
		this.s = s;
		this.sub2s = sub2s;
		this.sub1s = sub1s;
		this.avg = avg;
		this.add1s = add1s;
		this.add2s = add2s;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Double getHeight() {
		return this.height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getS() {
		return this.s;
	}

	public void setS(Double s) {
		this.s = s;
	}

	public Double getSub2s() {
		return this.sub2s;
	}

	public void setSub2s(Double sub2s) {
		this.sub2s = sub2s;
	}

	public Double getSub1s() {
		return this.sub1s;
	}

	public void setSub1s(Double sub1s) {
		this.sub1s = sub1s;
	}

	public Double getAvg() {
		return this.avg;
	}

	public void setAvg(Double avg) {
		this.avg = avg;
	}

	public Double getAdd1s() {
		return this.add1s;
	}

	public void setAdd1s(Double add1s) {
		this.add1s = add1s;
	}

	public Double getAdd2s() {
		return this.add2s;
	}

	public void setAdd2s(Double add2s) {
		this.add2s = add2s;
	}

}