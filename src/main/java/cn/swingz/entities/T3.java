package cn.swingz.entities;

import java.io.Serializable;

public class T3 implements Serializable {

    private Integer parentId;

    private String key;

    private String value;

    private String remarks;

    public T3(Integer parentId, String key, String value, String remarks) {
        this.parentId = parentId;
        this.key = key;
        this.value = value;
        this.remarks = remarks;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "T3{" +
                "parentId=" + parentId +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
