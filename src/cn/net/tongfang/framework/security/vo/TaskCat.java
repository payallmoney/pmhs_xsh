package cn.net.tongfang.framework.security.vo;

/**
 * Created by oyx on 2015-01-25.
 */
public class TaskCat {
    private String id;
    private String name;
    private Integer ord;

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

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskCat taskCat = (TaskCat) o;

        if (id != null ? !id.equals(taskCat.id) : taskCat.id != null) return false;
        if (name != null ? !name.equals(taskCat.name) : taskCat.name != null) return false;
        if (ord != null ? !ord.equals(taskCat.ord) : taskCat.ord != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (ord != null ? ord.hashCode() : 0);
        return result;
    }
}
