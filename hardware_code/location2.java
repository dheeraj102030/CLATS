import java.sql.*;  
import java.util.Scanner;

public class location2{
  
public static void main(String args[]){

	final String database_IP = ip.IP;

	final String user_name = "rfid_pi";
	final String password = "Password1";

	final String port = "3306";
	final String db_name = "test_db";
	final String table = "test_table";

	final String location = "Location 2";
	
	Scanner input = new Scanner(System.in);
	

  	final String url = "jdbc:mysql://"+database_IP+":"+port+"/"+db_name;

	try{  
		Class.forName("com.mysql.jdbc.Driver");  

		System.out.println("Connecting to the database...");
		Connection con=DriverManager.getConnection(url,user_name,password);  
		System.out.println("Connected successfully!");
		 
		Statement stmt=con.createStatement();

		String epc = "";

		while(true){ 
			
			epc = input.nextLine();
			
			if(epc.equals("$T0P"))
				break;

			String updateString = String.format("UPDATE %s SET Past_location_4=Past_location_3, Past_location_3=Past_location_2, Past_location_2=Past_location_1, Past_location_1=Present_location, Present_location='%s', Past_time_4=Past_time_3, Past_time_3=Past_time_2, Past_time_2=Past_time_1, Past_time_1=Time, Time=NOW() WHERE Cycle_Number='%s'",table,location,epc);
			
			int updateCount = stmt.executeUpdate(updateString); 
			System.out.println("Updated!");	
					
		}

		con.close();  
	}
	
	catch(Exception e){ 
		System.out.println(e);
	}  
}  
}  