package cn.net.tongfang.framework.security.vo;

/**
 * SmsSendTarget entity. @author MyEclipse Persistence Tools
 */

public class SmsSendTarget implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String tablename;
	private String msg;
	private String wherestr;
	private String tableidname;

	// Constructors

	/** default constructor */
	public SmsSendTarget() {
	}

	/** full constructor */
	public SmsSendTarget(String id, String name, String tablename, String msg,
			String wherestr, String tableidname) {
		this.id = id;
		this.name = name;
		this.tablename = tablename;
		this.msg = msg;
		this.wherestr = wherestr;
		this.tableidname = tableidname;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTablename() {
		return this.tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getWherestr() {
		return this.wherestr;
	}

	public void setWherestr(String wherestr) {
		this.wherestr = wherestr;
	}

	public String getTableidname() {
		return this.tableidname;
	}

	public void setTableidname(String tableidname) {
		this.tableidname = tableidname;
	}

}