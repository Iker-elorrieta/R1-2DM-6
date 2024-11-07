package modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;

public class Ejercicio implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombre, descripcion, foto;
	private long numReps, numSeries, tiempoDescanso;

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

	// Método para obtener ejercicios asociados a un Workout
	public ArrayList<Ejercicio> obtenerEjercicios(String workoutId, boolean online) {
		ArrayList<Ejercicio> listaEjercicios = new ArrayList<Ejercicio>();

		if (!online) {

		} else {
			Firestore fs = null;

			try {
				// Conectar a Firestore
				fs = Conexion.conectar();

				// Acceder a la subcolección Ejercicios dentro del documento de Workouts con
				// workoutId
				ApiFuture<QuerySnapshot> query = fs.collection("Workouts") // Colección Workouts
						.document(workoutId) // Documento específico de Workouts
						.collection(ejersCollection) // Subcolección Ejercicios
						.get();

				QuerySnapshot querySnapshot = query.get();
				List<QueryDocumentSnapshot> ejercicios = querySnapshot.getDocuments();

				for (QueryDocumentSnapshot ejercicioDoc : ejercicios) {
					Ejercicio e = new Ejercicio();
					e.setNombre(ejercicioDoc.getString(fieldNombre));
					e.setDescripcion(ejercicioDoc.getString(fieldDescripcion));
					e.setFoto(ejercicioDoc.getString(fieldFoto));

					Long numReps = ejercicioDoc.getLong(fieldnumReps);
					Long numSeries = ejercicioDoc.getLong(fieldNumSeries);
					Long tiempoDescanso = ejercicioDoc.getLong(fieldTiempoDescanso);

					// Si no es null, convertir Long a int
					if (numReps != null) {
						e.setNumReps(numReps);
					}
					if (numSeries != null) {
						e.setNumSeries(numSeries);
					}
					if (tiempoDescanso != null) {
						e.setTiempoDescanso(tiempoDescanso);
					}

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
