package modelo;

import java.util.Date;

public class Cliente extends Usuario {

	public Cliente(String nombre, String apellido, String email, Date fechaNacimiento, TipoUsuario tipoUsuario) {
		super(nombre, apellido, email, fechaNacimiento, tipoUsuario);
	}

}
