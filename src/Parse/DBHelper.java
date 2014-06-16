package Parse;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
 
 
//这里我们建立一个DBHelper类
 
public class DBHelper {
 
     private static Connection getCon() {
          Connection con = null;
          try {
               String driver = "com.mysql.jdbc.Driver"; //数据库驱动
               String url = "jdbc:mysql://localhost:3306/parseUrl?useUnicode=true&characterEncoding=utf-8";//
               String user = "root"; //用户名
               String password = "mysql";//密码
               Class.forName(driver); //加载数据库驱动
               con = DriverManager.getConnection(url, user, password);
           } catch (Exception e) {
                e.printStackTrace();
           }
           return con;
      } 
 
      //查询语句
 
      public static ResultSet executeQuery(String sql) throws SQLException {
           Connection con = getCon();
           Statement stmt = con.createStatement();
           ResultSet rs = stmt.executeQuery(sql);
           return rs;
      }
 
      public static ResultSet executeQuery(String sql, Object... obj)   throws SQLException {
         Connection con = getCon();
         PreparedStatement pstmt = con.prepareStatement(sql);
         for (int i = 0; i < obj.length; i++) {
               pstmt.setObject(i + 1, obj[i]);
         }
         ResultSet rs = pstmt.executeQuery();
         return rs;
     }
     //执行增删改
      
     public static int executeNonQuery(String sql) throws SQLException {
         Connection con = getCon();
         Statement stmt = con.createStatement();
         return stmt.executeUpdate(sql);
    }
 
    public static int executeNonQuery(String sql, Object... obj) throws SQLException {
    	Connection con = getCon();
         PreparedStatement pstmt = con.prepareStatement(sql);
         for (int i = 0; i < obj.length; i++) {
              pstmt.setObject(i + 1, obj[i]);
         }
        return pstmt.executeUpdate();
    }
}
 
