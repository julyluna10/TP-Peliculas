package ar.com.codoacodo.db;

import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeliculaService {

	 private Conexion conexion; 
	 public PeliculaService()
	 {
		 this.conexion=new Conexion();
		  
	 }
	 
	 //Si yo quiero listar la pelicula, lo ideal es devolver una LISTA DE OBJETOS del tipo pelicula
	 public List<Pelicula> getAllPeliculas() throws SQLException, ClassNotFoundException
	 {
		 List<Pelicula> peliculas= new ArrayList<>();
		 Connection con=conexion.getConnection();
		 
		 String sql="Select * from peliculas";
		 
		 PreparedStatement ps=con.prepareStatement(sql);
		 ResultSet rs=ps.executeQuery();
		 
		 while(rs.next())
		 {
			       Pelicula pelicula= new Pelicula(
					 rs.getInt("id"), //Esto convierte un dato del tipo lo que tiene el rs en la primer columna que es ID
					 rs.getString("titulo"),
					 rs.getString("genero"),
					 rs.getString("duracion"),
					 rs.getString("director"),
					 rs.getString("reparto"),
					 rs.getString("sinopsis"),
					 rs.getString("imagen")				 
					 ); 
			 peliculas.add(pelicula);
		 }
		 
		 rs.close();
		 ps.close();
		 return peliculas;
	 }
	 
	 public Pelicula getPeliculaById(int id) throws SQLException, ClassNotFoundException
	 {
		 Pelicula pelicula=null; 
		 Connection con=conexion.getConnection();
		 String sql="Select * from peliculas where id= ?";
		 PreparedStatement ps=con.prepareStatement(sql);
		 ps.setInt(1, id); // en el primer signo de pregunta, completame el signo con el parametro id. Se llama consulta precompilada
		 
		 //Que pasa si tengo 9 signos de pregunta? el numero me dice que reemplace el signo de pregunta numero x por el parametro
		 ResultSet rs=ps.executeQuery();
		 
		 while(rs.next())
		 {
			         pelicula= new Pelicula(
					 rs.getInt("id"), //Esto convierte un dato del tipo lo que tiene el rs en la primer columna que es ID
					 rs.getString("titulo"),
					 rs.getString("genero"),
					 rs.getString("duracion"),
					 rs.getString("director"),
					 rs.getString("reparto"),
					 rs.getString("sinopsis"),
					 rs.getString("imagen")				 
					 ); 
		 }
		 
		 rs.close();
		 ps.close();
		 return pelicula;
		 
		
	 }
	 
	//Ahora creamos un metodo para poder agregar una pelicula.
	 //A este metodo, le paso un objeto pelicula para que lo inserte en la BDD
	 
	 public void addPelicula(Pelicula pelicula) throws SQLException, ClassNotFoundException
	 {
		 Connection con=conexion.getConnection();
		 String sql="insert into peliculas (titulo,genero,duracion,director,reparto,sinopsis,imagen)" 
		 + "VALUES (?,?,?,?,?,?,?)"; // no le paso el ID porque se agrega solo porque es AUTOINCREMENTAL
		 PreparedStatement ps=con.prepareStatement(sql);
		 
		 ps.setString(1, pelicula.getTitulo());
		 ps.setString(2, pelicula.getGenero());
		 ps.setString(3, pelicula.getDuracion());
		 ps.setString(4, pelicula.getDirector());
		 ps.setString(5, pelicula.getReparto());
		 ps.setString(6, pelicula.getSinopsis());
		 ps.setString(7, pelicula.getImagen());
		 
		 ps.executeUpdate(); //Yo quiero actualizar o crear algo 
		 ps.close();
		 con.close();
	 }
	 
	 
	 public void updatePelicula(Pelicula pelicula) throws SQLException, ClassNotFoundException 
	    {
	        Connection con = conexion.getConnection();
	        String sql = "UPDATE peliculas SET titulo = ?,  genero = ?, duracion = ?, director = ?, reparto = ?, sinopsis = ?, imagen = ? WHERE id = ?";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, pelicula.getTitulo());      
	        ps.setString(2, pelicula.getGenero());
	        ps.setString(3, pelicula.getDuracion());
	        ps.setString(4, pelicula.getDirector());
	        ps.setString(5, pelicula.getReparto());
	        ps.setString(6, pelicula.getSinopsis());
	        ps.setString(7, pelicula.getImagen());
	        ps.setInt(8, pelicula.getId());
	        ps.executeUpdate();
	        ps.close();
	        con.close();
	    }
	 
	 public void deletePelicula(int id)throws SQLException, ClassNotFoundException
	 {
		 Connection con=conexion.getConnection();
		 String sql=" DELETE FROM peliculas WHERE id=?";
		 PreparedStatement ps=con.prepareStatement(sql);
		 ps.setInt(1, id);
		 
		 ps.executeUpdate(); 
		 ps.close();
		 con.close();
		 
	 }
	 
	 
	 
	 
}
