package Parse;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
 
 
//�������ǽ���һ��DBHelper��
 
public class DBHelper {
 
	private static Connection con ;
	static{
		try {
            String driver = "com.mysql.jdbc.Driver"; //���ݿ�����
            String url = "jdbc:mysql://localhost:3306/parseUrl?useUnicode=true&characterEncoding=utf-8";//
            String user = "root"; //�û���
            String password = "mysql";//����
            Class.forName(driver); //�������ݿ�����
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
             e.printStackTrace();
        }
	}
     private static Connection getCon() {
           return con;
      } 
 
      //��ѯ���
 
      public static ResultSet executeQuery(String sql) throws SQLException {
           Statement stmt = con.createStatement();
           ResultSet rs = stmt.executeQuery(sql);
           return rs;
      }
 
      public static ResultSet executeQuery(String sql, Object... obj)   throws SQLException {
         PreparedStatement pstmt = con.prepareStatement(sql);
         for (int i = 0; i < obj.length; i++) {
               pstmt.setObject(i + 1, obj[i]);
         }
         ResultSet rs = pstmt.executeQuery();
         return rs;
     }
     //ִ����ɾ��
      
     public static int executeNonQuery(String sql) throws SQLException {
         Statement stmt = con.createStatement();
         return stmt.executeUpdate(sql);
    }
 
    public static int executeNonQuery(String sql, Object... obj) throws SQLException {
         PreparedStatement pstmt = con.prepareStatement(sql);
         for (int i = 0; i < obj.length; i++) {
              pstmt.setObject(i + 1, obj[i]);
         }
        return pstmt.executeUpdate();
    }
}
 
