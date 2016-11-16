package modelo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Usuario implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	private String nombre;
	private String apellido;
	private String email;
	
	public Usuario()
	{
		this.nombre = "";
		this.apellido = "";
		this.email = "";
	}
	
	public Usuario(Usuario u)
	{
		this.nombre = u.getNombre();
		this.apellido = u.getApellido();
		this.email = u.getEmail();
	}
	
	public String getCadena()
	{
		return this.getNombre()+" / "+this.getApellido()+ " / "+this.getEmail();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String dir_correo) {
		this.email = dir_correo;
	}
}
