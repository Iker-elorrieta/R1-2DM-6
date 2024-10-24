package modelo;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.io.IOException;

public class Backup {

	public static void main(String[] args) {
		guardarUsuarios(new Usuario().obtenerMultiplesUsuarios());
		guardarWorkouts(new Workout().obtenerWorkouts((long) 3));
	}

	private static final String FILE_USERS = "backups/usuario.dat";
	private static final String FILE_WORKOUTS = "backups/workouts.dat";

	public static void guardarUsuarios(ArrayList<Usuario> usuarios) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_USERS))) {
			oos.writeObject(usuarios);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void guardarWorkouts(ArrayList<Workout> workouts) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_WORKOUTS))) {
			oos.writeObject(workouts);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
