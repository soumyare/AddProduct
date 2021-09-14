package com.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddProduct
 */
public class AddProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddProduct() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//get all the values from the form
		String name = request.getParameter("name");
		String color = request.getParameter("color");
		String price = request.getParameter("price");
		response.getWriter().println("<h2>Added!</h2><br>");
		response.getWriter().println("Added "+ color + " " + name +" to the database");
		
		double priceval = 0;
		//check if the input for price is valid
		try
		{
			priceval = Double.parseDouble(price);
		}
		//not valid so set it to hard coded value
		catch (NumberFormatException e) 
		{
			priceval = 500.00;
		}
		//add to database
		try
		{
			PrintWriter out = response.getWriter();

			out.println("<br><br>");
			out.println("<h3>Database</h3>");
			out.println("<br>");

			//create connection to server
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "password");
			Statement st=conn.createStatement();
			//select product database from before
			st.executeUpdate("use pets");
			int n=st.executeUpdate("insert into pets.product(name,color,price)values('"+name+"','"+color+"','"+priceval+"')");
			
			//sql query
			ResultSet rs = st.executeQuery("select * from pets.product");
			
			ResultSetMetaData md = rs.getMetaData();
			int cols = md.getColumnCount();                     

			//print out product table

			while (rs.next()) 
			{
				for(int i = 1 ; i <= cols; i++)
				{
					out.print(rs.getString(i) + " "); 
				}
			  	out.println("<br><br>");           
			}
		}
		catch(Exception e)
		{
			System.out.print(e);
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}