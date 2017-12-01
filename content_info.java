package spareTime;

import java.sql.ResultSet;
import java.sql.SQLException;

class content_info {// 컨텐츠 목록을 반환하기 위한 클래스
	String content;
	String name;
	float star;
	String address;
	int distance;	
	void add(ResultSet res) throws SQLException{
		content=res.getString("content");
		name=res.getString("name");
		star=res.getFloat("star");
		address=res.getString("address");
		distance=res.getInt("distance");
	}
	String output() {
		String a="";
			a=a+content+"_";
			a=a+name+"_";
			a=a+star+"_";
			a=a+address+"_";
			a=a+distance+"^";
		return a;
	}
}
