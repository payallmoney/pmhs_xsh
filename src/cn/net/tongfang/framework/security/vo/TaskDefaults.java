package cn.net.tongfang.framework.security.vo;

/**
 * Created by oyx on 2015-01-28.
 */
public class TaskDefaults {
    private String id;
    private String district;
    private String empcode;
    private Integer level;
    private String urlname;
    private String code;
    private String value;
    private String valuetype;
    private Boolean valid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getEmpcode() {
        return empcode;
    }

    public void setEmpcode(String empcode) {
        this.empcode = empcode;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValuetype() {
        return valuetype;
    }

    public void setValuetype(String valuetype) {
        this.valuetype = valuetype;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskDefaults that = (TaskDefaults) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (district != null ? !district.equals(that.district) : that.district != null) return false;
        if (empcode != null ? !empcode.equals(that.empcode) : that.empcode != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (level != null ? !level.equals(that.level) : that.level != null) return false;
        if (urlname != null ? !urlname.equals(that.urlname) : that.urlname != null) return false;
        if (valid != null ? !valid.equals(that.valid) : that.valid != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (valuetype != null ? !valuetype.equals(that.valuetype) : that.valuetype != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 31 * result + (empcode != null ? empcode.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (urlname != null ? urlname.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (valuetype != null ? valuetype.hashCode() : 0);
        result = 31 * result + (valid != null ? valid.hashCode() : 0);
        return result;
    }
}
