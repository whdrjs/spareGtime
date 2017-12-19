package spareTime;

import java.sql.ResultSet;
import java.sql.SQLException;

class content_info {
	//store's information content(what kinds of), name, star, address, distance
	String content;
	String name;
	float star;
	String address;
	int distance;	
	//add get information from database as ResultSet
	void add(ResultSet res) throws SQLException{
		content=res.getString("content");
		name=res.getString("name");
		star=res.getFloat("star");
		address=res.getString("address");
		distance=res.getInt("distance");
	}
	//return one String send message to client about information list 
	//each attribute divide "_", each data set divide "^"
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
