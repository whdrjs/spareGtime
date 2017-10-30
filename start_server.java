import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Scanner;
package gcfreiend
public class start_server {
	public static Scanner in = new Scanner(System.in);
	
	public static Connection getConnection()throws ClassNotFoundException,SQLException{
		String url = "jdbc:mysql://localhost:3306/server_db";
		String user = "root";
		String pass = "1234";
		
		Connection conn = null;
		Class.forName("org.gjt.mm.mysql.Driver");
		conn = DriverManager.getConnection(url,user,pass);
		System.out.println("접속");
		return conn;
	}
	public static void insert(String name,String id,String pw,int u_id) throws ClassNotFoundException,SQLException{
		Connection conn=getConnection();
		String sql="insert into person(name,id,password,u_id) values(?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1,name);
		pstmt.setString(2,id);
		pstmt.setString(3,pw);
		pstmt.setInt(4,u_id);	
		int res = pstmt.executeUpdate();
		if(res>0)
			System.out.println("처리");
		
		if(pstmt!=null)pstmt.close();
		if(conn!=null)conn.close();
	}
	public static void search_id(String name,int u_id) throws ClassNotFoundException,SQLException{
		Connection conn=getConnection();
		String sql="select id from person where name = ? and u_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1,name);
		pstmt.setInt(2,u_id);	
		int res = pstmt.executeUpdate();
		if(res>0)
			System.out.println("탐색완료");
		
		if(pstmt!=null)pstmt.close();
		if(conn!=null)conn.close();
	}
	public static void search_pw(String name,String id,int u_id) throws ClassNotFoundException,SQLException{
		Connection conn=getConnection();
		String sql="select pw from person where name = ? and id = ? and u_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1,name);
		pstmt.setString(2,id);
		pstmt.setInt(3,u_id);	
		int res = pstmt.executeUpdate();
		if(res>0)
			System.out.println("탐색완료");
		
		if(pstmt!=null)pstmt.close();
		if(conn!=null)conn.close();
	}
	public static void main(String[] args) throws ClassNotFoundException,SQLException{
		
	}
}
