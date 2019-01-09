import java.sql.*;  
import java.util.Scanner;

public class gate2{
  
public static void main(String args[]){


	final String database_IP = ip.IP;

	final String user_name = "rfid_pi";
	final String password = "Password1";

	final String port = "3306";
	final String db_name = "test_db";
	final String table = "test_table";

	final String location = "Gate 2";
	
	int flag,updateCount;
	
	Scanner input = new Scanner(System.in);
	

  	final String url = "jdbc:mysql://"+database_IP+":"+port+"/"+db_name;

	try{  
		Class.forName("com.mysql.jdbc.Driver");  

		System.out.println("Connecting to the database...");
		Connection con=DriverManager.getConnection(url,user_name,password);  
		System.out.println("Connected successfully!");
		 
		Statement stmt=con.createStatement();

		String epc = "";
		String pr1="", gs1="";
		String updateString = "";
		
		while(true){
			flag = 0;
			epc = input.nextLine();

			if(epc.equals("$T0P"))
				break;

			String ex1 = String.format("select riding_check, Present_location from test_table WHERE Cycle_Number='%s'",epc);

			//System.out.println(ex1);

			ResultSet rs=stmt.executeQuery(ex1);
			
			//System.out.println(ex1);

			while(rs.next()) {
				gs1 = rs.getString(1);
				if(gs1.equals("0"))
					SoundUtils.tone(200,1500,100);

				pr1=rs.getString(2);
				
				if((pr1.equals("Gate 1") || pr1.equals("Gate 2")) && gs1.equals("1"))
					flag = 1;	
			}
			
			if(flag == 0)
				updateString = String.format("UPDATE %s SET Past_location_4=Past_location_3, Past_location_3=Past_location_2, Past_location_2=Past_location_1, Past_location_1=Present_location, Present_location='%s', Past_time_4=Past_time_3, Past_time_3=Past_time_2, Past_time_2=Past_time_1, Past_time_1=Time, Time=NOW() WHERE Cycle_Number='%s'",table,location,epc);

			else
				updateString = String.format("UPDATE %s SET riding_check='0', Past_location_4=Past_location_3, Past_location_3=Past_location_2, Past_location_2=Past_location_1, Past_location_1=Present_location, Present_location='%s', Past_time_4=Past_time_3, Past_time_3=Past_time_2, Past_time_2=Past_time_1, Past_time_1=Time, Time=NOW() WHERE Cycle_Number='%s'",table,location,epc);				

			updateCount = stmt.executeUpdate(updateString);
			System.out.println("Updated!");	

		}
			
		con.close();  
	}
	
	catch(Exception e){ 
		System.out.println(e);
	}  
}  
}  