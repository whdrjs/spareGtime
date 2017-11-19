import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Vector;

public class start_server {
	private static final int PORT = 9000;
	private static HashSet<String> names = new HashSet<String>();
	private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
	Vector<Handler> vc;
	static class content_info {// 컨텐츠 목록을 반환하기 위한 클래스
		static int count = 0;
		static ArrayList<String> content = new ArrayList<String>();
		static ArrayList<String> name = new ArrayList<String>();
		static ArrayList<Double> latitude = new ArrayList<Double>();
		static ArrayList<Double> longitude = new ArrayList<Double>();
		static ArrayList<Float> star = new ArrayList<Float>();
		static ArrayList<String> address = new ArrayList<String>();
		static ArrayList<Integer> distance = new ArrayList<Integer>();
		static void add(res.getInt("ID")){
			inform.content.add(res.getInt("content"));
			inform.name.add(res.getString("name"));
			inform.latitude.add(res.getInt("latitude"));
			inform.longitude.add(res.getString("longitude"));
			inform.star.add(res.getString("star"));
			inform.address.add(res.getString("address"));
			inform.distance.add(res.getInt("distance"));
			inform.count++;
		}
		static void add(content_info a,int i){
			inform.content.add(a.content.get(i));
			inform.name.add(a.name.get(i));
			inform.latitude.add(a.latitude.get(i));
			inform.longitude.add(a.longitude.get(i));
			inform.star.add(a.star.get(i));
			inform.address.add(a.address.get(i));
			inform.distance.add(a.distance.get(i));
			inform.count++;
		}
	}
	static class room_info {// 방 목록을 반환하기 위한 클래스
		static int count = 0;
		static ArrayList<Integer> id = new ArrayList<Integer>();
		static ArrayList<String> name = new ArrayList<String>();
		static ArrayList<Integer> maximum = new ArrayList<Integer>();
		static ArrayList<String> spare_time = new ArrayList<String>();
		static ArrayList<String> content = new ArrayList<String>();
		static void add(ResultSet res){
			inform.id.add(res.getInt("ID"));
			inform.name.add(res.getString("name"));
			inform.maximum.add(res.getInt("maximum"));
			inform.spare_time.add(res.getString("spare_time"));
			inform.content.add(res.getString("content"));
			inform.count++;
		}
		static void add(room_info a,int i){
			inform.id.add(a.id.get(i));
			inform.name.add(a.name.get(i));
			inform.maximum.add(a.maximum.get(i));
			inform.spare_time.add(a.spare_time.get(i));
			inform.content.add(a.content.get(i));
			inform.count++;
		}
	}
	public start_server() throws Exception {
		System.out.println("The server is running.");
		ServerSocket listener = new ServerSocket(PORT);
		vc = new Vector<Handler>();
		try {
			while (true) {
				Handler hd = new Handler(listener.accept());
				hd.start();
				vc.add(hd);
			}
		} finally {
			listener.close();
		}
	}

	public void removeClient(Handler hd) {
		// Remove client to vector.
		vc.remove(hd);
	}

	private class Handler extends Thread {
		private String name;
		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;

		public Handler(Socket socket) {
			this.socket = socket;
		}
		

