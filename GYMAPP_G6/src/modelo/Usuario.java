package modelo;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import conexion.Conexion;

public class Usuario {

	private String id;
	private String nombre;
	private String apellido;
	private String email;
	private String user;
	private String password;
	private String usuario;
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

	public Usuario(String nombre, String apellido, String email, String user, String password, String usuario,
			int nivelUsuario, Date fechaNacimiento, IdiomaPreferido idiomaPreferido, TemaPreferido temaPreferido,
			TipoUsuario tipoUsuario) {
		super();

		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.user = user;
		this.password = password;
		this.usuario = usuario;
		this.nivelUsuario = 0;
		this.fechaNacimiento = fechaNacimiento;
		this.idiomaPreferido = idiomaPreferido;
		this.temaPreferido = temaPreferido;
		this.tipoUsuario = tipoUsuario;

	}

	// GETTERS AND SETTERS
	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getNivelUsuario() {
		return nivelUsuario;
	}

	public void setNivelUsuario(int nivelUsuario) {
		this.nivelUsuario = nivelUsuario;
	}

	// *** MÉTODOS CRUD ***

	public Usuario ObtenerContacto(String userName) {
		Firestore fs = null;
		Usuario userExists = null;

		try {
			fs = Conexion.conectar();

			// Realiza una consulta para buscar el documento donde el campo 'usuario'
			// coincida con 'userName'
			Query query = fs.collection("Usuarios").whereEqualTo("usuario", userName);
			ApiFuture<QuerySnapshot> querySnapshot = query.get();

			// Obtener el resultado de la consulta
			List<QueryDocumentSnapshot> userDocs = querySnapshot.get().getDocuments();

			// Verificar si se encontró algún documento
			if (!userDocs.isEmpty()) {
				DocumentSnapshot usuarioDoc = userDocs.get(0); // Asumimos que solo hay un documento por usuario
				userExists = new Usuario();
				userExists.setId(usuarioDoc.getId());
				userExists.setNombre(usuarioDoc.getString(fieldNombre));
				userExists.setEmail(usuarioDoc.getString(fieldEmail));
				userExists.setUsuario(usuarioDoc.getString("usuario")); // Asegúrate de que este campo esté correcto
				userExists.setPassword(usuarioDoc.getString(fieldPassword));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userExists;
	}

	public ArrayList<Usuario> ObtenerContactos() {
		Firestore fs = null;

		ArrayList<Usuario> listaUsers = new ArrayList<Usuario>();

		try {
			fs = Conexion.conectar();

			ApiFuture<QuerySnapshot> query = fs.collection(usersCollection).get();

			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> usuarios = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot usuario : usuarios) {

				Usuario u = new Usuario();
				u.setId(usuario.getId());
				u.setNombre(usuario.getString(fieldNombre));
				u.setApellido(usuario.getString(fieldApellido));
				u.setEmail(usuario.getString(fieldEmail));
				u.setUsuario(usuario.getString(fieldUsuario));
				u.setPassword(usuario.getString(fieldPassword));

				listaUsers.add(u);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaUsers;
	}

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
