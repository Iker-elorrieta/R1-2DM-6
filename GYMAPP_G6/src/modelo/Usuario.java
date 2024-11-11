package modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import java.util.List;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import conexion.Conexion;

public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String nombre;
	private String apellido;
	private String email;
	private String password;
	private String usuario;
	private Long nivelUsuario;
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

	// NOMBRE DE LOS CAMPOS
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

		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.password = password;
		this.usuario = usuario;
		this.nivelUsuario = (long) nivelUsuario;
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

	public long getNivelUsuario() {
		return nivelUsuario;
	}

	public void setNivelUsuario(long nivelUsuario) {
		this.nivelUsuario = nivelUsuario;
	}

	// *** MÉTODOS CRUD ***

	public Usuario obtenerUsuario(String userName, boolean online) {

		if (!online) {
			try {
				FileInputStream fic = new FileInputStream(Backup.FILE_USERS);
				ObjectInputStream ois = new ObjectInputStream(fic);
				while (fic.getChannel().position() < fic.getChannel().size()) {
					Usuario usuario = (Usuario) ois.readObject();
					if (usuario.getUsuario().equals(userName)) {
						ois.close();
						return usuario;
					}
				}
				ois.close();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}

		Firestore fs = null;
		Usuario userExists = null;

		try {
			fs = Conexion.conectar();

			// Realiza una consulta para buscar el documento donde el campo 'usuario'
			// coincida con 'userName'
			Query query = fs.collection(usersCollection).whereEqualTo(fieldUsuario, userName);
			ApiFuture<QuerySnapshot> querySnapshot = query.get();

			// Obtener el resultado de la consulta
			List<QueryDocumentSnapshot> userDocs = querySnapshot.get().getDocuments();

			// Verificar si se encontró algún documento
			if (!userDocs.isEmpty()) {
				DocumentSnapshot usuarioDoc = userDocs.get(0); // Asumimos que solo hay un documento por usuario
				userExists = new Usuario();
				userExists.setId(usuarioDoc.getId());
				userExists.setNombre(usuarioDoc.getString(fieldNombre));
				userExists.setApellido(usuarioDoc.getString(fieldApellido));
				userExists.setEmail(usuarioDoc.getString(fieldEmail));
				userExists.setUsuario(usuarioDoc.getString(fieldUsuario)); // Asegúrate de que este campo esté correcto
				userExists.setPassword(usuarioDoc.getString(fieldPassword));
				userExists.setFechaNacimiento(usuarioDoc.getDate(fieldFecNac));
				userExists.setNivelUsuario(usuarioDoc.getLong(fieldNivel));

			}
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userExists;
	}

	public ArrayList<Usuario> obtenerMultiplesUsuarios(boolean online) {

		ArrayList<Usuario> listaUsers = new ArrayList<Usuario>();
		Firestore fs = null;

		if (!online) {
			try (FileInputStream fic = new FileInputStream(Backup.FILE_USERS);
					ObjectInputStream ois = new ObjectInputStream(fic)) {

				while (fic.getChannel().position() < fic.getChannel().size()) {
					Usuario usuario = (Usuario) ois.readObject();
					listaUsers.add(usuario);
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		} else {
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
					u.setUsuario(usuario.getString(fieldUsuario)); // Asegúrate de que este campo esté correcto
					u.setPassword(usuario.getString(fieldPassword));
					u.setFechaNacimiento(usuario.getDate(fieldFecNac));
					u.setNivelUsuario(usuario.getLong(fieldNivel));

					listaUsers.add(u);
				}
				fs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return listaUsers;
	}

	public void crearUsuario() {
		Firestore fs = null;

		try {
			fs = Conexion.conectar();
			DocumentReference newUserRef = fs.collection(usersCollection).document();

			Map<String, Object> userData = new HashMap<>();
			userData.put(fieldNombre, this.nombre);
			userData.put(fieldApellido, this.apellido);
			userData.put(fieldEmail, this.email);
			userData.put(fieldFecNac, this.fechaNacimiento);
			userData.put(fieldIdioma, this.idiomaPreferido != null ? this.idiomaPreferido.name() : null);
			userData.put(fieldTema, this.temaPreferido != null ? this.temaPreferido.name() : null);
			userData.put(fieldUsuario, this.usuario);
			userData.put(fieldPassword, this.password);
			userData.put(fieldTipoUsuario, TipoUsuario.CLIENTE);
			userData.put(fieldNivel, this.nivelUsuario);

			newUserRef.set(userData);

			try {
				fs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido registrar el usuario.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	public void insertarNuevoItemHistorial(Historial historial) {
		// TODO Auto-generated method stub
		
	}

}