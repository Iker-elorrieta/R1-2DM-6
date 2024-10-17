package modelo;





import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;

public class Usuario {

	private String id;
	private String nombre;
	private String apellido;
	private String email;
	private String usuario;
	private String password;
	private Date fechaNacimiento;
	private TipoUsuario tipoUsuario;
	
	// Nombre de los campos
	private static String usersCollection = "Usuarios";
	private static String fieldNombre = "nombre";
	private static String fieldApellido = "apellido";
	private static String fieldEmail = "email";
	private static String fieldUsuario = "usuario";
	private static String fieldContrasena = "contraseña";

	public enum TipoUsuario {
		CLIENTE, ENTRENADOR
	};

	
	//CONSTRUCTOR 	
	public Usuario() {
		
	}
	
	public Usuario(String nombre, String apellido, String email, Date fechaNacimiento, TipoUsuario tipoUsuario) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.fechaNacimiento = fechaNacimiento;
		this.tipoUsuario = tipoUsuario;
	}

	
	//GETTERS AND SETTERS
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
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Usuario ObtenerContacto(String userName) {
	    Firestore fs = null;
	    Usuario userExists = null;

	    try {
	        fs = Conexion.conectar();

	        // Realiza una consulta para buscar el documento donde el campo 'usuario' coincida con 'userName'
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
	            userExists.setPassword(usuarioDoc.getString(fieldContrasena));
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
				u.setPassword(usuario.getString(fieldContrasena));
				
				listaUsers.add(u);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		return listaUsers;
	}

}
