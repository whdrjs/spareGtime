import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Scanner;
public class start_server {
	static class room_info {
		static int count=0;
		static ArrayList < Integer > id=new ArrayList < Integer >();
		static ArrayList < String > name=new ArrayList < String >();
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
	public static void insert_room(int ID,String name,int maximum,String spare_time,String day,String content) throws ClassNotFoundException,SQLException{
		Connection conn=getConnection();
		String sql="insert into roomlist values(?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		//int id
		//String name
		//int maximum
		//String spare_time
		//String day
		//String content
		
		pstmt.setInt(1,ID);	
		pstmt.setString(2,name);
		pstmt.setInt(3,maximum);
		pstmt.setString(4,spare_time);
		pstmt.setString(5,day);	
		pstmt.setString(6,content);	
		int res = pstmt.executeUpdate();
		if(res>0)
			System.out.println("처리");
		
		if(pstmt!=null)pstmt.close();
		if(conn!=null)conn.close();
	}
	public static void insert_event(String content,String name,double latitude,double longitude,float star,String address) throws ClassNotFoundException,SQLException{
		Connection conn=getConnection();
		String sql="insert into contents1 values(?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		//String content 
		//String name 
		//double latitude
		//double longitude
		//float star
		//String address
		pstmt.setString(1,content);
		pstmt.setString(2,name);
		pstmt.setDouble(3,latitude);
		pstmt.setDouble(4,longitude);
		pstmt.setFloat(5,star);
		pstmt.setString(6,address);
		
		int res = pstmt.executeUpdate();
		if(res>0)
			System.out.println("처리");
		
		if(pstmt!=null)pstmt.close();
		if(conn!=null)conn.close();
	}
	public static void delete_room(int ID) throws ClassNotFoundException,SQLException{
		Connection conn=getConnection();
		String sql="delete from roomlist where ID = ?";
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
		String sql="delete from contents1 where name = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1,name);

		int res = pstmt.executeUpdate();
		if(res>0)
			System.out.println("처리");
		
		if(pstmt!=null)pstmt.close();
		if(conn!=null)conn.close();
	}
	public static room_info search_room(String spare_time,String day,String content) throws ClassNotFoundException,SQLException{
		Connection conn=getConnection();
		room_info inform= new room_info();
		String sql="select ID,name from roomlist where spare_time = ? and day = ? and content = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,spare_time);
		pstmt.setString(2,day);
		pstmt.setString(3,content);
			
		ResultSet res = pstmt.executeQuery();
		if(res!=null) {
			System.out.println("탐색완료");

		}
		else {
			room_info.name.add("정보에 맞는 방이 없습니다");
			return inform;
		}
		while(res.next()){
			room_info.id.add(res.getInt("ID"));
			room_info.name.add(res.getString("name"));
			inform.count++;
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
		//int id
		//String name
		//int maximum
		//String spare_time
		//String day
		//String content
		
		int ID=1234;
		String R_name="pc방 갈사람";
		int maximum=4;
		String spare_time="10:00~11:00";
		String day="wensday";
		String R_content="pc";
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
		
		room_info R=new room_info();
		insert_room(ID,R_name,maximum,spare_time,day,R_content);
		R=search_room(spare_time,day,R_content);
		for(int i=0;i<R.count;i++) {
			System.out.println(R.id.get(i)+" "+R.name.get(i));
		}
		delete_room(ID);
		insert_event(E_content ,E_name,latitude,longitude,star,address);
		delete_event(E_name);
	}
}
