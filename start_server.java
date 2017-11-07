import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Scanner;
public class start_server {
	static class room_info {//방 목록을 반환하기 위한 클래스
		static int count=0;
		static ArrayList < Integer > id=new ArrayList < Integer >();
		static ArrayList < String > name=new ArrayList < String >();
		static ArrayList < Integer > maximum=new ArrayList < Integer >();
		static ArrayList < String > spare_time=new ArrayList < String >();
		static ArrayList < String > day=new ArrayList < String >();
		static ArrayList < String > content=new ArrayList < String >();
		
	}
	public static Scanner in = new Scanner(System.in);
	
	public static Connection getConnection()throws ClassNotFoundException,SQLException{
		//sql 데이터베이스에 연결
		String url = "jdbc:mysql://localhost:3306/sparegtime";//경로
		String user = "root";//ID
		String pass = "1234";//password
		
		Connection conn = null;
		Class.forName("org.gjt.mm.mysql.Driver");
		conn = DriverManager.getConnection(url,user,pass);
		System.out.println("접속");
		return conn;
	}
	public static void insert_room(int ID,String name,int maximum,String spare_time,String day,String content) throws ClassNotFoundException,SQLException{
		//사용자가 방을 만들었을 때 방의 정보를 sql에 저장하는 함수
		Connection conn=getConnection();
		String sql="insert into roomlist values(?,?,?,?,?,?)";//sql 쿼리
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		//방번호 int id
		//방이름 String name
		//최대인원 int maximum
		//공강시간 String spare_time
		//공강요일 String day
		//컨텐츠 String content
		
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
		//이벤트를 추가할때 사용하는 함수
		//다른 컨텐츠를 추가할때도 사용가능
		Connection conn=getConnection();
		String sql="insert into contents1 values(?,?,?,?,?,?)";//sql 쿼리
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		//컨텐츠 String content 
		//이름 String name 
		//위도 double latitude
		//경도 double longitude
		//평점 float star
		//주소 String address
		
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
		//방에 인원이 다나갔을때 방을 삭제하는 함수
		//방에 인원이 다나갔는지 확인하는 기능추가(메인에서)
		Connection conn=getConnection();
		String sql="delete from roomlist where ID = ?";//sql 쿼리
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1,ID);

		int res = pstmt.executeUpdate();
		if(res>0)
			System.out.println("처리");
		
		if(pstmt!=null)pstmt.close();
		if(conn!=null)conn.close();
	}
	public static void delete_event(String name) throws ClassNotFoundException,SQLException{
		//기간이 지나거나 끝난 이벤트를 지우는 함수
		//다른 컨텐츠도 사용가능해서 가게를 지울때도 사용가능
		Connection conn=getConnection();
		String sql="delete from contents1 where name = ?";//sql 쿼리
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1,name);

		int res = pstmt.executeUpdate();
		if(res>0)
			System.out.println("처리");
		
		if(pstmt!=null)pstmt.close();
		if(conn!=null)conn.close();
	}
	public static room_info search_room(String spare_time,String day,String content) throws ClassNotFoundException,SQLException{
		//방목록을 가져오는 함수
		//공강시간 요일 컨텐츠로 sql에서 검색해서 반환
		Connection conn=getConnection();
		room_info inform= new room_info();
		String sql="select ID,name from roomlist where spare_time = ? and day = ? and content = ?";//sql 쿼리
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
			room_info.maximum.add(res.getInt("maximum"));
			room_info.day.add(res.getString("day"));
			room_info.spare_time.add(res.getString("spare_time"));
			room_info.content.add(res.getString("content"));
			inform.count++;
		}
		if(pstmt!=null)pstmt.close();
		if(conn!=null)conn.close();
		return inform;
	}
	
	public static String[] search_content(String main) throws ClassNotFoundException,SQLException{
		//카테고리에서 컨텐츠를 찾아오는 함수
		//매뉴에서 컨텐츠를 보여줄때 사용
		Connection conn=getConnection();
		String sql="select content from category where main = ?";//sql 쿼리
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
		//함수 실행 예제 
		
		int ID=1234;
		String R_name="pc방 갈사람";
		int maximum=4;
		String spare_time="10:00~11:00";
		String day="wensday";
		String R_content="pc";
		
		//방정보와 컨텐츠에서 content와 name이 겹치므로 이용할때는 서로구분함
		
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
			System.out.println(R.id.get(i)+" "+R.name.get(i)+" "R.maximum.get(i) );
		}
		delete_room(ID);
		insert_event(E_content ,E_name,latitude,longitude,star,address);
		delete_event(E_name);
	}
}
