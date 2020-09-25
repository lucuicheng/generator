package com.lucuicheng.plugin.model;

/**
 * Created by on 2016/1/14.
 */
public class Field {
    private String column;
    private String field;
    private String javaType;
    private String jdbcType;
    private String sqlAlchemyType;
    private String key;
    private String comment;

    public Field(String column, String field, String javaType, String jdbcType, String sqlAlchemyType, String key, String comment) {
        this.column = column;
        this.field = field;
        this.javaType = javaType;
        this.jdbcType = jdbcType;
        this.sqlAlchemyType = sqlAlchemyType;
        this.key = key;
        this.comment = comment;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSqlAlchemyType() {
        return sqlAlchemyType;
    }

    public void setSqlAlchemyType(String sqlAlchemyType) {
        this.sqlAlchemyType = sqlAlchemyType;
    }
}
