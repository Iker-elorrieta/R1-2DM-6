package modelo;

import java.util.ArrayList;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;

public class Serie {
	private String nombreSerie;
	private long numReps, tiempo;

	// NOMBRE DE LOS CAMPOS
	private static String seriesCollection = "Series";
	private static String ejersCollection = "Ejercicios";
	private static String workoutCollection = "Workout";
	private static String fieldNombre = "nombre";
	private static String fieldnumReps = "numRepeticiones";
	private static String fieldTiempo = "tiempo";

	public Serie() {

	}

	public Serie(String nombreSerie, int numReps, int tiempo) {
		this.nombreSerie = nombreSerie;
		this.numReps = numReps;
		this.tiempo = tiempo;
	}

	public String getNombreSerie() {
		return nombreSerie;
	}

	public void setNombreSerie(String nombreSerie) {
		this.nombreSerie = nombreSerie;
	}

	public long getNumReps() {
		return numReps;
	}

	public void setNumReps(Long numReps) {
		this.numReps = numReps;
	}

	public long getTiempo() {
		return tiempo;
	}

	public void setTiempo(long tiempo) {
		this.tiempo = tiempo;
	}

	

}
