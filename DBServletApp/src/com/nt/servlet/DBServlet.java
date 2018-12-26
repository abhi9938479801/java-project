package com.nt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DBServlet extends HttpServlet {
	private static final String GET_EMP_DETAILS_BY_NO="SELECT EMPNO,ENAME,JOB,SAL FROM EMP WHERE EMPNO=?";
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter pw=null;
		int no=0;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//general setting
		pw=res.getWriter();
		res.setContentType("text/html");
		//read form data
		try {
		no=Integer.parseInt(req.getParameter("eno"));
		//register jdbc driver s/w
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//establish the connection
		con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott","tiger");
		//create prepared statment obj
		ps=con.prepareStatement(GET_EMP_DETAILS_BY_NO);
		//set value to query param
		ps.setInt(1, no);
		//execuate the query
		rs=ps.executeQuery();
		//process the result
		pw.println("<h1 style='color:red'>Employee details are</h1>");
		if(rs.next()) {
			pw.println("<b>Employee number::"+rs.getInt(1));
			pw.println("<b>Employee name::"+rs.getString(2));
			pw.println("<b>Employee desg::"+rs.getString(3));
			pw.println("<b>Employee salary::"+rs.getInt(4));	
		}
		else {
			pw.println("<h1 style='color:red'>Employee Not Found</h1>");
		}
	}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(rs!=null)
					rs.close();
			}//try
			catch(Exception e) {
				e.printStackTrace();
			}//catch
			try {
				if(con!=null)
					con.close();
			}//try
			catch(Exception e) {
				e.printStackTrace();
			}//catch
			try {
				if(ps!=null)
					ps.close();
			}//try
			catch(Exception e) {
				e.printStackTrace();
			}//catch
		 }//finally	
		//add hyperlink
		pw.println("<a href='input.html>home</a>");
		//close straem
		pw.close();
	}//doGet(-,-)
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		doGet(req,res);
	}

}
