package com.lucuicheng.plugin.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucuicheng on 2016/6/28.
 */
public class JdbcType {

    private static Map<String, String> relation;
    private static JdbcType type;

    private JdbcType(Map<String, String> relation) {
        JdbcType.relation = relation;
    }

    public static Map<String, String> getRelation() {
        return relation;
    }

    public static JdbcType getIntance() {
        if(type == null) {
            Map<String, String> relation = new HashMap<String,String>();
            //mysql
            relation.put("BIGINT", "BIGINT");
            relation.put("TINYINT", "TINYINT");
            relation.put("SMALLINT", "SMALLINT");
            relation.put("MEDIUMINT", "INTEGER");
            relation.put("INTEGER", "INTEGER");
            relation.put("INT", "INTEGER");
            relation.put("FLOAT", "REAL");
            relation.put("DOUBLE", "DOUBLE");
            relation.put("DECIMAL", "DECIMAL");
            relation.put("NUMERIC", "DECIMAL");
            relation.put("CHAR", "CHAR");
            relation.put("VARCHAR", "VARCHAR");
            relation.put("TINYBLOB", "BINARY");
            relation.put("TINYTEXT", "VARCHAR");
            relation.put("BLOB", "BINARY");
            relation.put("TEXT", "LONGVARCHAR");
            relation.put("MEDIUMBLOB", "LONGVARBINARY");
            relation.put("MEDIUMTEXT", "LONGVARCHAR");
            relation.put("LONGBLOB", "LONGVARBINARY");
            relation.put("LONGTEXT", "LONGVARCHAR");
            relation.put("DATE", "DATE");
            relation.put("TIME", "TIME");
            relation.put("YEAR", "DATE");
            relation.put("DATETIME", "TIMESTAMP");
            relation.put("TIMESTAMP", "TIMESTAMP");
            relation.put("ENUM", "Enum");

            //oracle
            //sql server
            type = new JdbcType(relation);
        }
        return type;
    }

    public static String toJdbcType(String type) {
        int index;
        if((index = type.indexOf("(")) > -1) {
            type = type.substring(0, index);
        }
        type = type.toUpperCase();
        return JdbcType.getRelation().get(type);
    }

}
