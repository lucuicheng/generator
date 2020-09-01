package com.lucuicheng.plugin.utils;

import com.lucuicheng.plugin.model.Field;
import com.lucuicheng.plugin.model.JavaType;
import com.lucuicheng.plugin.model.JdbcType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUtils {

    private static Log log = LogFactoryImpl.getLog(JDBCUtils.class);
    private static String driver = "";
    private static String url = "";
    private static String username = "";
    private static String password = "";

    public static void init(String driver, String url, String username, String password) {
        JDBCUtils.driver = driver;
        JDBCUtils.url = url;
        JDBCUtils.username = username;
        JDBCUtils.password = password;
    }

    public static String getDriver() {
        return driver;
    }

    public static void setDriver(String driver) {
        JDBCUtils.driver = driver;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        JDBCUtils.url = url;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        JDBCUtils.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        JDBCUtils.password = password;
    }


    /**
     * 打开数据库连接
     *
     * @return
     */
    private static Connection getConnection() {
        log.debug("JDBCUtil getConnection start");
        Connection conn = null;
        try {
            //驱动加载
            Class.forName(JDBCUtils.getDriver());
            conn = DriverManager.getConnection(JDBCUtils.getUrl(), JDBCUtils.getUsername(), JDBCUtils.getPassword());
            conn.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();

        }
        log.debug("JDBCUtil getConnection end");
        return conn;
    }


    /**
     * 关闭数据库连接
     *
     * @param conn
     */
    private static void closeConnection(Connection conn) {
        log.debug("JDBCUtil closeConnection start");
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        log.debug("JDBCUtil closeConnection end");
    }

    /**
     * SHOW FULL FIELDS FROM channel
     *
     * @param tableName
     */
    public static Map<String, Object> getTableInfoFrom(String tableName, String databaseType) {
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement pstmt = null;

        String sql = "";
        if("mysql".trim().equalsIgnoreCase(databaseType)) {
            sql = "SHOW FULL FIELDS FROM " + tableName;

        } else if("oracle".trim().equalsIgnoreCase(databaseType)) {
            sql = "SHOW FULL FIELDS FROM " + tableName;

        } else if("sqlserve".trim().equalsIgnoreCase(databaseType)) {
            sql = "SHOW FULL FIELDS FROM " + tableName;

        }

        List<Field> list = new ArrayList<Field>();
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();

            while (rs.next()) {
                String column = rs.getString("Field");
                String filed = StringUtils.upcaseUnderlineNext(rs.getString("Field"));
                if(!"tableType".equalsIgnoreCase(filed)  && !"rowFormat".equalsIgnoreCase(filed) && !"columnKey".equalsIgnoreCase(filed)) {
                    String javaType = JavaType.getIntance().toJavaType(rs.getString("Type"));
                    String jdbcType = JdbcType.getIntance().toJdbcType(rs.getString("Type"));
                    String key = rs.getString("Key");
                    String comment = rs.getString("Comment");

                    if(org.apache.commons.lang.StringUtils.isNotBlank(key) && "PRI".equals(key)) {
                        result.put("key", new Field(column, filed, javaType, jdbcType, key, comment));
                    }

                    if("tableSchema".equalsIgnoreCase(filed) && "TABLES".equals(tableName)) {
                        result.put("key", new Field(column, filed, javaType, jdbcType, key, comment));
                    }

                    if("tableName".equalsIgnoreCase(filed) && "COLUMNS".equals(tableName)) {
                        result.put("key", new Field(column, filed, javaType, jdbcType, key, comment));
                    }

                    if("tableName".equalsIgnoreCase(filed) && "STATISTICS".equals(tableName)) {
                        result.put("key", new Field(column, filed, javaType, jdbcType, key, comment));
                    }

                    list.add(new Field(column, filed, javaType, jdbcType, key, comment));
                }

            }

            result.put("fields", list);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeConnection(conn);
        }

        return result;
    }
}