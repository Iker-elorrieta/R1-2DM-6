package modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;

public class Workout implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombre, videoUrl, id, descripcion;
	private int nivel, numEjers;
	private ArrayList<Ejercicio> listaEjercicios = new ArrayList<>(); // Lista de Ejercicios asociados al Workout

	// Nombres de los campos en Firestore
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
		this.listaEjercicios = new ArrayList<>();
	}

	// Getters y setters
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

	public ArrayList<Ejercicio> getListaEjercicios() {
		return listaEjercicios;
	}

	public void setListaEjercicios(ArrayList<Ejercicio> listaEjercicios) {
		this.listaEjercicios = listaEjercicios;
	}

	public ArrayList<Workout> obtenerWorkouts(Long nivelUsuario, boolean online) {
		ArrayList<Workout> listaWorkouts = new ArrayList<Workout>();

		if (!online) {
			try (FileInputStream fic = new FileInputStream(Backup.FILE_WORKOUTS);
					ObjectInputStream ois = new ObjectInputStream(fic)) {

				while (fic.getChannel().position() < fic.getChannel().size()) {
					Workout workout = (Workout) ois.readObject();

					// workout level
					if (workout.getNivel() <= nivelUsuario) {
						listaWorkouts.add(workout);
					}
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}

		} else {
			Firestore fs = null;

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

					w.setListaEjercicios(new Ejercicio().obtenerEjercicios(workoutsCollection, online));

				}
				fs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return listaWorkouts;
	}

	public Workout obtenerWorkoutPorId(String workoutId, boolean online) {
		Workout workoutEncontrado = null;

		if (!online) {
			try (FileInputStream fic = new FileInputStream(Backup.FILE_WORKOUTS);
					ObjectInputStream ois = new ObjectInputStream(fic)) {

				while (fic.getChannel().position() < fic.getChannel().size()) {
					Workout workout = (Workout) ois.readObject();

					if (workout.getId().equals(workoutId)) {
						workoutEncontrado = workout;
						break;
					}
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		} else {
			Firestore fs = null;

			try {
				fs = Conexion.conectar();

				// Busca el documento específico que coincide con el id proporcionado
				DocumentReference docRef = fs.collection(workoutsCollection).document(workoutId);
				ApiFuture<DocumentSnapshot> future = docRef.get();
				DocumentSnapshot document = future.get();

				// Verifica si el documento existe
				if (document.exists()) {
					workoutEncontrado = new Workout();
					workoutEncontrado.setId(document.getId());
					workoutEncontrado.setNombre(document.getString(fieldNombre));

					// Campos numéricos: manejo de nulos y conversión de Long a int
					Long nivel = document.getLong(fieldNivel);
					Long numEjers = document.getLong(fieldNumEjers);

					if (nivel != null)
						workoutEncontrado.setNivel(nivel.intValue());
					if (numEjers != null)
						workoutEncontrado.setNumEjers(numEjers.intValue());

					workoutEncontrado.setVideoUrl(document.getString(fieldVideoUrl));
					workoutEncontrado.setDescripcion(document.getString(fieldDescripcion));

				}

				fs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return workoutEncontrado;
	}

	@Override
	public String toString() {
		return "" + id + " - " + nombre + " - " + nivel;
	}
}