		public void run()  {
	            try {
	            	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            	out = new PrintWriter(socket.getOutputStream(), true);
	            	
	                while (true) {
	                	out.println("SUBMITNAME");
	                	name = in.readLine();
	                    if (name == null) {
	                        return;
	                    }
	                    synchronized (names) {
	                        if (!names.contains(name)) {
	                            names.add(name);
	                            break;
	                        }
	                    }
	                }

	                out.println("NAMEACCEPTED");
	                writers.add(out);

	                while (true) {
	                    String input = in.readLine();
	                    if (input == null) {
	                        return;
	                    }
	                    String command=input.substring(0,4);
	                    //가게목록 검색
	                    if(command=="ACT") {
				    //"ACT content distance 1"
				    //"ACT content star"
	                    	String input1[]=input.split(" ");
				content_info inform = new content_info();
				    inform = search_content(input[1]);
				if(input[2]=="distance"){
					//거리순
					int i,j,rank[5];
					content_info rank = new content_info();
					switch (input[2]){
						case 1: //비타 7
							for(i=0;i<inform.distance.size();i++){
								inform.distance.set(i,inform.distance.get(i)-7);
								if(inform.distance.get(i)<0)
									inform.distance.set(i,inform.distance.get(i)*-1);
							}
							break;
						case 2: //복정파출소 20
							for(i=0;i<inform.distance.size();i++){
								inform.distance.set(i,inform.distance.get(i)-20);
								if(inform.distance.get(i)<0)
									inform.distance.set(i,inform.distance.get(i)*-1);
							}
							break;
						case 3: //동서울대 27
							for(i=0;i<inform.distance.size();i++){
								inform.distance.set(i,inform.distance.get(i)-27);
								if(inform.distance.get(i)<0)
									inform.distance.set(i,inform.distance.get(i)*-1);
							}
					}
					int min[4]=0;
					int c=0;
					for(i=0;i<inform.distance.size();i++){
						for(j=i;j<inform.distance.size();j++){
							if(inform.distance.get(j)<inform.distance.get(min[c])){
								for(int k=0;k<c;k++){
								}
								min[c]=j;
							}
						}
					}
				}
				else if(input[1]=="star"){
					
				}
				else{
	                    		out.println();
	                    	}
			    }
	                    //가게정보 검색
	                    if(command=="STO") {
	                    	input=input.substring(4);
	                    	out.println(search_store(input));
	                    }
	                    //채팅방 검색
	                    if(command=="SCH") {
	                    	String input1=input.substring(4,16);
	                    	String input2=input.substring(16);
	                    	out.println(search_room(input1,input2));
	                    }
	                    //채팅방 생성
	                    if(command=="MAK") {
	                    	input=input.substring(4);
	                    	String input1[]=input.split(" ");
	                    	int int_input1=Integer.valueOf(input1[0]);
	                    	int int_input2=Integer.valueOf(input1[2]);
	                    	insert_room(int_input1,input1[1],int_input2,input1[3],input1[4]);
	                    }
	                    //채팅방 삭제
	                    if(command=="DEL") {
	                    	input=input.substring(4);
	                    	int int_input=Integer.valueOf(input);
	                    	delete_room(int_input);
	                    }
	                    //채팅방 입장
	                    if(command=="COM") {
	                    	input=input.substring(4);
	                    	int port=enter_room();
	                    	new ChatServer(port);
	                    }
	                    //C_Server(room_num);
	                    
	                }
	            } catch ( Exception e) {
	                System.out.println(e);
	            } finally {

	                if (name != null) {
	                    names.remove(name);
	                }
	                if (out != null) {
	                    writers.remove(out);
	                }
	                removeClient(this);
	                try {
	                	socket.close();
	                } catch (IOException e) {
	                }
	            }

	        }

		public Handler findThread(String name) {
			Handler hd = null;
			for (int i = 0; i < vc.size(); i++) {
				hd = vc.get(i);
				if (hd.name.equals(name))
					break;
			}
			return hd;
		}
	}
	

	public static Scanner in = new Scanner(System.in);

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		// sql 데이터베이스에 연결
		String url = "jdbc:mysql://localhost:3306/sparegtime";// 경로
		String user = "root";// ID
		String pass = "1234";// password

		Connection conn = null;
		Class.forName("org.gjt.mm.mysql.Driver");
		conn = DriverManager.getConnection(url, user, pass);
		System.out.println("접속");

