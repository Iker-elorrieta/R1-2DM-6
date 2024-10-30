package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;

public class Workout implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombre, videoUrl, id, descripcion;
	private int nivel, numEjers;

	// NOMBRE DE LOS CAMPOS
	private static String workoutsCollection = "Workouts";
	private static String fieldNombre = "nombre";
	private static String fieldNivel = "nivel";
	private static String fieldNumEjers = "numEjers";
	private static String fieldVideoUrl = "video";
	private static String fieldDescripcion = "descripcion";

	// Constructor
	public Workout() {

	}

	public Workout(String nombre, String videoUrl, String descripcion, int nivel, int numEjers) {
		this.nombre = nombre;
		this.videoUrl = videoUrl;
		this.nivel = nivel;
		this.numEjers = numEjers;
		this.descripcion = descripcion;
	}

//Getters y setters
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

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getNumEjers() {
		return numEjers;
	}

	public void setNumEjers(int numEjers) {
		this.numEjers = numEjers;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ArrayList<Workout> obtenerWorkouts(Long nivelUsuario) {
		Firestore fs = null;
		ArrayList<Workout> listaWorkouts = new ArrayList<Workout>();

		try {
			fs = Conexion.conectar();
			ApiFuture<QuerySnapshot> query = fs.collection(workoutsCollection)
					.whereLessThanOrEqualTo(fieldNivel, nivelUsuario).get();
			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> workouts = querySnapshot.getDocuments();

			for (QueryDocumentSnapshot workout : workouts) {
				Workout w = new Workout();
				w.setId(workout.getId());
				w.setNombre(workout.getString(fieldNombre));

				// Cambiar de getString a getLong para los campos numéricos
				Long nivel = workout.getLong(fieldNivel);
				Long numEjers = workout.getLong(fieldNumEjers);

				// Evitar el error de casting si el valor es null
				if (nivel != null)
					w.setNivel(nivel.intValue()); // Convertir de Long a int

				if (numEjers != null)
					w.setNumEjers(numEjers.intValue()); // Convertir de Long a int

				w.setVideoUrl(workout.getString(fieldVideoUrl));
				w.setDescripcion(workout.getString(fieldDescripcion));
				listaWorkouts.add(w);

			}
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaWorkouts;
	}

	@Override
	public String toString() {
		return "" + id + " - " + nombre + " - " + nivel ;
	}
}
