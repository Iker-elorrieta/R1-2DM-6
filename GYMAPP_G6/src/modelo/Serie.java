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

public class Serie implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nombreSerie;
	private long numReps, tiempo;

	// NOMBRE DE LOS CAMPOS
	private static String seriesCollection = "Series";
	private static String fieldNombre = "nombre";
	private static String fieldNumReps = "numRepeticiones";
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

	public void setNumReps(long numReps) {
		this.numReps = numReps;
	}

	public long getTiempo() {
		return tiempo;
	}

	public void setTiempo(long tiempo) {
		this.tiempo = tiempo;
	}

	public ArrayList<Serie> obtenerSeries(String ejersCollection, String ejercicioId, String workoutId,
			boolean online) {
		ArrayList<Serie> listaSeries = new ArrayList<>();

		if (!online) {
			try (FileInputStream fic = new FileInputStream(Backup.FILE_WORKOUTS);
					ObjectInputStream ois = new ObjectInputStream(fic)) {

				while (fic.getChannel().position() < fic.getChannel().size()) {
					Ejercicio ejercicio = (Ejercicio) ois.readObject();

					// Verifica si el workout tiene el id que estamos buscando
					if (ejercicio.getId().equals(ejercicioId)) {
						listaSeries = ejercicio.getListaSeries(); // Obtiene los ejercicios asociados
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

				DocumentReference workoutDoc = fs.collection("Workouts").document(workoutId);
				DocumentReference ejerDoc = workoutDoc.collection(ejersCollection).document(ejercicioId);

				ApiFuture<QuerySnapshot> seriesQuery = ejerDoc.collection(seriesCollection).get();
				QuerySnapshot seriesSnapshot = seriesQuery.get();
				List<QueryDocumentSnapshot> series = seriesSnapshot.getDocuments();

				for (QueryDocumentSnapshot serieDoc : series) {
					Serie s = new Serie();

					s.setNombreSerie(serieDoc.getString(fieldNombre));
					Long numReps = serieDoc.getLong(fieldNumReps);
					Long tiempo = serieDoc.getLong(fieldTiempo);

					if (numReps != null) {
						s.setNumReps(numReps.intValue());
					}
					if (tiempo != null) {
						s.setTiempo(tiempo.intValue());
					}

					listaSeries.add(s);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fs != null) {
					try {
						fs.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		return listaSeries;
	}

}
