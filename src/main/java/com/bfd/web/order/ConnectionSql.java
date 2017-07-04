package com.bfd.web.order;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet; 
import java.sql.SQLException;
import com.mysql.jdbc.CallableStatement;

public class ConnectionSql {
	public static final String url = "jdbc:mysql://172.24.5.214/BaiduWenkuController";
	public static final String connname = "com.mysql.jdbc.Driver";
	public static final String username = "crawl";
	public static final String password = "crawl";
	
	public Connection conn = null;
	public static PreparedStatement prepared = null;
	public CallableStatement cstmt = null;
	
	public ConnectionSql(String sql){
		try {
			Class.forName(connname);//指定連接類型
			conn = DriverManager.getConnection(url,username,password);//获取连接
			prepared = conn.prepareStatement(sql);//准备执行sql语句
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ConnectionSql(){
		try {
			Class.forName(connname);//指定連接類型
			conn = DriverManager.getConnection(url,username,password);//获取连接
			cstmt = (CallableStatement) conn.prepareCall("{call create_table()}");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			this.conn.close();
			this.prepared.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String sql = "SELECT * FROM `order`";
		ConnectionSql consql = new ConnectionSql(sql);
		try {
			ResultSet ret = consql.prepared.executeQuery();//执行语句，得到结果集 
			while(ret.next()){
				String uid = ret.getString(2);  
				System.out.println("dd"+uid);
			}
			ret.close();
			consql.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