		return conn;

	}

	public static void insert_room(int ID, String name, int maximum, String spare_time, String content)
			throws ClassNotFoundException, SQLException {
		// 사용자가 방을 만들었을 때 방의 정보를 sql에 저장하는 함수
		Connection conn = getConnection();
		String sql = "insert into roomlist values(?,?,?,?,?,?)";// sql 쿼리
		PreparedStatement pstmt = conn.prepareStatement(sql);

		// 방번호 int id
		// 방이름 String name
		// 최대인원 int maximum
		// 공강시간 String spare_time
		// 컨텐츠 String content

		pstmt.setInt(1, ID);
		pstmt.setString(2, name);
		pstmt.setInt(3, maximum);
		pstmt.setString(4, spare_time);
		pstmt.setString(5, content);

		int res = pstmt.executeUpdate();

		if (res > 0)
			System.out.println("처리");
		if (pstmt != null)
			pstmt.close();
		if (conn != null)
			conn.close();
	}

	public static void insert_event(String content, String name, double latitude, double longitude, float star,
			String address) throws ClassNotFoundException, SQLException {

		// 이벤트를 추가할때 사용하는 함수
		// 다른 컨텐츠를 추가할때도 사용가능

		Connection conn = getConnection();
		String sql = "insert into contents1 values(?,?,?,?,?,?)";// sql 쿼리
		PreparedStatement pstmt = conn.prepareStatement(sql);

		// 컨텐츠 String content
		// 이름 String name
		// 위도 double latitude
		// 경도 double longitude
		// 평점 float star
		// 주소 String address

		pstmt.setString(1, content);
		pstmt.setString(2, name);
		pstmt.setDouble(3, latitude);
		pstmt.setDouble(4, longitude);
		pstmt.setFloat(5, star);
		pstmt.setString(6, address);

		int res = pstmt.executeUpdate();

		if (res > 0)
			System.out.println("처리");
		if (pstmt != null)
			pstmt.close();
		if (conn != null)
			conn.close();

	}

	public static void delete_room(int ID) throws ClassNotFoundException, SQLException {

		// 방에 인원이 다나갔을때 방을 삭제하는 함수
		// 방에 인원이 다나갔는지 확인하는 기능추가(메인에서)

		Connection conn = getConnection();
		String sql = "delete from roomlist where ID = ?";// sql 쿼리
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setInt(1, ID);

		int res = pstmt.executeUpdate();
		if (res > 0)
			System.out.println("처리");
		if (pstmt != null)
			pstmt.close();
		if (conn != null)
			conn.close();
	}

	public static void delete_event(String name) throws ClassNotFoundException, SQLException {

		// 기간이 지나거나 끝난 이벤트를 지우는 함수
		// 다른 컨텐츠도 사용가능해서 가게를 지울때도 사용가능

		Connection conn = getConnection();
		String sql = "delete from contents1 where name = ?";// sql 쿼리
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, name);

		int res = pstmt.executeUpdate();
		if (res > 0)
			System.out.println("처리");
		if (pstmt != null)
			pstmt.close();
		if (conn != null)
			conn.close();

	}
	public static int enter_room() throws ClassNotFoundException, SQLException{
		int port;
		Connection conn = getConnection();
		String sql = "select ID from roomlist";// sql 쿼리
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet res = pstmt.executeQuery();
		int id[] = null,i=0,j,k,min=0,temp;
		while (res.next()) {
			id[i]=res.getInt("ID");
			i++;
		}
		for(j=0;j<i;j++) {
			for(k=j;k<i;k++) {
				if(id[j]>id[k]) {
					temp=id[j];
					id[j]=id[k];
					id[k]=temp;
				}
			}
			if(j>0&&(id[j]!=id[j-1]+1)) {
				min=j;
				break;
			}
		}
		port=id[min];
		return port;
	}
	public static room_info search_room(String spare_time, String content)
			throws ClassNotFoundException, SQLException {

		// 방목록을 가져오는 함수
		// 공강시간 요일 컨텐츠로 sql에서 검색해서 반환

		Connection conn = getConnection();
		room_info inform = new room_info();
		String sql = "select ID,name from roomlist where spare_time = ? and content = ?";// sql 쿼리
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, spare_time);
		pstmt.setString(2, content);

		ResultSet res = pstmt.executeQuery();
		if (res != null) {
			System.out.println("탐색완료");
		} else {
			room_info.name.add("정보에 맞는 방이 없습니다");
			return inform;
		}

		while (res.next()) {
			inform.add(res);
		}

		if (pstmt != null)
			pstmt.close();
		if (conn != null)
			conn.close();
		return inform;
	}

	public static content_info search_content(String content) throws ClassNotFoundException, SQLException {

		// 카테고리에서 컨텐츠를 찾아오는 함수
		// 매뉴에서 컨텐츠를 보여줄때 사용

		Connection conn = getConnection();
		content_info inform = new content_info();
		String sql = "select * from contents where content = ?";// sql 쿼리
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, content);
		
		ResultSet res = pstmt.executeQuery();

		if (res != null)
			System.out.println("탐색완료");
		while (res.next()) {
			inform.add(res);
		}

		if (pstmt != null)
			pstmt.close();
		if (conn != null)
			conn.close();
		return inform;
	}
	public static String[] search_store(String name) throws ClassNotFoundException, SQLException {

		// 카테고리에서 컨텐츠를 찾아오는 함수
		// 매뉴에서 컨텐츠를 보여줄때 사용

		Connection conn = getConnection();
		String sql = "select star,address,latitude,longitude from contents where name = ?";// sql 쿼리
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, name);
		String store[] = null;
		// 지도 주소 평점 
		int count = 0;

		ResultSet res = pstmt.executeQuery();

		if (res != null)
			System.out.println("탐색완료");
		else {
			store[0] = "정보에 맞는 컨텐츠가 없습니다";
			return store;
		}
		while (res.next()) {
			store[count] = res.getString("address")+" "+res.getString("star")+" "+res.getString("latitude")+" "+res.getString("longitude");
			count++;
		}
		if (pstmt != null)
			pstmt.close();
		if (conn != null)
			conn.close();
		return store;
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {

		new start_server();
	}
}
