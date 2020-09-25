package com.lucuicheng.plugin.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucuicheng on 2016/6/28.
 */
public class SqlAlchemyType {

    private static Map<String, String> relation;
    private static SqlAlchemyType type;

    private SqlAlchemyType(Map<String, String> relation) {
        SqlAlchemyType.relation = relation;
    }

    public static Map<String, String> getRelation() {
        return relation;
    }

    public static SqlAlchemyType getInstance() {
        if (type == null) {
            Map<String, String> relation = new HashMap<String, String>();
            //mysql
            relation.put("BIGINT", "Integer");
            relation.put("TINYINT", "Integer");
            relation.put("SMALLINT", "Integer");
            relation.put("MEDIUMINT", "Integer");
            relation.put("INTEGER", "Integer");
            relation.put("INT", "Integer");
            relation.put("FLOAT", "Float");
            relation.put("DOUBLE", "Float");
            relation.put("DECIMAL", "Float");
            relation.put("NUMERIC", "Float");
            relation.put("CHAR", "String");
            relation.put("VARCHAR", "String");
            relation.put("TINYBLOB", "LargeBinary");//DataTypeWithBLOBs.byte[]
            relation.put("TINYTEXT", "Text");
            relation.put("BLOB", "LargeBinary");//DataTypeWithBLOBs.byte[]
            relation.put("TEXT", "String");//DataTypeWithBLOBs.String
            relation.put("MEDIUMBLOB", "LargeBinary");//DataTypeWithBLOBs.byte[]
            relation.put("MEDIUMTEXT", "Text");//DataTypeWithBLOBs.String
            relation.put("LONGBLOB", "LargeBinary");//DataTypeWithBLOBs.byte[]
            relation.put("LONGTEXT", "Text");//DataTypeWithBLOBs.String
            relation.put("DATE", "DateTime");
            relation.put("TIME", "DateTime");
            relation.put("YEAR", "DateTime");
            relation.put("DATETIME", "DateTime");
            relation.put("TIMESTAMP", "DateTime");
            relation.put("ENUM", "String");

            //oracle
            //sql server
            type = new SqlAlchemyType(relation);
        }
        return type;
    }

    public static String toSqlAlchemyType(String type) {
        int index;
        String size = "";
        String newType = "";
        if ((index = type.indexOf("(")) > -1) {
            newType = type.substring(0, index);
            size = type.substring(index);
        } else {
            newType = type;
        }
        String realType = SqlAlchemyType.getRelation().get(newType.toUpperCase());
        return "String".endsWith(realType) ? realType + size : realType;
    }

}
