package cn.net.tongfang.framework.security.vo;

/**
 * ExamExamcfg entity. @author MyEclipse Persistence Tools
 */

public class ExamExamcfg implements java.io.Serializable {

	// Fields

	private String id;
	private String examname;
	private String itemname;
	private String item;

	// Constructors

	/** default constructor */
	public ExamExamcfg() {
	}

	/** minimal constructor */
	public ExamExamcfg(String id, String examname, String itemname) {
		this.id = id;
		this.examname = examname;
		this.itemname = itemname;
	}

	/** full constructor */
	public ExamExamcfg(String id, String examname, String itemname, String item) {
		this.id = id;
		this.examname = examname;
		this.itemname = itemname;
		this.item = item;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExamname() {
		return this.examname;
	}

	public void setExamname(String examname) {
		this.examname = examname;
	}

	public String getItemname() {
		return this.itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

}