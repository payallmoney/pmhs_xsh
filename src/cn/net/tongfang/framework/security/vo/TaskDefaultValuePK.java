package cn.net.tongfang.framework.security.vo;

import java.io.Serializable;

/**
 * Created by oyx on 2015-01-28.
 */
public class TaskDefaultValuePK implements Serializable {
    private String empcode;
    private String name;

    public String getEmpcode() {
        return empcode;
    }

    public void setEmpcode(String empcode) {
        this.empcode = empcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskDefaultValuePK that = (TaskDefaultValuePK) o;

        if (empcode != null ? !empcode.equals(that.empcode) : that.empcode != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = empcode != null ? empcode.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
