import java.sql.*;
import java.io.*;

public class SQLiteConnection {

	public static void main(String[] args) throws IOException {
	BufferedReader buffer= new BufferedReader(new InputStreamReader(System.in));
	Display();
	System.out.print("Please Input Your Choice:");
	
	boolean judgement = true;
	while (judgement){
	String choice = buffer.readLine();
	if (choice.equals("1"))
	{
		CreateTable();
		judgement = false;
	}
	
	
	else if (choice.equals("2"))
	{
		System.out.println("Please Type the Table Name you want to Drop");
		String TableDropped = buffer.readLine();
		String DropSql = "DROP TABLE "+ TableDropped+";";
		DROP(DropSql,TableDropped);
		judgement = false;
	}
	
	
	else if (choice.equals("3"))
	{
		System.out.println("Please Enter Select Command");
		String selectsql = buffer.readLine();
		SELECT(selectsql);
		judgement = false;
	}
	
	
	
	else if (choice.equals("4"))
	{
		System.out.println("Please Enter the Table Name and Attributes to be Inserted."
				+" Command Syntax: 'insert into <table_name>(<attribute1>,<attribute2>,....)' ");
		String insertsql1=buffer.readLine();
		System.out.println("Please Enter the Values You Want to Insert."
				+" Command Syntax: 'values(<value1>,<value2>,....)'");
		String insertsql2=buffer.readLine();
		INSERT(insertsql1,insertsql2);	
		judgement = false;
	}
	
	else if (choice.equals("5"))
	{
		System.out.println("Please Enter Update Command(SQLite UPDATE Syntax: UPDATE<table_name> SET <column1>=<value1>,<column2>=<value2>.... WHERE <some_columns>=<some_values>)");
		String updatesql = buffer.readLine();
		UPDATE(updatesql);
		judgement = false;
	}
	
	else if (choice.equals("6"))
	{
		System.out.println("Please Enter Delete Command(SQLite DELETE Syntax: DELETE FROM <table_name> WHERE <some_columns>=<some_values>)");
		String deletesql = buffer.readLine();
		UPDATE(deletesql);
		judgement = false;
	}
	
	else {
		System.out.println("Please Enter the Number From 1 to 5!");
		System.out.println("Please Enter Again:");
	}
	
	}
	}

public static void Display(){
	System.out.println("Press 1 to Create A New Table");
	System.out.println("Press 2 to Drop An Existing Table");
	System.out.println("Press 3 for SELECT Operation");
	System.out.println("Press 4 for INSERT Operation");
	System.out.println("Press 5 for UPDATE Operation");
	System.out.println("Press 6 for DELETE Operation");
	
	}

public static void CreateTable(){
	try{
		Connection SQLiteCon = null;
		Statement stmt = null;
		Class.forName("org.sqlite.JDBC");
		SQLiteCon = DriverManager.getConnection("jdbc:sqlite://d:/SQLite/test.db");
		System.out.println("Connection established!");
		stmt = SQLiteCon.createStatement();
		System.out.println("Please Enter Table Name:");
		BufferedReader buffer= new BufferedReader(new InputStreamReader(System.in));
		String Table_Name = buffer.readLine();
		System.out.println("Please Enter COLUMN NAME and DATA TYPE in SQLite Syntax(For Example:<name int>)");
		String Column[]= new String[100];
		int i = 0;
		int j = 1;
		boolean status = true;
		String Sql_Column="";
		
		while (status){
			boolean AddColumn = true;
			System.out.print("Enter Column "+j+" Name and Data Type:");
			Column[i] = buffer.readLine();
			//System.out.println(Column[i]);
			System.out.println("");
			System.out.println("Still Adding Column?(Yes/No)");
			
			while (AddColumn){
				String AddColu = buffer.readLine();
				if (AddColu.equals("Yes") || AddColu.equals("yes") || AddColu.equals("YES")){
					i++;
					j++;
					AddColumn = false;
				}
				else if (AddColu.equals("No") || AddColu.equals("no") || AddColu.equals("NO")){
					System.out.println("Input is Over");
					AddColumn = false;
					status =false;
				}
				else {
					AddColumn = true;
					System.out.println("Your Input is INVALID, Please Type Yes or No");
				}
					
			}
			
		}
		for (int k = 0; k<i;k++)
		{
			Sql_Column = Sql_Column + Column[k]+",";
		}
		
		String Sql = "CREATE TABLE "+ Table_Name + "("+Sql_Column+Column[i]+")"+";";
		stmt.executeUpdate(Sql);
		System.out.println("Table "+ Table_Name+" Created!");
		stmt.close();
		SQLiteCon.close();
	}	catch (Exception e) {
		System.err.println(e.getClass().getName()+ ":" + e.getMessage());
		System.exit(0);
	}	
}

public static void DROP(String Str1,String Str2){
	try{
		Connection SQLiteCon = null;
		Statement stmt = null;
		Class.forName("org.sqlite.JDBC");
		SQLiteCon = DriverManager.getConnection("jdbc:sqlite://d:/SQLite/test.db");
		System.out.println("Connection established!");
		stmt = SQLiteCon.createStatement();
		String sql=Str1;	
		stmt.executeUpdate(sql);
		System.out.println("Table "+ Str2 + " Has Been Dropped");
		stmt.close();
		SQLiteCon.close();
	}
	catch(SQLException | ClassNotFoundException e){
		System.out.println("delete Error!");
		e.printStackTrace();
	}
}

public static void SELECT(String Str){
  try {
	Connection SQLiteCon = null;
	Statement stmt = null;
	Class.forName("org.sqlite.JDBC");
	SQLiteCon = DriverManager.getConnection("jdbc:sqlite://d:/SQLite/test.db");
	System.out.println("Connection established!");
	stmt = SQLiteCon.createStatement();
	String sql=Str;
	ResultSet rs=stmt.executeQuery(sql);
	ResultSetMetaData rsmd;	
	System.out.println("The Query result are:");
	rsmd = rs.getMetaData();
	int NumOfCols = rsmd.getColumnCount();	
	for(int i = 1;i<=NumOfCols;i++)
		System.out.print(rsmd.getColumnName(i)+"\t");
	System.out.println();	
	boolean flag = rs.next();
	while(flag){
		for(int i=1; i<=NumOfCols;i++)
			System.out.print(rs.getString(i)+"\t");
		System.out.println();
		flag = rs.next();
	}
	
	rs.close(); 
	stmt.close();
	SQLiteCon.close();
	System.out.println("executed!");
  } catch(SQLException | ClassNotFoundException e){
		System.out.println("Select Error!");
		e.printStackTrace();
}
}

public static void INSERT(String Str1, String Str2){
	try{
		Connection SQLiteCon = null;
		Statement stmt = null;
		Class.forName("org.sqlite.JDBC");
		SQLiteCon = DriverManager.getConnection("jdbc:sqlite://d:/SQLite/test.db");
		System.out.println("Connection Established!");
		stmt = SQLiteCon.createStatement();
		String sql=Str1+Str2;
		int count = stmt.executeUpdate(sql);
		System.out.println(count+" Record(s) Has/Have Been Inserted");	
		stmt.close();
		SQLiteCon.close();
		System.out.println("Executed!");
	}
	catch(SQLException | ClassNotFoundException e){
		System.out.println("Insertion Error!");
		e.printStackTrace();
		}
}

public static void UPDATE(String Str){
	try{
		Connection SQLiteCon = null;
		Statement stmt = null;
		Class.forName("org.sqlite.JDBC");
		SQLiteCon = DriverManager.getConnection("jdbc:sqlite://d:/SQLite/test.db");
		System.out.println("Connection established!");
		stmt = SQLiteCon.createStatement();
		String sql=Str;	
		int count = stmt.executeUpdate(sql);
		System.out.println(count+" Record(s) Has/Have Been Updated");
		stmt.close();
		SQLiteCon.close();
		System.out.println("Executed!");
	}
	catch(SQLException | ClassNotFoundException e){
		System.out.println("Update Error!");
		e.printStackTrace();
	}
}

public static void DELETE(String Str){
	try{
		Connection SQLiteCon = null;
		Statement stmt = null;
		Class.forName("org.sqlite.JDBC");
		SQLiteCon = DriverManager.getConnection("jdbc:sqlite://d:/SQLite/test.db");
		System.out.println("Connection Established!");
		stmt = SQLiteCon.createStatement();
		String sql=Str;	
		int count = stmt.executeUpdate(sql);
		System.out.println(count+" Record(s) Has/Have Been Deleted");
		stmt.close();
		SQLiteCon.close();
		System.out.println("Executed!");
	}
	catch(SQLException | ClassNotFoundException e){
		System.out.println("Delete Error!");
		e.printStackTrace();
	}
}

}


