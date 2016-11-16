package com;

import java.io.*;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import modelo.Usuario;
import modelo.BDUsuario;

public class ListaCorreosServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//GET llama a POST
	@Override
	protected void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws ServletException, IOException {
		doPost(peticion, respuesta);
	}
	
	@Override
	protected void doPost(HttpServletRequest peticion, HttpServletResponse respuesta) throws ServletException, IOException {
		String accion = peticion.getParameter("action");
		String nombre = peticion.getParameter("nombre");
		String apellido = peticion.getParameter("apellido");
		String email = peticion.getParameter("email");
		//Si no hay acción; listamos los usuarios
		if (accion == null) 
		{	
			respuesta.setContentType("text/html");
			respuesta.setCharacterEncoding("UTF-8");
			PrintWriter writer = respuesta.getWriter();
			writer.println("<h1>Usuarios</h1>");
			writer.println("<h2>Nombre    |   Apellidos     |    Email  </h2>");
			for (Usuario usuario: BDUsuario.listarUsuarios())
				writer.println("<h3>"+usuario.getCadena()+"</h3>");
		} 
		else //En caso de que exista acción, se llama a su función
		{
			ObjectOutputStream oos = new ObjectOutputStream(respuesta.getOutputStream()); 
			
			switch (accion) {
			case "aniadirUsuario":
				if (BDUsuario.select(email)==null) {
					Usuario usuario = new Usuario();
					usuario.setNombre(nombre);
					usuario.setApellido(apellido);
					usuario.setEmail(email);
					BDUsuario.insert(usuario);
					oos.writeInt(0);
					oos.writeObject("Usuario aniadido correctamente.");
				} else {
					oos.writeInt(1);
					oos.writeObject("Ya existe un usuario con el email " + email + ".");
				}
				break;
			case "actualizarUsuario":
				if (BDUsuario.select(email)!=null) {
					Usuario usuario = BDUsuario.select(email);
					usuario.setNombre(nombre);
					usuario.setApellido(apellido);
					BDUsuario.update(usuario);
					oos.writeInt(0);
					oos.writeObject("Usuario actualizado correctamente.");
				} else {
					oos.writeInt(1);
					oos.writeObject("No existe un usuario con el email " + email + ".");
				}
				break;
			case "eliminarUsuario":
				if (BDUsuario.select(email)!=null) {
					Usuario usuario = BDUsuario.select(email);
					BDUsuario.delete(usuario);
					oos.writeInt(0);
					oos.writeObject("Usuario eliminado correctamente.");
				} else {
					oos.writeInt(1);
					oos.writeObject("No existe un usuario con el email " + email + ".");
				}
				break;
	  		default: // por defecto lista usuarios
	  			List<Usuario> usuarios2 = BDUsuario.listarUsuarios();
	  			oos.writeObject(usuarios2);
			}
			
			oos.flush();
			oos.close();
		}		
	}
}