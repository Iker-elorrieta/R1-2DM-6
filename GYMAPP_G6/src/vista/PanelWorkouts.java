package vista;

import javax.swing.*;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import modelo.Ejercicio;
import modelo.Workout;

import javax.swing.JButton;
import java.awt.Color;

public class PanelWorkouts extends JPanel {

	private static final long serialVersionUID = 1L;
	private JList<Workout> workoutsList;
	private DefaultListModel<Workout> workoutListModel;
	
	private JList<Ejercicio> ejersList;
	private DefaultListModel<Ejercicio> ejersListModel;
	
	private JButton btnReturn, btnVideo, btnDescripcion, btnStartWorkout;
	private Map<String, String> workoutUrls = new HashMap<>();
	private Map<String, String> workoutDescripciones = new HashMap<>();

	public PanelWorkouts() {

		setBounds(0, 0, 880, 560);
		setBackground(new Color(173, 216, 230)); // Fondo azul claro
		setLayout(null);

		// Etiqueta de título

		JLabel lblWorkouts = new JLabel("Workouts");
		lblWorkouts.setHorizontalAlignment(SwingConstants.CENTER);
		lblWorkouts.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblWorkouts.setForeground(new Color(10, 75, 128)); // Azul oscuro
		lblWorkouts.setBounds(274, 20, 271, 36);
		add(lblWorkouts);

		workoutListModel = new DefaultListModel<>();
		workoutsList = new JList<>(workoutListModel);
		workoutsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		workoutsList.setBackground(new Color(208, 239, 249)); // Fondo de la lista
		workoutsList.setFont(new Font("Tahoma", Font.BOLD, 14));
		workoutsList.setBorder(BorderFactory.createLineBorder(new Color(10, 75, 128), 2)); // Borde azul oscuro

		JScrollPane scrollPaneWorkouts = new JScrollPane(workoutsList);
		scrollPaneWorkouts.setBounds(50, 100, 350, 350);
		add(scrollPaneWorkouts);

		JLabel lblSelecWorkout = new JLabel("Selecciona un Workout:");
		lblSelecWorkout.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSelecWorkout.setForeground(new Color(10, 75, 128)); // Azul oscuro
		scrollPaneWorkouts.setColumnHeaderView(lblSelecWorkout);

		ejersListModel = new DefaultListModel<>();
		ejersList = new JList<>(ejersListModel);
		ejersList.setBackground(new Color(211, 235, 248)); // Fondo de la lista de ejercicios
		ejersList.setFont(new Font("Tahoma", Font.BOLD, 14));
		ejersList.setBorder(BorderFactory.createLineBorder(new Color(10, 75, 128), 2)); // Borde azul oscuro

		JScrollPane scrollPaneEjers = new JScrollPane(ejersList);
		scrollPaneEjers.setBounds(450, 150, 350, 300); // Ajusta el tamaño y la posición del scroll
		add(scrollPaneEjers);

		JLabel lblSelecEjer = new JLabel("Selecciona un ejercicio:");
		lblSelecEjer.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSelecEjer.setForeground(new Color(10, 75, 128)); // Azul
		scrollPaneEjers.setColumnHeaderView(lblSelecEjer);

		btnVideo = new JButton("Ver vídeo");
		btnVideo.setEnabled(false);
		btnVideo.setBounds(240, 480, 160, 40);
		btnVideo.setBackground(new Color(10, 75, 128)); // Azul
		btnVideo.setForeground(Color.WHITE);
		btnVideo.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnVideo.setFocusPainted(false);
		btnVideo.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		add(btnVideo);

		btnDescripcion = new JButton("Ver descripción");
		btnDescripcion.setBounds(450, 480, 160, 40);
		btnDescripcion.setBackground(new Color(10, 75, 128)); // Azul
		btnDescripcion.setForeground(Color.WHITE);
		btnDescripcion.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnDescripcion.setFocusPainted(false);
		btnDescripcion.setEnabled(false);
		btnDescripcion.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		add(btnDescripcion);

		// Botón de regresar
		btnReturn = new JButton("Atrás");
		btnReturn.setBounds(33, 480, 160, 40);
		btnReturn.setBackground(new Color(10, 75, 128)); // Azul
		btnReturn.setForeground(Color.WHITE);
		btnReturn.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnReturn.setFocusPainted(false);
		btnReturn.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		add(btnReturn);

		// Botón para iniciar el workout
		btnStartWorkout = new JButton("Iniciar Workout");
		btnStartWorkout.setBounds(680, 480, 160, 40);
		btnStartWorkout.setBackground(new Color(10, 75, 128)); // Azul oscuro
		btnStartWorkout.setForeground(Color.WHITE);
		btnStartWorkout.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnStartWorkout.setFocusPainted(false);
		btnStartWorkout.setEnabled(false);
		btnStartWorkout.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		add(btnStartWorkout);

	}

	/**
	 * Método para añadir un objeto Workout al modelo de la lista
	 * @param workout
	 * @param url
	 * @param descripcion
	 */
	public void addWorkout(Workout workout, String url, String descripcion) {
		if (workout != null) {
			workoutListModel.addElement(workout);
			workoutUrls.put(workout.getVideoUrl(), url);
			workoutDescripciones.put(workout.getDescripcion(), descripcion);
		}
	}

	public JButton getBtnReturn() {
		return btnReturn;
	}

	public void setBtnReturn(JButton btnReturn) {
		this.btnReturn = btnReturn;
	}

	public JButton getBtnVideo() {
		return btnVideo;
	}

	public void setBtnVideo(JButton btnVideo) {
		this.btnVideo = btnVideo;
	}

	public JButton getBtnDescripcion() {
		return btnDescripcion;
	}

	public void setBtnDescripcion(JButton btnDescripcion) {
		this.btnDescripcion = btnDescripcion;
	}
	
	public JList<Workout> getWorkoutList() {
		return workoutsList;
	}

	public void setWorkoutsList(JList<Workout> workoutsList) {
		this.workoutsList = workoutsList;
	}

	public DefaultListModel<Workout> getWorkoutListModel() {
		return workoutListModel;
	}

	public void setWorkoutListModel(DefaultListModel<Workout> workoutListModel) {
		this.workoutListModel = workoutListModel;
	}

	
	public JList<Ejercicio> getEjersList() {
		return ejersList;
	}

	public void setEjersList(JList<Ejercicio> ejersList) {
		this.ejersList = ejersList;
	}
	
	public DefaultListModel<Ejercicio> getEjersListModel() {
		return ejersListModel;
	}

	public void setEjersListModel(DefaultListModel<Ejercicio> ejersListModel) {
		this.ejersListModel = ejersListModel;
	}

	

	public String getWorkoutUrl(String workout) {
		return workoutUrls.get(workout); // Retorna la URL asociada al workout
	}

	public Map<String, String> getWorkoutUrls() {
		return workoutUrls;
	}

	public void setWorkoutUrls(Map<String, String> workoutUrls) {
		this.workoutUrls = workoutUrls;
	}

	public void setWorkoutDescripciones(Map<String, String> workoutDescripciones) {
		this.workoutDescripciones = workoutDescripciones;
	}

	public String getWorkoutDescripcion(String workout) {
		return workoutDescripciones.get(workout); // Retorna la descripción asociada al workout
	}

	public Map<String, String> getWorkoutDescripciones() {
		return workoutDescripciones;
	}

	public JButton getBtnStartWorkout() {
		return btnStartWorkout;
	}

	public void setBtnStartWorkout(JButton btnStartWorkout) {
		this.btnStartWorkout = btnStartWorkout;
	}

}