package modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;

public class Ejercicio implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombre, descripcion, foto, id;
	private long numReps, numSeries, tiempoDescanso;
	private ArrayList<Serie> listaSeries = new ArrayList<>();

	// NOMBRE DE LOS CAMPOS
	private static String ejersCollection = "Ejercicios";
	private static String fieldNombre = "nombre";
	private static String fieldDescripcion = "descripcion";
	private static String fieldFoto = "foto";
	private static String fieldnumReps = "numRepeticiones";
	private static String fieldNumSeries = "numSeries";
	private static String fieldTiempoDescanso = "tiempoDescanso";

	public Ejercicio() {
	}

	public Ejercicio(String nombre, String descripcion, String foto, int numReps, int numSeries, int tiempoDescanso,
			String workoutId) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.foto = foto;
		this.numReps = numReps;
		this.numSeries = numSeries;
		this.tiempoDescanso = tiempoDescanso;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public long getNumReps() {
		return numReps;
	}

	public void setNumReps(long numReps) {
		this.numReps = numReps;
	}

	public long getNumSeries() {
		return numSeries;
	}

	public void setNumSeries(long numSeries) {
		this.numSeries = numSeries;
	}

	public long getTiempoDescanso() {
		return tiempoDescanso;
	}

	public void setTiempoDescanso(long tiempoDescanso) {
		this.tiempoDescanso = tiempoDescanso;
	}
	
	public ArrayList<Serie> getListaSeries() {
		return listaSeries;
	}

	public void setListaSeries(ArrayList<Serie> listaSeries) {
		this.listaSeries = listaSeries;
	}
	
	

	// Método para obtener ejercicios asociados a un Workout
	public ArrayList<Ejercicio> obtenerEjercicios(String workoutId, boolean online) {
		ArrayList<Ejercicio> listaEjercicios = new ArrayList<Ejercicio>();

		if (!online) {

			try (FileInputStream fic = new FileInputStream(Backup.FILE_WORKOUTS);
					ObjectInputStream ois = new ObjectInputStream(fic)) {

				while (fic.getChannel().position() < fic.getChannel().size()) {
					Workout workout = (Workout) ois.readObject();

					// Verifica si el workout tiene el id que estamos buscando
					if (workout.getId().equals(workoutId)) {
						listaEjercicios = workout.getListaEjercicios(); // Obtiene los ejercicios asociados
						break;
					}
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		} else {
			Firestore fs = null;

			try {
				// Conectar a Firestore
				fs = Conexion.conectar();

				DocumentReference workoutDoc =fs.collection("Workouts").document(workoutId);
				ApiFuture<QuerySnapshot> ejersQuery = workoutDoc.collection(ejersCollection).get();
				QuerySnapshot ejersSnapshot = ejersQuery.get();
				List<QueryDocumentSnapshot> ejercicios = ejersSnapshot.getDocuments();

				for (QueryDocumentSnapshot ejercicio : ejercicios) {
					Ejercicio e = new Ejercicio();
					e.setId(ejercicio.getId());
					e.setNombre(ejercicio.getString(fieldNombre));
					e.setDescripcion(ejercicio.getString(fieldDescripcion));
					e.setFoto(ejercicio.getString(fieldFoto));

					Long numReps = ejercicio.getLong(fieldnumReps);
					Long tiempoDescanso = ejercicio.getLong(fieldTiempoDescanso);

					// Si no es null, convertir Long a int
					if (numReps != null) {
						e.setNumReps(numReps);
					}
					if (tiempoDescanso != null) {
						e.setTiempoDescanso(tiempoDescanso);
					}
					e.setListaSeries(new Serie().obtenerSeries(ejersCollection, e.getId(), workoutId, online));
					// Agregar el ejercicio a la lista
					listaEjercicios.add(e);
				}

				// Cierra la conexión a Firestore después del bucle
				fs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return listaEjercicios;
	}

	@Override
	public String toString() {
		return nombre;
	}
}
