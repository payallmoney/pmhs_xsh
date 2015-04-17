package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * TaskTask entity. @author MyEclipse Persistence Tools
 */

public class TaskTask implements java.io.Serializable {

	// Fields

	private String id;
	private String taskname;
	private String taskid;
	private Timestamp startdate;
	private Timestamp enddate;
	private Integer num;
	private Integer orgid;
	private String inputpersonid;
	private String districtid;
	private Timestamp inputdate;
	private Integer autorun;
	private Integer hasrun;

	// Constructors

	/** default constructor */
	public TaskTask() {
	}

	/** minimal constructor */
	public TaskTask(String id, String taskid, Timestamp inputdate) {
		this.id = id;
		this.taskid = taskid;
		this.inputdate = inputdate;
	}

	/** full constructor */
	public TaskTask(String id, String taskname, String taskid,
			Timestamp startdate, Timestamp enddate, Integer num, Integer orgid,
			String inputpersonid, String districtid, Timestamp inputdate,
			Integer autorun, Integer hasrun) {
		this.id = id;
		this.taskname = taskname;
		this.taskid = taskid;
		this.startdate = startdate;
		this.enddate = enddate;
		this.num = num;
		this.orgid = orgid;
		this.inputpersonid = inputpersonid;
		this.districtid = districtid;
		this.inputdate = inputdate;
		this.autorun = autorun;
		this.hasrun = hasrun;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskname() {
		return this.taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getTaskid() {
		return this.taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public Timestamp getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Timestamp startdate) {
		this.startdate = startdate;
	}

	public Timestamp getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getOrgid() {
		return this.orgid;
	}

	public void setOrgid(Integer orgid) {
		this.orgid = orgid;
	}

	public String getInputpersonid() {
		return this.inputpersonid;
	}

	public void setInputpersonid(String inputpersonid) {
		this.inputpersonid = inputpersonid;
	}

	public String getDistrictid() {
		return this.districtid;
	}

	public void setDistrictid(String districtid) {
		this.districtid = districtid;
	}

	public Timestamp getInputdate() {
		return this.inputdate;
	}

	public void setInputdate(Timestamp inputdate) {
		this.inputdate = inputdate;
	}

	public Integer getAutorun() {
		return this.autorun;
	}

	public void setAutorun(Integer autorun) {
		this.autorun = autorun;
	}

	public Integer getHasrun() {
		return this.hasrun;
	}

	public void setHasrun(Integer hasrun) {
		this.hasrun = hasrun;
	}

}