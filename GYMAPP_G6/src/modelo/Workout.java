package modelo;

import java.util.ArrayList;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;

public class Workout {

	private String nombre, videoUrl, id;
	private int nivel, numEjers;

	// NOMBRE DE LOS CAMPOS
	private static String workoutsCollection = "Workouts";
	private static String fieldNombre = "nombre";
	private static String fieldNivel = "nivel";
	private static String fieldNumEjers = "numEjers";
	private static String fieldVideoUrl = "video";

	// Constructor
	public Workout() {

	}

	public Workout(String nombre, String videoUrl, int nivel, int numEjers) {
		this.nombre = nombre;
		this.videoUrl = videoUrl;
		this.nivel = nivel;
		this.numEjers = numEjers;
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

	public ArrayList<Workout> obtenerWorkouts(Long nivelUsuario) {
		Firestore fs = null;
		ArrayList<Workout> listaWorkouts = new ArrayList<Workout>();

		try {
			fs = Conexion.conectar();
			ApiFuture<QuerySnapshot> query = fs.collection(workoutsCollection).whereEqualTo(fieldNivel, nivelUsuario)
					.get();
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
				if (nivel != null) {
					w.setNivel(nivel.intValue()); // Convertir de Long a int
				}
				if (numEjers != null) {
					w.setNumEjers(numEjers.intValue()); // Convertir de Long a int
				}

				w.setVideoUrl(workout.getString(fieldVideoUrl));
				listaWorkouts.add(w);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaWorkouts;
	}
}
