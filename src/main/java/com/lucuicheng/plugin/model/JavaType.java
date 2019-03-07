package com.lucuicheng.plugin.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucuicheng on 2016/6/28.
 */
public class JavaType {

    private static Map<String, String> relation;
    private static JavaType type;

    private JavaType(Map<String, String> relation) {
        JavaType.relation = relation;
    }

    public static Map<String, String> getRelation() {
        return relation;
    }

    public static JavaType getIntance() {
        if(type == null) {
            Map<String, String> relation = new HashMap<String,String>();
            //mysql
            relation.put("BIGINT", "Long");
            relation.put("TINYINT", "Byte");
            relation.put("SMALLINT", "Short");
            relation.put("MEDIUMINT", "Integer");
            relation.put("INTEGER", "Integer");
            relation.put("INT", "Integer");
            relation.put("FLOAT", "Float");
            relation.put("DOUBLE", "Double");
            relation.put("DECIMAL", "BigDecimal");
            relation.put("NUMERIC", "BigDecimal");
            relation.put("CHAR", "String");
            relation.put("VARCHAR", "String");
            relation.put("TINYBLOB", "byte[]");//DataTypeWithBLOBs.byte[]
            relation.put("TINYTEXT", "String");
            relation.put("BLOB", "byte[]");//DataTypeWithBLOBs.byte[]
            relation.put("TEXT", "String");//DataTypeWithBLOBs.String
            relation.put("MEDIUMBLOB", "byte[]");//DataTypeWithBLOBs.byte[]
            relation.put("MEDIUMTEXT", "String");//DataTypeWithBLOBs.String
            relation.put("LONGBLOB", "byte[]");//DataTypeWithBLOBs.byte[]
            relation.put("LONGTEXT", "String");//DataTypeWithBLOBs.String
            relation.put("DATE", "Date");
            relation.put("TIME", "Date");
            relation.put("YEAR", "Date");
            relation.put("DATETIME", "Date");
            relation.put("TIMESTAMP", "Date");
            relation.put("ENUM", "Enum");

            //oracle
            //sql server
            type = new JavaType(relation);
        }
        return type;
    }

    public static String toJavaType(String type) {
        int index;
        if((index = type.indexOf("(")) > -1) {
            type = type.substring(0, index);
        }
        type = type.toUpperCase();
        return JavaType.getRelation().get(type);
    }

}
