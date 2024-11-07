package modelo;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.io.IOException;

public class Backup {

	public static final String FILE_USERS = "backups/usuario.dat";
	public static final String FILE_WORKOUTS = "backups/workouts.dat";

	private static int maxLevel = 3;

	public static void main(String[] args) {
		guardarUsuarios(new Usuario().obtenerMultiplesUsuarios(true));
		guardarWorkouts(new Workout().obtenerWorkouts((long) maxLevel, true));
	}

	public static void guardarUsuarios(ArrayList<Usuario> usuarios) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_USERS))) {
			for (Usuario usu : usuarios) {
				oos.writeObject(usu);
			}
			System.out.println("Users saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void guardarWorkouts(ArrayList<Workout> workouts) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_WORKOUTS))) {
			for (Workout wot : workouts) {

				if (wot.getListaEjercicios() == null || wot.getListaEjercicios().isEmpty()) {

					wot.setListaEjercicios(new Ejercicio().obtenerEjercicios(wot.getId(), true)); // Este método debería
																									// llenar la lista
				}
				oos.writeObject(wot);
			}
			System.out.println("Workouts saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
