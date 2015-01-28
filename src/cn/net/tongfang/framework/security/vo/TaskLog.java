package cn.net.tongfang.framework.security.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * TaskLog entity. @author MyEclipse Persistence Tools
 */

public class TaskLog implements Serializable {

	// Fields

	private String id;
	private Timestamp smsdate;
	private String examname;
	private String fileno;
	private String tel;
	private String msg;
	private Integer status;
    private Timestamp sendtime;
	private String error;
	private String tablename;
	private String tableidvalue;
	private String personname;
	private String querytype;
    private String examid;

	// Constructors
    private String parentid;

	/** default constructor */
	public TaskLog() {
	}

	/** minimal constructor */
	public TaskLog(String id, Timestamp smsdate, String examname,
			String fileno, String tel, String msg, Integer status) {
		this.id = id;
		this.smsdate = smsdate;
		this.examname = examname;
		this.fileno = fileno;
		this.tel = tel;
		this.msg = msg;
		this.status = status;
	}

	// Property accessors

	/** full constructor */
	public TaskLog(String id, Timestamp smsdate, String examname,
			String fileno, String tel, String msg, Integer status,
			Timestamp sendtime, String error, String tablename,
			String tableidvalue, String personname, String querytype) {
		this.id = id;
		this.smsdate = smsdate;
		this.examname = examname;
		this.fileno = fileno;
		this.tel = tel;
		this.msg = msg;
		this.status = status;
		this.sendtime = sendtime;
		this.error = error;
		this.tablename = tablename;
		this.tableidvalue = tableidvalue;
		this.personname = personname;
		this.querytype = querytype;
	}

    public void setStatus(int status) {
        this.status = status;
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getSmsdate() {
		return this.smsdate;
	}

	public void setSmsdate(Timestamp smsdate) {
		this.smsdate = smsdate;
	}

	public String getExamname() {
		return this.examname;
	}

	public void setExamname(String examname) {
		this.examname = examname;
	}

	public String getFileno() {
		return this.fileno;
	}

	public void setFileno(String fileno) {
		this.fileno = fileno;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getSendtime() {
		return this.sendtime;
	}

	public void setSendtime(Timestamp sendtime) {
		this.sendtime = sendtime;
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getTablename() {
		return this.tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getTableidvalue() {
		return this.tableidvalue;
	}

	public void setTableidvalue(String tableidvalue) {
		this.tableidvalue = tableidvalue;
	}

	public String getPersonname() {
		return this.personname;
	}

	public void setPersonname(String personname) {
		this.personname = personname;
	}

	public String getQuerytype() {
		return this.querytype;
	}

	public void setQuerytype(String querytype) {
		this.querytype = querytype;
	}

    public String getExamid() {
        return examid;
    }

    public void setExamid(String examid) {
        this.examid = examid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskLog taskLog = (TaskLog) o;

        if (error != null ? !error.equals(taskLog.error) : taskLog.error != null) return false;
        if (examid != null ? !examid.equals(taskLog.examid) : taskLog.examid != null) return false;
        if (examname != null ? !examname.equals(taskLog.examname) : taskLog.examname != null) return false;
        if (fileno != null ? !fileno.equals(taskLog.fileno) : taskLog.fileno != null) return false;
        if (id != null ? !id.equals(taskLog.id) : taskLog.id != null) return false;
        if (msg != null ? !msg.equals(taskLog.msg) : taskLog.msg != null) return false;
        if (personname != null ? !personname.equals(taskLog.personname) : taskLog.personname != null) return false;
        if (querytype != null ? !querytype.equals(taskLog.querytype) : taskLog.querytype != null) return false;
        if (sendtime != null ? !sendtime.equals(taskLog.sendtime) : taskLog.sendtime != null) return false;
        if (smsdate != null ? !smsdate.equals(taskLog.smsdate) : taskLog.smsdate != null) return false;
        if (status != null ? !status.equals(taskLog.status) : taskLog.status != null) return false;
        if (tableidvalue != null ? !tableidvalue.equals(taskLog.tableidvalue) : taskLog.tableidvalue != null)
            return false;
        if (tablename != null ? !tablename.equals(taskLog.tablename) : taskLog.tablename != null) return false;
        if (tel != null ? !tel.equals(taskLog.tel) : taskLog.tel != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = smsdate != null ? smsdate.hashCode() : 0;
        result = 31 * result + (examname != null ? examname.hashCode() : 0);
        result = 31 * result + (fileno != null ? fileno.hashCode() : 0);
        result = 31 * result + (tel != null ? tel.hashCode() : 0);
        result = 31 * result + (msg != null ? msg.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (sendtime != null ? sendtime.hashCode() : 0);
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (tablename != null ? tablename.hashCode() : 0);
        result = 31 * result + (tableidvalue != null ? tableidvalue.hashCode() : 0);
        result = 31 * result + (personname != null ? personname.hashCode() : 0);
        result = 31 * result + (querytype != null ? querytype.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (examid != null ? examid.hashCode() : 0);
        return result;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }
}