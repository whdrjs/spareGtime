import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Scanner;
public class start_server {
	static class room_info {
		static int count=0;
		static int id[];
		static String name[];
	}
	public static Scanner in = new Scanner(System.in);
	
	public static Connection getConnection()throws ClassNotFoundException,SQLException{
		String url = "jdbc:mysql://localhost:3306/sparegtime";
		String user = "root";
		String pass = "1234";
		
		Connection conn = null;
		Class.forName("org.gjt.mm.mysql.Driver");
		conn = DriverManager.getConnection(url,user,pass);
		System.out.println("접속");
		return conn;
	}
	public static void insert_room(int ID,String name,int limit,int spare_time,String day,String content) throws ClassNotFoundException,SQLException{
		Connection conn=getConnection();
		String sql="insert into room values(?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		//int ID
		//String name
		//int limit
		//int spare_time
		//String day
		//String content
		
		pstmt.setInt(1,ID);	
		pstmt.setString(2,name);
		pstmt.setInt(3,limit);
		pstmt.setInt(4,spare_time);
		pstmt.setString(5,day);	
		pstmt.setString(6,content);	
		int res = pstmt.executeUpdate();
		if(res>0)
			System.out.println("처리");
		
		if(pstmt!=null)pstmt.close();
		if(conn!=null)conn.close();
	}
	public static void insert_event(String content,String name,double latitude,double longitude,float star,String address,String phone ) throws ClassNotFoundException,SQLException{
		Connection conn=getConnection();
		String sql="insert into contents values(?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		//String content 
		//String name 
		//double latitude
		//double longitude
		//float star
		//String address
		//String phone
		pstmt.setString(1,content);
		pstmt.setString(2,name);
		pstmt.setDouble(3,latitude);
		pstmt.setDouble(4,longitude);
		pstmt.setFloat(5,star);
		pstmt.setString(6,address);
		pstmt.setString(7,phone);
		
		int res = pstmt.executeUpdate();
		if(res>0)
			System.out.println("처리");
		
		if(pstmt!=null)pstmt.close();
		if(conn!=null)conn.close();
	}
	public static void delete_room(int ID) throws ClassNotFoundException,SQLException{
		Connection conn=getConnection();
		String sql="delete from room where ID = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1,ID);

		int res = pstmt.executeUpdate();
		if(res>0)
			System.out.println("처리");
		
		if(pstmt!=null)pstmt.close();
		if(conn!=null)conn.close();
	}
	public static void delete_event(String name) throws ClassNotFoundException,SQLException{
		Connection conn=getConnection();
		String sql="delete from contents where name = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1,name);

		int res = pstmt.executeUpdate();
		if(res>0)
			System.out.println("처리");
		
		if(pstmt!=null)pstmt.close();
		if(conn!=null)conn.close();
	}
	public static room_info search_room(int spare_time,String day,String content) throws ClassNotFoundException,SQLException{
		Connection conn=getConnection();
		room_info inform= new room_info();
		String sql="select ID,name from room where spare_time = ? and day = ? and content = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1,spare_time);
		pstmt.setString(2,day);
		pstmt.setString(3,content);
			
		ResultSet res = pstmt.executeQuery();
		if(res!=null)
			System.out.println("탐색완료");
		else {
			room_info.name[room_info.count]="정보에 맞는 방이 없습니다";
			return inform;
		}
		while(res.next()) {
			room_info.id[room_info.count]=res.getInt("ID");
			room_info.name[room_info.count]=res.getString("name");
			room_info.count++;
		}
		if(pstmt!=null)pstmt.close();
		if(conn!=null)conn.close();
		return inform;
	}
	
	public static String[] search_content(String main) throws ClassNotFoundException,SQLException{
		Connection conn=getConnection();
		String sql="select content from category where main = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,main);
		String name[]=null;	
		int count=0;
		ResultSet res = pstmt.executeQuery();
		if(res!=null)
			System.out.println("탐색완료");
		else {
			name[0]= "정보에 맞는 컨텐츠가 없습니다";
			return name;
		}
		while(res.next()) {
			name[count]=res.getString("name");
			count++;
		}
		if(pstmt!=null)pstmt.close();
		if(conn!=null)conn.close();
		return name;
	}
	
	public static void main(String[] args) throws ClassNotFoundException,SQLException{
		int ID=1234;
		String R_name="pc방 갈사람";
		int limit=4;
		int spare_time=10;
		String day="화";
		String R_content="pc방";
		//String content 
		//String name 
		//double latitude
		//double longitude
		//float star
		//String address
		//String phone
		
		String E_content ="이벤트";
		String E_name="학과의날";
		double latitude=37.4482461;
		double longitude=127.1270569;
		float star=5;
		String address="IT대학 ";
		String phone="010-1234-5678";
		
		insert_room(ID,R_name,limit,spare_time,day,R_content);
		delete_room(ID);
		insert_event(E_content ,E_name,latitude,longitude,star,address,phone);
		delete_event(E_name);
	}
}