package ar.com.codoacodo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Conexion 
{

	public String driver="com.mysql.cj.jdbc.Driver";
	
	public Connection getConnection() throws ClassNotFoundException
	{
		Connection conexion=null; 
		try
		{
			//Tengo que publicar el driver, es decir hacerlo visible para toda la aplicacion
			Class.forName(driver);	//Es para invocar al driver del proyecto y poder verlo en todos lados del proyecto.
			conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/movies_cac", "admin", "heladote1@");
			//Los parametros son, los nombres de BDD, el usuario y la contrasena
		
		}
		catch(SQLException e)
		{
		System.out.println("Hay un error: " +e);	
		}
		
		return conexion; 
	}
	
		
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{
		Connection conexion=null; 
		Conexion con=new Conexion();
		conexion=con.getConnection();
		
		PreparedStatement ps; 
		ResultSet rs; 
		
		ps=conexion.prepareStatement("select * from peliculas");
		rs=ps.executeQuery();
		
		while(rs.next()) //next es el metodo de los result set para ver si hay algun dato siguiente para mostrar. Mientras haya info en el RS se va a ejecutar el while.
		{
			String titulo=rs.getString("titulo");
			System.out.println(titulo);
			
		}
	}

}
