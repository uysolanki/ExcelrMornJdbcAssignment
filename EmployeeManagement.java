package day35.jdbc.assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class EmployeeManagement {
	static Connection con;
	static Statement st;
	static String query;
	static PreparedStatement ps;
	static ResultSet rs;
	static Scanner sc =new Scanner(System.in);
	
	public static void main(String[] args) throws Exception{
		do
		{
		System.out.println("1.Create Connection"
				+ "\n2.Create Table"
				+"\n3.Add Employee"
				+"\n4.View All Records"
				+"\n5.Search Employee"
				+"\n6.Update Employee"
				+"\n7.Delete Employee"
				+"\n8.Exit");
		System.out.println("Enter Choice:");
		int ch=sc.nextInt();
		switch(ch)
		{
		case 1:
			createConnection();
			break;
		case 2:
			createTable();
			break;
		case 3:
			addEmp();
			break;
		case 4:
			viewAllEmployees();
			break;
		case 5:
			System.out.println("1.By Name\n2.By ID");
			System.out.println("Enter Choice:");
			int ch1=sc.nextInt();
			switch(ch1)
			{
			case 1:
				System.out.println("Enter Name To Search:");
				String s= sc.next();
				searchEmployee(s);
			break;
			case 2:
				System.out.println("Enter ID To Search:");
				int id= sc.nextInt();
				searchEmployee(id);
			break;
			}
			break;
		case 6:
			updateEmployee();
			break;
		case 7:
			deleteEmployee();
			break;
		case 8:
			System.exit(0);
		default:
			System.out.println("Invalid Input");		
		}
		}while(true);
	}

	private static void deleteEmployee() throws Exception{
		System.out.println("Enter ID To Delete");
		int id=sc.nextInt();
		query="delete from employees where eno=? ";
		ps=con.prepareStatement(query);
		ps.setInt(1, id);
		ps.executeUpdate();
		System.out.println("Employee Deleted");
		
	}

	private static void updateEmployee() throws Exception{
		System.out.println("Enter ID To Update");
		int id=sc.nextInt();
		System.out.println("Enter New Employee Name:");
		String ename=sc.next();
		System.out.println("Enter New Employee Department:");
		String department=sc.next();
		System.out.println("Enter New Employee Salary:");
		int salary=sc.nextInt();
		query="update employees set ename = ?,department = ?,salary = ? where eno = ?";
		ps=con.prepareStatement(query);
		ps.setString(1, ename);
		ps.setString(2, department);
		ps.setInt(3, salary);
		ps.setInt(4, id);
		ps.executeUpdate();
		System.out.println("Record Updated");
	}

	private static void searchEmployee(int id) throws Exception {
		query="select * from employees where eno =?  ";
		ps=con.prepareStatement(query);
		ps.setInt(1, id);
		rs=ps.executeQuery();
		if(rs.next())
		{
		System.out.println(rs.getInt("eno")+" "+rs.getString("ename")+" "+rs.getString("department")+" "+ rs.getInt("salary"));
		}
		
	}

	private static void searchEmployee(String s) throws Exception {
		query="select * from employees where ename like ? ";
		ps=con.prepareStatement(query);
		ps.setString(1, s);
		rs=ps.executeQuery();
		while(rs.next())
		{
		System.out.println(rs.getInt("eno")+" "+rs.getString("ename")+" "+rs.getString("department")+" "+ rs.getInt("salary"));
		}
		
	}

	

	private static void viewAllEmployees() throws Exception{
		query="select * from employees";
		rs=st.executeQuery(query);
		System.out.println(String.format("%-10s","Eno")+String.format("%-10s","Ename")+String.format("%-15s","Department")+String.format("%-15s","Salary"));
		while(rs.next())
		{
		System.out.println(String.format("%-10s",rs.getInt("eno"))+String.format("%-10s",rs.getString("ename"))+String.format("%-15s",rs.getString("department"))+String.format("%-15s",rs.getInt("salary")));
		//System.out.println(rs.getInt("eno")+" "+rs.getString("ename")+" "+rs.getString("department")+" "+ rs.getInt("salary"));
		}
		
	}

	private static void addEmp() throws Exception{
		Scanner sc =new Scanner(System.in);
		System.out.println("Enter Employee ID:");
		int id=sc.nextInt();
		System.out.println("Enter Employee Name:");
		String ename=sc.next();
		System.out.println("Enter Employee Department:");
		String department=sc.next();
		System.out.println("Enter Employee Salary:");
		int salary=sc.nextInt();
		
		query="insert into employees values(?,?,?,?)";
		 ps=con.prepareStatement(query);
		 ps.setInt(1,id);
        ps.setString(2,ename);
        ps.setString(3,department);
        ps.setInt(4,salary);
 
        int n=ps.executeUpdate();
        
        if(n>0)
        {
        	System.out.println(n+" Record Inserted");
        }
        else
        {
        	System.out.println("No Record Inserted");
        }
		
	}

	private static void createTable() throws Exception{
		query="create table employees(\r\n"
				+ "eno int(5) primary key,\r\n"
				+ "ename varchar(20),\r\n"
				+ "department varchar(10),\r\n"
				+ "salary int(10)\r\n)";
		st=con.createStatement();
		st.execute(query);
		System.out.println("Table Created");
		
	}

	private static void createConnection() throws Exception {
		
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		//establishing the connection
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_management","root","kedar@210302");
		System.out.println("Connection Established");
		st=con.createStatement();
	}

}
