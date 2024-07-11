package ar.com.codoacodo.db;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper; 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List; 

@WebServlet("/peliculas/*")

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;


public class PeliculaServlet extends HttpServlet {
	
	private PeliculaService peliculaService;
	private ObjectMapper objectMapper; 
	
	@Override
    public void init() throws ServletException 
    {
        peliculaService = new PeliculaService();
        objectMapper = new ObjectMapper();
    }
	
	  @Override
	  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	  {
		  String pathInfo=req.getPathInfo(); 
		  try
		  {
			  
			  //
			  if(pathInfo==null||pathInfo.equals("/")) //la barrita sola quiere decir que despues de la barra no hay nada. 
			  {
				  List<Pelicula> peliculas=peliculaService.getAllPeliculas(); //Aca obtengo todas las peliculas
				  String json = objectMapper.writeValueAsString(peliculas); //Tengo que transformarlas en JSON
				  resp.setContentType("application/json"); //Aca le digo que lo que le voy a mandar es un json
				  resp.getWriter().write(json);//Le devolvi a mi front todas las peliculas en formato JSON.
			  }
			  else //aca voy a contestar si me preguntan por alguna pelicula especifica
			  {
				  String[] pathParts = pathInfo.split("/"); //
				  int id=Integer.parseInt(pathParts[1]);// convierto lo que viene despues de la barra y ese es el ID
				  Pelicula pelicula=peliculaService.getPeliculaById(id);//Aca obtengo peliculas por ID. 
				  if(pelicula!=null)
				  {
					  String json = objectMapper.writeValueAsString(pelicula);
					  resp.setContentType("application/json");
					  resp.getWriter().write(json); 
				  }
				  else
				  {
					  resp.sendError(HttpServletResponse.SC_NOT_FOUND);
				  }
				  
				  
				  
			  } 
			  
		  }
		  catch(SQLException|ClassNotFoundException e)
		  {
			  resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		  }
		  
	  }
	  
	  //Aca al object mapper le paso algo, es decir le paso directamente un json. El cual esta metido en req. 
	  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	  {
		  try
		  {
			  Pelicula pelicula =objectMapper.readValue(req.getReader(),Pelicula.class);//Esto es para que lea todo lo que hay en req. Y me convierte los datos en clase de tipo pelicula
			  peliculaService.addPelicula(pelicula);
			  resp.setStatus(HttpServletResponse.SC_CREATED);  //Devolve el estatus de lo que paso aca para ver si el dato se inserto correctamente
		  }
		  catch(SQLException|ClassNotFoundException e)
		  {
			  resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		  }
		  
		  catch(Exception e)
		  {
			  resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR , e.getMessage());
		  }
		  
	  }
	
	  //Este metodo es para actualizar una pelicula
	  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	  {
		  try
		  {
			  Pelicula pelicula =objectMapper.readValue(req.getReader(),Pelicula.class);//Esto es para que lea todo lo que hay en req. Y me convierte los datos en clase de tipo pelicula
			  
			  peliculaService.updatePelicula(pelicula);
			  
			  resp.setStatus(HttpServletResponse.SC_CREATED);  //Devolve el estatus de lo que paso aca para ver si el dato se inserto correctamente
		  }
		  catch(SQLException|ClassNotFoundException e)
		  {
			  resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		  }
		  
	  }
	
	  @Override
	  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  
	  {
		  String pathInfo=req.getPathInfo(); 
		  try
		  {
			  
			  if(pathInfo!=null&&pathInfo.split("/").length>1) //la barrita sola quiere decir que despues de la barra no hay nada. 
			  {
				  int id= Integer.parseInt(pathInfo.split("/")[1]);
				  peliculaService.deletePelicula(id);
				  
				  resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
			  }
			  else
			  {
				  resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			  }
			  
		  }
		  catch(SQLException|ClassNotFoundException e)
		  {
			  resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		  }
	  }
				  
				  
				  
	}


