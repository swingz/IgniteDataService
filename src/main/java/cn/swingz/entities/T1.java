package cn.swingz.entities;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;

public class T1 implements Serializable {

    @QuerySqlField(index = true)
    private Integer id;

    @QuerySqlField
    private String typeName;

    public T1(Integer id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "T1{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
