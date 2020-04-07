package com.sdl.utils;

import com.sdl.pojo.Columns;
import com.sdl.pojo.Tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program databasedoc
 * @description: 数据库操作
 * @author: songdeling
 * @create: 2020/04/06 22:54
 */
public class DataUtils {
    public static List<Tables> getTableMsg() throws SQLException, ClassNotFoundException {
        Connection conn = null;

        // oracle连接
        Class.forName("oracle.jdbc.OracleDriver");
        String url="url";
        conn=DriverManager.getConnection(url,"user","password");

        // mysql连接
        //Class.forName("org.gjt.mm.mysql.Driver");
        //conn = DriverManager.getConnection("jdbc:mysql://ip:port/DBNAME", user, password);

        //DB2
        //Class.forName("com.ibm.db2.jcc.DB2Driver");
        //conn = DriverManager.getConnection("jdbc:db2://ip:port/DBNAME", user, password);

        PreparedStatement pst = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        try {
            List<Tables> tablesList = new ArrayList<>();
            String sqlStr = "SELECT ALLT.TABLE_NAME,UTC.COMMENTS FROM ALL_TABLES ALLT,USER_TAB_COMMENTS UTC WHERE ALLT.TABLE_NAME = UTC.TABLE_NAME AND OWNER='数据库用户必须大写'";
            pst = conn.prepareStatement(sqlStr);
            rs = pst.executeQuery();
            while(rs.next()){
                Tables tables = new Tables();
                tables.setTableName(rs.getString("TABLE_NAME"));
                tables.setTableComments(rs.getString("COMMENTS"));
                //字段
                sqlStr = "SELECT UTC.COLUMN_NAME, UTC.DATA_TYPE, UTC.DATA_LENGTH, UTC.NULLABLE, UTC.DATA_DEFAULT, UCC.COMMENTS FROM USER_TAB_COLUMNS UTC,USER_COL_COMMENTS UCC " +
                        "WHERE UTC.TABLE_NAME='"+ tables.getTableName() +"' AND UTC.COLUMN_NAME = UCC.COLUMN_NAME AND UTC.TABLE_NAME = UCC.TABLE_NAME";
                pst = conn.prepareStatement(sqlStr);
                rs1 = pst.executeQuery();
                List<Columns> columnsList = new ArrayList<>();
                while(rs1.next()){
                    Columns columns = new Columns();
                    columns.setName(rs1.getString("COLUMN_NAME"));
                    columns.setDatatype(rs1.getString("DATA_TYPE"));
                    columns.setLen(rs1.getString("DATA_LENGTH"));
                    columns.setIsnull(rs1.getString("NULLABLE"));
                    columns.setDef(rs1.getString("DATA_DEFAULT"));
                    columns.setComments(rs1.getString("COMMENTS"));
                    columnsList.add(columns);
                }
                tables.setColumnsList(columnsList);
                tablesList.add(tables);
            }
            return tablesList;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                rs1.close();
                rs.close();
                pst.close();
            } catch(SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
