

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
	private   final int PORT = 9000;
	private   HashSet<String> names = new HashSet<String>();
	private   HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
	Vector<Handler> vc;
	class content_info {// 컨텐츠 목록을 반환하기 위한 클래스
		int count = 0;
		ArrayList<String> content = new ArrayList<String>();
		ArrayList<String> name = new ArrayList<String>();
		ArrayList<Float> star = new ArrayList<Float>();
		ArrayList<String> address = new ArrayList<String>();
		ArrayList<Integer> distance = new ArrayList<Integer>();		
		void add(ResultSet res) throws SQLException{
			this.content.add(res.getString("content"));
			this.name.add(res.getString("name"));
			this.star.add(res.getFloat("star"));
			this.address.add(res.getString("address"));
			this.distance.add(res.getInt("distance"));
			this.count++;
		}
		void add(content_info a,int i){
			this.content.add(a.content.get(i));
			this.name.add(a.name.get(i));
			this.star.add(a.star.get(i));
			this.address.add(a.address.get(i));
			this.distance.add(a.distance.get(i));
			this.count++;
		}
		String output() {
			String a="";
			for(int i=0;i<count;i++) {
				a=a+this.content.get(i)+"_";
				a=a+this.name.get(i)+"_";
				a=a+this.star.get(i)+"_";
				a=a+this.address.get(i)+"_";
				a=a+this.distance.get(i)+"^";
			}
			return a;
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
					String input = in.readLine();
					if (input == null) {
						return;
					}
					String command=input.substring(0,3);
					//가게목록 검색
					if(command.equals("ACT")) {
						//"ACT content distance 1"
						//"ACT content star"
						String input1[]=input.split(" ");
						content_info inform = new content_info();
						inform = search_content(input1[1]);
						if(input1[2].equals("distance")){
							//거리순
							int i,j;
							content_info rank = new content_info();
							switch (input1[3]){
							case "1": //비타 7
								for(i=0;i<inform.distance.size();i++){
									inform.distance.set(i,inform.distance.get(i)-7);
									if(inform.distance.get(i)<0)
										inform.distance.set(i,inform.distance.get(i)*-1);
								}
								break;
							case "2": //복정파출소 20
								for(i=0;i<inform.distance.size();i++){
									inform.distance.set(i,inform.distance.get(i)-20);
									if(inform.distance.get(i)<0)
										inform.distance.set(i,inform.distance.get(i)*-1);
								}
								break;
							case "3": //동서울대 27
								for(i=0;i<inform.distance.size();i++){
									inform.distance.set(i,inform.distance.get(i)-27);
									if(inform.distance.get(i)<0)
										inform.distance.set(i,inform.distance.get(i)*-1);
								}
							}
							int min[]={0,1,2,3};
							for(i=4;i<inform.distance.size();i++){
								int c=0;
								for(j=0;j<4;j++){
									if(inform.distance.get(min[j])>inform.distance.get(min[c])){
										c=j;
									}
								}
								if(inform.distance.get(i)<inform.distance.get(min[c])){
									min[c]=i;
								}
							}					int m1=0,m2=0;
							for(i=0;i<4;i++){
								if(inform.distance.get(min[i])>inform.distance.get(min[m1])){
									m1=i;
								}
								if(inform.distance.get(min[i])<inform.distance.get(min[m2])){
									m2=i;
								}
							}
							int temp;
							temp=min[3];
							min[3]=min[m1];
							min[m1]=temp;
							temp=min[0];
							min[0]=min[m2];
							min[m2]=temp;
							if(inform.distance.get(min[2])<inform.distance.get(min[1])){
								temp=min[1];
								min[1]=min[2];
								min[2]=temp;
							}
							for(i=0;i<4;i++){
								rank.add(inform,min[i]);
							}	 
							String a=rank.output();

							out.println(a);
						}
						else if(input1[2].equals("star")){
							int i,j;
							content_info rank = new content_info();
							int min[]={0,1,2,3};
							for(i=4;i<inform.star.size();i++){
								int c=0;
								for(j=0;j<4;j++){
									if(inform.star.get(min[j])>inform.star.get(min[c])){
										c=j;
									}
								}
								if(inform.star.get(i)<inform.star.get(min[c])){
									min[c]=i;
								}
							}
							int m1=0,m2=0;
							for(i=0;i<4;i++){
								if(inform.distance.get(min[i])>inform.distance.get(min[m1])){
									m1=i;
								}
								if(inform.distance.get(min[i])<inform.distance.get(min[m2])){
									m2=i;
								}
							}
							int temp;
							temp=min[3];
							min[3]=min[m1];
							min[m1]=temp;
							temp=min[0];
							min[0]=min[m2];
							min[m2]=temp;
							if(inform.distance.get(min[2])<inform.distance.get(min[1])){
								temp=min[1];
								min[1]=min[2];
								min[2]=temp;
							}
							for(i=0;i<4;i++){
								rank.add(inform,min[i]);
							}	 
							String a=rank.output();
							out.println(a);
						}
						else{
							String a=inform.output();
							out.println(a);
						}
					}
					
					//채팅방 입장
					if(command.equals("Roo")) {
						String input1[]=input.split(" ");
						System.out.println(input1[1]+" "+input1[2]);
						int port=search_room(input1[2],input1[1]);
						out.println(Integer.toString(port));
						new ChatServer(port);
					}
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


	public   Scanner in = new Scanner(System.in);

	public   Connection getConnection() throws ClassNotFoundException, SQLException {
		// sql 데이터베이스에 연결
		String url = "jdbc:mysql://localhost:3306/sparegtime";// 경로
		String user = "root";// ID
		String pass = "1234";// password

		Connection conn = null;
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, user, pass);
		System.out.println("접속");

		return conn;
	}

	
	public int search_room(String time, String content)
			throws ClassNotFoundException, SQLException {

		// 방목록을 가져오는 함수
		// 공강시간 요일 컨텐츠로 sql에서 검색해서 반환
		
		Connection conn = getConnection();
		String sql = "select port from roomlist where time = ? and content = ?";// sql 쿼리
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, time);
		pstmt.setString(2, content);

		ResultSet res = pstmt.executeQuery();
		if (res != null) {
			System.out.println("탐색완료");
		} else {
			System.out.println("정보에 맞는 방이 없습니다");
			return 0;
		}
		int port;
		res.next();
		port=res.getInt("port");
		return port;
	}

	public   content_info search_content(String content) throws ClassNotFoundException, SQLException {

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

	public static void main(String[] args) throws Exception {
		new start_server();
	}
}

