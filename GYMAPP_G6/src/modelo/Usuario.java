package modelo;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;

import conexion.Conexion;

public class Usuario {

	private String nombre;
	private String apellido;
	private String email;
	private String user;
	private String password;
	private int nivelUsuario;
	private Date fechaNacimiento;
	private IdiomaPreferido idiomaPreferido;
	private TemaPreferido temaPreferido;
	private TipoUsuario tipoUsuario;

	public enum IdiomaPreferido {
		ESPAÑOL, INGLES
	};

	public enum TemaPreferido {
		CLARO, OSCURO
	};

	public enum TipoUsuario {
		CLIENTE, ENTRENADOR
	};

	private static String usersCollection = "Usuarios";
	private static String fieldNombre = "nombre";
	private static String fieldApellido = "apellido";
	private static String fieldEmail = "email";
	private static String fieldFecNac = "fecNac";
	private static String fieldIdioma = "idioma";
	private static String fieldNivel = "nivel";
	private static String fieldTema = "tema";
	private static String fieldUsuario = "usuario";
	private static String fieldPassword = "contraseña";
	private static String fieldTipoUsuario = "tipoUsuario";

	// CONSTRUCTORES

	public Usuario() {

	}

	public Usuario(String nombre, String apellido, String email, String user, String password, int nivelUsuario,
			Date fechaNacimiento, IdiomaPreferido idiomaPreferido, TemaPreferido temaPreferido,
			TipoUsuario tipoUsuario) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.user = user;
		this.password = password;
		this.nivelUsuario = 0;
		this.fechaNacimiento = fechaNacimiento;
		this.idiomaPreferido = idiomaPreferido;
		this.temaPreferido = temaPreferido;
		this.tipoUsuario = tipoUsuario;

	}

	// GETTERS AND SETTERS

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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public IdiomaPreferido getIdiomaPreferido() {
		return idiomaPreferido;
	}

	public void setIdiomaPreferido(IdiomaPreferido idiomaPreferido) {
		this.idiomaPreferido = idiomaPreferido;
	}

	public TemaPreferido getTemaPreferido() {
		return temaPreferido;
	}

	public void setTemaPreferido(TemaPreferido temaPreferido) {
		this.temaPreferido = temaPreferido;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public int getNivelUsuario() {
		return nivelUsuario;
	}

	public void setNivelUsuario(int nivelUsuario) {
		this.nivelUsuario = nivelUsuario;
	}

	// *** MÉTODOS CRUD ***

	public void crearUsuario() {
		Firestore co = null;

		try {
			co = Conexion.conectar();
			DocumentReference newUserRef = co.collection(usersCollection).document();

			Map<String, Object> userData = new HashMap<>();
			userData.put(fieldNombre, this.nombre);
			userData.put(fieldApellido, this.apellido);
			userData.put(fieldEmail, this.email);
			userData.put(fieldFecNac, this.fechaNacimiento);
			userData.put(fieldIdioma, this.idiomaPreferido != null ? this.idiomaPreferido.name() : null);
			userData.put(fieldTema, this.temaPreferido != null ? this.temaPreferido.name() : null);
			userData.put(fieldUsuario, this.user);
			userData.put(fieldPassword, this.password);
			userData.put(fieldTipoUsuario, TipoUsuario.CLIENTE);
			userData.put(fieldNivel, this.nivelUsuario);

			newUserRef.set(userData);

			System.out.println("Registro correcto en la DB");
			try {
				co.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
