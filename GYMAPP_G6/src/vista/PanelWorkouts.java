package vista;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;

public class PanelWorkouts extends JPanel {

	private static final long serialVersionUID = 1L;

	private DefaultListModel<String> workoutListModel;

	private JList<String> workoutsList;

	private DefaultListModel<String> ejersListModel;

	private JList<String> ejersList;

	private JButton btnReturn, btnVideo, btnDescripcion;

	private Map<String, String> workoutUrls = new HashMap<>();

	private Map<String, String> workoutDescripciones = new HashMap<>();

	public PanelWorkouts() {
		setBackground(new Color(141, 204, 235));

		setBounds(0, 0, 880, 560);
		setLayout(null);

		JLabel lblWorkouts = new JLabel("Workouts");
		lblWorkouts.setHorizontalAlignment(SwingConstants.CENTER);
		lblWorkouts.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblWorkouts.setBounds(274, 55, 271, 36);
		add(lblWorkouts);

		workoutListModel = new DefaultListModel<>();
		workoutsList = new JList<>(workoutListModel);
		workoutsList.setBackground(new Color(208, 239, 249));
		workoutsList.setFont(new Font("Tahoma", Font.BOLD, 14));

		JScrollPane scrollPaneWorkouts = new JScrollPane(workoutsList);
		scrollPaneWorkouts.setBounds(50, 150, 350, 300);
		add(scrollPaneWorkouts);

		JLabel lblSelecWorkout = new JLabel("Selecciona un Workout:");
		lblSelecWorkout.setBackground(new Color(156, 210, 239));
		scrollPaneWorkouts.setColumnHeaderView(lblSelecWorkout);
		lblSelecWorkout.setFont(new Font("Tahoma", Font.BOLD, 14));

		ejersListModel = new DefaultListModel<>();
		ejersList = new JList<>(ejersListModel);
		ejersList.setBackground(new Color(211, 235, 248));
		ejersList.setFont(new Font("Tahoma", Font.BOLD, 14));

		JScrollPane scrollPaneEjers = new JScrollPane(ejersList);
		scrollPaneEjers.setBounds(450, 150, 350, 300); // Ajusta el tamaño y la posición del scroll
		add(scrollPaneEjers);

		JLabel lblSelecEjer = new JLabel("Selecciona un ejercicio:");
		lblSelecEjer.setBackground(new Color(156, 210, 239));
		lblSelecEjer.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPaneEjers.setColumnHeaderView(lblSelecEjer);

		btnReturn = new JButton("Atrás");
		btnReturn.setBounds(10, 513, 102, 36);
		add(btnReturn);

		btnVideo = new JButton("Ver vídeo");
		btnVideo.setBounds(450, 470, 150, 36);
		btnVideo.setEnabled(false);
		add(btnVideo);

		btnDescripcion = new JButton("Ver descripción");
		btnDescripcion.setEnabled(false);
		btnDescripcion.setBounds(650, 470, 150, 36);
		add(btnDescripcion);

	}

	public void addWorkout(String workout, String url, String descripcion) {
		if (workout != null && !workout.trim().isEmpty()) {
			workoutListModel.addElement(workout);
			workoutUrls.put(workout, url);
			workoutDescripciones.put(workout, descripcion);
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

	public DefaultListModel<String> getWorkoutListModel() {
		return workoutListModel;
	}

	public void setWorkoutListModel(DefaultListModel<String> workoutListModel) {
		this.workoutListModel = workoutListModel;
	}

	public JList<String> getWorkoutList() {
		return workoutsList;
	}

	public void setWorkoutsList(JList<String> workoutsList) {
		this.workoutsList = workoutsList;
	}

	public DefaultListModel<String> getEjersListModel() {
		return ejersListModel;
	}

	public void setEjersListModel(DefaultListModel<String> ejersListModel) {
		this.ejersListModel = ejersListModel;
	}

	public JList<String> getEjersList() {
		return ejersList;
	}

	public void setEjersList(JList<String> ejersList) {
		this.ejersList = ejersList;
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
}
