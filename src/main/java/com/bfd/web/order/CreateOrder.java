package com.bfd.web.order;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class CreateOrder {
	private static final Log LOG = LogFactory.getLog(CreateOrder.class);

	public static void main(String[] args) {
//		 * 测试调用存储过程，创建数据表
		ConnectionSql consql = new ConnectionSql();
		try {
			consql.cstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

//		 *测试添加订单数据
		 try {
			int result = insertOrder("jingdingtest","jingdongtestId",0);
			if(result>0){
				System.out.println("订单记录添加成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		
//		 *测试数据库中是否存在order年+月表
		 try {
			boolean result=validateTableNameExist();
			if(result){
				System.out.println("订单记录添加成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加order订单记录
	 * @0 or 1
	 */
	public static int insertOrder(String username,String userId,int status){
		int result = 0;
		//添加数据前，判断是否有该年月的order表，将存数据添加在相应的年月order表中
		if(validateTableNameExist()){
			String insertSql = "insert into `order_"+getdate()+"` (username,userId,status,create_time,update_time,request_time,temp1,temp2,temp3) values ('"+username+"','"+userId+"',"+status+",now(),now(),now(),'','','');";
			System.out.println(insertSql);
			ConnectionSql consql = new ConnectionSql(insertSql);
			try {
				result = consql.prepared.executeUpdate();//执行语句，得到结果集
				consql.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 判断数据库中是否有当前年-月的order表
	 * @yes or no 
	 */
	public static boolean validateTableNameExist(){
		boolean status = false;
		String sql = "select count(*) from information_schema.`TABLES` where TABLE_NAME ='order_"+getdate()+"'";
		System.out.println(sql);
		try {
			ConnectionSql consql = new ConnectionSql(sql);
			ResultSet ret =consql.prepared.executeQuery() ;  
			if (ret.next()) {  
				status= true;  
			}else {  
				status= false;  
			}  
			System.out.println(status);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return status;
	}
	
	public static String getdate(){
		String date="";
		Calendar now = Calendar.getInstance();  
        int month=(now.get(Calendar.MONTH) + 1);
        String monthstr = "";
        if(month<10){
        	monthstr="0"+String.valueOf(month);
        }
		date=now.get(Calendar.YEAR)+monthstr;
		return date;
	}

}
