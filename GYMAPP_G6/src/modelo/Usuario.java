package modelo;

import java.util.Date;

public class Usuario {

	private String nombre;
	private String apellido;
	private String email;
	private Date fechaNacimiento;
	private TipoUsuario tipoUsuario;

	public enum TipoUsuario {
		CLIENTE, ENTRENADOR
	};

	
	//CONSTRUCTOR 	
	public Usuario(String nombre, String apellido, String email, Date fechaNacimiento, TipoUsuario tipoUsuario) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.fechaNacimiento = fechaNacimiento;
		this.tipoUsuario = tipoUsuario;
	}

	
	//GETTERS AND SETTERS
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

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

}
