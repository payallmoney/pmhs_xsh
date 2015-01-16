package cn.net.tongfang.framework.security.vo;

import java.sql.Timestamp;

/**
 * Created by oyx on 2015-01-16.
 */
public class TaskRule {
    private String id;
    private String name;
    private String tablename;
    private String col;
    private int days;
    private Timestamp optdate;
    private String msg;
    private String wherestr;
    private String tableidname;
    private String type;
    private String rulestr;
    private String teltable;
    private String teljoinstr;
    private String telcol;
    private String idtype;
    private String inputpage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public Timestamp getOptdate() {
        return optdate;
    }

    public void setOptdate(Timestamp optdate) {
        this.optdate = optdate;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getWherestr() {
        return wherestr;
    }

    public void setWherestr(String wherestr) {
        this.wherestr = wherestr;
    }

    public String getTableidname() {
        return tableidname;
    }

    public void setTableidname(String tableidname) {
        this.tableidname = tableidname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRulestr() {
        return rulestr;
    }

    public void setRulestr(String rulestr) {
        this.rulestr = rulestr;
    }

    public String getTeltable() {
        return teltable;
    }

    public void setTeltable(String teltable) {
        this.teltable = teltable;
    }

    public String getTeljoinstr() {
        return teljoinstr;
    }

    public void setTeljoinstr(String teljoinstr) {
        this.teljoinstr = teljoinstr;
    }

    public String getTelcol() {
        return telcol;
    }

    public void setTelcol(String telcol) {
        this.telcol = telcol;
    }

    public String getIdtype() {
        return idtype;
    }

    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }

    public String getInputpage() {
        return inputpage;
    }

    public void setInputpage(String inputpage) {
        this.inputpage = inputpage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskRule taskRule = (TaskRule) o;

        if (days != taskRule.days) return false;
        if (col != null ? !col.equals(taskRule.col) : taskRule.col != null) return false;
        if (id != null ? !id.equals(taskRule.id) : taskRule.id != null) return false;
        if (idtype != null ? !idtype.equals(taskRule.idtype) : taskRule.idtype != null) return false;
        if (inputpage != null ? !inputpage.equals(taskRule.inputpage) : taskRule.inputpage != null) return false;
        if (msg != null ? !msg.equals(taskRule.msg) : taskRule.msg != null) return false;
        if (name != null ? !name.equals(taskRule.name) : taskRule.name != null) return false;
        if (optdate != null ? !optdate.equals(taskRule.optdate) : taskRule.optdate != null) return false;
        if (rulestr != null ? !rulestr.equals(taskRule.rulestr) : taskRule.rulestr != null) return false;
        if (tableidname != null ? !tableidname.equals(taskRule.tableidname) : taskRule.tableidname != null)
            return false;
        if (tablename != null ? !tablename.equals(taskRule.tablename) : taskRule.tablename != null) return false;
        if (telcol != null ? !telcol.equals(taskRule.telcol) : taskRule.telcol != null) return false;
        if (teljoinstr != null ? !teljoinstr.equals(taskRule.teljoinstr) : taskRule.teljoinstr != null) return false;
        if (teltable != null ? !teltable.equals(taskRule.teltable) : taskRule.teltable != null) return false;
        if (type != null ? !type.equals(taskRule.type) : taskRule.type != null) return false;
        if (wherestr != null ? !wherestr.equals(taskRule.wherestr) : taskRule.wherestr != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (tablename != null ? tablename.hashCode() : 0);
        result = 31 * result + (col != null ? col.hashCode() : 0);
        result = 31 * result + days;
        result = 31 * result + (optdate != null ? optdate.hashCode() : 0);
        result = 31 * result + (msg != null ? msg.hashCode() : 0);
        result = 31 * result + (wherestr != null ? wherestr.hashCode() : 0);
        result = 31 * result + (tableidname != null ? tableidname.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (rulestr != null ? rulestr.hashCode() : 0);
        result = 31 * result + (teltable != null ? teltable.hashCode() : 0);
        result = 31 * result + (teljoinstr != null ? teljoinstr.hashCode() : 0);
        result = 31 * result + (telcol != null ? telcol.hashCode() : 0);
        result = 31 * result + (idtype != null ? idtype.hashCode() : 0);
        result = 31 * result + (inputpage != null ? inputpage.hashCode() : 0);
        return result;
    }
}
