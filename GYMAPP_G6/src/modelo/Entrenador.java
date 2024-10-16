package modelo;

import java.util.Date;

public class Entrenador extends Usuario {

	public Entrenador(String nombre, String apellido, String email, Date fechaNacimiento, TipoUsuario tipoUsuario) {
		super(nombre, apellido, email, fechaNacimiento, tipoUsuario);
	}

}
