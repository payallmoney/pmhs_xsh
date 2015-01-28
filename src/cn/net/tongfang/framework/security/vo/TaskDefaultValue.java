package cn.net.tongfang.framework.security.vo;

/**
 * Created by oyx on 2015-01-28.
 */
public class TaskDefaultValue {
    private String empcode;
    private String name;
    private String urlname;
    private String content;

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

    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskDefaultValue that = (TaskDefaultValue) o;

        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (empcode != null ? !empcode.equals(that.empcode) : that.empcode != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (urlname != null ? !urlname.equals(that.urlname) : that.urlname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = empcode != null ? empcode.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (urlname != null ? urlname.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
